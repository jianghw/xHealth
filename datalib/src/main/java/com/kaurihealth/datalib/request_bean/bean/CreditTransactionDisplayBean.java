package com.kaurihealth.datalib.request_bean.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by KauriHealth on 2016/7/15.
 */
public class CreditTransactionDisplayBean {
    public int creditTransactionId;
    public int userId;
    public double amount;
    public double currentTotal;
    public String type;
    public int orderId;
    public OrderDisplayBean order;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss", timezone="GMT")
    public Date date;
}
