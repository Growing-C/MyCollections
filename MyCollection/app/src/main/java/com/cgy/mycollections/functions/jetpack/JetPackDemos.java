package com.cgy.mycollections.functions.jetpack;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.cgy.mycollections.R;
import com.cgy.mycollections.base.AppBaseActivity;
import com.cgy.mycollections.functions.jetpack.navigation.NavigationFragment;
import com.cgy.mycollections.functions.jetpack.room.RoomFragment;
import com.cgy.mycollections.functions.jetpack.workmanager.WorkManagerFragment;
import com.cgy.mycollections.functions.mediamanager.mediaimageui.SectionsPagerAdapter;
import com.cgy.mycollections.utils.ReflectUtils;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;


public class JetPackDemos extends AppBaseActivity {

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
//        sectionsPagerAdapter.addFragment("CameraX", CameraXFragment.newInstance());
        Fragment cameraXFragment = ReflectUtils.getFragmentByName("com.cgy.mycamera.camerax.CameraXFragment");
        if (cameraXFragment != null) {
            sectionsPagerAdapter.addFragment("CameraX", ReflectUtils.getFragmentByName("com.cgy.mycamera.camerax.CameraXFragment"));
        }
        mTabPager.setAdapter(sectionsPagerAdapter);
        mTabs.setupWithViewPager(mTabPager);


//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//
//        Fragment tabFrag = mFragments.get(selectedTabIndex);
//        boolean tabFragIsAdded = tabFrag.isAdded();
//
//        if (!tabFragIsAdded) {
//            transaction.add(R.id.fragment_container, tabFrag);
//        }
//        transaction.show(tabFrag);
//        int commitResult = transaction.commitAllowingStateLoss();
    }


}
