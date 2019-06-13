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
import android.net.wifi.WifiInfo;
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
    String mTargetNetSSID;//连接目标网络

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("cgy", "BackgroundService service onStartCommand");

        String phoneModel = Build.MANUFACTURER;
        Log.e("cgy", "手机型号：" + phoneModel);
        Log.e("cgy", "设备安卓版本号：" + Build.VERSION.SDK_INT);

        BluetoothAdapter bleAd = BluetoothAdapter.getDefaultAdapter();
        if (bleAd == null) {
            Log.e("cgy", "设备不支持蓝牙");
            return super.onStartCommand(intent, flags, startId);
        } else if (!bleAd.isEnabled()) {
            Log.e("cgy", "设备 蓝牙未打开");
            bleAd.enable();
        } else {
            openServer();
        }

//        Log.e("cgy", "address：" + bleAd.getAddress());
        openWifi();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("cgy", "BackgroundService service onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWifiScanReceiver != null)
            unregisterReceiver(mWifiScanReceiver);
        mWifiScanReceiver = null;
        Log.e("cgy", "BackgroundService Service onDestroy");
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
                            String pwd = strs[1].replace("PWD:", "");
                            boolean ssidHidden = false;
                            if (lockModuleStr.contains("SSIDHidden")) {
                                //隐藏ssid
                                ssidHidden = true;
                            }
                            Log.e("test", "SSID:" + ssid);
                            Log.e("test", "PWD:" + pwd);
                            if (!TextUtils.isEmpty(ssid) && !TextUtils.isEmpty(pwd)) {
                                mTargetNetSSID = ssid;
                                WifiInfo wifiInfo = mWifiAdmin.getConnectionInfo();
                                if (wifiInfo != null && wifiInfo.getNetworkId() != -1 &&
                                        wifiInfo.getSSID().contains(mTargetNetSSID)) {
                                    //已有连接网络且和目标网络一致
                                    L.e("已连接网络 ssid= " + wifiInfo.getSSID());
                                    L.e("已连接网络 getNetworkId = " + wifiInfo.getNetworkId());
                                    L.e("不重连了，直接通知设备网络连接成功 ");
                                    sendWifiConnectSuccess(wifiInfo.getSSID());
                                    return;
                                }
//                                mWifiAdmin.disableConfigedNetwork(ssid);//先清除可能存在的当前网络
                                connectWifi(ssid, pwd, ssidHidden);//连接指定wifi
                            }
                        }
                    }
                }

                @Override
                public void onConnected() {
                    Log.e("test", "客户端连接成功，可以发送数据");
                }
            });


        String log = "设备名：" + mBluetoothServer.getDeviceName()
                + "\n设备蓝牙地址:" + mBluetoothServer.getDeviceMac();
        Log.e("test", log);
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
        Log.e("test", "\n开始广播");
//        mServerLogV.setText(log);
    }

    public void stopAdvertise() {//结束广播
        if (mBluetoothServer != null) {
            mBluetoothServer.stopAdvertising();
        }
        Log.e("test", "\n结束广播");
//        String log = mServerLogV.getText().toString() + "\n结束广播";
//        mServerLogV.setText(log);
    }

    public void sendCommand(String commandContent) {
        if (mBluetoothServer != null && !TextUtils.isEmpty(commandContent)) {
            mBluetoothServer.sendCommand2Client(commandContent.getBytes());
        }
    }

    public void sendWifiConnectSuccess(String currentNet) {
        if (!TextUtils.isEmpty(mTargetNetSSID) &&
                !TextUtils.isEmpty(currentNet) && currentNet.contains(mTargetNetSSID)) {
            L.e("sendWifiConnectSuccess ");
            sendCommand(mTargetNetSSID + " connect success");
        }
    }

    public void sendWifiConnectFail(String currentNet) {
        if (!TextUtils.isEmpty(mTargetNetSSID) &&
                !TextUtils.isEmpty(currentNet) && currentNet.contains(mTargetNetSSID)) {
            sendCommand(mTargetNetSSID + " connect fail");
        }
    }

    //</editor-fold>

    //<editor-fold desc="wifi相关 ">

    WifiAdmin mWifiAdmin;

    BroadcastReceiver mWifiScanReceiver;

    public void openWifi() {
//        Log.e("test",("有 ACCESS_FINE_LOCATION   权限？" + (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == 0));
//        Log.e("test",("有 ACCESS_COARSE_LOCATION 权限？" + (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == 0));
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
                                Log.e("test", "WIFI已经关闭");
                                break;
                            }
                            case WifiManager.WIFI_STATE_DISABLING: {
                                Log.e("test", "WIFI正在关闭");
                                break;
                            }
                            case WifiManager.WIFI_STATE_ENABLED: {
                                Log.e("test", "WIFI已经打开");
//                        sortScaResult();
                                break;
                            }
                            case WifiManager.WIFI_STATE_ENABLING: {
                                Log.e("test", "WIFI正在打开");
                                break;
                            }
                            case WifiManager.WIFI_STATE_UNKNOWN: {
                                Log.e("test", "未知状态");
                                break;
                            }
                        }
                    } else if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
                        NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                        if (NetworkInfo.State.DISCONNECTED == info.getState()) {//wifi没连接上
                            Log.e("test", "wifi没连接上:" + info.toString());
                        } else if (NetworkInfo.State.CONNECTED == info.getState()) {//wifi连接上了
                            Log.e("test", "a.wifi连接上了:" + info.toString());
                            final WifiInfo wifiInfo = mWifiAdmin.getConnectionInfo();
                            Log.e("test", "a.已连接到网络:" + wifiInfo.getSSID());
                            sendWifiConnectSuccess(wifiInfo.getSSID());

                        } else {
                            if (NetworkInfo.State.CONNECTING == info.getState()) {//正在连接
                                Log.e("test", "b.wifi正在连接:" + info.toString());
                            }
                            NetworkInfo.DetailedState state = info.getDetailedState();
                            if (state == state.CONNECTING) {
                                Log.e("test", "b.连接中...");
                            } else if (state == state.AUTHENTICATING) {
                                Log.e("test", "b.正在验证身份信息...");
                            } else if (state == state.OBTAINING_IPADDR) {
                                Log.e("test", "b.正在获取IP地址...");
                            } else if (state == state.FAILED) {
                                Log.e("test", "b.连接失败");
                                sendWifiConnectFail(mTargetNetSSID);
                            }
                        }
                    } else if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                        Log.e("test", "网络列表变化了");
                        List<ScanResult> results = mWifiAdmin.getWifiManager().getScanResults();

                        if (results == null || results.isEmpty()) {
                            Log.e("test", "未扫描到网络：");
                        } else {
                            Log.e("test", "扫描到网络数量: " + results.size());
                            for (int i = 0; i < results.size(); i++) {
                                Log.e("test", "网络 " + i + " :" + results.get(i).toString());
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

//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
////                mWifiAdmin.startScan();
////                mWifiAdmin.disableAllConfigedNetwork();
//                connectWifi("mxi", "88888888", false);
//            }
//        }, 5000);
//        Observable.interval(5000, 5000, TimeUnit.MILLISECONDS).subscribe(new DisposableObserver<Long>() {
//            @Override
//            public void onNext(Long aLong) {
//                Log.e("test",("onNext:" + aLong);
////                mWifiAdmin.disableAllConfigedNetwork();
////                connectWifi("NO8", "88888888");
//                mWifiAdmin.startScan();
////                if (aLong >= 10)
//                dispose();
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });
    }


    public void connectWifi(String SSID, String key, boolean ssidHidden) {
        if (TextUtils.isEmpty(SSID) || TextUtils.isEmpty(key)) {
            Log.e("test", "请输入ssid/密码！");
            return;
        }
        if (mWifiAdmin == null)
            return;

        Log.e("test", String.format("开始连接wifi ssid = %s ,pwd = %s", SSID, key));
        //TODO:直接连 三星连不上，需要扫描后连能连上
        mWifiAdmin.connectHotpot(SSID, key, ssidHidden);
    }

    //</editor-fold>
}
