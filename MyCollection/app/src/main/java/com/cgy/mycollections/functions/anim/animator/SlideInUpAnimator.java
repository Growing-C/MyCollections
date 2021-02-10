package com.cgy.mycollections.functions.anim.animator;

import android.animation.ObjectAnimator;
import android.view.View;

/**
 * @author yangwq
 * @date 2021-2-2 16:28
 */
public class SlideInUpAnimator extends BaseViewAnimator<View> {
    private static final String TAG = SlideInUpAnimator.class.getSimpleName();

    private static final int DEFAULT_DISTANCE = 600;

    @Override
    public void prepareAnim(View target) {
        int distance = target.getHeight() + target.getTop();
        if (distance == 0) {
            distance = DEFAULT_DISTANCE;
        }
        getAnimatorSet().playTogether(
                ObjectAnimator.ofFloat(target, "alpha", 0, 1),
                ObjectAnimator.ofFloat(target, "translationY", distance, 0)
        );
    }
}
