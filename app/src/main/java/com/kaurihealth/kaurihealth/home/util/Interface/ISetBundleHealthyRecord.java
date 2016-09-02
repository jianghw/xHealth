package com.kaurihealth.kaurihealth.home.util.Interface;

import com.example.commonlibrary.widget.util.IGetBundle;

import java.util.Date;

/**
 * Created by 张磊 on 2016/6/8.
 * 介绍：
 */
public interface ISetBundleHealthyRecord extends IGetBundle {
    void setGender(String gender);

    void setName(String name);

    void setBirthDate(Date date);

    void setPatientId(int patientId);

    void setkauriHealthId(String kauriHealthId);

    void setAble(boolean isAble);
}
