package com.kaurihealth.mvplib.home_p;

import com.kaurihealth.mvplib.base_p.IMvpPresenter;

/**
 * Created by mip on 2016/9/19.
 */
public interface IDoctorRequestPresenter<V> extends IMvpPresenter<V> {
    void loadDoctorDetail(boolean b);
}
