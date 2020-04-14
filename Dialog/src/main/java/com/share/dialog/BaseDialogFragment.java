package com.share.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

/**
 * @ClassName : BaseDialogFragment
 * @CreateDate : 2020/4/13 17:28
 * @Author : CT
 * @Description : 针对 DialogFragment的使用 封装
 */
public class BaseDialogFragment extends DialogFragment {

    /**
     * 两种方式显示Dialog
     * 一.onCreateDialog
     * 返回我们创建的Dialog
     * 二.如同使用Fragment一样,然后再onStart方法中获取Dialog 并设置属性 如果需要的话
     * 如果要设置主题 需要在onCreate方法中调用setStylee
     */
//    @NonNull
//    @Override
//    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//        return new LoadingDialog(requireContext());
//    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.LoadingDialog);
    }

    /**
     * 如同Fragment一样 创建我们的布局
     * 会在onActivityCreated方法中将我们的布局通过Dialog的setContentView方法设置给Dialog
     */

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_loading, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        init(Objects.requireNonNull(getDialog()));

    }


    /**
     * 初始化
     */
    private void init(Dialog dialog) {

        Window window = dialog.getWindow();

        if (window == null)
            return;
        //设置背景亮度
        //window.setDimAmount(0);
        //获取DecorView 其实是一个FrameLayout
        View decorView = window.getDecorView();
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
        window.setWindowAnimations(R.style.DialogAnim);
    }

    /**
     * 初始化自定义布局控件
     */
    protected void initView(@NonNull FrameLayout rootView) {
    }
}
