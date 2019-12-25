package com.cgy.mycollections.functions.ble.deviceframe;

/**
 * Description :
 * Author :cgy
 * Date :2018/8/7
 */
public class BLEConstants {
    /**
     * The profile is in disconnected state
     */
    public static final int STATE_DISCONNECTED = 0;
    /**
     * The profile is in connecting state
     */
    public static final int STATE_CONNECTING = 1;
    /**
     * The profile is in connected state
     */
    public static final int STATE_CONNECTED = 2;
    public final static int STATE_SCANNING = 2000;//在扫描
    public static final int STATE_READY_TO_SEND_COMMAND = 2001;//设备准备好去发送指令
    public final static int STATE_DES_WRITE = 2006;

    //错误码
    public final static int ERR_SERVICE_NOT_FOUND = 1010;//没找到服务
    //    public final static int ERR_DEVICE_NOT_PAIRE = 1010;
    public final static int ERR_DEVICE_NOT_FOUND = 1020;
    public final static int ERR_DEVICE_DISCONNECT = 1030;
    public final static int ERR_DEVICE_WAIT_TIMEOUT = 1040;
    public final static int ERR_DEVICE_PAIRE_FAIL = 1050;
    public final static int ERR_DEVICE_CONNECT_FAIL = 1060;
    public final static int ERR_DEVICE_FOUND_MORE = 1070;
    public final static int ERR_DEVICE_PAIRE_TIMEOUT = 1080;//设备连接超时
    public final static int ERR_DEVICE_UNKNOW = 1090;
    public final static int ERR_DEVICE_DATATRANSFAIL = 1110;
    public final static int ERR_DEVICE_SHUTDOWN = 1120;
    public final static int ERR_DEVICE_MEASERROR = 1130;
//    public final static int ERR_INITCMD_ERROR = 1140;

    //以下是指令相关
    public final static int COMMAND_RECEIVE_SUCCESS = 1;//指令收到回调成功
    public final static int ERR_COMMAND_TIME_OUT = 1100;//指令超时
    public final static int ERR_COMMAND_SEND_FAIL = 1101;//指令发送失败
//    public final static int STATUS_CONNECT = 2000;
//    public final static int STATUS_DISCONNECT = 2001;
//    public final static int STATUS_CONNECT_END = 2002;
//    //    public final static int STATUS_SENDDATA = 2002;
//    public final static int STATUS_CONNECTTING = 2003;
//    public final static int STATUS_MEASURING = 2004;
//    public final static int STATUS_LOW_BATTERY = 2005;
//    public final static int QUEUE_INTERVAL_TIME_NORMAL = 400;
//    public final static int QUEUE_INTERVAL_TIME_SHORT = 40;


}
