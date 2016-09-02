package com.kaurihealth.kaurihealth.util;

import com.kaurihealth.datalib.request_bean.bean.OnlineConsultationRecordDetailDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.PatientRecordDisplayBean;
import com.kaurihealth.kaurihealth.util.Abstract.AbsMedicalRecordOnlineConsult;

/**
 * Created by 张磊 on 2016/6/24.
 * 介绍：修改网络医疗咨询
 */
public class MedicalRecordOnlineConsult extends AbsMedicalRecordOnlineConsult {
    public MedicalRecordOnlineConsult(PatientRecordDisplayBean displayBean) {
        super(displayBean);
    }

    @Override
    public void editPresentIllness(String presentIllness) {
        try {
            getOnlineConsultationRecord().presentIllness = presentIllness;
        } catch (NoValueException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getPresentIllness() throws NoValueException {
        return getOnlineConsultationRecord().presentIllness;
    }

    @Override
    public void editConsultComment(String consultComment) {
        try {
            getOnlineConsultationRecord().onlineConsultationDoctorComment = consultComment;
        } catch (NoValueException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getConsultComment() throws NoValueException {
        return getOnlineConsultationRecord().onlineConsultationDoctorComment;
    }

    @Override
    public void editOnlineConsultReason(String reason) {
        try {
            getOnlineConsultationRecord().onlineConsultationPurpose = reason;
        } catch (NoValueException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getOnlineConsultReason() throws NoValueException {
        return getOnlineConsultationRecord().onlineConsultationPurpose;
    }

    @Override
    public void editCurMajorIssue(String curIssue) {
        try {
            getOnlineConsultationRecord().majorIssue = curIssue;
        } catch (NoValueException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getCurMajorIssue() throws NoValueException {
        return getOnlineConsultationRecord().majorIssue;
    }

    private OnlineConsultationRecordDetailDisplayBean getOnlineConsultationRecord() throws NoValueException {
        if (displayBean.record != null && displayBean.record.onlineConsultationRecord != null) {
            return displayBean.record.onlineConsultationRecord;
        } else {
            throw new NoValueException();
        }
    }
}
