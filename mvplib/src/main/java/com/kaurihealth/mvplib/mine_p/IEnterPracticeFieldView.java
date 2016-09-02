package com.kaurihealth.mvplib.mine_p;


import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

/**
 * Created by Nick on 30/08/2016.
 */
public interface IEnterPracticeFieldView extends IMvpView {
    void displayError(Throwable e);

    DoctorDisplayBean getDoctorDisplayBean();
}
