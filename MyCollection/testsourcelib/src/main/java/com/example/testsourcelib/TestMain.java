package com.example.testsourcelib;


import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.CompletableFuture;

/**
 * Created by RB-cgy on 2018/6/15.
 */
public class TestMain {

    public static void main(String[] args) {
//        testRound();
//        testDirectSetOrAdd();
        getMaxNotRepeatStringLenInString("qwertyuiopqqasdfghjklzaazxcvbnmlkjzz");
    }

    public static void testCompletableFuture() {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

    }

    /**
     * 找到一串输入字符串中 最大不重复字符串的长度
     * <p>
     * 例  fffdsfdtlsdj  这串字符串中最大不重复串为 fdtls 即长度为 5
     *
     * @param input
     * @return
     */
    public static void getMaxNotRepeatStringLenInString(String input) {
        //TODO:
        if (input == null || input.length() == 0) {
            System.out.println("null string len is 0");
            return;
        }

        HashSet<Character> charSet = new HashSet<>();
        HashMap<Integer, String> maxStrings = new HashMap<>();
        int rightIndex = 0;
        int maxLen = 0;
        for (int i = 0, len = input.length(); i < len; i++) {
            while (rightIndex < len && !charSet.contains(input.charAt(rightIndex))) {
                charSet.add(input.charAt(rightIndex));
                rightIndex++;
            }
            maxLen = Math.max(maxLen, charSet.size());
            if (charSet.size() == maxLen) {
                String pre = maxStrings.get(maxLen);
                String maxString = input.substring(i, rightIndex);
                maxStrings.put(maxLen, pre == null ? maxString : pre + "," + maxString);
            }
            if (rightIndex == len) {
                //已经到最后一个了，不可能再增加了
                System.out.println(input + "--maxLen->" + maxLen);
                System.out.println("--all strings->" + maxStrings.get(maxLen));
                break;
            }

            charSet.remove(input.charAt(i));
        }
    }

    /**
     * 测试直接赋值和通过加减赋值性能差距
     * 10000000 级别都是2ms 几乎可以忽略不记
     */
    private static void testDirectSetOrAdd() {
        int a = 1;
        long currentTime = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            int b = i;
        }
        System.out.println("direct cost time :" + (System.currentTimeMillis() - currentTime));
        currentTime = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            int b = i + 1;
        }
        System.out.println("add cost time :" + (System.currentTimeMillis() - currentTime));
    }

    /**
     * 测试四舍五入
     */
    public static void testRound() {
        System.out.println("向上取整:" + (int) Math.ceil(96.1));// 97 (去掉小数凑整:不管小数是多少，都进一)
        System.out.println("向下取整" + (int) Math.floor(96.8));// 96 (去掉小数凑整:不论小数是多少，都不进位)
        System.out.println("四舍五入取整:" + Math.round(96.1));// 96 (这个好理解，不解释)
        System.out.println("四舍五入取整:" + Math.round(96.8));// 97
    }


}
