package com.miqt.o_ogame.net


interface ITcpClient {
    /**
     * 连接
     */
    fun connector(address: String, port: Int)

    /**
     * 发送消息
     */
    fun send(data: String)

    /**
     * 关闭
     */
    fun close()

    /**
     * 是否已经连接
     */
    fun isConnected(): Boolean
}