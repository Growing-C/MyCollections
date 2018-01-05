package appframe.fortest;




import java.io.InputStream;

import appframe.app.MyApplication;
import appframe.utils.IOUtils;
import appframe.utils.LogUtils;

/**
 * 从assets中读取mock的json数据
 * Created by RB-cgy on 2016/9/29.
 */
public class MockDataGenerator {

    public static String getMockDataFromJsonFile(String path) {
        String json = "";
        LogUtils.d("getMockDataFromJsonFile");
        try {
            InputStream inputStream = MyApplication.getInstance().getAssets().open(path);
            json = IOUtils.toString(inputStream);

//            LogUtils.d("MockDataGenerator:json？？:" + json);//.json文件格式读取无法打印log,
            // （谬误，似乎任何文件格式读取出的jsonString都无法打印,必须用JsonObject转换然后toString才能打印）
//            LogUtils.d(json.length() + " MockDataGenerator:json:" + new JSONObject(json).toString());
        } catch (Exception e) {
            LogUtils.d("getMockDataFromJsonFile IOException");
            e.printStackTrace();
        }

        return json;
    }
//
//    /**
//     * 读取输入流数据，返回一个byte数组
//     *
//     * @param inStream
//     * @return
//     * @throws Exception
//     */
//    public static byte[] readInputStream(InputStream inStream) throws IOException {
//        byte[] buffer = new byte[1024];
//        int len = -1;
//        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
//        while ((len = inStream.read(buffer)) != -1) {
//            outStream.write(buffer, 0, len);// 把buffer里的数据写入流中
//        }
//        byte[] data = outStream.toByteArray();
//        outStream.close();
//        inStream.close();
//        return data;
//    }




}
