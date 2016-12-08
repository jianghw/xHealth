package com.kaurihealth.mvplib.mine_p;

import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

import java.util.List;

/**
 * Created by Nick on 31/08/2016.
 */
public interface IEnterHospitalView extends IMvpView {
    DoctorDisplayBean getDoctorDisplayBean();

    //获得所有医院的信息
    void initAllHospitalList();

    //ListView 刷新数据
    void refreshListView(List<String> data);
}
