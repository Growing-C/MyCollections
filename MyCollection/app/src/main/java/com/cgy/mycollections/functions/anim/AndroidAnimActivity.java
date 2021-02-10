package com.cgy.mycollections.functions.anim;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

import com.cgy.mycollections.R;
import com.cgy.mycollections.databinding.ActivityAndroidAnimBinding;
import com.cgy.mycollections.functions.anim.animator.AnimatorType;
import com.cgy.mycollections.functions.anim.animator.BaseViewAnimator;

import appframe.utils.DisplayHelperUtils;
import appframe.utils.L;

import static com.cgy.mycollections.widgets.LockMenuView.LOCK_MODE_BLE_FAST;

/**
 *
 */
public class AndroidAnimActivity extends AppCompatActivity {

    //<editor-fold desc="卡片翻转参数">
    //---------卡片翻转动画-----------

    ActivityAndroidAnimBinding mBinding;

    AnimatorSet mLeft2Right1Set, mLeft2Right2Set, mRight2Left1Set, mRight2Left2Set;

    int mCardCenterX;

    boolean isCardShowFront = true;//卡片是否显示正面
    //--------------------------
    //</editor-fold>

    //<editor-fold desc="菜单参数">
//    @BindView(R.id.lock_menu)
//    LockMenuView mMenu;//属性动画

    //</editor-fold>

    //    AnimatorSet mSportModeAnimator;
    BaseViewAnimator mSportModeAnimator;
    AnimatorSet mBackgroundAnimator;

    int mBatteryState = 0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAndroidAnimBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mCardCenterX = DisplayHelperUtils.getScreenWidth() / 2;
        setAnimators();
        setCameraDistance();

        mBinding.lockMenu.selectMenu(LOCK_MODE_BLE_FAST, false);

        mBinding.cardContainer.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {//属性动画
                case MotionEvent.ACTION_DOWN:

                    break;
                case MotionEvent.ACTION_UP:
                    float x = event.getX();
                    float y = event.getY();

                    System.out.println(event.getAction() + "-->x:" + x + "        y:" + y);
                    System.out.println("-->mCardCenterX:" + mCardCenterX);
                    if (x <= mCardCenterX) {
                        System.out.println("left side");
                        flipCard(false);
                    } else {
                        System.out.println("right side");
                        flipCard(true);
                    }
                    break;
                default:
                    break;
            }
            return true;
        });


        //水平和竖直动画
        mBinding.ivDrivingModeHint.setOnClickListener(v -> {
            playDrivingModeAnim();
        });

        mBinding.battery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int bat = mBatteryState % 5;
                if (bat == 0) {
                    mBinding.battery.setBatteryLevel(0);
                } else if (bat == 1) {
                    mBinding.battery.setBatteryLevel(40);
                } else if (bat == 2) {
                    mBinding.battery.setBatteryLevel(80);
                } else if (bat == 3) {
                    mBinding.battery.startCharging();
                } else {
                    mBinding.battery.stopCharging();
                }
                mBatteryState++;
            }
        });
    }

    private void playDrivingModeAnim() {
        if (mSportModeAnimator == null) {
            mSportModeAnimator = AnimatorType.FadeIn.newAnimator(mBinding.ivSportMode);
            mSportModeAnimator.setTarget(mBinding.ivSportMode);
        }
        if (mBackgroundAnimator == null) {
            mBackgroundAnimator = new AnimatorSet();

//            mBackgroundAnimator.playTogether(
//                    ObjectAnimator.ofFloat(mBinding.ivDrivingModeHint, AnimatorImageView.SHOW_RECT_X, 0, mBinding.ivDrivingModeHint.getWidth())
//            );

            int startY = mBinding.ivDrivingModeHint.getHeight() + mBinding.ivDrivingModeHint.getTop();
            mBackgroundAnimator.playTogether(
                    ObjectAnimator.ofFloat(mBinding.ivDrivingModeHint, "alpha", 0, 1),
                    ObjectAnimator.ofFloat(mBinding.ivDrivingModeHint, "translationY", startY, 0)
            );
            mBackgroundAnimator.setInterpolator(new DecelerateInterpolator());
            mBackgroundAnimator.setDuration(600);

            mBackgroundAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    L.i("mBackgroundAnimator start~~");
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    L.i("mBackgroundAnimator end~~");
                }
            });
        }

        mBackgroundAnimator.start();
        mSportModeAnimator.play();
    }


    //<editor-fold desc="卡片翻转">

    // 设置动画
    private void setAnimators() {
        mLeft2Right1Set = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.set_left_2_right_first_half);
        mLeft2Right2Set = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.set_left_2_right_second_half);

        mRight2Left1Set = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.set_right_2_left_first_half);
        mRight2Left2Set = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.set_right_2_left_second_half);

        AnimatorListenerAdapter firstHalfListener = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mBinding.cardContainer.setEnabled(false);//防止多次触摸动画异常
            }
        };
        AnimatorListenerAdapter secondHalfListener = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mBinding.cardContainer.setEnabled(true);
            }
        };

        //设置动画监听
        mLeft2Right1Set.addListener(firstHalfListener);
        mLeft2Right2Set.addListener(secondHalfListener);

        mRight2Left1Set.addListener(firstHalfListener);
        mRight2Left2Set.addListener(secondHalfListener);
    }

    // 改变视角距离, 贴近屏幕
    private void setCameraDistance() {
        int distance = 16000;
        float scale = getResources().getDisplayMetrics().density * distance;
        mBinding.cardContent.setCameraDistance(scale);
        mBinding.cardBackground.setCameraDistance(scale);
    }

    // 翻转卡片 属性动画
    public void flipCard(boolean isFromLeftSide) {
        AnimatorSet firstHalf;
        AnimatorSet secondHalf;

        if (isFromLeftSide) {
            firstHalf = mLeft2Right1Set;
            secondHalf = mLeft2Right2Set;
        } else {
            firstHalf = mRight2Left1Set;
            secondHalf = mRight2Left2Set;
        }

        mBinding.cardBackground.setVisibility(View.VISIBLE);
        mBinding.cardContent.setVisibility(View.VISIBLE);
        if (isCardShowFront) {//原来是正面就翻转
            firstHalf.setTarget(mBinding.cardContent);
            secondHalf.setTarget(mBinding.cardBackground);
            firstHalf.start();
            secondHalf.start();
            isCardShowFront = false;
        } else { // 背面朝上
            firstHalf.setTarget(mBinding.cardBackground);
            secondHalf.setTarget(mBinding.cardContent);
            firstHalf.start();
            secondHalf.start();
            isCardShowFront = true;
        }
    }

    //</editor-fold>
}
