package com.miqt.o_ogame.view

import com.miqt.o_ogame.entity.Device

/**
 * Created by Administrator on 2017/9/1.
 */
interface IDevListView {

    /**
     * 更新数据的显示
     */
    fun notifyDataSetChanged(data: List<String>)
}