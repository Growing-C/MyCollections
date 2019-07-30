package com.cgy.mycollections.utils;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;

import java.lang.reflect.Field;

/**
 * Description : 反射相关 功能
 * Author :cgy
 * Date :2019/7/30
 */
public class ReflectUtils {
    /**
     * 获取 class中的 某个filed的值
     * 如"com.linkstec.blockchains.bclwallet.BCLWalletConstants$ROUTER"  $代表是内部类
     *
     * @param className
     * @param filedName
     * @return
     */
    static String getFiledContent(String className, String filedName) {
        if (filedName == null || filedName.length() == 0)
            return "";
        try {
            Field[] fields = Class.forName(className).getDeclaredFields();
            if (fields != null && fields.length > 0) {
                for (int i = 0; i < fields.length; i++) {
                    Field filed = fields[i];
                    if (filedName.equals(filed.getName())) {
                        filed.setAccessible(true);
                        return filed.get(null).toString();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据 xml中 string的name 来获取string值
     *
     * @param context
     * @param name
     * @return
     */
    public static String getString(@NonNull Context context, @NonNull String name) {
        Resources resources = context.getResources();
        String packageName = context.getPackageName();
        return resources.getString(resources.getIdentifier(name, "string", packageName));
    }

    /**
     * 根据xml中string的name来获取该string的id
     *
     * @param context
     * @param name
     * @return
     */
    public static int getStringId(@NonNull Context context, @NonNull String name) {
        Resources resources = context.getResources();
        String packageName = context.getPackageName();
        return resources.getIdentifier(name, "string", packageName);
    }
}
