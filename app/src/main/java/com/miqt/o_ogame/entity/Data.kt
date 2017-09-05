package com.miqt.o_ogame.entity

/**
 * Created by Administrator on 2017/9/1.
 */
data class Data<T>(val type: Int, val data: T){
    companion object {
        val TYPE_DEVICE_INFO=0x1
    }
}
