package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.mvplib.base_p.IMvpPresenter;

import java.util.Date;

/**
 * Created by jianghw on 2016/8/24.
 * <p/>
 * 描述：
 */
public interface IPatientInfoPresenter<V> extends IMvpPresenter<V> {
    /**
     * 开始倒计时
     * @param endDate
     */
    void startCountdown(Date endDate);
}
