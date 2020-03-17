package com.ct.ipcservice.connection

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import android.os.Parcel
import android.util.Log

class IPCServiceConnection : ServiceConnection {
    override fun onServiceDisconnected(name: ComponentName?) {
        Log.e("TAG", "与IPCService断开连接--$name")
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        Log.e("TAG", "与IPCService建立链接--$name")
        val data = Parcel.obtain()
        data.writeInt(100)
        data.writeInt(150)

        val reply = Parcel.obtain()
        service?.transact(1000, data, reply, 0)
        //reply.readException()
        Log.e("TAG", "获取到的数据:${reply.readInt()}")

    }

}