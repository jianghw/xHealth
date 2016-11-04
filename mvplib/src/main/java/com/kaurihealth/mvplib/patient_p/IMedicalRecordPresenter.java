package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.datalib.response_bean.PatientRecordDisplayBean;
import com.kaurihealth.mvplib.base_p.IMvpPresenter;

import java.util.List;


public interface IMedicalRecordPresenter<V> extends IMvpPresenter<V> {

    void loadingRemoteData(boolean isDirty);

    /**
     * 弹出框
     *
     * @param date
     * @param time
     */
    void currentlySelectedByDialog(String date, String time);

    /**
     * 分发事件
     *
     * @param list
     */
    void groupDataByList(List<PatientRecordDisplayBean> list);

    /**
     * 过滤规则
     *
     * @param list
     * @param title
     */
    void listGroupFilter(List<PatientRecordDisplayBean> list, String title);

    /**
     * 事件回调
     *
     * @param beanList
     * @param title
     */
    void dataCallBack(List<PatientRecordDisplayBean> beanList, String title);
}
