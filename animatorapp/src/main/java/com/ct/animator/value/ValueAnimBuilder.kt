package com.ct.animator.value

import android.animation.ValueAnimator
import android.util.Log
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnticipateOvershootInterpolator
import com.ct.animator.evaluator.CarEvaluator
import com.ct.animator.vo.Car

/**
 * 属性动画
 *
 * ValueAnimator构建器
 * https://developer.android.google.cn/guide/topics/graphics/prop-animation
 *
 * 系统提供的Interceptor插值器 用于计算每一帧对应的动画完成度
 *
 *AccelerateDecelerateInterpolator	该插值器的变化率在开始和结束时缓慢但在中间会加快。
 *AccelerateInterpolator	该插值器的变化率在开始时较为缓慢，然后会加快。
 *AnticipateInterpolator	该插值器先反向变化，然后再急速正向变化。
 *AnticipateOvershootInterpolator	该插值器先反向变化，再急速正向变化，然后超过定位值，最后返回到最终值。
 *BounceInterpolator	该插值器的变化会跳过结尾处。
 *CycleInterpolator	该插值器的动画会在指定数量的周期内重复。
 *DecelerateInterpolator	该插值器的变化率开始很快，然后减速。
 *LinearInterpolator	该插值器的变化率恒定不变。
 *OvershootInterpolator	该插值器会急速正向变化，再超出最终值，然后返回。
 *TimeInterpolator	该接口用于实现您自己的插值器。
 *
 *系统提供的TypeEvaluator类型评估器 用户计算每一帧对应的指定类型实际值
 *
 *IntEvaluator	这是用于计算 int 属性的值的默认评估程序。
 *FloatEvaluator	这是用于计算 float 属性的值的默认评估程序。
 *ArgbEvaluator	这是用于计算颜色属性的值（用十六进制值表示）的默认评估程序。
 *TypeEvaluator	此接口用于创建您自己的评估程序。
 *如果您要添加动画效果的对象属性不是 int、float 或颜色，那么您必须实现 TypeEvaluator 接口，才能指定如何计算对象属性添加动画效果之后的值。
 *如果您想以不同于默认行为的方式处理 int、float和颜色，您还可以为这些类型的值指定自定义 TypeEvaluator。如需详细了解如何编写自定义评估程序，请参阅使用 TypeEvaluator 部分。
 * */
object ValueAnimBuilder {

    /**
     * 获取一个简单的ValueAnimator
     * */
    fun buildIntValueAnimator() = ValueAnimator.ofInt(0, 100).apply {
        duration = 20 * 1000
        interpolator = AnticipateOvershootInterpolator()

    }

    fun buildCarValueAnimator() = ValueAnimator.ofObject(CarEvaluator(), Car(0), Car(100)).apply {
        duration =10*1000

    }


}