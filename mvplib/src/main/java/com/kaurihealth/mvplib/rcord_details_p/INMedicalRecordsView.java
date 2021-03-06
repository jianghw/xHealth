package com.kaurihealth.mvplib.rcord_details_p;


import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.PatientRecordCountDisplayBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

/**
 * Created by jianghw on 2016/8/5.
 * <p/>
 * 描述：
 */
public interface INMedicalRecordsView extends IMvpView {

    int getPatientId();

    void loadingIndicator(boolean b);

    void loadNewPatientRecordCountsByPatientId(PatientRecordCountDisplayBean list);

    void loadNewPatientRecordCountsByPatientIdError(String message);

    void insertPatientSucceed(DoctorPatientRelationshipBean bean);
}
