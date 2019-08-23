package com.cgy.mycollections.testsources.arknights.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.cgy.mycollections.R;
import com.cgy.mycollections.utils.L;

import java.lang.reflect.Method;

import appframe.utils.DisplayHelperUtils;

/**
 * Description :
 * Author :cgy
 * Date :2019/8/19
 */
public class PublicAdvertisePop implements View.OnClickListener {

    private PopupWindow mPop;
    Context mContext;
    WebView mWebView;

    public final String url = "http://wiki.joyme.com/arknights/%E5%85%AC%E5%BC%80%E6%8B%9B%E5%8B%9F%E5%B7%A5%E5%85%B7";

    public PublicAdvertisePop(Context context) {
        mContext = context;
    }

    private View getSettingTableView() {
        View settingTable = LayoutInflater.from(mContext).inflate(R.layout.pop_arknights, null);
        settingTable.findViewById(R.id.back).setOnClickListener(this);
        settingTable.findViewById(R.id.refresh).setOnClickListener(this);
        TextView titleV = settingTable.findViewById(R.id.title);

        titleV.setText("http://http://wiki.joyme.com/arknights/公开招募工具");
        titleV.setOnClickListener(this);
        mWebView = settingTable.findViewById(R.id.webview);
        initWebView(mWebView, url);
//        mWebView.setWebChromeClient(new WebChromeClient());
//        mWebView.setWebViewClient(new WebViewClient());
//        mWebView.getSettings().setJavaScriptEnabled(true);
//
//        mWebView.loadUrl(url);

        return settingTable;
    }

    private void initWebView(WebView webView, String url) {
        //解决webview加载的网页上的按钮点击失效  以及有些图标显示不出来的问题
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAppCacheEnabled(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.loadUrl(url);

        // 将webView的横向竖向的scrollBar都禁用掉，将不再与ScrollView冲突，解决了大面积空白的问题。
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webView.setVerticalScrollBarEnabled(false);
        webView.setVerticalScrollbarOverlay(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setHorizontalScrollbarOverlay(false);

        //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    public void showPop(final View touchView) {
        if (mPop == null) {
            int screenHeight = mContext.getResources().getDisplayMetrics().heightPixels;
            int screenWidth = mContext.getResources().getDisplayMetrics().widthPixels;

            L.e("screenHeight:" + screenHeight + "    screenWidth:" + screenWidth);
            int popWidth = screenWidth;
            int popHeight = screenHeight;
            if (mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                //竖屏
                L.e("竖屏");
                popHeight = screenHeight / 2;
            } else {
                //横屏
                L.e("横屏");
                popWidth = screenWidth / 2;
            }
            mPop = new PopupWindow(getSettingTableView(), popWidth, popHeight);

            mPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    touchView.setVisibility(View.VISIBLE);
                }
            });

            mPop.setBackgroundDrawable(null);//null 原来是new BitmapDrawable
            mPop.setTouchable(true);
            mPop.setFocusable(true);
            mPop.setOutsideTouchable(true);
//            mPop.setAnimationStyle(R.style.AnimationPreview);
            mPop.setFocusable(true);

            Class c = mPop.getClass();

            //popWindow 和悬浮窗口类似，但是其有固定的layoutType，固定的Type不能显示在其他view之上，所以我们反射来重新设置
            try {
                Method method = c.getMethod("setWindowLayoutType", int.class);
                method.setAccessible(true);

                int windowType;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//6.0
                    windowType = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
                } else {
                    windowType = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
                }
                method.invoke(mPop, windowType);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        mPop.showAtLocation(touchView, Gravity.NO_GRAVITY, 0, 0);
        touchView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title:
//                mWebView.loadUrl(url);
                break;
            case R.id.back:
                if (mWebView.canGoBack())
                    mWebView.goBack();
                break;
            case R.id.refresh:
                mWebView.loadUrl(url);
                break;
            default:
                break;
        }
    }
}
