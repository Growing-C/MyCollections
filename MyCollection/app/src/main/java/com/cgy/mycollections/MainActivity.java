package com.cgy.mycollections;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
//import androidx.appcompat.widget.DefaultItemAnimator;
//import androidx.appcompat.widget.LinearLayoutManager;
//import androidx.appcompat.widget.RecyclerView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.cgy.mycollections.functions.jetpack.JetPackDemos;
import com.cgy.mycollections.functions.ui.UiDemos;
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
import com.cgy.mycollections.functions.weixindemo.RedEnvelopeDemo;
import com.cgy.mycollections.listeners.OnItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.cgy.mycollections.FloatingService.ACTION_CHANGE_FLOATING_STATE;
import static com.cgy.mycollections.FloatingService.KEY_SHOW_FLOATING;


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

        addNotification();//添加通知栏，没啥意义

        //打开悬浮球
        Intent serviceIt = new Intent(MainActivity.this, FloatingService.class);
        startService(serviceIt);

    }

    private Demo[] demos = {
            new Demo(R.string.title_ui, R.string.info_ui, true, UiDemos.class),
            new Demo(R.string.title_jetpack, R.string.info_jetpack, JetPackDemos.class),
            new Demo(R.string.title_activity_thread_pool_demo, R.string.info_thread_pool_demo, ThreadPoolDemo.class),
            new Demo(R.string.title_activity_red_envelope_demo, R.string.info_red_envelope_demo, RedEnvelopeDemo.class),
            new Demo(R.string.title_activity_tts_demo, R.string.info_tts_demo, TTSDemo.class),
            new Demo(R.string.title_activity_data_binding_demo, R.string.info_data_binding_demo, true, DataBindingDemo.class),
            new Demo(R.string.title_activity_net_demo, R.string.info_net_demo, true, NetDemos.class),
            new Demo(R.string.title_anim_demo, R.string.info_anim_demo, AnimDemo.class),
            new Demo(R.string.title_database_demo, R.string.info_database_demo, DataBaseDemo.class),
            new Demo(R.string.title_cache_demo, R.string.info_cache_demo, CacheDemo.class),
            new Demo(R.string.title_ethereum_demo, R.string.info_ethereum_demo, EthereumDemo.class),
            new Demo(R.string.title_file, R.string.info_file, FileDemo.class),
            new Demo(R.string.title_ble, R.string.info_ble, BleDemo.class),
            new Demo(R.string.title_net_config, R.string.info_net_config, NetConfigDemo.class),
            new Demo(R.string.title_media_manager, R.string.info_media_manager, MediaManagerDemo.class),
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(MainActivity.this, FloatingService.class));
    }

    @Override
    public void onBackPressed() {
        //返回即home键
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addCategory(Intent.CATEGORY_HOME);
        startActivity(i);
    }

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
            openFloatingBtn();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //添加通知栏
    public void addNotification() {
        // 将AutoCancel设为true后，当你点击通知栏的notification后，它会自动被取消消失
        // 将Ongoing设为true 那么notification将不能滑动删除
//        Intent notificationIntent = new Intent(this,TestActivity.class); //点击该通知后要跳转的Activity
//        PendingIntent contentIntent = PendingIntent.getActivity(this,0,notificationIntent,0);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification.Builder(this).setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("自动抢红包").setContentText("保持通知栏显示可以防止进程结束").setTicker("aaa").setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setOngoing(true)
                .setAutoCancel(true)
                .setContentIntent(null)
                .build();
//        notification.flags = Notification.FLAG_NO_CLEAR | Notification.FLAG_ONGOING_EVENT;
        notificationManager.notify(0x100, notification);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (Build.VERSION.SDK_INT >= 23)
                if (!Settings.canDrawOverlays(this)) {
                    Toast.makeText(MainActivity.this, "权限授予失败，无法开启悬浮窗", Toast.LENGTH_SHORT).show();
                    getSharedPreferences("mm", MODE_PRIVATE).edit().putBoolean("no_permission", true).commit();
                } else {
                    Toast.makeText(MainActivity.this, "权限授予成功！", Toast.LENGTH_SHORT).show();
                    openFloatingBtn();
                }

        }
    }

    /**
     * 打开悬浮窗口
     */
    public void openFloatingBtn() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(MainActivity.this)) {
                SharedPreferences sp = getSharedPreferences("mm", MODE_PRIVATE);
                boolean no_permission = sp.getBoolean("no_permission", false);
                SharedPreferences.Editor editor = sp.edit();
                if (no_permission) {
                    showDialog();
                    editor.putBoolean("no_permission", false).apply();
                } else {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:" + getPackageName()));
                    startActivityForResult(intent, 1);
                }

                return;
            }
        }

        //设备sdk23以下似乎可以直接打开悬浮窗
        Intent floatingIntent = new Intent(ACTION_CHANGE_FLOATING_STATE);
        floatingIntent.putExtra(KEY_SHOW_FLOATING, true);
//            openFloat.setText("关闭悬浮控制球");
        sendBroadcast(floatingIntent);
    }

    /**
     * 打开悬浮窗口权限
     */
    private void showDialog() {
        AlertDialog mDialog = null;
        if (mDialog == null)
            mDialog = new AlertDialog.Builder(MainActivity.this).setTitle("需要权限")
                    .setMessage("没有显示悬浮窗权限，\n请点击设置进入权限管理打开悬浮窗权限\n\n" +
                            "设置>权限管理>悬浮窗>允许")
                    .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent localIntent = new Intent();
                            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                            localIntent.setData(Uri.fromParts("package", getPackageName(), null));
                            startActivity(localIntent);
                        }
                    }).setNegativeButton("取消", null).create();
        if (!mDialog.isShowing())
            mDialog.show();
    }
}
