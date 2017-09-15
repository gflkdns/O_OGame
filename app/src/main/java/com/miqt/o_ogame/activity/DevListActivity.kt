package com.miqt.o_ogame.activity

import android.Manifest
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.miqt.o_ogame.presenter.IUdpPublishPresenter
import com.miqt.o_ogame.presenter.PublishPresenter
import com.miqt.o_ogame.view.IDevListView
import pub.devrel.easypermissions.EasyPermissions
import android.net.wifi.WifiManager.MulticastLock
import android.net.wifi.WifiManager
import com.miqt.o_ogame.R


class DevListActivity : AppCompatActivity(), IDevListView, EasyPermissions.PermissionCallbacks {
    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>?) {
        EasyPermissions.requestPermissions(this, "必要的权限",
                requestCode,
                Manifest.permission.CHANGE_WIFI_MULTICAST_STATE)
    }

    private lateinit var wifiManager: WifiManager

    private lateinit var multicastLock: MulticastLock

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>?) {
        if (requestCode == REQUEST_GIT_CHANGE_WIFI_MULTICAST_STATE) {
            openMulticastLock()
        }
    }

    private fun openMulticastLock() {
        wifiManager = getSystemService(Context.WIFI_SERVICE) as WifiManager
        multicastLock = wifiManager.createMulticastLock("multicast.test")
        multicastLock.acquire()
    }


    var data = ArrayList<String>()
    override fun notifyDataSetChanged(data: List<String>) {
        runOnUiThread {
            this.data.clear()
            this.data.addAll(data)
            adapter.notifyDataSetChanged()
        }
    }

    private lateinit var pst: IUdpPublishPresenter


    companion object {
        val REQUEST_GIT_CHANGE_WIFI_MULTICAST_STATE = 0x2
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dev_list)
        val lv_dev = findViewById(R.id.lv_dev) as ListView
        if (!EasyPermissions.hasPermissions(this, Manifest.permission.CHANGE_WIFI_MULTICAST_STATE)) {
            EasyPermissions.requestPermissions(this, "必要的权限",
                    REQUEST_GIT_CHANGE_WIFI_MULTICAST_STATE,
                    Manifest.permission.CHANGE_WIFI_MULTICAST_STATE)
        }

        lv_dev.adapter = arrayAdapter()
        pst = PublishPresenter(this, this)

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onResume() {
        super.onResume()
        openMulticastLock()
        if (multicastLock.isHeld) {
            pst.start(2000)
        }
    }

    override fun onPause() {
        super.onPause()
        pst.stop()
        closeMulticastLock()
    }

    private fun closeMulticastLock() {
        multicastLock.release()
    }

    private lateinit var adapter: ArrayAdapter<String>

    private fun arrayAdapter(): ArrayAdapter<String> {
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, data)
        return adapter
    }
}
