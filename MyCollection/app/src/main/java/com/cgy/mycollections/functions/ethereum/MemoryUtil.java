package com.cgy.mycollections.functions.ethereum;

import com.cgy.mycollections.MyApplication;
import com.cgy.mycollections.utils.L;

import org.web3j.crypto.MnemonicUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import appframe.utils.IOUtils;

/**
 * Description :
 * Author :cgy
 * Date :2018/12/11
 */
public class MemoryUtil {
    /**
     * web3j 中助记词的WORD_LIST 字段用了jar包中的资源文件，但是获取到的是空的，所以要手动给它赋值
     */
    public static void injectWordList() {
        try {
            List<String> list = getWordList();

            //获取Bean类的INT_VALUE字段
            Field field = MnemonicUtils.class.getDeclaredField("WORD_LIST");
            //将字段的访问权限设为true：即去除private修饰符的影响
            field.setAccessible(true);
            /*去除final修饰符的影响，将字段设为可修改的*/
            Field modifiersField = Field.class.getDeclaredField("accessFlags");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
            //把字段值设为200
            field.set(null, list);

//            List<String> resultList = (List<String>) field.get(null);
//            L.e("resultList:" + (resultList.get(0)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> getWordList() {
        List<String> list = new ArrayList<>();
        try {
//            InputStream stream = MyApplication.getInstance().getResources().getAssets().open("en-mnemonic-word-list.txt");//需要拷到asset中
            InputStream stream = MnemonicUtils.class.getClassLoader().getResourceAsStream("en-mnemonic-word-list.txt");//直接使用jar包中的txt

            String result = IOUtils.toString(stream);
            String[] spl = result.split("\n");
            list = Arrays.asList(spl);
//            L.e(" list.size():" + list.size());
//            L.e(" list.size():" + list.get(0)+list.get(0).length());
        } catch (Exception e) {
            e.printStackTrace();
        }


        return list;
    }
}
