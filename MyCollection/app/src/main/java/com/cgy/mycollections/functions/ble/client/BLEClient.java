package com.cgy.mycollections.functions.ble.client;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.widget.Toast;

import com.cgy.mycollections.functions.ble.server.BluetoothServer;
import com.cgy.mycollections.utils.CHexConverter;
import com.cgy.mycollections.utils.L;

import java.lang.reflect.Method;
import java.util.UUID;

import appframe.utils.ToastCustom;

/**
 * Description :
 * Author :cgy
 * Date :2019/6/10
 */
public class BLEClient {

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

        byte[] command = raw.getBytes();//永远发第一个
        L.e("writeData 给设备发送了消息，内容：" + CHexConverter.byte2HexStr(command));
        mWriteCharacteristic.setValue(command);
        mWriteCharacteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
        boolean writeSuccess = mBluetoothGatt.writeCharacteristic(mWriteCharacteristic);
        L.e("writeData writeSuccess?= " + writeSuccess);  //如果isBoolean返回的是true则写入成功
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
            L.e("onCharacteristicChanged UUID:" + characteristic.getUuid().toString());
            final byte[] dealBytes = characteristic.getValue();

            String result = CHexConverter.byte2HexStr(dealBytes, dealBytes.length);
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

            if (callback!=null)
                callback.onConnected();
//            new ToastCustom(mContext, "onDescriptorWrite 连接成功，可以发送数据！", Toast.LENGTH_LONG).show();


//            byte[] command = new byte[]{11, 22, 33, 44, 55, 66};//永远发第一个
//            L.e("onDescriptorWrite 给设备发送了消息，内容：" + CHexConverter.byte2HexStr(command));
//            mWriteCharacteristic.setValue(command);
//            mWriteCharacteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
//            boolean writeSuccess = mBluetoothGatt.writeCharacteristic(mWriteCharacteristic);
//            L.e("onDescriptorWrite writeSuccess?= " + writeSuccess);  //如果isBoolean返回的是true则写入成功
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
