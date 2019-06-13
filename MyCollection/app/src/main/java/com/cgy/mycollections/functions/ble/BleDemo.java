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
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cgy.mycollections.BaseActivity;
import com.cgy.mycollections.R;
import com.cgy.mycollections.functions.ble.client.BLEClient;
import com.cgy.mycollections.functions.ble.client.DataCallback;
import com.cgy.mycollections.functions.ble.scan.BLEScanner;
import com.cgy.mycollections.functions.ble.scan.IBLEScanObserver;
import com.cgy.mycollections.functions.ble.server.BluetoothServer;
import com.cgy.mycollections.listeners.OnTItemClickListener;
import com.cgy.mycollections.utils.BinaryUtil;
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
    @BindView(R.id.ble_data)
    TextView mBleData;

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

//        String demoBle = "SSID:mxi,PWD:88888888";
        String demoBle = "SSID:NO8,PWD:linkage@12345,SSIDHidden";
        mBleData.setText(demoBle);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mScanner != null) {
            mScanner.stopScan(false);
            mScanner = null;
        }
    }

    @OnClick({R.id.start_scan, R.id.stop_scan, R.id.open_server, R.id.start_broad, R.id.stop_broad, R.id.close_device, R.id.send_data})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_scan:
                mBleDeviceList.clear();
                mDeviceAdapter.setData(mBleDeviceList);

                //开启蓝牙服务
                PermissionManager.requestBluetoothPermission(this, "打开蓝牙权限啦！");
                break;
            case R.id.stop_scan:
                if (mScanner != null)
                    mScanner.stopScan(false);
                break;
            case R.id.close_device:
                mBleDeviceList.clear();
                mDeviceAdapter.setData(mBleDeviceList);

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
            case R.id.send_data://客户端发送数据
                sendData();
                break;
            default:
                break;
        }
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
                }

                @Override
                public void onConnected() {
                    L.e("客户端连接成功，可以发送数据");
                }
            });

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

    BLEClient mBLEClient;

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
                    showToast("扫描结束！");
                    mLogV.setText("扫描结束！");
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
        closeDevice();
        if (mScanner != null)
            mScanner.stopScan(false);
        if (device != null) {
            mBLEClient = new BLEClient(BleDemo.this);
            mBLEClient.connect(device, mCallback);
//            mBluetoothGatt = device.connectGatt(this, false, mGattCallback);
        }
    }

    DataCallback mCallback = new DataCallback() {
        @Override
        public void onGetBleResponse(final String data, final byte[] rawData) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String lockModuleStr = BinaryUtil.bytesToASCIIStr(true, rawData); //转成字符串
                    showToast("收到设备回调数据 raw：" + data);
                    showToast("收到设备回调数据 解析：" + lockModuleStr);
                    mLogV.setText("收到设备回调数据：" + lockModuleStr);
                    if (!TextUtils.isEmpty(lockModuleStr) && lockModuleStr.contains("connect success")) {
                        L.e("网关设备 网络连接成功！");
                    }
                }
            });
        }

        @Override
        public void onConnected() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showToast("连接成功，可以发送数据");
                    mLogV.setText("连接成功，可以发送数据");
                    mBLEClient.setMTU(50);
                }
            });
        }
    };

    public void sendData() {
        String data = mBleData.getText().toString();
        if (TextUtils.isEmpty(data)) {
            showToast("请先输入蓝牙传输数据");
            return;
        }
        if (mBLEClient == null) {
            showToast("还未连接设备");
            return;
        }
        mBLEClient.writeData(data);
    }

    public void closeDevice() {
        if (mBLEClient != null) {
            mBLEClient.closeDevice();
            mBLEClient = null;
        }
//        if (mWriteCharacteristic != null) {
//            mWriteCharacteristic = null;
//        }
//
//        if (mBluetoothGatt != null) {
//            mBluetoothGatt.disconnect();
//            refreshDeviceCache();
//            mBluetoothGatt.close();//据说直接在disconnect后面close会导致STATE_DISCONNECTED收不到，确实是这样 待实验
//            mBluetoothGatt = null;
//        }
    }

    //</editor-fold>
}
