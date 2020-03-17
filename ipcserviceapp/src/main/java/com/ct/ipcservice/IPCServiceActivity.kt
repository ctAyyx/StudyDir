package com.ct.ipcservice

import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.os.Messenger
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ct.baseapp.util.AppUtil
import com.ct.ipcservice.connection.IPCServiceConnection
import com.ct.ipcservice.connection.MessengerConnection
import com.ct.ipcservice.service.IPCMessengerService
import com.ct.ipcservice.service.IPCBinderService

/**
 * 传递的数据必须实现Parcelable接口
 * */
class IPCServiceActivity : AppCompatActivity() {

    private lateinit var serviceConnection: ServiceConnection
    private lateinit var messengerConnection: MessengerConnection


    //1.创建Handler对象
    private val clientMessengerHandler = ClientMessengerHandler()
    private lateinit var clientMessenger: Messenger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ipc_service)
        Log.e("TAG", "IPCServiceActivity当前进程名:${AppUtil.getCurrentProcessName(this)}")
        serviceConnection = IPCServiceConnection()
        messengerConnection = MessengerConnection()
        clientMessenger = Messenger(clientMessengerHandler)

    }

    fun onClick(view: View) {
        when (view.id) {
            //使用Binder进行通讯
            R.id.btn_ipcService_binder -> {
                val intent = Intent(IPCServiceActivity@ this, IPCBinderService::class.java)
                val result = bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
                Log.e("TAG", "绑定服务是否成功$result'")
            }

            R.id.btn_ipcService_unbinder -> unbindService(serviceConnection)

            //实现使用Messenger跨进程通讯
            //实现Service端和Client端的相互通讯
            //1.客户端创建Handler用于接收Service端发送的数据
            //2.客户端创建Messenger对象并持有Handler
            //3.将Messenger对象发送给服务端
            R.id.btn_ipcService_messenger -> {
                bindService(
                    Intent(this, IPCMessengerService::class.java),
                    messengerConnection,
                    Context.BIND_AUTO_CREATE
                )
            }
            R.id.btn_ipcService_messenger_sendMsg -> {
                //发送消息
                messengerConnection.mMessenger?.send(

                    Message.obtain(
                        null,
                        100,
                        0, 0
                    ).apply {
                        replyTo = clientMessenger
                    }
                )
            }
            R.id.btn_ipcService_unMessenger -> {
                unbindService(messengerConnection)
            }
        }
    }


    inner class ClientMessengerHandler : Handler() {
        override fun handleMessage(msg: Message) {
            Log.e("TAG", "获取当Service端发送的消息:---->${msg}")
            super.handleMessage(msg)
        }
    }
}