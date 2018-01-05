package com.cgy.mycollections.functions.systemui.statusbar;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.cgy.mycollections.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * 使用第三方库 来管理 状态栏，本质和DynamicStatusActivity的方式差不多，
 * 此处是在SystemBarTintManager中通过decorView添加一个头部的view,覆盖在顶部statusBar的位置
 * 该方法比DynamicStatusActivity方便一点的优势在于 不需要在布局中添加一个statusbar占位的view
 */
public class ThirdLibStatusActivity extends AppCompatActivity {
    SystemBarTintManager tintManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_lib_status);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

            tintManager = new SystemBarTintManager(this);
            // 激活状态栏
            tintManager.setStatusBarTintEnabled(true);
            // enable navigation bar tint 激活导航栏
            tintManager.setNavigationBarTintEnabled(true);
            //设置系统栏设置颜色
            //tintManager.setTintColor(R.color.red);
            //给状态栏设置颜色
            tintManager.setStatusBarTintResource(android.R.color.transparent);
            //Apply the specified drawable or color resource to the system navigation bar.
            //给导航栏设置资源
            tintManager.setNavigationBarTintResource(android.R.color.transparent);
        }
    }

    boolean on = true;

    public void onClick(View v) {
        if (on) {
            //给状态栏设置颜色
            tintManager.setStatusBarTintResource(R.color.colorPrimary);
            //Apply the specified drawable or color resource to the system navigation bar.
            //给导航栏设置资源
            tintManager.setNavigationBarTintResource(R.color.colorPrimary);
            on = false;
        } else {
            //给状态栏设置颜色
            tintManager.setStatusBarTintResource(R.color.colorAccent);
            //Apply the specified drawable or color resource to the system navigation bar.
            //给导航栏设置资源
            tintManager.setNavigationBarTintResource(R.color.colorAccent);
            on = true;
        }
    }
}
