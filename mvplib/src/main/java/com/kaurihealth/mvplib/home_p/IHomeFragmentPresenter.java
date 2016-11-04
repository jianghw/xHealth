package com.kaurihealth.mvplib.home_p;

import com.kaurihealth.mvplib.base_p.IMvpPresenter;

/**
 * Created by KauriHealth on 2016/9/19.
 */
public interface IHomeFragmentPresenter<V> extends IMvpPresenter<V> {
    void loadPendingDoctorRelationships(boolean isDirty);

    void getPatientRequestNumber();

    void loadPatientRequestsByDoctor(boolean b);
}
