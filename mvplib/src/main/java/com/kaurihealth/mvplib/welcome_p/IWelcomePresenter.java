package com.kaurihealth.mvplib.welcome_p;

import com.kaurihealth.mvplib.base_p.IMvpPresenter;

/**
 * Created by jianghw on 2016/8/12.
 * <p/>
 * 描述：
 */
public interface IWelcomePresenter<V> extends IMvpPresenter<V> {
    //我 医生详情
    void loadDoctorDetail();

    //好友关系列表
    void loadContactListByDoctorId();

    void delayAuto();
}
