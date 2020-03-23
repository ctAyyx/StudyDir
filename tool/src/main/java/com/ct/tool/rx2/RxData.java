package com.ct.tool.rx2;

/**
 * Created by ct on 2017/7/31.
 * <p>
 * Rx 数据传递
 */
public class RxData<T> extends Object {

    public int code;

    public String msg;

    public T data;

    public RxData(int code) {
        this.code = code;
    }

    public RxData(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public RxData(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
