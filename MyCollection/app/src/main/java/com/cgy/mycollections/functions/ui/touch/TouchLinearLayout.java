package com.cgy.mycollections.functions.ui.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import appframe.utils.L;

/**
 * 作者: 陈高阳
 * 创建日期: 2020/10/22 11:02
 * 修改日期: 2020/10/22 11:02
 * 类说明：
 */
public class TouchLinearLayout extends LinearLayout {
    TouchListener mTouchListener;

    boolean shouldIntercept = false;//是否intercept
    boolean shouldInterceptDispatch = false;//是否阻塞dispatch方法
    boolean shouldCostTouch = false;//是否消费touch方法

    public TouchLinearLayout(Context context) {
        super(context);
    }

    public TouchLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setTouchListener(TouchListener touchListener) {
        this.mTouchListener = touchListener;
    }

    public void setShouldIntercept(boolean shouldIntercept) {
        this.shouldIntercept = shouldIntercept;
    }

    public void setShouldInterceptDispatch(boolean shouldInterceptDispatch) {
        this.shouldInterceptDispatch = shouldInterceptDispatch;
    }

    public void setShouldCostTouch(boolean shouldCostTouch) {
        this.shouldCostTouch = shouldCostTouch;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mTouchListener != null)
            mTouchListener.onInvokeDispatchTouchEvent(ev);
        if (shouldInterceptDispatch) {
            return true;
        }
        boolean dispatchResult = super.dispatchTouchEvent(ev);
        if (mTouchListener != null)
            mTouchListener.onDispatchTouchEventResult(ev, dispatchResult);
        return dispatchResult;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mTouchListener != null)
            mTouchListener.onInvokeInterceptTouchEvent(ev);
        if (shouldIntercept)
            return true;
        boolean interceptResult = super.onInterceptTouchEvent(ev);
        if (mTouchListener != null)
            mTouchListener.onInterceptTouchEventResult(ev, interceptResult);
        return interceptResult;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mTouchListener != null)
            mTouchListener.onInvokeTouchEvent(event);
        boolean touchResult = super.onTouchEvent(event);
        if (mTouchListener != null)
            mTouchListener.onTouchEventResult(event, touchResult);
        return touchResult;
    }
}
