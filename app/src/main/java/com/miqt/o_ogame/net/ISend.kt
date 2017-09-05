package com.miqt.o_ogame.net

/**
 * Created by Administrator on 2017/8/31.
 */
interface ISend {
    fun send(message: String)
    fun close()
}