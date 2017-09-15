package com.miqt.o_ogame.activity

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.miqt.o_ogame.presenter.IUdpPublishPresenter
import com.miqt.o_ogame.presenter.PublishPresenter
import com.miqt.o_ogame.view.IDevListView
import pub.devrel.easypermissions.EasyPermissions
import android.net.wifi.WifiManager.MulticastLock
import android.net.wifi.WifiManager
import android.os.IBinder
import android.view.View
import android.widget.*
import com.miqt.miwrenchlib.MNetUtils
import com.miqt.o_ogame.R
import com.miqt.o_ogame.cfg
import com.miqt.o_ogame.service.TcpService


class WiFIConnActivity : AppCompatActivity(), IDevListView, EasyPermissions.PermissionCallbacks {

    companion object {
        val REQUEST_GIT_CHANGE_WIFI_MULTICAST_STATE = 0x2
    }

    private lateinit var wifiManager: WifiManager
    private lateinit var multicastLock: MulticastLock
    private lateinit var mBunder: TcpService.TcpBinder
    var data = ArrayList<String>()
    private lateinit var pst: IUdpPublishPresenter
    private lateinit var lv_dev: ListView
    private lateinit var tv_localhost: TextView
    private lateinit var et_ip1: EditText
    private lateinit var et_ip2: EditText
    private lateinit var et_ip3: EditText
    private lateinit var et_ip4: EditText
    private lateinit var adapter: ArrayAdapter<String>

    private lateinit var bt_conn_tcp: Button
    var conn: ServiceConnection? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dev_list)

        lv_dev = findViewById(R.id.lv_dev) as ListView
        tv_localhost = findViewById(R.id.tv_localhost) as TextView
        et_ip1 = findViewById(R.id.et_ip1) as EditText
        et_ip2 = findViewById(R.id.et_ip2) as EditText
        et_ip3 = findViewById(R.id.et_ip3) as EditText
        et_ip4 = findViewById(R.id.et_ip4) as EditText
        bt_conn_tcp = findViewById(R.id.bt_conn_tcp) as Button
        //请求权限
        if (!EasyPermissions.hasPermissions(this, Manifest.permission.CHANGE_WIFI_MULTICAST_STATE)) {
            EasyPermissions.requestPermissions(this, "必要的权限",
                    REQUEST_GIT_CHANGE_WIFI_MULTICAST_STATE,
                    Manifest.permission.CHANGE_WIFI_MULTICAST_STATE)
        }
        startService(Intent(this, TcpService::class.java))
        conn = Conn()
        bindService(Intent(this, TcpService::class.java), conn, Context.BIND_AUTO_CREATE)
        //设置adapter显示组播扫描到的设备
        lv_dev.adapter = arrayAdapter()
        pst = PublishPresenter(this, this)

        val ip = MNetUtils.getIPAddress(this)
        tv_localhost.text = "IP$ip"

        bt_conn_tcp.setOnClickListener {
            val serverip = et_ip1.text.toString() +
                    et_ip2.text.toString() +
                    et_ip3.text.toString() +
                    et_ip4.text.toString()
            mBunder.connector(serverip, cfg.tcp_port)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(conn)
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

    private fun arrayAdapter(): ArrayAdapter<String> {
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, data)
        return adapter
    }

    private inner class Conn : ServiceConnection {

        override fun onServiceDisconnected(name: ComponentName?) {
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mBunder = service as TcpService.TcpBinder
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>?) {
        EasyPermissions.requestPermissions(this, "必要的权限",
                requestCode,
                Manifest.permission.CHANGE_WIFI_MULTICAST_STATE)
    }

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


    override fun notifyDataSetChanged(data: List<String>) {
        runOnUiThread {
            this.data.clear()
            this.data.addAll(data)
            adapter.notifyDataSetChanged()
        }
    }
}
