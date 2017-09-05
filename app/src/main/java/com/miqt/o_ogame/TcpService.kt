package com.miqt.o_ogame

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.miqt.o_ogame.entity.Data
import com.miqt.o_ogame.entity.Device
import com.miqt.o_ogame.thread.TcpServerThread
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.StringReader
import java.net.ServerSocket
import java.net.Socket

class TcpService : Service() {
    val mBinder = TcpBinder()
    override fun onBind(intent: Intent): IBinder? = mBinder
    inner class TcpBinder : Binder() {
        var runing = true
        lateinit var ss: ServerSocket
        /**
         * 启动一个服务，一直等着接收消息
         */
        fun start() {
            Thread {
                ss = ServerSocket(cfg.tcp_port)
                while (runing) {
                    val sk = ss.accept()
                    //启动一个线程来处理这一次连接
                    TcpServerThread(sk).start()
                }
            }.start()
        }

        /**
         * 停止服务器
         */
        fun stop() {
            Thread {
                runing=false
                ss.close()
            }.start()
        }
    }
}
