package com.miqt.o_ogame.thread

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.miqt.o_ogame.app.AppBus
import com.miqt.o_ogame.entity.Bullet
import com.miqt.o_ogame.entity.Data
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.Socket

/**
 * Created by Administrator on 2017/9/5.
 */
class TcpServerThread(val s: Socket) : Thread() {

    override fun run() {
        super.run()
        //如果这个套接字没有被关闭，就一直等着获取消息
        tcp_loop@ while (true) {
            val inp = s.getInputStream()
            val ir = InputStreamReader(inp)
            val reader = BufferedReader(ir)
            val json = reader.readLine()
            val data = Gson().fromJson<Data<String>>(json, object : TypeToken<Data<String>>() {}.type)
            when (data.what) {
                Data.TYPE_TCP_CHAT_MESSAGE -> {
                    //val data=Gson().fromJson(data,)
                }
                Data.TYPE_TCP_CLOSE -> {
                    inp.close()
                    ir.close()
                    reader.close()
                    s.close()
                    break@tcp_loop
                }
                Data.TYPE_TCP_GAME_BULLET -> {
                    val bullet = Gson().fromJson(data.data, Bullet::class.java)
                    AppBus.getInstance().post(bullet)
                }
            }
        }
    }
}