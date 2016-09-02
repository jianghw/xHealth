package com.kaurihealth.kaurihealth.patientwithdoctor.util.Interface;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by 张磊 on 2016/6/8.
 * 介绍：
 */
public class IsActive implements IIsActive {
    @Override
    public boolean isActive(boolean isActive, Date endDate) {
        boolean timeTag;
        if (endDate == null) {
            timeTag = true;
        } else {
            timeTag = overTime(endDate);
        }
        return timeTag && isActive;
    }

    boolean overTime(Date endDate) {
        return endDate.getTime() > Calendar.getInstance().getTime().getTime();
    }
}
