package com.cgy.mycollections.testsources;

import com.cgy.mycollections.utils.BinaryUtil;
import com.cgy.mycollections.utils.CHexConverter;

/**
 * Description :数据类型测试
 * Author :cgy
 * Date :2019/11/22
 */
public class DataTest {
    public static void main(String[] args) {
        byte[] aa = "C11A1FCD10000053".getBytes();
        System.out.println("转成16进制byte：" + CHexConverter.byte2HexStr(aa));
        System.out.println("aa 转成str：" + new String(aa));
        System.out.println("raw bytes：" + BinaryUtil.bytesToStr(true, aa));
    }

}
