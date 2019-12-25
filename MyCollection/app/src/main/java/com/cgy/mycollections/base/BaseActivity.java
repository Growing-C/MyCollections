package com.cgy.mycollections.base;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.widget.Toast;

import appframe.utils.L;
import com.cgy.mycollections.utils.LanguageUtils;
import com.witon.mylibrary.widget.LoadingDialog;


public abstract class BaseActivity extends AppCompatActivity {

    private LoadingDialog loadingDialog;

    @Override
    protected void attachBaseContext(Context newBase) {
//        L.e("Language", "BaseActivity attachBaseContext");
        super.attachBaseContext(LanguageUtils.getAppLanguageContext(newBase));//使用本地语言
    }

    //There is an issue in new app compat libraries related to night mode that is
    // causing to override the configuration on android 21 to 25.
    // This can be fixed by applying your configuration when this public function is called:
//    @Override
//    public void applyOverrideConfiguration(Configuration overrideConfiguration) {
//        if (Build.VERSION.SDK_INT >= 21 && Build.VERSION.SDK_INT <= 25) {
//            //Use you logic to update overrideConfiguration locale
//            Locale locale =  Locale.ENGLISH;//your own implementation here
//            overrideConfiguration.setLocale(locale);
//        }
//        super.applyOverrideConfiguration(overrideConfiguration);
//    }

    //androidx appcompat 1.1.0有bug ，在AppCompatDelegateImpl 中会完全覆盖configuration，导致切换语言无效(后续版本应该已经修复)
    //（小米9无此bug，三星 7.1系统有这个bug，有人说是安卓版本21到25之间有这个bug）
    // ,还使用1.1.0的话需要 ,需要手动重新设置 applyOverrideConfiguration
    // https://stackoverflow.com/questions/55265834/change-locale-not-work-after-migrate-to-androidx
    @Override
    public void applyOverrideConfiguration(Configuration overrideConfiguration) {
        if (overrideConfiguration != null) {
            int uiMode = overrideConfiguration.uiMode;
            overrideConfiguration.setTo(getBaseContext().getResources().getConfiguration());
            overrideConfiguration.uiMode = uiMode;
        }
        super.applyOverrideConfiguration(overrideConfiguration);
    }

    protected void showLoadingDialog(String message) {
        showLoadingDialog(message, null);
    }

    protected void showLoadingDialog(String message, DialogInterface.OnCancelListener listener) {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
            loadingDialog.setCancelable(true);
        }
        if (listener != null)
            loadingDialog.setOnCancelListener(listener);

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
