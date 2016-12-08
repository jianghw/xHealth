package com.kaurihealth.datalib.request_bean.builder;


import com.kaurihealth.datalib.request_bean.bean.NewCashOutAccountBean;

/**
 * Created by Nick on 2/08/2016.
 */
public class NewCashOutAccountBeanBuilder {
    public NewCashOutAccountBean Build(String nationalId , String accountType, String accountSource, String accountOwner, String accountDetail) {
        NewCashOutAccountBean accountBean = new NewCashOutAccountBean();
        accountBean.nationalId = nationalId;
        accountBean.accountType = accountType;
        accountBean.accountSource = accountSource;
        accountBean.accountOwner = accountOwner;
        accountBean.accountDetail = accountDetail;
        return accountBean;
    }
}
