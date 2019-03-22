package com.cgy.mycollections.utils;

import android.view.View;


import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Description : hook view的click事件，
 * Author :cgy
 * Date :2018/11/6
 * 使用方式
 * <blockquote><pre>
 * getActivity().getWindow().getDecorView().post(new Runnable() {
 *  @Override
 *   public void run() {
 *    HookViewClickUtil.hookView(mLlLockConfig);
 *       HookViewClickUtil.hookView(mLlLockDetail);
 *             }
 *         });
 * </pre></blockquote>
 */
public class HookViewClickUtil {

    public static void hookView(View view) {
        try {
            Class viewClazz = Class.forName("android.view.View");
            //事件监听器都是这个实例保存的
            Method listenerInfoMethod = viewClazz.getDeclaredMethod("getListenerInfo");
            if (!listenerInfoMethod.isAccessible()) {
                listenerInfoMethod.setAccessible(true);
            }
            Object listenerInfoObj = listenerInfoMethod.invoke(view);

            Class listenerInfoClazz = Class.forName("android.view.View$ListenerInfo");

            Field onClickListenerField = listenerInfoClazz.getDeclaredField("mOnClickListener");

            if (!onClickListenerField.isAccessible()) {
                onClickListenerField.setAccessible(true);
            }
            View.OnClickListener mOnClickListener = (View.OnClickListener) onClickListenerField.get(listenerInfoObj);
            //自定义代理事件监听器
            View.OnClickListener onClickListenerProxy = new OnClickListenerProxy(mOnClickListener);
            //更换
            onClickListenerField.set(listenerInfoObj, onClickListenerProxy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //自定义的代理事件监听器
    private static class OnClickListenerProxy implements View.OnClickListener {
        private View.OnClickListener object;

        private int MIN_CLICK_DELAY_TIME = 1000;

        private long lastClickTime = 0;

        private OnClickListenerProxy(View.OnClickListener object) {
            this.object = object;
        }

        @Override
        public void onClick(View v) {
            //点击时间控制
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
                lastClickTime = currentTime;
                L.e("OnClickListenerProxy", "OnClickListenerProxy");
                if (object != null) object.onClick(v);
            }
        }
    }


}
