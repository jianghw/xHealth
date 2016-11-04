package com.kaurihealth.mvplib.mine_p;

import com.kaurihealth.datalib.request_bean.bean.NewCashOutAccountBean;
import com.kaurihealth.datalib.response_bean.UserCashOutAccountDisplayBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

/**
 * Created by mip on 2016/9/14.
 */
public interface IAddBankCardView extends IMvpView{
    NewCashOutAccountBean getNewCashOutAccountBean();

    void insertCashSucceed(UserCashOutAccountDisplayBean userCashOutAccountDisplayBean);
}
