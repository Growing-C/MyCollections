package com.cgy.mycollections.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Description :屏幕相关工具
 * Author :cgy
 * Date :2019/9/19
 */
public class ScreenUtils {

    //<editor-fold desc="应用内截屏 ">
    public static void takeAppScreenshotWithoutStatusBar(Window window) {
        View dView = window.getDecorView();
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(dView.getDrawingCache());
        if (bitmap != null) {
            try {
                // 获取内置SD卡路径
                String sdCardPath = Environment.getExternalStorageDirectory().getPath();
                // 图片文件路径
                String filePath = sdCardPath + File.separator + "screenshot.png";
                File file = new File(filePath);
                FileOutputStream os = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
                os.close();
                L.d("存储完成:" + filePath);
            } catch (Exception e) {
            }
        }
    }

    public static Bitmap takeViewShot(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());

        //手动draw
//        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
////使用Canvas，调用自定义view控件的onDraw方法，绘制图片
//        Canvas canvas = new Canvas(bitmap);
//        view.draw(canvas);
        return bitmap;
    }

    //</editor-fold>

    //<editor-fold desc="应用外截屏 ">

    private static final int REQUEST_MEDIA_PROJECTION = 1024;

    /**
     * app外截图，需要配合activity的onActivityResult使用
     *
     * @param activity
     */
    public static void takeScreenShot(Activity activity) {
        MediaProjectionManager mMediaProjectionManager = (MediaProjectionManager)
                activity.getApplication().getSystemService(Context.MEDIA_PROJECTION_SERVICE);

        activity.startActivityForResult(mMediaProjectionManager.createScreenCaptureIntent(), REQUEST_MEDIA_PROJECTION);
    }

    /**
     * 处理截图 activity返回的result
     *
     * @param activity
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public static void handlerScreenShotCallback(Activity activity, int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_MEDIA_PROJECTION) {
            MediaProjectionManager mMediaProjectionManager = (MediaProjectionManager)
                    activity.getApplication().getSystemService(Context.MEDIA_PROJECTION_SERVICE);

            MediaProjection mMediaProjection = mMediaProjectionManager.getMediaProjection(resultCode, data);


        }

    }


    //</editor-fold>
}
