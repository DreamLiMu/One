package com.dream.one.common;

import android.util.Log;

/**
 * Created by muli on 15/11/28.
 */
public class AppLog {

    public static final String TAG = "AppLog";

    public static void debug(String msg){
        Log.d(TAG, msg);
    }

    public static void error(String msg){
        Log.e(TAG, msg);
    }

    public static void state(Class clazz, String msg){
        Log.i(clazz.getName(), msg);
    }


}
