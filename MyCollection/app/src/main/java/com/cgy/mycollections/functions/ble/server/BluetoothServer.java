package com.cgy.mycollections.functions.ble.server;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattServerCallback;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;
import android.os.Build;
import android.os.ParcelUuid;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.cgy.mycollections.functions.ble.client.DataCallback;
import com.cgy.mycollections.utils.CHexConverter;
import com.cgy.mycollections.utils.L;

import java.lang.reflect.Field;
import java.util.UUID;

import static android.content.Context.BLUETOOTH_SERVICE;

/**
 * Description :
 * Author :cgy
 * Date :2018/9/21
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class BluetoothServer {
    private final String TAG = "BluetoothServer";
    private BluetoothManager mBluetoothManager;
    private BluetoothGattServer mBluetoothGattServer;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeAdvertiser mBluetoothLeAdvertiser;

    private Context mContext;
    DataCallback mCallback;

    public static UUID UUID_LOCK_SERVICE = UUID.fromString("0000484a-ff4d-414e-5349-4f4e5f534552");//文档上的
    //                                                          5245535f-4e4f-4953-4e41-4dff4a480000
    public static UUID UUID_LOCK_WRITE = UUID.fromString("0000b002-ff4d-414e-5349-4f4e5f534552");
    public static UUID UUID_LOCK_READ = UUID.fromString("0000b001-ff4d-414e-5349-4f4e5f534552");
    public static UUID UUID_DESCRIPTOR = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    BluetoothGattCharacteristic characteristicRead;

    private BluetoothGattServerCallback mGattServerCallback = new BluetoothGattServerCallback() {
        @Override
        public void onConnectionStateChange(BluetoothDevice device, int status, int newState) {
            L.e(String.format("1.onConnectionStateChange：device name = %s, address = %s", device.getName(), device.getAddress()));
            L.e(String.format("1.onConnectionStateChange：status = %s, newState =%s ,0是断开连接，2是连接成功", status, newState));
            if (newState == BluetoothProfile.STATE_CONNECTED && mCallback != null) {
                mCallback.onConnected();
            }
        }

        @Override
        public void onServiceAdded(int status, BluetoothGattService service) {
            L.e(String.format("onServiceAdded：status = %s ，0是成功", status));
        }

        @Override
        public void onCharacteristicReadRequest(BluetoothDevice device, int requestId, int offset, BluetoothGattCharacteristic characteristic) {
            Log.e("CharacteristicReadReq", "远程设备请求读取数据");
            L.e(String.format("onCharacteristicReadRequest：device name = %s, address = %s", device.getName(), device.getAddress()));
            L.e(String.format("onCharacteristicReadRequest：requestId = %s, offset = %s", requestId, offset));

            mBluetoothGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0});
        }

        @Override
        public void onCharacteristicWriteRequest(BluetoothDevice device, int requestId, BluetoothGattCharacteristic characteristic, boolean preparedWrite, boolean responseNeeded, int offset, byte[] value) {
            Log.e("CharacteristicWriteReq", "远程设备请求写入数据");
            L.e(String.format("3.onCharacteristicWriteRequest：device name = %s, address = %s", device.getName(), device.getAddress()));
            L.e(String.format("3.onCharacteristicWriteRequest：requestId = %s, preparedWrite=%s, responseNeeded=%s, offset=%s, value=%s", requestId, preparedWrite, responseNeeded, offset, CHexConverter.byte2HexStr(value)));

//            mBluetoothGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, new byte[]{0, 9, 8, 7, 6, 5, 4, 3, 2, 1});
            mBluetoothGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, value);
            if (mCallback != null)
                mCallback.onGetBleResponse(new String(value), value);

            //4.处理响应内容
            onResponseToClient(value, device, requestId, characteristic);
        }

        @Override
        public void onDescriptorReadRequest(BluetoothDevice device, int requestId,
                                            int offset, BluetoothGattDescriptor descriptor) {
            Log.e("DescriptorReadReq", "远程设备请求read描述器 onDescriptorReadRequest!");
            mBluetoothGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, new byte[]{10, 11, 12, 13, 14, 15, 16, 17, 18, 19});
        }

        @Override
        public void onDescriptorWriteRequest(BluetoothDevice device,
                                             int requestId, BluetoothGattDescriptor descriptor, boolean preparedWrite,
                                             boolean responseNeeded, int offset, byte[] value) {
            Log.e(TAG, "远程设备请求Write描述器 onDescriptorWriteRequest");

            L.e(String.format("2.onDescriptorWriteRequest：device name = %s, address = %s", device.getName(), device.getAddress()));
            L.e(String.format("2.onDescriptorWriteRequest：requestId = %s, preparedWrite = %s, responseNeeded = %s, offset = %s, value = %s,", requestId, preparedWrite, responseNeeded, offset, CHexConverter.byte2HexStr(value, value.length)));

            // now tell the connected device that this was all successfull
            //调了这个方法  client那边才会走onDescriptorWrite
            mBluetoothGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, value);
        }

        @Override
        public void onExecuteWrite(BluetoothDevice device, int requestId, boolean execute) {
            Log.e("onExecuteWrite", "执行挂起写入操作");
        }

        @Override
        public void onNotificationSent(BluetoothDevice device, int status) {
            Log.e("onNotificationSent", "通知发送 status = " + status);
        }

        @Override
        public void onMtuChanged(BluetoothDevice device, int mtu) {
            Log.e("onMtuChanged", "mtu改变");
            L.e(String.format("onMtuChanged：mtu = %s", mtu));
        }

        /**
         * 4.处理响应内容
         *
         * @param reqeustBytes
         * @param device
         * @param requestId
         * @param characteristic
         */
        private void onResponseToClient(byte[] reqeustBytes, BluetoothDevice device, int requestId, BluetoothGattCharacteristic characteristic) {
            L.e(String.format("4.onResponseToClient：device name = %s, address = %s", device.getName(), device.getAddress()));
            L.e(String.format("4.onResponseToClient：requestId = %s", requestId));
//            String msg = OutputStringUtil.transferForPrint(reqeustBytes);
//            println("4.收到:" + msg);
//            showText("4.收到:" + msg);
            BluetoothGattCharacteristic characteristicRead = mBluetoothGattServer.getService(UUID_LOCK_SERVICE).getCharacteristic(UUID_LOCK_READ);

            L.e("onResponseToClient characteristicRead:" + characteristicRead.getUuid().toString());

            String str = new String(reqeustBytes) + " hello>";
            characteristicRead.setValue(str.getBytes());
            mBluetoothGattServer.notifyCharacteristicChanged(device, characteristicRead, false);

            L.e("onResponseToClient:" + str);
//            println("4.响应:" + str);
//            showText("4.响应:" + str);
        }
    };

    public String getDeviceMac() {
        return getBluetoothAddress(mBluetoothAdapter);
    }

    public String getDeviceName() {
        return mBluetoothAdapter.getName();
    }

    public BluetoothServer(Context context, DataCallback callback) {
        this.mContext = context;
        this.mCallback = callback;
        mBluetoothManager = (BluetoothManager) context.getSystemService(BLUETOOTH_SERVICE);
//        mBluetoothGattServer = mBluetoothManager.openGattServer(context, mGattServerCallback);

        mBluetoothAdapter = mBluetoothManager.getAdapter();
        if (!mBluetoothAdapter.isEnabled()) {
            throw new NullPointerException("open bluetooth please");
        }

        if (mBluetoothAdapter.isMultipleAdvertisementSupported())
            mBluetoothLeAdvertiser = mBluetoothAdapter.getBluetoothLeAdvertiser();
        else
            throw new NullPointerException("BluetoothLeAdvertiser not supported");

//        init();

    }


    private void initServices(Context context) {
        mBluetoothGattServer = mBluetoothManager.openGattServer(context, mGattServerCallback);
        BluetoothGattService service = new BluetoothGattService(UUID_LOCK_SERVICE, BluetoothGattService.SERVICE_TYPE_PRIMARY);

        //add a read characteristic.
        characteristicRead = new BluetoothGattCharacteristic(UUID_LOCK_READ, BluetoothGattCharacteristic.PROPERTY_READ, BluetoothGattCharacteristic.PERMISSION_READ);
        //add a descriptor
        BluetoothGattDescriptor descriptor = new BluetoothGattDescriptor(UUID_DESCRIPTOR, BluetoothGattCharacteristic.PERMISSION_WRITE);
        characteristicRead.addDescriptor(descriptor);
        service.addCharacteristic(characteristicRead);

        //add a write characteristic.
        BluetoothGattCharacteristic characteristicWrite = new BluetoothGattCharacteristic(UUID_LOCK_WRITE,
                BluetoothGattCharacteristic.PROPERTY_WRITE |
                        BluetoothGattCharacteristic.PROPERTY_READ |
                        BluetoothGattCharacteristic.PROPERTY_NOTIFY,
                BluetoothGattCharacteristic.PERMISSION_WRITE);
        service.addCharacteristic(characteristicWrite);

        mBluetoothGattServer.addService(service);
        L.e("2. initServices ok uuid:" + service.getUuid().toString());
    }

//    private void init() {
//        BluetoothGattCharacteristic characteristic = new BluetoothGattCharacteristic(
//                UUID_LOCK_READ,
//                BluetoothGattCharacteristic.PROPERTY_NOTIFY + BluetoothGattCharacteristic.PROPERTY_WRITE + BluetoothGattCharacteristic.PROPERTY_READ,
//                BluetoothGattCharacteristic.PERMISSION_WRITE + BluetoothGattCharacteristic.PERMISSION_READ);
//
//        BluetoothGattService service = new BluetoothGattService(UUID_LOCK_SERVICE, BluetoothGattService.SERVICE_TYPE_PRIMARY);
//
//        service.addCharacteristic(characteristic);
//        mBluetoothGattServer.addService(service);
//    }

    private AdvertiseCallback mAdvertiseCallback = new AdvertiseCallback() {
        @Override
        public void onStartSuccess(AdvertiseSettings settingsInEffect) {
            L.e("广播成功");
            initServices(mContext);
        }

        @Override
        public void onStartFailure(int errorCode) {
            L.e("广播失败" + errorCode);
        }
    };

    /**
     * 开始广播
     */
    public void startAdvertising() {
//        AdvertiseSettings settings = new AdvertiseSettings.Builder()
//                .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_POWER)
//                .setTimeout(0)
//                .setConnectable(true)
//                .build();
//        setAdvertiseMode(int advertiseMode)设置广播的模式，低功耗，平衡和低延迟三种模式；
//        setConnectable(boolean connectable)设置是否可以连接。
//        setTimeout(int timeoutMillis)设置广播的最长时间
//        setTxPowerLevel(int txPowerLevel)设置广播的信号强度

//        byte[] sendData = new byte[]{0x01,0x02,0x03};
//        AdvertiseData advertiseData = new AdvertiseData.Builder()
//                .setIncludeDeviceName(true)
////                .setIncludeTxPowerLevel(true)
//                .addServiceUuid(new ParcelUuid(UUID_LOCK_SERVICE))
//                .addServiceData(new ParcelUuid(UUID_LOCK_SERVICE), sendData)
//                .build();


        AdvertiseSettings settings = new AdvertiseSettings.Builder()
                .setConnectable(true)
                .build();

        AdvertiseData advertiseData = new AdvertiseData.Builder()
                .setIncludeDeviceName(true)
                .setIncludeTxPowerLevel(true)
                .build();

        AdvertiseData scanResponseData = new AdvertiseData.Builder()
                .addServiceUuid(new ParcelUuid(UUID_LOCK_SERVICE))
                .setIncludeTxPowerLevel(true)
                .build();

        mBluetoothLeAdvertiser.startAdvertising(settings, advertiseData, scanResponseData, mAdvertiseCallback);

//        mBluetoothLeAdvertiser.startAdvertising(settings, advertiseData, mAdvertiseCallback);
//        addManufacturerData(int manufacturerId, byte[] manufacturerSpecificData)添加厂商信息，貌似不怎么用到。
//        addServiceUuid(ParcelUuid serviceUuid),addServiceData(ParcelUuid serviceDataUuid, byte[] serviceData)添加服务进广播，即对外广播本设备拥有的服务。
//        setIncludeDeviceName(boolean includeDeviceName)是否广播设备名称。
//        setIncludeTxPowerLevel(boolean includeTxPowerLevel)是否广播信号强度
    }

    /**
     * 结束广播
     */
    public void stopAdvertising() {
        mBluetoothLeAdvertiser.stopAdvertising(mAdvertiseCallback);
        L.e("invoke stopAdvertising");
    }

    /**
     * Android6.0以上的版本获取的蓝牙地址始终为02:00:00:00，
     * Android早就封掉了相关接口， 用反射的方式去获取Mac地址
     *
     * @param adapter
     * @return
     */
    private String getBluetoothAddress(BluetoothAdapter adapter) {
        if (adapter == null) {
            return null;
        }

        try {
            Field mServiceField = adapter.getClass().getDeclaredField("mService");
            mServiceField.setAccessible(true);
            Object btManagerService = mServiceField.get(adapter);
            if (btManagerService != null) {
                return (String) btManagerService.getClass().getMethod("getAddress").invoke(btManagerService);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        int a = 0x14, b = 0x21, c = 0x32;
        int test = 0;
        System.out.println("a:" + a + "   b:" + b + "   c:" + c);
        System.out.println("test & a:" + (test & a));
        System.out.println("test & b:" + (test & b));

        test += a;
        System.out.println("------------------------");
        System.out.println("test & a:" + (test & a));
        System.out.println("test & b:" + (test & b));

        test += b;
        System.out.println("------------------------");
        System.out.println("test & a:" + (test & a));
        System.out.println("test & b:" + (test & b));
    }
}
