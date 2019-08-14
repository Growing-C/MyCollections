package com.cgy.mycollections.functions.ui;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.cgy.mycollections.Demo;
import com.cgy.mycollections.FloatingService;
import com.cgy.mycollections.MainItemAdapter;
import com.cgy.mycollections.R;
import com.cgy.mycollections.functions.anim.AnimDemo;
import com.cgy.mycollections.functions.ble.BleDemo;
import com.cgy.mycollections.functions.cache.CacheDemo;
import com.cgy.mycollections.functions.ethereum.EthereumDemo;
import com.cgy.mycollections.functions.file.FileDemo;
import com.cgy.mycollections.functions.framework.databinding.DataBindingDemo;
import com.cgy.mycollections.functions.mediamanager.MediaManagerDemo;
import com.cgy.mycollections.functions.net.NetDemos;
import com.cgy.mycollections.functions.netconfig.NetConfigDemo;
import com.cgy.mycollections.functions.sqlite.DataBaseDemo;
import com.cgy.mycollections.functions.threadpool.ThreadPoolDemo;
import com.cgy.mycollections.functions.tts.TTSDemo;
import com.cgy.mycollections.functions.ui.androiddesign.recyclerview.SimpleRecyclerViewDemo;
import com.cgy.mycollections.functions.ui.dialogdemo.DialogDemo;
import com.cgy.mycollections.functions.ui.systemui.statusbar.StatusBarDemo;
import com.cgy.mycollections.functions.ui.textdemo.TextDemo;
import com.cgy.mycollections.functions.weixindemo.RedEnvelopeDemo;
import com.cgy.mycollections.listeners.OnItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.cgy.mycollections.FloatingService.ACTION_CHANGE_FLOATING_STATE;
import static com.cgy.mycollections.FloatingService.KEY_SHOW_FLOATING;


public class UiDemos extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);//butterKnife和dataBinding并不冲突

        setSupportActionBar(toolbar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(10, 10, 10, 15);
            }
        });

        MainItemAdapter mainItemAdapter = new MainItemAdapter(uiDemos);
        mainItemAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                startActivity(new Intent(UiDemos.this, uiDemos[position].c));
            }
        });
        recyclerView.setAdapter(mainItemAdapter);
    }

    private Demo[] uiDemos = {
            new Demo(R.string.title_activity_simple_recycler_demo, R.string.info_simple_recycler_demo, true, SimpleRecyclerViewDemo.class),
            new Demo(R.string.title_activity_status_bar_demo, R.string.info_status_bar_demo, StatusBarDemo.class),
            new Demo(R.string.title_text_demo, R.string.info_text_demo, TextDemo.class),
            new Demo(R.string.title_dialog, R.string.info_dialog, DialogDemo.class),
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
