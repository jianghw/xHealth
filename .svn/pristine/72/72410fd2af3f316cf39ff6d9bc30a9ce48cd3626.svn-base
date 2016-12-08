package com.kaurihealth.kaurihealth.patient_v.health_condition;

import com.kaurihealth.datalib.request_bean.bean.HealthConditionDisplayBean;

import java.util.List;

/**
 * Created by 张磊 on 2016/4/28.
 * 介绍：
 */
public interface HealthyRecordInterface<T> {
    void add(T t);

    void edit(int index, String value);

    List<T> getAll();

    boolean isThisType(T t);

    String getType();

    void addNewOne(String value);

    List<HealthConditionDisplayBean> getListNeedToDelete();

    void setMode(Mode mode);

    void setListRes();

    T getValueAt(int position);
}
