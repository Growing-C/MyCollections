package com.cgy.mycollections.functions.net.retrofit.cache;

import java.io.Serializable;

/**
 * Description : 缓存数据
 * Author :cgy
 * Date :2020/7/14
 */
public class CacheData implements Serializable {
    int expireTimeInMillis;//缓存过期时间
    long cacheTime;//缓存时间 一般直接使用System.currentTimeInMillis

    String data;//接口返回的数据

}
