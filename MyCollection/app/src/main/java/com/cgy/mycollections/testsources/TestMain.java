package com.cgy.mycollections.testsources;

import android.content.Context;

import appframe.utils.L;
import com.cgy.mycollections.utils.PinYinUtils;

import org.web3j.crypto.MnemonicUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import appframe.utils.IOUtils;
import appframe.utils.TimeUtils;
import dalvik.system.DexFile;

/**
 * Created by RB-cgy on 2018/6/15.
 */
public class TestMain {
    public static void main(String[] args) {
        long currentTime = System.currentTimeMillis();
        System.out.println(currentTime+"t:" + TimeUtils.getTime(currentTime));
        System.out.println("t:" + TimeUtils.getTime(1496079745L * 1000));
//        1563791685827
//        1563186616
//        1496079745
//        testGetResources();
//        testPinyin();
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
