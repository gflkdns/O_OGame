package com.miqt.o_ogame.net

import android.content.Context
import com.miqt.o_ogame.cfg
import java.net.DatagramPacket
import java.net.InetAddress
import java.net.MulticastSocket
import java.nio.charset.Charset
import android.net.wifi.WifiManager.MulticastLock
import android.net.wifi.WifiManager


/**
 * Created by Administrator on 2017/8/31.
 */
abstract class AUdpReceive : IReceive {
    constructor()
    constructor( context: Context){
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        multicastLock = wifiManager.createMulticastLock("multicast.test")
        multicastLock.acquire()
    }
    lateinit var multicastLock: MulticastLock
    var socket:MulticastSocket
    init {
        socket = MulticastSocket(cfg.udp_port)
    }
    override fun join() {
        val address = InetAddress.getByName(cfg.udp_ip)
        socket.joinGroup(address)
        val buf = ByteArray(1024)
        val p = DatagramPacket(buf, buf.size, address, cfg.udp_port)
        while (!socket.isClosed) {
            socket.receive(p)
            val str = String(p.data, Charset.forName("UTF-8")).trimEnd().split("\n".toRegex())
            onRecerver(str[0])
        }
    }

    override fun close() {
        socket.close()
        multicastLock.release()
    }
}