package com.example.testsourcelib;

/**
 * author: chengy
 * created on: 2021-2-9 10:57
 * description: 可以复用的byte数组
 */
public class PoolBytes {
    public static final Object sPoolSync = new Object();
    private static final int MAX_POOL_SIZE = 50;
    private static PoolBytes sPool;
    private static int sPoolSize = 0;

    public byte[] mBytes;
    PoolBytes mNext;

    PoolBytes(byte[] bytes) {
        mBytes = bytes;
    }

    /**
     * 获取PoolBytes，有缓存使用缓存，无缓存才new
     *
     * @param bytesLen 外部传入的byte[]长度
     * @return PoolBytes
     */
    public static PoolBytes obtainBytes(int bytesLen) {
        synchronized (sPoolSync) {
            if (sPool != null) {
                PoolBytes b = sPool;
                sPool = b.mNext;
                b.mNext = null;
                sPoolSize--;
                System.out.println("b.mBytes.length:" + b.mBytes.length + "-----bytesLen:" + bytesLen);
                if (b.mBytes.length != bytesLen) {
                    b.mBytes = new byte[bytesLen];
                }
                return b;
            }
        }
        System.out.println("obtainBytes new 000");
        return new PoolBytes(new byte[bytesLen]);
    }

    public void recycle() {
        synchronized (sPoolSync) {
            if (sPoolSize < MAX_POOL_SIZE) {
                mNext = sPool;
                sPool = this;
                sPoolSize++;
                System.out.println("recycle sPoolSize:" + sPoolSize);
            }
        }
    }
}
