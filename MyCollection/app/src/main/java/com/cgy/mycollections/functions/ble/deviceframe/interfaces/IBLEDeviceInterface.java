package com.cgy.mycollections.functions.ble.deviceframe.interfaces;

import android.bluetooth.BluetoothDevice;

import com.cgy.mycollections.functions.ble.deviceframe.BaseLockCommandSender;
import com.cgy.mycollections.functions.ble.deviceframe.Command;

/**
 * Description :
 * Author :cgy
 * Date :2018/8/7
 */
public interface IBLEDeviceInterface {

    /**
     * 开始连接
     *
     * @param listener
     */
    void startConnect(IDeviceConnectListener listener);

    IDeviceConnectListener getDeviceListener();

    /**
     * 重新连接
     */
    void reStart();

    /**
     * 设置是否自动重连
     *
     * @param isNeedReconnect 是否自动重连
     * @param reConnectLimit  重连次数限制
     */
    void setAutoReconnect(boolean isNeedReconnect, int reConnectLimit);

    /**
     * 关闭
     *
     * @param shouldNotify 是否通知
     */
    void close(boolean shouldNotify);

    /**
     * 发送命令（此处的命令可能是一条指令拆分成多个20字节的组分成多次sendCommand发送）
     *
     * @param command
     */
    void sendCommand(Command command);

    /**
     * 设置接收数据超时时间
     *
     * @param millis
     */
    void setReceiveTimeOut(int millis);

    /**
     * 是否是同一个设备 省的创建多次
     *
     * @param device
     * @return
     */
    boolean isSameDevice(BluetoothDevice device);

    /**
     * 获取连接状态
     *
     * @return
     */
    int getConnectStatus();

    /**
     * 手动设置连接状态（使用时要注意，此时的连接状态可能为自定义状态）
     *
     * @param mConnectStatus
     */
    void setConnectStatus(int mConnectStatus);

    /**
     * 信号回调
     *
     * @param rssiCallback
     */
    void setRssiCallback(IRssiCallback rssiCallback);

    /**
     * 添加命令creator
     *
     * @param commandCreator
     */
    void addCommandCreator(BaseLockCommandSender commandCreator);

    BaseLockCommandSender getCommandCreator();
}
