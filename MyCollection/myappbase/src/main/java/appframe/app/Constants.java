package appframe.app;

import appframe.network.update.UpdateChecker;
import appframe.utils.MobileManager;

/**
 * 全局常量
 * Created by RB-cgy on 2016/9/14.
 */
public class Constants {
    public static final String HOSPITAL_ID = "hsyyadmin";//hospitalId
    public static final String DEVICE_ID;//登录设备号标示
    public static final String APP_VER;//app版本号
    public static final String SYSTEM_VER;//系统版本号

    static {
        DEVICE_ID = new MobileManager(MyApplication.getInstance()).getMEID();
        APP_VER = UpdateChecker.getVersionName(MyApplication.getInstance());
        SYSTEM_VER = android.os.Build.VERSION.RELEASE;
        System.out.println("DEVICE_ID:" + DEVICE_ID + "--->APP_VER:" + APP_VER);
    }

    public static final String KEY_LOGIN_NAME = "login_name";//医生登录名保存的key
    public static final String KEY_PASSWORD = "password";//医生登录密码保存的key
}
