package com.cgy.mycollections.functions.netconfig;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
//import android.support.design.widget.TextInputEditText;
import com.google.android.material.textfield.TextInputEditText;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ToggleButton;

import com.cgy.mycollections.base.AppBaseActivity;
import com.cgy.mycollections.R;
import appframe.utils.L;
import com.cgy.mycollections.utils.SystemUtil;

import appframe.permission.PermissionDenied;
import appframe.permission.PermissionDialog;
import appframe.permission.PermissionGranted;
import appframe.permission.PermissionManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 配网解决方案
 * 包含权限检查等
 */
public class NetConfigDemo extends AppBaseActivity {
    @BindView(R.id.wifi_ssid)
    TextInputEditText mSsidV;
    @BindView(R.id.wifi_password)
    TextInputEditText mPwdV;
    @BindView(R.id.ssid_hidden)
    ToggleButton mSsidHiddenStateV;

    NetConfigOperator mOperator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_config_demo);
        ButterKnife.bind(this);

        try {
            L.e("有 ACCESS_FINE_LOCATION   权限？" + (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED));

            L.e("NetConfigDemo", "start net Config demo!!");
            L.e("NetConfigDemo", "SystemUtil.isRoot()：" + SystemUtil.isRoot());
//            L.e("NetConfigDemo", "ShellUtil.grantPermission 成功了吗：" + ShellUtil.grantPermission(this, Manifest.permission.ACCESS_FINE_LOCATION));
//            L.e("NetConfigDemo", "ShellUtil.checkRootPermission：" + ShellUtil.checkRootPermission());
        } catch (Exception e) {
            e.printStackTrace();
        }
        PermissionManager.requestLocationPermission(this, "ness");

        mSsidV.setText("NO8");
        mPwdV.setText("linkage@12345");

        mOperator = new NetConfigOperator(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mOperator.close();
    }

    @OnClick({R.id.config_net, R.id.open_config_service})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.config_net:
                //开启蓝牙服务
                PermissionManager.requestBluetoothPermission(this, "打开蓝牙权限啦！");
                configNet();
                break;
            case R.id.open_config_service:
                //打开背景服务
                Intent bgService = new Intent(NetConfigDemo.this, BackgroundService.class);
                startService(bgService);
                break;
            default:
                break;
        }
    }

    public void configNet() {
        String ssid = mSsidV.getText().toString();
        String pwd = mPwdV.getText().toString();
        boolean ssidHidden = mSsidHiddenStateV.isChecked();
        L.e("准备配网 ssid:" + ssid + "   pwd:" + pwd + "  ssid隐藏？" + ssidHidden);

        if (TextUtils.isEmpty(ssid) || TextUtils.isEmpty(pwd)) {
            showToast("ssid/pwd为空！");
            return;
        }

        mOperator.startConfig(ssid, pwd, ssidHidden, new IConfigCallback() {
            @Override
            public void onError(int errorCode, String errorMsg) {
                L.e("IConfigCallback", errorCode + ">onError>" + errorMsg);
            }

            @Override
            public void onProcessChange(int process, String msg) {

                L.e("IConfigCallback", process + ">onProcessChange>" + msg);
            }

            @Override
            public void onSuccess() {

                L.e("IConfigCallback", ">onSuccess>");
            }
        });
    }


    @PermissionGranted(requestCode = PermissionManager.REQUEST_BLUETOOTH)
    public void bluetoothPermissionGranted() {
        L.e("test", "bluetoothPermissionGranted");
        configNet();
    }

    @PermissionDenied(requestCode = PermissionManager.REQUEST_BLUETOOTH)
    public void bluetoothPermissionDenied() {
        L.e("bluetoothPermissionDenied");

        PermissionDialog mPermissionDialog = new PermissionDialog(this, "打开蓝牙权限嘛");
        mPermissionDialog.show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @PermissionGranted(requestCode = PermissionManager.REQUEST_LOCATION)
    public void onLocationPermissionGranted() {
        Log.e("NetConfigDemo", "获取到了location 权限");
    }

    @PermissionDenied(requestCode = PermissionManager.REQUEST_LOCATION)
    public void onPermissionDenied() {
        Log.e("NetConfigDemo", " location 权限被拒绝！");
    }

}
