package com.kaurihealth.mvplib.login_p;

import com.kaurihealth.mvplib.base_p.IMvpView;

/**
 * Created by jianghw on 2016/8/5.
 * <p>
 * 描述：
 */
public interface ILoginView extends IMvpView {

    String getUserName();

    String getPassword();

    void setUserName();

    void setPassword();

    void setImageHead();

    void loginSuccessful();

    void completeRegister();

    void loginError(Throwable e);

    void showSuccessToast();

    /**
     * 创建请求bean
     *
     * @param <T>
     * @return
     */
    <T> T createRequestBean();
}
