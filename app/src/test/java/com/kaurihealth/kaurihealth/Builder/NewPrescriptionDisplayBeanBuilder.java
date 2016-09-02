package com.kaurihealth.kaurihealth.Builder;

import com.kaurihealth.datalib.request_bean.bean.DoctorUserBean;

/**
 * 版权所有者：刘正
 * 创建日期： 2016/5/5.
 * 备注：
 */
public class NewPrescriptionDisplayBeanBuilder {
    public DoctorUserBean Builder() {
        DoctorUserBean doctorUserBean = new DoctorUserBean();
        doctorUserBean.email = "";
        doctorUserBean.firstName = "正";
        doctorUserBean.lastName = "刘";
        doctorUserBean.fullName = "刘正";
        doctorUserBean.gender = "男";
        doctorUserBean.dateOfBirth = "";
        doctorUserBean.avatar = "";
        return doctorUserBean;
    }
}
