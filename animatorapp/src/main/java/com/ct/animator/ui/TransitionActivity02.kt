package com.ct.animator.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Explode
import android.transition.Slide

import com.ct.animator.R
import kotlinx.android.synthetic.main.activity_transition02.*

class TransitionActivity02 : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transition02)

        img_transition02_share.transitionName = "image_share"
        btn_transition02_21.setOnClickListener {
            //在5.0以后 要使用finishAfterTransition来表示完成转场动画之后finish掉界面
           close()
        }
//        if (Build.VERSION.SDK_INT >= 21) {
//
//
//            window.enterTransition = Explode()
//            window.exitTransition = Explode()
//        }


    }


//    override fun finish() {
//        super.finish()
//        overridePendingTransition(R.anim.slide_top_enter, R.anim.slide_bottom_exit)
//    }
}
