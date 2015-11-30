package com.dream.one.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.dream.one.R;
import com.dream.one.common.AppLog;
import com.dream.one.common.AppManager;
import com.github.clans.fab.FloatingActionButton;

public abstract class BaseActivity extends AppCompatActivity
        implements View.OnClickListener {

    private static final int ACTIVITY_RESUME = 0;
    private static final int ACTIVITY_STOP = 1;
    private static final int ACTIVITY_PAUSE = 2;
    private static final int ACTIVITY_DESTROY = 3;

    public int activityState;
    //是否允许全屏
    private boolean mAllowFullScreen = true;

    public abstract void initViews();

    public abstract void viewsClick(View view);

    public void setAllowFullScreen(boolean allowFullScreen) {
        this.mAllowFullScreen = allowFullScreen;
    }

    @Override
    public void onClick(View view) {
        viewsClick(view);
    }

    /*****************************************
     * Activity 生命周期
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppLog.debug(this.getClass() + "--------onCreate");
        // 竖屏锁定
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (mAllowFullScreen) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        AppManager.getManager().addActivity(this);

        //initViews();
        super.onCreate(savedInstanceState);

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
        AppManager.getManager().removeActivity(this);
    }
}
