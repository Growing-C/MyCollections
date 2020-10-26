package com.cgy.mycollections.functions.ui.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import appframe.utils.L;

/**
 * 作者: 陈高阳
 * 创建日期: 2020/10/22 11:04
 * 修改日期: 2020/10/22 11:04
 * 类说明：
 */
public class TouchChild extends View {
    TouchListener mTouchListener;

    boolean shouldInterceptDispatch = false;//是否阻塞dispatch方法
    boolean shouldCostTouch = false;//是否消费touch方法

    public TouchChild(Context context) {
        super(context);
    }

    public TouchChild(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setTouchListener(TouchListener touchListener) {
        this.mTouchListener = touchListener;
    }

    public void setShouldInterceptDispatch(boolean shouldInterceptDispatch) {
        this.shouldInterceptDispatch = shouldInterceptDispatch;
    }

    public void setShouldCostTouch(boolean shouldCostTouch) {
        this.shouldCostTouch = shouldCostTouch;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (mTouchListener != null)
            mTouchListener.onInvokeDispatchTouchEvent(event);
        if (shouldInterceptDispatch)
            return true;
        boolean dispatchResult = super.dispatchTouchEvent(event);
        if (mTouchListener != null)
            mTouchListener.onDispatchTouchEventResult(event, dispatchResult);
        return dispatchResult;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mTouchListener != null)
            mTouchListener.onInvokeTouchEvent(event);
        if (shouldCostTouch)
            return true;
        boolean touchResult = super.onTouchEvent(event);
        if (mTouchListener != null)
            mTouchListener.onTouchEventResult(event, touchResult);
        return touchResult;
    }
}
