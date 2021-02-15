package com.ix.ibrahim7.stepcounter.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.ix.ibrahim7.stepcounter.databinding.ActivityMainBinding
import com.ix.ibrahim7.stepcounter.other.STEPNUMBER
import com.ix.ibrahim7.stepcounter.util.Constant
import kotlin.properties.Delegates


class MyService : Service(),SensorEventListener {

    private var sensorManager: SensorManager? = null
    private var running = false
    private var totalStep = 0f
    private var previousTotalStep = 0f

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        try {
            running  =true
            val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

            sensorManager?.registerListener(this,stepSensor,SensorManager.SENSOR_DELAY_UI)
        } catch (e: Exception) {
            Log.e("eee ERROR", e.message.toString())
        }


        return super.onStartCommand(intent, flags, startId)
    }


    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onSensorChanged(event: SensorEvent?) {
        if (running)
            totalStep = event!!.values[0]
        val currentSteps = totalStep.toInt() - previousTotalStep.toInt()
        Constant.editor(this).putFloat(STEPNUMBER,previousTotalStep).apply()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }




    override fun onDestroy() {
        val intent = Intent(this,MyPhoneReciver::class.java)
        sendBroadcast(intent)
        super.onDestroy()
    }

}