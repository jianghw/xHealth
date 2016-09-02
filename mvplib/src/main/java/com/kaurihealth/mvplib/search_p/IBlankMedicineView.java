package com.kaurihealth.mvplib.search_p;

import com.kaurihealth.datalib.request_bean.bean.SearchDoctorDisplayBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

import java.util.List;

/**
 * Created by Garnet_Wu on 2016/8/30.
 */
public interface IBlankMedicineView  extends IMvpView {
    //设置数据
    void  setData(List<SearchDoctorDisplayBean> searchBeanslist);
    //清除数据
    void  clear();
}
