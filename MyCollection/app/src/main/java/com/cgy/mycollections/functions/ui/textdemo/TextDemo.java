package com.cgy.mycollections.functions.ui.textdemo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cgy.mycollections.R;
import com.cgy.mycollections.widgets.WaveView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 1.该页面展示了iconFont  点击圆形效果  和波纹动画
 * 2.svg作为background的使用。svg在5.x可以直接使用，5.0以下 如4.x直接使用可能会报错，需要适配一下，这里懒得适配
 * Google在Android 5.X中提供了两个新API来帮助支持SVG:
 * VectorDrawable
 * AnimatedVectorDrawable
 */
public class TextDemo extends AppCompatActivity {
    @BindView(R.id.wave_view)
    WaveView mWaveView;
    @BindView(R.id.tv_unlock)
    View mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_demo);
        ButterKnife.bind(this);

        setUpWave();
    }

    @OnClick({R.id.tv_unlock, R.id.edit_demo})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_demo:
                startActivity(new Intent(this,EditTextActivity.class));
                break;
            case R.id.tv_unlock:
                mWaveView.start();
                mWaveView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mWaveView.stop();
                    }
                }, 5000);
                break;
            default:
                break;
        }

    }

    public void setUpWave() {
        mWaveView.setSpeed(700);
        mWaveView.setOneWaveDuration(5000);
        mWaveView.setMaxRadiusRate(2);
        mWaveView.setStyle(Paint.Style.STROKE);//画圆环
        mWaveView.setInitialRadius(120);//字体dp大小
        mWaveView.setColor(Color.WHITE);
        mWaveView.setInterpolator(new LinearOutSlowInInterpolator());
    }
}
