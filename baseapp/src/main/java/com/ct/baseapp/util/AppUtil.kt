package com.ct.baseapp.util

import android.app.ActivityManager
import android.content.Context

object AppUtil {

    /**
     * 获取当前进程名称
     * */
    fun getCurrentProcessName(context: Context): String? {
        val pid = android.os.Process.myPid()
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityManager.runningAppProcesses.forEach {
            if (it.pid == pid)
                return it.processName
        }
        return null
    }
}