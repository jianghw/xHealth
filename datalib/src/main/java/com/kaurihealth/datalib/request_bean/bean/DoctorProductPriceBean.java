package com.kaurihealth.datalib.request_bean.bean;

/**
 * 版权:    张磊
 * 作者:    张磊
 * 描述：
 * 修订日期:
 */
public class DoctorProductPriceBean {
    private int PriceId;
    private int ProductId;
    private int DoctorId;
    private String Description;
    public boolean IsAvailable;
    private double UnitPrice;
    private Object Product = null;

    public DoctorProductPriceBean(int priceId, int productId, int doctorId, String description, boolean isAvailable, double unitPrice) {
        PriceId = priceId;
        ProductId = productId;
        DoctorId = doctorId;
        Description = description;
        IsAvailable = isAvailable;
        UnitPrice = unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        UnitPrice = unitPrice;
    }

    public void setIsAvailable(boolean isAvailable) {
        IsAvailable = isAvailable;
    }

    public int getPriceId() {
        return PriceId;
    }

    public int getProductId() {
        return ProductId;
    }

    public int getDoctorId() {
        return DoctorId;
    }

    public String getDescription() {
        return Description;
    }


    public double getUnitPrice() {
        return UnitPrice;
    }

    public Object getProduct() {
        return Product;
    }
}
