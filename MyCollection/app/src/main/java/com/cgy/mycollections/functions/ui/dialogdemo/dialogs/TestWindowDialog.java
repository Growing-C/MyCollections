package com.cgy.mycollections.functions.ui.dialogdemo.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cgy.mycollections.R;

import butterknife.ButterKnife;

/**
 * 作者: 陈高阳
 * 创建日期: 2020/9/4 10:52
 * 修改日期: 2020/9/4 10:52
 * 类说明： 测试window层级相关
 */
public class TestWindowDialog extends Dialog {
    public TestWindowDialog(@NonNull Context context) {
        super(context);
    }

    public TestWindowDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected TestWindowDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_test_window);
        ButterKnife.bind(this);

    }

}
