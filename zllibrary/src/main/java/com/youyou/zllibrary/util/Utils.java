package com.youyou.zllibrary.util;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 张磊 on 2016/1/14.
 * 介绍：
 */
public class Utils {


    /**
     * 检测字符串中是否有中文
     *
     * @param content
     * @return true:有字符串 false:没有字符串
     */
    public static boolean hasChinese(String content) {
        char[] chars = content.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (isChinese(chars[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否是中文字符
     *
     * @param ch
     * @return true:是中文字符  false:不是中文字符
     */
    private static boolean isChinese(char ch) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(ch);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }

    /***
     * 根据日期计算年龄
     *
     * @param data
     * @return
     */
    public static int calculateAge(String data) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date;
        try {
            date = format.parse(data);
            return calculateAge(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }

    }

    /**
     * 将String 转化成 Date
     *
     * @param data
     * @return
     */
    public static Date stringToDate(String data) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date;
        try {
            date = format.parse(data);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("stringToDate", e.getMessage());
            return null;
        }
    }

    public static Date myStringtoDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            return format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int calculateAge(@NonNull Date date) {
        if (date == null) {
            return 0;
        }
        Calendar calendarBefore = Calendar.getInstance();
        calendarBefore.setTime(date);
        Calendar calendarCur = Calendar.getInstance();
        if (calendarBefore.get(Calendar.YEAR) == calendarCur.get(Calendar.YEAR)) {
            return 0;
        } else {
            if (calendarBefore.get(Calendar.MONTH) < calendarCur.get(Calendar.MONTH)) {
                return calendarCur.get(Calendar.YEAR) - calendarBefore.get(Calendar.YEAR);
            } else if (calendarBefore.get(Calendar.MONTH) > calendarCur.get(Calendar.MONTH)) {
                return calendarCur.get(Calendar.YEAR) - calendarBefore.get(Calendar.YEAR) - 1;
            } else {
                if (calendarCur.get(Calendar.DAY_OF_MONTH) >= calendarBefore.get(Calendar.DAY_OF_MONTH)) {
                    return calendarCur.get(Calendar.YEAR) - calendarBefore.get(Calendar.YEAR);
                } else {
                    return calendarCur.get(Calendar.YEAR) - calendarBefore.get(Calendar.YEAR) - 1;
                }
            }
        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public static String getFormatDate(Date date) {
        return format.format(date);
    }
}
