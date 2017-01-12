package com.kaurihealth.mvplib.add_record_p;

import com.kaurihealth.datalib.response_bean.PatientRecordDisplayDto;
import com.kaurihealth.mvplib.base_p.IMvpView;

import java.util.List;

/**
 * Created by jianghw on 2016/9/19.
 * <p/>
 * Describe:
 */
public interface IAddOutpatientView extends IMvpView {

    int getPatientId();

    String getCategory();

    void loadingIndicator(boolean flag);

    void loadAllPatientGenericRecordsSucceed(List<PatientRecordDisplayDto> bean);

    void loadAllPatientGenericRecordsError(String message);
}
