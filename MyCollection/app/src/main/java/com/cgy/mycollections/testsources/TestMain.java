package com.cgy.mycollections.testsources;

import android.content.Context;

import com.growingc.mediaoperator.beans.MediaInfo;

import appframe.utils.L;


import org.web3j.crypto.MnemonicUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;

import appframe.utils.IOUtils;
import appframe.utils.PinYinUtils;
import dalvik.system.DexFile;

/**
 * Created by RB-cgy on 2018/6/15.
 */
public class TestMain {
    //android项目下现在直接运行main可能报错，放到testsourcelib中
    public static void main(String[] args) {
        testMemory();
        testRound();
//        long currentTime = System.currentTimeMillis();
//        System.out.println(currentTime + "t:" + TimeUtils.getTime(currentTime));
//        System.out.println("t:" + TimeUtils.getTime(1496079745L * 1000));
////        1563791685827
////        1563186616
////        1496079745
////        testGetResources();
////        testPinyin();
//        System.out.println("t:" + (1.001f == (int) 1.001f));
//        System.out.println("a:" + ((int) (8f / 10f)));
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

    /**
     * 测试不同情况耗时问题
     * 结论：
     * 1.new 一个对象耗时比较多，大量计算时需要尽量避免无必要new
     * 2.clone操作比new操作耗时少，所以不得不new的时候可以考虑用clone替代
     */
    public static void testMemory() {
        long time = System.currentTimeMillis();

        MediaInfo m = new MediaInfo();
        long a = 355;
        Calendar b = Calendar.getInstance();
        for (int i = 0; i < 1000; i++) {
            Calendar c = (Calendar) b.clone();
//            Calendar d = Calendar.getInstance();
            a += 20;
            a = a * 13;
            if (a > 2000) {
                a -= 2000;
            }
        }
        System.out.println("11 cost time:" + (System.currentTimeMillis() - time));

        time = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            Calendar c = Calendar.getInstance();
//            Calendar c = (Calendar) b.clone();
        }
        System.out.println("222 cost time:" + (System.currentTimeMillis() - time));

    }

    public static void testPinyin() {
        System.out.println("-----------------------------------------------------");
        System.out.println("1:" + PinYinUtils.getFirstLetter("陈d订单", true));
        System.out.println("2:" + PinYinUtils.getFirstLetters("ceed", true));
        System.out.println("3:" + PinYinUtils.getFirstLetters("陈f.@@110),", false));
        System.out.println("/////////////////////////////////////");
        System.out.println("a:" + PinYinUtils.getPinyinString("陈f.@@110)"));
        System.out.println("b:" + PinYinUtils.getPinyinString("陈好啊 啊"));
        System.out.println("-----------------------------------------------------");
    }

    public static void testGetResources() {
        URL url = MnemonicUtils.class.getClassLoader().getResource("en-mnemonic-word-list.txt");
        System.out.println("url:" + url);
        try {
//            url:jar:file:/data/app/com.cgy.mycollections-2/base.apk!/en-mnemonic-word-list.txt
            //该路径用FileReader会报文件不存在，打在jar包中的东西无法用file 必须用getResourceAsStream
            System.out.println("toURI:" + url.toURI().getSchemeSpecificPart());
            InputStream stream = MnemonicUtils.class.getClassLoader().getResourceAsStream("en-mnemonic-word-list.txt");

            String result = IOUtils.toString(stream);
            String[] spl = result.split("\n");
            List<String> list = Arrays.asList(spl);
//            List<String> list = readAllLines(url.toURI().getSchemeSpecificPart());//读不到
            L.e(" list.size():" + list.size());
            L.e(" list.size():" + list.get(0) + list.get(0).length());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> readAllLines(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        List<String> data = new ArrayList<String>();
        for (String line; (line = br.readLine()) != null; ) {
            data.add(line);
        }
        return data;
    }

    public static void testGetDexResoureces(Context context) {
        DexFile dexFile = null;
        try {
            dexFile = new DexFile(context.getPackageCodePath());

            Enumeration<String> enumeration = dexFile.entries();
            while (enumeration.hasMoreElements()) {
                String className = enumeration.nextElement();
                L.e("classname", className);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
