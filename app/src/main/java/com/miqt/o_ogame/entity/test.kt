package com.miqt.o_ogame.entity

/**
 * Created by Administrator on 2017/9/4.
 */

class test {
    init {
        val name = "123"
        val a = name.split("2".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    }
}
