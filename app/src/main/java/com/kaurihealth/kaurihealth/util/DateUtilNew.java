package com.kaurihealth.kaurihealth.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 张磊 on 2016/7/21.
 * 介绍：
 */
public class DateUtilNew {
    private final SimpleDateFormat todayFormat;
    private final SimpleDateFormat yesterdayFormat;
    private final SimpleDateFormat curyearFormat;
    private final SimpleDateFormat otherFormat;
    Date curDate = Calendar.getInstance().getTime();

    public DateUtilNew(String formatTodayStr, String formatYesterdayStr, String formatCurYearStr, String otherFormatStr) {
        todayFormat = new SimpleDateFormat(formatTodayStr);
        yesterdayFormat = new SimpleDateFormat(formatYesterdayStr);
        curyearFormat = new SimpleDateFormat(formatCurYearStr);
        otherFormat = new SimpleDateFormat(otherFormatStr);
    }

    public static String getDataText(Date date) {
        String result = null;

        return result;
    }

    private void notifyDate() {
        curDate = Calendar.getInstance().getTime();
    }

    private boolean isToday(Date date) {
        notifyDate();

        return false;
    }

    private boolean isYesterDay() {
        return false;
    }

    private boolean isCurYear() {
        return false;
    }
}
