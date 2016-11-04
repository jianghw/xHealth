package com.kaurihealth.datalib.request_bean.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Nick on 21/04/2016.
 */
public class AddressDisplayBean implements Serializable {
    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    /// <summary>
    /// 地址ID
    /// </summary>
    private int addressId;

    /// <summary>
    /// 地址1
    /// </summary>
    public String addressLine1;

    /// <summary>
    ///地址2
    /// </summary>
    public String addressLine2;

    /// <summary>
    /// 地址3
    /// </summary>
    public String addressLine3;

    /// <summary>
    /// 市
    /// </summary>
    public String city;

    /// <summary>
    /// 省
    /// </summary>
    public String province;

    /// <summary>
    /// 开始时间
    /// </summary>

    public Date createdDate;

    /// <summary>
    /// 修改时间
    /// </summary>

    public Date modifiedDate;

    /// <summary>
    /// 邮政编码
    /// </summary>
    public String postcode;

    /// <summary>
    /// 区
    /// </summary>
    public String district;
}
