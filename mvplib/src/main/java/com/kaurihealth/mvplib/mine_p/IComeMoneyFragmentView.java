package com.kaurihealth.mvplib.mine_p;

import com.kaurihealth.datalib.request_bean.bean.CreditTransactionDisplayBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

import java.util.List;

/**
 * Created by mip on 2016/11/14.
 */

public interface IComeMoneyFragmentView extends IMvpView{
    void getBean(List<CreditTransactionDisplayBean> displayBeen);

}
