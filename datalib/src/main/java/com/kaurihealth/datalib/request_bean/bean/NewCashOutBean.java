package com.kaurihealth.datalib.request_bean.bean;

/**
 * Created by 张磊 on 2016/7/14.
 * 介绍：
 */
public class NewCashOutBean {
    public double amount;
    public String comment;
    public String cashOutMethod;
    public String cashOutMethodDetail;
    public int accountId;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCashOutMethod() {
        return cashOutMethod;
    }

    public void setCashOutMethod(String cashOutMethod) {
        this.cashOutMethod = cashOutMethod;
    }

    public String getCashOutMethodDetail() {
        return cashOutMethodDetail;
    }

    public void setCashOutMethodDetail(String cashOutMethodDetail) {
        this.cashOutMethodDetail = cashOutMethodDetail;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
}
