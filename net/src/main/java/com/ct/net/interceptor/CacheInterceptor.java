package com.ct.net.interceptor;

import android.util.Log;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/*
 * 实现数据缓存
 * */
public class CacheInterceptor implements Interceptor {


    private int maxAge;//该值表示有网络的时候，希望在指定时间内不去重新获取网络数据，而是直接使用缓存数据；
    private int maxStale;//表示在无网络的情况下，在指定时间内使用缓存数据


    private CacheControl CAHCE;

    public CacheInterceptor() {
        this(10, 10);
    }

    public CacheInterceptor(int maxAge, int maxStale) {
        this.maxAge = maxAge;
        this.maxStale = maxStale;

        CAHCE = new CacheControl.Builder().onlyIfCached()
                .maxStale(maxStale, TimeUnit.SECONDS)
                .build();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        if (true) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_NETWORK)
                    .build();
            Log.d("NET", "获取网络数据");
        } else {
            request = request.newBuilder()
                    .cacheControl(CAHCE)
                    .build();
            Log.d("NET", "获取缓存数据");
        }


        Response response = chain.proceed(request);

        if (true)
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();


        return response.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Cache-Control")
                .header("Cache-Control", "public,only-if-cached,max-stale=" + maxStale)
                .build();
    }
}
