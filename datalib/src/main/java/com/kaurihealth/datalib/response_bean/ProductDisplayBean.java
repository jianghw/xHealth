package com.kaurihealth.datalib.response_bean;

import com.litesuits.orm.db.annotation.Mapping;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.enums.AssignType;
import com.litesuits.orm.db.enums.Relation;

import java.io.Serializable;

/**
 * Created by Nick on 21/07/2016.
 医生提供服务的参数 {@link RelatedDoctorBean}
 */
public class ProductDisplayBean implements Serializable {
    // 指定自增，每个对象需要有一个主键
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int id;
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
    @Mapping(Relation.OneToOne)// 一对一
    public PriceDisplayBean price;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PriceDisplayBean getPrice() {
        return price;
    }

    public void setPrice(PriceDisplayBean price) {
        this.price = price;
    }
}
