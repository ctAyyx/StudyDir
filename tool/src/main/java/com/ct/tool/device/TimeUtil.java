package com.ct.tool.device;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ct on 2018/3/21.
 * <p>
 * 时间工具类
 */

public class TimeUtil {

    public static final String MATE_YMD = "yyyy-MM-dd";
    public static final String MATE_YMD_SM = "yyyy-MM-dd HH:mm";
    public static final String MATE_YMD_SMS = "yyyy-MM-dd HH:mm:ss";

    /*
     * 获取当前时间的前一天
     * */
    public static Date getBeforeDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        return date;
    }

    public static String getBeforeDay(String mate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        SimpleDateFormat format = new SimpleDateFormat(mate, Locale.CHINA);

        return format.format(calendar.getTime());
    }


    /*
     * 获取当前时间
     *
     * */

    public static Date getCurrentDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 0);
        date = calendar.getTime();
        return date;
    }

    public static String getCurrentDay(String mate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, 0);
        SimpleDateFormat format = new SimpleDateFormat(mate, Locale.CHINA);

        return format.format(calendar.getTime());
    }

    /*
     * 获取当前时间的后一天
     * */
    public static Date getNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        date = calendar.getTime();
        return date;
    }

    public static String getNextDay(String mate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat format = new SimpleDateFormat(mate, Locale.CHINA);

        return format.format(calendar.getTime());
    }


    /*
     * 判断输入时间和当前时间那个在前
     *
     * true 输入时间在当前时间以前
     * false 输入时间在当前时间以后
     * */
    public static boolean isBeforeNow(String time, String mate) {

        try {
            SimpleDateFormat format = new SimpleDateFormat(mate, Locale.CHINA);
            long time1 = format.parse(time).getTime();
            long time2 = new Date().getTime();
            return time1 - time2 < 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    /**
     * 比较两个时间 那个在前
     */
    public static boolean isBefore(String time, String time2) {
        long timeSize = new Date(time).getTime();
        long timeSize2 = new Date(time2).getTime();
        return timeSize < timeSize2;

    }
}
