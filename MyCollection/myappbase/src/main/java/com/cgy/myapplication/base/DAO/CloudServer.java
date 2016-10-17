package com.cgy.myapplication.base.DAO;

import android.os.Handler;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * used to connect to the network
 * Created by RB-cgy on 2016/3/15.
 */
public class CloudServer {

    public static final String CHARSET = "utf-8";// 编码方式

    private static final int TIME_OUT = 10 * 1000;// 超时时间

    private Handler mHandler;

    public CloudServer(Handler handler) {
        this.mHandler = handler;
        if (mHandler == null)
            this.mHandler = new Handler();
    }

    //http connection must be used in thread
    public void httpConnect(final String url, final JSONObject obj) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                postJson(url, obj);
            }
        };
        new Thread(runnable).start();
    }

    synchronized void postJson(String urlPath, JSONObject obj) {
        try {
            URL url = new URL(urlPath);
            HttpURLConnection conn = (HttpURLConnection) url
                    .openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(TIME_OUT);

//            if (null != SESSIONID) {
//                conn.setRequestProperty("Cookie", SESSIONID);
//            }

            conn.connect();
            OutputStream out = conn.getOutputStream();
            out.write(obj.toString().getBytes());
            out.flush();
            out.close();

//            getSession(conn);

            int code = conn.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK) {
                byte[] bit = readInputStream(conn
                        .getInputStream());
                String string = new String(bit, CHARSET);
//                listener.onResponse(string);
            } else {
//                listener.onConnectFailed();
            }
        } catch (Exception e) {
//            listener.onConnectFailed();
        }

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
