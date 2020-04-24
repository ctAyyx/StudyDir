package com.ct.animator

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Config
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ct.animator.databinding.FragmentHomeBinding
import com.ct.animator.ui.TransitionActivity01
import kotlinx.android.synthetic.main.fragment_home.*
import kotlin.math.hypot


/**
 * Android 动画·
 *
 * 1.属性动画
 *  ValueAnimator(默认每10ms刷新一次)
 *    TimeInterpolator //时间插值器 指定了如何根据时间计算动画中的特定值
 *    TypeEvaluator //类型求值器 负责告知属性动画系统如何计算指定属性的值
 *    duration //持续时间
 *    startPropertyValue //开始的属性值
 *    endPropertyValue //结束的属性值
 *    start 开始动画
 *  ValueAnimator.AnimatorUpdateListener //用来监听动画的改变
 *    onAnimationUpdate //属性动画每10ms刷新后的回调
 *        ValueAnimator.getAnimatorValue() 获取当前动画的完成值
 *  ObjectAnimator 是ValueAnimator的子类，
 *     要添加动画效果的对象属性必须具有 set<PropertyName>() 形式的 setter 函数（采用驼峰式大小写形式）
 *  AnimatorSet 编排多个动画
 *     1.先播放 bounceAnim。
 *     2.之后同时播放 squashAnim1、squashAnim2、stretchAnim1 和 stretchAnim2。
 *     3.之后播放 bounceBackAnim。
 *     4.之后播放 fadeAnim。
 *
 *
 *     AnimatorSet bouncer = new AnimatorSet();
 *      bouncer.play(bounceAnim).before(squashAnim1);
 *      bouncer.play(squashAnim1).with(squashAnim2);
 *      bouncer.play(squashAnim1).with(stretchAnim1);
 *      bouncer.play(squashAnim1).with(stretchAnim2);
 *      bouncer.play(bounceBackAnim).after(stretchAnim2);
 *      ValueAnimator fadeAnim = ObjectAnimator.ofFloat(newBall, "alpha", 1f, 0f);
 *      fadeAnim.setDuration(250);
 *      AnimatorSet animatorSet = new AnimatorSet();
 *      animatorSet.play(bouncer).before(fadeAnim);
 *      animatorSet.start();
 *
 *    before()方法:在play()方法中提供的动画结束之后执行的动画
 *    with()方法：在play()方法中提供的动画同时执行的动画
 *    after()方法:在play()方法中提供的动画会在after()提供的动画结束后执行
 *
 *  动画监听器
 *   Animator.AnimatorListener
 *         onAnimationStart() - 在动画开始播放时调用。
 *         onAnimationEnd() - 在动画结束播放时调用。
 *         onAnimationRepeat() - 在动画重复播放时调用。
 *         onAnimationCancel() - 在动画取消播放时调用。取消的动画也会调用 onAnimationEnd()，无论它们以何种方式结束。
 *         如果不想实现全部方法可以使用AnimatorListenerAdapter
 *
 *  ValueAnimator.AnimatorUpdateListener
 *
 *  针对ViewGroup的动画
 *
 *  ViewPropertyAnimator
 * */

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentHomeBinding.inflate(inflater, container, false).apply {


        btnAnimHomeValue.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_valueFragment)
        }

        btnAnimHomeActivity.setOnClickListener {
            requireActivity().startActivity(Intent(activity, TransitionActivity01::class.java))
        }
        btnAnimHomeLayout.setOnClickListener {
            it.visibility = View.GONE
        }
        btnAnimHomeCrosss.setOnClickListener {
            crossFade(imgCross, progressCross)

        }

        btnAnimHomeFlip.setOnClickListener {
            reversal(imgFlip02.alpha != 0f)
        }

        btnAnimHomeReveal.setOnClickListener {
            reveal(imgReveal.visibility != View.VISIBLE)
        }

        btnAnimHomeMove.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_moveFragment)
        }

    }.root


    /**
     * 实现视图的淡入淡出效果
     * @param contentView 要淡入的视图
     * @param loadingView 要淡出的视图
     * */
    private fun crossFade(contentView: View, loadingView: View) {
        Log.e("TAG", "'动画---->$contentView   $loadingView")
        val duration = 200L//android.R.integer.config_shortAnimTime.toLong() //这个值需要缓存起来

        contentView.alpha = 0f
        contentView.visibility = View.VISIBLE

        contentView.animate().alpha(1.0f).duration = duration


        loadingView.animate()
            .alpha(0f)
            .setDuration(duration)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    loadingView.visibility = View.GONE
                    Log.e("TAG", "动画结束")
                }
            })


    }


    /**
     * 卡片翻转效果
     *
     * 要创建卡片翻转动画，总共需要四个 Animator。
     * 两个 Animator 用于卡片正面向左侧淡出以及从左侧淡入的动画。
     * 两个 Animator，用于卡片背面从右侧淡入以及向右侧淡出的动画。
     * */
    private fun reversal(isReversal: Boolean) {

        val leftOut = AnimatorInflater.loadAnimator(requireContext(), R.animator.card_flip_left_out)
        val leftIn = AnimatorInflater.loadAnimator(requireContext(), R.animator.card_flip_left_in)
        val rightOut =
            AnimatorInflater.loadAnimator(requireContext(), R.animator.card_flip_right_out)
        val rightIn = AnimatorInflater.loadAnimator(requireContext(), R.animator.card_flip_right_in)

        if (isReversal) {
            leftIn.setTarget(img_flip01)
            rightOut.setTarget(img_flip02)
            AnimatorSet()
                .apply {
                    play(leftIn)
                        .with(rightOut)
                    start()
                }
        } else {
            leftOut.setTarget(img_flip01)
            rightIn.setTarget(img_flip02)
            AnimatorSet()
                .apply {
                    play(leftOut)
                        .with(rightIn)
                    start()
                }
        }


    }

    /**
     * 揭露动画
     * 在Android5.0(21) ViewAnimationUtils.createCircularReveal() 方法让您能够为裁剪圆形添加动画以揭露或隐藏视图
     *
     * */
    private fun reveal(isGone: Boolean) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val centerX = img_reveal.width / 2.0
            val centerY = img_reveal.height / 2.0

            //计算一个直角三角形的斜边
            val radius = hypot(centerX, centerY).toFloat()

            if (isGone)
            //显示控件
            {
                val anim = ViewAnimationUtils.createCircularReveal(
                    img_reveal,
                    centerX.toInt(),
                    centerY.toInt(),
                    0f,
                    radius
                )
                img_reveal.visibility = View.VISIBLE
                anim.start()
            } else
            //隐藏控件
            {
                val anim = ViewAnimationUtils.createCircularReveal(
                    img_reveal,
                    centerX.toInt(),
                    centerY.toInt(),
                    radius,
                    0f
                )
                img_reveal.visibility = View.GONE
                anim.start()
            }


        } else
            img_reveal.visibility = if (isGone) View.VISIBLE else View.GONE

    }
}