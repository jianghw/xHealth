package com.kaurihealth.kaurihealth.util;

import android.util.Log;

import com.kaurihealth.utilslib.date.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by mip on 2016/11/1.
 * 注意传入的值
 */

public class DateConvertUtils {
    private static SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    //想传入哪个，另一个参数为null即可。
    public static String getWeekOfDate(Date dt, String creatTime) {


        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        String date_str = date_format.format(cal.getTime());
//        String date = date_format.format(dt);
        Log.d("cal", date_str);
//        Log.d("date", date);

        /** 当前日期的时间**/
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;  //获取月
        int dates = cal.get(Calendar.DATE);   //获取天
//        Log.d("今天的日期", year + " " + month + " " + dates);

        /** 传递过来日期的时间**/
        if (creatTime != null) {
            SimpleDateFormat formatStrToData = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            try {
                if (dt == null) {
                    dt = new Date();
//                    Log.e("creatTime", creatTime);
                    Date parse = formatStrToData.parse(creatTime);
                    cal.setTime(parse);//用传递过来的String类型装载
                    dt = parse;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            cal.setTime(dt);//用传递过来的date类型装载
        }

        int year_date = cal.get(Calendar.YEAR);
        int month_date = cal.get(Calendar.MONTH) + 1;
        int dates_date = cal.get(Calendar.DATE);
//        Log.d("date的日期", year_date + " " + month_date + " " + dates_date);
        if (year_date == year) {

            //判断是同一天的话就显示今天的时分
            if (dates_date == dates
                    && month == month_date
                    && year == year_date) {
                return DateUtils.GetDateText(dt, "HH:mm");
            } else if ((dates - dates_date) < 2
                    && month == month_date
                    && year_date == year) {
                return "昨天" + " " + DateUtils.GetDateText(dt, "HH:mm");
            } else if (dates - dates_date < 3
                    && month == month_date
                    && year == year_date) {
                return "前天" + " " + DateUtils.GetDateText(dt, "HH:mm");
            } else if (dates - dates_date < 8
                    && month == month_date
                    && year == year_date) {//一周内
                cal.setTime(dt);
                int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
                if (w < 0)
                    w = 0;
                return weekDays[w] + " " + DateUtils.GetDateText(dt, "HH:mm");
            } else if (year == year_date) { //一年内显示只显示月日
                return DateUtils.GetDateText(dt, "MM-dd HH:mm");
            } else if (year - year_date > 1) {//大于一年显示年月日
                return DateUtils.GetDateText(dt, "yyyy-MM-dd HH:mm");
            }
        } else {
            return DateUtils.GetDateText(dt, "yyyy-MM-dd HH:mm");
        }
        return " ";
    }

    /**
     * 拿到指定日期的年
     */
    public static String getYear(String creatTime) {
        Calendar cal = Calendar.getInstance();
        String date_str = date_format.format(cal.getTime());
//        String date = date_format.format(dt);
//        Log.d("cal", date_str);
//        Log.d("date", date);

        /** 当前日期的时间**/
        int year = cal.get(Calendar.YEAR);

        Date dt = null;

        /** 传递过来日期的时间**/
        if (creatTime != null) {
            SimpleDateFormat formatStrToData = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            try {
                dt = new Date();
                Date parse = formatStrToData.parse(creatTime);
                cal.setTime(parse);//用传递过来的String类型装载
                dt = parse;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        int year_date = cal.get(Calendar.YEAR);

        if (year == year_date) {
            return DateUtils.GetDateText(dt, "yyyy");
        }else {
            return "";
        }
    }
}
