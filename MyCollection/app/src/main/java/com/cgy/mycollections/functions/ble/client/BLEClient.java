package com.cgy.mycollections.functions.ble.client;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.os.Build;

import com.cgy.mycollections.functions.ble.server.BluetoothServer;
import com.cgy.mycollections.utils.dataparse.CHexConverter;
import com.cgy.mycollections.utils.L;

import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;

/**
 * Description :
 * Author :cgy
 * Date :2019/6/10
 */
public class BLEClient {
    public static int BLE_PACKAGE_LEN = 50;//蓝牙包长度，大于这个就要分包

    Context mContext;
    DataCallback callback;

    public BLEClient(Context context) {
        this.mContext = context;
    }

    public void connect(BluetoothDevice device, DataCallback dataCallback) {
        this.callback = dataCallback;
        if (device != null) {
            mBluetoothGatt = device.connectGatt(mContext, false, mGattCallback);
        }
    }

    /**
     * 发送数据
     *
     * @param raw
     */
    public void writeData(String raw) {
        if (raw == null || raw.length() == 0) {
            L.e("BLEClient", "writeData data=null");
            return;
        }
        if (mWriteCharacteristic == null || mBluetoothGatt == null) {
            L.e("BLEClient", "mWriteCharacteristic/mBluetoothGatt为空，可能没连上");
            return;
        }

        new Thread() {
            @Override
            public void run() {
                byte[] command = raw.getBytes();//永远发第一个
                L.e("writeData 给设备发送了消息，内容：" + CHexConverter.byte2HexStr(command));

//                String lockModuleStr = BinaryUtil.bytesToASCIIStr(true, command); //转成字符串(此方法中文会乱码)
                String lockModuleStr = new String(command); //转成字符串
                L.e("发送消息内容：" + lockModuleStr);


                List<byte[]> childBytes = CommandHelper.divideBytes(command, BLE_PACKAGE_LEN);
                L.e("BLEClient", "++++++++分包，该指令已经分为  " + childBytes.size() + " 个包");

                for (int i = 0, len = childBytes.size(); i < len; i++) {
//            int childPackageRewriteTimeLimit = 3;//每个分包最多只可以失败3次
//            int rewriteTime = 0;//重发次数

                    byte[] childByte = childBytes.get(i);
                    mWriteCharacteristic.setValue(childByte);
                    mWriteCharacteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);

                    boolean writeSuccess = mBluetoothGatt.writeCharacteristic(mWriteCharacteristic);
                    L.e("writeData writeSuccess?= " + writeSuccess);  //如果isBoolean返回的是true则写入成功
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }.start();


//        mWriteCharacteristic.setValue(command);
//        mWriteCharacteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
//        boolean writeSuccess = mBluetoothGatt.writeCharacteristic(mWriteCharacteristic);
//        L.e("writeData writeSuccess?= " + writeSuccess);  //如果isBoolean返回的是true则写入成功
    }

    /**
     * 设置传输数据长度
     *
     * @param mtu
     * @return
     */
    public boolean setMTU(int mtu) {
        L.e("BLE", "setMTU " + mtu);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (mtu > 20) {
                boolean ret = mBluetoothGatt.requestMtu(mtu);
                L.e("BLE", "requestMTU " + mtu + " ret=" + ret);
                return ret;
            }
        }

        return false;
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
                                setCharacteristicNotification(s.getUuid(), c.getUuid(), BluetoothServer.UUID_DESCRIPTOR);
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
            L.e("特征值数据改变-->onCharacteristicChanged UUID:" + characteristic.getUuid().toString());
            final byte[] dealBytes = characteristic.getValue();
            //回复给客户端的数据Jim
//            E5 9B 9E E5 A4 8D E7 BB 99 E5 AE A2 E6 88 B7 E7 AB AF E7 9A 84 E6 95 B0 E6 8D AE 4A 69 6D
            String result = CHexConverter.byte2HexStr(dealBytes);
            L.e("收到蓝牙服务端数据 onCharacteristicChanged value：" + result);
            if (callback != null) {
                callback.onGetBleResponse(result, dealBytes);
            }
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

//            setMTU(50);
//            setMTU(20);
            if (BLE_PACKAGE_LEN > 20) {
                //mtu默认是20 超过20 就需要手动设置了
                setMTU(BLE_PACKAGE_LEN);
            } else if (callback != null) {
                //包长度小于20就直接返回了
                callback.onConnected();
            }
//            new ToastCustom(mContext, "onDescriptorWrite 连接成功，可以发送数据！", Toast.LENGTH_LONG).show();


//            byte[] command = new byte[]{11, 22, 33, 44, 55, 66};//永远发第一个
//            L.e("onDescriptorWrite 给设备发送了消息，内容：" + CHexConverter.byte2HexStr(command));
//            mWriteCharacteristic.setValue(command);
//            mWriteCharacteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
//            boolean writeSuccess = mBluetoothGatt.writeCharacteristic(mWriteCharacteristic);
//            L.e("onDescriptorWrite writeSuccess?= " + writeSuccess);  //如果isBoolean返回的是true则写入成功
        }

        @Override
        public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
            super.onMtuChanged(gatt, mtu, status);
            L.e("onMtuChanged");
            if (callback != null)
                callback.onConnected();
        }


        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);

            L.e("onCharacteristicWrite UUID:" + characteristic.getUuid().toString());
            final byte[] dealBytes = characteristic.getValue();

//            String result = CHexConverter.byte2HexStr(dealBytes, dealBytes.length);
//            String result = BinaryUtil.bytesToASCIIStr(true, dealBytes);//(此方法中文乱码)
            String result = new String(dealBytes);
            L.e(String.format(" onCharacteristicWrite： result = %s", "收到蓝牙服务端确认数据 onCharacteristicChanged value：" + result));

        }
    };

    boolean setCharacteristicNotification(UUID serviceUuid, UUID characteristicUuid,
                                          UUID descriptorUuid) {
        L.e("setCharacteristicNotification  start ");
        boolean enable = true;
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
}
