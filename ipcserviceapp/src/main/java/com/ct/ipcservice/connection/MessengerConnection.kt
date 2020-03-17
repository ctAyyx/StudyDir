package com.ct.ipcservice.connection

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import android.os.Messenger
import android.util.Log

class MessengerConnection : ServiceConnection {

    var mMessenger: Messenger? = null
        private set

    override fun onServiceDisconnected(name: ComponentName?) {
        Log.e("TAG", "与IPCMessengerService断开连接--$name")
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        Log.e("TAG", "与IPCMessengerService建立连接--$name")
        mMessenger = Messenger(service)

    }

}