package com.cgy.mycollections.functions.jetpack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.cgy.mycollections.R;
import com.cgy.mycollections.base.BaseActivity;
import com.cgy.mycollections.functions.jetpack.camerax.CameraXFragment;
import com.cgy.mycollections.functions.jetpack.navigation.NavigationFragment;
import com.cgy.mycollections.functions.jetpack.room.RoomFragment;
import com.cgy.mycollections.functions.jetpack.workmanager.WorkManagerFragment;
import com.cgy.mycollections.functions.mediamanager.mediaimageui.SectionsPagerAdapter;
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
    @BindView(R.id.view_pager)
    ViewPager mTabPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jet_pack_demos);
        ButterKnife.bind(this);

//        <!--        android:text="WorkManager - 满足您的后台调度需求。"-->
//        <!--        android:text="Room - 实现数据存储持久性"-->
//        <!--        android:text="Navigation - 管理应用导航流程。"-->
//        <!--        android:text="CameraX - 满足相机应用需求。"-->

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        sectionsPagerAdapter.addFragment("WorkManager", WorkManagerFragment.newInstance());
        sectionsPagerAdapter.addFragment("Room", RoomFragment.newInstance());
        sectionsPagerAdapter.addFragment("Navigation", NavigationFragment.newInstance());
        sectionsPagerAdapter.addFragment("CameraX", CameraXFragment.newInstance());
        mTabPager.setAdapter(sectionsPagerAdapter);
        mTabs.setupWithViewPager(mTabPager);
    }


}
