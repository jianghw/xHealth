package com.kaurihealth.mvplib.register_p;

import com.kaurihealth.mvplib.base_p.IMvpView;

/**
 * Created by jianghw on 2016/8/16.
 * <p/>
 * 描述：
 */
public interface IRegisterView extends IMvpView {
    String getPhoneNumber();

    String getPassword();

    String getVerificationCode();

    void showPhoneNumberErrorMessage();

    void startCountDown();
}
