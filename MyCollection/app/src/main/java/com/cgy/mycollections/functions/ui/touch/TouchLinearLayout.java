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

    public TouchLinearLayout(Context context) {
        super(context);
    }

    public TouchLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setTouchListener(TouchListener touchListener) {
        this.mTouchListener = touchListener;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        L.e("TouchLinearLayout", "dispatchTouchEvent:" + ev.getAction());
        boolean dispatchResult = super.dispatchTouchEvent(ev);
        L.e("TouchLinearLayout", "dispatchTouchEvent dispatchResult:" + dispatchResult);
        return dispatchResult;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        L.e("TouchLinearLayout", "onInterceptTouchEvent:" + ev.getAction());
        boolean interceptResult = super.onInterceptTouchEvent(ev);
        L.e("TouchLinearLayout", "onInterceptTouchEvent interceptResult:" + interceptResult);
        return interceptResult;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        L.e("TouchLinearLayout", "onTouchEvent:" + event.getAction());
        boolean touchResult = super.onTouchEvent(event);
        L.e("TouchLinearLayout", "onTouchEvent touchResult:" + touchResult);
        return touchResult;
    }
}
