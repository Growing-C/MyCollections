package com.witon.mylibrary.widget;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.witon.mylibrary.R;

import appframe.utils.L;
import appframe.utils.SystemUiUtils;


/**
 * toolbar
 * Created by RB-cgy on 2017/1/9.
 */
public class HeaderBar {
    private final Toolbar mToolbar;
    private final AppCompatActivity mActivity;
    private View rootView;//根view

    public HeaderBar(AppCompatActivity activity) {//activity使用,必须保证activity布局中有include toolbar
        this.mActivity = activity;
        mToolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        mToolbar.setTitle("");//默认的title靠左，设为空
        setDefaultBackIcon();
        mActivity.setSupportActionBar(mToolbar);//设置activity的actionBar
        setHeadImg("", "");
    }

    public HeaderBar(AppCompatActivity activity, View rootView) {//fragment使用,必须保证rootView布局中有toolbar
        this.mActivity = activity;
        this.rootView = rootView;
        this.mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        mToolbar.setTitle("");//默认的title靠左，设为空
        mActivity.setSupportActionBar(mToolbar);
        setHeadImg("", "");
    }

    /**
     * 设置 沉浸态的情况下 不要遮盖状态栏
     *
     * @deprecated 不灵，不如外面包一层layout 然后外部设置 fitSystemWindows。
     */
    public void setFitSystemWindows(final Window window) {
        mToolbar.setFitsSystemWindows(true);
        mToolbar.postDelayed(new Runnable() {
            @Override
            public void run() {
                int statusBarHeight = SystemUiUtils.getStatusBarHeight(window);
                L.i("statusBarHeight:" + statusBarHeight);
//                mToolbar.getLayoutParams();
            }
        }, 0);
    }

    /**
     * 设置背景
     *
     * @param ids
     */
    public void setBackGround(int ids) {
        mToolbar.setBackgroundResource(ids);
    }

    /**
     * 自动处理返回键
     */
    public void setDefaultBackIcon() {
        setDefaultBackIcon(-1, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mActivity != null)
                    mActivity.finish();
            }
        });
    }


    /**
     * 左边关闭按钮
     */
    public void setDefaultBackText() {
        TextView textView = (TextView) findViewById(R.id.back_text);

        textView.setVisibility(View.VISIBLE);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mActivity != null)
                    mActivity.finish();
            }
        });
    }

    /**
     * 设置返回的按钮
     *
     * @param imgIds
     */
    public void setDefaultBackIcon(int imgIds) {
        setDefaultBackIcon(imgIds, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mActivity != null)
                    mActivity.finish();
            }
        });
    }

    /**
     * 设置返回键
     */
    public void setDefaultBackIcon(int imgIds, View.OnClickListener listener) {
        ImageView imageView = (ImageView) findViewById(R.id.back_img);
        LinearLayout back = (LinearLayout) findViewById(R.id.ll_back);
        if (imgIds != -1) {//-1为使用默认的
            imageView.setImageResource(imgIds);
        }

        imageView.setVisibility(View.VISIBLE);
        back.setOnClickListener(listener);
//        mToolbar.setNavigationIcon(R.drawable.selector_back_btn);//toolbar自定义高度导致NavigationIcon不居中，更换为自定义icon
//        mToolbar.setNavigationOnClickListener(listener);
    }

    /**
     * 隐藏返回键
     */
    public void setBackImgInVisible() {
        LinearLayout back = (LinearLayout) findViewById(R.id.ll_back);
        back.setVisibility(View.INVISIBLE);
    }

    /**
     * 设置标题
     *
     * @param ids
     */
    public void setTitle(int ids) {
        TextView textView = (TextView) findViewById(R.id.toolbar_title);

        textView.setText(ids);
    }

    /**
     * 设置标题和标题颜色
     *
     * @param ids
     * @param colorIds
     */
    public void setTitle(int ids, int colorIds) {
        TextView textView = (TextView) findViewById(R.id.toolbar_title);
        textView.setTextColor(ContextCompat.getColor(mActivity, colorIds));
        textView.setText(ids);
    }

    /**
     * 设置标题
     */
    public void setTitle(String appellation) {
        TextView textView = (TextView) findViewById(R.id.toolbar_title);

        textView.setText(appellation);
    }

    /**
     * 设置标题
     */
    public void setSubTitle(String subTitle) {
        TextView textView = (TextView) findViewById(R.id.toolbar_sub_title);

        textView.setText(subTitle);
    }

    /**
     * 设置右边的文字
     *
     * @param ids
     */
    public void setRightText(int ids, View.OnClickListener listener) {
        TextView textView = (TextView) findViewById(R.id.toolbar_right_tx);

        textView.setText(ids);
        if (listener != null)
            textView.setOnClickListener(listener);
    }

    /**
     * 设置右边的文字
     */
    public void setRightText(String str, View.OnClickListener listener) {
        TextView textView = (TextView) findViewById(R.id.toolbar_right_tx);

        if (TextUtils.isEmpty(str)) {
            textView.setVisibility(View.GONE);
        }
        textView.setText(str);
        if (listener != null)
            textView.setOnClickListener(listener);
    }

    public View getRightImageView() {
        return findViewById(R.id.toolbar_right_image);
    }

    /**
     * 添加右边的图片
     *
     * @param ids
     * @param listener
     */
    public void setRightImage(int ids, View.OnClickListener listener) {
        ImageView imageView = (ImageView) findViewById(R.id.toolbar_right_image);

        imageView.setImageResource(ids);
        imageView.setOnClickListener(listener);
    }

    /**
     * 显示头像
     *
     * @param drawable
     */
    public void setHeadImg(Drawable drawable, String name) {
        if (drawable == null)
            return;
        ImageView toolbar_headimg = (ImageView) findViewById(R.id.toolbar_headimg);

        toolbar_headimg.setVisibility(View.VISIBLE);
        toolbar_headimg.setImageDrawable(drawable);

        TextView tv = (TextView) findViewById(R.id.head_name);
        tv.setVisibility(View.VISIBLE);
        tv.setText(name);
    }

    /**
     * 显示头像
     *
     * @param url 图像url
     */
    public void setHeadImg(String url, String name) {
        if (TextUtils.isEmpty(url))//headimg默认是gone
            return;
        ImageView toolbar_headimg = (ImageView) findViewById(R.id.toolbar_headimg);
        TextView tv = (TextView) findViewById(R.id.head_name);
        tv.setVisibility(View.VISIBLE);
        tv.setText(name);

        toolbar_headimg.setVisibility(View.VISIBLE);
//        Glide.with(mActivity.getBaseContext())
//                .load(url)
//                .placeholder(R.drawable.ic_launcher)
//                .centerCrop()
//                .into(toolbar_headimg);
    }

    /**
     * 查找view
     *
     * @param ids
     * @return
     */
    private View findViewById(int ids) {
        View view = mActivity.findViewById(ids);
        if (view == null)
            view = rootView.findViewById(ids);
        return view;
    }
}
