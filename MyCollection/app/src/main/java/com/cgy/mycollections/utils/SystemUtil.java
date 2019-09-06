package com.cgy.mycollections.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.UserHandle;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * android系统工具类
 */
public class SystemUtil {
    private static final String TAG = "SystemUtil";
    private static final String DEVICE_ID = "DEVICE_ID";

    private SystemUtil() {

    }

    /**
     * 检查权限
     *
     * @param permission android.permission.WRITE_EXTERNAL_STORAGE
     * @return manifest 已经定义了则返回true
     */
    public static boolean checkPermission(@NonNull Context context, @NonNull String permission) {
        return context.checkCallingOrSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    //调用系统api获取权限，需要root
    public static boolean grantPermission(@NonNull Context context, @NonNull String permission) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                String pkg = context.getPackageName();
                PackageManager packageManager = context.getPackageManager();
                Method method = packageManager.getClass().getDeclaredMethod("grantRuntimePermission", String.class, String.class, UserHandle.class);
                method.setAccessible(true);
                method.invoke(packageManager, pkg, permission, android.os.Process.myUserHandle());
                return true;
            } else {
                LogUtils.e(TAG, "android os version is too low");
            }

        } catch (Exception e) {
            LogUtils.e(TAG, "error when SERVICE_EXTENDED_API_SET_NOTICICATION_OBSERVIER");
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 获取指定包名的包信息
     */
    public static PackageInfo getAppInfo(Context context, String packageName) {
        try {
            return context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取未安装软件包的包名
     */
    public static String getApkPackageName(Context context, String apkPath) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
        if (info != null) {
            ApplicationInfo appInfo = info.applicationInfo;
            appInfo.sourceDir = apkPath;
            appInfo.publicSourceDir = apkPath;
            return appInfo.packageName;
        }
        return "";
    }

    /**
     * 判断是否安装
     */
    public static boolean apkIsInstall(Context context, String apkPath) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
        if (info != null) {
            ApplicationInfo appInfo = info.applicationInfo;
            appInfo.sourceDir = apkPath;
            appInfo.publicSourceDir = apkPath;
            try {
                pm.getPackageInfo(appInfo.packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
                return true;
            } catch (PackageManager.NameNotFoundException localNameNotFoundException) {
                return false;
            }
        }
        return false;
    }

    /**
     * 判断服务是否后台运行
     *
     * @param context Context
     * @param clazz
     * @return true 在运行 false 不在运行
     */
    public static boolean isServiceRun(Context context, Class<?> clazz) {
        boolean isRun = false;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(Integer.MAX_VALUE);
        int size = serviceList.size();
        for (int i = 0; i < size; i++) {
            if (serviceList.get(i).service.getClassName().equals(clazz.getName())) {
                isRun = true;
                break;
            }
        }
        return isRun;
    }

    /**
     * 启动另外一个App
     */
    public static void startOtherApp(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        Intent launcherIntent = pm.getLaunchIntentForPackage(packageName);
        context.startActivity(launcherIntent);
    }

    /**
     * 判断手机是否拥有Root权限。
     *
     * @return 有root权限返回true，否则返回false。
     */
    public static boolean isRoot() {
        String binPath = "/system/bin/su";
        String xBinPath = "/system/xbin/su";
        return new File(binPath).exists() && isExecutable(binPath) || new File(xBinPath).exists() && isExecutable(xBinPath);
    }

    private static boolean isExecutable(String filePath) {
        Process p = null;
        try {
            p = Runtime.getRuntime().exec("ls -l " + filePath);
            // 获取返回内容
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
            String str = in.readLine();
            if (str != null && str.length() >= 4) {
                char flag = str.charAt(3);
                if (flag == 's' || flag == 'x')
                    return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (p != null) {
                p.destroy();
            }
        }
        return false;
    }

    /**
     * 检查手机是否安装了指定的APK
     *
     * @param packageName 该APK的包名
     * @return
     */
    public static boolean checkApkExists(Context context, String packageName) {
        List<PackageInfo> packageInfos = getAllApps(context);
        for (PackageInfo pi : packageInfos) {
            if (pi.packageName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 查询手机内所有应用
     *
     * @param context
     * @return
     */
    public static List<PackageInfo> getAllApps(Context context) {
        List<PackageInfo> apps = new ArrayList<PackageInfo>();
        PackageManager pManager = context.getPackageManager();
        //获取手机内所有应用
        List<PackageInfo> paklist = pManager.getInstalledPackages(0);
        for (int i = 0; i < paklist.size(); i++) {
            PackageInfo pak = paklist.get(i);
            apps.add(pak);
        }
        return apps;
    }

    /**
     * 查询手机内非系统应用
     *
     * @param context
     * @return
     */
    public static List<PackageInfo> getAllNoSystemApps(Context context) {
        List<PackageInfo> apps = new ArrayList<PackageInfo>();
        PackageManager pManager = context.getPackageManager();
        //获取手机内所有应用
        List<PackageInfo> paklist = pManager.getInstalledPackages(0);
        for (int i = 0; i < paklist.size(); i++) {
            PackageInfo pak = paklist.get(i);
            //判断是否为非系统预装的应用程序
            if ((pak.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
                // customs applications
                apps.add(pak);
            }
        }
        return apps;
    }

    /**
     * 获取目录
     */
    public static String getSourcePath(Context context, String packageName) {
        ApplicationInfo appInfo = null;
        try {
            appInfo = context.getPackageManager().getApplicationInfo(packageName, 0);
            return appInfo.sourceDir;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 手机内省
     */
    public static String getPhoneType() {
        return Build.MODEL;
    }

    /**
     * 获取包名
     */
    public static String getPackageName(Context context) {
        return getPackageInfo(context).packageName;
    }

    /**
     * 获取应用名
     */
    public static String getAppName(Context context) {
        try {
            return context.getString(context.getApplicationInfo().labelRes);
        } catch (Resources.NotFoundException e) {

            return "";
        }
    }

    /**
     * 获取设备的唯一ID
     *
     * @param context
     * @return
     */
    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 返回版本名称
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    /**
     * 返回版本号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    /**
     * 打开一个隐藏了图标的APP
     *
     * @param context
     */
    public static void openAppWithAction(Context context, String packageName, String activity) {
        ComponentName componentName = new ComponentName(packageName, activity);
        try {
            Intent intent = new Intent();
            intent.setComponent(componentName);
            context.startActivity(intent);
        } catch (Exception e) {


        }
    }

    /**
     * 应用是否被安装
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isInstall(Context context, String packageName) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            //            e.printStackTrace();
        }
        return packageInfo != null;
    }

    /**
     * 安装APP
     *
     * @param file
     */
    public static void install(Context context, File file) {

        context.startActivity(getInstallIntent(file));
    }

    /**
     * 卸载APk
     *
     * @param context
     * @param packageName 包名
     */
    public static void uninstall(Context context, String packageName) {
        Uri packageURI = Uri.parse("package:" + packageName);
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        uninstallIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(uninstallIntent);
    }

    /**
     * 获取安装应用的Intent
     *
     * @param file
     * @return
     */
    public static Intent getInstallIntent(File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        return intent;
    }

//    /**
//     * 拷贝Assets中的文件到指定目录
//     *
//     * @param context
//     * @param fileName
//     * @param path
//     * @return
//     */
//    public static boolean copyFileFromAssets(Context context, String fileName, String path) {
//        boolean copyIsFinish = false;
//        try {
//            InputStream is = context.getAssets().open(fileName);
//            File file = FileUtil.createFile(path);
//            FileOutputStream fos = new FileOutputStream(file);
//            byte[] temp = new byte[1024];
//            int i = 0;
//            while ((i = is.read(temp)) > 0) {
//                fos.write(temp, 0, i);
//            }
//            fos.close();
//            is.close();
//            copyIsFinish = true;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return copyIsFinish;
//    }

    /**
     * 获取版本信息
     */
    public static PackageInfo getPackageInfo(Context context) {
        PackageInfo pkg = null;
        if (context == null) {
            return null;
        }
        try {
            pkg = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pkg;
    }

    /**
     * 获取设备的显示属性
     *
     * @param context
     * @return
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }

//    /**
//     * 获取电话号码
//     *
//     * @param context
//     * @return
//     */
//    public static String getLocalPhoneNumber(Context context) {
//        String line1Number = getTelephonyManager(context).getLine1Number();
//        return line1Number == null ? "" : line1Number;
//    }

    /**
     * 获取设备型号(Nexus5)
     *
     * @return
     */
    public static String getDeviceModel() {
        return Build.MODEL;
    }

    /**
     * 获取电话通讯管理（可以通过这个对象获取手机号码等）
     */
    public static TelephonyManager getTelephonyManager(Context context) {
        return (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    }

    /**
     * 返回ApplicationInfo（可以通过这个读取meta-data等等）
     */
    public static ApplicationInfo getApplicationInfo(Context context) {
        if (context == null) {
            return null;
        }
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return applicationInfo;
    }

    /**
     * 获取MetaData的Bundle
     */
    public static Bundle getMetaData(Context context) {
        ApplicationInfo applicationInfo = getApplicationInfo(context);
        if (applicationInfo == null) {
            return new Bundle();
        }
        return applicationInfo.metaData;
    }

    /**
     * 应用是否启动
     */
    public static boolean appIsRunning(Context context) {
        boolean isAppRunning = false;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        String packageName = getPackageInfo(context).packageName;
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals(packageName) && info.baseActivity.getPackageName().equals(packageName)) {
                isAppRunning = true;
                //find it, break
                break;
            }
        }
        return isAppRunning;
    }

    /**
     * 检查系统是否有这个Intent，在启动Intent的时候需要检查，因为启动一个没有的Intent程序会Crash
     */
    public static boolean isIntentSafe(Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        return activities.size() > 0;
    }

    /**
     * 获取当前包的系统缓存目录
     * "/Android/data/" + context.getPackageName() + "/cache"
     */
    public static String getDiskCacheDir(Context context) {
        // Check if media is mounted or storage is built-in, if so, try and use external cache dir
        // otherwise use internal cache dir
        File cacheDirPath = getExternalCacheDir(context);
        if (cacheDirPath == null) {
            cacheDirPath = context.getCacheDir();
        }
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !isExternalStorageRemovable() ? cacheDirPath.getPath() : context.getCacheDir().getPath();
    }

    /**
     * 获取当前包的外置路径
     * "/Android/data/" + context.getPackageName()
     */
    public static String getDiskPackage(Context context) {
        return new File(getDiskCacheDir(context)).getParent();
    }

    /**
     * Check if external storage is built-in or removable.
     *
     * @return True if external storage is removable (like an SD card), false
     * otherwise.
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static boolean isExternalStorageRemovable() {
        return Environment.isExternalStorageRemovable();
    }

    /**
     * Get the external app cache directory.
     *
     * @param context The context to use
     * @return The external cache dir
     */
    @TargetApi(Build.VERSION_CODES.FROYO)
    public static File getExternalCacheDir(Context context) {
        File cacheDir;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            // /sdcard/Android/data/<application package>/cache
            if (context == null) {
                NullPointerException ex = new NullPointerException("context == null");
//                    FL.e(TAG, FL.getExceptionString(ex));
                throw ex;
            }
            cacheDir = context.getExternalCacheDir();
        } else {
            // /data/data/<application package>/cache
            cacheDir = context.getCacheDir();
        }
        return cacheDir;
//        // Before Froyo we need to construct the external cache dir ourselves
//        final String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";
//        return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
    }

    /**
     * Check how much usable space is available at a given path.
     *
     * @param path The path to check
     * @return The space available in bytes
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static long getUsableSpace(File path) {
        return path.getUsableSpace();
    }

    public static String getMac() {
        String macSerial = "";
        try {
            Process pp = Runtime.getRuntime().exec(
                    "cat /sys/class/net/wlan0/address");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);

            String line;
            while ((line = input.readLine()) != null) {
                macSerial += line.trim();
            }

            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return macSerial;
    }


    private static Boolean isDebug = null;

    public static boolean isDebug() {
        return isDebug == null ? false : isDebug.booleanValue();
    }

    /**
     * Sync lib debug with app's debug value. Should be called in module Application
     *
     * @param context
     */
    public static void syncIsDebug(Context context) {
        if (isDebug == null) {
            isDebug = context.getApplicationInfo() != null &&
                    (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        }
    }

    /**
     * 获取设备ID
     *
     * @param context
     * @return
     */
    public static String getDeviceId(Context context) {
        String deviceID = SharePreUtil.getString(TAG, context, DEVICE_ID);
        if (TextUtils.isEmpty(deviceID)) {
            StringBuilder deviceIDSB = new StringBuilder();
            // 渠道标志
            deviceIDSB.append("app");
            try {
                String androidID = getAndroidId(context);
                if (!TextUtils.isEmpty(androidID)) {
                    SharePreUtil.putString(TAG, context, DEVICE_ID, androidID);
                    return androidID;
                }
//
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

                //IMEI（imei）
                String imei = null;
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    if (tm != null) {
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        imei = tm.getDeviceId();
                        if (!TextUtils.isEmpty(imei)) {
                            deviceIDSB.append("imei");
                            deviceIDSB.append(imei);
                            deviceID = deviceIDSB.toString();
                            SharePreUtil.putString(TAG, context, DEVICE_ID, deviceID);
                            return deviceID;
                        }
                    }
                    //序列号（sn）
                    String sn = null;
                    if (tm != null) {
                        sn = tm.getSimSerialNumber();
                    }
                    if (!TextUtils.isEmpty(sn)) {
                        deviceIDSB.append("sn");
                        deviceIDSB.append(sn);
                        deviceID = deviceIDSB.toString();
                        SharePreUtil.putString(TAG, context, DEVICE_ID, deviceID);
                        return deviceID;
                    }
                }
                //wifi mac地址
                WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                WifiInfo info = wifi.getConnectionInfo();
                String wifiMac = info.getMacAddress();
                if (!TextUtils.isEmpty(wifiMac)) {
                    deviceIDSB.append("wifi");
                    deviceIDSB.append(wifiMac);
                    deviceID = deviceIDSB.toString();
                    SharePreUtil.putString(TAG, context, DEVICE_ID, deviceID);
                    return deviceID;
                }
                //如果上面都没有， 则生成一个id：随机码
                String uuid = getUUID(context);
                if (!TextUtils.isEmpty(uuid)) {
                    deviceIDSB.append("id");
                    deviceIDSB.append(uuid);
                    deviceID = deviceIDSB.toString();
                    SharePreUtil.putString(TAG, context, DEVICE_ID, deviceID);
                    return deviceID;
                }
            } catch (Exception e) {
                deviceIDSB.append("id").append(getUUID(context));
                deviceID = deviceIDSB.toString();
                SharePreUtil.putString(TAG, context, DEVICE_ID, deviceID);
            }
            return deviceID;
        }
        return deviceID;
    }

    /**
     * 得到全局唯一UUID
     */
    private static String getUUID(Context context) {
        String uuid = UUID.randomUUID().toString();
        SharePreUtil.putString(DEVICE_ID, context, DEVICE_ID, uuid);
        Log.e(TAG, "getUUID : " + uuid);
        return uuid;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”。日语，是jp或ja
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取状态栏高度
     *
     * @return 默认38
     */
    public static int getStatuBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 38;// 默认为38，貌似大部分是这样的
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = context.getResources()
                    .getDimensionPixelSize(x);

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return sbar;
    }


    public static String getSimCountryIo(Context context) {
        TelephonyManager tm = getTelephonyManager(context);
        String simCountryIso = tm.getSimCountryIso();
        if (!TextUtils.isEmpty(simCountryIso)) {
            simCountryIso = simCountryIso.toUpperCase();
            return simCountryIso;
        }
        return "";
    }
}