package com.ct.study.method;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;

import androidx.annotation.Nullable;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FlingAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;

/**
 * @ClassName : TestScroller
 * @CreateDate : 2020/4/24 10:03
 * @Author : CT
 * @Description :
 */
public class TestScroller extends View {

    private Paint mPaint = new Paint();

    //柱状图的数量
    private int num = 200;
    //柱状图的宽度
    private int graphWidth = 20;
    //柱状图间的距离
    private int distance = 20;
    //最大滚动距离
    private int maxScroll;

    //初始化VelocityTracker
    private VelocityTracker velocityTracker;

    //滑动的最小 最大速度 px/s
    private int minVelocity, maxVelocity;

    public TestScroller(Context context) {
        this(context, null);
    }

    public TestScroller(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestScroller(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        mPaint.setColor(Color.RED);
        velocityTracker = VelocityTracker.obtain();
        initVelocity(getContext());
        setClickable(true);
        maxScroll = (num - 1) * distance + num * graphWidth;
        Log.e("TAG", "maxScroll:" + maxScroll);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制柱状图
        for (int i = 0; i < 200; i++) {
            Rect rect = getRect(i);
            canvas.drawRect(rect, mPaint);

        }
        //计算出滚动的最大距离
        int max = (200 - 1) * 40 + 20;

    }

    private Rect getRect(int position) {
        Rect rect = new Rect();
        rect.left = position * 40;
        rect.top = 0;
        rect.right = rect.left + 20;
        rect.bottom = rect.top + 200 + (position % 10 * 20);
        return rect;
    }

    private float downX, downY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();

                break;
            case MotionEvent.ACTION_MOVE:
                //获取的是手指相对于控件原点的坐标
                //可能为负数 如果手指移动到控件外面
                float relativeX = event.getX();
                float relativeY = event.getY();

                //获取的是手指相对于手机屏幕原点的坐标
                float rawX = event.getRawX();
                float rawY = event.getRawY();


                float moveX = Math.abs(downX - relativeX);

                // scrollTo((int) moveX, 0);
                break;
            case MotionEvent.ACTION_UP:
                downX = 0;
                downY = 0;

                break;
        }
        getFlingVelocity(event);


        return super.onTouchEvent(event);
    }


    /**
     * 初始化 最大滑动速度 和 最小滑动速度
     */
    private void initVelocity(Context context) {
        minVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
        maxVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
        Log.e("TAG", "最下速度:" + minVelocity + "--最大速度:" + maxVelocity);
    }

    /**
     * 获取手指滑动的速度
     */
    private void getFlingVelocity(MotionEvent event) {

        //添加要计算速度的事件
        velocityTracker.addMovement(event);
        //计算速度
        //units 速度的单位 ms 这里指定以秒为单位
        //maxVelocity 最大速度 计算出来的速度不会大于最大速度
        velocityTracker.computeCurrentVelocity(1000, maxVelocity);
        //获取X轴方向的速度
        float xVelocity = velocityTracker.getXVelocity();
        Log.e("TAG", minVelocity + "再X轴上手指的速度:" + xVelocity);
        if (event.getAction() == MotionEvent.ACTION_UP) {//只获取手指抬起后的速度

            if (Math.abs(xVelocity) > minVelocity)
                startFlingAnim(xVelocity);
            velocityTracker.clear();
        }

    }


    /**
     * 这里开始一个Fling动画来移动视图
     * <p>
     * 如果 速度是负值 表示 从右向左
     * 如果 速度是正值 表示 从左向右
     */
    private void startFlingAnim(float velocity) {
        Log.e("TAG", "开启Fling动画移动视图" + getScrollX());
        FlingAnimation flingAnimation = new FlingAnimation(this, DynamicAnimation.SCROLL_X);

//        if (velocity > 0)//表示 从左向右的滑动
//            flingAnimation.setMinValue(-velocity);
//        else
//            flingAnimation.setMinValue(0f);

        flingAnimation.setStartVelocity(-velocity)
                .setMinValue(-100000f)
                .setMaxValue(100000f)
                //.setStartValue(getScrollX())
                .setFriction((float) 0.75);//设置摩檫力


        flingAnimation.start();
    }

    /**
     * 开始一个弹簧动画
     */
    private void startSpringAnim() {

        SpringAnimation springAnimation = new SpringAnimation(this, DynamicAnimation.SCROLL_Y, 0);
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //回收
        velocityTracker.recycle();
    }
}
