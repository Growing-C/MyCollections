package com.cgy.mycamera.camerax.utils;

import android.text.TextUtils;

/**
 * 文件操作工具 可以创建和删除文件等
 */
public class FileUtil {

    /**
     * 获取文件后缀名
     */
    public static String getFileExtensionName(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return "";
        }
        int endP = fileName.lastIndexOf(".");
        return endP > -1 ? fileName.substring(endP + 1, fileName.length()) : "";
    }

}
