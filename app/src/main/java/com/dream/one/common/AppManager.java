package com.dream.one.common;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

/**
 * Created by muli on 15/11/28.
 */
public class AppManager {
    public static Activity contex = null;

    Stack<Activity> activityStack;
    public static AppManager manager;

    private AppManager(){
        activityStack = new Stack<Activity>();
    }

    // 单例模式
    public static AppManager getManager(){
        if(manager == null){
            synchronized (AppManager.class){
                if(manager == null){
                    manager = new AppManager();
                }
            }
        }
        return manager;
    }

    // 添加Activity到栈
    public void addActivity(Activity activity){
        activityStack.add(activity);
    }

    // 获取当前Activity
    public Activity currentActivity(){
        Activity activity = activityStack.lastElement();
        return activity;
    }

    // 结束当前Activity
    public void finishiActivity(){
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    // 结束指定Activity
    public void finishActivity(Activity activity){
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    // 结束指定类名的Activity
    public void finishiActivity(Class<?> clazz){
        for (Activity activity : activityStack){
            if(activity.getClass().equals(clazz)){
                finishActivity(activity);
            }
        }
    }

    // 清空栈
    public void finishiAllActivity(){
        for(int i = 0; i < activityStack.size(); i++){
            if(null != activityStack.get(i)){
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    // 退出应用程序
    public void appExit(Context context){
        try{
            finishiAllActivity();
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.restartPackage(context.getPackageName());
            System.exit(0);

        }catch (Exception e){}
    }

}
