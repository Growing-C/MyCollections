package com.cgy.mycollections.functions.ui.systemui.statusbar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.cgy.mycollections.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 系统的方式沉浸式状态栏实现,该方式 会导致状态栏为透明，不会显示colorPrimaryDark的颜色
 * 启用沉浸式状态栏步骤：
 * 第一个view是一般的view（不是toolbar）
 * 1.设置状态栏为透明
 * 2.view中添加fitSystemWindows="true"
 * <p>
 * toolbar（特殊）
 * 1.设置高度为wrap_content.(也可以设置为具体高度，但是似乎显示高度小于设置的高度，猜测可能算上了状态栏)
 * 2.Toolbar中添加 fitSystemWindows="true"(若外层嵌套了AppBarLayout 也要放在toolbar中)
 * 3.设置状态栏为透明（不能把底部导航栏的导航栏设为透明，会导致wrap_content状态的Toolbar 高度变大，
 * 具体大小的toolbar 文字消失，猜测是因为高度也变大了,padding导致文字进入不可见区域了）
 * <p>
 * toolbar的种种问题有一个解决办法
 * 使用纯色actionbar的时候，可以使用容器包裹toolbar，只设置容器 fitsSystemWindows为true。
 * 由于fitsSystemWindows属性本质上是给当前控件设置了一个padding，所以我们设置到根布局的话，会导致状态栏是透明的，
 * 并且和窗口背景一样，和toolbar背景不同。如果我们设置给toolbar，则会由于padding的存在，导致toolbar的内容下移。
 * 所以我们选择使用LinearLayout包裹toolbar，并将toolbar的背景等属性设置在appbarlayout上就可以完美实现效果
 */
public class SystemStatusActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_status);
        ButterKnife.bind(this);

        //直接用其他的view 可以  不知道为什么 toolbar沉浸式一直有问题
        setSupportActionBar(mToolbar);
//        NOTICE:需要沉浸的view要搭配 android:fitsSystemWindows="true"来使用，不然  view的内容会占用statusBar的位置，导致重叠
        //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4-6.0的状态栏会是  白字，看不到字
//            以下功能也可以通过在appTheme中添加<item name="android:windowIsTranslucent">true</item> 来实现

            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏（在使用toolbar，且其高度为wrap_content时 下面的属性会导致toolbar高度异常）
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        //6.0以上的 亮色状态栏模式，可以把状态栏字体变成 黑字
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
//        1. 在 Android 6.0 以前，Android 没有方法可以实现「状态栏黑色字符」效果，因此 MIUI 自己做了一个接口；
//        2. 在 Android 6.0 及以上版本，Android 提供了标准的方法实现「状态栏黑色字符」效果，但这个方法和 MIUI 的方法产生了冲突，所以当开发者使用 Android 标准方法时，没有出现预期的效果，这给很多开发者都造成了困扰，尤其是海外开发者
    }


    /**
     * 修改状态栏为全透明
     *
     * @param activity
     */
    @TargetApi(19)
    public static void transparencyBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 修改状态栏颜色，支持4.4以上版本
     *
     * @param activity
     * @param colorId
     */
    public static void setStatusBarColor(Activity activity, int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
//      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(colorId));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //使用SystemBarTint库使4.4版本状态栏变色，需要先将状态栏设置为透明
            transparencyBar(activity);
            SystemBarTintManager tintManager = new SystemBarTintManager(activity);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(colorId);
        }
    }

    /**
     * 状态栏亮色模式，设置状态栏黑色文字、图标，
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     *
     * @return 1:MIUUI 2:Flyme 3:android6.0
     */
    public void StatusBarLightMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }

    /**
     * 状态栏暗色模式，清除MIUI、flyme或6.0以上版本状态栏黑色文字、图标
     */
    public void StatusBarDarkMode() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
    }
}
