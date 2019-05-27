package com.cgy.mycollections.functions.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cgy.mycollections.BaseActivity;
import com.cgy.mycollections.R;
import com.cgy.mycollections.functions.ble.scan.BLEScanner;
import com.cgy.mycollections.functions.ble.scan.IBLEScanObserver;
import com.cgy.mycollections.functions.ble.server.BluetoothServer;
import com.cgy.mycollections.listeners.OnTItemClickListener;
import com.cgy.mycollections.utils.CHexConverter;
import com.cgy.mycollections.utils.L;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import appframe.permission.PermissionDenied;
import appframe.permission.PermissionDialog;
import appframe.permission.PermissionGranted;
import appframe.permission.PermissionManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class BleDemo extends BaseActivity {
    @BindView(R.id.type_group)
    RadioGroup mTypeGroup;

    @BindView(R.id.ble_device_list)
    RecyclerView mBleDeviceListV;
    @BindView(R.id.client_op)
    View mClientOpsV;
    @BindView(R.id.server_op)
    View mServerOpsV;
    @BindView(R.id.log)
    TextView mLogV;
    @BindView(R.id.server_log)
    TextView mServerLogV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ble_demo);
        ButterKnife.bind(this);

        mTypeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.server) {
                    //设备类型是server
                    mClientOpsV.setVisibility(View.INVISIBLE);
                    mServerOpsV.setVisibility(View.VISIBLE);
                } else {
                    //设备作为client
                    mClientOpsV.setVisibility(View.VISIBLE);
                    mServerOpsV.setVisibility(View.INVISIBLE);
                }
            }
        });

        mBleDeviceListV.setLayoutManager(new LinearLayoutManager(this));
        mDeviceAdapter = new BleDeviceAdapter();
        mDeviceAdapter.setOnItemClickListener(new OnTItemClickListener<BluetoothDevice>() {
            @Override
            public void onItemClickOne(int position, BluetoothDevice data) {
                connect(data);
            }

            @Override
            public void onItemClickTwo(int position, BluetoothDevice data) {
//                closeDevice();
            }
        });
        mBleDeviceListV.setAdapter(mDeviceAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mScanner != null) {
            mScanner.stopScan(false);
            mScanner = null;
        }
    }

    @OnClick({R.id.start_scan, R.id.stop_scan, R.id.open_server, R.id.start_broad, R.id.stop_broad, R.id.close_device})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_scan:
                //开启蓝牙服务
                PermissionManager.requestBluetoothPermission(this, "打开蓝牙权限啦！");
                break;
            case R.id.stop_scan:
                if (mScanner != null)
                    mScanner.stopScan(false);
                break;
            case R.id.close_device:
                closeDevice();
                break;

            case R.id.open_server://开启蓝牙服务端
                openServer();
                break;
            case R.id.start_broad://开始广播
                startAdvertise();
                break;
            case R.id.stop_broad://结束广播
                stopAdvertise();
                break;
            default:
                break;
        }
    }

    //<editor-fold desc="蓝牙服务端 ">

    BluetoothServer mBluetoothServer;

    public void openServer() {//开启蓝牙服务端
        if (mBluetoothServer == null)
            mBluetoothServer = new BluetoothServer(this);

        String log = "设备名：" + mBluetoothServer.getDeviceName()
                + "\n设备蓝牙地址:" + mBluetoothServer.getDeviceMac();
        L.e(log);
        mServerLogV.setText(log);
        showToast("蓝牙服务端已开启");
    }

    public void startAdvertise() {//开始广播
        if (mBluetoothServer != null) {
            mBluetoothServer.startAdvertising();
        }
        String log = mServerLogV.getText().toString() + "\n开始广播";
        mServerLogV.setText(log);
    }

    public void stopAdvertise() {//结束广播
        if (mBluetoothServer != null) {
            mBluetoothServer.stopAdvertising();
        }

        String log = mServerLogV.getText().toString() + "\n结束广播";
        mServerLogV.setText(log);
    }

    //</editor-fold>


    //<editor-fold desc="蓝牙客户端 ">

    BleDeviceAdapter mDeviceAdapter;

    BLEScanner mScanner;

    List<BluetoothDevice> mBleDeviceList = new ArrayList<>();

    @PermissionGranted(requestCode = PermissionManager.REQUEST_BLUETOOTH)
    public void bluetoothPermissionGranted() {
        L.e("test", "bluetoothPermissionGranted");
        startScan();
    }

    @PermissionDenied(requestCode = PermissionManager.REQUEST_BLUETOOTH)
    public void bluetoothPermissionDenied() {
        L.e("bluetoothPermissionDenied");

        PermissionDialog mPermissionDialog = new PermissionDialog(this, "打开蓝牙权限嘛");
        mPermissionDialog.show();
    }

    private void startScan() {
        if (mScanner == null) {
            mScanner = new BLEScanner(this, new IBLEScanObserver() {
                @Override
                public void onScanStarted() {
                    L.e("扫描开始");
                }

                @Override
                public void onScanStopped() {

                }

                @Override
                public void onBLEUnsupported() {

                }

                @Override
                public void onBLEDisabled() {
                    Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    enableIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(enableIntent);
                }

                @Override
                public void onScanTimeout() {

                }

                @Override
                public void onError(String error) {

                }

                @Override
                public void onDeviceFound(BluetoothDevice device) {
                    if (!mBleDeviceList.contains(device)) {
                        mBleDeviceList.add(device);
                        mDeviceAdapter.setData(mBleDeviceList);
                        L.e("device list size:" + mBleDeviceList.size());
                    }

                }
            });
            mScanner.setScanBle(false);
        }
        mScanner.stopScan(false);
        mScanner.startScan(-1);
    }

    public void connect(BluetoothDevice device) {
        if (device != null) {
            mBluetoothGatt = device.connectGatt(this, false, mGattCallback);
        }
    }

    public void closeDevice() {
        if (mWriteCharacteristic != null) {
            mWriteCharacteristic = null;
        }

        if (mBluetoothGatt != null) {
            mBluetoothGatt.disconnect();
            refreshDeviceCache();
            mBluetoothGatt.close();//据说直接在disconnect后面close会导致STATE_DISCONNECTED收不到，确实是这样 待实验
            mBluetoothGatt = null;
        }
    }

    /**
     * Clears the device cache. After uploading new hello4 the DFU target will have other services than before.
     */
    public boolean refreshDeviceCache() {
        L.e("refreshDeviceCache");
        /*
         * There is a refresh() method in BluetoothGatt class but for now it's hidden. We will call it using reflections.
         */
        try {
            final Method refresh = BluetoothGatt.class.getMethod("refresh");
            if (refresh != null) {
                final boolean success = (Boolean) refresh.invoke(mBluetoothGatt);
                L.e("Refreshing result: " + success);
                return success;
            }
        } catch (Exception e) {
            L.e("An exception occured while refreshing device");
        }
        return false;
    }

    BluetoothGatt mBluetoothGatt;
    BluetoothGattCharacteristic mWriteCharacteristic;//写入
    BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            switch (newState) {
                case BluetoothProfile.STATE_CONNECTED:
                    L.e("onConnectionStateChange -->  connect success :");
                    mBluetoothGatt.discoverServices();
                    break;
                case BluetoothProfile.STATE_DISCONNECTED:
                    L.e("--STATE_DISCONNECTED status:" + status);
                    break;
            }

        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            L.e("onServicesDiscovered:" + status);
            if (status == BluetoothGatt.GATT_SUCCESS) {
                //枚举所有服务：
                L.d("-------------------");
                L.d(" onServicesDiscovered Service size:" + mBluetoothGatt.getServices().size());
                A:
                for (BluetoothGattService s : mBluetoothGatt.getServices()) {
                    L.d("S size:" + s.getCharacteristics().size() + " uuid:" + s.getUuid());
                    for (BluetoothGattCharacteristic c : s.getCharacteristics()) {
                        L.d("--character:" + c.getUuid());
                        if (c.getUuid().toString().toUpperCase().contains("b001".toUpperCase())) {
                            L.d("b001 raw uuid:" + c.getUuid().toString());
                            L.d("b001 tar uuid:" + BluetoothServer.UUID_LOCK_READ.toString());
                            L.d("equals? :" + (c.getUuid().toString().equals(BluetoothServer.UUID_LOCK_READ.toString())));
                            try {
                                if (mWriteCharacteristic == null) {
                                    mWriteCharacteristic = s.getCharacteristic(BluetoothServer.UUID_LOCK_WRITE);//这个uuid选里面的
                                }
                                setCharacteristicNotification(s.getUuid(), c.getUuid(), BluetoothServer.UUID_DESCRIPTOR, true);
                            } catch (Exception e) {
                                L.e(e);
                            }
                            break A;
                        }
                    }

                }
                L.d("-------------------");
            } else {

            }

        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
            L.e("onCharacteristicChanged UUID:" + characteristic.getUuid().toString());
            final byte[] dealBytes = characteristic.getValue();

            String result = CHexConverter.byte2HexStr(dealBytes, dealBytes.length);
            L.e("收到蓝牙服务端数据 onCharacteristicChanged value：" + result);

//            byte[] command = new byte[]{11, 22, 33, 44, 55, 66, 77, 88, 99, 10};//永远发第一个
//
//            L.e("给设备发送了消息，内容：" + CHexConverter.byte2HexStr(command, command.length));
//            mWriteCharacteristic.setValue(command);
//            mWriteCharacteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
//            boolean writeSuccess = mBluetoothGatt.writeCharacteristic(mWriteCharacteristic);
//            L.e("mSendCommandRunnable writeSuccess?= " + writeSuccess);  //如果isBoolean返回的是true则写入成功

        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
            L.e("onDescriptorWrite");


            byte[] command = new byte[]{11, 22, 33, 44, 55, 66};//永远发第一个
            L.e("onDescriptorWrite 给设备发送了消息，内容：" + CHexConverter.byte2HexStr(command));
            mWriteCharacteristic.setValue(command);
            mWriteCharacteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
            boolean writeSuccess = mBluetoothGatt.writeCharacteristic(mWriteCharacteristic);
            L.e("onDescriptorWrite writeSuccess?= " + writeSuccess);  //如果isBoolean返回的是true则写入成功
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);

            L.e("onCharacteristicWrite UUID:" + characteristic.getUuid().toString());
            final byte[] dealBytes = characteristic.getValue();

            String result = CHexConverter.byte2HexStr(dealBytes, dealBytes.length);
            L.e(String.format(" onCharacteristicWrite： result = %s", "收到蓝牙服务端数据 onCharacteristicChanged value：" + result));

        }
    };

    public boolean setCharacteristicNotification(UUID serviceUuid, UUID characteristicUuid,
                                                 UUID descriptorUuid, boolean enable) {
        L.e("setCharacteristicNotification  start ");
        BluetoothGattCharacteristic characteristic = mBluetoothGatt.getService(serviceUuid)
                .getCharacteristic(characteristicUuid);
        mBluetoothGatt.setCharacteristicNotification(characteristic, enable);
        if (descriptorUuid != null) {//指定descriptor
            BluetoothGattDescriptor descriptor = characteristic.getDescriptor(descriptorUuid);
            descriptor.setValue(enable ? BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE : BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
            mBluetoothGatt.writeDescriptor(descriptor); //descriptor write operation successfully started?

            L.e("setCharacteristicNotification  writeDescriptor ");
        } else {//未指定descriptor
            for (BluetoothGattDescriptor dp : characteristic.getDescriptors()) {
                L.e("setCharacteristicNotification BluetoothGattDescriptor:" + dp.getUuid());
                if ((characteristic.getProperties() & BluetoothGattCharacteristic.PROPERTY_NOTIFY) != 0) {
                    L.e("1...ENABLE_NOTIFICATION_VALUE");
                    dp.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                } else if ((characteristic.getProperties() & BluetoothGattCharacteristic.PROPERTY_INDICATE) != 0) {
                    L.e("2...ENABLE_INDICATION_VALUE");
                    dp.setValue(BluetoothGattDescriptor.ENABLE_INDICATION_VALUE);//测试中发现大锁使用ENABLE_INDICATION_VALUE的话就没下文了，所以基本还是用ENABLE_NOTIFICATION_VALUE
                }
                mBluetoothGatt.writeDescriptor(dp);
            }
        }
        L.e("setCharacteristicNotification  end ");
        return true;
    }

    //</editor-fold>
}
