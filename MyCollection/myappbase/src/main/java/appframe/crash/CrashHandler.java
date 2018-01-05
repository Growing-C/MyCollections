package appframe.crash;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.widget.Toast;

import appframe.ProjectConfig;

/**
 * Created by RB-cgy on 2016/5/12.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static CrashHandler mCrashHandler;
    Thread.UncaughtExceptionHandler defaultUncaughtHandler;

    private Context mContext;
    Class<?> mRestartActivityClass;//崩溃重启时的启动界面

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        if (mCrashHandler == null) {
            mCrashHandler = new CrashHandler();
        }

        return mCrashHandler;
    }

    public void init(Context context, Class<?> cls) {
        this.mRestartActivityClass = cls;
        mContext = context;
        defaultUncaughtHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (ex == null)
            return;

        if (ProjectConfig.isDebugMode() && defaultUncaughtHandler != null) {//如果是debug模式则直接交给系统的crashHandler处理
            defaultUncaughtHandler.uncaughtException(thread, ex);
            return;
        }

        //使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "Sorry, an exception occurred, about to restart", Toast.LENGTH_LONG).show();
                Looper.loop();

            }
        }.start();

        System.out.println("uncaught exception happened--------" + thread.toString());
        System.out.println(ex.toString());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("error : " + e);
        }

        restartApplication();
    }

    /**
     * 重启应用
     */
    private void restartApplication() {
        if (mRestartActivityClass == null)
            return;

        Intent intent = new Intent(mContext.getApplicationContext(), mRestartActivityClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent restartIntent = PendingIntent.getActivity(
                mContext.getApplicationContext(), 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        AlarmManager mgr = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 500,
                restartIntent); // 0.5秒钟后重启应用

        //退出程序
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}
