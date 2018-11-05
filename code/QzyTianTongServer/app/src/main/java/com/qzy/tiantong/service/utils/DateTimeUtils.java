package com.qzy.tiantong.service.utils;

import android.app.AlarmManager;
import android.content.Context;
import android.provider.Settings;
import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeUtils {

    /**
     * 判断系统使用的是24小时制还是12小时制
     *
     * @param context
     * @return
     */
    public static boolean is24Hour(Context context) {
        return DateFormat.is24HourFormat(context);
    }

    /**
     * 设置系统的12小时制
     *
     * @param context
     */
    public static void set12Hour(Context context) {
        android.provider.Settings.System.putString(context.getContentResolver(),
                android.provider.Settings.System.TIME_12_24, "12");
    }

    /**
     * 设置系统的24小时制
     *
     * @param context
     */
    public static void set24Hour(Context context) {
        android.provider.Settings.System.putString(context.getContentResolver(),
                android.provider.Settings.System.TIME_12_24, "24");
    }

    /**
     * 判断系统的时区是否是自动获取的
     *
     * @return
     */
    public static boolean isTimeZoneAuto(Context context) {
        try {
            return android.provider.Settings.Global.getInt(context.getContentResolver(),
                    android.provider.Settings.Global.AUTO_TIME_ZONE) > 0;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置系统的时区是否自动获取
     *
     * @param checked
     */
    public static void setAutoTimeZone(Context context, int checked) {
        android.provider.Settings.Global.putInt(context.getContentResolver(),
                android.provider.Settings.Global.AUTO_TIME_ZONE, checked);
    }

    /**
     * 判断系统的时间是否自动获取的
     *
     * @return
     */
    public static boolean isDateTimeAuto(Context context) {
        try {
            return android.provider.Settings.Global.getInt(context.getContentResolver(),
                    android.provider.Settings.Global.AUTO_TIME) > 0;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置系统的时间是否需要自动获取
     *
     * @param checked
     */
    public static void setAutoDateTime(Context context, int checked) {
        android.provider.Settings.Global.putInt(context.getContentResolver(),
                android.provider.Settings.Global.AUTO_TIME, checked);
    }

    /**
     * 设置系统日期
     *
     * @param year
     * @param month
     * @param day
     */
    public static void setSysDate(Context context, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        long when = c.getTimeInMillis();
        if (when / 1000 < Integer.MAX_VALUE) {
            ((AlarmManager) context.getSystemService(Context.ALARM_SERVICE)).setTime(when);
        }
    }

    public static void setSysDate(Context context, Calendar c) {
        long when = c.getTimeInMillis();
        if (when / 1000 < Integer.MAX_VALUE) {
            ((AlarmManager) context.getSystemService(Context.ALARM_SERVICE)).setTime(when);
        }
    }


    /**
     * 设置系统时间
     *
     * @param hour
     * @param minute
     */
    public static void setSysTime(Context context, int hour, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        long when = c.getTimeInMillis();
        if (when / 1000 < Integer.MAX_VALUE) {
            ((AlarmManager) context.getSystemService(Context.ALARM_SERVICE)).setTime(when);
        }
    }

    public static void setSysTime(Context context, Calendar c) {
        long when = c.getTimeInMillis();
        if (when / 1000 < Integer.MAX_VALUE) {
            ((AlarmManager) context.getSystemService(Context.ALARM_SERVICE)).setTime(when);
        }
    }


    /**
     * 设置系统时区
     *
     * @param timeZone
     */
    public static void setTimeZone(String timeZone) {
        final Calendar now = Calendar.getInstance();
        TimeZone tz = TimeZone.getTimeZone(timeZone);
        now.setTimeZone(tz);
    }

    public static void setTimeZone(TimeZone timeZone) {
        final Calendar now = Calendar.getInstance();
        now.setTimeZone(timeZone);
    }


    /**
     * 获取系统当前的时区
     *
     * @return
     */
    public static String getDefaultTimeZone() {
        return TimeZone.getDefault().getDisplayName();
    }

    /**
     * yyyy-mm-dd hh:mm:ss 转换
     *
     * @param dateTime
     * @return
     */
    public static Calendar getCalendarByDateTimeStr(String dateTime) {
        Calendar now = Calendar.getInstance();
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
            Date date = simpleDateFormat.parse(dateTime);
            now.setTimeZone(TimeZone.getDefault());
            now.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return now;

    }

    /**
     * 获取当前系统时间
     *
     * @return
     */
    public static String getCurrentTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }


}
