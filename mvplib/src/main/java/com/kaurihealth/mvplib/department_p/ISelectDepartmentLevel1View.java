package com.kaurihealth.mvplib.department_p;

import com.kaurihealth.datalib.response_bean.DepartmentDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

import java.util.List;

/**
 * Created by Garnet_Wu on 2016/8/31.
 */
public interface ISelectDepartmentLevel1View extends IMvpView {
    //一级科室ListView 刷新数据
    void refreshListView(List<DepartmentDisplayBean> list);

    DoctorDisplayBean getDoctorDisplayBean();

    void returnBundle();
}
