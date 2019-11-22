package com.cgy.mycollections.testsources;

import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Description :
 * Author :cgy
 * Date :2019/10/30
 */
public class TimeFormatTest {
//    public static void main(String[] args) {
//        testTimeFormat();
//    }

    public static void testTimeFormat() {
        Calendar calendar = Calendar.getInstance();
        System.out.println("time :" + DateFormat.format("yyyy-MM-dd HH:mm", calendar));
    }
}
