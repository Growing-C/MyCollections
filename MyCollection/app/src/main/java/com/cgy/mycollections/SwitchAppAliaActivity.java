package com.cgy.mycollections;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;


import com.cgy.mycollections.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 切换app logo 和名称,通过桌面长按applogo 选中shortcut中的app样式进入
 * https://juejin.im/post/5c36f2226fb9a049b7809170
 * 可以根据alias切换app到不同模式
 * 目前仅切换了app名和图标
 * TODO：  待开发不同模式内容，如好孩子模式等，
 */
public class SwitchAppAliaActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_app_alia);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.demos, R.id.settings, R.id.wechat})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.demos:
                switchAlias(1);
                break;
            case R.id.settings:
                switchAlias(2);
                break;
            case R.id.wechat:
                switchAlias(3);
                break;
            default:
                break;
        }
    }

    private void switchAlias(int aliaIndex) {
        int enableState = PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
        int disableState = PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
        PackageManager packageManager = getPackageManager();
        packageManager.setComponentEnabledSetting(new ComponentName(this, getPackageName() +
                ".MainActivity"), aliaIndex == 1 ? enableState : disableState, PackageManager
                .DONT_KILL_APP);
        packageManager.setComponentEnabledSetting(new ComponentName(this, getPackageName() +
                ".MainActivityEntry1"), aliaIndex == 2 ? enableState : disableState, PackageManager
                .DONT_KILL_APP);
        packageManager.setComponentEnabledSetting(new ComponentName(this, getPackageName() +
                ".MainActivityEntry2"), aliaIndex == 3 ? enableState : disableState, PackageManager
                .DONT_KILL_APP);

    }
}
