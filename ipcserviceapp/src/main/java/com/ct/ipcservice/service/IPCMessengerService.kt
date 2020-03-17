package com.ct.ipcservice.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.util.Log
import com.ct.baseapp.util.AppUtil

/**
 * 使用 Messenger实现跨进程通讯
 * 1.服务实现一个 Handler，由该类为每个客户端调用接收回调。
 * 2.服务使用 Handler 来创建 Messenger 对象（对 Handler 的引用）。
 * */

class IPCMessengerService : Service() {
    private var mMessenger: Messenger? = null
    private var clientMessenger: Messenger? = null
    override fun onBind(intent: Intent?): IBinder? {
        Log.e(
            "TAG",
            "IPCMessengerService onBind---${AppUtil.getCurrentProcessName(this)} --${intent}"
        )
        //创建Messenger 并持有IncomingHandler
        mMessenger = Messenger(IncomingHandler(this))
        return mMessenger!!.binder

    }


    /**
     * 创建IncomingHandler来接收客户端回调
     * */
    inner class IncomingHandler(context: Context) : Handler() {

        override fun handleMessage(msg: Message) {
            Log.e("TAG", "IPCMessengerService收到的消息--$msg")
            //如果需要与客户端通讯 获取客户端传递过来的Messenger对象
            clientMessenger = msg.replyTo
            //向客户端发送消息
            clientMessenger?.send(Message.obtain(null, 200, 10, 10))
        }
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.e("TAG", "IPCMessengerService onUnbind")
        return super.onUnbind(intent)
    }
}