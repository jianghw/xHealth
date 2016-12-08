package com.kaurihealth.datalib.request_bean.builder;


import com.kaurihealth.datalib.request_bean.bean.NewRegistByDoctorBean;

/**
 * Created by Nick on 27/05/2016.
 */
public class NewRegistByDoctorBeanBuilder {
    public NewRegistByDoctorBean Build(String userName , String firstName , String lastName, String gender, String dateOfBirth) {
        NewRegistByDoctorBean newRegistByDoctorBean = new NewRegistByDoctorBean();
        newRegistByDoctorBean.userName = userName;
        newRegistByDoctorBean.firstName = firstName;
        newRegistByDoctorBean.lastName = lastName;
        newRegistByDoctorBean.gender = gender;
        newRegistByDoctorBean.dateOfBirth = dateOfBirth;
        return newRegistByDoctorBean;
    }
}
