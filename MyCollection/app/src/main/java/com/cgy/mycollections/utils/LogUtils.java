package com.cgy.mycollections.utils;

import android.util.Log;

import com.cgy.mycollections.BuildConfig;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by RB-cgy on 2015/12/25.
 * 打印调试log的工具
 */
public class LogUtils {
    private static final boolean DEBUG = true;
    private static final String FLAG = "cgy";

    private static String logTime() {
        DateFormat dateFormat = new SimpleDateFormat("[dd-MM-yyyy HH:mm:ss]: ");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static void v(String msg) {
        if (BuildConfig.DEBUG)
            Log.v(FLAG, logTime() + msg);
    }

    public static void v(String tag, String msg) {
        if (BuildConfig.DEBUG)
            Log.v(tag, logTime() + msg);
    }

    public static void d(String msg) {
        if (BuildConfig.DEBUG)
            Log.d(FLAG, logTime() + msg);
    }

    public static void d(String tag, String msg) {
        if (BuildConfig.DEBUG)
            Log.d(tag, logTime() + msg);
    }

    public static void i(String msg) {
        if (BuildConfig.DEBUG)
            Log.i(FLAG, logTime() + msg);
    }

    public static void i(String tag, String msg) {
        if (BuildConfig.DEBUG)
            Log.i(tag, logTime() + msg);
    }

    public static void w(String msg) {
        if (BuildConfig.DEBUG)
            Log.w(FLAG, logTime() + msg);
    }

    public static void w(String tag, String msg) {
        if (DEBUG)
            Log.w(tag, logTime() + msg);
    }

    public static void e(String msg) {
        if (DEBUG)
            Log.e(FLAG, logTime() + msg);
    }

    public static void e(String tag, String msg) {
        if (DEBUG)
            Log.e(tag, logTime() + msg);
    }
}
