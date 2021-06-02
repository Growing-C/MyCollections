package com.cgy.mycollections.functions.ui.wheel;

import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cgy.mycollections.R;
import com.cgy.mycollections.base.AppBaseActivity;
import com.cgy.mycollections.functions.ui.wheel.rvgallery.GalleryFragment;
import com.google.android.material.tabs.TabLayout;

import appframe.utils.ToastCustom;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 该页面展示了对话框
 * 1.密码输入对话框
 */
public class WheelDemo extends AppBaseActivity {

    @BindView(R.id.tabs)
    TabLayout mTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheel_demo);
        ButterKnife.bind(this);

        mTabs.addTab(mTabs.newTab().setText("horizontal"));
        mTabs.addTab(mTabs.newTab().setText("vertical"));

        mTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                new ToastCustom(WheelDemo.this, "onTabSelected:" + tab.getPosition(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        GalleryFragment fragment = GalleryFragment.newInstance();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
    }

}
