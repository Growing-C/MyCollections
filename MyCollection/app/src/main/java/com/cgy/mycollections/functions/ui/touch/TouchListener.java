package com.cgy.mycollections.functions.ui.touch;

import android.view.MotionEvent;

/**
 * 作者: 陈高阳
 * 创建日期: 2020/10/22 15:44
 * 修改日期: 2020/10/22 15:44
 * 类说明：
 */
public interface TouchListener {
    /**
     * 调用 dispatchTouchEvent
     *
     * @param event
     */
    void onInvokeDispatchTouchEvent(MotionEvent event);

    /**
     * 调用 dispatchTouchEvent的返回
     *
     * @param event
     */
    void onDispatchTouchEventResult(MotionEvent event, boolean result);

    /**
     * 调用 onTouchEvent
     *
     * @param event
     */
    void onInvokeTouchEvent(MotionEvent event);

    /**
     * 调用 onTouchEvent 的返回
     *
     * @param event
     */
    void onTouchEventResult(MotionEvent event, boolean result);

    /**
     * 调用 onInterceptTouchEvent
     *
     * @param event
     */
    default void onInvokeInterceptTouchEvent(MotionEvent event) {
    }

    /**
     * 调用 onInterceptTouchEvent 结果
     *
     * @param event
     */
    default void onInterceptTouchEventResult(MotionEvent event, boolean result) {
    }

}
