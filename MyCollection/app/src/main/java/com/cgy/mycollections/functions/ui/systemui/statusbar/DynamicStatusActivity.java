package com.cgy.mycollections.functions.ui.systemui.statusbar;

import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.cgy.mycollections.R;

import java.lang.reflect.Field;

/**
 * 实现思路，添加隐藏布局，然后我们动态的计算状态栏的高度，然后把这个高度设置成这个隐藏的布局的高度，便可以实现
 * 在这里我们通过反射来获取状态栏的高度，
 *
 * <p></p>该方式的优势在于可以定制statusBar的颜色
 */
public class DynamicStatusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_status);
        initState();
    }

    /**
     * 动态的设置状态栏  实现沉浸式状态栏
     */
    private void initState() {

        //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            //
            View bar = findViewById(R.id.bar);
            bar.setVisibility(View.VISIBLE);
            //获取到状态栏的高度
            int statusHeight = getStatusBarHeight();
            System.out.println("statusHeight:" + statusHeight);
            //动态的设置隐藏布局的高度
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) bar.getLayoutParams();
            params.height = statusHeight;
            bar.setLayoutParams(params);
        }
    }


    /**
     * 通过反射的方式获取状态栏高度
     *
     * @return
     */
    private int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
