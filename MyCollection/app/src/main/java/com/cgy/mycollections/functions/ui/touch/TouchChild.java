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

    public TouchChild(Context context) {
        super(context);
    }

    public TouchChild(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setTouchListener(TouchListener touchListener) {
        this.mTouchListener = touchListener;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        L.e("TouchChild", "TouchChild dispatchTouchEvent:" + event.getAction());
        boolean dispatchResult = super.dispatchTouchEvent(event);
        L.e("TouchChild", "TouchChild dispatchTouchEvent dispatchResult:" + dispatchResult);
        return dispatchResult;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        L.e("TouchChild", "TouchChild onTouchEvent:" + event.getAction());
        boolean touchResult = super.onTouchEvent(event);
        L.e("TouchChild", "TouchChild onTouchEvent touchResult:" + touchResult);
        return touchResult;
    }
}
