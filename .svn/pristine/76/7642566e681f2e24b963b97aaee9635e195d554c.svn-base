package com.kaurihealth.mvplib.department_p;

import com.kaurihealth.datalib.response_bean.DepartmentDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

import java.util.List;

/**
 * Created by Garnet_Wu on 2016/9/6.
 */
public interface ISelectDepartmentLevel2View extends IMvpView {
        //刷新二级科室的ListView
        void    refreshListView(List<DepartmentDisplayBean> list);
        //错误提示
        void displayError(Throwable e);
        //为了得到当前的parentDepartmentId
        int getParentDepartmentId();

//        //获取DoctorDisplayBean对象
       // DoctorDisplayBean getDoctorDisplayBean();


    //回传bundle
       void returnBundle();


    DoctorDisplayBean getDoctorDisplayBean();



}
