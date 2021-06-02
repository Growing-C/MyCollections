package appframe.utils;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import java.text.NumberFormat;
import java.util.Random;
import java.util.UUID;

/**
 * Description :
 * Author :cgy
 * Date :2019/8/2
 */
public class CommonUtils {


    /**
     * 随机生成len长度数字
     *
     * @param len
     * @return String
     */
    public static String numRandom(int len) {
        StringBuilder buf = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            buf.append(random.nextInt(10));
        }
        return buf.toString();

    }

    // 将字符串转成ASCII的Java方法

    public static String stringToAscii(String value) {
        StringBuffer sbu = new StringBuffer();
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            int aChar = chars[i];
            sbu.append(Integer.toHexString(aChar));
        }
        return sbu.toString();
    }

    /**
     * 格式化钱的数字，保留两位小数
     */
    public static String formatTwoDecimal(double number) {
        NumberFormat numberFormat = NumberFormat.getInstance();//格式化成2位小数
        numberFormat.setMinimumFractionDigits(2);
        return numberFormat.format(number);
    }

    /**
     * 格式化钱的数字，保留指定位数小数
     */
    public static String formatDecimal(double number, int digitLen) {
        NumberFormat numberFormat = NumberFormat.getInstance();//格式化成2位小数
        numberFormat.setMinimumFractionDigits(digitLen);
        return numberFormat.format(number);
    }

    /**
     * 格式化数字，保留0位小数
     */
    public static String formatZeroDecimal(double number) {
        NumberFormat numberFormat = NumberFormat.getInstance();//格式化成2位小数
        numberFormat.setMinimumFractionDigits(0);
        return numberFormat.format(number);
    }
}