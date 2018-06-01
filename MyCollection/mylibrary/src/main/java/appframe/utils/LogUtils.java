package appframe.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import appframe.ProjectConfig;

public class LogUtils {

    private static final String FLAG = "witon";

    private static String logTime() {
        DateFormat dateFormat = new SimpleDateFormat("[dd-MM-yyyy HH:mm:ss]: ");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private static boolean isDebug() {
        return ProjectConfig.isDebugMode();
    }

    public static void v(String msg) {
        if (isDebug())
            Log.v(FLAG, logTime() + msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug())
            Log.v(tag, logTime() + msg);
    }

    public static void d(String msg) {
        if (isDebug())
            Log.d(FLAG, logTime() + msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug())
            Log.d(tag, logTime() + msg);
    }

    public static void i(String msg) {
        if (isDebug())
            Log.i(FLAG, logTime() + msg);
    }

    public static void i(String tag, String msg) {
        if (isDebug())
            Log.i(tag, logTime() + msg);
    }

    public static void w(String msg) {
//        if (isDebug())
            Log.w(FLAG, logTime() + msg);
    }

    public static void w(String tag, String msg) {
//        if (isDebug())
            Log.w(tag, logTime() + msg);
    }

    public static void e(String msg) {
//        if (isDebug())
            Log.e(FLAG, logTime() + msg);
    }

    public static void e(String tag, String msg) {
//        if (isDebug())
            Log.e(tag, logTime() + msg);
    }

}
