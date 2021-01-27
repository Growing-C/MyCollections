package com.growingc.mediaoperator.picturerender;

import android.graphics.Bitmap;

/**
 * @author Tauren
 * Date: 2019-6-21 14:40
 * Description：PoolBitmap
 */
public class PoolBitmap {
    public static final Object sPoolSync = new Object();
    private static final int MAX_POOL_SIZE = 50;
    private static PoolBitmap sPool;
    private static int sPoolSize = 0;

    public Bitmap mBitmap;
    PoolBitmap mNext;

    private int mUsedCount = 0;


    PoolBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    public static PoolBitmap obtainBitmap(int width, int height) {
        synchronized (sPoolSync) {
            if (sPool != null) {
                PoolBitmap b = sPool;
                sPool = b.mNext;
                b.mNext = null;
                sPoolSize--;
                if (b.mBitmap.getWidth() != width || b.mBitmap.getHeight() != height) {
                    b.mBitmap.recycle();
                    b.mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                }
                return b;
            }
        }
        return new PoolBitmap(Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565));
    }

    public synchronized void lock() {
        mUsedCount += 1;
    }

    public synchronized void unlock() {
        mUsedCount -= 1;
    }

    public synchronized void recycle() {
        if (mUsedCount > 0) {
            return;
        }
        synchronized (sPoolSync) {
            if (null != mNext) {
                return;
            }
            if (sPoolSize < MAX_POOL_SIZE) {
                mNext = sPool;
                sPool = this;
                sPoolSize++;
            }
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        recycle();
    }
}
