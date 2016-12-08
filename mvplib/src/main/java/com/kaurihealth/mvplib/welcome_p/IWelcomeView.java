package com.kaurihealth.mvplib.welcome_p;

import com.kaurihealth.datalib.response_bean.TokenBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

/**
 * Created by jianghw on 2016/8/5.
 * <p>
 * 描述：
 */
public interface IWelcomeView extends IMvpView {
    //聊天功能
    void initChatKitOpen(TokenBean bean);

    void completeRegister();
}
