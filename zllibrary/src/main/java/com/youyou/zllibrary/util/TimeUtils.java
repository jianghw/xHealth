package com.youyou.zllibrary.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 版权:    张磊
 * 作者:    张磊
 * 描述：
 * 针对这种时间格式2016-01-18T11:04:48
 * 创建日期:2016/1/20
 * 修订日期:
 */

public class TimeUtils {
    private String time;
    private Calendar calendar;
    private Date date;

    public TimeUtils(String time) {
        this.time = time;
        init();
    }

    public TimeUtils() {
    }

    public void setTime(String time) {
        this.time = time;
        init();
    }

    public void setTime(String year, String monthOfYear, String dayOfMonth, String hourOfDay, String minuteOfHour, String secondOfMinute) {
        StringBuilder stringBuilder = new StringBuilder(year);
        stringBuilder.append("-");
        stringBuilder.append(monthOfYear);
        stringBuilder.append("-");
        stringBuilder.append(dayOfMonth);
        stringBuilder.append("T");
        stringBuilder.append(hourOfDay);
        stringBuilder.append(":");
        stringBuilder.append(minuteOfHour);
        stringBuilder.append(":");
        stringBuilder.append(secondOfMinute);
        this.time = stringBuilder.toString();
        init();
    }

    private void init() {
//        String[] times = time.split("T|:|-");
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }
        calendar = Calendar.getInstance();
        calendar.setTime(date);
    }

    //是否是上午
    public boolean isAm() {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        return hour <= 12;
    }

    public String getTime() {
        return time;
    }

    //格式化当前日期
    public String getDate(String year, String month, String dayOfMonth) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(calendar.get(Calendar.YEAR));
        stringBuilder.append(year);
        stringBuilder.append(calendar.get(Calendar.MONTH));
        stringBuilder.append(month);
        stringBuilder.append(calendar.get(Calendar.DAY_OF_MONTH));
        stringBuilder.append(dayOfMonth);
        return stringBuilder.toString();
    }

    //格式化当前时间
    public String getTime(String hour) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(calendar.get(Calendar.HOUR_OF_DAY));
        stringBuilder.append(hour);
        stringBuilder.append(calendar.get(Calendar.MINUTE));
        return stringBuilder.toString();
    }

    //获得年龄   周岁
    public int getAge() {
        int age = 1;
        Calendar curCalendar = Calendar.getInstance();
        if (curCalendar.get(Calendar.YEAR) == this.calendar.get(Calendar.YEAR) && calendar.getTime().getTime() <= curCalendar.getTime().getTime()) {
            return 0;
        }
        age = curCalendar.get(Calendar.YEAR) - this.calendar.get(Calendar.YEAR);
        if (curCalendar.get(Calendar.MONTH) < calendar.get(Calendar.MONTH)) {
            return age;
        } else if (curCalendar.get(Calendar.MONTH) > calendar.get(Calendar.MONTH)) {
            return age - 1;
        } else {
            if (curCalendar.get(Calendar.DAY_OF_MONTH) < calendar.get(Calendar.DAY_OF_MONTH)) {
                return age;
            } else if (curCalendar.get(Calendar.DAY_OF_MONTH) > calendar.get(Calendar.DAY_OF_MONTH)) {
                return age - 1;
            } else {
                return age;
            }
        }
    }
}
