package com.cgy.mycollections.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.provider.Settings;

import appframe.utils.L;

/**
 * Description :系统画面 跳转
 * Author :cgy
 * Date :2020/1/21
 */
public class SystemPageUtils {

    /**
     * 打开位置设置
     *
     * @param activity
     */
    public static void openLocationSettings(Activity activity) {
        LocationManager locationManager
                = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
            boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            L.e("SystemPageUtils", "位置服务打开了？" + gps);
        }

//        Uri packageURI = Uri.parse("package:" + activity.getPackageName());
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        activity.startActivity(intent);
    }
}
