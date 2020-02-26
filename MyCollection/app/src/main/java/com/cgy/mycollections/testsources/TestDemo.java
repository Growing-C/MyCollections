package com.cgy.mycollections.testsources;

import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

        //全屏，设置了这个之后调用changeFullScreen 不会抖动
//        View decorView = getWindow().getDecorView();
//        int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
//        decorView.setSystemUiVisibility(option);


        View decorView = getWindow().getDecorView();
        //透明状态栏
//        if (Build.VERSION.SDK_INT >= 21) {
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }

        //全屏且隐藏导航栏,不过屏幕碰一下就显示了status和导航栏
//        int option = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_FULLSCREEN;
//        decorView.setSystemUiVisibility(option);

        //view占据全屏，但是依然能看到status 和导航栏，只是背景透明
//        这里使用了SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION，表示会让应用的主体内容占用系统导航栏的空间，然后又调用了setNavigationBarColor()方法将导航栏设置成透明色。
        int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(option);
        getWindow().setNavigationBarColor(Color.TRANSPARENT);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);//真正的沉浸式
        L.e("test", "onWindowFocusChanged hasFocus:" + hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.navigate:
                SystemPageUtils.openLocationSettings(this);
                break;
            case R.id.fullscreen:
//                SystemUiUtils.showSystemUi(mContentView);
                hideStatus();
//                toggle(true);
                changeFullScreen(true);
                break;
            case R.id.quit_full:
//                SystemUiUtils.blurSystemUi(mContentView);
//                hideNavigation();
//                toggle(false);
                changeFullScreen(false);
                break;
            default:
                break;
        }
    }

    void hideStatus() {
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    void hideNavigation() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    private void changeFullScreen(boolean isFullScreen) {
        if (!isFullScreen) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN); //非全屏
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN); //清除非全屏的flag
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //设置全屏的flag
        }

//        if (isFullScreen) {
//            WindowManager.LayoutParams params = getWindow().getAttributes();
//            params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
//            getWindow().setAttributes(params);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        } else {
//            WindowManager.LayoutParams params = getWindow().getAttributes();
//            params.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            getWindow().setAttributes(params);
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        }
    }

    /**
     * 依然会闪一下
     *
     * @param mVisible
     */
    private void toggle(boolean mVisible) {
        if (mVisible) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LOW_PROFILE
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        } else {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            );
        }
    }

    void setNavVisibility(boolean visible) {
        int newVis = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE;
        if (!visible) {
            newVis |= View.SYSTEM_UI_FLAG_LOW_PROFILE | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE;
        }
        // If we are now visible, schedule a timer for us to go invisible.
        // Set the new desired visibility.
        mContentView.setSystemUiVisibility(newVis);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}
