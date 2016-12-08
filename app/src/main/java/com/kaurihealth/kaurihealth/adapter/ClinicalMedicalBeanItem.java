package com.kaurihealth.kaurihealth.adapter;

import com.kaurihealth.datalib.response_bean.PatientRecordDisplayBean;

import java.util.List;

/**
 * 描述：
 * 修订日期:
 */
public class ClinicalMedicalBeanItem {
    private String title;
    private List<PatientRecordDisplayBean> list;


    public ClinicalMedicalBeanItem(String title, List<PatientRecordDisplayBean> list) {
        this.title = title;
        this.list = list;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<PatientRecordDisplayBean> getList() {
        return list;
    }

    public void setList(List<PatientRecordDisplayBean> list) {
        this.list = list;
    }
}
