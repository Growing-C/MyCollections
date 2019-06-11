package com.cgy.mycollections.functions.netconfig;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.cgy.mycollections.BackgroundService;
import com.cgy.mycollections.BaseActivity;
import com.cgy.mycollections.MainActivity;
import com.cgy.mycollections.R;
import com.cgy.mycollections.functions.net.NetRequestDemo;
import com.cgy.mycollections.functions.net.WifiAPDemo;
import com.cgy.mycollections.functions.net.WifiP2PDemo;
import com.cgy.mycollections.utils.L;
import com.cgy.mycollections.utils.ShellUtil;
import com.cgy.mycollections.utils.SystemUtil;

import appframe.permission.PermissionDenied;
import appframe.permission.PermissionGranted;
import appframe.permission.PermissionManager;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 配网解决方案
 * 包含权限检查等
 */
public class NetConfigDemo extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_demos);
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
        //打开背景服务
        Intent bgService = new Intent(NetConfigDemo.this, BackgroundService.class);
        startService(bgService);
    }

    @PermissionDenied(requestCode = PermissionManager.REQUEST_LOCATION)
    public void onPermissionDenied() {
        Log.e("NetConfigDemo", " location 权限被拒绝！");
    }

}
