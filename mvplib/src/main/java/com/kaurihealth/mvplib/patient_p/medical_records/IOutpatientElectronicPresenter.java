package com.kaurihealth.mvplib.patient_p.medical_records;

import com.kaurihealth.mvplib.base_p.IMvpPresenter;

/**
 * Created by jianghw on 2016/9/14.
 * <p>
 * 描述：
 */
public interface IOutpatientElectronicPresenter<V> extends IMvpPresenter<V> {
    void addNewPatientRecord();
}
