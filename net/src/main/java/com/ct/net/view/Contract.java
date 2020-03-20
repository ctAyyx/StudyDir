package com.ct.net.view;

/**
 * Created by ct on 2018/1/11.
 * <p>
 * 包含数据请求 回调 loading
 */

public interface Contract {

    interface IView {


        /**
         * 显示进度控件
         *
         * @param code 数据请求码
         */
        void showProgress(int code, String msg);

        /**
         * 隐藏进度控件
         *
         * @param code 数据请求码
         */
        void hideProgress(int code);

        /**
         * 数据请求成功
         *
         * @param code     数据请求码
         * @param response 返回数据
         */
        <T> void onSuccess(int code, T response);

        /**
         * 数据请求失败
         *
         * @param code 数据请求码
         * @param msg  错误信息
         */
        void onError(int code, String msg);


        /**
         * 数据请求异常
         *
         * @param code      数据请求码
         * @param throwable 异常
         */
        void onException(int code, Throwable throwable);

        /**
         * 数据请求前
         *
         * @param code 请求码
         */
        void onBefore(int code);


        /**
         * 数据请求完成或取消
         *
         * @param code           数据请求码
         * @param isCancelByUser 是否由用户停止的网络访问
         */

        void onAfter(int code, boolean isCancelByUser);


        /**
         * 无网络连接
         *  @param code           数据请求码
         * */
        void onNoNet(int code);

    }

    interface Presenter {
        void dispose();

        void subscribe();
    }
}
