package com.ct.animator.ui

import android.animation.ObjectAnimator
import android.graphics.Path
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.PathInterpolator
import android.widget.ImageView
import androidx.core.view.animation.PathInterpolatorCompat
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.FlingAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.fragment.app.Fragment
import com.ct.animator.databinding.FragmentMoveBinding

/**
 * @ClassName : MoveFragment
 * @CreateDate : 2020/4/23 14:40
 * @Author : CT
 * @Description : 使用动画移动视图
 *
 */
class MoveFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentMoveBinding.inflate(inflater, container, false).apply { subscribeUi(this) }.root


    private fun subscribeUi(binding: FragmentMoveBinding) {
        with(binding) {
            imgMove.setOnClickListener {
                //moveImage(imgMove)
                //moveByPath(imgMove)
                // fling(imgMove)

                spring(imgMove)
            }

        }

    }


    /**
     * 移动一张图片
     * 这里使用ObjectAnimator

     * */
    /**
     *
     *  *添加曲线动作
     * 虽然使用 ObjectAnimator 很方便，但默认情况下它会使用起点和终点之间的直线重新定位视图
     * 使用 PathInterpolator Android 5.0 (API 21)
     * */
    private fun moveImage(view: ImageView) {
        val anim = ObjectAnimator.ofFloat(view, "translationX", 100f)
            .apply {
                duration = 2000

            }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //这里 使用PathInterpolator

            val pathInterpolator = PathInterpolatorCompat.create(0.4f, 0f)

            anim.interpolator = pathInterpolator
        }
        anim.start()


    }

    private fun moveByPath(view: ImageView) {

        val animator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val path = Path().apply {
                arcTo(0f, 0f, 720f, 1000f, 225f, -180f, true)
            }
            ObjectAnimator.ofFloat(view, View.X, View.Y, path)
        } else {
            ObjectAnimator.ofFloat(view, View.Y, 1000f)
        }
        animator.duration = 3000
        animator.start()

    }


    /************基于物理特性的动画*************/

    private fun fling(view: ImageView) {


        //实现受摩檫力影响的又初始速度的动画
        //默认是向Y轴正向移动
        //如果想向Y轴负向移动,则可以设置速度为负值,最小值也要是负值
        FlingAnimation(view, DynamicAnimation.TRANSLATION_Y)
            .setStartVelocity(-2000f)//设置开始的速度 px/s
            .setMinValue(-10000f) //设置最小值
            .setMaxValue(1000f) //设置最大值
            .setFriction(1.1f) //设置摩擦力
            .start()
    }


    private fun spring(view: ImageView) {
        SpringAnimation(view, DynamicAnimation.TRANSLATION_Y, 300f)
            .apply {
                setStartVelocity(10000f)
                spring
                    .setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY) //设置阻尼值
                    .stiffness = SpringForce.STIFFNESS_HIGH //设置弹簧的刚度


                start()
            }


    }


}