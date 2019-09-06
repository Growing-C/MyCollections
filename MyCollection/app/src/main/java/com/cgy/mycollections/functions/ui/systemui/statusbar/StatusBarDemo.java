package com.cgy.mycollections.functions.ui.systemui.statusbar;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cgy.mycollections.R;

/**
 * Google从android kitkat(Android 4.4)开始,给我们开发者提供了一套能透明的系统ui样式给状态栏和导航栏，
 * 这样的话就不用向以前那样每天面对着黑乎乎的上下两条黑栏了，还可以调成跟Activity一样的样式，
 * 形成一个完整的主题,和IOS7.0以上系统一样了。
 */
public class StatusBarDemo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_bar_demo);
    }

    public void onClick(View v) {//实现沉浸式状态栏的三种方式
        switch (v.getId()) {
            case R.id.system_method://系统实现沉浸式状态栏
                startActivity(new Intent(this, SystemStatusActivity.class));
                break;
            case R.id.dynamic_method://动态添加布局实现沉浸式状态栏
                startActivity(new Intent(this, DynamicStatusActivity.class));
                break;
            case R.id.third_lib_method://使用第三方依赖库实现沉浸式状态栏
                startActivity(new Intent(this, ThirdLibStatusActivity.class));
                break;
            default:
                break;
        }
    }
}
