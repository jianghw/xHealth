package com.kaurihealth.kaurihealth.Builder;

import com.kaurihealth.utilslib.date.DateUtils;
import com.kaurihealth.datalib.request_bean.bean.NewRegistByDoctorBean;

/**
 * Created by Nick on 27/05/2016.
 */
public class NewRegistByDoctorBeanBuilder {
    public NewRegistByDoctorBean Build(String userName ) {
        NewRegistByDoctorBean newRegistByDoctorBean = new NewRegistByDoctorBean();
        newRegistByDoctorBean.userName = userName;
        newRegistByDoctorBean.firstName = "TestFirstName";
        newRegistByDoctorBean.lastName = "TestLastName";
        newRegistByDoctorBean.gender = "男";
        newRegistByDoctorBean.dateOfBirth = DateUtils.Today();
        return newRegistByDoctorBean;
    }
}
