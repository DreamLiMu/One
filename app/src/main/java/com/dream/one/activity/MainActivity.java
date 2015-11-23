package com.dream.one.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import com.dream.one.R;

public class MainActivity extends Activity {

    Context context;
    final String TAG = "One App";
    SharedPreferences preferences = null;
    // 闪光字体View
    ShimmerTextView shimmerTextView;
    Shimmer shimmer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置no title, 一定要在setContentView之前设置
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        // 初始化相关view
        initView();
        shimmer = new Shimmer();
        shimmer.start(shimmerTextView);

        preferences = context.getSharedPreferences("one",MODE_PRIVATE);

        initMethod();
    }

    // Handler 处理延时操作，延时3s进入主界面
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // 设置默认值1，第一次运行必为1，再次运行时就不为1了
            if(preferences.getInt("one",1) == 1){
                // 修改为0，后跳转
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("one",0);
                editor.commit();
                // 第一次运行
                startActivity(new Intent(context, WelcomeActivity.class));
            }
            else {
                startActivity(new Intent(context, OneActivity.class));
            }

            finish();
        }
    };

    private void initMethod(){
        handler.sendEmptyMessageDelayed(0,1000);
    }

    public void initView(){
        context = MainActivity.this;
        shimmerTextView = (ShimmerTextView) findViewById(R.id.shimmer_tv);
    }

    // 获取屏幕宽和高的方法
    public void getHeightAndWidth(){
        //第一种获取屏幕宽高的方式
        WindowManager windowManager = getWindowManager();
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        int mScreenWidth1 = metrics.widthPixels;
        int mScreenHeight1 = metrics.heightPixels;
        //第二种获取屏幕宽高的方式
        int mScreenWidth2 = windowManager.getDefaultDisplay().getWidth();
        int mScreenHeight2 = windowManager.getDefaultDisplay().getHeight();
    }

    // dip转px
    public static int dip2px(Context context,float dpValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5f);
    }
}
