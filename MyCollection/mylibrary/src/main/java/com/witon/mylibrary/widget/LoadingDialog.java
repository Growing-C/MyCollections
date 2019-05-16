package com.witon.mylibrary.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.witon.mylibrary.R;


/**
 * 加载框
 * Created by RB-cgy on 2017/1/12.
 */

public class LoadingDialog extends ProgressDialog {
    TextView mLoadingTextV;

    public LoadingDialog(Context context) {
        super(context, R.style.LoadingStyle);
    }

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);

        mLoadingTextV = findViewById(R.id.loading_tx);

        setCanceledOnTouchOutside(false);//点击外部是否可以取消
        setCancelable(false);//是否可以返回取消
    }

    public void show(String msg) {
        super.show();
        if (mLoadingTextV != null)
            mLoadingTextV.setText(msg);
    }
}
