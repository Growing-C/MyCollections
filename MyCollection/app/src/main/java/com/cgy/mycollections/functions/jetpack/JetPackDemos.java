package com.cgy.mycollections.functions.jetpack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cgy.mycollections.R;
import com.cgy.mycollections.base.BaseActivity;
import com.cgy.mycollections.functions.net.NetRequestDemo;
import com.cgy.mycollections.functions.net.WifiAPDemo;
import com.cgy.mycollections.functions.net.WifiP2PDemo;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class JetPackDemos extends BaseActivity {

    @BindView(R.id.tabs)
    TabLayout mTabs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jet_pack_demos);
        ButterKnife.bind(this);
//        mTabs.setTabMode();

    }

//    @OnClick({R.id.work_manager, R.id.room, R.id.navigation, R.id.camerax})
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.work_manager:
//                startActivity(new Intent(this, NetRequestDemo.class));
//                break;
//            case R.id.room:
//                startActivity(new Intent(this, WifiP2PDemo.class));
//                break;
//            case R.id.navigation:
//                startActivity(new Intent(this, WifiAPDemo.class));
//                break;
//            case R.id.camerax:
//                startActivity(new Intent(this, WifiAPDemo.class));
//                break;
//            default:
//                break;
//        }
    }

}
