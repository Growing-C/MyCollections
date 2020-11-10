package com.example.testsourcelib;


/**
 * Created by RB-cgy on 2018/6/15.
 */
public class TestMain {
    public static void main(String[] args) {
//        testRound();
        testDirectSetOrAdd();

    }

    /**
     *  测试直接赋值和通过加减赋值性能差距
     *  10000000 级别都是2ms 几乎可以忽略不记
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
