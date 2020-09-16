package com.cgy.mycollections.functions.net.retrofit.cache;

import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import appframe.utils.L;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Description : 由于 okhttp 只缓存 get ，post 需要自己手动缓存
 * Author :cgy
 * Date :2020/7/13
 */
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class PostCacheInterceptor implements Interceptor {
//    private NetworkMonitor mMonitor;

    private int cacheExpireTime;
    private Gson gson;
    private CacheStrategy cacheStrategy;

    public PostCacheInterceptor(int cacheExpireTime, CacheStrategy strategy) {
        setCacheExpireTime(cacheExpireTime);
        this.gson = new Gson();
        setCacheStrategy(strategy);
    }

    /**
     * 添加外部的缓存策略
     *
     * @param strategy
     */
    public void setCacheStrategy(CacheStrategy strategy) {
        this.cacheStrategy = strategy;
    }

    public void setCacheExpireTime(int cacheExpireTime) {
        this.cacheExpireTime = cacheExpireTime;
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

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);

        L.e(CacheManager.TAG, "response code:" + response.code());
        // post 请求且缓存时间大于0 才进行缓存和读取缓存
        if (request.method().equals("POST") && cacheExpireTime > 0) {

            NetworkHelper.clearCacheIfTimeInvalid(System.currentTimeMillis());

            // 请求成功的时候进行缓存
            if (response.code() == 200) {
                cache(request, response);
            } else if (canGetCache(request)) {
                //请求失败的时候且允许读取缓存的时候读取缓存
                response = getFromCache(request, response);
            }
        }
        return response;
    }

    private void cache(Request request, Response response) throws IOException {
        String key = getRequestContent(request);
        L.d(CacheManager.TAG, "PostCacheInterceptor request  key :" + key);
        if (TextUtils.isEmpty(key)) {
            // key为空不缓存
            return;
        }

        String cacheControl = request.header("Cache-Control");
        int requestStale = NetworkHelper.getExpireTimeFromCacheControl(cacheControl);
        //接口header中含有cacheControl的时候优先使用header中的过期时间
        if (requestStale < 0) {
            requestStale = cacheExpireTime;
        }
        // 缓存时间等于0 也需要缓存，这样获取的时候才能根据0来选择不使用缓存
        if (requestStale >= 0) {
            ResponseBody responseBody = response.body();
            MediaType contentType = responseBody.contentType();

            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE);
            Buffer buffer = source.buffer();

            Charset charset = StandardCharsets.UTF_8;
            if (contentType != null) {
                charset = contentType.charset(StandardCharsets.UTF_8);
            }

            //服务器返回的json原始数据
            String json = buffer.clone().readString(charset);

            if (needCache(request, json)) {
                CacheManager.getInstance().putCache(key, generateCacheData(json, requestStale));
                CacheManager.getInstance().putCache(NetworkHelper.KEY_LAST_CACHE_TIME, String.valueOf(System.currentTimeMillis()));
                L.d(CacheManager.TAG, "put cache-> key:" + key + "-> json:" + json);
            }
        }
    }

    private String generateCacheData(String data, int cacheExpireTime) {
        CacheData cacheData = new CacheData();
        cacheData.cacheTime = System.currentTimeMillis();
        cacheData.expireTimeInMillis = cacheExpireTime * 1000;
        cacheData.data = data;
        return gson.toJson(cacheData);
    }

    private String getDataFromCache(String cache) {
        String data = null;

        if (!TextUtils.isEmpty(cache)) {
            try {
                CacheData cacheData = gson.fromJson(cache, CacheData.class);
                long currentTimeFromCacheTime = System.currentTimeMillis() - cacheData.cacheTime;
                L.e(CacheManager.TAG, "离上次缓存时间：" + currentTimeFromCacheTime / 1000 + "--缓存过期时间：" + cacheData.expireTimeInMillis / 1000);
                if (currentTimeFromCacheTime >= 0 && currentTimeFromCacheTime <= cacheData.expireTimeInMillis) {
                    //缓存没有过期，使用缓存数据
                    data = cacheData.data;
                    L.e(CacheManager.TAG, "使用缓存数据：" + data);
                }
            } catch (Exception e) {
                L.e(CacheManager.TAG, "error:" + e.toString());
                e.printStackTrace();
            }
        } else {
            L.e(CacheManager.TAG, "getDataFromCache but there is no cache data!!!!!!!!!");
        }
        return data;
    }

    /**
     * 从缓存获取数据
     *
     * @param request
     */
    private Response getFromCache(Request request, Response response) {
        String cacheKey = getRequestContent(request);

        String cache = CacheManager.getInstance().getCache(cacheKey);
        L.d(CacheManager.TAG, "getFromCache cache key:" + cacheKey + "->->-> content:" + cache);

        String cacheData = getDataFromCache(cache);
        // 当上次缓存的数据为空时就还是用原来的response(为空原因可能是没有cache或者cache过期)
        if (TextUtils.isEmpty(cacheData))
            return response;

        return getHttpSuccessResponse(request, cacheData);
    }

    private String getRequestContent(Request request) {
        String url = request.url().toString();
        RequestBody requestBody = request.body();
        Charset charset = StandardCharsets.UTF_8;
        StringBuilder requestContent = new StringBuilder();
        requestContent.append(url);
        MediaType requestContentType = requestBody.contentType();
        if (requestContentType != null) {
            charset = requestContentType.charset(StandardCharsets.UTF_8);
        }
        Buffer requestBuffer = new Buffer();
        try {
            requestBody.writeTo(requestBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String paramString = requestBuffer.readString(charset);
        if (cacheStrategy != null) {
            paramString = cacheStrategy.handlerRequestParamForCache(request, paramString);
        }

        requestContent.append(paramString);
        requestBuffer.close();

        return requestContent.toString();
    }

    /**
     * 根据数据JSON字符串构造HTTP响应，在JSON数据不为空的情况下返回200响应，否则返回500响应
     *
     * @param request  用户的请求
     * @param dataJson 响应数据，JSON格式
     * @return 构造的HTTP响应
     */
    private Response getHttpSuccessResponse(Request request, String dataJson) {
        Response response;
        if (TextUtils.isEmpty(dataJson)) {
            L.d(CacheManager.TAG, "getHttpSuccessResponse: dataJson is empty!");
            response = new Response.Builder()
                    .code(500)
                    .protocol(Protocol.HTTP_1_0)
                    .request(request)
                    .message("no available cache!")
                    .body(ResponseBody.create(MediaType.parse("application/json"), "no available cache!"))
                    .build();
        } else {
            response = new Response.Builder()
                    .code(200)
                    .message(dataJson)
                    .request(request)
                    .protocol(Protocol.HTTP_1_0)
                    .addHeader("Content-Type", "application/json")
                    .body(ResponseBody.create(MediaType.parse("application/json"), dataJson)).build();
        }
        return response;
    }
}
