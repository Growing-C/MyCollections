package com.growingc.mediaoperator.filebrowser;

import android.content.Context;

import appframe.utils.SharePreUtil;

/**
 * Description :配置相关
 * Author :cgy
 * Date :2019/12/5
 */
public class FileConfig {
    private static final String CONFIG_PREF = "config";

    private static final String KEY_SHOW_HIDDEN_FILES = "show_hidden_file";

    /**
     * 设置是否显示隐藏文件
     *
     * @param isShow
     */
    public static void setShowHiddenFiles(boolean isShow, Context context) {
        SharePreUtil.putBoolean(CONFIG_PREF, context, KEY_SHOW_HIDDEN_FILES, isShow);
    }

    /**
     * 获取是否显示隐藏文件，默认不显示
     *
     * @return
     */
    public static boolean isShowHiddenFiles(Context context) {
        return SharePreUtil.getBoolean(CONFIG_PREF, context, KEY_SHOW_HIDDEN_FILES, false);
    }
}
