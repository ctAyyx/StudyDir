package com.ct.ipc.connection

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import com.ct.ipcservice.IPCAidlInterface
import com.ct.ipcservice.vo.Rect

class AIDLServiceConnection : ServiceConnection {

    private var ipcAidlInterface: IPCAidlInterface? = null

    override fun onServiceDisconnected(name: ComponentName?) {
        Log.e("TAG", "与远程AIDLService断开链接")
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        Log.e("TAG", "与远程AIDLService建立链接")
        //向服务端发送数据
        //1.获取IPCAidlInterface
        ipcAidlInterface = IPCAidlInterface.Stub.asInterface(service)

    }


    fun sum() {
        val sum = ipcAidlInterface?.sumNum(100, 200)
        Log.e("TAG", "求和:$sum")
    }

    fun obj() {
        val inRect = Rect()
        inRect.left = 10
        ipcAidlInterface?.getInRect(inRect)
        Log.e("TAG", "客户端inRect:$inRect")


        val outRect = Rect()
        outRect.left = 10
        ipcAidlInterface?.getOutRect(outRect)
        Log.e("TAG", "客户端outRect:$outRect")

        val inOutRect = Rect()
        inOutRect.left = 10
        ipcAidlInterface?.getInOutRect(inOutRect)
        Log.e("TAG", "客户端inOutRect:$inOutRect")

    }

    fun list() {
        //获取StringList
        Log.e("TAG", "客户端获取StringList(类型ArrayList)--${ipcAidlInterface?.stringList}")
        Log.e("TAG", "客户端获取RectList(类型ArrayList)--${ipcAidlInterface?.rectList}")

        val inRectList = mutableListOf(Rect().apply { left = 10 })
        val resultIn = ipcAidlInterface?.getInList(inRectList)
        Log.e("TAG", "in --传递的RectList:$inRectList -- 接收到的:$resultIn")

        val inoutRectList = mutableListOf(Rect().apply { left = 10 })
        val resultInOut = ipcAidlInterface?.getInOutList(inoutRectList)
        Log.e("TAG", "inout--传递的RectList:$inoutRectList -- 接收到的:$resultInOut")
    }

}