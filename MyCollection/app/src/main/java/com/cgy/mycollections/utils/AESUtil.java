package com.cgy.mycollections.utils;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by sh on 2019/1/2.
 * <p>
 * AES加解密都用这个，其他两个util有问题
 */
public class AESUtil {
    private static final String KEY_ALGORITHM = "AES";
    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
    public static final String DEVICE_AES256_DEFAULT_KEY = "b3f84e5b10f735c82b932c8fb349f560";//默认用的scretKey 要和后台一致

    /**
     * Used to build output as Hex
     */
    private static final char[] DIGITS = {
            '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    };

    /**
     * Encodes the specifed byte array to a character array and then returns that character array
     * as a String.
     *
     * @param bytes the byte array to Hex-encode.
     * @return A String representation of the resultant hex-encoded char array.
     */
    public static String encodeToString(byte[] bytes) {
        char[] encodedChars = encode(bytes);
        return new String(encodedChars);
    }

    /**
     * Converts an array of bytes into an array of characters representing the hexidecimal values of each byte in order.
     * The returned array will be double the length of the passed array, as it takes two characters to represent any
     * given byte.
     *
     * @param data byte[] to convert to Hex characters
     * @return A char[] containing hexidecimal characters
     */
    public static char[] encode(byte[] data) {

        int l = data.length;

        char[] out = new char[l << 1];

        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS[0x0F & data[i]];
        }

        return out;
    }

    /**
     * Converts an array of character bytes representing hexidecimal values into an
     * array of bytes of those same values. The returned array will be half the
     * length of the passed array, as it takes two characters to represent any
     * given byte. An exception is thrown if the passed char array has an odd
     * number of elements.
     *
     * @param array An array of character bytes containing hexidecimal digits
     * @return A byte array containing binary data decoded from
     * the supplied byte array (representing characters).
     * @throws IllegalArgumentException Thrown if an odd number of characters is supplied
     *                                  to this function
     * @see #decode(char[])
     */
    public static byte[] decode(byte[] array) throws IllegalArgumentException {
        String s = null;
        try {
            s = new String(array, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decode(s);
    }

    /**
     * Converts the specified Hex-encoded String into a raw byte array.  This is a
     * convenience method that merely delegates to {@link #decode(char[])} using the
     * argument's hex.toCharArray() value.
     *
     * @param hex a Hex-encoded String.
     * @return A byte array containing binary data decoded from the supplied String's char array.
     */
    public static byte[] decode(String hex) {
        return decode(hex.toCharArray());
    }

    /**
     * Converts an array of characters representing hexidecimal values into an
     * array of bytes of those same values. The returned array will be half the
     * length of the passed array, as it takes two characters to represent any
     * given byte. An exception is thrown if the passed char array has an odd
     * number of elements.
     *
     * @param data An array of characters containing hexidecimal digits
     * @return A byte array containing binary data decoded from
     * the supplied char array.
     * @throws IllegalArgumentException if an odd number or illegal of characters
     *                                  is supplied
     */
    public static byte[] decode(char[] data) throws IllegalArgumentException {

        int len = data.length;

        if ((len & 0x01) != 0) {
            throw new IllegalArgumentException("Odd number of characters.");
        }

        byte[] out = new byte[len >> 1];

        // two characters form the hex value.
        for (int i = 0, j = 0; j < len; i++) {
            int f = toDigit(data[j], j) << 4;
            j++;
            f = f | toDigit(data[j], j);
            j++;
            out[i] = (byte) (f & 0xFF);
        }

        return out;
    }

    /**
     * Converts a hexadecimal character to an integer.
     *
     * @param ch    A character to convert to an integer digit
     * @param index The index of the character in the source
     * @return An integer
     * @throws IllegalArgumentException if ch is an illegal hex character
     */
    protected static int toDigit(char ch, int index) throws IllegalArgumentException {
        int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new IllegalArgumentException("Illegal hexadecimal charcter " + ch + " at index " + index);
        }
        return digit;
    }

    /**
     * AES加密
     *
     * @param password
     * @param hexKey   16进制的密钥
     * @return
     */
    public static String encryptAes(String password, String hexKey) {
        try {
            Key key = new SecretKeySpec(decode(hexKey), KEY_ALGORITHM);
            return encryptAes(password, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES加密
     *
     * @param password
     * @param key      密钥
     * @return
     */
    public static String encryptAes(String password, Key key) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] doFinal = cipher.doFinal(password.getBytes());
            return AESUtil.encodeToString(doFinal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES解密
     *
     * @param cipherText
     * @param hexKey     16进制的密钥
     * @return
     */
    public static String decryptAes(String cipherText, String hexKey) {
        try {
            Key key = new SecretKeySpec(AESUtil.decode(hexKey), KEY_ALGORITHM);
            return decryptAes(cipherText, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES解密
     *
     * @param cipherText
     * @param key        密钥
     * @return
     */
    public static String decryptAes(String cipherText, Key key) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] doFinal2 = cipher.doFinal(AESUtil.decode(cipherText));
            return new String(doFinal2, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成key
     *
     * @param key
     * @return
     */
    public static Key generateKey(String key) {
        try {
            KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(key.getBytes());
            kg.init(128, secureRandom);
            return kg.generateKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成16进制的key
     *
     * @param key
     * @return 16进制的key
     */
    public static String generateHexKey(String key) {
        Key generateKey = generateKey(key);
        if (generateKey != null) {
            return AESUtil.encodeToString(generateKey.getEncoded());
        }
        return null;
    }

    public static String md5(byte[] inputBytes) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(inputBytes);
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void test() {
        String data = "123456";
        System.out.println("md5:" + md5(data.getBytes()));
        System.out.println(BinaryUtil.bytesToStr(true, BinaryUtil.strToBytes(true, md5(data.getBytes()))));

        String des = null;
        try {
            des = AESUtil.encryptAes(data, DEVICE_AES256_DEFAULT_KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("des:" + des);
        String source = null;
        try {
            source = AESUtil.decryptAes(des, DEVICE_AES256_DEFAULT_KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("source:" + source);
    }

    public static void main(String[] args) {
//        test();

//        testbyteStr();
//        testFukiCommand();
    }

    private static void testbyteStr() {
        byte[] aa = "C11A1FCD10000053".getBytes();
        System.out.println("转成16进制byte：" + CHexConverter.byte2HexStr(aa, aa.length));
        System.out.println("aa 转成str：" + new String(aa));
    }


//    //模拟fuki锁具接收到指令的情况
//    private static void testFukiCommand() {
////        String demoStr = "20202020FEFE6C860001070001000300C060013B20202020";
////        byte[] demoBytes = BinaryUtil.strToBytes(true, demoStr);
////        onCharacteristicChange(demoBytes);
//
//        String s1 = "20202020FEFE6C860001";
//        String s2 = "070001000300C0";
//        String s3 = "60013B2020202020202020FEFE6C860001070001000300";
//        String s4 = "C060013B20202020";
//        String s9 = "FEFE202020FEFE20";
//        byte[] b1 = BinaryUtil.strToBytes(true, s1);
//        byte[] b2 = BinaryUtil.strToBytes(true, s2);
//        byte[] b3 = BinaryUtil.strToBytes(true, s3);
//        byte[] b4 = BinaryUtil.strToBytes(true, s4);
//        onCharacteristicChange(b1);
//        onCharacteristicChange(b2);
//        onCharacteristicChange(b3);
//        onCharacteristicChange(b4);
//        onCharacteristicChange(BinaryUtil.strToBytes(true, s9));
//    }
//
//    private static List<byte[]> mByteList = new ArrayList<>();//数据分包缓存
//    private static final String COMMAND_START = "FEFE";
//    private static final String COMMAND_END = "3B";
//
//    private static void onCharacteristicChange(byte[] source) {
//        if (source == null || source.length == 0)
//            return;
//        String commandString = BinaryUtil.bytesToStr(true, source);
//        System.out.println("commandString:" + commandString);
//        int sourBytesLen = source.length;
//        int commandBodyLength;
//        int realCommandLen;
//
//        if (mByteList.size() > 0) {//已经分包，且0位置必定是第一个包
//            byte[] existedBytes = BinaryUtil.mergeBytes(mByteList);
//            int existedLength = existedBytes.length;//已有的长度，是所有mByteList的长度之和
//            System.out.println("333 已经分过包了，准备处理当钱包，已有包字节长度：" + existedLength);
//
//            if (existedLength <= 7) {
//                //缓存包里面没有包含body长度
//                //默认 一次获取到指令必定大于10位，所以最差第二个包必须有body长度位，否则会有问题！
//                existedBytes = BinaryUtil.mergeBytes(existedBytes, source);
//            }
//            //到这里existedBytes必须长度大于7，懒得判断了，一般来说应该满足
//            commandBodyLength = BinaryUtil.bytesToInt(new byte[]{0, 0, existedBytes[7], existedBytes[6]});
//            realCommandLen = commandBodyLength + 9;//body长度+9=指令长度
//            int remainLen = realCommandLen - existedLength;//还需要的长度
//            if (sourBytesLen >= remainLen) {
//                //这个包已经包含了该条指令余下的内容
//                mByteList.add(BinaryUtil.subBytes(source, 0, remainLen));
//                onGetCommandBytes(BinaryUtil.mergeBytes(mByteList));
//            } else {
//                //这个包 还只是该条指令其中的一个包，还有下一个包
//                mByteList.add(source);
//                System.out.println("333-2 结束一次包处理，添加分包，已分包次数：" + mByteList.size());
//                return;
//            }
//        }
//
//
//        //看看是否包含一个指令
//        int headIndex = commandString.indexOf(COMMAND_START);
//        if (headIndex < 0) {
//            //没有开头
//            return;
//        }
//        int headIndexInBytes = headIndex / 2;
//        int endHeadIndexInBytes = commandString.lastIndexOf(COMMAND_START) / 2;
//
//        //包含指令头，不管是否有多个，一律清空已有的分包缓存
//        mByteList.clear();
//        if (headIndexInBytes == endHeadIndexInBytes) {
//            //说明返回的只有一条指令,此时必须是一条指令的开始
//            System.out.println("111 指令头 位置：" + headIndexInBytes);
//            if (sourBytesLen > headIndexInBytes + 7) {
//                //可以截取到body长度的bytes
//                // 第一个分包的index 6,7是Body长度。本来是2B，加两个0，用来转int
//                commandBodyLength = BinaryUtil.bytesToInt(new byte[]{0, 0, source[headIndexInBytes + 7], source[headIndexInBytes + 6]});
//                realCommandLen = commandBodyLength + 9;//body长度+9=指令长度
//
//                if (sourBytesLen >= realCommandLen + headIndexInBytes) {
//                    //说明包含了整条指令，直接截取
//                    byte[] realCommandBytes = BinaryUtil.subBytes(source, headIndexInBytes, realCommandLen);
//
//                    onGetCommandBytes(realCommandBytes);
//                    return;
//                }
//            }
//            //有分包,包含两种情况
//            //1.当前收到的包 包含body长度位，但是没有完整的包含指令
//            //2.body长度位bytes不完全在这一条指令中
//            byte[] headPartBytes = BinaryUtil.subBytes(source, headIndexInBytes, sourBytesLen - headIndexInBytes);
//            mByteList.add(headPartBytes);
//            System.out.println("222 首次处理包，添加分包，已分包次数：" + mByteList.size());
//        } else {//多于两条指令的情况暂时不考虑
//            System.out.println("一个包中有多个FEFE请求头的情况未处理");
//        }
//    }
//
//    /**
//     * 获取到完整的指令bytes，懒得做非空验证了，调的时候确保非空吧
//     *
//     * @param commandBytes
//     */
//    private static void onGetCommandBytes(byte[] commandBytes) {
//        mByteList.clear();//获取到一条指令了，就直接清空缓存指令
//        System.out.println("onGetCommandBytes：" + CHexConverter.byte2HexStr(commandBytes));
//        String commandString = BinaryUtil.bytesToStr(true, commandBytes);
//
//        //检验一下头和结尾对不对
//        if (commandString.startsWith(COMMAND_START) && commandString.endsWith(COMMAND_END)) {
//            System.out.println("444 结束一次指令包处理，获取到了正确格式的指令：" + commandString);
//            //此处回调指令处理逻辑
//
//        } else {
//            System.out.println("这个指令不对啊：" + commandString);
//        }
//    }
}
