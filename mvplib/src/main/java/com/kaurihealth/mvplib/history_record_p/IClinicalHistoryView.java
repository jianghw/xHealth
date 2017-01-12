package com.kaurihealth.mvplib.history_record_p;

import com.kaurihealth.datalib.response_bean.PatientRecordDisplayDto;
import com.kaurihealth.mvplib.base_p.IMvpView;

import java.util.List;

/**
 * Created by jianghw on 2016/9/19.
 * <p/>
 * Describe:
 */
public interface IClinicalHistoryView extends IMvpView {

    int getPatientId();

    String getCategory();

    void loadingIndicator(boolean flag);

    void loadAllPatientGenericRecordsSucceed(List<PatientRecordDisplayDto> bean);

    void loadAllPatientGenericRecordsError(String message);

    void setClinicalOutpatient(List<PatientRecordDisplayDto> list);

    void setHospitalRecords(List<PatientRecordDisplayDto> list);

    void setRecordAccessoryData(List<PatientRecordDisplayDto> list,int position);

    void setPhologyData(List<PatientRecordDisplayDto> list);

    void setLobTestData(List<PatientRecordDisplayDto> list,int position);

}
