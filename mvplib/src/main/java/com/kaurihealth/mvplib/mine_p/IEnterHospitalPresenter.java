package com.kaurihealth.mvplib.mine_p;

import com.kaurihealth.mvplib.base_p.IMvpPresenter;

import java.util.List;

/**
 * Created by Nick on 31/08/2016.
 */
public interface IEnterHospitalPresenter<V> extends IMvpPresenter<V> {
    void getAllHospitalName();
}
