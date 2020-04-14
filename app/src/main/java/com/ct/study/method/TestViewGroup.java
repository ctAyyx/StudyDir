package com.ct.study.method;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

/**
 * @ClassName : TestViewGroup
 * @CreateDate : 2020/4/14 10:33
 * @Author : CT
 * @Description :
 */
public class TestViewGroup extends LinearLayout {
    public TestViewGroup(Context context) {
        super(context);
    }

    public TestViewGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    /**
     * ViewGroup的绘制流程
     * <p>
     * ViewGroup的onMeasure调用,
     * 并通知子控件调用自己的onMeasure
     * <p>
     * ViewGroup的onSizeChanged
     * ViewGroup的onLayout调用
     * 并调用子控件的 onSizeChanged
     * 并调用子控件的 onLayout
     * 布局位置绘制完成,然后开始绘制视图
     * ViewGroup的
     * draw
     * onDraw
     * dispatchDraw被调用
     * --drawChild被调用方法，并调用
     * ----子控件的onDraw
     * ----子控件的onDrawForeground
     * onDrawForeground
     * <p>
     * 如果ViewGroup没有设置背景的话
     * 只会执行下面的绘制流程
     * dispatchDraw被调用
     * --drawChild被调用方法，并调用
     * ----子控件的onDraw
     * ----子控件的onDrawForeground
     */

    @Override
    public void draw(Canvas canvas) {
        Log.e("TAG", "draw  start");
        super.draw(canvas);
        Log.e("TAG", "draw  end ");
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        Log.e("ViewGroup", "dispatchDraw start");
        super.dispatchDraw(canvas);
        Log.e("ViewGroup", "dispatchDraw end");
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        Log.e("ViewGroup", "drawChild -- start");
        boolean result = super.drawChild(canvas, child, drawingTime);
        Log.e("ViewGroup", "drawChild --end");
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.e("ViewGroup", "onDraw -- start");
        super.onDraw(canvas);
        Log.e("ViewGroup", "onDraw -- end");
        canvas.drawColor(Color.YELLOW);
    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        Log.e("ViewGroup", "onDrawForeground -- start");
        super.onDrawForeground(canvas);
        Log.e("ViewGroup", "onDrawForeground -- end");
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        Log.e("ViewGroup", "onMeasure--start:" + width + "==" + height);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        Log.e("ViewGroup", "onMeasure --end:" + width + "==" + height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.e("ViewGroup", "onSizeChanged -- start:" + w + "==" + h + "旧的值:" + oldw + "==" + oldh);
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e("ViewGroup", "onSizeChanged -- end:" + w + "==" + h + "旧的值:" + oldw + "==" + oldh);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.e("ViewGroup", "onLayout -- start:" + changed + "值==" + left + "==" + top + "==" + right + "==" + bottom);
        super.onLayout(changed, left, top, right, bottom);
        Log.e("ViewGroup", "onLayout -- end:" + changed + "值==" + left + "==" + top + "==" + right + "==" + bottom);
    }


    /***************************以下是针对 事件分发机制*********************************/

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("ViewGroup", "dispatchTouchEvent -- start" + getAction(ev));
        boolean result = super.dispatchTouchEvent(ev);
        Log.e("ViewGroup", "dispatchTouchEvent -- end" + result);
        return result;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("ViewGroup", "onInterceptTouchEvent -- start");
        boolean result = super.onInterceptTouchEvent(ev);
        Log.e("ViewGroup", "onInterceptTouchEvent -- end" + result);
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("ViewGroup", "onTouchEvent -- start");
        boolean result = super.onTouchEvent(event);
        Log.e("ViewGroup", "onTouchEvent -- end" + result);
        return result;
    }


    private String getAction(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return "ACTION_DOWN";
            case MotionEvent.ACTION_UP:
                return "ACTION_UP";
            case MotionEvent.ACTION_MOVE:
                return "ACTION_MOVE";
            case MotionEvent.ACTION_CANCEL:
                return "ACTION_CANCEL";
            default:
                return "事件：" + ev.getAction();
        }
    }
}
