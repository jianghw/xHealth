package com.kaurihealth.mvplib.mine_p;

import com.kaurihealth.datalib.request_bean.bean.CreditTransactionDisplayBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

import java.util.List;

/**
 * Created by mip on 2016/9/18.
 */
public interface IAccountDetailsView extends IMvpView{
   void getAmountDetail(List<CreditTransactionDisplayBean> been);
}
