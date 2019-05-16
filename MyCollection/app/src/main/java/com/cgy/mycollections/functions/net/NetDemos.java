package com.cgy.mycollections.functions.net;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cgy.mycollections.BaseActivity;
import com.cgy.mycollections.R;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class NetDemos extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_demos);
        ButterKnife.bind(this);
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

}
