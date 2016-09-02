package com.kaurihealth.kaurihealth.util;

import com.kaurihealth.datalib.request_bean.bean.RemoteConsultationRecordDetailDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.PatientRecordDisplayBean;
import com.kaurihealth.kaurihealth.util.Abstract.AbsMedicalRecordRemoteConsult;

/**
 * Created by 张磊 on 2016/6/24.
 * 介绍：修改远程医疗咨询
 */
public class MedicalRecordRemoteConsult extends AbsMedicalRecordRemoteConsult {
    public MedicalRecordRemoteConsult(PatientRecordDisplayBean displayBean) {
        super(displayBean);
    }


    @Override
    public void editCurrentHealthIssues(String currentHealthIssues) {
        try {
            getRemote().currentHealthIssues=currentHealthIssues;
        } catch (NoValueException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getCurrentHealthIssues() throws NoValueException {
        return getRemote().currentHealthIssues;
    }

    @Override
    public void editCurrentTreatment(String currentTreatment) {
        try {
            getRemote().currentTreatment=currentTreatment;
        } catch (NoValueException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getCurrentTreatment() throws NoValueException {
        return getRemote().currentTreatment;
    }

    @Override
    public void editIsPatientHealthRecordReviewed(boolean is) {
        try {
            getRemote().isPatientHealthRecordReviewed=is;
        } catch (NoValueException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean getIsPatientHealthRecordReviewed() throws NoValueException {
        return getRemote().isPatientHealthRecordReviewed;
    }

    @Override
    public void editPresentIllness(String presentIllness) {
        try {
            getRemote().presentIllness=presentIllness;
        } catch (NoValueException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getPresentIllness() throws NoValueException {
        return getRemote().presentIllness;
    }

    @Override
    public void editConsultComment(String consultComment) {
        try {
            getRemote().remoteConsultationDoctorComment=consultComment;
        } catch (NoValueException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getConsultComment() throws NoValueException {
        return getRemote().remoteConsultationDoctorComment;
    }

    private RemoteConsultationRecordDetailDisplayBean getRemote() throws NoValueException {
        if (displayBean.record != null && displayBean.record.remoteConsultationRecord != null) {
            return displayBean.record.remoteConsultationRecord;
        } else {
            throw new NoValueException();
        }
    }
}
