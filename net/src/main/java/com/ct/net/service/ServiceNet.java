package com.ct.net.service;



import com.ct.net.interceptor.CInterceptor;
import com.ct.net.interceptor.CacheInterceptor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ct on 2018/1/9.
 * <p>
 * 基于Retrofit2 封装的网络访问
 * <p>
 * 此封装基于常规数据访问,返回数据格式为{code:100,msg:"",data:实际数据}
 * 如果返回格式和基本格式不一致 可在BaseResponse中修改,
 * 如果还继承了BasePresenter,也要做相应修改
 */

public class ServiceNet {
    public static int TIME_OUT = 15000;
    private static ServiceNet serviceNet;

    private final Map<String, Retrofit> map = new HashMap<>();

    public static ServiceNet init() {
        if (serviceNet == null) {
            synchronized (ServiceNet.class) {
                if (serviceNet == null)
                    serviceNet = new ServiceNet();
            }
        }
        return serviceNet;
    }


    public Retrofit initService(String baseUrl) {
        return getService(baseUrl, initClient());
    }


    public Retrofit initCacheService(String baseUrl) {
        return getService(baseUrl, initCacheClient());
    }


    private Retrofit getService(String baseUrl, OkHttpClient client) {
        String key = baseUrl.hashCode() + "";
        if (map.get(key) == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    //.addConverterFactory(ScalarsConverterFactory.create())
                    .client(client)
                    .build();

            map.put(key, retrofit);
        }

        return map.get(key);
    }


    /**
     * 创建默认的客户端
     * <p>
     * addNetworkInterceptor添加的是网络拦截器，他会在在request和resposne是分别被调用一次，
     * 能够操作中间过程的响应,如重定向和重试
     * 而addinterceptor添加的是aplication拦截器，他只会在response被调用一次，
     * 且总是只调用一次，不需要担心中间过程的响应。
     */
    private OkHttpClient initClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .addInterceptor(new CInterceptor())
                .build()
                ;
    }


    private OkHttpClient initCacheClient() {

        return new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .addInterceptor(new CInterceptor())
                .addNetworkInterceptor(new CacheInterceptor())
                .build()
                ;
    }
}
