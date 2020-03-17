package com.ct.animator.handler

import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.Button
import com.ct.animator.value.ValueAnimBuilder

class AnimatorHandler {

    fun startSimpleValueAnim(view: View) {
        ValueAnimBuilder.buildIntValueAnimator()
            .apply {
                addUpdateListener {
                    if (view is Button)
                        view.text = "当前值:${it.animatedValue} -- 当前分值:${it.animatedFraction}"
                }
            }
            .start()
    }

    fun startCarValueAnim(view: View) {
        ValueAnimBuilder.buildCarValueAnimator().apply {
            interpolator = AnticipateOvershootInterpolator()
            addUpdateListener {
                Log.e("TAG", "${it.animatedFraction} --- ${it.animatedValue}")
            }
        }.start()
    }
}