package com.cgy.mycollections.functions.anim.animator;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;

import androidx.annotation.NonNull;

import com.facebook.common.internal.Preconditions;

/**
 * @author yangwq
 * @date 2021-2-2 16:19
 */
public abstract class BaseViewAnimator<T extends View> {
    public static final long DURATION = 300;
    private static final String TAG = BaseViewAnimator.class.getSimpleName();

    private AnimatorSet mAnimatorSet;

    private long mDuration = DURATION;
    /**
     * 重复次数
     * 默认0次
     * INFINITE 无限循环
     */
    private int mRepeatCount = 0;
    /**
     * RESTART REVERSE INFINITE
     */
    private int mRepeatMode = ValueAnimator.RESTART;

    public BaseViewAnimator() {
        mAnimatorSet = new AnimatorSet();
    }

    /**
     * 准备动画内容
     *
     * @param target
     */
    protected abstract void prepareAnim(T target);

    public BaseViewAnimator setTarget(@NonNull T target) {
        reset(target);
        prepareAnim(target);
        return this;
    }

    /**
     * 开始播放
     */
    public void play() {
        start();
    }

    /**
     * 销毁
     */
    public void destroy() {
        cancel();
        removeAllListener();
    }

    /**
     * reset the view to default status
     *
     * @param target
     */
    protected void reset(@NonNull T target) {
        Preconditions.checkNotNull(target, "reset target must not be null!");
        target.setAlpha(1);
        target.setScaleX(1);
        target.setScaleY(1);
        target.setTranslationX(0);
        target.setTranslationY(0);
        target.setTranslationY(0);
        target.setRotation(0);
        target.setRotationY(0);
        target.setRotationX(0);
    }

    /**
     * start to animate
     * 默认取消当前动画，显示最新的动画
     */
    private void start() {
        Log.d(TAG, "start ");
        if (isRunning()) {
            Log.d(TAG, "isStarted or is Running ");
            cancel();
        }
        startInternal();
    }

    private void startInternal() {
        for (Animator animator : mAnimatorSet.getChildAnimations()) {
            if (animator instanceof ValueAnimator) {
                ((ValueAnimator) animator).setRepeatCount(mRepeatCount);
                ((ValueAnimator) animator).setRepeatMode(mRepeatMode);
            }
        }
        mAnimatorSet.setDuration(mDuration);
        mAnimatorSet.start();
    }

    public BaseViewAnimator addAnimatorListener(Animator.AnimatorListener l) {
        mAnimatorSet.addListener(l);
        return this;
    }

    public void cancel() {
        mAnimatorSet.cancel();
        removeAllListener();
    }

    public boolean isRunning() {
        return mAnimatorSet.isRunning();
    }

    public boolean isStarted() {
        return mAnimatorSet.isStarted();
    }

    public void removeAnimatorListener(Animator.AnimatorListener l) {
        mAnimatorSet.removeListener(l);
    }

    public void removeAllListener() {
        mAnimatorSet.removeAllListeners();
    }

    public long getStartDelay() {
        return mAnimatorSet.getStartDelay();
    }

    public BaseViewAnimator setStartDelay(long delay) {
        mAnimatorSet.setStartDelay(delay);
        return this;
    }

    public BaseViewAnimator setInterpolator(Interpolator interpolator) {
        mAnimatorSet.setInterpolator(interpolator);
        return this;
    }

    public long getDuration() {
        return mDuration;
    }

    public BaseViewAnimator setDuration(long duration) {
        mDuration = duration;
        return this;
    }

    public BaseViewAnimator setRepeatCount(int repeatCount) {
        mRepeatCount = repeatCount;
        return this;
    }

    public BaseViewAnimator setRepeatMode(int repeatMode) {
        mRepeatMode = repeatMode;
        return this;
    }

    public AnimatorSet getAnimatorSet() {
        return mAnimatorSet;
    }
}
