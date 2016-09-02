package com.kaurihealth.datalib.request_bean.builder;

import com.kaurihealth.datalib.request_bean.bean.DoctorUserBean;

/**
 * 版权所有者：刘正
 * 创建日期： 2016/5/5.
 * 备注：
 */
public class DoctorUserBeanBuilder {
    public DoctorUserBean Build(String firstName, String lastName, String gender, String dateOfBirth) {
        DoctorUserBean doctorUserBean = new DoctorUserBean();
        doctorUserBean.email = "";
        doctorUserBean.firstName = firstName;
        doctorUserBean.lastName = lastName;
        doctorUserBean.fullName = "";
        doctorUserBean.gender = gender;
        doctorUserBean.dateOfBirth =dateOfBirth ;
        doctorUserBean.avatar = "";
        return doctorUserBean;
    }
}
