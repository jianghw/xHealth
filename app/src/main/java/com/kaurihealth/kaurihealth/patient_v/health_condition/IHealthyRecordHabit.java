package com.kaurihealth.kaurihealth.patient_v.health_condition;

import android.view.View;

import java.util.List;

/**
 * Created by 张磊 on 2016/4/28.
 * 介绍：
 */
public interface IHealthyRecordHabit<T> {
    boolean isThisType(T t);

    void add(T t);

    List<T> getAll();


    View getView();
}
