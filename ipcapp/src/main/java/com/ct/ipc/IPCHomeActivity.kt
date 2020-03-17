package com.ct.ipc

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity

/**
 * 实现跨进程通讯
 *
 * 1.Binder
 * */
class IPCHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ipc_home)
    }

    fun onClick(view: View) {

        when (view.id) {
            R.id.btn_ipcHome_binder -> {
                val intent = Intent()
                intent.action = "com.ct.ipc_service"
                intent.`package` = "com.ct.ipcservice"
                bindService(intent, IPCServiceConnection(), Context.BIND_AUTO_CREATE)

            }
        }
    }

    inner class IPCServiceConnection : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            Log.e("TAG", "与IPCService断开连接--$name")
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.e("TAG", "与IPCService建立链接--$name")
            val data = Parcel.obtain()
            data.writeInt(200)
            data.writeInt(50)
            val replay = Parcel.obtain()
            service?.transact(1000, data, replay, 0)

            Log.e("TAG", "从远程服务获取到的数据 ${replay.readInt()}")

        }

    }
}