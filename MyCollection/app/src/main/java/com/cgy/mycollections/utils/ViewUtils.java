package com.cgy.mycollections.utils;

import android.app.Activity;
import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import appframe.utils.L;

/**
 * Description :
 * Author :cgy
 * Date :2019/3/22
 */
public class ViewUtils {

    private static long lastClickTime = 0;//上次点击时间
    private static int fastClickCount = 0;//快速点击次数

    /**
     * 是否是快速点击，1s内的连续点击都是快速点击，防止多次点击
     *
     * @return
     */
    public static boolean isFastClick() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime < 1000) {
            L.e("don't click too fast , slow down man~");
            return true;
        }

        lastClickTime = currentTime;
        return false;
    }

    /**
     * 快速点击次数，用于区分是否是快速点击
     *
     * @return
     */
    public static int fastClickCount() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime < 1000) {
            L.e("don't click too fast , slow down man~");
            fastClickCount++;
            lastClickTime = currentTime;
            return fastClickCount;
        }

        lastClickTime = currentTime;
        fastClickCount = 0;
        return fastClickCount;
    }

    /**
     * 关闭软键盘
     *
     * @param context
     */
    public static void closeKeyboard(Activity context) {
        if (context == null)
            return;
        View view = context.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 显示软键盘
     *
     * @param context
     * @param view
     */
    public static void showKeyboard(Context context, View view) {
        if (context == null || view == null)
            return;
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(view, 0);
    }

    /**
     * 禁止EditText输入特殊字符(人名中有空格应该是允许的) 空格正则为 \\s
     *
     * @param editText
     * @param specialChatsRegex 特殊字符的正则表达式，这个不为空则用这个
     */
    public static void setEditTextInhibitInputSpeChat(EditText editText, final String specialChatsRegex) {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String speChat = specialChatsRegex;
                if (TextUtils.isEmpty(specialChatsRegex)) {
                    speChat = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\n]";
                }
                Pattern pattern = Pattern.compile(speChat);
                Matcher matcher = pattern.matcher(source.toString());
                if (matcher.find()) return "";
                else return null;
            }
        };
        InputFilter[] oldFilters = editText.getFilters();//防止覆盖其他filter
        InputFilter[] newFilters;
        if (oldFilters == null) {
            newFilters = new InputFilter[]{filter};
        } else {
            newFilters = new InputFilter[oldFilters.length + 1];
            for (int i = 0; i < oldFilters.length; i++) {
                newFilters[i] = oldFilters[i];
            }
            newFilters[newFilters.length - 1] = filter;
        }
        editText.setFilters(newFilters);
    }
}
