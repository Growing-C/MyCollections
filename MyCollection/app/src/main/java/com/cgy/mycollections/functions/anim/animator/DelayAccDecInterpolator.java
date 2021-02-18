package com.cgy.mycollections.functions.anim.animator;

import android.view.animation.Interpolator;

/**
 * author: chengy
 * created on: 2021-2-18 14:06
 * description:由于AnimatorSet循环播放时无法设置间隔，使用此类作为可以延迟的动画的interpolator,
 * 基础是 AccelerateDecelerateInterpolator
 * 在这个基础上根据总时间和延迟时间将返回值进行偏移。totalTime这段时间播放完整动画，delayTime这段时间返回值都会是1
 */
public class DelayAccDecInterpolator implements Interpolator {
    float mRealEndInput;//input范围是0-1，这个值是根据总时间，延迟时间计算出来的实际结束input值

    public DelayAccDecInterpolator(float totalTime, float delayTime) {
        mRealEndInput = (totalTime - delayTime) / totalTime;
        if (mRealEndInput <= 0 || mRealEndInput > 1) {
            throw new IllegalArgumentException("totalTime or delayTime is invalid!");
        }
    }

    @Override
    public float getInterpolation(float input) {
        float actualInput = input / mRealEndInput;
        if (actualInput > 1) {
            actualInput = 1;
        }

        return (float) (Math.cos((actualInput + 1) * Math.PI) / 2.0f) + 0.5f;
    }
}
