package com.kaurihealth.mvplib.mine_p;

import com.kaurihealth.mvplib.base_p.IMvpPresenter;

import java.util.ArrayList;

/**
 * Created by Nick on 24/08/2016.
 */
public interface IDoctorDetailsPresenter<V> extends IMvpPresenter<V> {
    void updateAvatarDoctor(ArrayList<String> paths);
}
