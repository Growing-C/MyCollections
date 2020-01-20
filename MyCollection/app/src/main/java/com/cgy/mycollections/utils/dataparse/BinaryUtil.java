package com.cgy.mycollections.utils.dataparse;

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





    public static boolean isAllZero(byte[] bytes, int offset, int len) {
        for (int i = 0; i < len; i++) {
            if (bytes[offset + i] != 0x00) {
                return false;
            }
        }

        return true;
    }



}
