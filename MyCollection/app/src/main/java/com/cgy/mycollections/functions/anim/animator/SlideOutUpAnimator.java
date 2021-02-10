package com.cgy.mycollections.functions.anim.animator;

import android.animation.ObjectAnimator;
import android.view.View;

/**
 * @author yangwq
 * @date 2021-2-2 16:29
 */
public class SlideOutUpAnimator extends BaseViewAnimator<View> {
    @Override
    public void prepareAnim(View target) {
        getAnimatorSet().playTogether(
                ObjectAnimator.ofFloat(target, "alpha", 1, 0),
                ObjectAnimator.ofFloat(target, "translationY", 0, target.getBottom())
        );
    }
}
