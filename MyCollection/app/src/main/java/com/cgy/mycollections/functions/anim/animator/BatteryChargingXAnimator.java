package com.cgy.mycollections.functions.anim.animator;

import android.animation.ObjectAnimator;

import com.cgy.mycollections.widgets.BatteryView;

public class BatteryChargingXAnimator extends BaseViewAnimator<BatteryView> {
    @Override
    public void prepareAnim(BatteryView target) {
        int distance = (int) (target.getWidth() + target.getX());
        getAnimatorSet().playTogether(
                ObjectAnimator.ofFloat(target, "chargingX", 0, distance)
        );
    }
}
