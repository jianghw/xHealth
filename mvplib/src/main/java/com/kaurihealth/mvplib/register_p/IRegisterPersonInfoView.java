package com.kaurihealth.mvplib.register_p;

import com.kaurihealth.datalib.request_bean.bean.DoctorUserBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

public interface IRegisterPersonInfoView extends IMvpView {

    String getFirstName();

    String getLastName();

    String getGender();

    String getBirthday();

    void connectLeanCloud();

    DoctorUserBean getDoctorUserBean();

}
