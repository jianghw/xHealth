package com.kaurihealth.kaurihealth.util.Interface;

import com.kaurihealth.datalib.request_bean.bean.DepartmentDisplayBean;
import com.example.commonlibrary.widget.bean.TokenBean;
import com.kaurihealth.datalib.request_bean.bean.DepartmentDisplayWrapBean;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.PersonInfoBean;

/**
 * Created by 张磊 on 2016/5/26.
 * 介绍：
 */
public interface IPutter {

    void setTokenBean(TokenBean tokenBean);

    void setMyself(DoctorDisplayBean doctorDisplayBean);

    @Deprecated
    void setDepartment(DepartmentDisplayBean[] departments);

    void setDepartment(DepartmentDisplayWrapBean[] departments);

    void setPersonInfo(PersonInfoBean personInfo);

    void setToken(String token);

    void setName(String firstName, String secondName);

    void setExclusiveProduct(boolean isAvailable, double unitPrice);

    void setRemoteProduct(boolean isAvailable, double unitPrice);

    void setRegistPercentage(String registPercentage);

    void reduceTotalCredit(double amount);

    void reduceAvailableCredit(double amount);


}
