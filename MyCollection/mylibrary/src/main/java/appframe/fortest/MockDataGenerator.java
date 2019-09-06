package appframe.fortest;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import appframe.utils.IOUtils;
import appframe.utils.LogUtils;

/**
 * 从assets中读取mock的json数据
 * Created by RB-cgy on 2016/9/29.
 */
public class MockDataGenerator {

    public static void testInstall(Context context, String fileName) {
        File file = new File(Environment.getExternalStorageDirectory()
                + "/witon/", fileName + ".apk");
        System.out.println("exists:" + file.exists() + "-->filePath:" + file.toString());

        //7.0 跳转到安装界面属于别的应用程序 没有权限执行解析操作 ，会出现解析程序包出错
        //解决方案：
        //通过在代码中写入linux指令修改此apk文件的权限，改为全局可读可写可执行：
        String[] command = {"chmod", "777", file.getPath()};
        ProcessBuilder builder = new ProcessBuilder(command);
        try {
            builder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Uri updateFileUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateFileUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file);

            context.grantUriPermission(context.getPackageName(), updateFileUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            updateFileUri = Uri.fromFile(file);
        }
        System.out.println("updateFileUri：" + updateFileUri.toString());

        // 执行动作
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//Granting Temporary Permissions to a URI
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//新的task,没有这个无法安装成功
        // 执行的数据类型
        intent.setDataAndType(updateFileUri,
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    public static String getMockDataFromJsonFile(Context context, String path) {
        String json = "";
        LogUtils.d("getMockDataFromJsonFile");
        try {
            InputStream inputStream = context.getAssets().open(path);
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
