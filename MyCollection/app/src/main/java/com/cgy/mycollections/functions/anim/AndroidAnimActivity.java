package com.cgy.mycollections.functions.anim;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.cgy.mycollections.R;
import com.cgy.mycollections.widgets.LockMenuView;

import appframe.utils.DisplayHelperUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

import static com.cgy.mycollections.widgets.LockMenuView.LOCK_MODE_BLE_FAST;

/**
 */
public class AndroidAnimActivity extends AppCompatActivity {

    //<editor-fold desc="卡片翻转参数">
    //---------卡片翻转动画-----------
    @BindView(R.id.card_content)
    View mCardContent;//卡正面
    @BindView(R.id.card_background)
    View mCardBackground;//卡背面
    @BindView(R.id.card_container)
    View mCardContainer;//正反面容器

    AnimatorSet mLeft2Right1Set, mLeft2Right2Set, mRight2Left1Set, mRight2Left2Set;

    int mCardCenterX;

    boolean isCardShowFront = true;//卡片是否显示正面
    //--------------------------
    //</editor-fold>

    //<editor-fold desc="菜单参数">
    @BindView(R.id.lock_menu)
    LockMenuView mMenu;//属性动画

    //</editor-fold>

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_anim);
        ButterKnife.bind(this);

        mCardCenterX = DisplayHelperUtils.getScreenWidth() / 2;

        setAnimators();
        setCameraDistance();

        mMenu.selectMenu(LOCK_MODE_BLE_FAST, false);
    }



    //<editor-fold desc="卡片翻转">

    @OnTouch({R.id.card_container})
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
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
    }

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
                mCardContainer.setEnabled(false);//防止多次触摸动画异常
            }
        };
        AnimatorListenerAdapter secondHalfListener = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mCardContainer.setEnabled(true);
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
        mCardContent.setCameraDistance(scale);
        mCardBackground.setCameraDistance(scale);
    }

    // 翻转卡片
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

        mCardBackground.setVisibility(View.VISIBLE);
        mCardContent.setVisibility(View.VISIBLE);
        if (isCardShowFront) {//原来是正面就翻转
            firstHalf.setTarget(mCardContent);
            secondHalf.setTarget(mCardBackground);
            firstHalf.start();
            secondHalf.start();
            isCardShowFront = false;
        } else { // 背面朝上
            firstHalf.setTarget(mCardBackground);
            secondHalf.setTarget(mCardContent);
            firstHalf.start();
            secondHalf.start();
            isCardShowFront = true;
        }
    }

    //</editor-fold>
}
