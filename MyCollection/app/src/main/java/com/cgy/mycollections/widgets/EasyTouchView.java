package com.cgy.mycollections.widgets;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.cgy.mycollections.R;

import java.lang.reflect.Method;

/**
 * 仿iphone悬浮圆点
 * Created by user on 2016/10/2.
 */
public class EasyTouchView {
    private Context mContext;
    private WindowManager mWManager;
    private LayoutParams mWMParams;
    private View mTouchView;
    private ImageView mIconImageView = null;
    Button mPauseButton;
    private PopupWindow mPop;
    private FloatingViewActionListener mActionListener;

    private int midX;
    private int midY;

    private boolean mIsPause = false;

    public boolean isPause() {
        return mIsPause;
    }

    public void setPause(boolean mIsPause) {
        this.mIsPause = mIsPause;
    }

    public EasyTouchView(Context context, FloatingViewActionListener listener) {
        mContext = context;
        mActionListener = listener;
    }

    public void show() {
        addToWindow();
    }

    private void addToWindow() {
        // 设置载入view WindowManager参数
        mWManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);

        Point sizePoint = new Point();
        mWManager.getDefaultDisplay().getSize(sizePoint);

        midX = sizePoint.x / 2 - 25;//屏幕宽度
        midY = sizePoint.y / 2 - 44;//屏幕高度
        mTouchView = LayoutInflater.from(mContext).inflate(R.layout.easy_touch_view, null);
        mIconImageView = (ImageView) mTouchView.findViewById(R.id.easy_touch_view_image);
        mTouchView.setBackgroundColor(Color.TRANSPARENT);
        mTouchView.setOnTouchListener(mTouchListener);

        mWMParams = new LayoutParams();
        mWMParams.type = LayoutParams.TYPE_SYSTEM_ALERT; // 这里的2002表示系统级窗口，你也可以试试2003。
        mWMParams.flags = 40; // 设置桌面可控
        mWMParams.width = LayoutParams.WRAP_CONTENT;
        mWMParams.height = LayoutParams.WRAP_CONTENT;
        mWMParams.format = -3; // 透明

        mWManager.addView(mTouchView, mWMParams);

//        midX = mWMParams.x;
//        midY = mWMParams.y;
    }

    private View getSettingTableView() {
        View settingTable = LayoutInflater.from(mContext).inflate(R.layout.easy_touch_setting_table, null);
        mPauseButton = (Button) settingTable.findViewById(R.id.show_setting_table_item_common_use_button);
        Button screenLockButton = (Button) settingTable.findViewById(R.id.show_setting_table_item_screen_lock_button);
        Button notificationButton = (Button) settingTable.findViewById(R.id.show_setting_table_item_notification_button);

        Button phoneButton = (Button) settingTable.findViewById(R.id.show_setting_table_item_phone_button);
        Button pageButton = (Button) settingTable.findViewById(R.id.show_setting_table_item_page_button);
        Button cameraButton = (Button) settingTable.findViewById(R.id.show_setting_table_item_camera_button);

        Button backButton = (Button) settingTable.findViewById(R.id.show_setting_table_item_back_button);
        Button homeButton = (Button) settingTable.findViewById(R.id.show_setting_table_item_home_button);
        Button exitTouchButton = (Button) settingTable.findViewById(R.id.show_setting_table_item_exit_touch_button);

        mPauseButton.setOnClickListener(mClickListener);
        screenLockButton.setOnClickListener(mClickListener);
        notificationButton.setOnClickListener(mClickListener);

        phoneButton.setOnClickListener(mClickListener);
        pageButton.setOnClickListener(mClickListener);
        cameraButton.setOnClickListener(mClickListener);

        backButton.setOnClickListener(mClickListener);
        homeButton.setOnClickListener(mClickListener);
        exitTouchButton.setOnClickListener(mClickListener);

        return settingTable;
    }

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mActionListener == null)
                return;
            switch (v.getId()) {
                case R.id.show_setting_table_item_common_use_button://暂停
                    mActionListener.onFloatingAction(ACTION_PAUSE);
                    break;
                case R.id.show_setting_table_item_screen_lock_button://锁屏
                    mActionListener.onFloatingAction(ACTION_LOCK);
                    break;
                case R.id.show_setting_table_item_notification_button://退出
                    mActionListener.onFloatingAction(ACTION_EXIT);
                    break;
                case R.id.show_setting_table_item_phone_button://打开抢红包服务
                    mActionListener.onFloatingAction(ACTION_OPEN);
                    break;
                case R.id.show_setting_table_item_page_button:
                    break;
                case R.id.show_setting_table_item_camera_button:
                    break;
                case R.id.show_setting_table_item_back_button:
                    break;
                case R.id.show_setting_table_item_home_button:
                    break;
                case R.id.show_setting_table_item_exit_touch_button:
                    break;
            }
            hideSettingTable();

        }
    };

    public void dismiss() {
        hideSettingTable();
        mWManager.removeView(mTouchView);
    }

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        float lastX, lastY;
        int paramX, paramY;
        long downTime = 0;//按下时间
        private int mTag = 0;

        private int mOldOffsetX;
        private int mOldOffsetY;

        public boolean onTouch(View v, MotionEvent event) {
            final int action = event.getAction();

            float x = event.getRawX();
            float y = event.getRawY();

            if (mTag == 0) {
                mOldOffsetX = mWMParams.x; // 偏移量
                mOldOffsetY = mWMParams.y; // 偏移量
            }

            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    motionActionDownEvent(x, y);
                    break;

                case MotionEvent.ACTION_MOVE:
                    motionActionMoveEvent(x, y);
                    break;

                case MotionEvent.ACTION_UP:
                    motionActionUpEvent(x, y);
                    break;

                default:
                    break;
            }

            return true;
        }

        private void motionActionDownEvent(float x, float y) {
            downTime = System.currentTimeMillis();
            lastX = x;
            lastY = y;
            paramX = mWMParams.x;
            paramY = mWMParams.y;
            Log.d("cgy", "down x:" + x + "  down y:" + y);
        }

        private void motionActionMoveEvent(float x, float y) {
            int dx = (int) (x - lastX);
            int dy = (int) (y - lastY);
            mWMParams.x = paramX + dx;
            mWMParams.y = paramY + dy;
            mTag = 1;

            // 更新悬浮窗位置
            mWManager.updateViewLayout(mTouchView, mWMParams);
        }

        private void motionActionUpEvent(float x, float y) {
            long upTime = System.currentTimeMillis();
            int newOffsetX = mWMParams.x;
            int newOffsetY = mWMParams.y;
            if (mOldOffsetX == newOffsetX && mOldOffsetY == newOffsetY && (upTime - downTime) < 1000) {
                //点击事件
//                int offsetX = midX - newOffsetX;
//                int offsetY = midY - newOffsetY;
//                Log.d("cgy", "up x:" + x + "  y:" + y);
//                if (Math.abs(mOldOffsetX) > midX) {
//                    if (mOldOffsetX > 0) {
//                        mOldOffsetX = midX;
//                    } else {
//                        mOldOffsetX = -midX;
//                    }
//                }
//
//                if (Math.abs(mOldOffsetY) > midY) {
//                    if (mOldOffsetY > 0) {
//                        mOldOffsetY = midY;
//                    } else {
//                        mOldOffsetY = -midY;
//                    }
//                }
                showPop(0, 0);
            } else {
                mTag = 0;
            }
        }
    };

    private void showPop(int x, int y) {
        if (mPop == null) {

            mPop = new PopupWindow(getSettingTableView(), dip2px(mContext, 200), dip2px(mContext, 200));

            mPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    mTouchView.setVisibility(View.VISIBLE);
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
                method.invoke(mPop, LayoutParams.TYPE_SYSTEM_ALERT);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        if (mIsPause)
            mPauseButton.setText("开始");
        else
            mPauseButton.setText("暂停");
        Log.d("cgy", "--show pop midX:" + midX + "   midY:" + midY);

        mPop.showAtLocation(mTouchView, Gravity.CENTER, 0, 0);
        mTouchView.setVisibility(View.INVISIBLE);
    }

    private void hideSettingTable() {
        if (null != mPop) {
            mPop.dismiss();
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static final int ACTION_PAUSE = 0x01;//暂停
    public static final int ACTION_LOCK = 0x02;//锁屏
    public static final int ACTION_EXIT = 0x03;//退出
    public static final int ACTION_OPEN = 0x04;//打开

    public interface FloatingViewActionListener {
        void onFloatingAction(int actionId);
    }
}
