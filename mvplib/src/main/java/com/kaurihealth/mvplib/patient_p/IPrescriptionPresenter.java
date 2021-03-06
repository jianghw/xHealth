package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.mvplib.base_p.IMvpPresenter;

/**
 * Created by jianghw on 1/09/2016.
 * <p/>
 * Describe:
 */
public interface IPrescriptionPresenter<V> extends IMvpPresenter<V> {
    void loadingRemoteData(boolean isDirty);

    void loadDoctorPatientRelationshipForPatientId();
}
