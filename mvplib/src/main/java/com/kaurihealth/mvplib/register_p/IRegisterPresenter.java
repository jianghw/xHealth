package com.kaurihealth.mvplib.register_p;

import com.kaurihealth.mvplib.base_p.IMvpPresenter;

/**
 * Created by jianghw on 2016/8/16.
 * <p/>
 * 描述：
 */
public interface IRegisterPresenter<V> extends IMvpPresenter<V>{
    void sendVerificationCodeRequest();

}
