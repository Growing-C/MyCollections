package com.cgy.mycollections.functions.net;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.cgy.mycollections.BaseActivity;
import com.cgy.mycollections.R;
import com.cgy.mycollections.functions.net.wifiap.WifiAdapter;
import com.cgy.mycollections.functions.net.wifiap.WifiAdmin;
import com.cgy.mycollections.listeners.OnTItemClickListener;
import com.cgy.mycollections.utils.L;

import java.util.Iterator;
import java.util.List;

import appframe.permission.PermissionDenied;
import appframe.permission.PermissionGranted;
import appframe.permission.PermissionManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;


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
    @BindView(R.id.scan_list)
    RecyclerView mScanListV;

    WifiAdapter mWifiAdapter;

    WifiAdmin mWifiAdmin;

    Disposable mD;

    BroadcastReceiver mWifiScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {
                int state = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
                switch (state) {
                    /**
                     * WIFI_STATE_DISABLED    WLAN已经关闭
                     * WIFI_STATE_DISABLING   WLAN正在关闭
                     * WIFI_STATE_ENABLED     WLAN已经打开
                     * WIFI_STATE_ENABLING    WLAN正在打开
                     * WIFI_STATE_UNKNOWN     未知
                     */
                    case WifiManager.WIFI_STATE_DISABLED: {
                        L.e("WIFI已经关闭");
                        break;
                    }
                    case WifiManager.WIFI_STATE_DISABLING: {
                        L.e("WIFI正在关闭");
                        break;
                    }
                    case WifiManager.WIFI_STATE_ENABLED: {
                        L.e("WIFI已经打开");
//                        sortScaResult();
                        break;
                    }
                    case WifiManager.WIFI_STATE_ENABLING: {
                        L.e("WIFI正在打开");
                        break;
                    }
                    case WifiManager.WIFI_STATE_UNKNOWN: {
                        L.e("未知状态");
                        break;
                    }
                }
            } else if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
                NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                if (NetworkInfo.State.DISCONNECTED == info.getState()) {//wifi没连接上
                    L.e("wifi没连接上:" + info.toString());
                } else if (NetworkInfo.State.CONNECTED == info.getState()) {//wifi连接上了
                    L.e("wifi连接上了:" + info.toString());
                } else if (NetworkInfo.State.CONNECTING == info.getState()) {//正在连接
                    L.e("wifi正在连接:" + info.toString());
                }
            } else if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                L.e("网络列表变化了:" + intent.toString());
//                Bundle bd = intent.getExtras();
//                if (bd != null) {
//                    Iterator it = bd.keySet().iterator();
//                    L.e("size:" + bd.size());
//                    while (it.hasNext()) {
//                        String key = (String) it.next();
//                        L.e("key:" + key);
//                        L.e("value:" + bd.get(key));
//                    }
//                }

                List<ScanResult> results = mWifiAdmin.getWifiManager().getScanResults();

                mWifiAdapter.setData(results);

                if (results == null || results.isEmpty()) {
                    L.e("未扫描到网络：");
                } else {
                    L.e("扫描到网络数量: " + results.size());
                    for (int i = 0; i < results.size(); i++) {
                        L.e("网络 " + i + " :" + results.get(i).toString());
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_ap_demo);
        ButterKnife.bind(this);


        IntentFilter filter = new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(mWifiScanReceiver, filter);

        mWifiAdmin = new WifiAdmin(this);

        mHotpotToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.e("WifiAP", "onCheckedChanged:" + isChecked);
                if (isChecked) {
                    mWifiAdmin.openWifiHotpot();
                } else {
                    mWifiAdmin.close();
                }
            }
        });


//        WifiConfiguration mWifiConfig  = mWifiManager.getWifiApConfiguration();

        mScanListV.setLayoutManager(new LinearLayoutManager(this));
        mWifiAdapter = new WifiAdapter();
        mWifiAdapter.setOnItemClickListener(new OnTItemClickListener<ScanResult>() {
            @Override
            public void onItemClickOne(int position, final ScanResult data) {
                L.e("click connect:" + data.toString());
                final String pwd = mPwdV.getText().toString();
                new AlertDialog.Builder(WifiAPDemo.this)
                        .setMessage("准备连接wifi  \n名称：" + data.SSID + "\n密码：" + pwd)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (data.SSID.contains("NO8")) {
                                    mWifiAdmin.test();
                                } else {
                                    mWifiAdmin.connectNet(data, pwd);
                                }
                            }
                        }).setNegativeButton("取消", null)
                        .create()
                        .show();

            }

            @Override
            public void onItemClickTwo(int position, ScanResult data) {

            }
        });
        mScanListV.setAdapter(mWifiAdapter);

        L.e("有 ACCESS_FINE_LOCATION   权限？" + (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == 0));
        L.e("有 ACCESS_COARSE_LOCATION 权限？" + (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == 0));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mD != null) {
            mD.dispose();
            mD = null;
        }
        unregisterReceiver(mWifiScanReceiver);
    }

    @OnClick({R.id.open_hotpot, R.id.get_wifi_state, R.id.connect_hotpot})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.open_hotpot://打开随机热点
                PermissionManager.requestLocationPermission(this, "ness");
                break;
            case R.id.get_wifi_state://获取wifi状态
                mWifiAdmin.getWifiAPState();

                mWifiAdmin.startScan();//startScan之后直接.getScanResults()可能拿到上次的扫描结果，需要在广播里面接收本次结果
                break;
            case R.id.connect_hotpot://连接指定热点
                String SSID = mSsidV.getText().toString();
                String key = mPwdV.getText().toString();
                if (TextUtils.isEmpty(SSID) || TextUtils.isEmpty(key)) {
                    showToast("请输入ssid/密码！");
                    return;
                }
                mWifiAdmin.connectHotpot(SSID, key,false);
                break;
            default:
                break;
        }
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
        mWifiAdmin.openHotpotBySysApi();
    }

    @PermissionDenied(requestCode = PermissionManager.REQUEST_LOCATION)
    public void onPermissionDenied() {
        Log.e("WifiAP", " location 权限被拒绝！");
    }

}
