package com.cgy.mycollections.utils;

import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

/**
 * Description :系统画面 statusBar,navigationBar等系统的ui 控制
 * https://www.jb51.net/article/111936.htm
 * Author :cgy
 * Date :2020/1/21
 */
public class SystemUiUtils {
//    View.SYSTEM_UI_FLAG_FULLSCREEN, //全屏，状态栏和导航栏不显示
//    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION, //隐藏导航栏
//    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN, //全屏，状态栏会盖在布局上
//    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION,
//    View.SYSTEM_UI_FLAG_LAYOUT_STABLE,
//    View.SYSTEM_UI_FLAG_LOW_PROFILE,
//    View.SYSTEM_UI_FLAG_VISIBLE, //显示状态栏和导航栏
//    View.SYSTEM_UI_LAYOUT_FLAGS

    /**
     * 获取statusBar高度
     * 需要注意 在activity#onCreate中调用结果是0，因为此时view还没有测量绘制
     *
     * @param window
     * @return
     */
    public static int getStatusBarHeight(@NonNull Window window) {
        Rect rect = new Rect();
        /*
         * getWindow().getDecorView()得到的View是Window中的最顶层View，可以从Window中获取到该View，
         * 然后该View有个getWindowVisibleDisplayFrame()方法可以获取到程序显示的区域，
         * 包括标题栏，但不包括状态栏。
         */
        window.getDecorView().getWindowVisibleDisplayFrame(rect);

//        1.获取状态栏高度：
//        根据上面所述，我们可以通过rect对象得到手机状态栏的高度
        return rect.top;
    }

    /**
     * 获取titleBar高度
     * 需要注意 在activity#onCreate中调用结果是0，因为此时view还没有测量绘制
     *
     * @param window
     * @return
     */
    public static int getTitleBarHeight(@NonNull Window window) {
        Rect rect = new Rect();
        /*
         * getWindow().getDecorView()得到的View是Window中的最顶层View，可以从Window中获取到该View，
         * 然后该View有个getWindowVisibleDisplayFrame()方法可以获取到程序显示的区域，
         * 包括标题栏，但不包括状态栏。
         */
        window.getDecorView().getWindowVisibleDisplayFrame(rect);

//        1.获取状态栏高度：
//        根据上面所述，我们可以通过rect对象得到手机状态栏的高度
        int statusBarHeight = rect.top;

//        2.获取标题栏高度：
//        该方法获取到的View是程序不包括标题栏的部分，这样我们就可以计算出标题栏的高度了。
        int contentTop = window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
        //statusBarHeight是上面所求的状态栏的高度
        return contentTop - statusBarHeight;
    }

    /**
     * 设置窗口status和navigation 透明,此时 view配置 fitSystemWindows=true 可以沉浸式全屏
     * 不过此时statusBar字体图标都是白色，看情况配合setStatusLight使用。
     * 设置这个的时候会自动全屏，此时搭配fitsSystemWindows=true来使用可以实现沉浸式
     *
     * @param window
     */
    public static void setWindowTranslucentStatusAndNavigation(Window window) {
        //        NOTICE:需要沉浸的view要搭配 android:fitsSystemWindows="true"来使用，不然  view的内容会占用statusBar的位置，导致重叠
        //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4-6.0的状态栏会是  白字，看不到字
//            以下功能也可以通过在appTheme中添加<item name="android:windowIsTranslucent">true</item> 来实现

            //透明状态栏
            //When this flag is enabled for a window, it automatically sets
            //the system UI visibility flags {@link View.SYSTEM_UI_FLAG_LAYOUT_STABLE} and
            //{@link View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN}
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//去除全屏的标记，温和方法。
            //透明导航栏
            //When this flag is enabled for a window, it automatically sets
            //the system UI visibility flags {@link View#SYSTEM_UI_FLAG_LAYOUT_STABLE} and
            //{@link View#SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION}
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);


            //退出全屏
//            window.setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
        }
    }

    /**
     * 设置 statusBar 为light模式 ， 此时statusBar中的 字图标颜色都是黑色，
     * 配合setWindowTranslucentStatusAndNavigation在 statusBar透明的时候使用
     *
     * @param view 使用getWindow().getDecorView() 和任何其中的view效果一样
     */
    public static void setStatusLight(View view) {
        //6.0以上的 亮色状态栏模式，可以把状态栏字体变成 黑字
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    /**
     * 模糊statusBar和navigationBar
     *
     * @param view
     */
    public static void blurSystemUi(View view) {
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LOW_PROFILE);
    }

    /**
     * 隐藏statusBar和navigationBar
     *
     * @param view
     */
    public static void hideSystemUi(View view) {
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE //不设置这个 会导致任何用户行为 强行清除SYSTEM_UI_FLAG_HIDE_NAVIGATION并显示navigationBar
                | View.SYSTEM_UI_FLAG_FULLSCREEN//非全屏切换全屏的时候 会导致view 上下晃动一下,暂时不知道怎么解决
        );
    }

    /**
     * 显示 statusBar 和navigationBar
     *
     * @param view
     */
    public static void showSystemUi(View view) {
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        );
    }
}
