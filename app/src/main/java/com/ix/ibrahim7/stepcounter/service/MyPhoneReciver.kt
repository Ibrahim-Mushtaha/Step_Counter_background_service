package com.ix.ibrahim7.stepcounter.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat

class MyPhoneReciver : BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
            ContextCompat.startForegroundService(context!!, Intent(context, MyService::class.java))
    }

}