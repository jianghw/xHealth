package com.kaurihealth.kaurihealth.patientwithdoctor.util.endtime;

import com.kaurihealth.utilslib.date.RemainTimeBean;

import java.util.Date;

/**
 * Created by 张磊 on 2016/4/29.
 * 介绍：
 */
public interface CalculateEndTimeInterface {
    RemainTimeBean getEndTime() throws IllegalFormatTimeException;

    void setEndTime(Date endTime);
}
