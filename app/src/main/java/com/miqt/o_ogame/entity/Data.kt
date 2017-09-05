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
        val TYPE_REQUEST_TCP_CONN = 0x2
    }
}
