package com.kaurihealth.mvplib.register_p;

        import com.kaurihealth.datalib.response_bean.RegisterResponse;
        import com.kaurihealth.mvplib.base_p.IMvpPresenter;

/**
 * Created by jianghw on 2016/8/16.
 * <p/>
 * 描述：
 */
public interface IRegisterPresenter<V> extends IMvpPresenter<V> {
    void sendVerificationCodeRequest();

    void saveTokenToDatabase(RegisterResponse response);
}
