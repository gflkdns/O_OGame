package com.miqt.o_ogame.net

/**
 * Created by Administrator on 2017/8/31.
 */
interface IReceive {
    fun join()
    fun onRecerver(message: String)
    fun close()
}