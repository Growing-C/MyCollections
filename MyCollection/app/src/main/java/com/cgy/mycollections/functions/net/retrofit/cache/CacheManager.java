package com.cgy.mycollections.functions.net.retrofit.cache;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.cgy.mycollections.MyApplication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import appframe.utils.L;
import okhttp3.internal.cache.DiskLruCache;
import okhttp3.internal.io.FileSystem;
import okio.Buffer;
import okio.Sink;
import okio.Source;


/**
 * Description : https://www.jianshu.com/p/7aa8f3443e05
 * Author :cgy
 * Date :2020/7/13
 */
public final class CacheManager {

    public static final String TAG = "EAGLE_BASE_tCache";

    //max cache size 10mb
    private static final long DISK_CACHE_SIZE = 1024 * 1024 * 10;

    private static final int DISK_CACHE_INDEX = 0;

    private static final String CACHE_DIR = "responses";

    private DiskLruCache mDiskLruCache;

    private volatile static CacheManager mCacheManager;

    public static CacheManager getInstance() {
        if (mCacheManager == null) {
            synchronized (CacheManager.class) {
                if (mCacheManager == null) {
                    mCacheManager = new CacheManager(MyApplication.getInstance());
                }
            }
        }
        return mCacheManager;
    }

    private CacheManager(Context applicationContext) {
        File diskCacheDir = getDiskCacheDir(applicationContext, CACHE_DIR);
        if (!diskCacheDir.exists()) {
            boolean b = diskCacheDir.mkdirs();
            L.d(TAG, "!diskCacheDir.exists() --- diskCacheDir.mkdirs()=" + b);
        }
        if (diskCacheDir.getUsableSpace() > DISK_CACHE_SIZE) {
            try {
//                mDiskLruCache = DiskLruCache.open(diskCacheDir,
//                        getAppVersion(BaseApplication.getDefault()), 1/*一个key对应多少个文件*/, DISK_CACHE_SIZE);

                mDiskLruCache = DiskLruCache.create(FileSystem.SYSTEM, diskCacheDir,
                        getAppVersion(applicationContext), 1, DISK_CACHE_SIZE);
                L.d(TAG, "mDiskLruCache created");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 同步设置缓存
     */
    public void putCache(String key, String value) {
        if (mDiskLruCache == null) return;
//        OutputStream os = null;
        Sink sink = null;
        Buffer buffer = null;
        InputStream inputStream = null;
        try {
            DiskLruCache.Editor editor = mDiskLruCache.edit(encryptMD5(key));
//            os = editor.newOutputStream(DISK_CACHE_INDEX);
//            os.write(value.getBytes());
//            os.flush();
//            editor.commit();
//            mDiskLruCache.flush();

            if (editor != null) {
                sink = editor.newSink(DISK_CACHE_INDEX);
                byte[] fileReader = new byte[4096];
                buffer = new Buffer();
                inputStream = new ByteArrayInputStream(value.getBytes());
                int len = 0;
                while (true) {
                    len = inputStream.read(fileReader);
                    if (len == -1)
                        break;
                    buffer.write(fileReader, 0, len);
                    //fileSizeDownloaded += read;
                    sink.write(buffer, len);
                    buffer.clear();
                }

                sink.flush();
                editor.commit();
                mDiskLruCache.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (buffer != null) {
                buffer.clear();
                buffer.close();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (sink != null) {
                try {
                    sink.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void putCache(String key, Buffer buffer) {
        if (mDiskLruCache == null || buffer == null || TextUtils.isEmpty(key)) return;
        Sink sink = null;
        try {
            DiskLruCache.Editor editor = mDiskLruCache.edit(encryptMD5(key));

            if (editor != null) {
                sink = editor.newSink(DISK_CACHE_INDEX);
//                byte[] fileReader = new byte[4096];
//                int len = 0;
//                while (len != -1) {
//                    len = buffer.read(fileReader);
                sink.write(buffer, buffer.size());
//                }

                sink.flush();
                editor.commit();
                mDiskLruCache.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            buffer.clear();
            buffer.close();

            if (sink != null) {
                try {
                    sink.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 异步设置缓存
     */
    public void setCache(final String key, final String value) {
        new Thread() {
            @Override
            public void run() {
                putCache(key, value);
            }
        }.start();
    }

    /**
     * 同步获取缓存
     */
    public String getCache(String key) {
        if (mDiskLruCache == null) {
            return null;
        }
//        FileInputStream fis = null;
        Source source = null;
        ByteArrayOutputStream bos = null;
        try {
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(encryptMD5(key));
            if (snapshot != null) {
//                fis = (FileInputStream) snapshot.getInputStream(DISK_CACHE_INDEX);
                source = snapshot.getSource(DISK_CACHE_INDEX);
                Buffer buffer = new Buffer();
                bos = new ByteArrayOutputStream();
                long len = 0;
                while (len != -1) {
                    len = source.read(buffer, 1024);
                }
                buffer.writeTo(bos);

//                byte[] buf = new byte[1024];
//                int len;
//                while ((len = source.read(buf)) != -1) {
//                    bos.write(buf, 0, len);
//                }
                byte[] data = bos.toByteArray();
                return new String(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
//            if (fis != null) {
//                try {
//                    fis.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
            if (source != null) {
                try {
                    source.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

//    /**
//     * 异步获取缓存
//     */
//    public void getCache(final String key, final CacheCallback callback) {
//        new Thread() {
//            @Override
//            public void run() {
//                String cache = getCache(key);
//                callback.onGetCache(cache);
//            }
//        }.start();
//    }

    /**
     * 移除缓存
     */
    public boolean removeCache(String key) {
        if (mDiskLruCache != null) {
            try {
                return mDiskLruCache.remove(encryptMD5(key));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 清空缓存
     */
    public void clearCache() {
        if (mDiskLruCache != null) {
            try {
                mDiskLruCache.evictAll();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取缓存目录
     */
    private File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath = context.getCacheDir().getPath();
        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * 对字符串进行MD5编码
     */
    public static String encryptMD5(String string) {
        try {
            byte[] hash = MessageDigest.getInstance("MD5").digest(
                    string.getBytes("UTF-8"));
            StringBuilder hex = new StringBuilder(hash.length * 2);
            for (byte b : hash) {
                if ((b & 0xFF) < 0x10) {
                    hex.append("0");
                }
                hex.append(Integer.toHexString(b & 0xFF));
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return string;
    }

    /**
     * 获取APP版本号
     */
    private int getAppVersion(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi == null ? 0 : pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }
}