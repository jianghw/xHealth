package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.mvplib.base_p.IMvpPresenter;

/**
 * Created by Nick on 1/09/2016.
 */
public interface IHealthConditionPresenter<V> extends IMvpPresenter<V> {

    void updataHealthRecord();

    void deleteHealthRecord();
}
