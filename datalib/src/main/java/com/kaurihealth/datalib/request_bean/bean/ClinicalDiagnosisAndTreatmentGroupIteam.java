package com.kaurihealth.datalib.request_bean.bean;

import android.app.Activity;



import java.util.List;

/**
 * 版权:    张磊
 * 作者:    张磊
 * 描述：
 * 修订日期:
 */
public class ClinicalDiagnosisAndTreatmentGroupIteam {
    private String title;
    private List<PatientRecordDisplayBean> list;
    Class<? extends Activity> purpose;

    public ClinicalDiagnosisAndTreatmentGroupIteam(String title, Class<? extends Activity> purpose) {
        this.title = title;
        this.purpose = purpose;
    }

    public ClinicalDiagnosisAndTreatmentGroupIteam(String title) {
        this.title = title;
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

    public Class<? extends Activity> getPurpose() {
        return purpose;
    }

    public void setPurpose(Class<? extends Activity> purpose) {
        this.purpose = purpose;
    }
}
