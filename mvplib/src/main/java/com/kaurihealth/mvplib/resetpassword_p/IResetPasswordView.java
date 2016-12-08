package com.kaurihealth.mvplib.resetpassword_p;

import com.kaurihealth.datalib.request_bean.bean.ResetPasswordDisplayBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

/**
 * Created by Garnet_Wu on 2016/8/18.
 */
public interface IResetPasswordView extends IMvpView {

    ResetPasswordDisplayBean getResetPasswordDisplayBean();

}
