package com.ct.aac

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ct.aac.lifecycle.CustomLifecycleObservable
import com.ct.aac.lifecycle.CustomLifecycleOwner

class LifecycleActivity : AppCompatActivity() {

    //初始化自定义的LifeCycleOwner
    private lateinit var myOwner:CustomLifecycleOwner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifecycle)
        //加入自定义的生命周期感知类
        lifecycle.addObserver(CustomLifecycleObservable())

        myOwner = CustomLifecycleOwner()
        //加入自定义的生命周期感知类
        //myOwner.lifecycle.addObserver(CustomLifecycleObservable())
        myOwner.onCreate()
    }

    override fun onStop() {
        super.onStop()
        myOwner.onStop()
    }
}
