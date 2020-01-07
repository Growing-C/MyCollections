package com.cgy.mycollections;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;

import appframe.utils.L;

import appframe.utils.DisplayHelperUtils;


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
        L.setDebug(true);

        //使用其他应用打开本地文件时 可能会抛出错误，因为 android 24之后限制了不通应用之间共享文件的uri 不能使用file://要用 content://
        //有两种解决方案
        //1.定义fileProvider
        //2.使用以下的StrictMode（更方便，application中增加以下几行代码即可）
        // this is a diagnostic tool, 这个似乎是诊断性的功能，不应该使用在app中，最好还是用 fileProvider
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.detectFileUriExposure();
        }
    }

    public static MyApplication getInstance() {
        return sInstance;
    }

    public void bringApp2Foreground() {

        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//        am.moveTaskToFront(rti.id, ActivityManager.MOVE_TASK_WITH_HOME);//方法将应用唤起到前台，


    }
}
