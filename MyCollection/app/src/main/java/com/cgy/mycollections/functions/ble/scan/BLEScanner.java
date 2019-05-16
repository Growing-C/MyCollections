package com.cgy.mycollections.functions.ble.scan;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

/**
 * Description : 蓝牙扫描者
 * Author :cgy
 * Date :2018/8/7
 */
public class BLEScanner {
    private final String TAG = this.getClass().getSimpleName();

    List<BLETarget> mTargetList = new ArrayList<>();
    BluetoothAdapter mBluetoothAdapter;
    IBLEScanObserver mObserver;

    Context mContext;
    BluetoothScanBroadcast mScanBroadcast;
    BluetoothLeScanner mScanner;

    Handler mHandler = new Handler();

    private long startTime;//开始时间，仅调试使用

    private final int SCAN_TIMEOUT = 20000;//默认的连接超时时间 18秒

    int mAndroidDivideVersion = 23;//api 23 测试vivo手机api 22不行，必须设置为23
    private boolean isScanning = false;

    private boolean scanBle = true;//是否可以用  ble的扫描方式 默认是可以的

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    ScanCallback mLeScanCallBack = new ScanCallback() {
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            foundDevice(result.getDevice());
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
            Log.e(TAG, "onScanFailed errorCode：" + errorCode);
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);
            Log.e(TAG, "onBatchScanResults size：" + results.size());
        }
    };

    //扫描超时处理
    Runnable mTimeOutRunnable = new Runnable() {
        @Override
        public void run() {
            stopScan(true);
        }
    };

    public BLEScanner(@NonNull Context context, @NonNull IBLEScanObserver observer) {//观察者为空没有意义
        this.mObserver = observer;
        this.mContext = context;
    }

    /**
     * 用于切换扫描方式
     *
     * @param scanBle false-只能用discovery的方式扫描
     *                true -可以用ble扫描，根据手机 api level来决定
     */
    public void setScanBle(boolean scanBle) {
        this.scanBle = scanBle;
    }

    /**
     * 添加扫描目标
     *
     * @param target
     */
    public void addTarget(BLETarget target) {
        if (target != null && !mTargetList.contains(target)) {
            for (BLETarget item : mTargetList) {//如果有同样的设备就不添加了，防止重复添加多个设备
                if (item.isTarget(target.getTargetName(), target.getTargetAddress())) {
                    return;
                }
            }
            mTargetList.add(target);
            Log.e(TAG, "addTarget:" + target.toString());
        }
    }

    /**
     * 清空数据和mHandler message
     */
    public void clean() {
        mTargetList.clear();
        mHandler.removeCallbacksAndMessages(null);
    }

    /**
     * 是否在扫描
     *
     * @return
     */
    public boolean isScanning() {
        return isScanning;
    }

    public void startScan() {
        startScan(SCAN_TIMEOUT);
    }

    /**
     * 开始扫描(可以直接扫描 不需要先stop再start 除了会多调一次onScanStarted 没有其他影响)
     */
    public void startScan(int pairTimeOut) {
        mHandler.removeCallbacksAndMessages(null);

//        if (mTargetList.isEmpty())//没设备扫描没意义
//            return;

        if (mBluetoothAdapter == null)
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null) {//不支持蓝牙
            mObserver.onBLEUnsupported();
        } else if (!mBluetoothAdapter.isEnabled()) {//蓝牙被关闭 需要打开蓝牙
            mObserver.onBLEDisabled();
        } else {//开始扫描
            isScanning = true;
            mObserver.onScanStarted();
            startTime = System.currentTimeMillis();

            if (Build.VERSION.SDK_INT >= mAndroidDivideVersion && scanBle) {//21以后用 BluetoothLeScanner.startScan 这个速度快
                scanAfterApi21();
            } else {//21以下用 startDiscovery 这个扫描速度慢一点
                scanBeforeApi21();
            }
            if (pairTimeOut > 0)
                mHandler.postDelayed(mTimeOutRunnable, pairTimeOut);
        }
    }

    /**
     * 手动停止扫描(每次停止扫描的时候这个 必须会走)
     *
     * @param shouldNotify 是否要通知扫描停止 false-不会回调onScanStopped
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void stopScan(boolean shouldNotify) {
        mHandler.removeCallbacksAndMessages(null);
        isScanning = false;
        if (mBluetoothAdapter == null)
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {//蓝牙被关闭 或者不支持蓝牙就啥都不做
            //不然  当蓝牙被手动关闭  而又调用了stopScan的时候会崩溃
        } else {
            if (Build.VERSION.SDK_INT >= mAndroidDivideVersion && scanBle) {//21以后用 BluetoothLeScanner.startScan 这个速度快
                if (mScanner != null) {
                    Log.e(TAG, "--------------stopLEScan++++++++++++++++++++++");
                    mScanner.stopScan(mLeScanCallBack);
                    mScanner = null;
                }
            } else {//21以下用 startDiscovery 这个扫描速度慢一点
                Log.e(TAG, "--------------cancelDiscovery++++++++++++++++++++++");
                mBluetoothAdapter.cancelDiscovery();
                if (mScanBroadcast != null) {
                    mContext.unregisterReceiver(mScanBroadcast);
                    mScanBroadcast = null;
                }
            }
        }

        if (shouldNotify)
            mObserver.onScanStopped();
    }

    /**
     * 找到蓝牙设备 判断是否是目标设备
     *
     * @param device
     * @return
     */
    private boolean foundDevice(BluetoothDevice device) {
        if (device == null)
            return false;

        Log.i(TAG, "foundDevice：" + device.getName() + "-->addr:" + device.getAddress());
        if (mTargetList.isEmpty()) {//有指定设备
            mObserver.onDeviceFound(device);
            return true;
        } else {
            for (BLETarget target : mTargetList) {
                if (target.isTarget(device.getName(), device.getAddress())) {//找到目标设备之一
                    Log.i(TAG, "foundDevice  目标设备！扫描用时：" + (System.currentTimeMillis() - startTime));
                    mObserver.onDeviceFound(device);
                    return true;
                }
            }
        }
        return false;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void scanAfterApi21() {
        List<ScanFilter> filterList = new ArrayList<>();
        try {
            if (!mTargetList.isEmpty()) {
                for (BLETarget target : mTargetList) {
                    ScanFilter.Builder builder = new ScanFilter.Builder();
                    if (!TextUtils.isEmpty(target.getTargetAddress())) {
                        builder.setDeviceAddress(target.getTargetAddress());
                    }
                    if (!TextUtils.isEmpty(target.getTargetName())) {
                        builder.setDeviceName(target.getTargetName());
                    }
                    ScanFilter filter = builder.build();

                    filterList.add(filter);
                }
            }
        } catch (Exception e) {
            isScanning = false;
            mObserver.onError(e.getMessage());//地址错误，扫描失败
            return;
        }

        ScanSettings settings = new ScanSettings.Builder()
//                .setCallbackType(ScanSettings.CALLBACK_TYPE_FIRST_MATCH)//只回调第一个收到的设备
//                .setMatchMode(ScanSettings.MATCH_MODE_AGGRESSIVE)//积极的搜索 信号弱也可以
                .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                .build();

        Log.e(TAG, "---------------------startScan+++++++++++++++++++++++");
        if (mScanner == null) {//用是否为null判断是否在扫描
            mScanner = mBluetoothAdapter.getBluetoothLeScanner();
//            mScanner.startScan(mLeScanCallBack);//此方法一旦开启会一直搜索，需要手动关闭
            mScanner.startScan(filterList, settings,
                    mLeScanCallBack);//此方法一旦开启会一直搜索，需要手动关闭
        }
    }


    private void scanBeforeApi21() {
        if (!mBluetoothAdapter.isDiscovering()) {
            Log.e(TAG, "--startDiscovery++");
            if (mScanBroadcast == null) {
                //注册广播，接收搜索到的蓝牙设备：
                mScanBroadcast = new BluetoothScanBroadcast();
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
                intentFilter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
                intentFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
                intentFilter.addAction(BluetoothDevice.ACTION_PAIRING_REQUEST);
                intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
//                intentFilter.addAction(BluetoothDevice.ACTION_PAIRING_CANCEL);//hide
                intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
                intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
                intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
                mContext.registerReceiver(mScanBroadcast, intentFilter);
            }
            mBluetoothAdapter.startDiscovery();
        }
    }

    class BluetoothScanBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction(); //得到action

            // 从Intent中获取设备蓝牙device对象
            BluetoothDevice btDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

            if (action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {
                Log.d(TAG, "Bluetooth discovery finished!");
                stopScan(true);
            } else if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                // Get the BluetoothDevice object from the Intent
                // Add the name and address to an array adapter to show in a ListView
                foundDevice(btDevice);
            }
        }
    }
}
