package com.witon.mylibrary.flux.actions;

/**
 * Created by RB-cgy on 2017/5/5.
 */

public interface BaseActions {
    String ACTION_REQUEST_START = "request_start";//网络操作开始
    String ACTION_REQUEST_END = "request_end";//网络操作结束
    String COMMON_ACTION_FAIL = "common_fail";//通用失败action

    String UNIQUE_ACTION_FAIL = "unique_fail";//需特殊处理的失败action

    String ACTION_DIFFER_INTENT = "diff_intent";//区分intent来源
    String ACTION_SAVE_INTENT_DATA = "save_intent";//保存intent数据
}
