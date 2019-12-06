/**
 * Witontek.com.
 * Copyright (c) 2012-2017 All Rights Reserved.
 */
package com.cgy.mycollections.utils.encrypt;

import android.text.TextUtils;

import com.cgy.mycollections.functions.net.mywebservice.request.models.MicroPayParams;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;


/**
 * @author song.li@witontek.com
 * @version $Id: SignUtils.java, v 0.1 2017年4月7日 上午9:40:50 song.li@witontek.com Exp $
 */
public class SignUtils {


    public static String sign(MicroPayParams signObj, String signKey) {
        return sign(toSignMap(signObj), signKey);
    }

    public static String sign(Map<String, Object> map, String signKey) {
        if (map == null || TextUtils.isEmpty(signKey)) {
            return null;
        }
        try {
            String content = toSignStr(map, signKey);

            return Md5(content).toUpperCase();
        } catch (Exception e) {
            return null;
        }
    }

    private static Map<String, Object> toSignMap(MicroPayParams signObj) {
        String json = new Gson().toJson(signObj);
        Map<String, Object> map = new Gson().fromJson(json, new TypeToken<HashMap<String, Object>>() {
        }.getType());
        return map;
    }

    private static String toSignStr(Map<String, Object> params, String signKey) {
        TreeSet<String> keys = new TreeSet<>(params.keySet());
        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            if (TextUtils.equals(key, "sign")) {
                continue;
            }
            Object value = params.get(key);
            if (value == null) {
                continue;
            }
            String strValue = value.toString();
            if (!TextUtils.isEmpty(strValue)) {
                sb.append(key).append("=").append(strValue).append("&");
            }
        }
        sb.append("key=").append(signKey);
        return sb.toString();
    }

    public static String Md5(String source) {
        if (TextUtils.isEmpty(source)) {
            return "";
        }
        String result;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(source.getBytes());
            result = bytesToHexString(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            result = String.valueOf(source.hashCode());
        }
        return result;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
