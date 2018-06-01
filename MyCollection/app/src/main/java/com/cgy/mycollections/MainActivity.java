package com.cgy.mycollections;

import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cgy.mycollections.functions.androiddesign.recyclerview.SimpleRecyclerViewDemo;
import com.cgy.mycollections.functions.framework.databinding.DataBindingDemo;
import com.cgy.mycollections.functions.net.NetRequestDemo;
import com.cgy.mycollections.functions.systemui.statusbar.StatusBarDemo;
import com.cgy.mycollections.functions.textdemo.TextDemo;
import com.cgy.mycollections.functions.threadpool.ThreadPoolDemo;
import com.cgy.mycollections.functions.tts.TTSDemo;
import com.cgy.mycollections.functions.weixindemo.RedEnvelopeDemo;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {
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

        MainItemAdapter mainItemAdapter = new MainItemAdapter(demos);
        mainItemAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                startActivity(new Intent(MainActivity.this, demos[position].c));
            }
        });
        recyclerView.setAdapter(mainItemAdapter);

    }

    private Demo[] demos = {
            new Demo(R.string.title_activity_thread_pool_demo, R.string.info_thread_pool_demo, ThreadPoolDemo.class),
            new Demo(R.string.title_activity_red_envelope_demo, R.string.info_red_envelope_demo, RedEnvelopeDemo.class),
            new Demo(R.string.title_activity_tts_demo, R.string.info_tts_demo, TTSDemo.class),
            new Demo(R.string.title_activity_simple_recycler_demo, R.string.info_simple_recycler_demo, true, SimpleRecyclerViewDemo.class),
            new Demo(R.string.title_activity_data_binding_demo, R.string.info_data_binding_demo, true, DataBindingDemo.class),
            new Demo(R.string.title_activity_status_bar_demo, R.string.info_status_bar_demo, StatusBarDemo.class),
            new Demo(R.string.title_activity_net_demo, R.string.info_net_demo, NetRequestDemo.class),
            new Demo(R.string.title_text_demo, R.string.info_text_demo, TextDemo.class),
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
