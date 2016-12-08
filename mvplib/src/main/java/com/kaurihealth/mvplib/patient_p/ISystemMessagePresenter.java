package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.mvplib.base_p.IMvpPresenter;

import java.util.List;

/**
 * Created by jianghw on 2016/10/24.
 */

public interface ISystemMessagePresenter<V> extends IMvpPresenter<V> {
    void updateUserNotify(List<Integer> beanList);
}
