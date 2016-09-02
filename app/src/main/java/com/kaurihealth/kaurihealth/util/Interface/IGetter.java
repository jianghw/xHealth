package com.kaurihealth.kaurihealth.util.Interface;


import com.example.commonlibrary.widget.bean.TokenBean;
import com.kaurihealth.datalib.request_bean.bean.DepartmentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.DepartmentDisplayWrapBean;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.PersonInfoBean;
import com.kaurihealth.datalib.request_bean.bean.ProductDisplayBean;
import com.kaurihealth.kaurihealth.util.NoValueException;

/**
 * Created by 张磊 on 2016/5/26.
 * 介绍：
 */
public interface IGetter {
    String getToken();

    TokenBean getTokenBean() throws IllegalAccessException;

    String getAccount();

    String getKauriHealthId();

    String getFullName();

    @Deprecated
    DepartmentDisplayBean[] getDepartments();

    DepartmentDisplayWrapBean[] getDepartmentDisplayWrapBean();

    String getAvatar();

    PersonInfoBean getPersonInfo();

    String getRefreshToken();

    int getUserId();

    DoctorDisplayBean getMyself();

    ProductDisplayBean getRemoteProduct();

    ProductDisplayBean getExclusiveProduct();

    double getTotalCredit();

    double getAvailableCredit();

    double getRegistPercentage();

    DepartmentDisplayBean getDepartment() throws NoValueException;

    String getHospitalName();

    String getNationalIdentity();

}
