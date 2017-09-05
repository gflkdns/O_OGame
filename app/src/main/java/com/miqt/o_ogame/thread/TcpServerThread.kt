package com.miqt.o_ogame.thread

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.miqt.o_ogame.entity.Data
import com.miqt.o_ogame.entity.Device
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.Socket

/**
 * Created by Administrator on 2017/9/5.
 */
class TcpServerThread(val s: Socket) : Thread() {

    override fun run() {
        super.run()
        val inp = s.getInputStream()
        val ir = InputStreamReader(inp)
        val reader = BufferedReader(ir)
        val json = reader.readLine()
        val data = Gson().fromJson<Data<Device>>(json, object : TypeToken<Data<String>>() {}.type)
        when (data.what) {
            Data.TYPE_REQUEST_TCP_CONN -> {
                //
            }
        }
    }
}