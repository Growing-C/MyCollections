package com.cgy.mycollections.functions.ble.deviceframe;

import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.util.Log;

import appframe.utils.L;


/**
 * Description :子线程的looper，在这里运行的runnable都是在 子线程的looper中
 * 类似 HandlerThread 需要注意 初始化后最好不要直接post，
 * 因为线程可能没有及时启动，导致getHandler阻塞一段时间，
 * 若在主线程中调用会卡住主线程。
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
        Log.e(TAG, "ThreadHandler start");
        if (looperThread != null) {
            throw new IllegalArgumentException("looperThread isn't null! can't start!");
        }
        looperThread = new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Log.e(TAG, "new Handler() current thread name:" + Thread.currentThread().getName());

//                synchronized (this) {
                mLooper = Looper.myLooper();
//                    notifyAll();
//                }
                Process.setThreadPriority(Process.THREAD_PRIORITY_DEFAULT);
                Looper.loop();
                Log.e(TAG, "LooperHandler quit");
            }
        };
        looperThread.start();
    }

    public Looper getLooper() {
        if (!looperThread.isAlive()) {
            return null;
        }

        // If the thread has been started, wait until the looper has been created.
        synchronized (this) {
            while (looperThread.isAlive() && mLooper == null) {
                try {
                    Log.e(TAG, "LooperHandler getLooper blocked wait 10ms");
                    wait(10);
                } catch (InterruptedException e) {
                }
            }
        }
        return mLooper;
    }

    /**
     * @return a shared {@link Handler} associated with this thread
     */
    private Handler getThreadHandler() {
        if (mLooperHandler == null) {
            mLooperHandler = new Handler(getLooper());
        }
        return mLooperHandler;
    }

    /**
     * post,注意 start 和post不能一起调，start中的线程启动有一定的时间，此时post 可能handler还没有初始化
     *
     * @param runnable
     */
    public synchronized void post(Runnable runnable) {
        Log.e(TAG, "LooperHandler post");
        if (getThreadHandler() == null) {
            Log.e(TAG, "LooperHandler post mLooperHandler = null ,放弃post");
            return;
        }
        getThreadHandler().post(runnable);
    }

    public synchronized void postDelayed(Runnable r, long delayMillis) {
        Log.e(TAG, "LooperHandler postDelayed");
        if (getThreadHandler() == null) {
            Log.e(TAG, "LooperHandler post mLooperHandler = null ,放弃post");
            return;
        }
        getThreadHandler().postDelayed(r, delayMillis);
    }

    public synchronized final void removeCallbacks(Runnable r) {
        if (getThreadHandler() == null) {
            Log.e(TAG, "LooperHandler removeCallbacks mLooperHandler = null ,放弃post");
            return;
        }
        getThreadHandler().removeCallbacks(r, null);
    }

    public synchronized void removeAll() {
        if (getThreadHandler() == null) {
            Log.e(TAG, "LooperHandler removeAll mLooperHandler = null ,放弃post");
            return;
        }
        getThreadHandler().removeCallbacksAndMessages(null);
    }

    public synchronized boolean isShutDown() {
        return looperThread == null;
    }

    /**
     * 关闭looper，此时如果要重新使用 需要调用start
     */
    public synchronized void shutDown() {
        Log.e(TAG, "Thread handler shutDown");
        removeAll();

        if (mLooper != null) {
            mLooper.quitSafely();
            mLooper = null;
        }
        mLooperHandler = null;
        looperThread = null;
    }
}
