package appframe.utils;

import java.io.UnsupportedEncodingException;

/**
 * Created by Elon_wang on 2017/5/10.
 */

public class PinyinUtils {

    static final int GB_SP_DIFF = 160;
    // 存放国标一级汉字不同读音的起始区位码
    static final int[] secPosValueList = {1601, 1637, 1833, 2078, 2274, 2302,
            2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858, 4027,
            4086, 4390, 4558, 4684, 4925, 5249, 5600};
    // 存放国标一级汉字不同读音的起始区位码对应读音
    static final char[] firstLetter = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
            'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'w', 'x',
            'y', 'z'};

    //取汉字首字母
    public static Character getFirstLetter(char ch) {

        byte[] uniCode = null;
        try {
            uniCode = String.valueOf(ch).getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return 'U';
        }
        if (uniCode[0] < 128 && uniCode[0] > 0) { // 非汉字
            return ch;
        } else {
            return convert(uniCode);
        }
    }

    /**
     * 获取一个汉字的拼音首字母。 GB码两个字节分别减去160，转换成10进制码组合就可以得到区位码
     * 例如汉字“你”的GB码是0xC4/0xE3，分别减去0xA0（160）就是0x24/0x43
     * 0x24转成10进制就是36，0x43是67，那么它的区位码就是3667，在对照表中读音为‘n’
     */
    static char convert(byte[] bytes) {
        char result = '-';
        int secPosValue = 0;
        int i;
        for (i = 0; i < bytes.length; i++) {
            bytes[i] -= GB_SP_DIFF;
        }
        secPosValue = bytes[0] * 100 + bytes[1];
        for (i = 0; i < 23; i++) {
            if (secPosValue >= secPosValueList[i]
                    && secPosValue < secPosValueList[i + 1]) {
                result = firstLetter[i];
                break;
            }
        }
        return result;
    }
//TODO:要使用以下功能需要导入lib中的pingyin4j.jar
//    /**
//     * 判断后一个字符串是否包含前一个，如果前一个是拼音就把后一个转换成拼音来匹配
//     *
//     * @param target
//     * @param source
//     * @return
//     */
//    public static boolean containsFirst(String target, String source) {
//        if (TextUtils.isEmpty(target) || TextUtils.isEmpty(source))//为空则返回false,确保之后的判断中都不为空
//            return false;
//
//        if (target.matches("[a-zA-Z]+")) {//第一个参数都是拼音，则把第二个转换成拼音来匹配
//            String sourcePinyin = PinyinUtils.parse2Pinyin(source).toUpperCase();//都用大写来匹配
//            return sourcePinyin.contains(target.toUpperCase());//TODO:拼音匹配待完善，例如两个汉字的首字母在一起应该能匹配到两个汉字
//        } else {//第一个不全是拼音，则直接匹配
//            return source.contains(target);
//        }
//    }

//    /**
//     * 第一个字符串在第二个中的相同字符串的位置，包含字母匹配中文功能
//     *
//     * @param target
//     * @param source
//     * @return
//     */
//    public static int indexOfFirstStr(String target, String source) {
//        if (TextUtils.isEmpty(target) || TextUtils.isEmpty(source))//为空则返回false,确保之后的判断中都不为空
//            return -1;
//
//        if (target.matches("[a-zA-Z]+")) {//第一个参数都是拼音，则把第二个转换成拼音来匹配
//            //TODO:拼音匹配待完成
//            if (!TextUtils.isEmpty(target))//TODO:暂时返回-1，待完成删除
//                return -1;
//
//            char[] srcCharArray = source.toCharArray();//转换成char数组
//            String[] tempStringArray;//临时的stringArray保存
//            String pinyinOutput = "";//输出
//            int stringLength = srcCharArray.length;
//
//            HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
//            format.setCaseType(HanyuPinyinCaseType.UPPERCASE);//大写
//            format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);//无音调
//            format.setVCharType(HanyuPinyinVCharType.WITH_V);//用v表示ü  (nv)
//
//            try {
//                for (int i = 0; i < stringLength; i++) {
//                    // 判断是否为汉字字符
//                    if (Character.toString(srcCharArray[i]).matches(
//                            "[\\u4E00-\\u9FA5]+")) {
//                        tempStringArray = PinyinHelper.toHanyuPinyinStringArray(srcCharArray[i], format);//有的汉字（如好，当等）用这个方法会返回数组包含两个相同的拼音字符串，不明觉厉
//                        pinyinOutput += tempStringArray[0];
//                    } else
//                        pinyinOutput += Character.toString(srcCharArray[i]);
//                }
//            } catch (BadHanyuPinyinOutputFormatCombination e1) {
//                e1.printStackTrace();
//            }
//
//            return pinyinOutput.indexOf(target.toUpperCase());
//        } else {//第一个不全是拼音，则直接匹配
//            return source.indexOf(target);
//        }
//    }


//    /**
//     * 把字符串转换成拼音
//     *
//     * @param src
//     * @return
//     */
//    public static String parse2Pinyin(String src) {
//        char[] srcCharArray = src.toCharArray();//转换成char数组
//        String[] tempStringArray;//临时的stringArray保存
//        String pinyinOutput = "";//输出
//        int stringLength = srcCharArray.length;
//
//        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
//        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);//大写
//        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);//无音调
//        format.setVCharType(HanyuPinyinVCharType.WITH_V);//用v表示ü  (nv)
//
//        try {
//            for (int i = 0; i < stringLength; i++) {
//                // 判断是否为汉字字符
//                if (Character.toString(srcCharArray[i]).matches(
//                        "[\\u4E00-\\u9FA5]+")) {
////                    System.out.println(i + "-->srcCharArray[i]:" + srcCharArray[i]);
//                    tempStringArray = PinyinHelper.toHanyuPinyinStringArray(srcCharArray[i], format);//有的汉字（如好，当等）用这个方法会返回数组包含两个相同的拼音字符串，不明觉厉
////                    System.out.println("tempStringArray len:" + tempStringArray.length);
//                    pinyinOutput += tempStringArray[0];
//                } else
//                    pinyinOutput += Character.toString(srcCharArray[i]);
//            }
//            return pinyinOutput;
//        } catch (BadHanyuPinyinOutputFormatCombination e1) {
//            e1.printStackTrace();
//        }
//        return pinyinOutput;
//    }
}
