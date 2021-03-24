package com.cgy.mycollections.functions.aidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;

import com.cgy.mycollections.R;
import com.cgy.mycollections.base.AppBaseActivity;

/**
 * author: chengy
 * created on: 2021-3-22 15:09
 * description:
 */
public class AidlDemo extends AppBaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_aidl_demo);

        bindService(new Intent(""), new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, BIND_ADJUST_WITH_ACTIVITY);
    }

}
