package com.cgy.mycollections.testsources;

import com.cgy.mycollections.utils.dataparse.DataFormater;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.web3j.utils.Numeric;

/**
 * Description :数据类型测试
 * Author :cgy
 * Date :2019/11/22
 */
public class DataTest {
    public static void main(String[] args) {
        testJson();
    }

    private static void testBinary() {
        byte[] aa = "C11A1FCD10000053".getBytes();
        System.out.println("转成16进制byte：" + DataFormater.byte2HexStr(aa));
        System.out.println("aa 转成str：" + new String(aa));
        System.out.println("raw bytes：" + DataFormater.bytesToStr(true, aa));

        String hexS = "73b0501e1af66be7707a267f53d6e6a2ea75999ea3849ddc15fef2084bae6135ea46af7fba6819d10000000000000000000000000000000000000000000000000de0b6b3a76400005dd7a2cde16636185ea8d8db";
        System.out.println("CHexConverter：" + DataFormater.byte2HexStr(DataFormater.hexStr2Bytes(hexS)));
        System.out.println("Numeric：" + Numeric.toHexString(Numeric.hexStringToByteArray(hexS)));
//        CHexConverter：73 B0 50 1E 1A F6 6B E7 70 7A 26 7F 53 D6 E6 A2 EA 75 99 9E A3 84 9D DC 15 FE F2 08 4B AE 61 35 EA 46 AF 7F BA 68 19 D1 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 0D E0 B6 B3 A7 64 00 00 5D D7 A2 CD E1 66 36 18 5E A8 D8 DB
//        Numeric：0x73b0501e1af66be7707a267f53d6e6a2ea75999ea3849ddc15fef2084bae6135ea46af7fba6819d10000000000000000000000000000000000000000000000000de0b6b3a76400005dd7a2cde16636185ea8d8db

    }

    public static void testJson() {
        String source = "{[\"1\",\"2\",\"3\"]}";
        String[] s = new String[]{"1", "2", "3"};
        Gson gson = new Gson();
        String jsonS = gson.toJson(s);
        System.out.println("jsonS:" + jsonS);
        String[] sP = gson.fromJson(jsonS, String[].class);
        for (String ss :
                sP) {
            System.out.println("ss:" + ss);
        }
//        Type listType = new TypeToken<List<UserBean>>() {}.getType();
        try {
            JSONArray a = new JSONArray(sP);
            System.out.println("a:" + a.toString());
            JSONArray JSONArray = new JSONArray(a.toString());
            System.out.println("get2:" + JSONArray.get(2));
            System.out.println("JSONArray.toString:" + JSONArray.toString());
            System.out.println("source.toString:" + new JSONArray(source).toString());//这个会报错

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
