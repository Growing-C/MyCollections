package com.cgy.mycollections.functions.ble.client;



import com.cgy.mycollections.utils.BinaryUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Description :
 * zhouzd
 */

public class CommandHelper {
    private static final String TAG = "CommandHelper";
    private static final String DEVICE_ID = "DEVICE_ID";

    /**
     * 根据bytes数组获取command字符串
     *
     * @param bytes
     * @param highIndex 高位
     * @param lowIndex  低位
     * @return
     */
    public static String getCommandString(byte[] bytes, int highIndex, int lowIndex) {
        if (bytes.length > highIndex) {
            return BinaryUtil.bytesToStr(true, new byte[]{bytes[highIndex], bytes[lowIndex]}).toUpperCase();
        } else {
            return "";
        }
    }

    /**
     * 根据bytes数组获取command字符串
     *
     * @param bytes
     */
    public static String getCommandString(byte[] bytes, int index) {
        if (bytes.length > index) {
            return BinaryUtil.bytesToStr(true, new byte[]{bytes[index]}).toUpperCase();
        } else {
            return "";
        }
    }

    /**
     * 分包，分成最大20字节的多个包
     *
     * @param maxLength 每个包最大长度
     * @return
     */
    public static List<byte[]> divideBytes(byte[] bytes, int maxLength) {

        int size = 0;
        if (bytes.length % maxLength == 0) {
            size = bytes.length / maxLength; //集合的个数
        } else {
            size = bytes.length / maxLength + 1;
        }
        List<byte[]> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (i == size - 1) { //如果是最后一个
                int lastLength = bytes.length - i * maxLength;
                byte[] newBytes = new byte[lastLength];
                System.arraycopy(bytes, i * maxLength, newBytes, 0, lastLength);
                list.add(newBytes);
            } else {
                byte[] newBytes = new byte[maxLength];
                System.arraycopy(bytes, i * maxLength, newBytes, 0, maxLength);
                list.add(newBytes);
            }
        }
        return list;
    }

}
