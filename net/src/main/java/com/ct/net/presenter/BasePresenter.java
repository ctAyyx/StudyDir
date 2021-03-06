package com.ct.net.presenter;


import android.content.Context;
import android.util.SparseArray;


import com.ct.net.view.Contract;
import com.ct.net.service.ServiceNet;
import com.ct.tool.rx.RxDeviceTool;
import com.ct.tool.rx.RxNetTool;
import com.ct.tool.rx2.RxSchedulers;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by ct on 2018/1/11.
 * <p>
 * 网络数据访问
 * <p>
 * 一般数据请求存在两种模式:
 * 1 获取缓存,在需要的时候请求网络(电商等)
 * 2 获取缓存和网络请求(对数据实时性要求高的)
 */

public abstract class BasePresenter implements Contract.Presenter {

    protected Contract.IView iView;


    protected SparseArray<Disposable> map;

    protected Context context;

    public BasePresenter(Context context) {
        if (map == null)
            map = new SparseArray<>();
        this.context = context.getApplicationContext();

    }


    @Override
    public void subscribe(Contract.IView iView) {
        this.iView = iView;

    }

    @Override
    public void dispose() {
        cancel();
    }

    public void dispose(int code) {
        cancel(code);
    }

    /**
     * 实现基本的数据响应,默认实现BaseResponse响应数据
     */

    protected <T> void onFilter(Observable<T> observable, final int code) {
        onFilter(observable, code, "");
    }

    protected <T> void onFilter(Observable<T> observable, final int code, String msg) {

        final Disposable d = observable.compose(RxSchedulers.<T>io_main())
                .subscribe(new Consumer<T>() {
                               @Override
                               public void accept(T response) throws Exception {
                                   onNext(response, code);
                               }
                           }, //onNext
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                if (iView != null) {
                                    iView.onException(code, throwable);
                                    iView.hideProgress(code);
                                }

                            }
                        },//onError

                        new Action() {
                            @Override
                            public void run() throws Exception {
                                cancel(code, false);
                            }
                        }//onComplete
                        ,
                        new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                if (iView != null) {
                                    iView.onBefore(code);
                                    iView.showProgress(code, msg);

                                }

                                map.put(code, disposable);

                                //在这里判断网络是否连接
                                if (!RxNetTool.isNetworkAvailable(context)) {
                                    if (iView != null)
                                        iView.onError(code, "当前网络不可用!", 100);
                                    cancel(code, false
                                    );
                                }

                            }
                        }//onSubscribe

                );

    }


    protected <T> void onNext(T response, int code) {
        if (iView == null)
            return;
        if (response == null)
            iView.onError(code, "BaseResponse is null", 101);
        else //TODO 可以在这里对数据进行预处理 默认为数据不为NULL 则成功
            iView.onSuccess(code, response);

        iView.hideProgress(code);
    }

    private void cancel() {

        for (int i = 0; i < map.size(); i++) {
            Disposable disposable = map.valueAt(i);
            if (disposable != null && !disposable.isDisposed())
                disposable.dispose();
        }

        map = null;

        if (iView != null)
            iView.onAfter(-1, true);

    }

    private void cancel(int code) {
        cancel(code, true);
    }

    private void cancel(int code, boolean isCancelByUser) {
        Disposable disposable = map.get(code);

        if (disposable != null && !disposable.isDisposed())
            disposable.dispose();

        if (iView != null) {
            iView.hideProgress(code);
            iView.onAfter(code, isCancelByUser);
        }

    }

}
