package com.cgy.mycollections.utils.dataparse;

import java.util.List;
import java.util.Locale;

/**
 * Description :数据格式解析整合 ， 二进制 16进制 string等互转
 * Author :cgy
 * Date :2020/1/20
 */
public class DataFormater {

    private final static String _BYTES = "0123456789ABCDEF";

    private final static char[] mChars = "0123456789ABCDEF".toCharArray();
    private final static String mHexStr = "0123456789ABCDEF";

    //<editor-fold desc="string和byte互转">

    /**
     * 检查是否是16进制字符串
     *
     * @param sHex String 16进制字符串
     * @return boolean
     */
    public static boolean isValidHexString(String sHex) {
        String sTmp = sHex.trim().replace(" ", "").toUpperCase(Locale.US);
        int iLen = sTmp.length();

        if (iLen > 1 && iLen % 2 == 0) {
            for (int i = 0; i < iLen; i++)
                if (!mHexStr.contains(sTmp.substring(i, i + 1)))
                    return false;
            return true;
        } else
            return false;
    }


    /**
     * unicode的String转换成String的字符串
     *
     * @param hex String 16进制值字符串 （一个unicode为2byte）
     * @return String 全角字符串
     * @see
     */
    public static String unicodeToString(String hex) {
        int t = hex.length() / 6;
        int iTmp = 0;
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < t; i++) {
            String s = hex.substring(i * 6, (i + 1) * 6);
            // 将16进制的string转为int
            iTmp = (Integer.valueOf(s.substring(2, 4), 16) << 8) | Integer.valueOf(s.substring(4), 16);
            // 将int转换为字符
            str.append(new String(Character.toChars(iTmp)));
        }
        return str.toString();
    }


    /**
     * 十六进制字符串转换成 ASCII字符串
     *
     * @return String 对应的字符串
     */
    public static String hexStr2Str(String hexStr) {
        hexStr = hexStr.toString().trim().replace(" ", "").toUpperCase(Locale.US);
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int iTmp = 0x00;

        for (int i = 0; i < bytes.length; i++) {
            iTmp = mHexStr.indexOf(hexs[2 * i]) << 4;
            iTmp |= mHexStr.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (iTmp & 0xFF);
        }
        return new String(bytes);
    }

    /**
     * bytes字符串转换为Byte值
     *
     * @param src String Byte字符串，每个Byte之间没有分隔符(字符范围:0-9 A-F)
     * @return byte[]
     */
    public static byte[] hexStr2Bytes(String src) {
        /*对输入值进行规范化整理*/
        src = src.trim().replace(" ", "").toUpperCase(Locale.US);
        //处理值初始化
        int m = 0, n = 0;
        int iLen = src.length() / 2; //计算长度
        byte[] ret = new byte[iLen]; //分配存储空间

        for (int i = 0; i < iLen; i++) {
            m = i * 2 + 1;
            n = m + 1;
            ret[i] = (byte) (Integer.decode("0x" + src.substring(i * 2, m) + src.substring(m, n)) & 0xFF);
        }
        return ret;
    }

    public static String byte2HexStr(byte[] b) {
        StringBuilder sb = new StringBuilder();
        int iLen = b.length;
        for (int n = 0; n < iLen; n++) {
            sb.append(mChars[(b[n] & 0xFF) >> 4]);
            sb.append(mChars[b[n] & 0x0F]);
            sb.append(' ');
        }
        return sb.toString().trim().toUpperCase(Locale.US);
    }

    /**
     * bytes转换成十六进制字符串
     *
     * @param b    byte[] byte数组
     * @param iLen int 取前N位处理 N=iLen
     * @return String 每个Byte值之间空格分隔
     */
    public static String byte2HexStr(byte[] b, int iLen) {
        StringBuilder sb = new StringBuilder();
        for (int n = 0; n < iLen; n++) {
            sb.append(mChars[(b[n] & 0xFF) >> 4]);
            sb.append(mChars[b[n] & 0x0F]);
            sb.append(' ');
        }
        return sb.toString().trim().toUpperCase(Locale.US);
    }
//    private final static String _BYTES = "0123456789ABCDEF";

//    public static String byteToStr(byte b) {
//        int val = b;
//        if (val < 0) {
//            val += 256;
//        }
//
//        char[] chs = new char[2];
//        chs[0] = _BYTES.charAt(val / 16);
//        chs[1] = _BYTES.charAt(val % 16);
//        return new String(chs);
//    }


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

    public static byte strToByte(String str) {
        str = str.toUpperCase();
        int hi = str.charAt(0) - '0';
        int lo = str.charAt(1) - '0';
        if (hi > 9) hi = hi - 7;
        if (lo > 9) lo = lo - 7;
        return (byte) ((hi << 4) + lo);
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

    /**
     * String的字符串转换成unicode的String
     *
     * @param strText String 全角字符串
     * @return String 每个unicode之间无分隔符
     */
    public static String strToUnicode(String strText) throws Exception {
        char c;
        StringBuilder str = new StringBuilder();
        int intAsc;
        String strHex;
        for (int i = 0; i < strText.length(); i++) {
            c = strText.charAt(i);
            intAsc = (int) c;
            strHex = Integer.toHexString(intAsc);
            if (intAsc > 128)
                str.append("\\u");
            else // 低位在前面补00
                str.append("\\u00");
            str.append(strHex);
        }
        return str.toString();
    }

    /**
     * 字符串转换成十六进制字符串
     *
     * @param str String 待转换的ASCII字符串
     * @return String 每个Byte之间空格分隔，如: [61 6C 6B]
     */
    public static String str2HexStr(String str) {
        StringBuilder sb = new StringBuilder();
        byte[] bs = str.getBytes();

        for (int i = 0; i < bs.length; i++) {
            sb.append(mChars[(bs[i] & 0xFF) >> 4]);
            sb.append(mChars[bs[i] & 0x0F]);
            sb.append(' ');
        }
        return sb.toString().trim();
    }
    //</editor-fold>

    //<editor-fold desc="short和byte,string互转">


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

    public static String shortToStr(short s) {
        StringBuffer sb = new StringBuffer();
        sb.append(byteToStr((byte) ((s & 0xff00) >> 8)));
        sb.append(byteToStr((byte) (s & 0xff)));
        return sb.toString();
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
    //</editor-fold>


    //<editor-fold desc="int和string，byte互转 ">


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

    public static String intToHexStr(int s) {
        StringBuffer sb = new StringBuffer();
        sb.append(byteToStr((byte) ((s & 0xff000000) >> 24)));
        sb.append(byteToStr((byte) ((s & 0xff0000) >> 16)));
        sb.append(byteToStr((byte) ((s & 0xff00) >> 8)));
        sb.append(byteToStr((byte) (s & 0xff)));
        return sb.toString();
    }

    /**
     * int转byte数组 一个int 32位bit 所以等于4个byte
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

    /**
     * int取short内容 即后两位返回byte[2]
     *
     * @param bigEndian
     * @param a
     * @return
     */
    public static byte[] intToShortBytes(boolean bigEndian, int a) {
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

    //</editor-fold>

    //<editor-fold desc="long和string，byte互转">

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


    public static byte[] longToBytes(boolean bigEndian, long a) {//bingEndian为true的情况暂不支持
        byte[] b = new byte[8];
        for (int i = 0; i < 8; i++) {
            int shift = (bigEndian ? i << 3 : (7 - i) * 8);
            b[i] = (byte) ((a >> shift) & 0xFF);
        }
        return b;
    }
    //</editor-fold>

    //<editor-fold desc="bytes合并，截取等操作">

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
    //</editor-fold>

    //<editor-fold desc="test ">

    //</editor-fold>


}
