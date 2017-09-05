package com.miqt.o_ogame

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.ListView
import com.miqt.o_ogame.entity.Device
import com.miqt.o_ogame.presenter.IUdpPublishPresenter
import com.miqt.o_ogame.presenter.PublishPresenter
import com.miqt.o_ogame.view.IDevListView

class DevListActivity : AppCompatActivity(), IDevListView {
    var data = ArrayList<String>()
    override fun notifyDataSetChanged(dev: List<String>) {
        runOnUiThread {
            data.clear()
            data.addAll(dev)
            adapter.notifyDataSetChanged()
        }
    }

    private lateinit var pst: IUdpPublishPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dev_list)
        val lv_dev = findViewById(R.id.lv_dev) as ListView
        lv_dev.adapter = arrayAdapter()
        pst = PublishPresenter(this)
        pst.start(2000)
    }

    override fun onDestroy() {
        super.onDestroy()
        pst.stop()
    }

    private lateinit var adapter: ArrayAdapter<String>

    private fun arrayAdapter(): ArrayAdapter<String> {
        adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data)
        return adapter
    }
}
