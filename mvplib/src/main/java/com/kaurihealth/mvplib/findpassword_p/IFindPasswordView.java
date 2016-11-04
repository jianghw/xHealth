package com.kaurihealth.mvplib.findpassword_p;

import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

/**
 * Created by Garnet_Wu on 2016/8/15.
 */
public interface IFindPasswordView extends IMvpView {

    //获取验证码进行倒计时
    void startCountDown();


    //后添加- 获取验证码内容
    String getPhoneNumber();


    //后添加-获取验证码内容
    String getVersionCodeContent();

    void showPhoneNumberErrorMessage();

    void switchToActivity(ResponseDisplayBean responseDisplayBean);

}
