package com.ct.ipcservice.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.ct.ipcservice.socket.SocketBuilder

class SocketService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        kotlin.run {
            SocketBuilder().createSocket()
        }
    }

}