package com.gradproject.server.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static String FORMAT = "yyyyMMdd";
    public static String FORMAT_SHORT = "yyyy-MM-dd";
    public static String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";
    public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";
    public static String FORMAT_FULLY = "yyyyMMddHHmmssS";
    public static String FORMAT_MEDIUM = "yyyyMMddHHmmss";
    public static String FORMAT_SHORT_CN = "yyyy年MM月dd";
    public static String FORMAT_LONG_CN = "yyyy年MM月dd日  HH时mm分ss秒";
    public static String FORMAT_FULL_CN = "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒";


    public static Date getDate(String source, String format) {
        if ((format == null) || ("".equals(format))) {
            format = FORMAT;
        }
        if ((source == null) || ("".equals(source))) {
            return null;
        }
        SimpleDateFormat df = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = df.parse(source);
        } catch (ParseException e) {
            //e.printStackTrace();
        }
        return date;
    }

    public static Date dealVueDateFormat(String oldDate) {
        Date date1 = null;
        try {
            oldDate = oldDate.replace("Z", " UTC");//注意是空格+UTC
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");//注意格式化的表达式
            date1 = format.parse(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return date1;
    }

    public static Date getStartDate(String source) {
        String format = "YYYY-MM";
        if ((source == null) || ("".equals(source))) {
            return null;
        }
        SimpleDateFormat df = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = df.parse(source);
        } catch (ParseException e) {
            //e.printStackTrace();
        }
        date = addDays(date, 1);
        return date;
    }

    public static Date addMonths(Date time, int months) {
        SimpleDateFormat sf = new SimpleDateFormat(FORMAT_LONG);
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        c.add(2, months);
        Date result = getDate(sf.format(c.getTime()), FORMAT_LONG);

        return result;
    }


    public static Date addDays(Date time, int days) {
        SimpleDateFormat sf = new SimpleDateFormat(FORMAT_LONG);
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        //5代表对日期进行操作
        c.add(5, days);
        Date result = getDate(sf.format(c.getTime()), FORMAT_LONG);

        return result;
    }

    public static Date addHours(Date time, int hours) {
        SimpleDateFormat sf = new SimpleDateFormat(FORMAT_LONG);
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        c.add(Calendar.HOUR_OF_DAY, hours);
        Date result = getDate(sf.format(c.getTime()), FORMAT_LONG);

        return result;
    }

    public static Date addMinuter(Date time, int minuter) {
        SimpleDateFormat sf = new SimpleDateFormat(FORMAT_LONG);
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        c.add(12, minuter);

        Date result = getDate(sf.format(c.getTime()), FORMAT_LONG);

        return result;
    }

    /**
     * 获取本月1号 00:00:00
     *
     * @param time
     * @return
     */
    public static Date getCurrentMonth(Date time) {
        SimpleDateFormat sf = new SimpleDateFormat(FORMAT_LONG);
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        Date result = getDate(sf.format(c.getTime()), FORMAT_LONG);

        return result;
    }

    public static Date addSeconds(Date time, int seconds) {
        SimpleDateFormat sf = new SimpleDateFormat(FORMAT_LONG);
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        c.add(13, seconds);

        Date result = getDate(sf.format(c.getTime()), FORMAT_LONG);

        return result;
    }

    public static String getTransDate(Date date) {
        SimpleDateFormat myFmt = new SimpleDateFormat("YYMMdd");

        return myFmt.format(date);
    }

    public static String getFormatDate(Date date, String format) {
        if (format == null || "".equals(format)) {
            format = FORMAT_LONG;
        }
        SimpleDateFormat myFmt = new SimpleDateFormat(format);

        return myFmt.format(date);
    }

    public static String getTaskDate(Date date) {
        SimpleDateFormat myFmt = new SimpleDateFormat(FORMAT);

        return myFmt.format(date);
    }

    public static String getTimeString() {
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_FULL);
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());
    }

    public static String getCurrentTimeString() {
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_FULLY);
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());
    }

    public static String getYear(Date date) {
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_FULL);
        return df.format(date).substring(0, 4);
    }

    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(2) + 1;
    }

    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(5);
    }

    public static int getDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(7);
    }

    public static int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(11);
    }

    public static int getMinute(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(12);
    }

    public static int getSecond(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(13);
    }

    public static long getMillis(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    public static Date todayBeginDate(Date date) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(date);

        cal.set(11, cal.getActualMinimum(11));

        cal.set(12, cal.getActualMinimum(12));

        cal.set(13, cal.getActualMinimum(13));

        cal.set(14, cal.getActualMinimum(14));

        return cal.getTime();
    }

    public static Date todayEndDate(Date date) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(date);

        cal.set(11, cal.getActualMaximum(11));

        cal.set(12, cal.getActualMaximum(12));

        cal.set(13, cal.getActualMaximum(13));

        cal.set(14, cal.getActualMaximum(14));

        return cal.getTime();
    }

    public static Date monthBeginDate(Date date) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(date);

        cal.set(5, cal.getActualMinimum(5));

        cal.set(11, cal.getActualMinimum(11));

        cal.set(12, cal.getActualMinimum(12));

        cal.set(13, cal.getActualMinimum(13));

        cal.set(14, cal.getActualMinimum(14));

        return cal.getTime();
    }

    public static Date monthEndDate(Date date) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(date);

        cal.set(5, cal.getActualMaximum(5));

        cal.set(11, cal.getActualMaximum(11));

        cal.set(12, cal.getActualMaximum(12));

        cal.set(13, cal.getActualMaximum(13));

        cal.set(14, cal.getActualMaximum(14));

        return cal.getTime();
    }

    public static Date splitJointDateByHHss(String time) {
        if ((time == null) || (time.length() != 4)) {
            return null;
        }
        Calendar cal = Calendar.getInstance();

        cal.setTime(new Date());

        cal.set(11, Integer.valueOf(time.substring(0, 2)).intValue());

        cal.set(12, Integer.valueOf(time.substring(2, 4)).intValue());

        cal.set(13, 0);

        cal.set(14, 0);

        return cal.getTime();
    }

    public static Date addDaysAndHours(Date time, int days, int hours, int minute) {
        SimpleDateFormat sf = new SimpleDateFormat(FORMAT_LONG);
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        c.add(5, days);//添加天数
        c.add(Calendar.HOUR_OF_DAY, hours);//添加小时
        c.add(12, minute);//添加分
        Date result = getDate(sf.format(c.getTime()), FORMAT_LONG);

        return result;
    }
}
