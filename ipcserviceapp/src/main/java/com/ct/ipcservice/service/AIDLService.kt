package com.ct.ipcservice.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.ct.ipcservice.binder.AIDLBinder

/**
 * 使用AIDL进行跨进程通讯
 * */
class AIDLService : Service() {

    override fun onCreate() {
        super.onCreate()
        Log.e("TAG", "AIDLService服务被创建")
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.e("TAG", "AIDLService onBind")
        return AIDLBinder()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.e("TAG", "AIDLService onUnBind")
        return super.onUnbind(intent)
    }

}