package com.cgy.mycollections.utils;

import android.os.Build;

/**
 * android版本检测工具
 **/
public class SystemVersionUtil {
    private SystemVersionUtil() {
    }

    /**
     * 获取Android系统版本号比如6.0
     */
    public static String getSystemVersion() {
        // 获取android版本号
        return Build.VERSION.RELEASE;
    }

    /**
     * 判断Android系统API的版本，比如14
     *
     * @return
     */
    public static int getApiVersion() {
        int APIVersion;
        try {
            APIVersion = Integer.valueOf(Build.VERSION.SDK);
        } catch (NumberFormatException e) {
            APIVersion = 0;
        }
        return APIVersion;
    }

    /**
     * 当前Android系统版本是否在（ IceCreamSandwich） Android4.0或 Android4.0以上
     * api level 14
     *
     * @return
     */
    public static boolean hasIcecreamsandwich() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    /**
     * 当前android系统版本是否在（JellyBean）Android4.1或android4.1以上
     * api level 16
     *
     * @return
     */
    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    /**
     * 当前android系统版本是否在（KitKat）Android4.4或android4.4以上
     * api level 19
     *
     * @return
     */
    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    /**
     * 当前是否在5.0以上  api level 21
     */
    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * 当前是否在6.0以上  api level 23
     */
    public static boolean hasM() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }


}
