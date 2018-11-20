package com.cgy.mycollections.functions.anim;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cgy.mycollections.R;

/**
 * 该页面展示了动画效果
 * 包含多种动画
 * 1.airbnb/lottie-android 库动画
 */
public class AnimDemo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_demo);
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lottie:
                startActivity(new Intent(AnimDemo.this, LottieAnimActivity.class));
                break;
            case R.id.android_anim:
                startActivity(new Intent(AnimDemo.this, AndroidAnimActivity.class));
                break;
            default:
                break;
        }
    }

}
