package com.cgy.mycollections.functions.net.wifip2p;

import android.app.IntentService;
import android.content.Intent;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import androidx.annotation.Nullable;

import com.cgy.mycollections.utils.L;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Description :
 * Author :cgy
 * Date :2019/5/15
 */
public class WifiServerService extends IntentService {

    private static final String TAG = "WifiServerService";


    private ServerSocket serverSocket;

    private InputStream inputStream;

    private ObjectInputStream objectInputStream;

    private OutputStream mOutputStream;

    private OnProgressChangListener progressChangListener;

    public class MyBinder extends Binder {
        public WifiServerService getService() {
            return WifiServerService.this;
        }
    }

    public WifiServerService() {
        super("WifiServerService");
        L.e(TAG, "new WifiServerService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        L.e(TAG, "onCreate");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        L.e(TAG, "onBind");
        return new MyBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        L.e(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        L.e(TAG, "onHandleIntent");
        clean();
        TransferData data = null;
        try {
            serverSocket = new ServerSocket();
            serverSocket.setReuseAddress(true);
            serverSocket.bind(new InetSocketAddress(WifiSendTask.PORT));

//            serverSocket = new ServerSocket(WifiSendTask.PORT);
//            serverSocket.setReuseAddress(true);
//            serverSocket.bind(new InetSocketAddress(WifiSendTask.PORT));
            Socket client = serverSocket.accept();
            L.e(TAG, "客户端IP地址 : " + client.getRemoteSocketAddress());
//            L.e(TAG, "客户端IP地址 : " + client.getInetAddress().getHostAddress());
            inputStream = client.getInputStream();
            objectInputStream = new ObjectInputStream(inputStream);
            /**如果你写的是2个程序，尽管FileTransfer一样，但是也要注意包名，其实只要Socket 连接通，客户端IP 拿到，就达到了目标。**/
            data = (TransferData) objectInputStream.readObject();
            L.e(TAG, "待接收的数据: " + data);
            if (data.dataType == 1) {//1是string
                mOutputStream = new ByteArrayOutputStream();
            } else if (data.dataType == 2) {
                String name = new File(data.transferFilePath).getName();
                //将文件存储至指定位置
                File file = new File(Environment.getExternalStorageDirectory() + "/" + name);
                mOutputStream = new FileOutputStream(file);
            } else {
                L.e(TAG, "暂不支持的数据类型，取消发送");
                return;
            }

            byte[] buf = new byte[512];
            int len;
            long total = 0;
            int progress;
            long dataLen = data.getSendDataLen();

            L.e(TAG, "开始接收数据 总长度: " + dataLen);
            while ((len = inputStream.read(buf)) != -1) {
                mOutputStream.write(buf, 0, len);
                total += len;
                progress = (int) ((total * 100) / dataLen);
                L.e(TAG, "数据接收进度: " + progress);
                if (progressChangListener != null) {
                    progressChangListener.onProgressChanged(data, progress);
                }
            }
            L.e(TAG, "数据接收成功:" + mOutputStream.toString());
        } catch (Exception e) {
            L.e(TAG, "数据接收 Exception: " + e.getMessage());
        } finally {
            clean();
            if (progressChangListener != null) {
                progressChangListener.onTransferFinished(data);
            }
            //再次启动服务，等待客户端下次连接
            startService(new Intent(this, WifiServerService.class));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        clean();
    }

    public void setProgressChangListener(OnProgressChangListener progressChangListener) {
        this.progressChangListener = progressChangListener;
    }

    private void clean() {
        if (serverSocket != null) {
            try {
                serverSocket.close();
                serverSocket = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (inputStream != null) {
            try {
                inputStream.close();
                inputStream = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (objectInputStream != null) {
            try {
                objectInputStream.close();
                objectInputStream = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (mOutputStream != null) {
            try {
                mOutputStream.close();
                mOutputStream = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
