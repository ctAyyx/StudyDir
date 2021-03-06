package com.ct.tool.rx;

import android.content.Context;

public class RxDimens {

    /**
     * dip转px
     *
     * @param dpValue dp值
     * @return px值
     */
    public static int dip2px(float dpValue) {
        return dp2px(dpValue);
    }

    public static int dip2px(Context mContext, float dpValue) {
        return dp2px(mContext, dpValue);
    }

    /**
     * dp转px
     *
     * @param dpValue dp值
     * @return px值
     */
    public static int dp2px(float dpValue) {
        return dp2px(RxTool.getContext(), dpValue);
    }


    public static int dp2px(Context mContext, float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转dip
     *
     * @param pxValue px值
     * @return dip值
     */
    public static int px2dip(float pxValue) {
        return px2dp(pxValue);
    }

    /**
     * px转dp
     *
     * @param pxValue px值
     * @return dp值
     */
    public static int px2dp(float pxValue) {
        return px2dp(RxTool.getContext(), pxValue);
    }

    public static int px2dp(Context mContext, float pxValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp转px
     *
     * @param spValue sp值
     * @return px值
     */
    public static int sp2px(float spValue) {
        final float fontScale = RxTool.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px转sp
     *
     * @param pxValue px值
     * @return sp值
     */
    public static int px2sp(float pxValue) {
        final float fontScale = RxTool.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }
}
