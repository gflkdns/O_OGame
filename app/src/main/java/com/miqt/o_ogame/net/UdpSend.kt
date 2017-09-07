package com.miqt.o_ogame.net

import android.content.Context
import android.net.wifi.WifiManager
import com.miqt.miwrenchlib.MNetUtils
import com.miqt.o_ogame.cfg
import java.io.IOException
import java.net.DatagramPacket
import java.net.InetAddress
import java.net.MulticastSocket

/**
 * Created by Administrator on 2017/8/31.
 */
class UdpSend : ISend {
    var socket: MulticastSocket
    lateinit var multicastLock: WifiManager.MulticastLock
    constructor()
    constructor(context: Context) {
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        multicastLock = wifiManager.createMulticastLock("multicast.test")
        multicastLock.acquire()
    }

    init {

        socket = MulticastSocket(cfg.udp_port)
        val address = InetAddress.getByName(cfg.udp_ip)
        socket.joinGroup(address)
    }


    override fun send(message: String) {
        val buf = "$message\n".toByteArray()
        val address = InetAddress.getByName(cfg.udp_ip)
        val p = DatagramPacket(buf, buf.size, address,
                cfg.udp_port)
        socket.send(p)
        println("广播已经发出")
    }

    override fun close() {
        socket.close()
        multicastLock.release()
    }
}