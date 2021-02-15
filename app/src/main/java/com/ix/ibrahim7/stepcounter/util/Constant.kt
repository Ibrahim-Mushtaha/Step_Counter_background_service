package com.ix.ibrahim7.stepcounter.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.ix.ibrahim7.stepcounter.other.*
import com.ix.ibrahim7.stepcounter.R
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlin.collections.ArrayList

object Constant {

    fun getSharePref(context: Context) =
        context.getSharedPreferences(SHARE, Activity.MODE_PRIVATE)

    fun editor(context: Context) = getSharePref(context).edit()



    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun setUpStatusBar(activity: Activity) {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            activity.window.statusBarColor = ContextCompat.getColor(activity, R.color.design_default_color_background)
    }


    @SuppressLint("LogNotTimber")
    fun printLog(tag:String, message:String?){
        Log.v("$TAG $tag", message!!)
    }

    fun getPermission(
        context: Context,
        permissions: ArrayList<String>,
        onComplete: (done:Boolean) -> Unit
    ) {
        Dexter.withContext(context)
            .withPermissions(
                permissions
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.let {
                        when {
                            report.areAllPermissionsGranted() -> {
                                onComplete(true)
                                printLog("location Permissions","onComplete")
                            }
                            else -> {
                                onComplete(false)
                                printLog("location Permissions","onDenied")
                            }
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {

                    token?.continuePermissionRequest()
                }
            })
            .withErrorListener {

            }
            .check()
    }


}