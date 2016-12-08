package com.kaurihealth.mvplib.search_p;

import com.kaurihealth.datalib.request_bean.bean.SearchDoctorDisplayBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

import java.util.List;

/**
 * Created by Garnet_Wu on 2016/8/29.
 */
public interface IBlankDoctorView  extends IMvpView {
    void setData(List<SearchDoctorDisplayBean> searchBeanslist);
    //清除数据
    void clear();
}
