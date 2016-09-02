package com.kaurihealth.kaurihealth.patientwithdoctor.util.endtime;

import com.bugtags.library.Bugtags;
import com.kaurihealth.utilslib.date.RemainTimeBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 张磊 on 2016/4/29.
 * 介绍：
 */
public class CalculateEndTimeUtil {
    private static final int Second = 1000;
    private static final int Minute = Second * 60;
    private static final int Hour = 60 * Minute;
    private static final int Day = 24 * Hour;

    public static CalculateEndTimeInterface getCalculateEndTimeInterface(final String endTimeParam) {
        CalculateEndTimeInterface calculateEndTimeInterface = new CalculateEndTimeInterface() {
            private String endTime = endTimeParam;
            private Date date;

            {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                try {
                    date = formatter.parse(endTimeParam);
                } catch (ParseException e) {
                    e.printStackTrace();
                    Bugtags.sendException(e);
                    date = null;
                }
            }

            @Override
            public RemainTimeBean getEndTime() throws IllegalFormatTimeException {
                RemainTimeBean remainTimeBean = new RemainTimeBean(0, 0, 0, 0);
                if (date == null) {
                    throw new IllegalFormatTimeException("时间的格式不合法");
                }
                Calendar calendar = Calendar.getInstance();
                long remain = date.getTime() - calendar.getTime().getTime();
                transform(remain, remainTimeBean);
                return remainTimeBean;
            }

            @Override
            public void setEndTime(Date endTime) {
                date = endTime;
            }

        };
        return calculateEndTimeInterface;
    }

    public static CalculateEndTimeInterface getCalculateEndTimeInterface(final Date date) {
        CalculateEndTimeInterface calculateEndTimeInterface = new CalculateEndTimeInterface() {
            Date endDate = date;

            @Override
            public RemainTimeBean getEndTime() throws IllegalFormatTimeException {
                RemainTimeBean remainTimeBean = new RemainTimeBean(0, 0, 0, 0);
                if (endDate == null) {
                    throw new IllegalFormatTimeException("时间的格式不合法");
                }
                Calendar calendar = Calendar.getInstance();
                long remain = endDate.getTime() - calendar.getTime().getTime();
                transform(remain, remainTimeBean);
                return remainTimeBean;
            }

            @Override
            public void setEndTime(Date endTime) {
                this.endDate = endTime;
            }

        };
        return calculateEndTimeInterface;
    }

    private static void transform(long remain, RemainTimeBean remainTimeBean) {
        if (remain <= 0) {
            remainTimeBean.setDay(0);
            remainTimeBean.setHour(0);
            remainTimeBean.setMinutes(0);
            remainTimeBean.setSecond(0);
            return;
        }
        int dayRes = (int) (remain / Day);
        remainTimeBean.setDay(dayRes);
        long remainDay = remain % Day;
        if (remainDay == 0) {
            remainTimeBean.setHour(0);
            remainTimeBean.setMinutes(0);
            remainTimeBean.setSecond(0);
        } else {
            int hourRes = (int) (remainDay / Hour);
            remainTimeBean.setHour(hourRes);
            long remainHour = remainDay % Hour;
            if (remainHour == 0) {
                remainTimeBean.setMinutes(0);
            } else {
                int minuteRes = (int) (remainHour / Minute);
                remainTimeBean.setMinutes(minuteRes);
                long remainMinute = remainHour % Minute;
                if (remainMinute == 0) {
                    remainTimeBean.setSecond(0);
                } else {
                    int secondRes = (int) (remainMinute / Second);
                    remainTimeBean.setSecond(secondRes);
                }
            }
        }
    }
}
