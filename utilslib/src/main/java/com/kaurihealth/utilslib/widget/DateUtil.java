package com.kaurihealth.utilslib.widget;

import org.joda.time.DateTime;
import org.joda.time.Years;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 版权所有者：刘正
 * 创建日期： 2016/4/19.
 * 备注：
 */
public class DateUtil {

    public static String Today() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        return format.format(Calendar.getInstance().getTime());
    }

    public static String GetNowDate(String dateFormat) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        return format.format(Calendar.getInstance().getTime());
    }

    public static String GetDateText(Date date, String dateFormat) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        return format.format(date);
    }

    public static Date GetDateTextToParse(String date, String dateFormat) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        try {
            return format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String GetDateText(Date date) {
        if (date == null) {
            return null;
        }
        return GetDateText(date, "yyyy-MM-dd'T'HH:mm:ss");
    }

    public static int GetAge(Date dateOfBirth) {
        DateTime now = new DateTime(Calendar.getInstance().getTime());
        DateTime dob = new DateTime(dateOfBirth);
        return Years.yearsBetween(dob, now).getYears();
    }


}
