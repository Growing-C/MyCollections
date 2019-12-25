package com.cgy.mycollections.functions.ble.deviceframe;

import com.cgy.mycollections.functions.ble.deviceframe.interfaces.IBLEDeviceInterface;
import com.cgy.mycollections.utils.L;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.internal.observers.FutureObserver;

/**
 * Description :
 * Author :cgy
 * Date :2019/4/8
 */
public class Command {
    public byte[] sendBytes;//实际的指令
    public String commandTag;//指令的tag，是根据 指令如 6010 获得的functionCode  尽量保证不为空，否则会有问题
    public boolean needResult = false;//指令是发出去不管还是需要回调

    public boolean needResend = false;//指令发送失败的话是否需要重试，默认不重试
    public int resendTimes = 0;//重试次数
//    public IBLEDeviceInterface device;
//    public VerticaleCallback callback;

    public long commandTimeOutInMillis = 10000;//指令超时时间

    private boolean commandSendSuccess = false;//代表指令发送结果
    private FutureObserver<Integer> mCommandResult = new FutureObserver<>();//用于处理指令结果回调，仅当needResult为true的时候使用

    public synchronized void setCommandSendSuccess() {
        commandSendSuccess = true;
    }

    public boolean isCommandSend() {
        return commandSendSuccess;
    }

    /**
     * 获取指令结果，该方法会阻塞线程，所以不要在主线程中使用
     *
     * @return
     * @throws ExecutionException
     */
    public int waitForCommandResult() throws ExecutionException, InterruptedException {
        try {
            //指令10s超时
            return mCommandResult.get(commandTimeOutInMillis, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            e.printStackTrace();
            return BLEConstants.ERR_COMMAND_TIME_OUT;
        }
    }

    /**
     * 设置指令回调结果，注意只能调一次，调用该方法会使得 waitForCommandResult 立即返回
     *
     * @param resultCode
     */
    public void setCommandResult(int resultCode) {
        if (mCommandResult.isDone()) {
            L.e("Stub!!  指令已经设置过一次了，怎么又来设置了？");
            return;
        }
        L.e("设置指令结果：" + resultCode);
        mCommandResult.onNext(resultCode);
        mCommandResult.countDown();
    }

    /**
     * 获取指令，如 6010
     *
     * @param device
     * @return
     */
    public String getCommandStr(IBLEDeviceInterface device) {
        try {
            return CommandFactory.getCommandStrByDevice(device, sendBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
