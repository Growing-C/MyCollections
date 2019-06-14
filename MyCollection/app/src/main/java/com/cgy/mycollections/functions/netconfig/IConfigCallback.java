package com.cgy.mycollections.functions.netconfig;

/**
 * Description :
 * Author :cgy
 * Date :2019/6/14
 */
public interface IConfigCallback {
    /**
     * 错误
     *
     * @param errorCode
     * @param errorMsg
     */
    void onError(int errorCode, String errorMsg);

    /**
     * 配网过程中
     *
     * @param process
     * @param msg
     */
    void onProcessChange(int process, String msg);

    /**
     * 配网成功
     */
    void onSuccess();
}
