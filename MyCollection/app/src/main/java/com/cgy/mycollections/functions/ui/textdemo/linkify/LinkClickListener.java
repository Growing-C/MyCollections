package com.cgy.mycollections.functions.ui.textdemo.linkify;

public interface LinkClickListener {
    /**
     * true  表示要自己处理  false 使用系统默认
     *
     * @param mURL
     * @return
     */
    boolean onLinkClick(String mURL);
}