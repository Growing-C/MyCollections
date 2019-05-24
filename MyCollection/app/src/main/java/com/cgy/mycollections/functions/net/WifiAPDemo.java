package com.cgy.mycollections.functions.net;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.cgy.mycollections.BaseActivity;
import com.cgy.mycollections.R;
import com.cgy.mycollections.utils.L;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.TimeUnit;

import appframe.permission.PermissionDenied;
import appframe.permission.PermissionGranted;
import appframe.permission.PermissionManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * wifi热点管理（并且通过热点通信）
 */
public class WifiAPDemo extends BaseActivity {
    @BindView(R.id.hotpot_toggle)
    ToggleButton mHotpotToggle;
    @BindView(R.id.wifi_ssid)
    TextInputEditText mSsidV;
    @BindView(R.id.wifi_password)
    TextInputEditText mPwdV;


    WifiManager mWifiManager;

    Disposable mD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_ap_demo);
        ButterKnife.bind(this);


        mWifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        mHotpotToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.e("WifiAP", "onCheckedChanged:" + isChecked);
                toggleWifi();
            }
        });
        if (!mWifiManager.isWifiEnabled()) {
            L.e("wifi 未打开，打开wifi，结果：" + mWifiManager.setWifiEnabled(true));
        }

        L.e("开始扫描wifi");
        mWifiManager.startScan();
        mD = Observable.interval(5000, 5000, TimeUnit.MILLISECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                L.e("轮询：" + aLong);

                List<ScanResult> results = mWifiManager.getScanResults();
                if (results == null || results.isEmpty()) {
                    L.e("未扫描到网络：");
                } else {
                    for (int i = 0; i < results.size(); i++) {
                        L.e("网络 " + i + " :" + results.get(i).toString());
                    }

                    if (mD != null) {
                        mD.dispose();
                        mD = null;
                    }
                }

            }
        });

        WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
        if (wifiInfo != null) {
            L.e("cur wifi = " + wifiInfo.getSSID());
            L.e("cur getNetworkId = " + wifiInfo.getNetworkId());
        }
//        WifiConfiguration mWifiConfig  = mWifiManager.getWifiApConfiguration();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mD != null) {
            mD.dispose();
            mD = null;
        }
    }

    @OnClick({R.id.open_hotpot, R.id.close_hotpot, R.id.get_wifi_state, R.id.connect_hotpot})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.open_hotpot:
                PermissionManager.requestLocationPermission(this, "ness");
                break;
            case R.id.close_hotpot:
//                close();
//                mWifiManager.getWifiState()
                break;
            case R.id.get_wifi_state://获取wifi状态
                getWifiAPState();
                break;
            case R.id.connect_hotpot://连接指定热点
                connectHotpot();
                break;
            default:
                break;
        }
    }

    public void connectNet(WifiConfiguration netConfig) {

//        int res = mWifiManager.addNetwork(netConfig);
        int netId = netConfig.networkId;
        if (netId == -1) {
            netId = mWifiManager.addNetwork(netConfig);
            L.e("new net  addNetwork:" + netId);
        }
//        mWifiManager.disconnect();
        boolean b = mWifiManager.enableNetwork(netId, true);
        mWifiManager.reconnect();

        showToast("connectNet networkId : " + netId + " 连接结果：" + b);
    }

    /**
     * 创建 WifiConfiguration，这里创建的是wpa2加密方式的wifi
     *
     * @param ssid     wifi账号
     * @param password wifi密码
     * @return
     */
    private WifiConfiguration createWifiInfo(String ssid, String password) {
        WifiConfiguration config = new WifiConfiguration();
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();
        config.SSID = "\"" + ssid + "\"";
        config.preSharedKey = "\"" + password + "\"";
        config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
        config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        config.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
        config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
        config.status = WifiConfiguration.Status.ENABLED;
        return config;
    }

    public void connectHotpot() {
        if (mWifiManager != null) {
            List<WifiConfiguration> existingConfigs = mWifiManager.getConfiguredNetworks();
            for (WifiConfiguration existingConfig : existingConfigs) {
                if (existingConfig == null) continue;
                L.e("已有的ssid:" + existingConfig.SSID + "  密码：" + existingConfig.preSharedKey);
                if (existingConfig.SSID.contains("NO8")) {
                    connectNet(existingConfig);
                    return;
                }
            }
        }

        String SSID = mSsidV.getText().toString();
        String key = mPwdV.getText().toString();
        if (TextUtils.isEmpty(SSID) || TextUtils.isEmpty(key)) {
            showToast("请输入ssid/密码！");
            return;
        }
//        SSID: NO8, BSSID: 8c:a6:df:90:3f:91, capabilities: [WPA2-PSK-CCMP+TKIP][WPA-PSK-CCMP+TKIP][ESS], level: -42, frequency: 2472, timestamp: 358238073085, distance: ?(cm), distanceSd: ?(cm), passpoint: no, ChannelBandwidth: 0, centerFreq0: 0, centerFreq1: 0,
        //        SSID = "qvtest";
//
//        key = "12345678";
        WifiConfiguration wc = createWifiInfo(SSID, key);

        connectNet(wc);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @PermissionGranted(requestCode = PermissionManager.REQUEST_LOCATION)
    public void onLocationPermissionGranted() {
        Log.e("WifiAP", "获取到了location 权限");
        openHotpotBySysApi();
    }

    @PermissionDenied(requestCode = PermissionManager.REQUEST_LOCATION)
    public void onPermissionDenied() {
        Log.e("WifiAP", " location 权限被拒绝！");
    }


    public static final int WIFI_AP_STATE_UNKNOWN = -1;
    public static final int WIFI_AP_STATE_DISABLING = 10;
    public static final int WIFI_AP_STATE_DISABLED = 11;
    public static final int WIFI_AP_STATE_ENABLING = 12;
    public static final int WIFI_AP_STATE_ENABLED = 13;
    public static final int WIFI_AP_STATE_FAILED = 14;

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
        ConnectivityManager mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
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
                            getPackageName());
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

    public void toggleWifi() {
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
            new SetWifiAPTask(false).execute();
        } else {
            showToast("热点已关闭！");
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
            Log.e(WIFI_SERVICE, e.getMessage());
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


    private int getWifiAPState() {
        int state = WIFI_AP_STATE_UNKNOWN;
        try {
            Method method2 = mWifiManager.getClass().getMethod("getWifiApState");
            state = (Integer) method2.invoke(mWifiManager);
        } catch (Exception e) {
        }
        Log.d("WifiAP", "getWifiAPState.state: " + state);
        return state;
    }

    private void updateStatusDisplay() {

        if (getWifiAPState() == WIFI_AP_STATE_ENABLED || getWifiAPState() == WIFI_AP_STATE_ENABLING) {
            mHotpotToggle.setChecked(true);
        } else {
            mHotpotToggle.setChecked(false);
        }

    }


    class SetWifiAPTask extends AsyncTask<Void, Void, Void> {

        boolean mMode;

        public SetWifiAPTask(boolean mode) {
            mMode = mode;
        }

        ProgressDialog d = new ProgressDialog(WifiAPDemo.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            d.setTitle("Turning WiFi AP " + (mMode ? "on" : "off") + "...");
            d.setMessage("...please wait a moment.");
            d.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                d.dismiss();
            } catch (IllegalArgumentException e) {
            }
            updateStatusDisplay();
        }

        @Override
        protected Void doInBackground(Void... params) {
            setWifiApEnabled(mMode);
            return null;
        }
    }
}
