package com.cgy.mycollections.functions.net.retrofit.cache;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import okhttp3.Request;

/**
 * Description : 缓存策略
 * Author :cgy
 * Date :2020/7/14
 */
public interface CacheStrategy {

    /**
     * 针对缓存的 参数中可能有些同接口 但是不固定的参数，比如时间什么的 会导致无法获取缓存，所以允许外部针对参数进行处理
     * 仅用来缓存的时候用，不影响接口请求
     *
     * @param request
     * @param param
     * @return
     */
    String handlerRequestParamForCache(@NonNull Request request, @NonNull String param);

    /**
     * 根据 request 和 返回的数据 决定是否需要缓存
     *
     * @param request
     * @param resultJson 返回数据的json
     * @return 默认应该是false，即不缓存
     */
    boolean needCache(@NonNull Request request, @Nullable String resultJson);

    /**
     * 根据request 决定是否允许获取缓存。
     *
     * @param request
     * @return 默认应该是false，即不获取缓存
     */
    boolean canGetCache(@NonNull Request request);
}
