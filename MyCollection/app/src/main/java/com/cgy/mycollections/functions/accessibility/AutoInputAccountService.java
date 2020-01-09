package com.cgy.mycollections.functions.accessibility;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import java.util.List;

import appframe.utils.L;

public class AutoInputAccountService extends AccessibilityService {
    public static final String TAG = "cgy";

    private final String loginNameViewId = "com.linkstec.blockchains.bclwallet:id/etAccount";
    private final String pwdViewId = "com.linkstec.blockchains.bclwallet:id/etPwd";

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();

        L.d(TAG, "事件---->" + event);
        Log.d(TAG, "事件---->" + event);

        switch (eventType) {
            //第一步：监听通知栏消息
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                break;
            //第二步：监听是否进入微信红包消息界面
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                AccessibilityNodeInfo rootNode1 = getRootInActiveWindow();
                recycleForPwd(rootNode1);
                break;
            case AccessibilityEvent.TYPE_VIEW_FOCUSED:
                String focusClassName = event.getClassName().toString();
                Log.d(TAG, "事件--focusClassName-->" + focusClassName);
                if (!TextUtils.isEmpty(focusClassName) && focusClassName.contains("EditText")) {
                    AccessibilityNodeInfo source = event.getSource();
                    if (source != null) {
                        Log.d(TAG, "事件--source-->" + source);
                        Log.d(TAG, "事件--sourceId-->" + source.getViewIdResourceName());

                        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
                        recycleForPwd(rootNode);

                        autoFindAndInputAccount();
                    }
                }
                break;
        }
    }

    public void autoFindAndInputAccount() {
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        if (rootNode != null) {
            List<AccessibilityNodeInfo> accountList = rootNode.findAccessibilityNodeInfosByViewId(loginNameViewId);
            List<AccessibilityNodeInfo> pwdList = rootNode.findAccessibilityNodeInfosByViewId(pwdViewId);
            if (accountList != null && accountList.size() > 0) {
                Log.d(TAG, "accountList.size()-->" + accountList.size());
                setText(accountList.get(0), "15051286108");
            }
            if (pwdList != null && pwdList.size() > 0) {
                Log.d(TAG, "pwdList.size()-->" + pwdList.size());
                setText(pwdList.get(0), "123456b");
            }
        }

    }

    public void setText(AccessibilityNodeInfo node, String text) {
        Bundle arguments = new Bundle();
        arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,
                text);
        node.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
    }

    /**
     * 打印一个节点的结构
     */
    @SuppressLint("NewApi")
    public void recycleForPwd(AccessibilityNodeInfo info) {
        if (info == null) {
            return;
        }
        if (info.getChildCount() == 0) {
            Log.i(TAG, "edit  recycleForPwd view  :" + info);
            Log.i(TAG, "edit  recycleForPwd view id:" + info.getViewIdResourceName());
        } else {
            for (int i = info.getChildCount() - 1; i >= 0; i--) {
                if (info.getChild(i) != null) {
                    recycleForPwd(info.getChild(i));
                }
            }
        }
    }

    @Override
    public void onInterrupt() {
        Toast.makeText(AutoInputAccountService.this, "自动帐号输入服务中断！", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Toast.makeText(AutoInputAccountService.this, "自动帐号输入服务已连接", Toast.LENGTH_SHORT).show();
    }
}
