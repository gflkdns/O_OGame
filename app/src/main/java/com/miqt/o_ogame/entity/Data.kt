package com.miqt.o_ogame.entity

/**
 * Created by Administrator on 2017/9/1.
 */
data class Data<T>(val what: Int, val data: T) {
    companion object {
        /**
         * 广播设备属性
         */
        val TYPE_DEVICE_INFO = 0x1
        /**
         * 请求tcp连接
         */
        val TYPE_TCP_CHAT_MESSAGE = 0x2
        val TYPE_TCP_GAME_BULLET = 0x3
        val TYPE_TCP_CLOSE = 0x4
    }
}
