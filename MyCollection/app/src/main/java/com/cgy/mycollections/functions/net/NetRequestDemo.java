package com.cgy.mycollections.functions.net;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cgy.mycollections.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetRequestDemo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_request_demo);
    }

    public void get(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                try {
                    url = new URL("https://rbtest.witontek.com/wpay/alipay/app_test.json");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();// 此处的urlConnection对象实际上是根据URL的
                    if (200 == urlConnection.getResponseCode()) {
                        //得到输入流
                        InputStream is = urlConnection.getInputStream();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        int len = 0;
                        while (-1 != (len = is.read(buffer))) {
                            baos.write(buffer, 0, len);
                            baos.flush();
                        }
                        String s = baos.toString("utf-8");
                        baos.close();
                        is.close();
                        JSONObject jsonObject = new JSONObject(s);

                        System.out.println("--------------------------s:" + jsonObject.getString("orderStr"));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static final String CHARSET = "utf-8";// 编码方式

    private static final int TIME_OUT = 10 * 1000;// 超时时间
    private static String lock = new String();

    public void post(View view) {
        new Thread() {
            public void run() {
                synchronized (lock) {


                    try {
                        JSONObject requestData = new JSONObject();
                        requestData.put("mobile", "15051286108");
                        requestData.put("password", "123456");
                        JSONObject inputJson = new JSONObject();
                        inputJson.put("requestData", requestData);
                        System.out.println("inputJson:" + inputJson.toString());

                        URL url = new URL("http://139.224.15.30:8080/hrss/rest/user/login");
                        HttpURLConnection conn = (HttpURLConnection) url
                                .openConnection();
                        conn.setDoOutput(true);
                        conn.setDoInput(true);
                        conn.setRequestMethod("POST");
                        conn.setConnectTimeout(TIME_OUT);

                        conn.connect();
                        OutputStream out = conn.getOutputStream();
                        out.write(inputJson.toString().getBytes());
                        out.flush();
                        out.close();

                        int code = conn.getResponseCode();
                        if (code == HttpURLConnection.HTTP_OK) {
                            byte[] bit = readInputStream(conn
                                    .getInputStream());
                            String string = new String(bit, CHARSET);
                            System.out.println("outputString:" + string);
                        } else {
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }.start();
    }

    public void downLoad(View view) {
        new Thread() {
            @Override
            public void run() {
                int len;
                int downSize = 0;// 已下载文件大小
                int totalSize;// 文件总 长度
                URL url;
                try {
                    url = new URL("http://d2.witon.us/minipay/dev/test/download/web-20171213-231653.zip");

                    HttpURLConnection conn = (HttpURLConnection) url
                            .openConnection();
                    conn.setConnectTimeout(5000);
                    conn.connect();
                    // 获取到文件的大小
                    totalSize = conn.getContentLength();
                    System.out.println("totalSize:" + totalSize);
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        System.out.println("start down load~~~~~~~~200");
                        InputStream is = conn.getInputStream();
                        String filePath = getExternalCacheDir() + "/app.zip";
                        System.out.println("filePath:" + filePath);
                        FileOutputStream fos = new FileOutputStream(filePath);
                        BufferedInputStream bis = new BufferedInputStream(is);
                        byte[] buffer = new byte[1024];

                        while ((len = bis.read(buffer)) != -1) {
                            fos.write(buffer, 0, len);
                            downSize += len;
                            // 获取当前下载量
                            System.out.println("progress:" + (downSize * 100 / totalSize));
                        }
                        fos.close();
                        bis.close();
                        is.close();
                        System.out.println("down load ok~");

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 读取输入流数据，返回一个byte数组
     *
     * @param inStream
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        byte[] buffer = new byte[1024];
        int len = -1;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);// 把buffer里的数据写入流中

        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inStream.close();

        return data;
    }
}
