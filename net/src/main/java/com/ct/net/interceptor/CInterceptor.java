package com.ct.net.interceptor;


import android.util.Log;

import androidx.annotation.NonNull;


import org.jetbrains.annotations.NotNull;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by ct on 2017/4/5.
 * <p>
 * OkHttp网络请求拦截器
 * <p>
 * 主要实现网络请求日志打印
 */

public class CInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();//获取请求

        String url = request.url().toString();//获取请求URL

        String method = request.method();
        long t1 = System.nanoTime();
        RequestBody requestBody = request.body();
        StringBuilder sb = new StringBuilder("Request Body [");
        if (requestBody != null) {

            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            Charset charset = StandardCharsets.UTF_8;
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(charset);
            }
            if (isPlaintext(buffer)) {
                sb.append(buffer.readString(charset));
                if (contentType != null)
                    sb.append(" (Content-Type = ").append(contentType.toString()).append(",")
                            .append(requestBody.contentLength()).append("-byte body)");
            } else {
                if (contentType != null)
                    sb.append(" (Content-Type = ").append(contentType.toString())
                            .append(",binary ").append(requestBody.contentLength()).append("-byte body omitted)");
            }


        }
        sb.append("]");
        Response response = chain.proceed(request);
        long t2 = System.nanoTime();


        //the response data
        ResponseBody body = response.body();
        BufferedSource source = body.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();
        Charset charset = Charset.defaultCharset();
        MediaType contentType = body.contentType();
        if (contentType != null) {
            charset = contentType.charset(charset);
        }
        String bodyString = buffer.clone().readString(charset);
        String str = new String(bodyString.getBytes(StandardCharsets.UTF_8));

        Log.d("NET:",
                "\r\n请求地址：" + url + "   请求方法：" + method + "   响应耗时：" + (t2 - t1) / 1e6d
                        + "请求状态：" + (response.isSuccessful() ? "success " : "fail ") + response.message() + "   请求状态码：" + response.code()
                        + "\r\n请求实体:" + sb
                        + "\r\n响应实体：" + str
        );


        return response;
    }

    private boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

}
