package com.kaurihealth.mvplib.patient_p.medical_records;

import com.kaurihealth.mvplib.base_p.IMvpPresenter;

/**
 * Created by mip on 2016/9/29.
 */
public interface IAddAndEditLobTestPresenter<V> extends IMvpPresenter<V> {
    void addNewPatientRecord();
}
