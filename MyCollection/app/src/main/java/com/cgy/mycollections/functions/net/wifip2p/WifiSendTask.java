package com.cgy.mycollections.functions.net.wifip2p;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import appframe.utils.L;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Description :wifi p2p 发送数据
 * Author :cgy
 * Date :2019/5/15
 */
public class WifiSendTask extends AsyncTask<String, Integer, Boolean> {

    private ProgressDialog progressDialog;

    private TransferData mSendData;

    public static final int PORT = 10010;

    private static final String TAG = "linkstec";

    public WifiSendTask(Context context, TransferData fileTransfer) {
        this.mSendData = fileTransfer;
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setTitle("正在发送文件");
        progressDialog.setMax(100);
    }

    @Override
    protected void onPreExecute() {
        progressDialog.show();
    }

    @Override
    protected Boolean doInBackground(String... strings) {
//        fileTransfer.setMd5(Md5Util.getMd5(new File(fileTransfer.getFilePath())));

        String hostName = strings[0];
        L.e(TAG, "准备发送给 group owner， address:" + hostName);

        Socket socket = null;
        OutputStream outputStream = null;
        ObjectOutputStream objectOutputStream = null;
        InputStream inputStream = null;
        try {
//            socket = new Socket(hostName, PORT);
            socket = new Socket();
            InetSocketAddress inetSocketAddress = new InetSocketAddress(hostName, PORT);
            socket.connect(inetSocketAddress);
//            socket.bind(null);
//            socket.connect((new InetSocketAddress(hostName, PORT)), 10000);
            L.e("发送数据 连接成功 端口：" + PORT);
            outputStream = socket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(mSendData);
            if (mSendData.dataType == 1) {//1是string
                inputStream = new ByteArrayInputStream(mSendData.transferContent.getBytes());
            } else if (mSendData.dataType == 2) {
                inputStream = new FileInputStream(new File(mSendData.transferFilePath));
            } else {
                L.e(TAG, "暂不支持的数据类型，取消发送");
                return true;
            }
            long dataLen = mSendData.getSendDataLen();
            if (dataLen == 0) {
                L.e(TAG, "数据长度为0，取消发送");
                return true;
            }
            L.e(TAG, "发送数据长度:" + dataLen + "，开始发送！！！");
            long total = 0;
            byte[] buf = new byte[512];
            int len;
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
                total += len;
                int progress = (int) ((total * 100) / dataLen);
                publishProgress(progress);
                L.e(TAG, "发送进度：" + progress);
            }
            outputStream.close();
            objectOutputStream.close();
            inputStream.close();
            socket.close();
            outputStream = null;
            objectOutputStream = null;
            inputStream = null;
            socket = null;
            L.e(TAG, "发送成功！！！");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            L.e(TAG, "文件发送异常 Exception: " + e.getMessage());
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        progressDialog.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        progressDialog.cancel();
        L.e(TAG, "onPostExecute: " + aBoolean);
    }

}