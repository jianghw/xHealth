package com.kaurihealth.datalib.request_bean.bean;


import java.util.Date;

/**
 * Created by 张磊 on 2016/7/18.
 * 介绍：
 */
public class OrderDisplayBean {
    public int orderId;
    public int priceId;
    public Date createdDate;
    public Date effectedDate;
    public String status;
    public Date expiredDate;
    public double unitPrice;
    public int quantity;
    public double totalPrice;
    public String orderNumber;
    public PriceDisplayBean price;
    public PaymentDisplayBean payment;
    public String sellerName;
    public String buyerName;
    public String description;
    public int sellerId;
    public int buyerId;
    public PatientRequestDisplayBean patientRequest;
}
