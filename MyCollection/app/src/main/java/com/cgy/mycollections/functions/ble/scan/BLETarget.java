package com.cgy.mycollections.functions.ble.scan;

import androidx.annotation.NonNull;
import android.text.TextUtils;


/**
 * Description : 蓝牙目标 信息
 * Author :cgy
 * Date :2018/8/7
 */
public class BLETarget {
    private String targetName;//设备名称 如Mansion
    private String targetAddress;//设备地址  必须是蓝牙地址格式 如 11:58:02:A0:02:40（8.0似乎还必须是大写！）

    public BLETarget(String name, String address) {
        this.targetName = name;
        if (!TextUtils.isEmpty(address))
            this.targetAddress = address.toUpperCase();//蓝牙地址似乎必须是大写，android 8.0报错
    }

    public BLETarget(String name, String address, @NonNull ITargetTransformer transformer) {
        this.targetName = transformer.transformName(name);

        if (!TextUtils.isEmpty(address))
            this.targetAddress = transformer.transformAddress(address).toUpperCase();//蓝牙地址似乎必须是大写，android 8.0报错
    }

    /**
     * 是否是目标设备
     *
     * @param name    蓝牙设备 蓝牙扫描到的完整名称
     * @param address
     * @return
     */
    public boolean isTarget(String name, String address) {
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(targetName) && name.contains(targetName)) {
            return true;
        }
        if (!TextUtils.isEmpty(targetAddress) && targetAddress.equals(address))
            return true;

        return false;
    }

    public String getTargetName() {
        return targetName;
    }

    public String getTargetAddress() {
        return targetAddress;
    }

    @Override
    public String toString() {//for log
        return "targetName:" + targetName + "--targetAddress:" + targetAddress;
    }
}
