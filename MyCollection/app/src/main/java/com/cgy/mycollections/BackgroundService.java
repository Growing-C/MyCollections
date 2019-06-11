package com.cgy.mycollections;

import android.Manifest;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Process;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;

import com.cgy.mycollections.functions.ble.client.DataCallback;
import com.cgy.mycollections.functions.ble.server.BluetoothServer;
import com.cgy.mycollections.functions.net.wifiap.WifiAdmin;
import com.cgy.mycollections.utils.BinaryUtil;
import com.cgy.mycollections.utils.L;
import com.cgy.mycollections.widgets.EasyTouchView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;


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
        Log.e("cgy", "address：" + bleAd.getAddress());

        Log.e("cgy", "name：" + bleAd.getName());
        String phoneModel = Build.MANUFACTURER;
        Log.e("cgy", "手机型号：" + phoneModel);
        Log.e("cgy", "SDK_INT：" + Build.VERSION.SDK_INT);

        openServer();

        openWifi();
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
        if (mWifiScanReceiver != null)
            unregisterReceiver(mWifiScanReceiver);
        mWifiScanReceiver = null;
        Log.d("cgy", "BackgroundService Service onDestroy");
    }

    //<editor-fold desc="蓝牙服务端 ">

    BluetoothServer mBluetoothServer;

    public void openServer() {//开启蓝牙服务端
        if (mBluetoothServer == null)
            mBluetoothServer = new BluetoothServer(this, new DataCallback() {
                @Override
                public void onGetBleResponse(final String data, final byte[] rawData) {
                    String lockModuleStr = BinaryUtil.bytesToASCIIStr(true, rawData); //转成字符串
                    L.e("收到客户端发送数据：" + data);
                    if (!TextUtils.isEmpty(lockModuleStr) && lockModuleStr.toUpperCase().contains("SSID")) {
                        String[] strs = lockModuleStr.split(",");
                        if (strs.length > 1) {
//                            SSID:mxi,PWD:88888888
                            String ssid = strs[0].replace("SSID:", "");
                            String pwd = strs[0].replace("PWD:", "");
                            L.e("SSID:" + ssid);
                            L.e("PWD:" + pwd);
                            if (!TextUtils.isEmpty(ssid) && !TextUtils.isEmpty(pwd)) {
                                connectWifi(ssid, pwd);
                            }
                        }
                    }
                }

                @Override
                public void onConnected() {
                    L.e("客户端连接成功，可以发送数据");
                }
            });


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

    //<editor-fold desc="wifi相关 ">

    WifiAdmin mWifiAdmin;

    BroadcastReceiver mWifiScanReceiver;

    public void openWifi() {
        L.e("有 ACCESS_FINE_LOCATION   权限？" + (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == 0));
        L.e("有 ACCESS_COARSE_LOCATION 权限？" + (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == 0));
        if (mWifiScanReceiver == null) {
            mWifiScanReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {
                        int state = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
                        switch (state) {
                            /**
                             * WIFI_STATE_DISABLED    WLAN已经关闭
                             * WIFI_STATE_DISABLING   WLAN正在关闭
                             * WIFI_STATE_ENABLED     WLAN已经打开
                             * WIFI_STATE_ENABLING    WLAN正在打开
                             * WIFI_STATE_UNKNOWN     未知
                             */
                            case WifiManager.WIFI_STATE_DISABLED: {
                                L.e("WIFI已经关闭");
                                break;
                            }
                            case WifiManager.WIFI_STATE_DISABLING: {
                                L.e("WIFI正在关闭");
                                break;
                            }
                            case WifiManager.WIFI_STATE_ENABLED: {
                                L.e("WIFI已经打开");
//                        sortScaResult();
                                break;
                            }
                            case WifiManager.WIFI_STATE_ENABLING: {
                                L.e("WIFI正在打开");
                                break;
                            }
                            case WifiManager.WIFI_STATE_UNKNOWN: {
                                L.e("未知状态");
                                break;
                            }
                        }
                    } else if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
                        NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                        if (NetworkInfo.State.DISCONNECTED == info.getState()) {//wifi没连接上
                            L.e("wifi没连接上:" + info.toString());
                        } else if (NetworkInfo.State.CONNECTED == info.getState()) {//wifi连接上了
                            L.e("wifi连接上了:" + info.toString());
                        } else if (NetworkInfo.State.CONNECTING == info.getState()) {//正在连接
                            L.e("wifi正在连接:" + info.toString());
                        }
                    } else if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                        L.e("网络列表变化了");
                        List<ScanResult> results = mWifiAdmin.getWifiManager().getScanResults();

                        if (results == null || results.isEmpty()) {
                            L.e("未扫描到网络：");
                        } else {
                            L.e("扫描到网络数量: " + results.size());
                            for (int i = 0; i < results.size(); i++) {
                                L.e("网络 " + i + " :" + results.get(i).toString());
//                                if (results.get(i).SSID.contains("mxi")) {
//                                    mWifiAdmin.connectNet(results.get(i), "88888888");
//                                }
                            }
                        }
                    }
                }
            };

            IntentFilter filter = new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
            filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
            filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
            registerReceiver(mWifiScanReceiver, filter);
        }
        if (mWifiAdmin == null)
            mWifiAdmin = new WifiAdmin(this);

        Observable.interval(5000, 5000, TimeUnit.MILLISECONDS).subscribe(new DisposableObserver<Long>() {
            @Override
            public void onNext(Long aLong) {
                L.e("onNext:" + aLong);
//                mWifiAdmin.disableAllConfigedNetwork();
//                connectWifi("NO8", "88888888");
                mWifiAdmin.startScan();
//                if (aLong >= 10)
                dispose();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }


    public void connectWifi(String SSID, String key) {
        if (TextUtils.isEmpty(SSID) || TextUtils.isEmpty(key)) {
            L.e("请输入ssid/密码！");
            return;
        }
        if (mWifiAdmin == null)
            return;

        L.e(String.format("开始连接wifi ssid = %s ,pwd = %s", SSID, key));
        //TODO:直接连 三星连不上，需要扫描后连能连上
        mWifiAdmin.connectHotpot(SSID, key);
    }

    //</editor-fold>
}
