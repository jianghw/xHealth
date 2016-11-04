package com.kaurihealth.kaurihealth.util;

import com.kaurihealth.utilslib.date.DateUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by mip on 2016/11/1.
 */

public class WeekOfDayUtils {

    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();

        //判断是同一天的话就显示今天的时分
        if (dt.getDay() == cal.getTime().getDay()) {
            return DateUtils.GetDateText(dt, "HH:mm");
        } else if (dt.getDay() - cal.getTime().getDay() == 1) {
            return "昨天";
        } else if (dt.getDay() - cal.getTime().getDay() == 2) {
            return "前天";
        }
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
}
