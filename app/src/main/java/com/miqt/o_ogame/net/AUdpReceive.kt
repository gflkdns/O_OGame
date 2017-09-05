package com.miqt.o_ogame.net

import com.miqt.o_ogame.cfg
import java.net.DatagramPacket
import java.net.InetAddress
import java.net.MulticastSocket
import java.nio.charset.Charset

/**
 * Created by Administrator on 2017/8/31.
 */
abstract class AUdpReceive : IReceive {
    val socket = MulticastSocket(cfg.udp_port)
    override fun join() {
        socket.timeToLive = 1
        val address = InetAddress.getByName(cfg.udp_ip)
        socket.joinGroup(address)
        val buf = ByteArray(1024)
        val p = DatagramPacket(buf, buf.size)
        while (!socket.isClosed) {
            socket.receive(p)
            val str = String(p.data, Charset.forName("UTF-8")).trimEnd().split("\n".toRegex())
            onRecerver(str[0])
        }
    }

    override fun close() {
        socket.close()
    }
}