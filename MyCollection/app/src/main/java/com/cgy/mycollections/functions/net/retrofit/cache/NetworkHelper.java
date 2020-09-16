package com.cgy.mycollections.functions.net.retrofit.cache;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.cgy.mycollections.MyApplication;
import com.cgy.mycollections.functions.net.retrofit.Api;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import appframe.network.retrofit.network.NetworkMonitor;
import appframe.utils.L;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Description :
 * Author :cgy
 * Date :2020/7/13
 */
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class NetworkHelper {
    private static final String TAG = "EAGLE_BASE_t";

    public static final String KEY_LAST_CACHE_TIME = "last_cache_time";

    private int cacheExpireTime = 24 * 60 * 60;//默认缓存超时时间为一天

    private PostCacheInterceptor postCacheInterceptor;

    private NetworkMonitor mMonitor;

    private CacheStrategy cacheStrategy;

    private volatile static NetworkHelper mNetworkHelper;
    boolean cacheEnabled = false;
    Context applicationContext;

    public static NetworkHelper getInstance() {
        if (mNetworkHelper == null) {
            synchronized (CacheManager.class) {
                if (mNetworkHelper == null) {
                    mNetworkHelper = new NetworkHelper();
                }
            }
        }
        return mNetworkHelper;
    }

    private NetworkHelper() {
        applicationContext = MyApplication.getInstance();
    }

    public boolean isCacheEnabled() {
        return cacheEnabled;
    }

    /**
     * 添加外部的缓存策略
     *
     * @param strategy
     */
    public void setCacheStrategy(CacheStrategy strategy) {
        this.cacheStrategy = strategy;
        if (postCacheInterceptor != null)
            postCacheInterceptor.setCacheStrategy(strategy);
    }


    /**
     * 设置过期时间
     *
     * @param cacheExpireTimeInSecond
     */
    public void setCacheExpireTime(int cacheExpireTimeInSecond) {
        this.cacheExpireTime = cacheExpireTimeInSecond;
        if (postCacheInterceptor != null)
            postCacheInterceptor.setCacheExpireTime(cacheExpireTime);
    }

    boolean needCache(@NonNull Request request, String resultJson) {
        if (cacheStrategy != null) {
            return cacheStrategy.needCache(request, resultJson);
        }
        return false;
    }

    /**
     * 根据request 决定是否允许获取缓存。
     *
     * @param request
     * @return
     */
    boolean canGetCache(@NonNull Request request) {
        if (cacheStrategy != null) {
            return cacheStrategy.canGetCache(request);
        }
        return false;
    }

    /**
     * 有网时候的缓存
     */
    private final Interceptor NetCacheInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            String cacheControl = request.header("Cache-Control");
            L.e(TAG, "NetCacheInterceptor   url:" + request.url() + "  cacheControl：" + cacheControl);

            Response response = chain.proceed(request);
            int onlineCacheTime = 3000000;//在线的时候的缓存过期时间，如果想要不缓存，直接时间设置为0
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=" + onlineCacheTime)
                    .build();
        }
    };
    /**
     * 没有网时候的缓存
     */
    private final Interceptor OfflineCacheInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            String cacheControl = request.header("Cache-Control");

            L.e(TAG, "OfflineCacheInterceptor url:" + request.url() + "  cacheControl：" + cacheControl);
//            if (mMonitor.isConnected()) {
//                L.e(TAG, "OfflineCacheInterceptor 有网络222222222222222   ");
//                request = request.newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build();
//            } else {
//                L.e(TAG, "OfflineCacheInterceptor 无网络1111111111111111   ");
//                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
//            }

            if (!mMonitor.isConnected()) {
                L.e(TAG, "OfflineCacheInterceptor 无网络1111111111111111   ");
                int requestStale = getExpireTimeFromCacheControl(cacheControl);
                //接口header中含有cacheControl的时候优先使用header中的过期时间
                if (requestStale < 0) {
                    requestStale = cacheExpireTime;
                }
                // 缓存时间大于0才使用缓存，默认所有接口都使用缓存，除非某个接口header中设置缓存时间为0
                if (requestStale > 0) {
                    L.e(TAG, "OfflineCacheInterceptor 无网络 使用缓存，缓存过期时间：" + requestStale);
                    request = request.newBuilder()
                            .removeHeader("Pragma")
//                        .cacheControl(new CacheControl.Builder().noCache().build())//强制不使用缓存
                            .cacheControl(new CacheControl
                                    .Builder()
                                    .maxStale(requestStale, TimeUnit.SECONDS)
                                    .onlyIfCached()
                                    .build()
                            )// 两种方式结果是一样的，写法不同
//                        .header("Cache-Control", "public, only-if-cached, max-stale=" + offlineCacheTime)
                            .build();
                }

            } else {
                L.e(TAG, "OfflineCacheInterceptor 有网络222222222222222   ");
            }
//            maxAge和maxStale的区别在于：
//            maxAge:没有超出maxAge,不管怎么样都是返回缓存数据，超过了maxAge,发起新的请求获取数据更新，请求失败返回缓存数据。
//            maxStale:没有超过maxStale，不管怎么样都返回缓存数据，超过了maxStale,发起请求获取更新数据，请求失败返回失败

            Response response = chain.proceed(request);

            L.e(TAG, "OfflineCacheInterceptor network: " + response.networkResponse());
            L.e(TAG, "OfflineCacheInterceptor cache: " + response.cacheResponse());

            return response;
        }
    };

    /**
     * 设置缓存策略,启用缓存
     *
     * @param cacheExpireTimeInSecond
     */
    public void enableCache(int cacheExpireTimeInSecond, CacheStrategy strategy) {
        setCacheStrategy(strategy);
        setCacheExpireTime(cacheExpireTimeInSecond);
        cacheEnabled = true;
        if (postCacheInterceptor != null) {
            L.e(TAG, "enableCache 在取消cache之前只能使用一次！");
            return;
        }

        mMonitor = new NetworkMonitor(applicationContext);
        //setup cache
        File httpCacheDirectory = new File(applicationContext.getExternalCacheDir(), "okhttpCache");
        L.e(TAG, "setNetworkCacheStrategy-------------------------- getCacheDir getPath:" + httpCacheDirectory.getPath());

        postCacheInterceptor = new PostCacheInterceptor(cacheExpireTimeInSecond, strategy);
        int cacheSize = 100 * 1024 * 1024; // 100 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);
        Api.getInstance().setNetCache(cache);
//        Api.getInstance().addNetworkInterceptor(NetCacheInterceptor);
        Api.getInstance().addInterceptor(postCacheInterceptor);// post需要自己手动缓存
        Api.getInstance().addInterceptor(OfflineCacheInterceptor);// get请求先走interceptor 的offline
    }

    /**
     * 取消cache，通过把过期时间设置为0实现
     */
    public void disableCache() {
        cacheEnabled = false;
        setCacheExpireTime(0);
        Api.getInstance().removeInterceptor(postCacheInterceptor);
        Api.getInstance().removeInterceptor(OfflineCacheInterceptor);
        postCacheInterceptor = null;
    }

    public static int getExpireTimeFromCacheControl(String cacheControl) {
        int requestStale = -1;
        if (!TextUtils.isEmpty(cacheControl)) {
            String[] split = cacheControl.split("=");
            if (split.length > 1) {
                try {
                    requestStale = Integer.parseInt(split[1]);
                } catch (Exception e) {
                }
            }
        }
        return requestStale;
    }

    /**
     * 当时间不合适的时候清空缓存
     *
     * @param externalCurrentTimeInMillis
     */
    public static void clearCacheIfTimeInvalid(long externalCurrentTimeInMillis) {
        if (mNetworkHelper == null || externalCurrentTimeInMillis <= 0)
            return;
        int cacheExpireTime = mNetworkHelper.cacheExpireTime;

        String lastCacheTime = CacheManager.getInstance().getCache(KEY_LAST_CACHE_TIME);
        if (!TextUtils.isEmpty(lastCacheTime)) {
            try {
                long lastTime = Long.parseLong(lastCacheTime);

                L.e(TAG, "clearCacheIfTimeInvalid currentTime:" + externalCurrentTimeInMillis);
                L.e(TAG, "clearCacheIfTimeInvalid lastTime:" + lastTime);
                L.e(TAG, "clearCacheIfTimeInvalid time between:" + (externalCurrentTimeInMillis - lastTime));
                if (externalCurrentTimeInMillis < lastTime) {
                    // a). 如果 本机时间<最后使用时间, 清空缓存，不能使用。
                    L.e(TAG, "clearCacheIfTimeInvalid 本机时间<最后使用时间 清空缓存:");
                    CacheManager.getInstance().clearCache();
                } else if (externalCurrentTimeInMillis > lastTime && lastTime + cacheExpireTime * 1000 <= externalCurrentTimeInMillis) {
                    // b).如果 本机时间>最后使用时间, 最初缓存时间 + 24 小时 <= 本机时间, 清空缓存，不能使用。
                    CacheManager.getInstance().clearCache();
                    L.e(TAG, "clearCacheIfTimeInvalid 缓存超时， 清空缓存:");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 检查本地当前时间和外部提供的当前时间的差值，如果不在允许的误差范围内就删除cache
     *
     * @param externalCurrentTimeInMillis
     * @param deviationTimeInMillis
     */
    public static void checkCurrentTimeWithExternalCurrentTime(long externalCurrentTimeInMillis, int deviationTimeInMillis) {
        if (externalCurrentTimeInMillis <= 0 || deviationTimeInMillis < 0)
            return;

        long currentTime = System.currentTimeMillis();
        long timeBetween = Math.abs(currentTime - externalCurrentTimeInMillis);
        L.e(TAG, "checkCurrentTimeWithExternalCurrentTime timeBetween :" + timeBetween);
        if (timeBetween > deviationTimeInMillis) {
            //误差超过允许的范围
            L.e(TAG, "checkCurrentTimeWithExternalCurrentTime 删除缓存，误差超过了:" + deviationTimeInMillis);
            CacheManager.getInstance().clearCache();
        }

    }
}
