package com.miqt.o_ogame.net

import com.miqt.o_ogame.cfg
import java.net.DatagramPacket
import java.net.InetAddress
import java.net.MulticastSocket
import java.nio.charset.Charset

abstract class AUdpReceive : IReceive {
    private var socket: MulticastSocket = MulticastSocket(cfg.udp_port)

    var runing = true

    override fun join() {
        runing = true
        socket.timeToLive = 32
        val address = InetAddress.getByName(cfg.udp_ip)
        socket.joinGroup(address)
        val buf = ByteArray(1024)
        val p = DatagramPacket(buf, buf.size, address, cfg.udp_port)
        while (!socket.isClosed && runing) {
            socket.receive(p)
            val str = String(p.data, Charset.forName("UTF-8")).trimEnd().split("\n".toRegex())
            onRecerver(str[0])
        }
        socket.close()
    }

    override fun close() {
        runing = false
    }
}