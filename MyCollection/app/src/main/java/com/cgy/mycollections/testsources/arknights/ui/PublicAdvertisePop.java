package com.cgy.mycollections.testsources.arknights.ui;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

import com.cgy.mycollections.R;

import java.lang.reflect.Method;

/**
 * Description :
 * Author :cgy
 * Date :2019/8/19
 */
public class PublicAdvertisePop implements View.OnClickListener {

    private PopupWindow mPop;
    Context mContext;

    public PublicAdvertisePop(Context context) {
        mContext = context;
    }

    private View getSettingTableView() {
        View settingTable = LayoutInflater.from(mContext).inflate(R.layout.easy_touch_setting_table, null);
        Button screenLockButton = (Button) settingTable.findViewById(R.id.show_setting_table_item_screen_lock_button);
        Button notificationButton = (Button) settingTable.findViewById(R.id.show_setting_table_item_notification_button);

        Button phoneButton = (Button) settingTable.findViewById(R.id.show_setting_table_item_phone_button);
        Button pageButton = (Button) settingTable.findViewById(R.id.show_setting_table_item_page_button);
        Button cameraButton = (Button) settingTable.findViewById(R.id.show_setting_table_item_camera_button);

        Button backButton = (Button) settingTable.findViewById(R.id.show_setting_table_item_back_button);
        Button homeButton = (Button) settingTable.findViewById(R.id.show_setting_table_item_home_button);
        Button exitTouchButton = (Button) settingTable.findViewById(R.id.show_setting_table_item_exit_touch_button);

        screenLockButton.setOnClickListener(this);
        notificationButton.setOnClickListener(this);

        phoneButton.setOnClickListener(this);
        pageButton.setOnClickListener(this);
        cameraButton.setOnClickListener(this);

        backButton.setOnClickListener(this);
        homeButton.setOnClickListener(this);
        exitTouchButton.setOnClickListener(this);

        return settingTable;
    }

    private void showPop(final View touchView) {
        if (mPop == null) {

            mPop = new PopupWindow(getSettingTableView(), WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

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

        mPop.showAtLocation(touchView, Gravity.CENTER, 0, 0);
    }

    @Override
    public void onClick(View v) {

    }
}
