package com.ct.animator.ui

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.transition.ChangeImageTransform
import android.transition.Explode
import android.transition.Fade
import android.transition.Slide
import android.view.Gravity
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair

import com.ct.animator.R
import kotlin.reflect.KClass

/**
 * @ClassName : BaseActivity
 * @CreateDate : 2020/4/23 16:41
 * @Author : CT
 * @Description : 封装 Activity过渡动画
 *  最好是在Style中定义
 *
 */
open class BaseActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        with(window) {
            requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)

            sharedElementExitTransition = ChangeImageTransform()
            sharedElementEnterTransition = ChangeImageTransform()
            sharedElementReenterTransition=ChangeImageTransform()
            sharedElementReturnTransition=ChangeImageTransform()
        }

    }

    /**
     * 初始化Android5.0(21)转场动画
     * 爆炸效果
     *
     * 需要再setContentView之前调用
     * */
    private fun initExplode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            with(window) {
                requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
                exitTransition = Explode()
                enterTransition = Explode()
                reenterTransition = Explode()
                returnTransition = Explode()
                sharedElementExitTransition = ChangeImageTransform()
                sharedElementEnterTransition = ChangeImageTransform()
            }
        }
    }

    /**
     * 初始化Android5.0(21)转场动画
     * 渐隐效果
     *
     * 需要再setContentView之前调用
     * */
    private fun initFade() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            with(window) {
                requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
                exitTransition = Fade(Fade.MODE_OUT)
                enterTransition = Fade(Fade.MODE_IN)
                reenterTransition = Fade()
                returnTransition = Fade()
            }
        }
    }


    /**
     * 初始化Android5.0(21)转场动画
     * 滑动效果
     *
     * 需要再setContentView之前调用
     *
     * @param slideEdge 方向
     * */
    private fun initSlide(@androidx.transition.Slide.GravityFlag slideEdge: Int = Gravity.START) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            with(window) {
                requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
                exitTransition = Slide(Gravity.START)
                enterTransition = Slide(Gravity.END)
                reenterTransition = Slide(Gravity.START)
                returnTransition = Slide(Gravity.END)
            }
        }
    }

    private fun getReverseSlideEdge(slideEdge: Int) =

        when (slideEdge) {
            Gravity.START, Gravity.LEFT -> Gravity.END
            Gravity.END, Gravity.RIGHT -> Gravity.START
            Gravity.TOP -> Gravity.BOTTOM
            Gravity.BOTTOM -> Gravity.TOP
            else -> slideEdge

        }


    /**
     * 启动一个 爆炸式的过渡动画
     * */
    fun readyGoBy(
        cls: KClass<*>,
        vararg sharedElements: Pair<View, String>,
        bundle: Bundle? = null
    ) {
        val intent = Intent(this, cls.java)
        if (bundle != null)
            intent.putExtras(bundle)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            startActivity(
                intent,
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this,
                    *sharedElements
                ).toBundle()
            )
        else {
            startActivity(intent)
            overridePendingTransition(R.anim.slide_right_enter, R.anim.slide_left_exit)
        }

    }

    fun createPair(view: View, name: String) = Pair.create(view, name)

    fun close() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            finishAfterTransition()
        else {
            finish()
            overridePendingTransition(R.anim.slide_left_enter, R.anim.slide_right_exit)
        }
    }
}