package com.cgy.mycollections;

import appframe.utils.SharePreUtil;

/**
 * Description :配置相关
 * Author :cgy
 * Date :2019/12/5
 */
public class Config {
    private static final String CONFIG_PREF = "config";

    private static final String KEY_SHOW_HIDDEN_FILES = "show_hidden_file";

    /**
     * 设置是否显示隐藏文件
     *
     * @param isShow
     */
    public static void setShowHiddenFiles(boolean isShow) {
        SharePreUtil.putBoolean(CONFIG_PREF, MyApplication.getInstance(), KEY_SHOW_HIDDEN_FILES, isShow);
    }

    /**
     * 获取是否显示隐藏文件，默认不显示
     *
     * @return
     */
    public static boolean isShowHiddenFiles() {
        return SharePreUtil.getBoolean(CONFIG_PREF, MyApplication.getInstance(), KEY_SHOW_HIDDEN_FILES, false);
    }
}
