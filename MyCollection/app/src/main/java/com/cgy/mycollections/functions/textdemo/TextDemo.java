package com.cgy.mycollections.functions.textdemo;

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

    @OnClick({R.id.tv_unlock})
    public void onClick(View v) {
        mWaveView.start();
        mWaveView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mWaveView.stop();
            }
        }, 5000);

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
