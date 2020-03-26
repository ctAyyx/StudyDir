package com.ct.aac.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry

/**
 * 自定义LifecycleOwner
 *
 * */
class CustomLifecycleOwner : LifecycleOwner {
    //创建 绑定类 用于管理多个生命周期感知组件
    private var lifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)

    fun onCreate() {


        //设置当前状态为CREATED
        //lifecycleRegistry.currentState = Lifecycle.State.CREATED
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    }


    fun onStop() {
        lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
    }

    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }

}