package com.cgy.mycollections.functions.anim;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.FloatProperty;
import android.util.Property;

import androidx.appcompat.widget.AppCompatImageView;

import appframe.utils.L;

/**
 * author: chengy
 * created on: 2021-2-5 17:37
 * description: 支持渐显 动画的imageView(view只显示部分内容，和scale，translate,rotate,alpha 不同，但是也可以通过animator来操作)
 */
@SuppressLint("NewApi")
public class AnimatorImageView extends AppCompatImageView {

    float mShowRectX = 0;//右边显示的边界位置
    float mShowRectY = 0;//底部显示的边界位置
    RectF mShowRect = new RectF();

    /**
     * A Property wrapper around the <code>alpha</code> functionality handled by the
     * {@link AnimatorImageView#setShowRectX(float) } and {@link AnimatorImageView#getShowRectX()}   methods.
     * ObjectAnimator.ofFloat(mBinding.ivDrivingModeHint, "showRectX", 0, x)
     * 可以使用 “showRectX”  也可以用 SHOW_RECT_X，效果是一样的  这个SHOW_RECT_X也可以不定义
     * 不过有个问题   ObjectAnimator.ofFloat(target, "xx", 1, 0), 中的 target View必须 有xxx方法，即方法和view要对应
     */
    public static final Property<AnimatorImageView, Float> SHOW_RECT_X = new FloatProperty<AnimatorImageView>("showRectX") {
        @Override
        public void setValue(AnimatorImageView object, float value) {
            object.setShowRectX(value);
        }

        @Override
        public Float get(AnimatorImageView object) {
            return object.getShowRectX();
        }
    };


    public AnimatorImageView(Context context) {
        super(context);
    }

    public AnimatorImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0 && h > 0) {
            L.i("AnimatorImageView width:" + w);
            L.i("AnimatorImageView height:" + h);
            mShowRectX = w;
            mShowRectY = h;
            mShowRect.left = 0;
            mShowRect.top = 0;
            mShowRect.right = mShowRectX;
            mShowRect.bottom = mShowRectY;
        }
    }

    /**
     * 设置显示的rect的右边界
     *
     * @param showRectRightX 有边界x
     */
    public void setShowRectX(float showRectRightX) {
        if (showRectRightX != mShowRectX && showRectRightX <= getWidth()) {
            mShowRectX = showRectRightX;
            mShowRect.right = mShowRectX;
            invalidate();
        }
    }

    public float getShowRectX() {
        L.i("AnimatorImageView getShowRectX:" + mShowRectX);
        return mShowRectX;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.clipRect(mShowRect);
        super.onDraw(canvas);

    }
}
