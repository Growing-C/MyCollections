package com.cgy.mycollections.base;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.witon.mylibrary.base.BaseActivity;

import appframe.utils.L;


public abstract class AppBaseActivity extends BaseActivity {
    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        L.i("cccc startActivityForResult ");
    }

    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        super.overridePendingTransition(enterAnim, exitAnim);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        L.i("ccccc finish ");
    }
}
