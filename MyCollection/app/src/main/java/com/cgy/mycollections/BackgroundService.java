package com.cgy.mycollections;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;

import com.cgy.mycollections.functions.ble.server.BluetoothServer;
import com.cgy.mycollections.utils.L;
import com.cgy.mycollections.widgets.EasyTouchView;


/**
 * Created by user on 2016/10/2.
 */
public class BackgroundService extends Service {

    Handler mHandler = new Handler();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("cgy", "BackgroundService service onStartCommand");

        BluetoothAdapter bleAd = BluetoothAdapter.getDefaultAdapter();
        if (bleAd == null) {
            L.e("cgy", "设备不支持蓝牙");
            return super.onStartCommand(intent, flags, startId);
        } else if (!bleAd.isEnabled()) {
            L.e("cgy", "设备 蓝牙未打开");
            bleAd.enable();
        }
        Log.d("cgy", "address：" + bleAd.getAddress());

        Log.d("cgy", "name：" + bleAd.getName());
        String phoneModel = Build.MANUFACTURER;
        Log.d("cgy", "手机型号：" + phoneModel);
        Log.d("cgy", "SDK_INT：" + Build.VERSION.SDK_INT);

        openServer();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("cgy", "BackgroundService service onCreate");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("cgy", "BackgroundService Service onDestroy");
    }

    //<editor-fold desc="蓝牙服务端 ">

    BluetoothServer mBluetoothServer;

    public void openServer() {//开启蓝牙服务端
        if (mBluetoothServer == null)
            mBluetoothServer = new BluetoothServer(this);

        String log = "设备名：" + mBluetoothServer.getDeviceName()
                + "\n设备蓝牙地址:" + mBluetoothServer.getDeviceMac();
        L.e(log);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startAdvertise();
            }
        }, 2000);
//        mServerLogV.setText(log);
//        showToast("蓝牙服务端已开启");
    }

    public void startAdvertise() {//开始广播
        if (mBluetoothServer != null) {
            mBluetoothServer.startAdvertising();
        }
//        String log = mServerLogV.getText().toString() + "\n开始广播";
        L.e("\n开始广播");
//        mServerLogV.setText(log);
    }

    public void stopAdvertise() {//结束广播
        if (mBluetoothServer != null) {
            mBluetoothServer.stopAdvertising();
        }
        L.e("\n结束广播");
//        String log = mServerLogV.getText().toString() + "\n结束广播";
//        mServerLogV.setText(log);
    }

    //</editor-fold>
}
