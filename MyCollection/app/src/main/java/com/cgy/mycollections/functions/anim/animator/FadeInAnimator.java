package com.cgy.mycollections.functions.anim.animator;

import android.animation.ObjectAnimator;
import android.view.View;

/**
 * author: chengy
 * created on: 2021-2-7 10:36
 * description: 渐现 动画
 */
public class FadeInAnimator extends BaseViewAnimator<View> {

    @Override
    public void prepareAnim(View target) {
        getAnimatorSet().playTogether(
                ObjectAnimator.ofFloat(target, "alpha", 0, 1));
    }
}