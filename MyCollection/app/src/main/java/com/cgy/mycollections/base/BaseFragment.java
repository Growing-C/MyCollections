package com.cgy.mycollections.base;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cgy.mycollections.R;
import com.witon.mylibrary.widget.LoadingDialog;


/**
 * Created by RB-cgy on 2016/9/14.
 */
public abstract class BaseFragment extends Fragment {
    private LoadingDialog mLoadingDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * fragment可见的时候操作，取代onResume，且在可见状态切换到可见的时候调用
     */
    protected void onVisible() {
        initData();
    }

    /**
     * fragment不可见的时候操作,onPause的时候,以及不可见的时候调用
     */
    protected void onHidden() {
    }

    //设置数据
    public void initData() {

    }

    @Override
    public void onResume() {//和activity的onResume绑定，Fragment初始化的时候必调用，但切换fragment的hide和visible的时候可能不会调用！
        super.onResume();
        if (isAdded() && !isHidden()) {//用isVisible此时为false，因为mView.getWindowToken为null
            onVisible();
        }
    }

    @Override
    public void onPause() {
        if (isVisible())
            onHidden();
        super.onPause();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {//默认fragment创建的时候是可见的，但是不会调用该方法！切换可见状态的时候会调用，但是调用onResume，onPause的时候却不会调用
        super.onHiddenChanged(hidden);
        if (!hidden) {
            onVisible();
        } else {
            onHidden();
        }
    }

    public void showToast(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }

    /**
     * 中间显示toast
     *
     * @param resId
     */
    public void showToast(int resId) {
        Toast toast = Toast.makeText(getActivity(), resId, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 显示正在加载对话框
     */
    protected void showLoading(String text) {
        System.out.println("loading...................................");
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(getContext(), R.style.LoadingStyle );
        }
        mLoadingDialog.show(text);
    }

    /**
     * 取消正在加载对话框
     */
    protected void hideLoading() {
        System.out.println("hideLoading--------------------------------");
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

}
