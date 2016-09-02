package com.kaurihealth.datalib.request_bean.bean;

import java.io.Serializable;

/**
 * Created by Nick on 21/07/2016.
 */
public class ProductDisplayBean implements Serializable {
    /// <summary>
    /// 服务ID
    /// </summary>
    public int productId;

    /// <summary>
    /// 服务名
    /// </summary>
    public String productName;

    /// <summary>
    /// 服务类型（0：AdminProduct，1：DoctorDefaultProduct，2：DoctorCustomProduct）
    /// </summary>
    public String productType;

    /// <summary>
    /// 描述
    /// </summary>
    public String description;

    /// <summary>
    /// 价格的参数
    /// </summary>
    public PriceDisplayBean price;
}
