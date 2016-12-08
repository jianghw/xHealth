package com.kaurihealth.mvplib.main_p;

import com.kaurihealth.datalib.request_bean.bean.PatientRequestDisplayBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

import java.util.List;

/**
 * Created by Garnet_Wu on 2016/9/9.
 */
public interface IHomeView  extends IMvpView {
    //刷先首页的数据
    void refreshListView(List<PatientRequestDisplayBean> list);


    //点击listview中的某一个子项，跳转到该子项代表的activity中
   void goToActivity(PatientRequestDisplayBean patientRequestDisplayBean);

}