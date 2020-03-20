package com.ct.ipc

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ct.ipc.connection.AIDLServiceConnection
import com.ct.ipc.socket.SocketBuilder
import kotlinx.android.synthetic.main.activity_ipc_home.view.*

/**
 * 实现跨进程通讯
 *
 * 1.Binder
 * */
class IPCHomeActivity : AppCompatActivity() {

    private lateinit var aidlServiceConnection: AIDLServiceConnection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ipc_home)
        aidlServiceConnection = AIDLServiceConnection()
    }

    fun onClick(view: View) {

        when (view.id) {
            R.id.btn_ipcHome_binder -> {
                val intent = Intent()
                intent.action = "com.ct.ipc_service"
                //在Android 5.0以上 不能隐式绑定服务
                //需要明确服务的包名
                intent.`package` = "com.ct.ipcservice"
                bindService(intent, IPCServiceConnection(), Context.BIND_AUTO_CREATE)

            }

            R.id.btn_ipcHome_aidl -> {
                //无法绑定
                val intent = Intent()
                intent.component = ComponentName(this, "com.ct.ipcservice.service.AIDLService")
                bindService(intent, aidlServiceConnection, Context.BIND_AUTO_CREATE)
            }

            R.id.btn_ipcHome_aidl2 -> {
                val intent = Intent()
                intent.action = "com.ct.ipc_aidl_service"
                //在Android 5.0以上 不能隐式绑定服务
                //需要明确服务的包名
                intent.`package` = "com.ct.ipcservice"
                bindService(intent, aidlServiceConnection, Context.BIND_AUTO_CREATE)
            }

            R.id.btn_ipcHome_sum -> {
                aidlServiceConnection.sum()
            }
            R.id.btn_ipcHome_obj -> {
                aidlServiceConnection.obj()
            }
            R.id.btn_ipcHome_list -> {
                aidlServiceConnection.list()
            }
            R.id.btn_ipcHome_map -> {
            }
            R.id.btn_ipcHome_remote -> {
                aidlServiceConnection.remoteCallback()
            }
            R.id.btn_ipcHome_socket -> {
                SocketBuilder().createClientSocket()
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
