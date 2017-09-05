package com.miqt.o_ogame.net

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
    val socket = MulticastSocket(cfg.udp_port)

    init {
        socket.setTimeToLive(1)
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
    }
}