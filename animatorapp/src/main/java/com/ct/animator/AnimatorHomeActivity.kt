package com.ct.animator

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity


/**
 * 动画 测试界面
 * */
class AnimatorHomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animator_home)
    }


    fun onClick(view: View) {

        when (view.id) {
            R.id.btn_animHome_value -> {
            }
        }
    }

}