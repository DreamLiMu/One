package com.dream.one.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dream.one.R;
import com.dream.one.service.HttpHelper;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.nostra13.universalimageloader.utils.L;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.security.Timestamp;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class ScrollFragment extends Fragment {

    private ObservableScrollView mScrollView;
    private static final String APP_ID = "12973";
    private static final String secret = "c85e466b303746beb6c5ba74ebd7cfa2";
    TextView tv;
    HttpHelper helper;
    Button btn_change;

    public static ScrollFragment newInstance() {
        return new ScrollFragment();
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                String res = msg.getData().getString("res");
                tv.setText(new Date().getTime() + "");
                tv.setText(res);
                btn_change.setText("完成");
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_scroll, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mScrollView = (ObservableScrollView) view.findViewById(R.id.scrollView);
        tv = (TextView) view.findViewById(R.id.http);
        btn_change = (Button) view.findViewById(R.id.btn_change);
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_change.setText("正在加载数据...");
                helper = HttpHelper.getInstance();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String url = "http://route.showapi.com/582-1?showapi_appid="
                                + APP_ID
                                + "&showapi_timestamp=" + new Date().getTime()
                                + "&id=&showapi_sign=" + secret;
                        final Callback callback = new Callback() {
                            @Override
                            public void onFailure(Request request, IOException e) {

                            }

                            @Override
                            public void onResponse(Response response) throws IOException {
                                if (response.isSuccessful()) {
                                    String res = response.body().string();
                                    Message msg = new Message();
                                    msg.what = 1;
                                    Bundle bundle = new Bundle();
                                    bundle.putString("res", res);
                                    mHandler.sendMessage(msg);
                                }
                            }
                        };
                        helper.getAsync(url, callback);
                    }
                }).start();

            }
        });

        MaterialViewPagerHelper.registerScrollView(getActivity(), mScrollView, null);
    }
}
