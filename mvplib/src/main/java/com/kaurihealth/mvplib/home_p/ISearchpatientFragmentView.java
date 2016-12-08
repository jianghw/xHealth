package com.kaurihealth.mvplib.home_p;

import com.kaurihealth.datalib.request_bean.bean.SearchBooleanPatientBean;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

import java.util.List;

/**
 * Created by mip on 2016/9/19.
 */
public interface ISearchpatientFragmentView extends IMvpView {

    //得到当前的关键字
    String getEditTextContent();

    //处理数据
    void processingData(List<SearchBooleanPatientBean> list);

    void insertPatientSucceed(DoctorPatientRelationshipBean bean);

    //设置当前Edit的提示内容
    void setEditTextHintContent();
}
