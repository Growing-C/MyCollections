package com.cgy.mycollections.base;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.cgy.mycollections.utils.L;
import com.witon.mylibrary.widget.LoadingDialog;


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
        mDialog = new AlertDialog.Builder(BaseActivity.this)
                .setMessage(msg)
                .setNegativeButton("取消", null).create();
        if (!mDialog.isShowing())
            mDialog.show();
    }
}
