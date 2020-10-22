package com.cgy.mycollections.functions.ui.touch;

import android.view.MotionEvent;

/**
 * 作者: 陈高阳
 * 创建日期: 2020/10/22 15:46
 * 修改日期: 2020/10/22 15:46
 * 类说明：
 */
public class TouchUtils {
    /**
     * 将touch
     *
     * @param action
     * @return
     */
    public static String getTouchActionName(int action) {
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                return "ACTION_DOWN";
            case MotionEvent.ACTION_MOVE:
                return "ACTION_MOVE";
            case MotionEvent.ACTION_UP:
                return "ACTION_UP";
            default:
                return String.valueOf(action);
        }
    }
}
