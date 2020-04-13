package com.ct.kt

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_test.*
import kotlinx.coroutines.*

/**
 *  针对 CoroutineScope的使用
 * */
class TestActivity : AppCompatActivity(),
    CoroutineScope by CoroutineScope(Dispatchers.Main+ SupervisorJob()) {

    private lateinit var builder: CoroutinesBuilder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        builder = CoroutinesBuilder()


        Log.e("TAG", "'${coroutineContext[Job]}")


        btn_ktTest_01.setOnClickListener {
            //在这里启动协程做耗时操作
            doWork()
        }
        btn_ktTest_02.setOnClickListener {
            Toast.makeText(this, "主线程提示", Toast.LENGTH_LONG).show()
        }


    }


    /**
     * 在这里做耗时操作
     * */
    private fun doWork() {

        launch {
            Log.e("TAG", "开始获取数据....'")
            delay(20 * 1000)
            Log.e("TAG", "数据获取完成....'")
            btn_ktTest_01.text = "获取数据完成"
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.e("TAG", "TestActivity Destroy")
        //coroutineContext.cancel()
    }


}
