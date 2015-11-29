package com.dream.one.common;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by muli on 15/11/28.
 */
public class AppManager {

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

    public void addActivity(Activity activity){
        activityStack.add(activity);
    }

    public void removeActivity(Activity activity){
        activityStack.remove(activity);
        activity = null;
    }

    // 清空栈
    public void removeAll(){
        for (Activity activity : activityStack){
            activityStack.remove(activity);
            activity = null;
        }
    }

}
