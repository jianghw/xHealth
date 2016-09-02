package com.kaurihealth.datalib.request_bean.bean;

import java.io.Serializable;

/**
 * Created by Nick on 21/07/2016.
 */
public class PriceDisplayBean implements Serializable {
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

    /// <summary>
    /// 产品的参数
    /// </summary>
    public ProductDisplayBean product;

}
