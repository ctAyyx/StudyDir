package com.ct.dagger

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class DaggerHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dagger_home)
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_dagger_simple -> {
            }
        }
    }


    private fun daggerSimple() {
        //获取生成的Component对象
    }
}