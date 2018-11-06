package com.cgy.redenvelop;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.Process;
import android.util.Log;

import com.cgy.redenvelop.view.EasyTouchView;

/**
 * Created by user on 2016/10/2.
 */
public class FloatingService extends Service implements EasyTouchView.FloatingViewActionListener {
    EasyTouchView mTouchView;
    boolean isPause = false;
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d("cgy", Process.myPid() + "FloatingService onReceive:" + action);
            if (action.equals(Constants.ACTION_CHANGE_FLOATING_STATE)) {
                boolean showFloating = intent.getBooleanExtra(Constants.KEY_SHOW_FLOATING, false);
                if (showFloating) {
                    if (mTouchView == null)
                        mTouchView = new EasyTouchView(FloatingService.this, FloatingService.this);

                    mTouchView.setPause(isPause);
                    mTouchView.show();
                } else if (mTouchView != null) {
                    mTouchView.dismiss();
                    mTouchView = null;
                }
            } else if (action.equals(Constants.ACTION_CHANGE_STATE)) {
                String runState = intent.getStringExtra(Constants.KEY_RUN_STATE);
                switch (runState) {
                    case Constants.STATE_RUN:
                        isPause = false;
                        break;
                    case Constants.STATE_PAUSE:
                        isPause = true;
                        break;
                }
                if (mTouchView != null)
                    mTouchView.setPause(isPause);
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
        filter.addAction(Constants.ACTION_CHANGE_STATE);
        filter.addAction(Constants.ACTION_CHANGE_FLOATING_STATE);
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
                Intent pauseIntent = new Intent(Constants.ACTION_CHANGE_STATE);
                if (isPause) {
                    pauseIntent.putExtra(Constants.KEY_RUN_STATE, Constants.STATE_RUN);
                } else {
                    pauseIntent.putExtra(Constants.KEY_RUN_STATE, Constants.STATE_PAUSE);
                }
                sendBroadcast(pauseIntent);
                break;
            case EasyTouchView.ACTION_LOCK:

                break;
            case EasyTouchView.ACTION_EXIT://退出
                Intent floatingIntent = new Intent(Constants.ACTION_CHANGE_FLOATING_STATE);
                floatingIntent.putExtra(Constants.KEY_SHOW_FLOATING, false);
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
