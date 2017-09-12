package com.miqt.o_ogame.net

/**
 * Created by Administrator on 2017/9/11.
 */
interface ITcpServer {
    fun start(port: Int)
    fun stop()
}