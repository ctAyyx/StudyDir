package com.ct.study.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import com.ct.study.R
import kotlinx.android.synthetic.main.activity_method.*

/**
 * 针对 View 和 ViewGroup中
 * 绘制流程的展示
 * 事件分发的展示 https://blog.csdn.net/yuncaidaishu/article/details/100039363
 *
 * 针对事件分发
 *  从Activity-->ViewGroup-->View
 *  调用的事件
 *  Activity:dispatchTouchEvent,onTouchEvent
 *  ViewGroup:dispatchTouchEvent, onInterceptTouchEvent ,onTouchEvent
 *  View : dispatchTouchEvent, onTouchEvent
 *
 *  分发流程
 *  Activity的dispatchTouchEvent中，通过 getWindow().superDispatchTouchEvent(ev)来调用DecorView的dispatchTouchEvent来分发事件
 *        调用ViewGroup的dispatchTouchEvent
 *           onInterceptTouchEvent
 *        默认不拦截事件,调用View的dispatchTouchEvent
 *        如果设置了OnTouchListener的话,会调用OnTouch方法
 *        如果onTouch方法返回true,事件被消费,则dispatchTouchEvent返回true
 *        否则 调用onTouchEvent方法,返回true 事件被消费,dispatchTouchEvent返回true.否则dispatchTouchEvent返回false
 *        继续由该View的ViewGroup处理事件
 *    如果View的 dispatchTouchEvent返回false,表示事件未被消费，则调用onTouchEvent
 *    如果onTouchEvent方法,返回true 事件被消费,dispatchTouchEvent返回true.否则dispatchTouchEvent返回false
 *    继续由该ViewGroup的上一层ViewGroup处理，直到事件被消费或返回Activity的onTouchEvent
 * 如果ViewGroup的   dispatchTouchEvent返回false,则会调用Activity的onTouchEvent
 *
 * ps:如果在Activity分发Down事件就不能消费的话后面的事件都会在调用getWindow().superDispatchTouchEvent(ev)时返回false
 *    直接交由Activity的onTouchEvent处理
 * */
class MethodActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_method)

        //tv_method.setOnClickListener { }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Log.e("Activity", "dispatchTouchEvent${ev?.action}")
        val result = super.dispatchTouchEvent(ev)
        Log.e("Activity", "dispatchTouchEvent${ev?.action}--${result}")
        return result
    }
}
