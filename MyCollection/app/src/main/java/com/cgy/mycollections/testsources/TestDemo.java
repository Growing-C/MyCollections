package com.cgy.mycollections.testsources;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cgy.mycollections.Demo;
import com.cgy.mycollections.MainItemAdapter;
import com.cgy.mycollections.R;
import com.cgy.mycollections.functions.ui.androiddesign.recyclerview.SimpleRecyclerViewDemo;
import com.cgy.mycollections.functions.ui.dialogdemo.DialogDemo;
import com.cgy.mycollections.functions.ui.scale.ScaleViewDemo;
import com.cgy.mycollections.functions.ui.systemui.statusbar.StatusBarDemo;
import com.cgy.mycollections.functions.ui.textdemo.TextDemo;
import com.cgy.mycollections.functions.ui.wheel.WheelDemo;
import com.cgy.mycollections.listeners.OnItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

//import androidx.appcompat.widget.DefaultItemAnimator;
//import androidx.appcompat.widget.LinearLayoutManager;
//import androidx.appcompat.widget.RecyclerView;


public class TestDemo extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);//butterKnife和dataBinding并不冲突

        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
