package com.ix.ibrahim7.stepcounter.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.ix.ibrahim7.stepcounter.databinding.ActivityMainBinding
import com.ix.ibrahim7.stepcounter.other.*
import com.ix.ibrahim7.stepcounter.service.MyService
import com.ix.ibrahim7.stepcounter.util.Constant
import com.ix.ibrahim7.stepcounter.util.Constant.getSharePref
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding


    override fun onResume() {
        super.onResume()
        stopService(Intent(this, MyService::class.java))
    }

    @SuppressLint("ServiceCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setSupportActionBar(toolbar)

    }

    override fun onStop() {
        ContextCompat.startForegroundService(this, Intent(this, MyService::class.java))
        super.onStop()
    }

}