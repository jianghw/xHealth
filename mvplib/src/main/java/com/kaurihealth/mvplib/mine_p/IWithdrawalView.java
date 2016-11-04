package com.kaurihealth.mvplib.mine_p;

import com.kaurihealth.datalib.request_bean.bean.NewCashOutBean;
import com.kaurihealth.datalib.response_bean.UserCashOutAccountDisplayBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

import java.util.List;

/**
 * Created by Nick on 24/08/2016.
 */
public interface IWithdrawalView extends IMvpView {
    NewCashOutBean getNewCashOutBean();
    //Activciy 上获得handler 对象
    void startNewCashSucceed();

    void  createAccountDialog(List<UserCashOutAccountDisplayBean> list, String[] strings);
}
