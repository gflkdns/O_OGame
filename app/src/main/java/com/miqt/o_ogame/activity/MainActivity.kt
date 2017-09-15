package com.miqt.o_ogame.activity

import android.content.Intent
import android.hardware.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.miqt.o_ogame.R

class MainActivity : AppCompatActivity() {

    private lateinit var manager: SensorManager
    private lateinit var sensor: Sensor
    private lateinit var listener: InnerListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        manager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensor = manager.getDefaultSensor(SensorManager.SENSOR_LIGHT, true)
        listener = InnerListener()
    }

    fun onMenuClick(v: View) {
        when (v.id) {
            R.id.bt_wifi -> {
                //启动WiFi连接页面
                startActivity(Intent(this, WiFIConnActivity::class.java))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        manager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI)
    }

    override fun onPause() {
        super.onPause()
        manager.unregisterListener(listener)
    }

    class InnerListener : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {

        }

        override fun onSensorChanged(event: SensorEvent) {
        }

    }
}
