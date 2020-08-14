package com.cgy.mycollections.functions.threadpool;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cgy.mycollections.R;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * A placeholder fragment containing a simple view.
 */
public class ThreadPoolDemoFragment extends Fragment {

    public ThreadPoolDemoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thread_pool_single, container, false);
    }
}
