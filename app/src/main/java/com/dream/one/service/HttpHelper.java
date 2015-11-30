package com.dream.one.service;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import com.dream.one.model.ArticleItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CNKI-0000 on 2015/11/26.
 */
public class HttpHelper {

    private static HttpHelper mInstance;
    private OkHttpClient client;
    private Handler mHandler;
    private static final String TAG = "HttpHelper";

    private HttpHelper() {
        this.client = new OkHttpClient();
        client.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
        mHandler = new Handler(Looper.getMainLooper());
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

    public Response get(String url) throws Exception{
        return _get(url);
    }

    // 异步Get请求
    private void _getAsync(String url, Callback callback) {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public List<ArticleItem> getArticleItem(String url) throws Exception{
        Response response = _get(url);

        String result = response.body().string();
        List<ArticleItem> items = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(result);
        JSONArray jsonArray = jsonObject.getJSONObject("showapi_res_body")
                .getJSONArray("contentlist");
        Gson gson = new Gson();
        Type type = new TypeToken<List<ArticleItem>>(){}.getType();
        items = gson.fromJson(jsonArray.toString(), type);
        return items;
    }

    public void getAsync(String url, Callback callback) {
        _getAsync(url, callback);
    }

    // 加载图片
    public void displayImage(String url, final ImageView imageView){

        final Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                InputStream is = null;
                try{
                    is = response.body().byteStream();
                    final Bitmap bitmap = BitmapFactory.decodeStream(is);
                    is.close();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            // 主UI线程
                            imageView.setImageBitmap(bitmap);
                        }
                    });

                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }



}
