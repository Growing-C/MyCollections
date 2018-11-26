package com.cgy.mycollections;

import com.cgy.mycollections.utils.L;
import com.cgy.mycollections.utils.PinYinUtils;

/**
 * Created by RB-cgy on 2018/6/15.
 */
public class TestMain {
    public static void main(String[] args) {
        testPinyin();
    }

    public static void testPinyin() {
        System.out.println("-----------------------------------------------------");
        System.out.println("1:" + PinYinUtils.getFirstLetter("陈d订单",true));
        System.out.println("2:" + PinYinUtils.getFirstLetters("ceed", true));
        System.out.println("3:" + PinYinUtils.getFirstLetters("陈f.@@110),", false));
        System.out.println("/////////////////////////////////////");
        System.out.println("a:" + PinYinUtils.getPinyinString("陈f.@@110)"));
        System.out.println("b:" + PinYinUtils.getPinyinString("陈好啊 啊"));
        System.out.println("-----------------------------------------------------");
    }
}
