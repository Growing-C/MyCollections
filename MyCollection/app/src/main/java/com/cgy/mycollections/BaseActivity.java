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

import com.cgy.mycollections.functions.androiddesign.recyclerview.SimpleRecyclerViewDemo;
import com.cgy.mycollections.functions.anim.AnimDemo;
import com.cgy.mycollections.functions.cache.CacheDemo;
import com.cgy.mycollections.functions.dialogdemo.DialogDemo;
import com.cgy.mycollections.functions.ethereum.EthereumDemo;
import com.cgy.mycollections.functions.file.FileDemo;
import com.cgy.mycollections.functions.framework.databinding.DataBindingDemo;
import com.cgy.mycollections.functions.net.NetRequestDemo;
import com.cgy.mycollections.functions.net.WifiP2PDemo;
import com.cgy.mycollections.functions.sqlite.DataBaseDemo;
import com.cgy.mycollections.functions.systemui.statusbar.StatusBarDemo;
import com.cgy.mycollections.functions.textdemo.TextDemo;
import com.cgy.mycollections.functions.threadpool.ThreadPoolDemo;
import com.cgy.mycollections.functions.tts.TTSDemo;
import com.cgy.mycollections.functions.weixindemo.RedEnvelopeDemo;
import com.cgy.mycollections.listeners.OnItemClickListener;
import com.cgy.mycollections.utils.L;
import com.witon.mylibrary.widget.LoadingDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.cgy.mycollections.FloatingService.ACTION_CHANGE_FLOATING_STATE;
import static com.cgy.mycollections.FloatingService.KEY_SHOW_FLOATING;


public class BaseActivity extends AppCompatActivity {

    private LoadingDialog loadingDialog;

    protected void showLoadingDialog(String message) {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.show(message);
    }

    protected void dismissLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }

    protected void showToast(String message) {
        L.e(message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showDialog(String msg) {
        AlertDialog mDialog = null;
        if (mDialog == null)
            mDialog = new AlertDialog.Builder(BaseActivity.this)
                    .setMessage(msg)
                    .setNegativeButton("取消", null).create();
        if (!mDialog.isShowing())
            mDialog.show();
    }
}
