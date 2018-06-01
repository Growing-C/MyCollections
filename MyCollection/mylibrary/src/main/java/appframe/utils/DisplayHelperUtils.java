package appframe.utils;

import android.content.Context;
import android.util.DisplayMetrics;

public class DisplayHelperUtils {
    public static DisplayMetrics metrics;

    /**
     * App启动时初始化
     */
    public static void init(Context context) {
        metrics = context.getResources().getDisplayMetrics();
        LogUtils.d(
                "w: " + getScreenWidth() + " -- h: " + getScreenHeight() + " -- density: " + getDensity() + " --densityDpi:" + metrics.densityDpi);
    }

//    /**
//     * 获得设备的高度的物理尺寸（单位：英寸）
//     * @param activity
//     */
//    public static void getScreenYOfDevice(Activity activity) {
//        try {
//            Point point = new Point();
//            activity.getWindowManager().getDefaultDisplay().getRealSize(point);
//            double y = point.y / metrics.ydpi;
//            LogFormat.d("screen Y inches:" + y);
//        } catch (Exception e) {
//
//        }
//    }

    public static float getDensity() {
        return metrics.density;
    }

    /**
     * 屏幕密度DPI
     */
    public static int getDensityDpi() {
        return metrics.densityDpi;
    }

    /**
     * [简要描述]: 获取屏幕宽度（像素值） [详细描述]:
     */
    public static int getScreenWidth() {
        return metrics.widthPixels;
    }

    /**
     * [简要描述]: 获取屏幕宽度（Dip值） [详细描述]:
     */
    public static float getWidthDip() {
        return (getScreenWidth() / getDensity());
    }

    /**
     * [简要描述]: 获取屏幕高度（像素值） [详细描述]:
     */
    public static int getScreenHeight() {
        return metrics.heightPixels;
    }

    /**
     * [简要描述]: 获取屏幕高度（Dip值） [详细描述]:
     */
    public static float getHeightDip() {
        return (getScreenHeight() / getDensity());
    }

    public static int dip2px(float dipValue) {
        final float scale = metrics.density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(float pxValue) {
        final float scale = metrics.density;
        return (int) (pxValue / scale + 0.5f);
    }
}
