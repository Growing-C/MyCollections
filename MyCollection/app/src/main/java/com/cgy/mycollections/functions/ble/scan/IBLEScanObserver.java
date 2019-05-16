package com.cgy.mycollections.functions.ble.scan;

import android.bluetooth.BluetoothDevice;

/**
 * Description :
 * Author :cgy
 * Date :2018/8/7
 */
public interface IBLEScanObserver {

    /**
     * 扫描开始
     */
    void onScanStarted();

    /**
     * 扫描停止（仅仅是通知扫描结束，不要在这里做任何开始扫描的操作！）
     */
    void onScanStopped();

    /**
     * 不支持 BLE
     */
    void onBLEUnsupported();

    /**
     * BLE被关闭
     */
    void onBLEDisabled();

    /**
     * 扫描超时
     */
    void onScanTimeout();

    /**
     * 扫描错误
     * @param error
     */
    void onError(String error);

    /**
     * 找到设备
     */
    void onDeviceFound(BluetoothDevice device);

}
