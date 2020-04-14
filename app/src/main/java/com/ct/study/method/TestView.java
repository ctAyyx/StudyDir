package com.ct.study.method;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

/**
 * @ClassName : TestView
 * @CreateDate : 2020/4/14 9:40
 * @Author : CT
 * @Description : 观察绘制方法的调用
 */
public class TestView extends View {
    public TestView(Context context) {
        super(context);
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);
        Log.e("View", "onDrawForeground:");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.RED);
        Log.e("View", "onDraw:");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e("View", "onSizeChanged:" + w + "==" + h + "旧的值:" + oldw + "==" + oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        Log.e("View", "onMeasure:" + width + "==" + height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.e("View", "onLayout:" + changed + "值==" + left + "==" + top + "==" + right + "==" + bottom);
    }


    /**************************以下是针对事件分发机制**********************************/

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e("View", "dispatchTouchEvent -- start");
        boolean result = super.dispatchTouchEvent(event);
        Log.e("View", "dispatchTouchEvent -- end" + result);
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("View", "onTouchEvent -- start");
        boolean result = super.onTouchEvent(event);
        Log.e("View", "onTouchEvent -- end" + result);

        return result;
    }

}
