package appframe.app;

import android.app.Application;
import android.content.Context;

import appframe.crash.CrashHandler;
import appframe.utils.DisplayHelperUtils;

/**
 * 全局application
 * Created by RB-cgy on 2016/9/14.
 * <p>
 * //TODO:注意要使用这个类的时候 要在manifest中注册
 */
public class MyApplication extends Application {
    private static MyApplication mInstance;
    private String mToken = "";

    public static Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        applicationContext = this;
        DisplayHelperUtils.init(this);
        CrashHandler.getInstance().init(this, null);//拦截crash,后面一个参数是崩溃重启的activity的class
//        CrashHandler.getInstance().init(this, LoginActivity.class);//拦截crash
    }

    public static MyApplication getInstance() {
        return mInstance;
    }


    public String getToken() {
        return mToken;
    }

    public void setToken(String token) {
        this.mToken = token;
    }

}
