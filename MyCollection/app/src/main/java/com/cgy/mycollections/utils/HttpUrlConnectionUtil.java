package com.cgy.mycollections.utils;

import android.view.View;

import androidx.annotation.NonNull;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Description :使用httpurlconnection 的帮助类
 * Author :cgy
 * Date :2019/12/6
 */
public class HttpUrlConnectionUtil {
    /**
     * 下载，需要在线程中使用
     *
     * @param downloadUrl 下载链接
     * @param cacheDir
     */
    public static void downLoad(@NonNull String downloadUrl, @NonNull String cacheDir) {//http download data
        int len;
        int downSize = 0;// 已下载文件大小
        int totalSize;// 文件总 长度
        URL url;
        try {
            url = new URL(downloadUrl);
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
                String filePath = cacheDir + "/app.zip";
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
                fos.flush();//需要flush一下
                fos.close();
                bis.close();
                is.close();
                System.out.println("down load ok~");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载url中的数据到stream中
     *
     * @param urlString
     * @param outputStream
     * @return
     */
    private boolean downloadUrlToStream(String urlString, OutputStream outputStream) {
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(), 8 * 1024);
            out = new BufferedOutputStream(outputStream, 8 * 1024);
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            return true;
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
