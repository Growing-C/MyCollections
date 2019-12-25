package com.cgy.mycollections.functions.ble.deviceframe.interfaces;

import java.util.List;

/**
 * Description :用于指令分割
 * Author :cgy
 * Date :2019/5/12
 */
public interface ISeparable {

    /**
     * 分包，一般是分成 20个字节一个包
     *
     * @param sourceBytes
     * @return
     */
    List<byte[]> separate(byte[] sourceBytes);
}
