package com.cgy.mycollections;

import android.app.Application;

/**
 * Created by RB-cgy on 2015/11/9.
 */
public class MyApplication extends Application {

    private static MyApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static MyApplication getInstance() {
        return sInstance;
    }
}
