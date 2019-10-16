package com.cgy.mycollections.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;


public class BinaryUtil {

    private final static String _BYTES = "0123456789ABCDEF";


    public static void setBytes(byte[] dest, String src, byte fillByte) {
        try {
            byte[] srcBytes = src.getBytes("MS936");
            Arrays.fill(dest, fillByte);
            System.arraycopy(srcBytes, 0, dest, 0, Math.min(dest.length, srcBytes.length));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String getString(byte[] src) {
        try {
            int iPos = 0;
            while ((iPos < src.length) && (src[iPos] != (byte) 0x00)) iPos++;
            if (iPos <= 0) {
                return "";
            } else {
                return new String(src, 0, iPos).trim();
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * UTF-16LE、UTF-8
     */
    public static String getString(byte[] src, String encode) {
        try {
            int iPos = 0;
            while ((iPos < src.length) && (src[iPos] != (byte) 0x00)) iPos++;
            if (iPos <= 0) {
                return "";
            } else {
                return new String(src, 0, iPos, encode).trim();
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static int getCheckSum(byte[] bytes, int start, int len) {
        long chkSum = 0L;
        for (int i = 0; i < len; i++) {
            chkSum += bytes[start + i] & 0xff;
        }
        return (int) (chkSum & 0xff);
    }

    public static class DataItemDef {
        public final Field field;
        public final Method method;

        public DataItemDef(Field f, Method m) {
            this.field = f;
            this.method = m;
        }
    }

    public static long getIntFromBytes(boolean bigEndian, byte[] bytes, int start, int len) {
        long val = 0;
        int curVal = 0;

        if (bigEndian) {
            for (int i = 0; i <= len - 1; i++) {
                curVal = bytes[start + i];
                if (curVal < 0) {
                    curVal = 256 + curVal;
                }

                val = (val << 8) + curVal;
            }
        } else {
            for (int i = len - 1; i >= 0; i--) {
                curVal = bytes[start + i];
                if (curVal < 0) {
                    curVal = 256 + curVal;
                }

                val = (val << 8) + curVal;
            }
        }

        return val;
    }

    public static short unsignedByte(byte b) {
        short val = b;
        if (val < 0) {
            val += 0x100;
        }
        return val;
    }

    public static int unsignedShort(short s) {
        int val = s;
        if (val < 0) {
            val += 0x10000;
        }
        return val;
    }

    public static long unsignedInt(int s) {
        long val = s;
        if (val < 0) {
            val += 0x100000000L;
        }
        return val;
    }

    public static boolean getBit(byte num, int i) {
        int newNum = unsignedByte(num);
        newNum = newNum & (0x01 << i);

        return (newNum != 0);
    }

    public static boolean getBit(short num, int i) {
        int newNum = unsignedShort(num);
        newNum = newNum & (0x01 << i);

        return (newNum != 0);
    }

    public static boolean getBit(int num, int i) {
        long newNum = unsignedInt(num);
        newNum = newNum & (0x01 << i);

        return (newNum != 0);
    }

    public static byte setBit(byte num, int i, boolean val) {
        int newNum = unsignedByte(num);
        if (val) {
            newNum = newNum | (0x01 << i);
        } else {
            int mask = ~(1 << i);
            newNum = newNum & mask;
        }

        return (byte) newNum;
    }

    public static short setBit(short num, int i, boolean val) {
        int newNum = unsignedShort(num);
        if (val) {
            newNum = newNum | (0x01 << i);
        } else {
            int mask = ~(1 << i);
            newNum = newNum & mask;
        }

        return (short) newNum;
    }

    public static int setBit(int num, int i, boolean val) {
        long newNum = unsignedInt(num);
        if (val) {
            newNum = newNum | (0x01 << i);
        } else {
            int mask = ~(1 << i);
            newNum = newNum & mask;
        }

        return (int) newNum;
    }

    /**
     * byte转为16进制的字符串
     *
     * @param b
     * @return
     */
    public static String byteToStr(byte b) {
        int val = b;
        if (val < 0) {
            val += 256;
        }

        char[] chs = new char[2];
        chs[0] = _BYTES.charAt(val / 16);
        chs[1] = _BYTES.charAt(val % 16);
        return new String(chs);
    }

    public static byte strToByte(String str) {
        str = str.toUpperCase();
        int hi = str.charAt(0) - '0';
        int lo = str.charAt(1) - '0';
        if (hi > 9) hi = hi - 7;
        if (lo > 9) lo = lo - 7;
        return (byte) ((hi << 4) + lo);
    }

    public static String shortToStr(short s) {
        StringBuffer sb = new StringBuffer();
        sb.append(byteToStr((byte) ((s & 0xff00) >> 8)));
        sb.append(byteToStr((byte) (s & 0xff)));
        return sb.toString();
    }


    /**
     * byte 转 short 等效于 直接 使用byte，所以这个方法似乎没必要
     *
     * @param b
     * @return
     */
    public static short byteToShort(byte b) {
        byte[] bytes = {0, b};
        return (short) (bytes[1] & 0xff
                | (bytes[0] & 0xff) << 8);
    }

    public static short bytesToShort(byte[] b) {
        return (short) (b[1] & 0xff
                | (b[0] & 0xff) << 8);
    }

    /**
     * bytes数组转为ASCII码的字母，此方法中文会是乱码
     *
     * @param bigEndian
     * @param bytes
     * @return
     */
    public static String bytesToASCIIStr(boolean bigEndian, byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        int len = bytes.length;
        if (bigEndian) {
            for (int i = 0; i < len; i++) {
                if (bytes[i] != 0)//去除00的情况，此情况是乱码
                    sb.append((char) bytes[i]);
            }
        } else {
            for (int i = len - 1; i >= 0; i--) {
                if (bytes[i] != 0)//去除00的情况，此情况是乱码
                    sb.append((char) bytes[i]);
            }
        }

        return sb.toString();
    }

    public static String bytesToStr(boolean bigEndian, byte[] bytes) {
        return bytesToStr(bigEndian, bytes, (char) 0, 0, bytes.length);
    }

    public static String bytesToStr(boolean bigEndian, byte[] bytes, char spliter) {
        return bytesToStr(bigEndian, bytes, spliter, 0, bytes.length);
    }

    public static String bytesToStr(boolean bigEndian, byte[] bytes, int offset, int len) {
        return bytesToStr(bigEndian, bytes, (char) 0, offset, len);
    }


    public static String bytesToStr(boolean bigEndian, byte[] bytes, char spliter, int offset, int len) {
        StringBuffer sb = new StringBuffer();
        if (bigEndian) {
            for (int i = 0; i < len; i++) {
                if ((i > 0) && (spliter != 0)) sb.append(spliter);
                sb.append(byteToStr(bytes[offset + i]));
            }
        } else {
            for (int i = len - 1; i >= 0; i--) {
                if ((i < len - 1) && (spliter != 0)) sb.append(spliter);
                sb.append(byteToStr(bytes[offset + i]));
            }
        }

        return sb.toString();
    }

    public static byte[] strToBytes(boolean bigEndian, String str) {
        return strToBytes(bigEndian, str, false);
    }

    public static byte[] strToBytes(boolean bigEndian, String str, boolean hasSpliter) {
        str = str.toUpperCase();
        int wordBytes = 2;
        int len = str.length() / wordBytes;
        if (hasSpliter) {
            wordBytes++;
            len = (str.length() + 1) / wordBytes;
        }

        byte[] bytes = new byte[len];
        int hi;
        int lo;
        for (int i = 0; i < len; i++) {
            hi = str.charAt(i * wordBytes) - '0';
            lo = str.charAt(i * wordBytes + 1) - '0';

            if (hi > 9) {
                hi = hi - 7;
            }

            if (lo > 9) {
                lo = lo - 7;
            }

            if (bigEndian) {
                bytes[i] = (byte) (hi * 16 + lo);
            } else {
                bytes[len - 1 - i] = (byte) (hi * 16 + lo);
            }
        }

        return bytes;
    }

    public static String intToHexStr(int s) {
        StringBuffer sb = new StringBuffer();
        sb.append(byteToStr((byte) ((s & 0xff000000) >> 24)));
        sb.append(byteToStr((byte) ((s & 0xff0000) >> 16)));
        sb.append(byteToStr((byte) ((s & 0xff00) >> 8)));
        sb.append(byteToStr((byte) (s & 0xff)));
        return sb.toString();
    }

    /**
     * long转换为16进制的字符串
     *
     * @param s
     * @return
     */
    public static String longToHexStr(long s) {
        StringBuffer sb = new StringBuffer();

        sb.append(byteToStr((byte) ((s & 0xff00000000000000L) >> 56)));
        sb.append(byteToStr((byte) ((s & 0xff000000000000L) >> 48)));
        sb.append(byteToStr((byte) ((s & 0xff0000000000L) >> 40)));
        sb.append(byteToStr((byte) ((s & 0xff00000000L) >> 32)));
        sb.append(byteToStr((byte) ((s & 0xff000000L) >> 24)));
        sb.append(byteToStr((byte) ((s & 0xff0000L) >> 16)));
        sb.append(byteToStr((byte) ((s & 0xff00L) >> 8)));
        sb.append(byteToStr((byte) (s & 0xffL)));
        return sb.toString();
    }

    public static boolean isAllZero(byte[] bytes, int offset, int len) {
        for (int i = 0; i < len; i++) {
            if (bytes[offset + i] != 0x00) {
                return false;
            }
        }

        return true;
    }

    public static byte[] subBytes(byte[] bytes, int offset, int len) {
        byte[] subs = new byte[len];
        System.arraycopy(bytes, offset, subs, 0, len);
        return subs;
    }

    /**
     * 多个byte数组的集合合并成一个byte数组
     *
     * @param list
     * @return
     */
    public static byte[] mergeBytes(List<byte[]> list) {
        int length = 0;
        for (int i = 0; i < list.size(); i++) {
            length += list.get(i).length;
        }
        byte[] bytes = new byte[length];
        int currentLength = 0;
        for (int i = 0; i < list.size(); i++) { //合并所有的byte到bytes里
            System.arraycopy(list.get(i), 0, bytes, currentLength, list.get(i).length);
            currentLength += list.get(i).length;
        }
        return bytes;
    }

    /**
     * 多个byte数组的集合合并成一个byte数组
     *
     * @param bytesList
     * @return
     */
    public static byte[] mergeBytes(byte[]... bytesList) {
//        L.e("len:" + bytesList.length);
        int length = 0;
        for (int i = 0; i < bytesList.length; i++) {
            length += bytesList[i].length;
        }
        byte[] bytes = new byte[length];
        int currentLength = 0;
        for (int i = 0; i < bytesList.length; i++) { //合并所有的byte到bytes里
            System.arraycopy(bytesList[i], 0, bytes, currentLength, bytesList[i].length);
            currentLength += bytesList[i].length;
        }
        return bytes;
    }

    /**
     * 结果必为 2字节
     *
     * @param bigEndian
     * @param a
     * @return
     */
    public static byte[] shortToBytes(boolean bigEndian, short a) {
        if (bigEndian) {
            return new byte[]{
                    (byte) (a & 0xFF),
                    (byte) ((a >> 8) & 0xFF)
            };
        } else {
            return new byte[]{
                    (byte) ((a >> 8) & 0xFF),
                    (byte) (a & 0xFF)
            };
        }
    }

    public static int bytesToShortInt(byte[] b) {
        return b[1] & 0xFF |
                (b[0] & 0xFF) << 8;
    }

    public static int bytesToInt(byte[] b) {
        return b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;
    }

    public static int bytesToInt(boolean bigEndian, byte[] b) {
        if (bigEndian) {
            return b[0] & 0xFF |
                    (b[1] & 0xFF) << 8 |
                    (b[2] & 0xFF) << 16 |
                    (b[3] & 0xFF) << 24;
        } else {
            return b[3] & 0xFF |
                    (b[2] & 0xFF) << 8 |
                    (b[1] & 0xFF) << 16 |
                    (b[0] & 0xFF) << 24;
        }
    }

    /**
     * 结果为8byte
     *
     * @param bigEndian
     * @param a
     * @return
     */
    public static byte[] intToBytes(boolean bigEndian, int a) {
        if (bigEndian) {//    //大小端有点乱，后期梳理一下
            return new byte[]{
                    (byte) (a & 0xFF),
                    (byte) ((a >> 8) & 0xFF),
                    (byte) ((a >> 16) & 0xFF),//顺序调整过 有可能有问题
                    (byte) ((a >> 24) & 0xFF)
            };
        } else {
            return new byte[]{
                    (byte) ((a >> 24) & 0xFF),
                    (byte) ((a >> 16) & 0xFF),
                    (byte) ((a >> 8) & 0xFF),
                    (byte) (a & 0xFF)
            };
        }
    }

    public static byte[] longToBytes(boolean bigEndian, long a) {//bingEndian为true的情况暂不支持
        byte[] b = new byte[8];
        for (int i = 0; i < 8; i++) {
            int shift = (bigEndian ? i << 3 : (7 - i) * 8);
            b[i] = (byte) ((a >> shift) & 0xFF);
        }
        return b;
    }

    /**
     * 取后两位返回byte[2]
     *
     * @param bigEndian
     * @param a
     * @return
     */
    public static byte[] intToBytes2(boolean bigEndian, int a) {
        if (bigEndian) {
            return new byte[]{
                    (byte) ((a >> 8) & 0xFF),
                    (byte) (a & 0xFF)
            };
        } else {
            return new byte[]{
                    (byte) (a & 0xFF),
                    (byte) ((a >> 8) & 0xFF)
            };
        }
    }
}
