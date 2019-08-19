package com.cgy.mycollections.functions.netconfig;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.cgy.mycollections.functions.ble.BleDemo;
import com.cgy.mycollections.functions.ble.BleDeviceAdapter;
import com.cgy.mycollections.functions.ble.client.BLEClient;
import com.cgy.mycollections.functions.ble.client.DataCallback;
import com.cgy.mycollections.functions.ble.scan.BLEScanner;
import com.cgy.mycollections.functions.ble.scan.IBLEScanObserver;
import com.cgy.mycollections.utils.BinaryUtil;
import com.cgy.mycollections.utils.L;

/**
 * Description :配网操作者 配网流程
 * 1.蓝牙连接待配网设备
 * 2.发送ssid pwd
 * 3.设备端解析ssid pwd 然后wifi连接
 * 4.设备端发送配网结果 在此处接收
 * Author :cgy
 * Date :2019/6/14
 */
public class NetConfigOperator {

    private final String DEVICE_BLE_NAME = "ChargeSPOT";
    Context mContext;

    BLEScanner mScanner;
    BLEClient mBLEClient;

    String mSSID;
    String mPwd;
    boolean mSsidHidden = false;

    BluetoothDevice mBleDevice;
    IConfigCallback mConfigCallback;

    IBLEScanObserver mScanObserver = new IBLEScanObserver() {
        @Override
        public void onScanStarted() {
            L.e("扫描开始");
        }

        @Override
        public void onScanStopped() {
            L.e("扫描结束！");
            if (mBleDevice == null) {
                L.e("未找到设备");
                if (mConfigCallback != null) {
                    mConfigCallback.onError(1, "未找到目标设备");
                }
            }
        }

        @Override
        public void onBLEUnsupported() {

        }

        @Override
        public void onBLEDisabled() {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            enableIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(enableIntent);
        }

        @Override
        public void onScanTimeout() {

        }

        @Override
        public void onError(String error) {

        }

        @Override
        public void onDeviceFound(BluetoothDevice device) {
            if (!TextUtils.isEmpty(device.getName()) && device.getName().contains(DEVICE_BLE_NAME)) {
                L.e("找到目标设备");
                mBleDevice = device;
                connect(device);
            }
        }
    };

    public NetConfigOperator(Context context) {
        this.mContext = context;
    }

    //----------------------外部调用---------------------------

    public void startConfig(String ssid, String pwd, boolean ssidHidden, IConfigCallback configCallback) {
        close();
        this.mSSID = ssid;
        this.mPwd = pwd;
        this.mSsidHidden = ssidHidden;
        this.mConfigCallback = configCallback;

        if (TextUtils.isEmpty(ssid) || TextUtils.isEmpty(pwd))
            throw new NullPointerException("ssid/pwd is null ! can't start config~");

        startScan();
    }

    public void close() {
        mConfigCallback = null;
        mBleDevice = null;

        if (mScanner != null)
            mScanner.stopScan(false);
        if (mBLEClient != null) {
            mBLEClient.closeDevice();
            mBLEClient = null;
        }
    }

    //-------------------------------------------------

    private void startScan() {
        if (mScanner == null) {
            mScanner = new BLEScanner(mContext, mScanObserver);
            mScanner.setScanBle(false);
        }
        mScanner.stopScan(false);
        mScanner.startScan(-1);
    }


    private void connect(BluetoothDevice device) {
        if (device != null) {
            mBLEClient = new BLEClient(mContext);
            mBLEClient.connect(device, mCallback);
        }
    }


    private DataCallback mCallback = new DataCallback() {
        @Override
        public void onGetBleResponse(final String data, final byte[] rawData) {
            String lockModuleStr = BinaryUtil.bytesToASCIIStr(true, rawData); //转成字符串
            L.e("收到设备回调数据 raw：" + data);
            L.e("收到设备回调数据 解析：" + lockModuleStr);
            if (!TextUtils.isEmpty(lockModuleStr)) {
                if (lockModuleStr.contains("connect success")) {
                    L.e("网关设备 网络连接成功！");
                    if (mConfigCallback != null)
                        mConfigCallback.onSuccess();
                } else if (lockModuleStr.contains("fail")) {
                    L.e("网关设备 网络连接失败！");
                    if (mConfigCallback != null)
                        mConfigCallback.onError(2, "设备组网失败！");
                }
            }
        }

        @Override
        public void onConnected() {
            L.e("连接成功，可以发送数据");
            if (mConfigCallback != null) {
                mConfigCallback.onProcessChange(1, "连接成功，可以发送数据");
            }
//            mBLEClient.setMTU(50);
//        String sendData = "SSID:mxi,PWD:88888888";
//            String sendData = "SSID:NO8,PWD:linkage@12345,SSIDHidden";
            String sendData = "SSID:" + mSSID + ",PWD:" + mPwd + (mSsidHidden ? ",SSIDHidden" : "");
            if (mBLEClient != null)
                mBLEClient.writeData(sendData);
        }
    };

//    private void sendData() {
//        String data = mBleData.getText().toString();
//        if (TextUtils.isEmpty(data)) {
//            showToast("请先输入蓝牙传输数据");
//            return;
//        }
//        if (mBLEClient == null) {
//            showToast("还未连接设备");
//            return;
//        }
//        mBLEClient.writeData(data);
//    }

}
