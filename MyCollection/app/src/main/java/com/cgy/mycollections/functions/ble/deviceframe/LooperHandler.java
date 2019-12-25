package com.cgy.mycollections.functions.ble.deviceframe;

import android.os.Handler;
import android.os.Looper;

import com.cgy.mycollections.utils.L;


/**
 * Description :子线程的looper，在这里运行的runnable都是在 子线程的looper中
 * Author :cgy
 * Date :2018/11/7
 */
public class LooperHandler {
    private final String TAG = this.getClass().getSimpleName();

    Handler mLooperHandler;
    Thread looperThread;
    Looper mLooper;

    public LooperHandler() {
        start();
    }

    public void start() {
        L.e(TAG, "start");
        if (looperThread != null) {
            throw new IllegalArgumentException("looperThread isn't null! can't start!");
        }
        looperThread = new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                mLooperHandler = new Handler();
                L.e(TAG, "new Handler() current thread name:" + Thread.currentThread().getName());
                mLooper = Looper.myLooper();
                Looper.loop();
                L.e(TAG, "LooperHandler quit");
            }
        };
        looperThread.start();
    }

    /**
     * post,注意 start 和post不能一起调，start中的线程启动有一定的时间，此时post 可能handler还没有初始化
     *
     * @param runnable
     */
    public synchronized void post(Runnable runnable) {
        L.e(TAG, "LooperHandler post");
        if (mLooperHandler == null) {
            L.e(TAG, "LooperHandler post mLooperHandler = null ,放弃post");
            return;
        }
        mLooperHandler.post(runnable);
    }

    public synchronized void postDelayed(Runnable r, long delayMillis) {
        L.e(TAG, "LooperHandler postDelayed");
        if (mLooperHandler == null) {
            L.e(TAG, "LooperHandler post mLooperHandler = null ,放弃post");
            return;
        }
        mLooperHandler.postDelayed(r, delayMillis);
    }

    public synchronized final void removeCallbacks(Runnable r) {
        if (mLooperHandler == null) {
            L.e(TAG, "LooperHandler removeCallbacks mLooperHandler = null ,放弃post");
            return;
        }
        mLooperHandler.removeCallbacks(r, null);
    }

    public synchronized void removeAll() {
        if (mLooperHandler == null) {
            L.e(TAG, "LooperHandler removeAll mLooperHandler = null ,放弃post");
            return;
        }
        mLooperHandler.removeCallbacksAndMessages(null);
    }

    public synchronized boolean isShutDown() {
        return looperThread == null;
    }

    /**
     * 关闭looper，此时如果要重新使用 需要调用start
     */
    public synchronized void shutDown() {
        L.e(TAG, "shutDown");
        removeAll();

        if (mLooper != null) {
            mLooper.quit();
            mLooper = null;
        }
        mLooperHandler = null;
        looperThread = null;
    }
}
