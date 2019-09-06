package com.cgy.mycollections.functions.anim;

import android.animation.Animator;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.cgy.mycollections.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 1.劣势
 * <p>
 * （1）性能不够好—某些动画特效，内存和性能不够好；相对于属性动画，在展示大动画时，帧率较低
 * <p>
 * 2.优势
 * <p>
 * （1）开发效率高—代码实现简单，更换动画方便，易于调试和维护。
 * <p>
 * （2）数据源多样性—可从assets,sdcard,网络加载动画资源，能做到不发版本，动态更新
 * <p>
 * （3）跨平台—设计稿导出一份动画描述文件，android,ios,react native通用
 */
public class LottieAnimActivity extends AppCompatActivity {

    @BindView(R.id.animation_view)
    LottieAnimationView mAnimationView;//lock

    @BindView(R.id.logo1)
    LottieAnimationView mLogo1;//l

    @BindView(R.id.logo2)
    LottieAnimationView mLogo2;//l

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottie_anim);
        ButterKnife.bind(this);
        mLogo1.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Toast.makeText(LottieAnimActivity.this, "animation cancel", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Toast.makeText(LottieAnimActivity.this, "onAnimationRepeat", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @OnClick({R.id.logo1, R.id.logo2 })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.logo1:
                if (!mLogo1.isAnimating()) {
                    mLogo1.setRepeatCount(2);
                    mLogo1.playAnimation();

                } else
                    mLogo1.cancelAnimation();
                break;
            case R.id.logo2:
                if (!mLogo2.isAnimating()) {
                    mLogo2.setRepeatCount(LottieDrawable.INFINITE);
                    mLogo2.playAnimation();
                } else
                    mLogo2.cancelAnimation();
                break;
            default:
                break;
        }
    }
}
