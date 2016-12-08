package com.kaurihealth.mvplib.main_p;

import com.kaurihealth.mvplib.base_p.IMvpPresenter;

/**
 * Created by mip on 2016/9/18.
 */
public interface IPatientRequestPresenter<V> extends IMvpPresenter<V> {
    void loadDoctorDetail(boolean b);
}
