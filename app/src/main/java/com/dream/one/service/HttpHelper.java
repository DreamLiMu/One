package com.dream.one.service;


import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.net.CookieManager;
import java.net.CookiePolicy;

/**
 * Created by CNKI-0000 on 2015/11/26.
 */
public class HttpHelper {

    private static HttpHelper mInstance;
    private OkHttpClient client;

    private static final String TAG = "HttpHelper";

    private HttpHelper() {
        this.client = new OkHttpClient();
        client.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
    }

    // 单例模式
    public static HttpHelper getInstance() {
        if (mInstance == null) {
            synchronized (HttpHelper.class) {
                if (mInstance == null) {
                    mInstance = new HttpHelper();
                }
            }
        }
        return mInstance;
    }

    // 同步Get请求
    private Response _get(String url) throws Exception {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        Response response = call.execute();
        return response;
    }

    // 异步Get请求
    private void _getAsync(String url, Callback callback) {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public void getAsync(String url, Callback callback) {
        _getAsync(url, callback);
    }

}
