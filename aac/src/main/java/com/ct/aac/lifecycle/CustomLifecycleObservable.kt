package com.ct.aac.lifecycle

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * 组件生命周期感知类Lifecycle
 *
 * 你可以实现LifecycleObserver接口 来实现对组件生命周期的感知
 *
 * */
class CustomLifecycleObservable : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        Log.e("TAG", "生命周期感知:onCreate--")
    }

    /**
     * ON_START事件  在onStart和onRestart都会调用
     * */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        Log.e("TAG", "生命周期感知:onStart")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        Log.e("TAG", "生命周期感知:onResume")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        Log.e("TAG", "生命周期感知:onPause")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        Log.e("TAG", "生命周期感知:onStop")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        Log.e("TAG", "生命周期感知:onDestroy")
    }
}