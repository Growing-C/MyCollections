package com.cgy.mycollections;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.Process;
import android.util.Log;

import com.cgy.mycollections.testsources.arknights.ui.PublicAdvertisePop;
import com.cgy.mycollections.widgets.EasyTouchView;


/**
 * Created by user on 2016/10/2.
 */
public class FloatingService extends Service implements EasyTouchView.FloatingViewActionListener {

    public static final String ACTION_CHANGE_STATE = "red_envelop_action_change";//改变暂停还是继续的action
    public static final String ACTION_CHANGE_FLOATING_STATE = "red_envelop_action_float";//是否展示悬浮窗口的action
    public static final String ACTION_SHOW_ARKNIGHTS = "show_ark";//显示arknights ui的action

    public static final String KEY_SHOW_FLOATING = "show_floating";//是否显示悬浮窗

    public static final String KEY_RUN_STATE = "run_state";//运行状态
    public static final String STATE_PAUSE = "pause";//暂停状态
    public static final String STATE_RUN = "run";//运行状态

    EasyTouchView mTouchView;
    boolean isPause = false;
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d("cgy", Process.myPid() + "FloatingService onReceive:" + action);
            if (action.equals(ACTION_CHANGE_FLOATING_STATE)) {
                boolean showFloating = intent.getBooleanExtra(KEY_SHOW_FLOATING, false);
                if (showFloating) {
                    if (mTouchView == null)
                        mTouchView = new EasyTouchView(FloatingService.this, FloatingService.this);
                    else
                        return;//不为空说明已经有一个在显示了，不操作

                    mTouchView.setPause(isPause);
                    mTouchView.show();
                } else if (mTouchView != null) {
                    mTouchView.dismiss();
                    mTouchView = null;
                }
            } else if (action.equals(ACTION_CHANGE_STATE)) {
                String runState = intent.getStringExtra(KEY_RUN_STATE);
                switch (runState) {
                    case STATE_RUN:
                        isPause = false;
                        break;
                    case STATE_PAUSE:
                        isPause = true;
                        break;
                }
                if (mTouchView != null)
                    mTouchView.setPause(isPause);
            } else if (ACTION_SHOW_ARKNIGHTS.equalsIgnoreCase(action)) {
                //显示arknights
                //TODO:activity悬浮方式

                //pop方式
                if (mTouchView != null) {
                    PublicAdvertisePop pop = new PublicAdvertisePop(getApplicationContext());
                    pop.showPop(mTouchView.getTouchView());
                }
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("cgy", "Floating service onCreate");

        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_CHANGE_STATE);
        filter.addAction(ACTION_CHANGE_FLOATING_STATE);
        filter.addAction(ACTION_SHOW_ARKNIGHTS);
        registerReceiver(mReceiver, filter);

//        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
//        PowerManager.WakeLock mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
//
//        mWakeLock.acquire();
//        mWakeLock.release();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTouchView != null)
            mTouchView.dismiss();

        unregisterReceiver(mReceiver);
        Log.d("cgy", "Floating Service onDestroy");
    }

    @Override
    public void onFloatingAction(int actionId) {
        switch (actionId) {
            case EasyTouchView.ACTION_PAUSE://暂停和开始
                Intent pauseIntent = new Intent(ACTION_CHANGE_STATE);
                if (isPause) {
                    pauseIntent.putExtra(KEY_RUN_STATE, STATE_RUN);
                } else {
                    pauseIntent.putExtra(KEY_RUN_STATE, STATE_PAUSE);
                }
                sendBroadcast(pauseIntent);
                break;
            case EasyTouchView.ACTION_LOCK://公招
                Intent showArk = new Intent(ACTION_SHOW_ARKNIGHTS);
                sendBroadcast(showArk);
                break;
            case EasyTouchView.ACTION_EXIT://退出
                Intent floatingIntent = new Intent(ACTION_CHANGE_FLOATING_STATE);
                floatingIntent.putExtra(KEY_SHOW_FLOATING, false);
                sendBroadcast(floatingIntent);
                break;
            case EasyTouchView.ACTION_OPEN://打开
                //打开系统设置中辅助功能
                Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
    }

}
