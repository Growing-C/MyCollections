package com.cgy.mycollections;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import appframe.utils.L;
import appframe.utils.TimeUtils;

import static android.content.Context.ALARM_SERVICE;

/**
 * author: chengy
 * created on: 2021-1-27 13:46
 * description:系统时间改变广播
 */
public class SystemTimeChangeBroadcastReceiver extends BroadcastReceiver {
    private static SystemTimeChangeBroadcastReceiver sReceiver;

    /**
     * 注册时间改变广播
     *
     * @param context context
     */
    public static synchronized void registerSystemTimeChangeReceiver(@NonNull Context context) {
        if (sReceiver == null) {
            TimeUtils.getSystemTimeFormat();//
            sReceiver = new SystemTimeChangeBroadcastReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_TIME_CHANGED);
//            filter.addAction(Intent.ACTION_TIME_TICK);//分钟改变广播
            filter.addAction(TIME_ACTION);
            context.registerReceiver(sReceiver, filter);
        }
    }

    /**
     * 反注册广播
     *
     * @param context context
     */
    public static synchronized void unRegisterSystemTimeChangeReceiver(@NonNull Context context) {
        if (sReceiver != null) {
            context.unregisterReceiver(sReceiver);
            sReceiver = null;
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {//广播在主线程
        L.i("SystemTimeChangeBroadcastReceiver:" + intent.getAction() + " --Thread:" + Thread.currentThread().toString());
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            for (String key : bundle.keySet()) {
                // key:android.intent.extra.TIME_PREF_24_HOUR_FORMAT--value:0
                L.i("key:" + key + "--value:" + bundle.get(key));
            }
        }
        if (TextUtils.equals(intent.getAction(), Intent.ACTION_TIME_TICK)) {
            //每分钟时间改变
            TimeUtils.getSystemTimeFormat();
        } else if (TextUtils.equals(intent.getAction(), Intent.ACTION_TIME_CHANGED)) {
            int hourFormat = intent.getExtras().getInt(Intent.EXTRA_TIME_PREF_24_HOUR_FORMAT);
            L.i("hourFormat:" + hourFormat);
            L.i("time_12_24:" + TimeUtils.getSystemHourFormat(context));

            TimeUtils.getSystemTimeFormat();
//        scheduleCountDownTimer();
//        scheduleTime();
//        scheduleExecutorTime();
//            scheduleAlarmTime(context);
        } else if (TextUtils.equals(TIME_ACTION, intent.getAction())) {
            L.i("mAlarmManager 24-->" + m24Format.format(new Date()));
            //AlarmManager定时
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mAlarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + TIME_INTERVAL, pendingIntent);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                mAlarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + TIME_INTERVAL, pendingIntent);
            }
        }
    }

    //----------------------以下为计时方案---------------------------------------------------
    //总结
    //1.CounDownTimer 和 Timer都有时间不准的缺陷
    //2.Timer是单线程的，schedule多个task的时候会互相影响
    //3.ScheduledExecutorService可以自己设置线程池，schedule多个task的时候更有优势
    //4.ScheduledExecutorService基于相对时间，修改系统时间不会影响执行时间
    //5.Timer基于绝对时间，修改系统时间会影响执行时间
    //6.Timer遇到异常会终止，ScheduledExecutorService不会
    //7.如果只需要监听分钟变化的话，直接使用ACTION_TIME_TICK

    //创建耗时，所以放到全局
    SimpleDateFormat m24Format = new SimpleDateFormat("HH:mm", Locale.getDefault());
    SimpleDateFormat m12Format = new SimpleDateFormat("hh:mm", Locale.getDefault());

    //----------------------AlarmManager---------------------------------------------------
    AlarmManager mAlarmManager;

    int TIME_INTERVAL = 5000; // 这是5s
    PendingIntent pendingIntent;
    public static final String TIME_ACTION = "XXX.XXX.XXX_TIME_ACTION";

    /**
     * 标准的Android触发定时任务的方式，依赖的是Android系统服务，有效唤醒
     * 但是依然还是会不准啊。。。（不过应该收到的时间点是准确的，只是执行的时间影响了收到的时间点）
     * 不过这个方式有个优势，AlarmManager主要是用来在某个时刻运行你的代码的，即时你的APP在那个特定时间并没有运行！
     */
    private void scheduleAlarmTime(Context context) {
        if (mAlarmManager == null) {
            Intent intent = new Intent();
            intent.setAction(TIME_ACTION);
            pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

            mAlarmManager = (AlarmManager) context.getApplicationContext().getSystemService(ALARM_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//6.0低电量模式需要使用该方法触发定时任务
                mAlarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), pendingIntent);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4以上 需要使用该方法精确执行时间
                mAlarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), pendingIntent);
            } else {//4。4一下 使用老方法
                mAlarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), TIME_INTERVAL, pendingIntent);
            }
            L.i("scheduleAlarmTime~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
    }

    //----------------------ScheduledExecutorService---------------------------------------------------
    ScheduledExecutorService mScheduledExecutorService;

    /**
     * 使用ScheduledExecutorService 内部使用System.nanoTime()
     * 1.由于是线程池，多线程机制可以使得schedule多个线程时，彼此不会影响执行时间
     * 2.由于使用nanoTime 修改系统时间不会影响执行时间
     * 3.不准确问题也有
     */
    private void scheduleExecutorTime() {
        if (mScheduledExecutorService == null) {
            mScheduledExecutorService = Executors.newScheduledThreadPool(3);

            mScheduledExecutorService.scheduleAtFixedRate(() -> {
                L.i("ScheduledExecutorService 24-->" + m24Format.format(new Date()));
                L.i("ScheduledExecutorService 12-->" + m12Format.format(new Date()));
                mScheduledExecutorService.shutdown();
            }, 1, 3, TimeUnit.SECONDS);

        }

    }

    //----------------------CountDownTimer---------------------------------------------------
    CountDownTimer mCountDownTimer;

    /**
     * 内部由 handler 实现。但是由于在onTick执行完了才会post下一个delayed。导致onTick执行时间会影响下个onTick时间
     * 具体表现为interval是1s时   40.777 ms时执行 ontick 下个onTick 可能在 41.788才执行，不完全是1s
     */
    private void scheduleCountDownTimer() {
        if (mCountDownTimer == null) {
            mCountDownTimer = new CountDownTimer(Integer.MAX_VALUE, 3000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    L.i("scheduleCountDownTimer 24-->" + m24Format.format(new Date()));
                    L.i("scheduleCountDownTimer 12-->" + m12Format.format(new Date()));
                }

                @Override
                public void onFinish() {
                    L.i("scheduleCountDownTimer onFinish-->" + new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
                }
            };
            mCountDownTimer.start();
            L.i("startCountDownTimer  -->");
        }
    }

    //---------------------timer--------------------------
    Timer mTimer;

    /**
     * timer计时，有个问题就是timer的定时不太准确，执行时间有偏差
     * 1.timer是基于系统绝对时间执行的，所以如果开始执行的时候调整系统时间，会导致timer执行的时间也会变
     * 如 60s后执行 系统时间是 12：30：00  本来应该 12：31：00执行，但是我们调整了系统时间到 12：29：00，实际也是 12：31：00 执行，但是时间已经过去120s了
     * 2.timer的第二个缺陷是，由于它使用的是单线程，所以长时间执行的任务会对其他任务产生影响。即timerTask的执行时间会影响下个任务的执行时间
     */
    private void scheduleTime() {
        if (mTimer == null) {
            mTimer = new Timer();
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    L.i("24-->" + m24Format.format(new Date()));
                    L.i("12-->" + m12Format.format(new Date()));
                }
            }, 1000, 3000);
            L.i("startTimer---");
        }
    }

    private void stopTime() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }
}
