package com.kaurihealth.mvplib.home_p;

import com.kaurihealth.mvplib.base_p.IMvpPresenter;

/**
 * Created by mip on 2016/10/24.
 */

public interface IReferralPatientRequestPresenter<V> extends IMvpPresenter<V> {

    void loadReferralDetail(boolean b);

    void loadDoctorPatientRelationshipForPatientId(int patientId);
}
