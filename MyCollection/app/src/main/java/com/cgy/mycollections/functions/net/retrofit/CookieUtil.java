package com.cgy.mycollections.functions.net.retrofit;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import appframe.utils.SharePreUtil;
import okhttp3.Cookie;

/**
 * Created by yanghailang on 16/9/2.
 */
public class CookieUtil {

    private static String SP_RECENT_LIST = "cookieData";
//    private static String RL = "cookieList";

    private static Context context;

    public static void init(Context context) {
        CookieUtil.context = context;
    }

    /**
     * 保存cookies对象
     *
     * @param cookies
     */
    public static void saveCookies(List<Cookie> cookies, String host) {
        if (TextUtils.isEmpty(host))
            return;

        if (cookies != null && cookies.size() > 0) {
            try {
                String cookieList = new Gson().toJson(cookies, new TypeToken<List<Cookie>>() {
                }.getType());
//                L.e("ApiCookie", "cookieList:" + cookieList);

                setCookie(cookieList, host);
            } catch (Exception e) {

            }
        } else {
            clearCookie(host);
        }
    }

    /**
     * 获取未过期Cookies
     *
     * @return
     */
    public static String getUnExpiredCookies(String host) {
        if (TextUtils.isEmpty(host))
            return "";//host作为cookie的key 必须不为空

        String savedCookies = getCookie(host);
        String cookiesStr = "";
        long currentTime = System.currentTimeMillis();
        try {
            if (!TextUtils.isEmpty(savedCookies)) {
                List<Cookie> cookieList = new Gson().fromJson(savedCookies, new TypeToken<List<Cookie>>() {
                }.getType());
                if (cookieList != null) {
                    for (int i = 0, len = cookieList.size(); i < len; i++) {
                        Cookie cookie = cookieList.get(i);
//                        L.e("ApiCookie", "cookie:" + cookie.toString());
//                        L.e("ApiCookie", "currentTime:" + currentTime);
//                        L.e("ApiCookie", "expiresAt11:" + cookie.expiresAt());
                        if (cookie.expiresAt() > currentTime) {//只要大于当前时间的cookie
                            cookiesStr += cookie.name() + '=' + cookie.value();
                            if (i < len - 1)
                                cookiesStr += "; ";
                        }
                    }
                }

                if (TextUtils.isEmpty(cookiesStr)) {//为空的话可能全部过期了，直接清掉，省得下次还要处理
                    clearCookie(host);
                }
            }
        } catch (Exception e) {

        }

//        L.e("ApiCookie", "cookiesStr to server:" + cookiesStr);
        return cookiesStr;
    }

    protected static String getCookie(String host) {
//        L.e("getCookie cookie host:--->" + host);
        String cookie = SharePreUtil.getString(SP_RECENT_LIST, context, host);
        return cookie;
    }

    protected static void setCookie(String cookie, String host) { //不知道为什么会没有JSESSIONID
//        L.e("setCookie host:--->" + host);
        SharePreUtil.putString(SP_RECENT_LIST, context, host, cookie);
    }

    /**
     * 清除最近浏览
     */
    protected static void clearCookie(String host) {
//        SharePreUtil.clearPref(SP_RECENT_LIST,context);
        SharePreUtil.removeKey(SP_RECENT_LIST, context, host);
    }

    /**
     * 根据cookie 的key获取cookie的等号后面的字段
     *
     * @param key
     * @return
     */
    public static String getCookieField(String key, String host) {
        String cookie = CookieUtil.getCookie(host);
        if (!TextUtils.isEmpty(cookie)) {
            if (cookie.contains(";")) { //有分号，格式为vertx-web.session=e014b44ecd569a708ad7ef9d77e04a95; Path=/; Domain=192.168.1.107
                String[] splitBySemi = cookie.split(";");
                for (String string : splitBySemi) {
                    if (string.contains(key)) {
                        String[] splitByEqual = string.split("=");
                        cookie = splitByEqual[splitByEqual.length - 1];
                    }
                }
            } else { //无分号，格式为vertx-web.session=71e17c5b464afd6b6297066ca952116e
                String[] splitByEqual = cookie.split("=");
                cookie = splitByEqual[splitByEqual.length - 1];
            }
        }
        return cookie;
    }
}
