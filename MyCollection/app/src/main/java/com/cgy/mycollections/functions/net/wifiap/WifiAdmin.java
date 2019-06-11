package com.cgy.mycollections.functions.net.wifiap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.ResultReceiver;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;

import com.cgy.mycollections.functions.net.WifiAPDemo;
import com.cgy.mycollections.utils.L;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * Description :
 * Author :cgy
 * Date :2019/5/27
 */
public class WifiAdmin {
    WifiManager mWifiManager;

    WifiInfo mWifiInfo;

    Context mContext;

    public static final int WIFI_AP_STATE_UNKNOWN = -1;
    public static final int WIFI_AP_STATE_DISABLING = 10;
    public static final int WIFI_AP_STATE_DISABLED = 11;
    public static final int WIFI_AP_STATE_ENABLING = 12;
    public static final int WIFI_AP_STATE_ENABLED = 13;
    public static final int WIFI_AP_STATE_FAILED = 14;

    /**
     * 构造器，获取wifi信息和管理对象
     * 注意！！！
     * location权限必须要，不然 扫描扫不到wifi，连接也连不上！！
     *
     * @param context      
     */
    public WifiAdmin(Context context) {
        this.mContext = context;
        //获取WifiManager对象
        mWifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        //获取WifiInfo对象
        mWifiInfo = mWifiManager.getConnectionInfo();
        if (mWifiInfo != null) {
            L.e("cur wifi = " + mWifiInfo.getSSID());
            L.e("cur getNetworkId = " + mWifiInfo.getNetworkId());
        }

        if (!mWifiManager.isWifiEnabled()) {
            L.e("wifi 未打开，打开wifi，结果：" + mWifiManager.setWifiEnabled(true));
        }
        getWifiIP();

//        LocationManager locManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
//
//        if(!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
//            // 未打开位置开关，可能导致定位失败或定位不准，提示用户或做相应处理
//        }
    }

    /**
     * 扫描Wifi
     * 1.有的ssid为""，也就是ssid !＝ null,获取不到ssid
     * 设置中并没有多余wifi，但这个热点点其它信息可以获取到，说明这个热点是存在的，
     *       是该热点隐藏了，所以获取不到。这也就是手机设置中为什么会有添加网路的按钮了
     * 2  wifi列表中有许多同名的wifi热点，也就是扫描的结果中有重合部分，并不是有多个同名的wifi热点
     *   当附近wifi热点比较少时不会出现此问题，当附近wifi网络比较多时会出现此问题。这就需要将同
     *   名的热点进行删除，但是如果真有两个ssid名相同的wifi，那就可以通过capabilities去区分吧，
     *   如果capabilities也相同就没办法了，系统设置里面也不显示同名的
     */
    public void startScan() {
        L.e("开始扫描wifi");
        //开始扫描
        //貌似必须有权限以及打开GPS才能扫到  不然一直为空
        mWifiManager.startScan();//startScan之后直接.getScanResults()可能拿到上次的扫描结果，需要在广播里面接收本次结果
    }

    public WifiManager getWifiManager() {
        return mWifiManager;
    }

    public void test() {
        List<WifiConfiguration> existingConfigs = mWifiManager.getConfiguredNetworks();
        for (WifiConfiguration existingConfig : existingConfigs) {
            if (existingConfig == null) continue;
            L.e("已有的ssid:" + existingConfig.SSID + "  密码：" + existingConfig.preSharedKey);
            if (existingConfig.SSID.contains("NO8")) {
//                connectNet(existingConfig);
//                return;
            }
        }
    }

    /**
     * 断开所有已经配置过的网络
     */
    public void disableAllConfigedNetwork() {
        List<WifiConfiguration> existingConfigs = mWifiManager.getConfiguredNetworks();
        for (WifiConfiguration existingConfig : existingConfigs) {
            if (existingConfig == null) continue;
            L.e(existingConfig.networkId + "--断开已有的ssid:" + existingConfig.SSID + "  密码：" + existingConfig.preSharedKey);
            mWifiManager.disableNetwork(existingConfig.networkId);
        }
    }

    public boolean connectNet(ScanResult result, String password) {
        WifiConfiguration configuration = createWifiInfo(result, password);
        L.e("连接扫描到的网络 ：" + result);
//        L.e("连接扫描到的网络 配置：" + configuration);
        return connectNet(configuration);
    }

    public boolean connectHotpot(String SSID, String pwd) {
//        SSID: NO8, BSSID: 8c:a6:df:90:3f:91, capabilities: [WPA2-PSK-CCMP+TKIP][WPA-PSK-CCMP+TKIP][ESS], level: -42, frequency: 2472, timestamp: 358238073085, distance: ?(cm), distanceSd: ?(cm), passpoint: no, ChannelBandwidth: 0, centerFreq0: 0, centerFreq1: 0,
        //        SSID = "qvtest";
//
//        key = "12345678";
        WifiConfiguration wc = createWifiInfo(SSID, pwd, "[WPA2-PSK-CCMP][ESS]");

        return connectNet(wc);
    }

    public boolean connectNet(WifiConfiguration netConfig) {

//        int res = mWifiManager.addNetwork(netConfig);
        int netId = netConfig.networkId;
        if (netId == -1) {
            netId = mWifiManager.addNetwork(netConfig);
            L.e("new net  addNetwork:" + netId);
        }
        mWifiManager.disconnect();
        boolean b = mWifiManager.enableNetwork(netId, true);
//        boolean b = connectWifiByReflectMethod(netId);
        mWifiManager.reconnect();

        L.e("connectNet networkId : " + netId + " 连接结果：" + b);

//        mWifiInfo = mWifiManager.getConnectionInfo();
//        if (mWifiInfo != null) {
//            L.e("cur wifi = " + mWifiInfo.getSSID());
//            L.e("cur getNetworkId = " + mWifiInfo.getNetworkId());
//        }
        return b;
    }

    /**
     * 由于存在 已经连接其他wifi的情况,此时断开wifi 再连接指定wifi的时候 系统也会自动连接优先级高的wifi，可能导致连接指定wifi失败
     * 所以
     * 通过反射出不同版本的connect方法来连接Wifi
     * 似乎依然无法解决问题！！
     *
     * @param netId
     * @return
     * @author jiangping.li
     * @since MT 1.0
     */
    private boolean connectWifiByReflectMethod(int netId) {
        Method connectMethod = null;
        L.e("connectWifiByReflectMethod road 1");
        // 反射方法： connect(int, listener) , 4.2 <= phone's android version
        for (Method methodSub : mWifiManager.getClass()
                .getDeclaredMethods()) {
            if ("connect".equalsIgnoreCase(methodSub.getName())) {
                Class<?>[] types = methodSub.getParameterTypes();
                if (types != null && types.length > 0) {
                    if ("int".equalsIgnoreCase(types[0].getName())) {
                        connectMethod = methodSub;
                    }
                }
            }
        }
        if (connectMethod != null) {
            try {
                L.e("调用系统 connect方法 netId：" + netId);
                connectMethod.invoke(mWifiManager, netId, null);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                L.e("connectWifiByReflectMethod Android "
                        + Build.VERSION.SDK_INT + " error!");
                return false;
            }
        } else {
            L.e("connect wifi by enableNetwork method, Add by jiangping.li");
            // 通用API
            return mWifiManager.enableNetwork(netId, true);
        }
    }

    /**
     * 创建 WifiConfiguration，这里创建的是wpa2加密方式的wifi
     *
     * @param ssid     wifi账号
     * @param password wifi密码
     * @return
     */
    private WifiConfiguration createWifiInfo(String ssid, String password, String capabilities) {
        WifiConfiguration config = null;
        config = getExistingConfig(ssid);
        if (config != null)
            return config;

        config = new WifiConfiguration();
        config.hiddenSSID = false;
        config.status = WifiConfiguration.Status.ENABLED;
        L.e("capabilities:" + capabilities);
//        capabilities:[WPA2-PSK-CCMP][ESS]
        if (capabilities.contains("WEP")) {
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
            config.SSID = "\"" + ssid + "\"";
            config.wepTxKeyIndex = 0;
            config.wepKeys[0] = password;
        } else if (capabilities.contains("PSK")) {
            config.SSID = "\"" + ssid + "\"";
            config.preSharedKey = "\"" + password + "\"";
        } else if (capabilities.contains("EAP")) {
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_EAP);
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            config.SSID = "\"" + ssid + "\"";
            config.preSharedKey = "\"" + password + "\"";
        } else {
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.SSID = "\"" + ssid + "\"";
            config.preSharedKey = null;
            config.wepKeys[0] = "\"" + "\"";
            config.wepTxKeyIndex = 0;
        }
        //据说不加这句经常连不上
        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);

        return config;
    }

    private WifiConfiguration getExistingConfig(String SSID) {
        if (mWifiManager != null) {
            List<WifiConfiguration> existingConfigs = mWifiManager
                    .getConfiguredNetworks();
            for (WifiConfiguration existingConfig : existingConfigs) {
                if (existingConfig.SSID.equals("\"" + SSID + "\"")) {
                    L.e("已经连接过这个网络，直接获取存在的配置");
                    return existingConfig;
                }
            }
        }
        return null;
    }

    private WifiConfiguration createWifiInfo(ScanResult scan, String Password) {
        WifiConfiguration config = new WifiConfiguration();
        config.hiddenSSID = false;
        config.status = WifiConfiguration.Status.ENABLED;
        L.e("capabilities:" + scan.capabilities);
//        capabilities:[WPA2-PSK-CCMP][ESS]
        if (scan.capabilities.contains("WEP")) {
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
            config.SSID = "\"" + scan.SSID + "\"";
            config.wepTxKeyIndex = 0;
            config.wepKeys[0] = Password;
        } else if (scan.capabilities.contains("PSK")) {
            config.SSID = "\"" + scan.SSID + "\"";
            config.preSharedKey = "\"" + Password + "\"";
        } else if (scan.capabilities.contains("EAP")) {
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_EAP);
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            config.SSID = "\"" + scan.SSID + "\"";
            config.preSharedKey = "\"" + Password + "\"";
        } else {
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.SSID = "\"" + scan.SSID + "\"";
            config.preSharedKey = null;
            config.wepKeys[0] = "\"" + "\"";
            config.wepTxKeyIndex = 0;
        }
        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        return config;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void openHotpotBySysApi() {
        @SuppressLint("HandlerLeak") final Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                Log.e("WifiAP", "handleMessage:" + msg.what);
                switch (msg.what) {
                    case 2018:
                        WifiConfiguration wifiConfiguration = (WifiConfiguration) msg.obj;
                        break;
                    default:
                        break;
                }
            }
        };

        //android 8.0 通过系统api来开启热点，这种方法热点ssid和密码都是随机的, 且连接上无法上网
        mWifiManager.startLocalOnlyHotspot(new WifiManager.LocalOnlyHotspotCallback() {

            @Override
            public void onStarted(WifiManager.LocalOnlyHotspotReservation reservation) {
                super.onStarted(reservation);
                WifiConfiguration wifiConfiguration = reservation.getWifiConfiguration();
                //可以通过config拿到开启热点的账号和密码
                mHandler.obtainMessage(2018, wifiConfiguration).sendToTarget();
                Log.e("WifiAP", "openHotpotBySysApi onStarted ssid:" + wifiConfiguration.SSID +
                        "   password:" + wifiConfiguration.preSharedKey);

            }

            @Override
            public void onStopped() {
                super.onStopped();
            }

            @Override
            public void onFailed(int reason) {
                super.onFailed(reason);

                Log.e("WifiAP", "openHotpotBySysApi onFailed:" + reason);
            }

        }, mHandler);
    }

    private void startTethering() {
        //不行，会报invocationException。。。
        ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mWifiManager != null) {
            int wifiState = mWifiManager.getWifiState();
            boolean isWifiEnabled = ((wifiState == WifiManager.WIFI_STATE_ENABLED) || (wifiState == WifiManager.WIFI_STATE_ENABLING));
            if (isWifiEnabled)
                mWifiManager.setWifiEnabled(false);
        }
        if (mConnectivityManager != null) {
            try {
                Field internalConnectivityManagerField = ConnectivityManager.class.getDeclaredField("mService");
                internalConnectivityManagerField.setAccessible(true);
                WifiConfiguration apConfig = new WifiConfiguration();
                apConfig.SSID = "cuieney";
                apConfig.preSharedKey = "12121212";

                StringBuffer sb = new StringBuffer();
                Class internalConnectivityManagerClass = Class.forName("android.net.IConnectivityManager");
                ResultReceiver dummyResultReceiver = new ResultReceiver(null);
                try {

                    Method mMethod = mWifiManager.getClass().getMethod("setWifiApConfiguration", WifiConfiguration.class);
                    mMethod.setAccessible(true);
                    mMethod.invoke(mWifiManager, apConfig);
                    Method startTetheringMethod = internalConnectivityManagerClass.getDeclaredMethod("startTethering",
                            int.class,
                            ResultReceiver.class,
                            boolean.class);

                    startTetheringMethod.invoke(internalConnectivityManagerClass,
                            0,
                            dummyResultReceiver,
                            true);
                } catch (NoSuchMethodException e) {
                    Method startTetheringMethod = internalConnectivityManagerClass.getDeclaredMethod("startTethering",
                            int.class,
                            ResultReceiver.class,
                            boolean.class,
                            String.class);

                    startTetheringMethod.invoke(internalConnectivityManagerClass,
                            0,
                            dummyResultReceiver,
                            false,
                            mContext.getPackageName());
                } catch (InvocationTargetException e) {
                    sb.append(11 + (e.getMessage()));
                    e.printStackTrace();
                } finally {
                    Log.e("WifiAP", sb.toString());
                }


            } catch (Exception e) {
                Log.e("WifiAP", Log.getStackTraceString(e));
            }
        }
    }


    /**
     * 打开热点
     */
    public void openWifiHotpot() {
        int wifiApState = getWifiAPState();
        boolean wifiApIsOn = wifiApState == WIFI_AP_STATE_ENABLED || wifiApState == WIFI_AP_STATE_ENABLING;
        if (!wifiApIsOn) {
            //没开ap
            startTethering();
        }
//        new SetWifiAPTask(!wifiApIsOn).execute();
    }

    public void close() {
        int wifiApState = getWifiAPState();
        boolean wifiApIsOn = wifiApState == WIFI_AP_STATE_ENABLED || wifiApState == WIFI_AP_STATE_ENABLING;
        if (wifiApIsOn) {
//            new WifiAPDemo.SetWifiAPTask(false).execute();
        } else {
//            showToast("热点已关闭！");
        }
    }


    /**
     * Endable/disable wifi
     *
     * @param enabled
     * @return WifiAP state
     */
    private int setWifiApEnabled(boolean enabled) {
        Log.d("WifiAP", "*** setWifiApEnabled CALLED **** " + enabled);
        if (enabled && mWifiManager.getConnectionInfo() != null) {
            mWifiManager.setWifiEnabled(false);
            try {
                Thread.sleep(1500);
            } catch (Exception e) {
            }
        }

        //int duration = Toast.LENGTH_LONG;
        //String toastText = "MobileAP status: ";
        int state = WIFI_AP_STATE_UNKNOWN;
        try {
            mWifiManager.setWifiEnabled(false);
            Method method1 = mWifiManager.getClass().getMethod("setWifiApEnabled",
                    WifiConfiguration.class, boolean.class);
            method1.invoke(mWifiManager, null, enabled); // true
            Method method2 = mWifiManager.getClass().getMethod("getWifiApState");
            state = (Integer) method2.invoke(mWifiManager);
        } catch (Exception e) {
            L.e(e.getMessage());
            // toastText += "ERROR " + e.getMessage();
        }

        if (!enabled) {
            int loopMax = 10;
            while (loopMax > 0 && (getWifiAPState() == WIFI_AP_STATE_DISABLING
                    || getWifiAPState() == WIFI_AP_STATE_ENABLED
                    || getWifiAPState() == WIFI_AP_STATE_FAILED)) {
                try {
                    Thread.sleep(500);
                    loopMax--;
                } catch (Exception e) {
                }
            }
            mWifiManager.setWifiEnabled(true);
        } else if (enabled) {
            int loopMax = 10;
            while (loopMax > 0 && (getWifiAPState() == WIFI_AP_STATE_ENABLING
                    || getWifiAPState() == WIFI_AP_STATE_DISABLED
                    || getWifiAPState() == WIFI_AP_STATE_FAILED)) {
                try {
                    Thread.sleep(500);
                    loopMax--;
                } catch (Exception e) {
                }
            }
        }

        return state;
    }


    public int getWifiAPState() {
        int state = WIFI_AP_STATE_UNKNOWN;
        try {
            Method method2 = mWifiManager.getClass().getMethod("getWifiApState");
            state = (Integer) method2.invoke(mWifiManager);
        } catch (Exception e) {
        }
        Log.d("WifiAP", "getWifiAPState.state: " + state);
        return state;
    }

    /**
     * 获取wifi的IP地址
     *
     * @return      
     */
    public String getWifiIP() {
        DhcpInfo dhcpInfo = mWifiManager.getDhcpInfo();
        String routeIP = Formatter.formatIpAddress(dhcpInfo.gateway);
        L.e("routeIP:" + routeIP);
//        new Thread(){
//            @Override
//            public void run() {
//                try {
//                    L.e("getWifiIP:" + InetAddress.getLocalHost().toString());
//                } catch (UnknownHostException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();

        return routeIP;
    }
}
