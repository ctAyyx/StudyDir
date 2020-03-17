package com.ct.ipcservice.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.ct.baseapp.util.AppUtil
import com.ct.ipcservice.IPCBinder

/**
 * 通过Binder进行通讯
 * */
class IPCBinderService : Service() {

    override fun onCreate() {
        super.onCreate()
        Log.e("TAG", "IPCService 启动 ===>${AppUtil.getCurrentProcessName(this)}")
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.e("TAG", "IPCService onBind ===>${AppUtil.getCurrentProcessName(this)}")
        return IPCBinder()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.e("TAG", "IPCService unbind")
        return super.onUnbind(intent)
    }

}