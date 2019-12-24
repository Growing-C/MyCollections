package com.cgy.mycollections.functions.accessibility.weixindemo;

import java.util.List;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

public class EnvelopeService extends AccessibilityService {
    public static final String TAG = "cgy";
    public boolean onGetBoolean = false;//表示已经点击了一个领取红包不要再点上一个了，一次只能抢最下面的那个红包

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();

        Log.d(TAG, "事件---->" + event);

        switch (eventType) {
            //第一步：监听通知栏消息
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                List<CharSequence> texts = event.getText();
                if (!texts.isEmpty()) {
                    for (CharSequence text : texts) {
                        String content = text.toString();
                        Log.d(TAG, "text:" + content);
                        if (content.contains("[微信红包]")) {
                            //模拟打开通知栏消息
                            if (event.getParcelableData() != null
                                    &&
                                    event.getParcelableData() instanceof Notification) {
                                Notification notification = (Notification) event.getParcelableData();
                                PendingIntent pendingIntent = notification.contentIntent;
                                try {
                                    pendingIntent.send();
                                } catch (CanceledException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
                break;
            //第二步：监听是否进入微信红包消息界面
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                String className = event.getClassName().toString();
                if (className.equals("com.tencent.mm.ui.LauncherUI")) {
                    //开始抢红包
                    getPacket();
                } else if (className.equals("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyReceiveUI")) {
                    //开始打开红包
                    openPacket();
                }
                break;
        }
    }

    /**
     * 查找到
     */
    @SuppressLint("NewApi")
    private void openPacket() {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo != null) {
            List<AccessibilityNodeInfo> list = nodeInfo
                    .findAccessibilityNodeInfosByText("拆红包");
            for (AccessibilityNodeInfo n : list) {
                n.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }

    }

    @SuppressLint("NewApi")
    private void getPacket() {
        onGetBoolean = false;
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        if (rootNode == null) {
            Log.d(TAG, "rootWindow为空!");
            Toast.makeText(EnvelopeService.this, "出现错误,红包没抢着！", Toast.LENGTH_SHORT).show();
            return;
        }
        recycle(rootNode);
    }

    /**
     * 打印一个节点的结构
     *
     * @param info
     */
    @SuppressLint("NewApi")
    public void recycle(AccessibilityNodeInfo info) {
        if (info.getChildCount() == 0) {
            if (info.getText() != null) {
                String str = info.getText().toString();
                if ("领取红包".equals(str) || "開".equals(str)) {
                    onGetBoolean = true;
                    //这里有一个问题需要注意，就是需要找到一个可以点击的View
                    Log.i("demo", "Click" + ",isClick:" + info.isClickable());
                    info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    AccessibilityNodeInfo parent = info.getParent();
                    while (parent != null) {
                        Log.i("demo", "parent isClick:" + parent.isClickable());
                        if (parent.isClickable()) {
                            parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                            break;
                        }
                        parent = parent.getParent();
                    }

                }
            }

        } else {

            for (int i = info.getChildCount() - 1; i >= 0; i--) {
                if (info.getChild(i) != null && !onGetBoolean) {
                    recycle(info.getChild(i));
                }
            }
        }
    }

    @Override
    public void onInterrupt() {
        Toast.makeText(EnvelopeService.this, "抢红包服务中断！", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Toast.makeText(EnvelopeService.this, "抢红包服务已连接", Toast.LENGTH_SHORT).show();
    }
}
