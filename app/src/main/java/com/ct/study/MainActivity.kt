package com.ct.study

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.widget.ActionBarContainer
import androidx.appcompat.widget.ActionBarOverlayLayout
import androidx.appcompat.widget.ContentFrameLayout
import androidx.appcompat.widget.FitWindowsLinearLayout
import androidx.core.view.marginTop
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 1.关于Android中动画的使用 在AnimatorApp中 封装在AnimatorModule中
 *
 * */
class MainActivity : AppCompatActivity() {

    private var contentFrameLayout: ContentFrameLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_app_layout.setOnClickListener {
            layout()
        }

        btn_app_change.setOnClickListener {
            changeLayout()
        }

        if (supportActionBar == null)
            setSupportActionBar(btn_bar)
//        if (Build.VERSION.SDK_INT >= 21) {
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.statusBarColor = Color.RED
//        }

    }

    private fun layout() {


//                Log.e("TAG", "当前布局层次:${f2.getChildAt(0)}")
//                Log.e("TAG", "当前布局层次:${(f2.getChildAt(0) as ActionBarOverlayLayout).getChildAt(0)}")
//                Log.e("TAG", "当前布局层次:${(f2.getChildAt(0) as ActionBarOverlayLayout).getChildAt(1)}")
//                val f3 =
//                    (f2.getChildAt(0) as ActionBarOverlayLayout).getChildAt(0) as ContentFrameLayout
//                Log.e("TAG", "当前布局层次3:${f3.getChildAt(0)}")

        //布局分析
        //1.DecorView 是FrameLayout
        val decorViewF = window.decorView as FrameLayout
        Log.e("TAG", "DecorView包含的子布局数量${decorViewF.childCount}")
        //DecorView包含2个布局
        repeat(decorViewF.childCount) {
            Log.e(
                "TAG",
                "'DecorView的子布局:${decorViewF.getChildAt(it)} --- 高度${decorViewF.getChildAt(it).height}"
            )
        }
        //2.DecorView包含一个LinearLayout布局和View
        //在Android5.0(21)以后,DecorView包含一个LinearLayout布局和View,View表示状态栏
        //在Android5.0(21)以前,DecorView只包含一个LinearLayout布局,状态栏的高度是该LinearLayout设置的padding

        val linear01 = decorViewF.getChildAt(0) as LinearLayout
        //该View是状态栏部分 在Android5.0以后
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            val view01 = decorViewF.getChildAt(1)
//            view01.setBackgroundColor(Color.RED)
//        } else {
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//            linear01.setBackgroundColor(Color.RED)
//            //  linear01.setPadding(0, 0, 0, 0)
//            Log.e("TAG", "----${linear01.paddingTop}")
//        }


        //3.该LinearLayout 包含一个ViewSub(没有高度)和 FrameLayout
        Log.e("TAG", "LinearLayout包含的布局数量:${linear01.childCount}")
        repeat(linear01.childCount) {
            Log.e(
                "TAG",
                "LinearLayout的子布局:${linear01.getChildAt(it)} --- 高度${linear01.getChildAt(it).height} -- ${linear01.paddingTop} -- }"
            )
        }
//        val viewSub = linear01.getChildAt(0) as ViewStub
//        viewSub.inflate()
//              Log.e("TAG", "ViewSub的高度:${viewSub.height}")
        val frame02 = linear01.getChildAt(1) as FrameLayout

        //4.FrameLayout只包含一个ActionBarOverlayLayout
        //如果取消了系统的ActionBar则只包含一个FitWindowsLinearLayout
        Log.e("TAG", "FrameLayout包含的布局数量:${frame02.childCount}")
        repeat(frame02.childCount) {
            Log.e(
                "TAG",
                "FrameLayout的子布局:${frame02.getChildAt(it)} --- 高度${frame02.getChildAt(it).height}"
            )
        }

        val aboLayout = if (frame02.getChildAt(0) is ActionBarOverlayLayout) {
            //没有取消系统自带的ActionBar
            frame02.getChildAt(0) as ActionBarOverlayLayout
        } else {
            //取消了系统自带的ActionBar
            frame02.getChildAt(0) as FitWindowsLinearLayout
        }
        //5.没取消系统ActionBar,ActionBarOverlayLayout包含ContentFrameLayout 和 ActionBarContainer(这是ActionBar)
        //如果取消系统的ActionBar,FitWindowsLinearLayout包含 ViewStubCompat(高度0） 和 ContentFrameLayout
        Log.e("TAG", "ActionBarOverlayLayout包含的布局数量:${aboLayout.childCount}")
        repeat(aboLayout.childCount) {
            Log.e(
                "TAG",
                "ActionBarOverlayLayout的子布局:${aboLayout.getChildAt(it)} --- 高度${aboLayout.getChildAt(
                    it
                ).height}"
            )
        }
        if (aboLayout.getChildAt(0) is ContentFrameLayout)
            contentFrameLayout = aboLayout.getChildAt(0) as ContentFrameLayout
        else
            contentFrameLayout = aboLayout.getChildAt(1) as ContentFrameLayout
        if (aboLayout.getChildAt(1) is ActionBarContainer) {
            val actionBarContainer = aboLayout.getChildAt(1) as ActionBarContainer
            actionBarContainer.setBackgroundColor(Color.YELLOW)
        } else {
            //取消了系统ActionBar
            val viewStubCompat = aboLayout.getChildAt(0)
        }


        contentFrameLayout?.setBackgroundColor(Color.GREEN)

        //6.ContentFrameLayout包含的是我们的XML布局
        Log.e("TAG", "ContentFrameLayout包含的布局数量:${contentFrameLayout!!.childCount}")
        repeat(contentFrameLayout!!.childCount) {
            Log.e(
                "TAG",
                "ContentFrameLayout的子布局:${contentFrameLayout!!.getChildAt(it)} --- 高度${contentFrameLayout!!.getChildAt(
                    it
                ).height}"
            )
        }

        myLayout = contentFrameLayout!!.getChildAt(0)


//        if (nullView == null) {
//            nullView =
//                LayoutInflater.from(this).inflate(R.layout.layout_null, null, false)
//            nullView!!.setOnClickListener {
//                changeLayout()
//            }
//            nullView!!.visibility = View.GONE
//            contentFrameLayout?.addView(nullView)
//        }

    }

    private var nullView: View? = null
    private var myLayout: View? = null
    private var isNull = true
    private fun changeLayout() {


        if (isNull) {
            myLayout?.visibility = View.GONE
            nullView?.visibility = View.VISIBLE
            isNull = !isNull
        } else {
            myLayout?.visibility = View.VISIBLE
            nullView?.visibility = View.GONE
            isNull = !isNull
        }
    }


    private fun setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //大于21
        }
    }


    //1.关于状态栏
    //android:windowTranslucentStatus 透明状态栏
    // window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS) 移除透明状态栏
    //在5.0以前 如果需要设置状态栏颜色,首先要使状态栏透明化,整体布局会上移到手机顶部,设置fitSystemWindow='true'可以防止布局上移
    //在5.0以后 默认是设置了状态栏的颜色,如果透明化了状态栏 则需要先移除透明状态栏标记再调用 window.statusBarColor

    //2.关于DecorView
    //在Android5.0(21)以后,DecorView包含一个LinearLayout布局和View,View表示状态栏如果透明化状态啦则只包含一个LinearLayout
    //在Android5.0(21)以前,DecorView只包含一个LinearLayout布局,状态栏的高度是该LinearLayout设置的padding
    //LinearLayout布局包含一个ViewSub(没有高度)和 FrameLayout
    //FrameLayout布局只包含一个ActionBarOverlayLayout,但是在取消系统ActionBar的情况下则只包含一个FitWindowsLinearLayout
    //-----ActionBarOverlayLayout包含ContentFrameLayout 和 ActionBarContainer
    //     ContentFrameLayout包含的是我们的XML布局,ActionBarContainer包含的是ActionBar的布局
    //-----FitWindowsLinearLayout包含 ViewStubCompat(高度0） 和 ContentFrameLayout
    //      ContentFrameLayout包含的是我们的XML布局
    //                                                                 DecorView
    //                                                        (5.0以前)|       |(5.0以后包括5.0)
    //


}
