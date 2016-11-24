package com.kaurihealth.mvplib.main_p;

import com.kaurihealth.datalib.response_bean.NotifyIsReadDisplayBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

/**
 * Created by jianghw on 2016/8/5.
 * <p/>
 * 描述：
 */
public interface IMessageView extends IMvpView {

    void loadingIndicator(boolean b);

    void loadConversationItemList();

    void isReadNotifySucceed(NotifyIsReadDisplayBean list);
}
