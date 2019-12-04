package com.cgy.mycollections.base;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
//import androidx.core.app.DialogFragment;
import androidx.fragment.app.DialogFragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.cgy.mycollections.R;
import com.cgy.mycollections.utils.L;
import com.witon.mylibrary.flux.rx.BaseRxAction;

/**
 * Description :
 * Author :cgy
 * Date :2019/1/21
 */
public abstract class BaseDialogFragment extends DialogFragment {
    BaseRxAction mAction;
    private ProgressDialog progressAlertDialog;

    int mDialogHeight = 0;
    int mDialogWidth = 0;

    /**
     * 设置高度，只有在show之前设置才有效
     *
     * @param height
     */
    protected void setHeight(int height) {
        this.mDialogHeight = height;
    }

    protected void setWidth(int wid) {
        this.mDialogWidth = wid;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.e("Base fragment oncreate");
        mAction = new BaseRxAction(null);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutIds(), container);
        if (getDialog().getWindow() != null)
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//背景透明

        initView(view);
        L.e("Base fragment onCreateView");
        return view;
    }

    /**
     * 设置布局id
     *
     * @return
     */
    public abstract int getLayoutIds();

    /**
     * 初始化view
     *
     * @param view
     */
    public abstract void initView(View view);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mAction.unSubscribe();
        if (isAdded())
            closeLoading();//防止出现leak
    }

    @Override
    public void onStart() {
        super.onStart();
        L.e("Base fragment onStart");
        if (getContext() == null)
            return;

        int bottom = (int) getContext().getResources().getDimension(R.dimen.dimen_10);
        int marginLR = (int) getContext().getResources().getDimension(R.dimen.dimen_10);
//        int dialogHeight = getResources().getDisplayMetrics().heightPixels * 500 / 793;
        int dialogWidth = getResources().getDisplayMetrics().widthPixels - marginLR * 2;

        //高度793/450
        //只有在此处设置宽度  才不会有限制
        Window win = getDialog().getWindow();
        win.setGravity(Gravity.CENTER);//中间弹出
        win.getDecorView().setPadding(0, 0, 0, bottom);//dialog 默认的样式@android:style/Theme.Dialog 对应的style 有pading属性，所以就能够水平占满了

        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = dialogWidth; //设置宽度

        if (mDialogHeight > 0) {
            lp.height = mDialogHeight; //设置高度
        }
        if (mDialogWidth > 0)
            lp.width = mDialogWidth; //设置宽度

        win.setAttributes(lp);
    }


    /**
     * 显示加载dialog
     */
    public void showLoading() {
        if (progressAlertDialog == null) {
            progressAlertDialog = new ProgressDialog(getActivity());
            progressAlertDialog.setCancelable(false);
        }

        if (!progressAlertDialog.isShowing()) {
            progressAlertDialog.show();
        }
    }

    /**
     * 显示加载dialog
     */
    public void showLoading(String text) {
        if (progressAlertDialog == null) {
            progressAlertDialog = new ProgressDialog(getActivity());
            progressAlertDialog.setCancelable(false);
        }
        progressAlertDialog.setMessage(text);
        if (!progressAlertDialog.isShowing()) {
            progressAlertDialog.show();
        }
    }

    /**
     * 隐藏加载dialog
     */
    public void closeLoading() {
        if (progressAlertDialog != null && progressAlertDialog.isShowing()) {
            progressAlertDialog.dismiss();
            progressAlertDialog = null;
        }
    }
}