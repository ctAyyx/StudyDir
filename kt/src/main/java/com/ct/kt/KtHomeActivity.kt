package com.ct.kt

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

/**
 *
 * Kotlin 测试类
 * */
class KtHomeActivity : AppCompatActivity() {

    private lateinit var builder: CoroutinesBuilder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_kt)
        builder = CoroutinesBuilder()

    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.tv_ktHome_Global -> {
                //针对Global协程的生命周期范围
                builder.globalMain()
            }

            R.id.tv_ktHome_runBlocking -> {
                //针对runBlocking协程影响范围
                builder.runBlockMain()
            }
            R.id.tv_ktHome_test -> {
                startActivity(Intent(this, TestActivity::class.java))
            }
        }
    }

}