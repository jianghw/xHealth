package com.kaurihealth.datalib.request_bean.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Nick on 21/04/2016.
 */
public class AddressDisplayBean implements Serializable {
    /// <summary>
    /// 地址ID
    /// </summary>
    public int addressId;

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
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss", timezone="GMT")
    public Date createdDate;

    /// <summary>
    /// 修改时间
    /// </summary>
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss", timezone="GMT")
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
