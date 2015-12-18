package com.cgy.mycollections.functions.threadpool;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.cgy.mycollections.R;

public class ThreadPoolDemo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_pool_demo);

        ThreadPoolDemoFragment threadPoolDemoFragment = new ThreadPoolDemoFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.id_content, threadPoolDemoFragment);
        ft.commit();
    }


}
