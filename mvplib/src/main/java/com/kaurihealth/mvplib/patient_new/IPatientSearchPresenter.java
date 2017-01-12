package com.kaurihealth.mvplib.patient_new;

import com.kaurihealth.mvplib.base_p.IMvpPresenter;

/**
 * Created by mip on 2017/1/4.
 */

public interface IPatientSearchPresenter<V> extends IMvpPresenter<V> {
    void loadingRemoteData(boolean first);
}
