package com.cgy.mycollections.functions.anim;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cgy.mycollections.R;
import com.cgy.mycollections.widgets.WaveView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
        ButterKnife.bind(this);

    }



}
