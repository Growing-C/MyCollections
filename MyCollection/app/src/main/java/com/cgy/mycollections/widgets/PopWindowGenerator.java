package com.cgy.mycollections.widgets;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.cgy.mycollections.widgets.pickerview.TimePickerView;

import java.util.Calendar;
import java.util.Date;

/**
 * 为了减少activity中的代码，创建一个类来专门创建popupWindow
 * Created by RB-cgy on 2017/1/18.
 */

public class PopWindowGenerator {
    /**
     * 选择时间
     *
     * @param context
     * @param listener
     * @return
     */
    public static TimePickerView createTimePickerView(Context context, TimePickerView.OnTimeSelectListener listener) {
        //时间选择器
        TimePickerView pvTime = new TimePickerView(context, TimePickerView.Type.YEAR_MONTH_DAY);//如果要仅显示年 改为TimePickerView.Type.YEAR即可
        //控制时间范围
//        Calendar calendar = Calendar.getInstance();
        Date currentDate = new Date();
        pvTime.setRange(1900, currentDate);//要在setTime 之前才有效果哦
        pvTime.setTime(currentDate);
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(listener);

        //默认日期设置为1990/1/1
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 1990);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DATE, 1);
        pvTime.setTime(calendar.getTime());

        return pvTime;
    }

    /**
     * 默认的pop样式
     *
     * @param v
     * @return
     */
    private static PopupWindow createPop(View v, int width, int height, int animIds) {
        PopupWindow pop = new PopupWindow(v, width, height, false);
        pop.setOutsideTouchable(true);
        //设置SelectPicPopupWindow弹出窗体可点击
        pop.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        if (animIds != -1) {//-1是用默认的
            pop.setAnimationStyle(animIds);
        }
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        pop.setBackgroundDrawable(dw);
        return pop;
    }
}
