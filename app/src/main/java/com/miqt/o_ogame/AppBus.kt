package com.miqt.o_ogame

import com.squareup.otto.Bus


/**
 * Created by Administrator on 2017/9/14.
 */
class AppBus private constructor() {
    companion object {
        @Volatile private lateinit var bus: Bus    //使用volatile来修饰多线程访问变量的情况
        fun getInstance(): Bus {
            if (bus == null) {
                synchronized(AppBus::class.java) {
                    bus = Bus()
                }
            }
            return bus
        }
    }
}