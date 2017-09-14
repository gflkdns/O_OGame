package com.miqt.o_ogame

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.miqt.o_ogame.net.ITcpServer
import com.miqt.o_ogame.net.ITcpClient
import com.miqt.o_ogame.thread.TcpServerThread
import java.io.BufferedWriter
import java.io.IOException
import java.io.OutputStreamWriter
import java.net.ServerSocket
import java.net.Socket

class TcpService : Service() {
    private val mBinder = TcpBinder()
    override fun onBind(intent: Intent): IBinder? = mBinder
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return Service.START_STICKY
    }

    inner class TcpBinder : Binder(), ITcpServer, ITcpClient {
        private lateinit var sc: Socket
        private var isConnector = false
        var runing = true
        lateinit var ss: ServerSocket

        override fun isConnected(): Boolean {
            return isConnector
        }

        override fun stop() {
            Thread {
                runing = false
            }.start()
        }

        override fun start(port: Int) {
            Thread {
                ss = ServerSocket(cfg.tcp_port)
                while (runing) {
                    val sk = ss.accept()
                    //启动一个线程来处理这一次连接
                    TcpServerThread(sk).start()
                }
                ss.close()
            }.start()
        }

        override fun connector(address: String, port: Int) {
            Thread {
                try {
                    sc = Socket(address, port)
                    isConnector = true
                } catch (e: IOException) {
                    isConnector = false
                }

            }.start()
        }

        override fun send(data: String) {
            Thread {
                if (isConnector) {
                    synchronized(sc.isClosed) {
                        val out = sc.getOutputStream()
                        val osw = OutputStreamWriter(out)
                        val buffw = BufferedWriter(osw)
                        buffw.write(data)
                        buffw.flush()
                        buffw.close()
                    }
                }
            }.start()
        }

        override fun close() {
            Thread {
                sc.close()
            }.start()
        }
    }
}
