package com.kaurihealth.mvplib.mine_p;

import com.kaurihealth.mvplib.base_p.IMvpPresenter;

/**
 * Created by Nick on 24/08/2016.
 */
public interface IPersonalDoctorServiceSettingPresenter<V> extends IMvpPresenter<V> {
    void updatePrice();

    void insertPrice();
}
