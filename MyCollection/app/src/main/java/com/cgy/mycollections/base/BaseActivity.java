package com.cgy.mycollections.base;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.res.Configuration;
import android.view.View;
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

    /**
     * toolbar 显示返回按钮
     * 设置使用系统自带的返回按钮样式
     * （如果要使用自带的样式 就在xml中设置 app:navigationIcon="@mipmap/title_bar_back"
     * 或者代码中设置 mToolbar.setNavigationIcon();）
     *
     * @param toolbar
     */
    protected void setUpActionBarBack(Toolbar toolbar) {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        Toolbar上的左上角的返回箭头的键值为Android.R.id.home 也可以在onOptionsItemSelected 中监听
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//返回
            }
        });
    }

    //    /**
//     * 动态修改actionbar返回键
//     */
//    protected void setToolbarCustomTheme() {
//        Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
//        if(upArrow != null) {
//            upArrow.setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP);
//            if(getSupportActionBar() != null) {
//                getSupportActionBar().setHomeAsUpIndicator(upArrow);
//            }
//        }
//    }

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
