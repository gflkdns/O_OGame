package com.miqt.o_ogame.presenter

import com.miqt.o_ogame.entity.Device

interface IUdpPublishPresenter {
    /**
     * 发送广播
     */
    fun publish(text: String)

    /**
     * 广播接收监听
     */
    fun addPublishReceiver(reveiver: PublishReceiver)

    /**
     * 获得当前可连接的设备
     */
    fun getCurrDev(): List<Device>

    /**
     * 启动定时发送心跳包
     */
    fun start(time: Long)

    fun stop()

    /**
     * 设置组播的订阅者
     */
    interface PublishReceiver {
        fun onReverver(msg: String)
    }
}