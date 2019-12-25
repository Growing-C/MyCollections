package com.cgy.mycollections.functions.ble.deviceframe;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.cgy.mycollections.functions.ble.client.CommandHelper;
import com.cgy.mycollections.functions.ble.deviceframe.interfaces.IBLEDeviceInterface;
import com.cgy.mycollections.functions.ble.deviceframe.interfaces.IDeviceConnectListener;
import com.cgy.mycollections.functions.ble.deviceframe.interfaces.IRssiCallback;
import com.cgy.mycollections.functions.ble.deviceframe.interfaces.ISeparable;
import appframe.utils.L;
import com.cgy.mycollections.utils.dataparse.CHexConverter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static android.bluetooth.BluetoothDevice.TRANSPORT_LE;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public abstract class DeviceBaseBT4 extends BluetoothGattCallback implements IBLEDeviceInterface, ISeparable {

    protected final String TAG = getClass().getSimpleName();
    protected long connectStartTime;//连接开始时间，仅供调试使用

    //<editor-fold desc="function params ">
    Context mContext;

    protected IDeviceConnectListener mDeviceListener;//设备连接 发送命令指令回调
    private IRssiCallback mRssiCallback;

    private BluetoothDevice mDevice;
    protected BluetoothGatt mBluetoothGatt;//设备连接后产生的蓝牙gatt
    protected BluetoothGattCharacteristic mWriteCharacteristic;//写入

    public Handler mHandler = new Handler();
    public LooperHandler mLooperHandler = new LooperHandler();//在子线程中

    private final int CONNECT_TIMEOUT = 25000;//默认的连接超时时间 25s
    private boolean autoReconnect = false;//是否自动重连
    private boolean autoConnectInner = false;//connectGatt是否自动重连
    protected boolean stop = false;

    protected int discoveryServiceTimes = 0; //发现服务次数，有时候找不到服务
    private int reWriteTimes = 0; //重新发送数据次数，最多5, 全局的，在发送完成之前 累计只能重发5次
    private int totalCacheReWriteTimes = 0; //全局重新发送数据次数，最多5,   累计只能重发5次
    private int reConnectTimes = 0; //重新连接次数，最多5
    private int mReConnectLimit = 5; //重新连接次数限制，默认最多5

    private int mConnectStatus = BLEConstants.STATE_DISCONNECTED;

    private int mReceiveDataTimeOut = 1000;//设置1秒没收到返回就重发指令

    protected Map<String, String> mCommandFuncMap = new HashMap<>();//TODO:无用 待删除
    private boolean isCommandQueueStarted = false;//命令队列是否开始

    private List<Command> mCommandList = new ArrayList<>();//待发送的命令

    private List<byte[]> mCommandCache = new ArrayList<>();//命令缓存
    private Map<String, List<byte[]>> mCommandDataCacheMap = new HashMap<>();//需要接收数据的command缓存

    protected BaseLockCommandSender mCommandCreator;

    //</editor-fold>

    public DeviceBaseBT4(Context context, @NonNull BluetoothDevice device) {
        this.mDevice = device;
        this.mContext = context;
        if (mDevice == null)
            throw new NullPointerException("device can't be null");

        String phoneModel = Build.MANUFACTURER;//手机型号  三星note8 SCV37  特殊处理
        L.d(TAG, "手机型号：" + phoneModel);
        if (!TextUtils.isEmpty(phoneModel) && phoneModel.contains("samsung")) {
            L.e(TAG, "三星手机特殊处理，设置autoConnect为true");
            //其实已经没啥必要了，不过 为false的时候三星还是存在失败的情况，但是概率已经很小了，为true连接不会失败但是也不慢，暂时保留
            autoConnectInner = true;
        }
    }

    //<editor-fold desc="IBELDeviceInterface impl">

    @Override
    public void startConnect(final IDeviceConnectListener listener) {//close()之后只有这个方法可以重新连接
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                stop = false;
                clean();
                if (mLooperHandler.isShutDown()) {
                    mLooperHandler.start();
                }

                if (listener != null)
                    mDeviceListener = listener;

                L.e(TAG, "startConnect attemp to bond:" + "[" + mDevice.getName() + "]");
                mConnectStatus = BLEConstants.STATE_CONNECTING;

                onConnectStatus(mConnectStatus);//回调出去连接状态

                connectStartTime = System.currentTimeMillis();
                //  autoConnect=false连接的快，此处使用autoConnectInner是因为三星手机老是连接失败，报133，不得已用之，
                // 但是 autoConnect=true连接特别慢,待后续寻找其他解决办法
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mBluetoothGatt = mDevice.connectGatt(mContext, autoConnectInner, DeviceBaseBT4.this, TRANSPORT_LE);
                } else {
                    mBluetoothGatt = mDevice.connectGatt(mContext, autoConnectInner, DeviceBaseBT4.this);
                }
                //连接超时暂未纳入重新连接范围
                mHandler.postDelayed(timeOutRunnable, CONNECT_TIMEOUT);
            }
        });
    }

    @Override
    public IDeviceConnectListener getDeviceListener() {
        return mDeviceListener;
    }

    /**
     * 设置connectGatt时是否是autoconnect
     *
     * @param autoConnect
     */
    public void setInnerAutoConnect(boolean autoConnect) {
        this.autoConnectInner = autoConnect;
    }

    @Override
    public void reStart() {
        if (stop) {
            return;
        }
        clean();
        //重连有个1s延时，此时把状态重新设置为连接中，防止这1s中状态会变成STATE_DISCONNECTED
        mConnectStatus = BLEConstants.STATE_CONNECTING;

        mHandler.removeCallbacks(restartRunnable);
        mHandler.postDelayed(restartRunnable, 1000);
    }

    @Override
    public synchronized void close(boolean shouldNotify) {//调了close 只有再调startConnect 才可以重新连接
        stop = true;//关闭之后 所有动作都应该停止

        clean();
        mLooperHandler.shutDown();

        if (shouldNotify)
            onConnectStatus(BluetoothProfile.STATE_DISCONNECTED);//由于主动close不会走onConnectionStateChange 故手动调用

        if (mDeviceListener != null)//清空防止close之后还会调用回调
            mDeviceListener = null;

        L.e(TAG, "BT4 device closed");
    }

    @Override
    public boolean isSameDevice(BluetoothDevice device) {
        if (mDevice != null && device != null) {
            if (mDevice == device || mDevice.getAddress().equals(device.getAddress()))
                return true;
        }
        return false;
    }

    @Override
    public void setReceiveTimeOut(int millis) {
        this.mReceiveDataTimeOut = millis;
    }

    /**
     * Sets auto reconnect.
     *
     * @param isNeedReconnect the is need reconnect
     */
    @Override
    public void setAutoReconnect(boolean isNeedReconnect, int reConnectLimit) {
        this.autoReconnect = isNeedReconnect;
        this.mReConnectLimit = reConnectLimit;
    }

    /**
     * 获取连接状态
     *
     * @return
     */
    @Override
    public int getConnectStatus() {
        return mConnectStatus;
    }

    /**
     * 由于signature问题 需要收到signature才能发送命令 此处加一个这个来设置状态
     *
     * @param mConnectStatus
     */
    @Override
    public void setConnectStatus(int mConnectStatus) {
        this.mConnectStatus = mConnectStatus;
    }

    @Override
    public void setRssiCallback(IRssiCallback rssiCallback) {
        this.mRssiCallback = rssiCallback;
    }

//    /**
//     * @param command        待发送指令
//     * @param commandStr     发送指令 string代码 如 0010之类
//     * @param commandFuncTag 命令代表的功能 如 getSignature
//     */
//    @Override
//    public void sendCommand(final byte[] command, String commandStr, String commandFuncTag) {
//        if (!TextUtils.isEmpty(commandStr) && !TextUtils.isEmpty(commandFuncTag)) {
//            mCommandFuncMap.put(commandStr, commandFuncTag);
//        }
//        if (mBluetoothGatt != null) {
//            mCommandCache.add(command);//添加命令
//            Log.e(TAG, "------sendCommand addCommand ：" + commandFuncTag + " 命令字符：" + commandStr + "  命令长度：" + command.length);
//
//            if (!isCommandQueueStarted) {
////                mCommandCache.clear();//开始发送的时候确保清空
//                reWriteTimes = 0;
//                isCommandQueueStarted = true;
//                mHandler.postDelayed(mSendCommandRunnable, 8);
//            }
//
//        }
//    }

    /**
     * 新的发送指令，承包了分包，等待回调等功能
     *
     * @param command
     */
    @Override
    public synchronized void sendCommand(Command command) {
        if (command == null || command.sendBytes == null) {
            L.e(TAG, "------>没有指令发什么发？？？");
            return;
        }
        if (mBluetoothGatt != null) {
            mCommandList.add(command);//添加命令
            L.e(TAG, "111 CommandLifeCycle 指令 " + command.commandTag + "------Device sendCommand 添加指令 命令字符：" + command.getCommandStr(this) + "  命令长度：" + command.sendBytes.length);
//
            if (!isCommandQueueStarted) {
                reWriteTimes = 0;
                isCommandQueueStarted = true;
                mLooperHandler.postDelayed(mNewSendCommandRunnable, 8);
            }

        }
    }

    @Override
    public void addCommandCreator(BaseLockCommandSender commandCreator) {
        if (commandCreator == null) {
            this.mCommandCreator = CommandFactory.createCommandSender(this);
        } else {
            this.mCommandCreator = commandCreator;
        }
        if (this.mCommandCreator != null) {
            mCommandCreator.bindDevice(this);//指令发送者绑定该设备
        }
    }

    @Override
    public BaseLockCommandSender getCommandCreator() {
        if (mCommandCreator == null) {//防止为空
            addCommandCreator(null);
        }
        return mCommandCreator;
    }

    //</editor-fold>


    //<editor-fold desc="BluetoothGattCallback impl">

    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        super.onConnectionStateChange(gatt, status, newState);
        try {
            //只要走了这里 就不管超时不超时了
            mHandler.removeCallbacks(timeOutRunnable);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (stop) {
            L.e(TAG, "222.onConnectionStateChange but stopped");
            return;
        }
        String str = null;
        switch (newState) {
            case BluetoothProfile.STATE_CONNECTED:
                mConnectStatus = BLEConstants.STATE_CONNECTED;
                L.e(TAG, "connect success -->cost  :" + (System.currentTimeMillis() - connectStartTime) + " millis");
                str = "STATE_CONNECTED";
                connectStartTime = System.currentTimeMillis();//测试时间消耗用
                getCommandCreator().runCommandQueue();

                //主线程启动
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (DeviceBaseBT4.this) {
                            if (mBluetoothGatt != null)
                                mBluetoothGatt.discoverServices();
                        }
                    }
                });
                if (mRssiCallback != null)//需要才查
                    readRssi(true);
                break;
            case BluetoothProfile.STATE_DISCONNECTED:
//                if (mRssiCallback != null)
//                    readRssi(false);
                str = "STATE_DISCONNECTED";
                L.e(TAG, "--STATE_DISCONNECTED status:" + status);
                if (autoReconnect) {//自动重连 的话不会调用onConnectStatus（STATE_DISCONNECTED） 除非连了几次都失败
                    if (reConnectTimes < mReConnectLimit) {
                        reStart();
                        return;
                    }
                }
                clean();
//                mConnectStatus = BLEConstants.STATE_DISCONNECTED;//断开连接状态只在不重连的时候才会改变成这个状态
                break;
            default:
                mConnectStatus = newState;//断开连接状态只在不重连的时候才会改变成这个状态
                break;
        }

        L.e(TAG, "--onConnectionStateChange:" + str + "  state:" + mConnectStatus);
        onConnectStatus(newState);
    }

    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
//        L.e(TAG, "++++++++++receive command callback,remove resend runnable~~~");
//        mHandler.removeCallbacks(mReSendRunnable);//只要收到命令就取消
        mCommandDataCacheMap.clear();//收到命令就要清空，不然会有问题
    }

    /**
     * 通知指令结果，需配合上层 实现类使用，用于通知 某个指令返回了
     *
     * @param function
     * @param resultCode
     */
    public void notifyCommandResult(String function, int resultCode) {
        if (!mCommandList.isEmpty()) {
            Command lastSendCommand = mCommandList.get(0);
            if (lastSendCommand.needResult && lastSendCommand.isCommandSend()
                    && !TextUtils.isEmpty(function) && function.equalsIgnoreCase(lastSendCommand.commandTag)) {
                L.e(TAG, "444 CommandLifeCycle 指令：" + lastSendCommand.commandTag + " ++++++++++收到设备回调， 设置回调成功，唤醒阻塞线程");
                //上个发送的指令 需要结果，且指令已经发送,此时回调收到锁端回复
                lastSendCommand.setCommandResult(resultCode);
            }
        }
    }

    /**
     * -60 ~ 0   4
     * -70 ~ -60 3
     * -80 ~ -70 2
     * <-80 1
     *
     * @param gatt
     * @param rssi
     * @param status
     */
    @Override
    public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
        super.onReadRemoteRssi(gatt, rssi, status);
        if (status == BluetoothGatt.GATT_SUCCESS) {
            if (mRssiCallback != null) {
                mRssiCallback.onRssiCallback(status, rssi);
            }
        }
    }

    @Override
    public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        super.onDescriptorWrite(gatt, descriptor, status);
        L.d(TAG, "onDescriptorWrite");
        onConnectStatus(BLEConstants.STATE_DES_WRITE);//在执行到这一步之后执行close不会抛异常，否则会抛异常
    }

    //</editor-fold>


    //<editor-fold desc="external method">


    @Override
    public List<byte[]> separate(byte[] sourceBytes) {
        //TODO:后续利用起来
        List<byte[]> divideBytes = CommandHelper.divideBytes(sourceBytes, 20);
        L.e(TAG, "++++++++分包，该指令已经分为  " + divideBytes.size() + " 个包");
        return divideBytes;
    }


    /**
     * 恢复到可以连接的状态
     */
    protected synchronized void clean() {
        mConnectStatus = BLEConstants.STATE_DISCONNECTED;
        discoveryServiceTimes = 0;
        reWriteTimes = 0;
        totalCacheReWriteTimes = 0;
        isCommandQueueStarted = false;
        mHandler.removeCallbacksAndMessages(null);//清空所有runnable
        mLooperHandler.removeAll();//清空所有runnable
        mCommandCache.clear();
        mCommandFuncMap.clear();
        mCommandDataCacheMap.clear();
        mCommandList.clear();

        getCommandCreator().shutDownCommandQueue();//关闭指令队列

        if (mWriteCharacteristic != null) {
            mWriteCharacteristic = null;
        }
        if (mBluetoothGatt != null) {
            mBluetoothGatt.disconnect();
            refreshDeviceCache();
            if (mBluetoothGatt != null)
                mBluetoothGatt.close();//据说直接在disconnect后面close会导致STATE_DISCONNECTED收不到，确实是这样 待实验
            mBluetoothGatt = null;
        }

    }


    /**
     * Clears the device cache. After uploading new hello4 the DFU target will have other services than before.
     */
    public boolean refreshDeviceCache() {
        L.e(TAG, "refreshDeviceCache");
        /*
         * There is a refresh() method in BluetoothGatt class but for now it's hidden. We will call it using reflections.
         */
        try {
            final Method refresh = BluetoothGatt.class.getMethod("refresh");
            if (refresh != null) {
                final boolean success = (Boolean) refresh.invoke(mBluetoothGatt);
                L.i(TAG, "Refreshing result: " + success);
                return success;
            }
        } catch (Exception e) {
            L.e(TAG, "An exception occured while refreshing device：" + e);
        }
        return false;
    }


    public void onError(int errorCode) {
        if (mDeviceListener != null)
            mDeviceListener.onError(errorCode);
    }

    public void onConnectStatus(int connectStatus) {
        if (mDeviceListener != null)
            mDeviceListener.onConnectStatus(connectStatus);
    }

    public boolean setCharacteristicNotification(UUID serviceUuid, UUID characteristicUuid,
                                                 UUID descriptorUuid, boolean enable) {
        if (stop) {
            return false;
        }
        L.e(TAG, "setCharacteristicNotification  start ");
        BluetoothGattCharacteristic characteristic = mBluetoothGatt.getService(serviceUuid)
                .getCharacteristic(characteristicUuid);
        mBluetoothGatt.setCharacteristicNotification(characteristic, enable);
        if (descriptorUuid != null) {//指定descriptor
            BluetoothGattDescriptor descriptor = characteristic.getDescriptor(descriptorUuid);
            descriptor.setValue(enable ? BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE : BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
            mBluetoothGatt.writeDescriptor(descriptor); //descriptor write operation successfully started?
        } else {//未指定descriptor
            for (BluetoothGattDescriptor dp : characteristic.getDescriptors()) {
//                L.e(TAG, "setCharacteristicNotification BluetoothGattDescriptor:" + dp.getUuid());
                if (dp != null) {
                    if ((characteristic.getProperties() & BluetoothGattCharacteristic.PROPERTY_NOTIFY) != 0) {
                        L.e(TAG, "1...ENABLE_NOTIFICATION_VALUE");
                        dp.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                    } else if ((characteristic.getProperties() & BluetoothGattCharacteristic.PROPERTY_INDICATE) != 0) {
                        L.e(TAG, "2...ENABLE_INDICATION_VALUE");
                        dp.setValue(BluetoothGattDescriptor.ENABLE_INDICATION_VALUE);//测试中发现大锁使用ENABLE_INDICATION_VALUE的话就没下文了，所以基本还是用ENABLE_NOTIFICATION_VALUE
                    }
                    mBluetoothGatt.writeDescriptor(dp);
                }
            }
        }
//        L.e(TAG, "setCharacteristicNotification  end ");
        return true;
    }

    /**
     * 读取蓝牙RSSi线程
     */
    public void readRssi(boolean isReadRssi) {
        if (isReadRssi && mBluetoothGatt != null) {
            mHandler.postDelayed(mRssiRunnable, 1000);
        } else {
            mHandler.removeCallbacks(mRssiRunnable);
        }
    }

    /**
     * 某一条指令生命周期结束，所有指令发送完成或者失败都必须走这里
     *
     * @param command
     */
    private synchronized void commandLifecycleEnd(@NonNull Command command, int commandResultCode) {
        //为了避免和 sendCommand发生混乱，加上锁
        doOnCommandEnd();
        mCommandList.remove(command);
        L.e(TAG, "555 CommandLifeCycle 指令 " + command.commandTag + " 结果已返回:" + commandResultCode + "，指令周期结束，移除指令,-1代表不需要结果，commandList size=" + mCommandList.size());

        if (!mCommandList.isEmpty())//还有就继续发
            mLooperHandler.postDelayed(mNewSendCommandRunnable, 8);//30-->10-->5 为了减少升级文件发送时间
        else {//全都发完了
            isCommandQueueStarted = false;
        }
        //成功回调 在对应的设备类里面，此处只管部分非指令返回的失败回调
        //指令结果不是成功，且指令需要返回结果，此时就肯定是指令失败了，回调出去
        if (commandResultCode != BLEConstants.COMMAND_RECEIVE_SUCCESS && commandResultCode != -1) {
            BaseDeviceResponse response = new BaseDeviceResponse();
            response.setSuccess(false);
            response.setErrorCode(commandResultCode);
            mDeviceListener.onCommandResult(command.commandTag, response);
        }
    }

    /**
     * 供外部使用，在指令结束后调用
     */
    protected void doOnCommandEnd() {

    }

    //</editor-fold>

    //<editor-fold desc="Runnable">
    //连接超时线程
    private Runnable timeOutRunnable = new Runnable() {
        @Override
        public void run() {
            if (!stop) {
//            只有找到设备正在配对中并且未成功才会进入这里
                //目前连接超时并没有终止设备连接过程，所以报了超时 依然在连接，仅用作日志查看，待后续看是否有必要纳入重连过程
                onError(BLEConstants.ERR_DEVICE_PAIRE_TIMEOUT);
            }

        }
    };

    //重连线程
    private Runnable restartRunnable = new Runnable() {
        @Override
        public void run() {
            L.e(TAG, "reStart reConnectTimes:" + reConnectTimes);
            reConnectTimes++;
            startConnect(null);
        }
    };

    //发送指令线程,新的模式 此线程使用looperHandler，在子线程中，可以做耗时操作
    private Runnable mNewSendCommandRunnable = new Runnable() {
        @Override
        public void run() {
            if (mWriteCharacteristic != null) {
                try {
                    //由于此过程比较长，需要确保 整个过程 锁具都是连接着，所以 断开连接的时候需要确保 中断该线程
                    Command command = mCommandList.get(0);//永远发第一个

                    byte[] sendByte = command.sendBytes;
                    List<byte[]> childBytes = separate(sendByte);//先分包
                    L.e(TAG, "222 CommandLifeCycle 指令 " + command.commandTag + "  "
                            + Thread.currentThread().getName() + " start->开始发送指令，指令内容："
                            + command.commandTag + "  \nbytes:" + CHexConverter.byte2HexStr(command.sendBytes));

                    for (int i = 0, len = childBytes.size(); i < len; i++) {
                        int childPackageRewriteTimeLimit = 3;//每个分包最多只可以失败3次
                        int rewriteTime = 0;//重发次数

                        byte[] childByte = childBytes.get(i);
                        mWriteCharacteristic.setValue(childByte);
                        mWriteCharacteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);

                        boolean writeSuccess = mBluetoothGatt.writeCharacteristic(mWriteCharacteristic);
//                        L.e(TAG, "发送第 " + i + " 个分包  ，长度：" + childByte.length +
//                                ",writeSuccess?= " + writeSuccess + ", 重发次数:" + rewriteTime);  //如果isBoolean返回的是true则写入成功

                        while (!writeSuccess) {
                            if (rewriteTime >= childPackageRewriteTimeLimit) {
                                L.e(TAG, "已经重发3次了，代表分包发送失败，是否需要重发？" + command.needResend);
                                if (command.needResend && command.resendTimes < 3) {
                                    //需要重发
                                    command.resendTimes++;
                                    L.e(TAG, "指令需要指令级重发，准备第" + command.resendTimes + "次重发");
                                    mLooperHandler.postDelayed(mNewSendCommandRunnable, 8);//30-->10-->5 为了减少升级文件发送时间
                                } else {
                                    //不需要重发 或者由外部处理，结束该指令的生命周期
                                    L.e(TAG, "指令发送失败，移除指令，回调失败");
                                    commandLifecycleEnd(command, BLEConstants.ERR_COMMAND_SEND_FAIL);
                                }
                                return;
                            }
                            L.e(TAG, "分包 " + i + " 发送失败，重新发送,第 " + rewriteTime + " 次重发");  //如果isBoolean返回的是true则写入成功

                            //没有发送成功就重新发送
                            writeSuccess = mBluetoothGatt.writeCharacteristic(mWriteCharacteristic);
                            rewriteTime++;
                        }
                        //到此处说明分包发送成功
//                        L.e(TAG, "第 " + i + " 个分包发送成功，准备发送下一条");

                        Thread.sleep(8);//延时8ms发送下一个分包
                    }
                    // 走到这里说明整个指令发送完成了，
                    command.setCommandSendSuccess();//设置指令已发送
                    int commandResultCode = -1;
                    if (command.needResult) {
                        L.e(TAG, "333 CommandLifeCycle 指令 " + command.commandTag + " 需要返回结果，等待锁端响应~~~~");
                        commandResultCode = command.waitForCommandResult();
                        //TODO:如果超时是否要重发
                    }
                    commandLifecycleEnd(command, commandResultCode);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private Runnable mRssiRunnable = new Runnable() {
        @Override
        public void run() {
            if (mBluetoothGatt != null) {
                mBluetoothGatt.readRemoteRssi();
                readRssi(true);
            }
        }
    };

    //</editor-fold>


}

//    /**
//     * 缓存命令，等待超时重发，目前仅支持一个完整command指令的重发，同时发送多条要监视的指令的情况暂时不支持
//     *
//     * @param divideBytes
//     * @param commandStr
//     * @param commandFuncTag
//     */
//    @Override
//    public void cacheCommand(List<byte[]> divideBytes, String commandStr, String commandFuncTag) {
//        mCommandDataCacheMap.clear();//一次保证只有一条命令缓存
//        mHandler.removeCallbacks(mReSendRunnable);//每次cache的时候都清除掉可能存在的定时器
//        if (divideBytes != null && divideBytes.size() > 0 && !TextUtils.isEmpty(commandStr)) {
//            L.e(TAG, "cacheCommand:" + commandFuncTag + "++++++++++command:" + commandStr);
//            mCommandDataCacheMap.put(commandStr, divideBytes);
//        }
//    }

//    /**
//     * 超过一定时间就要重发数据
//     */
//    private void startResendCommandCountDown() {
//        Log.e(TAG, "++++++++++send complete >>start watch command mReSendRunnable-----");
//        mHandler.removeCallbacks(mReSendRunnable);
//        mHandler.postDelayed(mReSendRunnable, mReceiveDataTimeOut);
//    }

//    //发送指令 老的模式
//    private Runnable mSendCommandRunnable = new Runnable() {
//        @Override
//        public void run() {
//            if (mWriteCharacteristic != null) {
//                byte[] command = mCommandCache.get(0);//永远发第一个
//
//                Log.e(TAG, Thread.currentThread().getName() + "->给设备发送了消息，内容：" + CHexConverter.byte2HexStr(command, command.length));
//                mWriteCharacteristic.setValue(command);
//                mWriteCharacteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
//                boolean writeSuccess = mBluetoothGatt.writeCharacteristic(mWriteCharacteristic);
//                Log.e(TAG, "mSendCommandRunnable writeSuccess?= " + writeSuccess + ", reWriteTimes:" + reWriteTimes);  //如果isBoolean返回的是true则写入成功
//                if (writeSuccess) {//写成功就移除第一个
//                    mCommandCache.remove(0);
//                    if (!mCommandCache.isEmpty())//还有就继续发
//                        mHandler.postDelayed(mSendCommandRunnable, 8);//30-->10-->5 为了减少升级文件发送时间
//                    else {//全都发完了
//                        isCommandQueueStarted = false;
////                        if (!BinaryUtil.bytesToStr(true, command).contains("010A")) {
//                        if (totalCacheReWriteTimes < 3)//缓存数据只重发4次
//                            startResendCommandCountDown();//等待数据返回
////                        }
//                    }
//                } else if (reWriteTimes < 5) { //写失败了，继续写，最多5次
//                    mHandler.removeCallbacks(mSendCommandRunnable);
//                    mHandler.postDelayed(mSendCommandRunnable, 10);
//                    reWriteTimes++;
//                } else {//超过5次 完全失败 直接断开连接
//                    close(true);
//                }
//            }
//        }
//    };

//    //超过一定时间没有收到回复数据 会重新发送
//    private Runnable mReSendRunnable = new Runnable() {
//        @Override
//        public void run() {
//            if (!mCommandDataCacheMap.isEmpty()) {
//                totalCacheReWriteTimes++;
//                // 就取第一个
//                String commandStr = mCommandDataCacheMap.keySet().iterator().next();
//                List<byte[]> divideBytes = mCommandDataCacheMap.get(commandStr);
//
//                Log.e(TAG, "++++++++++command no response -->resend:" + commandStr);
//
//                String commandFuncTag = mCommandFuncMap.get(commandStr);
//                for (int i = 0; i < divideBytes.size(); i++) {
//                    byte[] commandItem = divideBytes.get(i);
//
//                    sendCommand(commandItem, commandStr, commandFuncTag);
//                }
//            }
//        }
//    };

//    protected void setNotifyEnable() {
//        if (mReadCharacteristic != null) {
//            boolean b = mBluetoothGatt.setCharacteristicNotification(mReadCharacteristic, true);
////            boolean b1 = mCurrentGatt.setCharacteristicNotification(mWriteCharacteristic, true);
//            for (BluetoothGattDescriptor dp : mReadCharacteristic.getDescriptors()) {
//                if (dp != null) {
//                    if ((mReadCharacteristic.getProperties() & BluetoothGattCharacteristic.PROPERTY_NOTIFY) != 0) {
//                        dp.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
//                    } else if ((mReadCharacteristic.getProperties() & BluetoothGattCharacteristic.PROPERTY_INDICATE) != 0) {
//                        dp.setValue(BluetoothGattDescriptor.ENABLE_INDICATION_VALUE);
//                    }
//                    mBluetoothGatt.writeDescriptor(dp);
//                }
//            }
//            Log.e(TAG, "nofityEnable:Read=" + b);
//        }
//    }


//    @Override
//    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
//        super.onCharacteristicWrite(gatt, characteristic, status);
//        final byte[] dealBytes = characteristic.getValue();
//        String result = CHexConverter.byte2HexStr(dealBytes, dealBytes.length);
//        Log.e(TAG, "2.---------onCharacteristicWrite：" + result);
//    }