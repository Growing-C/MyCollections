/**
 * Witontek.com.
 * Copyright (c) 2012-2017 All Rights Reserved.
 */
package com.cgy.mycollections.functions.net.mywebservice;

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

    public static String Md5(String string) {

        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
