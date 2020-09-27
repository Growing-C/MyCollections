package com.cgy.mycollections.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * 作者: cgy
 * 创建日期: 2020/9/25 11:22
 * 修改日期: 2020/9/25 11:22
 * 类说明：使用系统分享的工具
 */
public class ShareUtils {

    /**
     * 分享文件，（不支持文件夹）
     *
     * @param context
     * @param file
     */
    public static void shareFile(Context context, File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //设置intent的Action属性
        intent.setAction(Intent.ACTION_VIEW);
        //获取文件file的MIME类型
        String type = FileUtil.getRawFileType(file);
        intent.setDataAndType(Uri.fromFile(file), type);
        //跳转
        context.startActivity(intent);
    }
}
