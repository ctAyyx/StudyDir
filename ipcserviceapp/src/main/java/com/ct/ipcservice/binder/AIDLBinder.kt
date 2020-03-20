package com.ct.ipcservice.binder

import android.os.Process
import android.os.RemoteCallbackList
import android.util.Log
import com.ct.ipcservice.IPCAidlInterface
import com.ct.ipcservice.RemoteCallbackAidlInterface
import com.ct.ipcservice.vo.Rect


class AIDLBinder : IPCAidlInterface.Stub() {
    override fun basicTypes(
        anInt: Int,
        aLong: Long,
        aBoolean: Boolean,
        aFloat: Float,
        aDouble: Double,
        aString: String?
    ) {
        Log.e("TAG", "基本原语 ---")
    }

    override fun getPid(): Int {
        return Process.myPid()
    }

    override fun sumNum(num1: Int, num2: Int): Int {
        return num1 + num2
    }

    /**
     * 在aidl文件中 注释太长了还不能编译
     *   in out inout表示数据流方向
     * in  表示数据由客户端传递 在服务端修改数据后 客户端的数据不受影响
     * out 表示数据由服务端生成 不管客户端的数据  修改后客户端数据跟着改变
     * inout 表示数据由客户端提供 服务端修改后 客户端数据跟着改变
     * */
    override fun getInRect(rect: Rect?) {
        Log.e("TAG", "getInRect获取传递过来的Rect:$rect")
        rect?.left = 20
        Log.e("TAG", "getInRect修改后的数据Rect:$rect")
    }


    override fun getOutRect(rect: Rect?) {
        Log.e("TAG", "getOutRect获取传递过来的Rect:$rect")
        rect?.left = 30
        Log.e("TAG", "getOutRect修改后的数据Rect:$rect")
    }


    override fun getInOutRect(rect: Rect?) {
        Log.e("TAG", "getInOutRect获取传递过来的Rect:$rect")
        rect?.left = 40
        Log.e("TAG", "getInOutRect修改后的数据Rect:$rect")
    }


    override fun getStringList(): MutableList<String> {
        return mutableListOf("A", "B", "C")
    }


    override fun getRectList(): MutableList<Rect> {
        return mutableListOf(Rect().apply { top = 10 }, Rect().apply { top = 20 })
    }

    /**
     * List传递自定义数据类型 必须要用 in inout修饰
     * */
    override fun getInList(list: MutableList<Rect>?): MutableList<Rect> {
        list?.let {
            it.forEach { rect: Rect ->
                rect.left = 40
            }

            return it
        }

        return mutableListOf()
    }

    override fun getInOutList(list: MutableList<Rect>?): MutableList<Rect> {

        list?.let {
            it.forEach { rect: Rect ->
                rect.left = 50
            }
            return it
        }

        return mutableListOf()
    }

    /**
     * AIDL传递Map数据 map不能使用泛型 需要用in out inout修饰
     * */
//    override fun getInOutMap(map: MutableMap<Any?, Any?>?) {
//        Log.e("TAG", "")
//    }


//    override fun getOutMap(map: MutableMap<Any?, Any?>?) {
//        Log.e("TAG", "")
//    }

//    override fun getInMap(map: MutableMap<Any?, Any?>?) {
//        Log.e("TAG", "")
//    }

    /**
     * 远程回调接口
     * */

    //这里需要远程回调接口 来绑定我们的远程回调
    private val remoteCallbackLIst: RemoteCallbackList<RemoteCallbackAidlInterface> =
        RemoteCallbackList()

    override fun onCallback(callback: RemoteCallbackAidlInterface?) {

        kotlin.run {
            remoteCallbackLIst.register(callback)
            Thread.sleep(5000)
            callback?.onCallBack("这是回调数据-- ${Thread.currentThread().name}")

            remoteCallbackLIst.unregister(callback)

        }

    }
}