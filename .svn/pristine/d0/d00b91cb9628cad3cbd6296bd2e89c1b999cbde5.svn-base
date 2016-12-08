package com.kaurihealth.datalib.request_bean.builder;


import com.kaurihealth.datalib.request_bean.bean.NewCashOutBean;

/**
 * Created by Nick on 29/07/2016.
 */
public class NewCashOutBeanBuilder {
    public NewCashOutBean Build(int userCashOutAccountId , double amount , String accountDetail) {
        NewCashOutBean newCashOutBean = new NewCashOutBean();
        newCashOutBean.accountId = userCashOutAccountId;
        newCashOutBean.amount = amount;
        newCashOutBean.cashOutMethod = "银行";
        newCashOutBean.cashOutMethodDetail = accountDetail;
        newCashOutBean.comment = "";
        return newCashOutBean;
    }
}
