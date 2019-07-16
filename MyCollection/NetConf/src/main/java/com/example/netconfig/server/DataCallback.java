package com.example.netconfig.server;

/**
 * Description :
 * Author :cgy
 * Date :2019/6/10
 */
public interface DataCallback {
    void onGetBleResponse(String data, byte[] rawData);
    void onConnected();
}
