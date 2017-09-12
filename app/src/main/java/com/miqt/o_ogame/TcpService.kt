package com.miqt.o_ogame

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.miqt.o_ogame.net.ITcpServer
import com.miqt.o_ogame.net.ITcpClient
import com.miqt.o_ogame.thread.TcpServerThread
import java.net.ServerSocket
import java.net.Socket

class TcpService : Service() {
    private val mBinder = TcpBinder()
    override fun onBind(intent: Intent): IBinder? = mBinder
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return Service.START_STICKY
    }

    inner class TcpBinder : Binder(), ITcpServer, ITcpClient {
        var runing = true
        lateinit var ss: ServerSocket

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

        lateinit var sc: Socket
        override fun connector(address: String, port: Int) {
            sc=Socket(address,port)
          //  sc.connect(address)
        }


        override fun send(data: String) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun close() {

        }

        override fun isConnected() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }


    }
}
