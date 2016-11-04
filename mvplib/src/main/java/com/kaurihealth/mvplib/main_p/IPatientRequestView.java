package com.kaurihealth.mvplib.main_p;

import com.kaurihealth.datalib.request_bean.bean.PatientRequestDisplayBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

import java.util.List;

/**
 * Created by mip on 2016/9/18.
 */
public interface IPatientRequestView extends IMvpView{

    //刷先首页的数据
    void refreshListView(List<PatientRequestDisplayBean> list);

    //点击listview中的某一个子项，跳转到该子项代表的activity中
    void goToActivity(PatientRequestDisplayBean patientRequestDisplayBean);

    void loadingIndicator(boolean flag);
}
