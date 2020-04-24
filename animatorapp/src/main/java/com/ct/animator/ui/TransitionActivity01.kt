package com.ct.animator.ui

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.ct.animator.R
import kotlinx.android.synthetic.main.activity_transition01.*

/***
 *
 * 在 5.0以前 一般用overridePendingTransition来实现转场动画 在startActivity 和 Finish后面
 *
 * 在5.0以后 用ActivityOptionsCompat来实现转场动画
 * */
class TransitionActivity01 : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transition01)

        btn_transition01_common.setOnClickListener {
            //commonTransitionStyle()
            //commonTransition()
            readyGoBy(TransitionActivity02::class)
        }

        btn_transition01_21.setOnClickListener {
            transition21()
        }

        btn_transition01_share.setOnClickListener {

            // transition21Share()
            // transition21Share02()
            readyGoBy(TransitionActivity02::class,createPair(img_transition01_share,"image_share"))
        }

    }


    private fun commonTransitionStyle() {
        val intent = Intent(this, TransitionActivity02::class.java)
        startActivity(intent)
    }

    /**
     * 通用的转场动画
     * */
    private fun commonTransition() {
        val intent = Intent(this, TransitionActivity02::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_bottom_enter, R.anim.slide_top_exit)
    }


    /**
     * 在Android5.0以后使用转场动画
     * */
    private fun transition21() {
        val intent = Intent(this, TransitionActivity02::class.java)
        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle())

    }

    /**
     * 共享元素动画
     * */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun transition21Share() {
        val intent = Intent(this, TransitionActivity02::class.java)
        //为两个布局中的共享元素指定一个通用名称
        img_transition01_share.transitionName = "image_share"
        startActivity(
            intent,
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                img_transition01_share,
                "image_share"
            ).toBundle()
        )
    }

    private fun transition21Share02() {
        val intent = Intent(this, TransitionActivity02::class.java)
        //为两个布局中的共享元素指定一个通用名称

        img_transition01_share.transitionName = "image_share"

        //在Kotlin中 泛型不能将ImageView转成View
        startActivity(
            intent,
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                Pair.create(
                    findViewById(R.id.img_transition01_share),
                    "image_share"
                ),
                Pair.create(
                    findViewById(R.id.tv_transition01_share),
                    "text_share"
                )
            ).toBundle()
        )
    }
}
