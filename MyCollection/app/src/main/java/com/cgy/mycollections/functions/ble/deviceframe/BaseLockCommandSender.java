package com.cgy.mycollections.functions.ble.deviceframe;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;


import com.cgy.mycollections.functions.ble.client.CommandHelper;
import com.cgy.mycollections.functions.ble.deviceframe.interfaces.IBLEDeviceInterface;
import appframe.utils.L;
import com.cgy.mycollections.utils.dataparse.BinaryUtil;
import com.cgy.mycollections.utils.dataparse.DataFormater;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.internal.observers.FutureObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by cgy on 2018/9/14.
 * 锁具命令发送基础类
 */
public abstract class BaseLockCommandSender {

    //<editor-fold desc="指令队列">

    private static final String TAG = "BaseLockCommandSender";
    //    private final int QUEUE_SIZE = 50;//队列大小
    private LinkedBlockingQueue<Command> mCommandQueue = new LinkedBlockingQueue<>();//默认容量无限
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private volatile boolean pause = false;

    private Thread mCommandThread;//指令发送线程
    private IBLEDeviceInterface mDevice;

    public BaseLockCommandSender() {
    }

    /**
     * 绑定设备
     *
     * @param device
     */
    public void bindDevice(IBLEDeviceInterface device) {
        this.mDevice = device;
    }

    private void addToQueue(byte[] sendBytes, String externalTag, boolean needResult) {
        Command command = new Command();
        command.sendBytes = sendBytes;
//        command.device = mDevice;
        command.needResult = needResult;

        String commandBytes = CommandHelper.getCommandString(sendBytes, 13, 12);//当前锁具返回的指令 如0100
//        String function = CommonLockConstants.sFukiLockCommandFuncMap.get(commandBytes);
        String function = "function";
        L.e("addToQueue--》externalTag:" + externalTag + "--->" + commandBytes + "--func--" + function);
        command.commandTag = TextUtils.isEmpty(function) ? externalTag : function;
        //commandTag优先按照指令对应的功能来设置

        add(command);
    }


    /**
     * 彻底销毁这个类 不再使用
     */
    public void destroy() {
        shutDownCommandQueue();
    }

    public synchronized void add(final Command command) {
        requireNonNull(command);
        try {
            if (mCommandQueue != null) {
                L.e(TAG, "添加指令到队列 command：" + command.commandTag + "  content:" + DataFormater.bytesToStr(true, command.sendBytes));
                mCommandQueue.put(command);
            }
        } catch (Exception e) {
            L.e(TAG, "error:  add到队列异常   " + e.toString());
        }
    }

    /**
     * 清空当前指令的队列
     */
    public synchronized void clearCommandQueue() {
        L.e(TAG, "清空当前指令的队列");
        mHandler.removeCallbacksAndMessages(null);
        if (mCommandQueue != null)
            mCommandQueue.clear();
    }

    /**
     * 关闭当前设备指令发送者队列，清空所有相关资源
     */
    public void shutDownCommandQueue() {
        if (mCommandThread != null && !mCommandThread.isInterrupted()) {
            L.e(TAG, "指令线程强制中断");
            mCommandThread.interrupt();
            mCommandThread = null;
        }
        clearCommandQueue();
    }


    public void runCommandQueue() {
        shutDownCommandQueue();

        L.e(TAG, "执行runQueue");
        mCommandThread = new Thread() {
            @Override
            public void run() {
                L.e(TAG, "runQueue 线程开始运行  name:" + Thread.currentThread().getName());
                for (; ; ) {
                    LinkedBlockingQueue<Command> commandQueue = mCommandQueue;
                    if (commandQueue == null) {
                        L.e(TAG, "该设备队列 已经找不到了，终止当前线程循环：" + Thread.currentThread().getName());
                        break;
                    }
                    if (!pause && mDevice != null) {
                        final Command command;
                        try {
                            L.e(TAG, "线程准备取出指令：" + Thread.currentThread().getName());
                            command = commandQueue.take();
                            L.e(TAG, Thread.currentThread().getName() + "-->执行队列里指令 " + "  command:" + command.commandTag);
//                            sendCommand(command.sendBytes, command.commandTag, command.needResult);

                            mDevice.sendCommand(command);
                            if (command.needResult) {
                                L.e(TAG, "SSSSSSS CommandQueue 需要result  ,调用getCommandResult，10s超时，期间阻塞线程");
                                int commandResult = command.waitForCommandResult();
                                L.e(TAG, "SSSSSSS CommandQueue 收到result  ,返回指令结果：" + commandResult);

                                //错误回调 放到 DeviceBase里面去了
                                if (commandResult == BLEConstants.ERR_COMMAND_TIME_OUT) {
                                    L.e(TAG, "SSSSSSS CommandQueue 指令超时");
                                }
                            }
                        } catch (InterruptedException e) {
//                            e.printStackTrace();
                            L.e(TAG, Thread.currentThread().getName() + "：线程中断 ，抛出InterruptedException");
                            return;
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        mCommandThread.start();
    }

    private static <T> T requireNonNull(T obj) {
        if (obj == null)
            throw new NullPointerException();
        return obj;
    }
    //</editor-fold>


    /**
     * 蓝牙数据帧定义
     * 分隔符	2B
     * CRC校验	2B
     * 协议版本号	1B
     * Payload长度	2B
     * payload数据内容（command+内容）(command  2B 内容 nB)
     */
    //协议版本号0001
    public byte[] head = DataFormater.strToBytes(false, "FEFE");
    public byte[] version = DataFormater.strToBytes(false, "01");
    public byte[] foot = DataFormater.strToBytes(false, "3B");


    //--------------------公有方法用abstract-------------------------

    //------------------------通用------------------------------------------

    /**
     * 根据command获取payLoadList，预先把command放进去，该方法提取出来仅仅是为了省几行代码。。。
     *
     * @param commandStr
     * @return
     */
    protected List<byte[]> createPayloadListFromCommand(String commandStr) {
        byte[] command = DataFormater.strToBytes(false, commandStr);
        List<byte[]> payloadBytesList = new ArrayList<>();
        payloadBytesList.add(command);
        return payloadBytesList;
    }


    public static void main(String[] args) {
        final FutureObserver<String> observer = new FutureObserver<>();
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                Thread.sleep(2000);
                System.out.println("observable sub thread:" + Thread.currentThread().getName());
                e.onNext("1");
                observer.countDown();
                System.out.println("aaaaaaaaaaaaaaa");
            }
        }).subscribeOn(Schedulers.io());
        new Thread() {
            @Override
            public void run() {
                System.out.println("1 thread:" + Thread.currentThread().getName());
                try {
                    String s = observer.get();
                    System.out.println("1 observer.get():" + s);
                    System.out.println("1 observer.isDone():" + observer.isDone());
                    System.out.println("1 observer.isDisposed():" + observer.isDisposed());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                System.out.println("2 thread:" + Thread.currentThread().getName());
                try {
                    String s = observer.get();
                    System.out.println("2 observer.get():" + s);
                    System.out.println("2 observer.isDone():" + observer.isDone());
                    System.out.println("2 observer.isDisposed():" + observer.isDisposed());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        System.out.println(" current thread:" + Thread.currentThread().getName());
        System.out.println("111111111111111");
        observable.subscribe(observer);
        System.out.println("22222222222222");
        try {
            String s = observer.get();
            System.out.println("observer.get():" + s);
            System.out.println("observer.isDone():" + observer.isDone());
            System.out.println("observer.isDisposed():" + observer.isDisposed());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("3333333333333");
    }


}
