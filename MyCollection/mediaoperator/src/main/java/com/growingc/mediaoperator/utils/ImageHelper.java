package com.growingc.mediaoperator.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

/**
 * Description : 图片辅助
 * Author :cgy
 * Date :2019/12/6
 */
public class ImageHelper {
    /**
     * 是否是图片
     *
     * @param filePath
     * @return
     */
    public static boolean isPic(String filePath) {
        if (TextUtils.isEmpty(filePath))
            return false;
        String lowerCase = filePath.toLowerCase();

        return lowerCase.endsWith(".jpg") || lowerCase.endsWith(".png") || lowerCase.endsWith(".jpeg") || lowerCase.endsWith(".gif") || lowerCase.endsWith(".webp");
    }

    /**
     * 是否是图片，忽略点,支持大小写
     *
     * @param filePath
     * @return
     */
    public static boolean isPicIgnoreDot(String filePath) {
        if (TextUtils.isEmpty(filePath))
            return false;
        String upperCaseFilePath = filePath.toUpperCase();

        if (upperCaseFilePath.endsWith("JPG")
                || upperCaseFilePath.endsWith("PNG")
                || upperCaseFilePath.endsWith("JPEG")
                || upperCaseFilePath.endsWith("GIF")
                || upperCaseFilePath.endsWith("WEBP"))
            return true;
        return false;
    }

    /**
     * 将本地资源图片大小缩放
     *
     * @param resId
     * @param w
     * @param h
     * @return
     */
    public static Drawable zoomImage(Context context, int resId, int w, int h) {
        Resources res = context.getResources();
        Bitmap oldBmp = BitmapFactory.decodeResource(res, resId);
        Bitmap newBmp = Bitmap.createScaledBitmap(oldBmp, w, h, true);
        return new BitmapDrawable(res, newBmp);
    }

    /**
     * 获取本地图片大小等信息，但是不会加载图片，所以不会造成oom，用此方法可以预先获取图片大小来使用策略加载图片，避免oom
     *
     * @param context
     * @param imageIds
     */
    public static void getLocalImageInfo(Context context, int imageIds) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//不加载bitmap，但是获取其大小
        BitmapFactory.decodeResource(context.getResources(), imageIds, options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        String imageType = options.outMimeType;
    }



}
