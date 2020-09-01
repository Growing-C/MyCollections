package com.cgy.mycollections.functions.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.cgy.mycollections.base.BaseActivity;
import com.cgy.mycollections.R;

import appframe.network.retrofit.network.NetworkMonitor;
import appframe.utils.L;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class NetDemos extends BaseActivity {
    NetWorkChangeBroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_demos);
        ButterKnife.bind(this);

        receiver = new NetWorkChangeBroadcastReceiver(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @OnClick({R.id.net_request, R.id.p2p, R.id.wifi_ap})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.net_request:
                startActivity(new Intent(this, NetRequestDemo.class));
                break;
            case R.id.p2p:
                startActivity(new Intent(this, WifiP2PDemo.class));
                break;
            case R.id.wifi_ap:
                startActivity(new Intent(this, WifiAPDemo.class));
                break;
            default:
                break;
        }
    }

    static class NetWorkChangeBroadcastReceiver extends BroadcastReceiver {
        NetworkMonitor mMonitor;

        NetWorkChangeBroadcastReceiver(@NonNull Context context) {
            mMonitor = new NetworkMonitor(context);
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean connected = mMonitor.isConnected();
            L.e("netState", "network state changed:" + connected + " intent:" + intent);
        }
    }
}
