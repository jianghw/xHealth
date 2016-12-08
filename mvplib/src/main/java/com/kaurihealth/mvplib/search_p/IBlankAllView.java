package com.kaurihealth.mvplib.search_p;

import com.kaurihealth.datalib.request_bean.bean.SearchDoctorDisplayBean;

import java.util.List;

/**
 * Created by Garnet_Wu on 2016/8/29.
 */
public interface IBlankAllView {
    void  setData(List<SearchDoctorDisplayBean> searchBeanlist);

    void  clear();
}
