package com.share.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @ClassName : BaseDialog
 * @CreateDate : 2020/4/13 11:41
 * @Author : CT
 * @Description : 一个基于 Dialog的基础Dialog类
 */
public abstract class BaseDialog extends Dialog {

    public abstract @LayoutRes
    int getLayoutId();

    public BaseDialog(@NonNull Context context) {
        super(context);
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected BaseDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);

    }


    /**
     * onCreate是在调用的show()方法之后才会调用
     * 所以 如果要动态设置我们布局的中控件的值 要先调用show方法 再设置值
     * 或者 你可以将 init方法在构造函数中调用 就可以直接赋值了
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
    }

    /**
     * 初始化
     */
    private void init() {
        setContentView(getLayoutId());
        Window window = getWindow();
        if (window == null)
            return;
        //获取DecorView 其实是一个FrameLayout
        View decorView = window.getDecorView();
        decorView.setBackgroundColor(Color.TRANSPARENT);
        //这里可以设置Padding 当布局为 MATCH_PARENT 时 控制 左上右下中content到边距
        decorView.setPadding(16, 16, 16, 16);
        //获取直接包裹 我们布局的View
        FrameLayout content = decorView.findViewById(android.R.id.content);
        initSize(window, content);
        initGravity(window);
        initAnim(window);
        initPadding(decorView);
        initView(content);
    }


    /**
     * 设置 布局的位置
     * 这里默认居中 宽 MATCH_PARENT高WRAP_CONTENT
     * <p>
     * 层次为
     * Window
     * ---|--DecorView
     * --------|----LinearLayout
     * ---------------|-FrameLayout
     * -------------------|--我们的布局
     */
    protected void initSize(@NonNull Window window, @NonNull FrameLayout rootView) {
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;

        window.setAttributes(params);

        //FrameLayout的宽高和Window设置的一致
        //将FrameLayout中 我们的布局居中
        //主要是针对 如果设置Window为MATCH_PARENT 而我们的布局用的是 WRAP_CONTENT
        //整体布局默认会处于左上的位置
        //也可以直接在布局文件上
        // android:layout_gravity="center"
        View myView = rootView.getChildAt(0);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) myView.getLayoutParams();
        layoutParams.gravity = Gravity.CENTER;
        myView.setLayoutParams(layoutParams);
    }

    /**
     * 设置 DecorView的Padding
     */
    protected void initPadding(View decorView) {
        decorView.setPadding(0, 0, 0, 0);
    }

    /**
     * 设置 Window的位置
     */
    protected void initGravity(@NonNull Window window) {
        window.setGravity(Gravity.CENTER);
    }

    /**
     * 设置 Window的入场出场动画
     * 以为Window的高度为 WRAP_CONTENT
     * 如果要使用上下平移动画,如果Dialog居中 切不是全屏的话 平移的距离值有我们布局高度
     * 看起来会很奇怪
     * 所以 使用上下平移动画的Dialog 应该位于 TOP|BOTTOM
     */
    protected void initAnim(@NonNull Window window) {
        //这里 使用默认动画
        //window.setWindowAnimations(R.style.DialogAnim);
    }

    /**
     * 初始化自定义布局控件
     */
    protected void initView(@NonNull FrameLayout rootView) {
    }

}
