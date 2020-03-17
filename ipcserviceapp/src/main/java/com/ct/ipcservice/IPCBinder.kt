package com.ct.ipcservice

import android.os.Binder
import android.os.Parcel
import android.util.Log

class IPCBinder : Binder() {

    override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {

        when (code) {
            1000 -> {
                Log.e("TAG", "收到来自客户端的数据:${data.readInt()}--${data.readInt()} -- $flags")

                reply?.writeInt(250)
                return true
            }
        }

        return super.onTransact(code, data, reply, flags)
    }
}