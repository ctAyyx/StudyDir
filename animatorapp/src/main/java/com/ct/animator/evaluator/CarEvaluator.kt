package com.ct.animator.evaluator

import android.animation.TypeEvaluator
import android.util.Log
import com.ct.animator.vo.Car

class CarEvaluator : TypeEvaluator<Car> {
    override fun evaluate(fraction: Float, startValue: Car?, endValue: Car?): Car {
        val start = startValue?.speed ?: 0
        val end = endValue!!.speed

        val car = Car(((end - start)*fraction).toInt())
        Log.e("TAG", "当前分值:$fraction -- $startValue -- $endValue")
        return car;
    }

}