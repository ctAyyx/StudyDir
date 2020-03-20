package com.ct.tool.rx;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by ct on 2018/1/10.
 * <p>
 * Rx2
 */

public class RxBus {

    private static RxBus rxBus;

    private final Subject<Object> subject;
    private Map<String, CompositeDisposable> map;


    public RxBus() {
        subject = PublishSubject.create().toSerialized();
    }

    public static RxBus getInstance() {
        if (rxBus == null) {
            synchronized (RxBus.class) {
                if (rxBus == null)
                    rxBus = new RxBus();
            }
        }

        return rxBus;
    }


    /**
     * 创建一个背压
     */
    private <T> Flowable<T> getObservable(Class<T> type) {
        return subject.toFlowable(BackpressureStrategy.BUFFER)
                .ofType(type);
    }

    /**
     * 创建一个订阅方式
     */
    public <T> Disposable doSubscribe(Class<T> type, Consumer<T> next, Consumer<? super Throwable> error) {
        return getObservable(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(next, error);
    }

    public void post(Object obj) {
        subject.onNext(obj);
    }


    /**
     * 将订阅添加到集合中
     */
    public void addSubscription(Object obj, Disposable disposable) {

        if (map == null)
            map = new HashMap<>();

        String key = obj.getClass().getSimpleName();

        if (map.get(key) != null)
            //向CompositeDisposable添加新的Disposable
            map.get(key).add(disposable);
        else {
            CompositeDisposable disposables = new CompositeDisposable();
            disposables.add(disposable);
            map.put(key, disposables);
        }

    }


    /**
     * 移除订阅事件
     */

    public void unSubscription(Object obj) {
        if (map == null)
            return;
        String key = obj.getClass().getSimpleName();
        if (map.get(key) != null)
            map.get(key).dispose();

        map.remove(key);
    }

}
