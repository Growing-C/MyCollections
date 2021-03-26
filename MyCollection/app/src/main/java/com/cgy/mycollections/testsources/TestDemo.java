package com.cgy.mycollections.testsources;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.cgy.mycollections.R;
import com.cgy.mycollections.utils.SystemPageUtils;
import com.cgy.mycollections.utils.SystemUiUtils;

import appframe.utils.L;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TestDemo extends AppCompatActivity {
    //    @BindView(R.id.toolbar)
//    Toolbar toolbar;
    @BindView(R.id.root)
    View mContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);//butterKnife和dataBinding并不冲突
//        setSupportActionBar(toolbar);

        //设置view大小扩展到navigationBar底下，这样隐藏导航栏时显示的view的大小就不会变化
//        SystemUiUtils.setScreenUnderNavBar(mContentView);

        //view占据全屏，但是依然能看到status 和导航栏，只是背景透明
//        getWindow().setNavigationBarColor(Color.TRANSPARENT);
//        getWindow().setStatusBarColor(Color.TRANSPARENT);

        //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
        SystemUiUtils.setWindowTranslucentStatusAndNavigation(getWindow());

        //6.0以上的 亮色状态栏模式，可以把状态栏字体变成 黑字
        SystemUiUtils.setStatusLight(mContentView);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.navigate) {
            SystemPageUtils.openLocationSettings(this);
        } else if (v.getId() == R.id.quit_full) {
            SystemUiUtils.showStatusAndNavBarExitImmersive(mContentView);
        } else if (v.getId() == R.id.lean_back) {
            SystemUiUtils.enableLeanbackMode(mContentView);
        } else if (v.getId() == R.id.immerse) {
            SystemUiUtils.enableImmersiveMode(mContentView);
        } else if (v.getId() == R.id.sticky_immerse) {
            SystemUiUtils.enableImmersiveStickyMode(mContentView);
        } else if (v.getId() == R.id.hide_status) {
            SystemUiUtils.hideStatusBarOnly(mContentView);
        } else if (v.getId() == R.id.hide_navigation) {//快速点击时系统会出bug， 显示了透明的导航栏
            SystemUiUtils.hideNavigationBarOnly(mContentView);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);//真正的沉浸式,隐藏status和navigation 可以下滑拉出但是一段时间后自动隐藏
        L.e("test", "onWindowFocusChanged hasFocus:" + hasFocus);
        if (hasFocus) {
//            hideSystemUI();
        }
    }
}
