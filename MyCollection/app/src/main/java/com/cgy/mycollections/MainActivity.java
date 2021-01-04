package com.cgy.mycollections;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Rect;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cgy.mycollections.functions.accessibility.AccessibilityDemo;
import com.cgy.mycollections.functions.anim.AnimDemo;
import com.cgy.mycollections.functions.ble.BleDemo;
import com.cgy.mycollections.functions.cache.CacheDemo;
import com.cgy.mycollections.functions.chart.ChartDemo;
import com.cgy.mycollections.functions.ethereum.EthereumDemo;
import com.cgy.mycollections.functions.file.FileDemo;
import com.cgy.mycollections.functions.file.ProtectedFilesActivity;
import com.cgy.mycollections.functions.framework.databinding.DataBindingDemo;
import com.cgy.mycollections.functions.jetpack.JetPackDemos;
import com.cgy.mycollections.functions.mediamanager.MediaManagerDemo;
import com.cgy.mycollections.functions.net.NetDemos;
import com.cgy.mycollections.functions.netconfig.NetConfigDemo;
import com.cgy.mycollections.functions.sqlite.DataBaseDemo;
import com.cgy.mycollections.functions.surfaceview.SurfaceViewDemo;
import com.cgy.mycollections.functions.threadpool.ThreadPoolDemo;
import com.cgy.mycollections.functions.tts.TTSDemo;
import com.cgy.mycollections.functions.ui.UiDemos;
import com.cgy.mycollections.functions.webview.WebViewDemos;
import com.cgy.mycollections.listeners.OnItemClickListener;
import com.cgy.mycollections.listeners.swipedrag.ItemTouchHelperAdapter;
import com.cgy.mycollections.listeners.swipedrag.SimpleItemTouchHelperCallback;
import com.cgy.mycollections.testsources.TestDemo;
import com.cgy.mycollections.utils.SystemUtil;

import java.util.List;

import appframe.utils.L;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.cgy.mycollections.FloatingService.ACTION_CHANGE_FLOATING_STATE;
import static com.cgy.mycollections.FloatingService.KEY_SHOW_FLOATING;

//import androidx.appcompat.widget.DefaultItemAnimator;
//import androidx.appcompat.widget.LinearLayoutManager;
//import androidx.appcompat.widget.RecyclerView;


public class MainActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private ItemTouchHelper mItemTouchHelper;//滑动删除 拖拽实现

    // 使用自定义的view替换自带的view
    private LayoutInflater.Factory2 factory2 = new LayoutInflater.Factory2() {
        @Override
        public View onCreateView(String name, Context context, AttributeSet attrs) {
            return onCreateView(null, name, context, attrs);
        }

        @Override
        public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
            View view = null;
            L.e("test", "factory replace view");
            if ("TextView".equals(name)) {
                view = new TextView(context, attrs);
                //可以替换全局的字体默认颜色
                //或者使用style中的
                ((TextView) view).setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
            }
            // 注意: 非activity的控件(如dialog)的,请在dialog中重新 setOnClickListener(), 此处无法区别
//            if (view != null) {
//                view.setOnClickListener(new HookViewClickUtil.OnClickListenerProxy(BaseFragmentActivity.this, getDoubleClickDuration()));
//            }

            return view;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //用于替换全局的某个view
//        LayoutInflaterCompat.setFactory2(LayoutInflater.from(this), factory2);
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
        recyclerView.setHasFixedSize(true);//可以优化recyclerView

        MainItemAdapter mainItemAdapter = new MainItemAdapter(demos, new MainItemAdapter.OnStartDragListener() {
            @Override
            public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
                mItemTouchHelper.startDrag(viewHolder);
            }
        });
        mainItemAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                startActivity(new Intent(MainActivity.this, demos[position].c));
            }
        });
        recyclerView.setAdapter(mainItemAdapter);
        initSwipeAndDrag(mainItemAdapter);

        //打开悬浮球
        Intent serviceIt = new Intent(MainActivity.this, FloatingService.class);
        startService(serviceIt);

        //下面的是展示用功能
        getAppList();
        getTaskInfo();

        L.d("ccc", "设备是几核的额？" + SystemUtil.getNumberOfCPUCores());
    }

    private void initSwipeAndDrag(ItemTouchHelperAdapter itemAdapter) {
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(itemAdapter, this);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    /**
     * task 信息 moveTaskToBack可以把当前task推入后台，如果当前app只有一个task会直接退入后台
     * isTaskRoot 判断activity是不是栈底
     * task.getTaskInfo().topActivity是栈顶 即当前显示的activity
     */
    private void getTaskInfo() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            L.e("test", isTaskRoot() + "--moveTaskToBack:" + moveTaskToBack(true));
            ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.AppTask> appTasks = manager.getAppTasks();//获取当前app的task
            if (appTasks != null) {
                L.e("test", "appTasks:" + appTasks.size());
                for (int i = 0, len = appTasks.size(); i < len; i++) {
                    ActivityManager.AppTask task = appTasks.get(i);
                    L.e("test", "numActivities:" + task.getTaskInfo().numActivities);
                    L.e("test", "topActivity:" + task.getTaskInfo().topActivity.getClassName());
                    L.e("test", "baseActivity:" + task.getTaskInfo().baseActivity.getShortClassName());
//                L.e("test", "base:" + task.getTaskInfo().origActivity.getShortClassName());
                }
            }
        }
    }

    private void getAppList() {
        PackageManager pm = getPackageManager();
        // Return a List of all packages that are installed on the device.
        List<PackageInfo> packages = pm.getInstalledPackages(0);
        for (PackageInfo packageInfo : packages) {
            // 判断系统/非系统应用
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) { // 非系统应用
//                L.e("test", "非系统应用 MainActivity.getAppList, packageInfo=" + packageInfo.packageName);
            } else {
                // 系统应用
//                L.e("test", "系统应用 MainActivity.getAppList, packageInfo=" + packageInfo.packageName);
            }
//            if (packageInfo.packageName.toUpperCase().contains("webview".toUpperCase())) {
            if (packageInfo.packageName.toUpperCase().contains("ark".toUpperCase())) {
                L.e("test", "app list:" + packageInfo.packageName);
            }
        }
        //根据id获取资源名称
        L.e("test", "resource name:" + getResources().getResourceEntryName(R.string.action_settings));

//        Intent intent = pm.getLaunchIntentForPackage(packageName);
//        startActivity(intent);
    }

    private Demo[] demos = {
            new Demo(R.string.title_surface, R.string.surface_ui, false, SurfaceViewDemo.class),
            new Demo(R.string.title_test, R.string.test_ui, true, TestDemo.class),
            new Demo(R.string.title_webview, R.string.info_webview, true, WebViewDemos.class),
            new Demo(R.string.title_ui, R.string.info_ui, true, UiDemos.class),
            new Demo(R.string.title_jetpack, R.string.info_jetpack, JetPackDemos.class),
            new Demo(R.string.title_activity_chart_demo, R.string.info_chart_demo, ChartDemo.class),
            new Demo(R.string.title_activity_thread_pool_demo, R.string.info_thread_pool_demo, ThreadPoolDemo.class),
            new Demo(R.string.title_activity_red_envelope_demo, R.string.info_red_envelope_demo, AccessibilityDemo.class),
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
        switch (id) {
            case R.id.action_settings:
                openFloatingWindow();
                return true;
            case R.id.action_notify:
                addNotification();//添加通知栏，仅展示玩玩
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    //添加通知栏
    public void addNotification() {
        // 将AutoCancel设为true后，当你点击通知栏的notification后，它会自动被取消消失
        // 将Ongoing设为true 那么notification将不能滑动删除
//        Intent notificationIntent = new Intent(this,TestActivity.class); //点击该通知后要跳转的Activity
//        PendingIntent contentIntent = PendingIntent.getActivity(this,0,notificationIntent,0);

//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        Notification notification = new Notification.Builder(this).setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle("自动抢红包").setContentText("保持通知栏显示可以防止进程结束").setTicker("aaa").setWhen(System.currentTimeMillis())
//                .setPriority(Notification.PRIORITY_MAX)
//                .setDefaults(Notification.DEFAULT_SOUND)
//                .setOngoing(true)
//                .setAutoCancel(true)
//                .setContentIntent(null)
//                .build();
////        notification.flags = Notification.FLAG_NO_CLEAR | Notification.FLAG_ONGOING_EVENT;
//        notificationManager.notify(0x100, notification);

        //-----------------------------------------------------------------------------
//        Intent intent = new Intent(this, MediaManagerDemo.class);
        Intent intent = new Intent(this, ProtectedFilesActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(MyApplication.getInstance()
                , 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationManager notificationManager =
                (NotificationManager) MyApplication.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
        //新版本需要设置channel
        NotificationChannel channel = null;
        NotificationCompat.Builder notificationBuilder = null;
        String channelId = "test_channel_id";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel(channelId, "快速启动", NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true); //是否在桌面icon右上角展示小红点
            channel.setLightColor(Color.RED); //小红点颜色
            channel.setShowBadge(true); //是否在久按桌面图标时显示此渠道的通知

            notificationManager.createNotificationChannel(channel);
            notificationBuilder = new NotificationCompat.Builder(this, channelId);
        } else {
            notificationBuilder = new NotificationCompat.Builder(this, channelId);
        }
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notificationBuilder
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("快速启动")
                .setContentText("打开媒体管理")
                .setTicker("aaa")
                .setPriority(Notification.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setOngoing(true)
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        Ringtone r = RingtoneManager.getRingtone(MyApplication.getInstance(), defaultSoundUri);
        r.play();

//        notificationBuilder.build().flags = Notification.FLAG_NO_CLEAR | Notification.FLAG_ONGOING_EVENT;
        notificationManager.notify(0x100 /* ID of notification */, notificationBuilder.build());
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
                    openFloatingWindow();
                }

        }
    }

    /**
     * 打开悬浮窗口
     */
    public void openFloatingWindow() {
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
