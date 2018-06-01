package com.witon.mylibrary.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import com.witon.mylibrary.R;


/**
 * 加载框
 * Created by RB-cgy on 2017/1/12.
 */

public class LoadingDialog extends ProgressDialog {

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);

        setCanceledOnTouchOutside(false);//点击外部是否可以取消
        setCancelable(false);//是否可以返回取消
    }
}
