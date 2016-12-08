package com.kaurihealth.utilslib.date;

/**
 * Created by 张磊 on 2016/4/29.
 * 介绍：
 */
public class RemainTimeBean {
    private int day;
    private int hour;
    private int minutes;
    private int second;

    public RemainTimeBean(int day, int hour, int minutes, int second) {
        this.day = day;
        this.hour = hour;
        this.minutes = minutes;
        this.second = second;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }
}
