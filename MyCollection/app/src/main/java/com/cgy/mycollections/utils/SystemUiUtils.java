package com.cgy.mycollections.utils;

import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

/**
 * Description :系统画面 statusBar,navigationBar等系统的ui 控制
 * https://developer.android.com/training/system-ui/immersive?hl=zh-cn
 * Author :cgy
 * Date :2020/1/21
 */
public class SystemUiUtils {
//    View.SYSTEM_UI_FLAG_FULLSCREEN, //全屏，状态栏和导航栏不显示，但是这个会导致状态栏变成一个黑条
//    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION, //隐藏导航栏
//    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN, //全屏，状态栏会盖在布局上
//    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION, 隐藏导航栏
//    View.SYSTEM_UI_FLAG_LAYOUT_STABLE,
//    View.SYSTEM_UI_FLAG_LOW_PROFILE,
//    View.SYSTEM_UI_FLAG_VISIBLE, //显示状态栏和导航栏
//    View.SYSTEM_UI_LAYOUT_FLAGS

    /**
     * 获取statusBar高度
     * 需要注意 在activity#onCreate中调用结果是0，因为此时view还没有测量绘制
     *
     * @param window window
     * @return 高度
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
     * @param window window
     * @return 高度
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
     * 设置窗口status和navigation 透明,此时 view配置 fitSystemWindows=true 可以沉浸式全屏（不配置似乎也行）
     * 不过此时statusBar字体图标都是白色，看情况配合setStatusLight使用。
     * 设置这个的时候会自动全屏，此时搭配fitsSystemWindows=true来使用可以实现沉浸式
     *
     * @param window window
     * @apiNote 这个方式状态栏只是背景透明了，但是文字图标还看得到，但是又不能用View.SYSTEM_UI_FLAG_FULLSCREEN 因为会导致出现黑边。。咋整？
     */
    public static void setFullScreen(@NonNull Window window) {
        //        NOTICE:需要沉浸的view要搭配 android:fitsSystemWindows="true"来使用，不然  view的内容会占用statusBar的位置，导致重叠
        //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4-6.0的状态栏会是  白字，看不到字
//            以下功能也可以通过在appTheme中添加<item name="android:windowIsTranslucent">true</item> 来实现

        //透明状态栏
        //When this flag is enabled for a window, it automatically sets
        //the system UI visibility flags {@link View.SYSTEM_UI_FLAG_LAYOUT_STABLE} and
        //{@link View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN}
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//去除全屏的标记，温和方法。
        //透明导航栏（在使用toolbar，且其高度为wrap_content时 下面的属性会导致toolbar高度异常）
        //When this flag is enabled for a window, it automatically sets
        //the system UI visibility flags {@link View#SYSTEM_UI_FLAG_LAYOUT_STABLE} and
        //{@link View#SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION}
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        //退出全屏
//            window.setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
//        }
//        hideNavigationBarOnly(window.getDecorView());//如果不要显示透明的导航栏则调用这个
        darkSystemUi(window.getDecorView());
    }

    /**
     * 退出全屏沉浸模式
     *
     * @param window window
     */
    public static void quitFullScreen(@NonNull Window window) {
        //        NOTICE:需要沉浸的view要搭配 android:fitsSystemWindows="true"来使用，不然  view的内容会占用statusBar的位置，导致重叠
        //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4-6.0的状态栏会是  白字，看不到字
//            以下功能也可以通过在appTheme中添加<item name="android:windowIsTranslucent">true</item> 来实现

        //透明状态栏
        //When this flag is enabled for a window, it automatically sets
        //the system UI visibility flags {@link View.SYSTEM_UI_FLAG_LAYOUT_STABLE} and
        //{@link View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN}
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//去除全屏的标记，温和方法。
        //透明导航栏（在使用toolbar，且其高度为wrap_content时 下面的属性会导致toolbar高度异常）
        //When this flag is enabled for a window, it automatically sets
        //the system UI visibility flags {@link View#SYSTEM_UI_FLAG_LAYOUT_STABLE} and
        //{@link View#SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION}
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        //退出全屏
//            window.setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
//        }
//        showStatusAndNavBarExitImmersive(window.getDecorView());
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
            int uiOptions = view.getSystemUiVisibility();
            uiOptions |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            uiOptions |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            uiOptions |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            uiOptions |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(uiOptions);
        }
    }

    //----------------------------------------------------------------

    //<editor-folder desc="安卓官方demo做法">

    /**
     * 调暗状态栏和导航栏(没感觉啥区别)
     *
     * @param view 用于setSystemUiVisibility的view
     */
    public static void darkSystemUi(@NonNull View view) {
        int uiOptions = view.getSystemUiVisibility();
        uiOptions |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
        view.setSystemUiVisibility(uiOptions);
    }

    /**
     * 正常模式 显示状态栏，导航栏，退出沉浸模式
     *
     * @param view 用于setSystemUiVisibility的view
     */
    public static void showStatusAndNavBarExitImmersive(@NonNull View view) {
        int uiOptions = view.getSystemUiVisibility();
        uiOptions &= ~View.SYSTEM_UI_FLAG_LOW_PROFILE;
        uiOptions &= ~View.SYSTEM_UI_FLAG_FULLSCREEN;
        uiOptions &= ~View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        uiOptions &= ~View.SYSTEM_UI_FLAG_IMMERSIVE;
        uiOptions &= ~View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        view.setSystemUiVisibility(uiOptions);
    }


    /**
     * 隐藏状态栏(会重置 其他状态，仅仅隐藏状态栏)
     *
     * @param view 用于setSystemUiVisibility的view
     */
    public static void hideStatusBarOnly(@NonNull View view) {
        int uiOptions = view.getSystemUiVisibility();
        uiOptions &= ~View.SYSTEM_UI_FLAG_LOW_PROFILE;
        uiOptions |= View.SYSTEM_UI_FLAG_FULLSCREEN;//非全屏切换全屏的时候 会导致view 上下晃动一下,暂时不知道怎么解决
        uiOptions &= ~View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        uiOptions &= ~View.SYSTEM_UI_FLAG_IMMERSIVE;
        uiOptions &= ~View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        view.setSystemUiVisibility(uiOptions);
    }

    /**
     * 隐藏导航栏(会重置 其他状态，仅仅隐藏导航栏)
     *
     * @param view 用于setSystemUiVisibility的view
     */
    public static void hideNavigationBarOnly(@NonNull View view) {
        int uiOptions = view.getSystemUiVisibility();
        uiOptions &= ~View.SYSTEM_UI_FLAG_LOW_PROFILE;
        uiOptions &= ~View.SYSTEM_UI_FLAG_FULLSCREEN;
        uiOptions |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        uiOptions &= ~View.SYSTEM_UI_FLAG_IMMERSIVE;
        uiOptions &= ~View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        view.setSystemUiVisibility(uiOptions);
    }

    /**
     * 设置屏幕底部在 navigationBar的下面，这样显示隐藏navigationBar的时候就不会重新测量屏幕高度
     * <p>
     * Setting these flags makes the content appear under the navigation
     * bars, so that showing/hiding the nav bars doesn't resize the content
     * window, which can be jarring.
     *
     * @param view 用于setSystemUiVisibility的view
     */
    public static void setScreenUnderNavBar(@NonNull View view) {
        int uiOptions = view.getSystemUiVisibility();
        uiOptions |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;//相当于布局的时候当作无导航栏布局，实际会导致布局到导航栏下方
        uiOptions |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE;//必须有 ，否则其他两个无效
        uiOptions |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;//相当于布局的时候当作无状态栏布局
        view.setSystemUiVisibility(uiOptions);
    }

    /**
     * 启用向后倾斜模式。会做以下二件事
     * 1.隐藏顶部状态栏
     * 2.隐藏底部导航栏
     * 效果是全屏（但是顶部有黑边）
     * <p>
     * 此模式 用户上下滑动状态栏时会退出这个模式
     *
     * @param view 用于setSystemUiVisibility的view
     */
    public static void enableLeanbackMode(@NonNull View view) {
        int uiOptions = view.getSystemUiVisibility();
        uiOptions &= ~View.SYSTEM_UI_FLAG_LOW_PROFILE;
        uiOptions |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        uiOptions |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        uiOptions &= ~View.SYSTEM_UI_FLAG_IMMERSIVE;
        uiOptions &= ~View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        view.setSystemUiVisibility(uiOptions);
    }

    /**
     * 启用沉浸模式。会做以下三件事
     * 1.隐藏顶部状态栏
     * 2.隐藏底部导航栏
     * 3.启用沉浸模式
     * 效果是全屏（但是顶部有黑边）
     * <p>
     * 此模式 用户上下滑动状态栏时会复原
     *
     * @param view 用于setSystemUiVisibility的view
     * @apiNote 此模式并没有将view覆盖到statusbar中，不能算真正的沉浸
     */
    public static void enableImmersiveMode(@NonNull View view) {
        int uiOptions = view.getSystemUiVisibility();
        uiOptions &= ~View.SYSTEM_UI_FLAG_LOW_PROFILE;
        uiOptions |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        uiOptions |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        uiOptions |= View.SYSTEM_UI_FLAG_IMMERSIVE;
        uiOptions &= ~View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        view.setSystemUiVisibility(uiOptions);
    }


    /**
     * 启用粘性沉浸模式。会做以下三件事
     * 1.隐藏顶部状态栏
     * 2.隐藏底部导航栏
     * 3.启用沉浸模式
     * 效果是全屏（但是顶部有黑边）
     * <p>
     * 此模式 用户上下滑动状态栏时仅会显示透明栏，一段时间后还是这个模式
     *
     * @param view 用于setSystemUiVisibility的view
     * @apiNote 此模式并没有将view覆盖到statusbar中，不能算真正的沉浸
     */
    public static void enableImmersiveStickyMode(@NonNull View view) {
        int uiOptions = view.getSystemUiVisibility();
        uiOptions &= ~View.SYSTEM_UI_FLAG_LOW_PROFILE;
        uiOptions |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        uiOptions |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        uiOptions &= ~View.SYSTEM_UI_FLAG_IMMERSIVE;
        uiOptions |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        view.setSystemUiVisibility(uiOptions);
    }

    //</editor-folder>
}
