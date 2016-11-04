package com.kaurihealth.mvplib.open_an_account_p;

import com.kaurihealth.datalib.request_bean.bean.NewRegistByDoctorBean;
import com.kaurihealth.datalib.response_bean.RegisterResponse;
import com.kaurihealth.mvplib.base_p.IMvpView;

/**
 * Created by Garnet_Wu on 2016/8/22.
 */
public interface IOpenAnAccountView  extends IMvpView {
    //得到   医生帮患者快速注册的bean  对象
    NewRegistByDoctorBean  getNewRegistByDoctorBean();

    void openAnAccountSuccess(RegisterResponse response);

}
