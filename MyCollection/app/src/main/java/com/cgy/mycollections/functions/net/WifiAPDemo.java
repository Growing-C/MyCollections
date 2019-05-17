package com.cgy.mycollections.functions.net;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.cgy.mycollections.BaseActivity;
import com.cgy.mycollections.R;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import appframe.permission.PermissionDenied;
import appframe.permission.PermissionGranted;
import appframe.permission.PermissionManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * wifi热点管理（并且通过热点通信）
 */
public class WifiAPDemo extends BaseActivity {
    @BindView(R.id.hotpot_toggle)
    ToggleButton mHotpotToggle;
    WifiManager mWifiManager;

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
//        WifiConfiguration mWifiConfig  = mWifiManager.getWifiApConfiguration();
//
//        WifiInfo wifiInfo  = mWifiManager.getConnectionInfo(); 
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

                break;
            default:
                break;
        }
    }

    public void connectHotpot() {

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
