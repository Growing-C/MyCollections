package com.cgy.mycollections.functions.ui;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.DefaultItemAnimator;
//import androidx.appcompat.widget.LinearLayoutManager;
//import androidx.appcompat.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.View;

import com.cgy.mycollections.Demo;
import com.cgy.mycollections.MainItemAdapter;
import com.cgy.mycollections.R;
import com.cgy.mycollections.functions.ui.androiddesign.recyclerview.SimpleRecyclerViewDemo;
import com.cgy.mycollections.functions.ui.dialogdemo.DialogAndWidgetsDemo;
import com.cgy.mycollections.functions.ui.scale.ScaleViewDemo;
import com.cgy.mycollections.functions.ui.systemui.statusbar.StatusBarDemo;
import com.cgy.mycollections.functions.ui.textdemo.TextDemo;
import com.cgy.mycollections.functions.ui.touch.TouchDemo;
import com.cgy.mycollections.functions.ui.wheel.WheelDemo;
import com.cgy.mycollections.listeners.OnItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;


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

        MainItemAdapter mainItemAdapter = new MainItemAdapter(uiDemos, null);
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
            new Demo(R.string.title_wheel, R.string.info_wheel, WheelDemo.class),
            new Demo(R.string.title_activity_status_bar_demo, R.string.info_status_bar_demo, StatusBarDemo.class),
            new Demo(R.string.title_text_demo, R.string.info_text_demo, TextDemo.class),
            new Demo(R.string.title_dialog, R.string.info_dialog, DialogAndWidgetsDemo.class),
            new Demo(R.string.title_scale_view, R.string.info_scale_view, ScaleViewDemo.class),
            new Demo(R.string.title_touch, R.string.info_touch, TouchDemo.class),
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
