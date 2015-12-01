package com.dream.one.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dream.one.R;
import com.dream.one.common.AppLog;
import com.dream.one.common.AppManager;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class BaseActivity extends AppCompatActivity {

    private static final int ACTIVITY_RESUME = 0;
    private static final int ACTIVITY_STOP = 1;
    private static final int ACTIVITY_PAUSE = 2;
    private static final int ACTIVITY_DESTROY = 3;

    public int activityState;
    //是否允许全屏
    private boolean mAllowFullScreen = true;
    private FrameLayout rootView;
    private LinearLayout parentView;
    private FloatingActionMenu fam;
    private FloatingActionButton save_Fab;

    public void setAllowFullScreen(boolean allowFullScreen) {
        this.mAllowFullScreen = allowFullScreen;
    }

    /*****************************************
     * Activity 生命周期
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 竖屏锁定,必须在最前面
        if (mAllowFullScreen) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        super.onCreate(savedInstanceState);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initContentView(R.layout.activity_base);

        initViews();
        initListener();
        AppManager.getManager().addActivity(this);

    }

    // 初始化其他的view
    public void initViews(){
        save_Fab = (FloatingActionButton) findViewById(R.id.fab_save);
    }

    public void initListener(){
        save_Fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),"点击了Save按钮。。。",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initContentView(int resId) {
        ViewGroup viewGroup = (ViewGroup) findViewById(android.R.id.content);
        viewGroup.removeAllViews();
        rootView = (FrameLayout) getLayoutInflater().from(this).inflate(resId,null);
        viewGroup.addView(rootView);
        parentView = (LinearLayout) findViewById(R.id.content_ly);
    }

    @Override
    public void setContentView(int layoutResID) {
        LayoutInflater.from(this).inflate(layoutResID, parentView, true);
    }

    @Override
    public void setContentView(View view) {
        parentView.addView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        parentView.addView(view, params);
    }

    @Override
    protected void onStart() {
        super.onStart();
        AppLog.state(this.getClass(), "------------onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityState = ACTIVITY_RESUME;
        AppLog.state(this.getClass(), "------------onResue");
    }

    @Override
    protected void onStop() {
        super.onStop();
        activityState = ACTIVITY_STOP;
        AppLog.state(this.getClass(), "-------------onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        activityState = ACTIVITY_PAUSE;
        AppLog.state(this.getClass(), "--------------onPuase");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        AppLog.state(this.getClass(), "----------onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityState = ACTIVITY_DESTROY;
        AppLog.state(this.getClass(), "----------------onDestroy");
        AppManager.getManager().finishActivity(this);
    }
}
