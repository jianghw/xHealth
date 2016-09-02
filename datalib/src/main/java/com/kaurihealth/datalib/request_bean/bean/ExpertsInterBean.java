package com.kaurihealth.datalib.request_bean.bean;

/**
 * 版权所有者：刘正
 * 创建日期： 2016/3/24.
 * 备注：
 */
public interface ExpertsInterBean {

    //医生ID
    int doctorId();

    //医生名字
    String fullName();

    //医生图片
    String avatar();

    //医院名称
    String hospitalName();
}
