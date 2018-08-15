package com.linkstec.eagle.base.permission;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;


/**
 * to avoid building a dialog everywhere your permission request is denied,we use this class to conveniently construct an AlertDialog
 * Created by RB-cgy on 2016/8/12.
 */
public class PermissionRationaleDialog {
    AlertDialog mDialog;
    Context mContext;

    public PermissionRationaleDialog(Context context, String msg, DialogInterface.OnClickListener positiveClick, DialogInterface.OnClickListener negativeClick) {
        this.mContext = context;

        if (TextUtils.isEmpty(msg)) {
            msg = "necessary permission";
        }
        mDialog = new AlertDialog.Builder(context)
                .setTitle("权限说明")//设置标题
                .setMessage(msg)
                .setPositiveButton(android.R.string.ok, positiveClick)
                .setNegativeButton(android.R.string.cancel, negativeClick)//可选
                .create();
        mDialog.setCancelable(false);

    }

    public void show() {
        mDialog.show();
    }

    public boolean isShowing() {
        return mDialog != null && mDialog.isShowing();
    }
}
