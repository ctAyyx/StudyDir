package com.share.dialog;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

/**
 * @ClassName : LoadingDialog
 * @CreateDate : 2020/4/13 11:56
 * @Author : CT
 * @Description :
 */
public class LoadingDialog extends BaseDialog {
    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.LoadingDialog);
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_loading;
    }


    private void log(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childView = viewGroup.getChildAt(i);
            if (childView instanceof ViewGroup) {
                childView.setBackgroundColor(Color.RED);
                log((ViewGroup) childView);
            }
        }
    }

    @Override
    protected void initView(FrameLayout rootView) {
        super.initView(rootView);
        setCanceledOnTouchOutside(false);
        TextView tv = findViewById(R.id.tv_loading_msg);
        tv.setText("呵呵");
    }
}
