package com.kaurihealth.datalib.response_bean;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.enums.AssignType;

import java.io.Serializable;

/**
 * Created by Nick on 21/07/2016.
 *价格的参数  {@link ProductDisplayBean}
 */
public class PriceDisplayBean implements Serializable {
    // 指定自增，每个对象需要有一个主键
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int id;
    /// <summary>
    /// 价格ID
    /// </summary>
    public int priceId;

    /// <summary>
    /// 产品ID
    /// </summary>
    public int productId;

    /// <summary>
    /// 医生
    /// </summary>
    public int doctorId;

    /// <summary>
    /// 描述
    /// </summary>
    public String description;

    /// <summary>
    /// 是否可用
    /// </summary>
    public boolean isAvailable;

    /// <summary>
    /// 单价
    /// </summary>
    public double unitPrice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPriceId() {
        return priceId;
    }

    public void setPriceId(int priceId) {
        this.priceId = priceId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
