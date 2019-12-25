package com.cgy.mycollections.functions.ble.deviceframe;

import com.cgy.mycollections.functions.ble.deviceframe.interfaces.IBLEDeviceInterface;

/**
 * Description :
 * Author :cgy
 * Date :2018/9/19
 */
public class CommandFactory {
    /**
     * 获取CommandSender
     *
     * @param deviceInterface
     * @return
     */
    public static BaseLockCommandSender createCommandSender(IBLEDeviceInterface deviceInterface) {
        if (deviceInterface == null)
            return null;

//        if (deviceInterface instanceof SmartLockDevice) {
//            return new SmartLockCommand();
//        } else if (deviceInterface instanceof FukiLockDevice) {
//            return new FukiLockCommand();
//        } else if (deviceInterface instanceof MKLockDevice) {
//            return new MKLockCommand();
//        }

        return null;
    }

    /**
     * 根据设备类型来获取 指令id
     * （不同设备 数据长度 位可能不一样）
     *
     * @param deviceInterface
     * @return
     */
    public static String getCommandStrByDevice(IBLEDeviceInterface deviceInterface, byte[] bytes) {
        if (deviceInterface == null || bytes == null)
            return null;

        String commandStr = "";
//        if (deviceInterface instanceof SmartLockDevice) {
//            if (BinaryUtil.bytesToStr(true, bytes).startsWith("FEFE")) {
//                commandStr = CommandHelper.getCommandString(bytes, 8, 7);
//            }
//        } else if (deviceInterface instanceof FukiLockDevice) {
//            if (BinaryUtil.bytesToStr(true, bytes).startsWith("FEFE")) {
//                commandStr = CommandHelper.getCommandString(bytes, 13, 12);
//            }
//        }

        return commandStr;
    }
}
