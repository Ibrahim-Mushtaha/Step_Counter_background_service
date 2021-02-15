package com.ix.ibrahim7.stepcounter.ui

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ix.ibrahim7.stepcounter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() , SensorEventListener {

    private lateinit var mBinding: ActivityMainBinding
    private var sensorManager: SensorManager? = null
    private var running = false
    private var totalStep = 0f
    private var previousTotalStep = 0f

    override fun onResume() {
        super.onResume()
        running  =true
        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if (stepSensor == null)
            Toast.makeText(this,"Sensor not available in your phone",Toast.LENGTH_SHORT).show()
        else
            sensorManager?.registerListener(this,stepSensor,SensorManager.SENSOR_DELAY_UI)
    }

    @SuppressLint("ServiceCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.circularProgressBar.apply {
            setProgressWithAnimation(0f)
        }
        loadData()
        resetSteps()

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (running)
            totalStep = event!!.values[0]
        val currentSteps = totalStep.toInt() - previousTotalStep.toInt()
        mBinding.txtStepCount.text = ("$currentSteps")

        mBinding.circularProgressBar.apply {
            setProgressWithAnimation(currentSteps.toFloat())
        }

    }


    fun resetSteps(){
        mBinding.txtStepCount.setOnLongClickListener {
            previousTotalStep = totalStep
            mBinding.txtStepCount.text = "0"
            saveDate()
            true
        }
    }

    private fun saveDate() {
        val sharePref =getSharedPreferences("myPrefs",Context.MODE_PRIVATE)
        sharePref.apply {
            edit().putFloat("key1",previousTotalStep).apply()
        }
    }

    private fun loadData(){
        val sharePref =getSharedPreferences("myPrefs",Context.MODE_PRIVATE)
        val saveNumber = sharePref.getFloat("key1",0f)
        previousTotalStep = saveNumber
    }


}