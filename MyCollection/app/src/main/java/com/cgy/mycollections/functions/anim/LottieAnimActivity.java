package com.cgy.mycollections.functions.anim;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;
import com.cgy.mycollections.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 1.劣势
 *
 * （1）性能不够好—某些动画特效，内存和性能不够好；相对于属性动画，在展示大动画时，帧率较低
 *
 * 2.优势
 *
 * （1）开发效率高—代码实现简单，更换动画方便，易于调试和维护。
 *
 * （2）数据源多样性—可从assets,sdcard,网络加载动画资源，能做到不发版本，动态更新
 *
 * （3）跨平台—设计稿导出一份动画描述文件，android,ios,react native通用
 */
public class LottieAnimActivity extends AppCompatActivity {

    @BindView(R.id.animation_view)
    LottieAnimationView mAnimationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottie_anim);
        ButterKnife.bind(this);
    }
}
