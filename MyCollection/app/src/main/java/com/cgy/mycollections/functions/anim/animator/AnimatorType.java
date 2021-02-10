package com.cgy.mycollections.functions.anim.animator;

import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import androidx.annotation.NonNull;

/**
 * @author yangwq
 * @date 2021-2-2 17:01
 */
public enum AnimatorType {

    FadeIn(FadeInAnimator.class, 600),
    SlideOutUp(SlideOutUpAnimator.class, 600),
    SlideInDown(SlideInUpAnimator.class, 600),
    ChargingX(BatteryChargingXAnimator.class, 1800, 0, new DecelerateInterpolator(), ValueAnimator.INFINITE, ValueAnimator.RESTART);

    private Class animatorClazz;

    private long duration;
    private long delay;
    private int repeatCount;
    private int repeatMode;
    private Interpolator interpolator;

    AnimatorType(Class clazz, int duration) {
        this(clazz, duration, 0, null, 0, ValueAnimator.RESTART);
    }

    AnimatorType(Class clazz, int duration, Interpolator interpolator) {
        this(clazz, duration, 0, interpolator, 0, ValueAnimator.RESTART);
    }

    AnimatorType(Class clazz, long duration, long delay, Interpolator interpolator, int repeatCount, int repeatMode) {
        this.animatorClazz = clazz;
        this.duration = duration;
        this.delay = delay;
        this.interpolator = interpolator;
        this.repeatCount = repeatCount;
        this.repeatMode = repeatMode;
    }

    /**
     * 根据参数创建一个animator
     *
     * @return animator
     */
    public <T extends View> BaseViewAnimator<T> newAnimator(@NonNull T target) {
        try {
            BaseViewAnimator animator = (BaseViewAnimator) animatorClazz.newInstance();
            animator.setDuration(duration)
                    .setRepeatCount(repeatCount)
                    .setRepeatMode(repeatMode)
                    .setInterpolator(interpolator)
                    .setStartDelay(delay)
                    .setTarget(target);

            return animator;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error("Can not init animatorClazz instance");
        }
    }
}
