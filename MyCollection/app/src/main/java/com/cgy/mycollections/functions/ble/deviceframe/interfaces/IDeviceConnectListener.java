package com.cgy.mycollections.functions.ble.deviceframe.interfaces;


import com.cgy.mycollections.functions.ble.deviceframe.BaseDeviceResponse;

/**
 * Description :
 * Author :cgy
 * Date :2018/8/7
 */
public interface IDeviceConnectListener {

    /**
     * 错误
     *
     * @param errorCode
     */
    void onError(int errorCode);

    /**
     * 连接状态回调
     *
     * @param status
     */
    void onConnectStatus(int status);

    /**
     * 发送指令回调
     *
     * @param tag
     * @param response
     */
    void onCommandResult(String tag, BaseDeviceResponse response);
//    public void onReceiveData(Map<String,Object> map);
}
