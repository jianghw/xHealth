package com.kaurihealth.kaurihealth.patientwithdoctor.util.Interface;

import java.util.Date;

/**
 * Created by 张磊 on 2016/6/8.
 * 介绍：
 */
public interface IIsActive {
    boolean isActive(boolean isActive, Date endDate);
}
