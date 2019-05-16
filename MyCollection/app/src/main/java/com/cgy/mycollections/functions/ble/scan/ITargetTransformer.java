package com.cgy.mycollections.functions.ble.scan;

import android.text.TextUtils;

/**
 * Description : 蓝牙设备名 地址名翻译者
 * Author :cgy
 * Date :2018/8/7
 */
public interface ITargetTransformer {
    ITargetTransformer DEFAULT = new ITargetTransformer() {
        @Override
        public String transformName(String targetName) {
            return targetName;
        }

        @Override
        public String transformAddress(String targetAddress) {
            if (TextUtils.isEmpty(targetAddress) || targetAddress.contains(":"))
                return targetAddress;
            //115802A00240   ==>   11:58:02:A0:02:40
            StringBuffer buffer = new StringBuffer(targetAddress);
            int lastInsertIndex = buffer.length() - 2;
            char divideChar = ':';
            while (lastInsertIndex > 0) {
                buffer.insert(lastInsertIndex, divideChar);

                lastInsertIndex -= 2;
            }

            return buffer.toString();
        }
    };

    /**
     * 转化名字
     *
     * @param targetName
     * @return
     */
    String transformName(String targetName);

    /**
     * 转化地址
     *
     * @param targetAddress
     * @return
     */
    String transformAddress(String targetAddress);
}
