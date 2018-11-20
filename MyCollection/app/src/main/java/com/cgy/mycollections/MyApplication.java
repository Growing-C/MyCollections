package com.cgy.mycollections;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.cgy.mycollections.utils.DisplayHelperUtils;

/**
 * Created by RB-cgy on 2015/11/9.
 */
public class MyApplication extends Application {

    private static MyApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;


        DisplayHelperUtils.init(this);
    }

    public static MyApplication getInstance() {
        return sInstance;
    }

    public void bringApp2Foreground(){

        ActivityManager am = (ActivityManager)  getSystemService(Context.ACTIVITY_SERVICE);
//        am.moveTaskToFront(rti.id, ActivityManager.MOVE_TASK_WITH_HOME);//方法将应用唤起到前台，


    }
}
