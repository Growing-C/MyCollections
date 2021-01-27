package appframe.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * TimeUtils
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-8-24
 */
public class TimeUtils {

    public static final String PATTERN_DATE = "yyyy-MM-dd";
    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat DATE_FORMAT_DATE2 = new SimpleDateFormat("yyyyMMdd");

    private TimeUtils() {
        throw new AssertionError();
    }

    /**
     * 获取系统时间样式 12小时制 还是24小时制，或者跟随系统会返回null
     *
     * @param context context
     * @return 12 /24 / null
     * @see android.content.Intent#EXTRA_TIME_PREF_VALUE_USE_12_HOUR
     * @see android.text.format.DateFormat#is24HourFormat(Context) 和这个方法内容差不多
     * @deprecated use {@link android.text.format.DateFormat#is24HourFormat(Context)}
     */
    public static String getSystemHourFormat(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        String time_12_24 = Settings.System.getString(contentResolver, Settings.System.TIME_12_24);
        L.i("time_12_24:" + time_12_24);
        return time_12_24;
    }

    /**
     * 获取当前系统时间格式
     * 12小时制 2021年1月27日 下午1:53:57   toPattern:y年M月d日 ah:mm:ss
     * 24小时制 2021年1月27日 13:54:02     toPattern:y年M月d日 HH:mm:ss
     *
     * @deprecated 这种方式在有的地方不准，使用android.text.DateFormat.is24HourFormat()方法
     */
    public static void getSystemTimeFormat() {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance();
        String formatS = dateFormat.format(calendar.getTime());
        L.i("testTimeFormat:" + formatS);
        if (dateFormat instanceof SimpleDateFormat) {
            L.i("toPattern:" + ((SimpleDateFormat) dateFormat).toPattern());
        }
        L.i("am_pm:" + calendar.get(Calendar.AM_PM));
    }

    /**
     * 获取当前系统时间是不是24小时制
     *
     * @return true-24小时制 false-12小时制
     * @deprecated 这种方式在有的地方不准，使用android.text.DateFormat.is24HourFormat()方法
     */
    public static boolean isSystemTimeIn24() {
        String systemTimePattern = null;
        DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance();
        if (dateFormat instanceof SimpleDateFormat) {
            systemTimePattern = ((SimpleDateFormat) dateFormat).toPattern();
            L.i("toPattern:" + systemTimePattern);
        }
        return (systemTimePattern != null && systemTimePattern.contains("HH"));
    }

    /**
     * long time to string
     *
     * @param timeInMillis
     * @param dateFormat
     * @return
     */
    public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMillis));
    }

    /**
     * long time to string, format is {@link #DEFAULT_DATE_FORMAT}
     *
     * @param timeInMillis
     * @return
     */
    public static String getTime(long timeInMillis) {
        return getTime(timeInMillis, DEFAULT_DATE_FORMAT);
    }

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static long getCurrentTimeInLong() {
        return System.currentTimeMillis();
    }


    public static long convertUTCToLocal(long timeInMillis) {
        return timeInMillis - TimeZone.getDefault().getRawOffset();
    }

    //get age
    public static String getAge(long bitrh) {
        String birthday = getTime(bitrh);
        String birth_year = birthday.substring(0, 4);
        String currentday = getTime(getCurrentTimeInLong());
        String currentday_year = currentday.substring(0, 4);
        return String.valueOf(Integer.valueOf(currentday_year) - Integer.valueOf(birth_year));
    }

    //get age
    public static String getAgeString(String birthday) {
        String birth_year = birthday.substring(0, 4);
        String currentday = getTime(getCurrentTimeInLong());
        String currentday_year = currentday.substring(0, 4);
        return String.valueOf(Integer.valueOf(currentday_year) - Integer.valueOf(birth_year));
    }

    public static String getDate(String date) {
        String notice_date = "";
        String notice_year = date.substring(0, 4);
        String notice_month = date.substring(5, 7);
        String notice_day = date.substring(8, 10);
        //  System.out.println("notice_year=="+notice_year+"notice_month=="+notice_month
        //       +"notice_day=="+notice_day);
        String currentday = getTime(getCurrentTimeInLong());
        String currentday_year = currentday.substring(0, 4);
        String currentday_month = currentday.substring(5, 7);
        String currentday_day = currentday.substring(8, 10);
        //  System.out.println("currentday_year=="+currentday_year+"currentday_month=="+currentday_month
        //   +"currentday_day=="+currentday_day);
        if (!notice_year.equals(currentday_year)) {
            notice_date = notice_year.substring(2, 4) + "/" + notice_month + "/" + notice_day;
        } else if (!notice_month.equals(currentday_month)) {
            notice_date = notice_month + "/" + notice_day;
        } else if (!notice_day.equals(currentday_day)) {
            notice_date = notice_month + "/" + notice_day;
        } else {
            notice_date = date.substring(date.length() - 5, date.length());
        }
        return notice_date;
    }

    /**
     * 获取milli的毫秒值直接换算成时间的值
     *
     * @param milli
     * @return
     */
    public static String getIntervalTime(long milli) {
        if (milli <= 0)
            return "0";
        String intervalTime = "";

        long sec = 1000;
        long min = sec * 60;
        long hour = min * 60;
        long day = hour * 24;
        //不用month了，每个月天数不一样计算起来麻烦
        long year = day * 365;

        if (milli > year) {//时间大于一年
            long yearCount = milli / year;
            intervalTime = yearCount + "年";
            milli -= year * yearCount;//减去已计算的年份时间
        }
        if (milli > day) {
            long dayCount = milli / day;
            intervalTime += dayCount + "天";
            milli -= day * dayCount;//减去已计算的天数时间
        }
        if (milli > hour) {
            long hourCount = milli / hour;
            intervalTime += hourCount + "小时";
            milli -= hour * hourCount;
        }
        if (milli > min) {
            long minCount = milli / min;
            intervalTime += minCount + "分钟";
            milli -= min * minCount;
        }
        if (milli > sec) {
            long secCount = milli / sec;
            intervalTime += secCount + "秒";
        }
        if (!TextUtils.isEmpty(intervalTime))
            intervalTime += "前";
        return intervalTime;
    }

    /**
     * 获取该日期是周几
     *
     * @param dateS 必须是 mm-dd格式
     * @return
     */
    public static String getWeekDay(String dateS) {
        if (TextUtils.isEmpty(dateS))
            return "";
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sf.parse(dateS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date == null)
            return "";

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
//        System.out.println("dateS:" + dateS + "---dayOfWeek:" + dayOfWeek);
        String weekDayS = "";
        switch (dayOfWeek) {
            case Calendar.SUNDAY:
                weekDayS = "周日";
                break;
            case Calendar.MONDAY:
                weekDayS = "周一";
                break;
            case Calendar.TUESDAY:
                weekDayS = "周二";
                break;
            case Calendar.WEDNESDAY:
                weekDayS = "周三";
                break;
            case Calendar.THURSDAY:
                weekDayS = "周四";
                break;
            case Calendar.FRIDAY:
                weekDayS = "周五";
                break;
            case Calendar.SATURDAY:
                weekDayS = "周六";
                break;
            default:
                break;
        }
        return weekDayS;
    }

    /**
     * 是否是同一天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDay(Calendar date1, Calendar date2) {
        if (date1 == null || date2 == null)
            return false;

        if (date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR)
                && date1.get(Calendar.MONTH) == date2.get(Calendar.MONTH)
                && date1.get(Calendar.DATE) == date2.get(Calendar.DATE))
            return true;

        return false;
    }

    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null)
            return false;
        Calendar ca1 = Calendar.getInstance();
        ca1.setTime(date1);
        Calendar ca2 = Calendar.getInstance();
        ca2.setTime(date2);
        return isSameDay(ca1, ca2);
    }

    public static boolean isSameDay(long timeInMillis1, long timeInMillis2) {
        if (timeInMillis1 <= 0 || timeInMillis2 <= 0)
            return false;
        Calendar ca1 = Calendar.getInstance();
        ca1.setTimeInMillis(timeInMillis1);
        Calendar ca2 = Calendar.getInstance();
        ca2.setTimeInMillis(timeInMillis2);
        return isSameDay(ca1, ca2);
    }
}