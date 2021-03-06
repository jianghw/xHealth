package com.kaurihealth.mvplib.login_p;

import com.kaurihealth.datalib.request_bean.bean.LoginBean;
import com.kaurihealth.datalib.response_bean.TokenBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

/**
 * Created by jianghw on 2016/8/5.
 * <p>
 * 描述：
 */
public interface ILoginView extends IMvpView {

    String getUserName();

    String getPassword();

    void completeRegister();

    void loginError(Throwable e);

    void showSuccessToast();

    //将用户名用sharedPerference存储起来
    void saveUsername();

    /**
     * 创建请求bean
     */
    LoginBean createRequestBean();

    void setLoginBtnEnable(boolean flag);

    void initChatKitOpen(TokenBean tokenBean);

    //提示"电话号码输入有错"
    void showPhoneNumberErrorMessage();

    //获取验证码进行倒计时
    void startCountDown();

    void unauthorizedPersonnel(TokenBean bean);
}
