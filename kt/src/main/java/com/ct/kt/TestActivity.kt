package com.ct.kt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity() {

    private lateinit var builder: CoroutinesBuilder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        builder = CoroutinesBuilder()


        btn_ktTest_01.setOnClickListener {
            //在这里启动协程做耗时操作
            builder.doWork()
        }
        btn_ktTest_02.setOnClickListener {
            Toast.makeText(this, "主线程提示", Toast.LENGTH_LONG).show()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("TAG", "TestActivity Destroy")
    }


}
