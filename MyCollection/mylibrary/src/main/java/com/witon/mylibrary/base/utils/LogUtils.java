package com.witon.mylibrary.base.utils;

import android.util.Log;


import com.witon.mylibrary.BuildConfig;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by RB-cgy on 2016/3/15.
 */
public class LogUtils {
    private static final String TAG = "myApp";

    private static String logTime() {
        DateFormat dateFormat = new SimpleDateFormat("[dd-MM-yyyy HH:mm:ss]: ");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static void v(String msg) {
        if (BuildConfig.DEBUG)
            Log.v(TAG, logTime() + msg);
    }

    public static void d(String msg) {
        if (BuildConfig.DEBUG)
            Log.d(TAG, logTime() + msg);
    }

    public static void i(String msg) {
        if (BuildConfig.DEBUG)
            Log.i(TAG, logTime() + msg);
    }

    public static void w(String msg) {
        if (BuildConfig.DEBUG)
            Log.w(TAG, logTime() + msg);
    }

    public static void e(String msg) {
        if (BuildConfig.DEBUG)
            Log.e(TAG, logTime() + msg);
    }


    //methods below use self-defined tag
    public static void v(String tag, String msg) {
        if (BuildConfig.DEBUG)
            Log.v(tag, logTime() + msg);
    }

    public static void i(String tag, String msg) {
        if (BuildConfig.DEBUG)
            Log.i(tag, logTime() + msg);
    }

    public static void d(String tag, String msg) {
        if (BuildConfig.DEBUG)
            Log.d(tag, logTime() + msg);
    }

    public static void w(String tag, String msg) {
        if (BuildConfig.DEBUG)
            Log.w(tag, logTime() + msg);
    }

    public static void e(String tag, String msg) {
        if (BuildConfig.DEBUG)
            Log.e(tag, logTime() + msg);
    }
}
