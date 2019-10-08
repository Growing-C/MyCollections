package com.cgy.mycollections.widgets.pickerview.view;

import android.content.Context;
import android.view.View;


import com.cgy.mycollections.R;
import com.cgy.mycollections.widgets.pickerview.TimePickerView;
import com.cgy.mycollections.widgets.pickerview.adapter.NumericWheelAdapter;
import com.cgy.mycollections.widgets.pickerview.lib.WheelView;
import com.cgy.mycollections.widgets.pickerview.listener.OnItemSelectedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class WheelTime {
    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private View view;
    private WheelView wv_year;
    private WheelView wv_month;
    private WheelView wv_day;
    private WheelView wv_hours;
    private WheelView wv_mins;

    private TimePickerView.Type type;
    public static final int DEFULT_START_YEAR = 1900;
    public static int DEFULT_END_YEAR;
    private int startYear = DEFULT_START_YEAR;
    private int endYear = DEFULT_END_YEAR;
    private int mEndMonth = -1;//默认不设置结束月份和日期
    private int mEndDay = -1;

    private Date mEndDate;//结束日期


    public WheelTime(View view) {
        super();
        this.view = view;
        type = TimePickerView.Type.ALL;
        setView(view);
    }

    public WheelTime(View view, TimePickerView.Type type) {
        super();
        this.view = view;
        this.type = type;
        setView(view);
    }

    private void setDefaultEndYear() {
        Calendar c = Calendar.getInstance();
        DEFULT_END_YEAR = c.get(Calendar.YEAR);
        endYear = DEFULT_END_YEAR;//设置默认的结束年 防止没有数据
    }

    public void setPicker(int year, int month, int day) {
        this.setPicker(year, month, day, 0, 0);
    }

    public void setPicker(int year, int month, int day, int h, int m) {

        // 添加大小月月份并将其转换为list,方便之后的判断
        String[] months_big = {"1", "3", "5", "7", "8", "10", "12"};
        String[] months_little = {"4", "6", "9", "11"};

        final List<String> list_big = Arrays.asList(months_big);
        final List<String> list_little = Arrays.asList(months_little);

        Context context = view.getContext();
        // 年
        wv_year = (WheelView) view.findViewById(R.id.year);
        wv_year.setAdapter(new NumericWheelAdapter(startYear, endYear));// 设置"年"的显示数据
        wv_year.setLabel(context.getString(R.string.pickerview_year));// 添加文字
        wv_year.setCurrentItem(year - startYear);// 初始化时显示的数据
        System.out.println("setPicker  year:" + year + "  -----endyear:" + endYear);
        // 月
        wv_month = (WheelView) view.findViewById(R.id.month);

        if (endYear == year && mEndMonth > 0) {//如果是最后一年,且设置了结束月，则最多显示到结束的月份
            wv_month.setAdapter(new NumericWheelAdapter(1, mEndMonth));
        } else {
            wv_month.setAdapter(new NumericWheelAdapter(1, 12));
        }
        wv_month.setLabel(context.getString(R.string.pickerview_month));
        wv_month.setCurrentItem(month - 1);

        // 日
        System.out.println("setPicker  Month:" + month + "  -----mEndMonth" + mEndMonth + "   mEndDay:" + mEndDay);

        wv_day = (WheelView) view.findViewById(R.id.day);
        if (endYear == year && mEndMonth == month && mEndDay > 0) {//如果是最后一年,且当前月份是结束月，且设置了结束天，则设置日滚轮限制
            wv_day.setAdapter(new NumericWheelAdapter(1, mEndDay));

            wv_day.setCurrentItem(day <= mEndDay ? day - 1 : mEndDay - 1);//防止设置的日期大于endDay
        } else {
            // 判断大小月及是否闰年,用来确定"日"的数据
            if (list_big.contains(String.valueOf(month + 1))) {
                wv_day.setAdapter(new NumericWheelAdapter(1, 31));
            } else if (list_little.contains(String.valueOf(month + 1))) {
                wv_day.setAdapter(new NumericWheelAdapter(1, 30));
            } else {
                // 闰年
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
                    wv_day.setAdapter(new NumericWheelAdapter(1, 29));
                else
                    wv_day.setAdapter(new NumericWheelAdapter(1, 28));
            }
            wv_day.setCurrentItem(day - 1);
        }
        wv_day.setLabel(context.getString(R.string.pickerview_day));


        wv_hours = (WheelView) view.findViewById(R.id.hour);
        wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
        wv_hours.setLabel(context.getString(R.string.pickerview_hours));// 添加文字
        wv_hours.setCurrentItem(h);

        wv_mins = (WheelView) view.findViewById(R.id.min);
        wv_mins.setAdapter(new NumericWheelAdapter(0, 59));
        wv_mins.setLabel(context.getString(R.string.pickerview_minutes));// 添加文字
        wv_mins.setCurrentItem(m);

        // 添加"年"监听
        OnItemSelectedListener wheelListener_year = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                int year_num = index + startYear;
                int month_num;
                System.out.println("\"年\"监听:year_num:" + year_num);

                month_num = setMonthWheel(year_num);//此处因为代码修改monthWheel不会触发wheelListener_month，所以手动获得month来设置

                setDayWheel(list_big, list_little, year_num, month_num);
            }
        };
        // 添加"月"监听
        OnItemSelectedListener wheelListener_month = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                int month_num = index + 1;
                int year_num = wv_year.getCurrentItem() + startYear;

                System.out.println("\"月\"监听:month_num:" + month_num);

                setDayWheel(list_big, list_little, year_num, month_num);
            }
        };
        wv_year.setOnItemSelectedListener(wheelListener_year);
        wv_month.setOnItemSelectedListener(wheelListener_month);

        // 根据屏幕密度来指定选择器字体的大小(不同屏幕可能不同)
        int textSize = 6;
        switch (type) {
            case ALL:
                textSize = textSize * 3;
                break;
            case YEAR_MONTH_DAY:
                textSize = textSize * 4;
                wv_hours.setVisibility(View.GONE);
                wv_mins.setVisibility(View.GONE);
                break;
            case YEAR:
                textSize = textSize * 4;
                wv_hours.setVisibility(View.GONE);
                wv_mins.setVisibility(View.GONE);
                wv_month.setVisibility(View.GONE);
                wv_day.setVisibility(View.GONE);
                break;
            case HOURS_MINS:
                textSize = textSize * 4;
                wv_year.setVisibility(View.GONE);
                wv_month.setVisibility(View.GONE);
                wv_day.setVisibility(View.GONE);
                break;
            case MONTH_DAY_HOUR_MIN:
                textSize = textSize * 3;
                wv_year.setVisibility(View.GONE);
                break;
            case YEAR_MONTH:
                textSize = textSize * 4;
                wv_day.setVisibility(View.GONE);
                wv_hours.setVisibility(View.GONE);
                wv_mins.setVisibility(View.GONE);
        }
        wv_day.setTextSize(textSize);
        wv_month.setTextSize(textSize);
        wv_year.setTextSize(textSize);
        wv_hours.setTextSize(textSize);
        wv_mins.setTextSize(textSize);

    }


    /**
     * 设置月份
     *
     * @param year_num
     * @return 返回当前显示的月份
     */
    private int setMonthWheel(int year_num) {
        System.out.println("setMonthWheel----------------");
        int maxMonth = 12;

        if (endYear == year_num && mEndMonth > 0) {//如果是最后一年,且设置了结束月，则最多显示到结束的月份
            wv_month.setAdapter(new NumericWheelAdapter(1, mEndMonth));
            maxMonth = mEndMonth;
        } else {
            wv_month.setAdapter(new NumericWheelAdapter(1, 12));
        }

        //防止月份超出
        if (wv_month.getCurrentItem() > maxMonth - 1) {
            wv_month.setCurrentItem(maxMonth - 1);
        }

        return wv_month.getCurrentItem() + 1;
    }

    /**
     * 设置天的滚轮
     *
     * @param list_big
     * @param list_little
     * @param year_num
     * @param monthNum
     */
    private void setDayWheel(List<String> list_big, List<String> list_little, int year_num, int monthNum) {
        System.out.println("setDayWheel++++++++++++++++");
        // 判断大小月及是否闰年,用来确定"日"的数据
        int maxItem;
        if (year_num == endYear && monthNum == mEndMonth && mEndDay > 0) {//设置了结束日期，且当前日期年月都是结束日期
            wv_day.setAdapter(new NumericWheelAdapter(1, mEndDay));
            maxItem = mEndDay;
        } else if (list_big.contains(String.valueOf(monthNum))) {
            wv_day.setAdapter(new NumericWheelAdapter(1, 31));
            maxItem = 31;
        } else if (list_little.contains(String.valueOf(monthNum))) {
            wv_day.setAdapter(new NumericWheelAdapter(1, 30));
            maxItem = 30;
        } else {
            if ((year_num % 4 == 0 && year_num % 100 != 0)
                    || year_num % 400 == 0) {
                wv_day.setAdapter(new NumericWheelAdapter(1, 29));
                maxItem = 29;
            } else {
                wv_day.setAdapter(new NumericWheelAdapter(1, 28));
                maxItem = 28;
            }
        }
        if (wv_day.getCurrentItem() > maxItem - 1) {
            wv_day.setCurrentItem(maxItem - 1);
        }
    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic
     */
    public void setCyclic(boolean cyclic) {
        wv_year.setCyclic(cyclic);
        wv_month.setCyclic(cyclic);
        wv_day.setCyclic(cyclic);
        wv_hours.setCyclic(cyclic);
        wv_mins.setCyclic(cyclic);
    }

    public String getTime() {
        StringBuffer sb = new StringBuffer();
        sb.append((wv_year.getCurrentItem() + startYear)).append("-")
                .append((wv_month.getCurrentItem() + 1)).append("-")
                .append((wv_day.getCurrentItem() + 1)).append(" ")
                .append(wv_hours.getCurrentItem()).append(":")
                .append(wv_mins.getCurrentItem());
        return sb.toString();
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
        setDefaultEndYear();//先设置默认的结束年，防止没有数据
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    /**
     * 设置结束日期
     *
     * @param endDate
     */
    public void setEndDate(Date endDate) {
        this.mEndDate = endDate;
        if (mEndDate != null) {
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(mEndDate);

            endYear = endCalendar.get(Calendar.YEAR);
            mEndMonth = endCalendar.get(Calendar.MONTH) + 1;//month从0开始 下面wheelview从1开始，所以默认加1
            mEndDay = endCalendar.get(Calendar.DAY_OF_MONTH);
        }
    }
}
