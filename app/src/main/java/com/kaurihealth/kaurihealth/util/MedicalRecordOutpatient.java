package com.kaurihealth.kaurihealth.util;

import com.kaurihealth.datalib.request_bean.bean.OutpatientRecordDetailDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.PatientRecordDisplayBean;
import com.kaurihealth.kaurihealth.util.Abstract.AbsMedicalRecordOutpatient;

/**
 * Created by 张磊 on 2016/6/24.
 * 介绍：修改门诊记录电子病历
 */
public class MedicalRecordOutpatient extends AbsMedicalRecordOutpatient {
    public MedicalRecordOutpatient(PatientRecordDisplayBean displayBean) {
        super(displayBean);
    }

    @Override
    public void editPresentIllness(String presentIllness) {
        try {
            getOutPatientRecord().presentIllness = presentIllness;
        } catch (NoValueException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getPresentIllness() throws NoValueException {
        return getOutPatientRecord().presentIllness;
    }

    @Override
    public void editConsultComment(String consultComment) {
        try {
            getOutPatientRecord().treatmentAndPlan = consultComment;
        } catch (NoValueException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getConsultComment() throws NoValueException {
        return getOutPatientRecord().treatmentAndPlan;
    }

    @Override
    public void editPastIllnessReviewed(boolean is) {
        try {
            getOutPatientRecord().isPastIllnessReviewed = is;
        } catch (NoValueException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean getPastIllnessReviewed() throws NoValueException {
        return getOutPatientRecord().isPastIllnessReviewed;
    }

    @Override
    public void editPastIllnessUpToDate(boolean is) {
        try {
            getOutPatientRecord().isPastIllnessUpToDate = is;
        } catch (NoValueException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean getPastIllnessUpToDate() throws NoValueException {

        return getOutPatientRecord().isPastIllnessUpToDate;
    }

    @Override
    public void editCurrentMedicationReviewed(boolean is) {
        try {
            getOutPatientRecord().isCurrentMedicationReviewed = is;
        } catch (NoValueException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean getCurrentMedicationReviewed() throws NoValueException {
        return getOutPatientRecord().isCurrentMedicationReviewed;
    }

    @Override
    public void editCurrentMedicationUpToDate(boolean is) {
        try {
            getOutPatientRecord().isCurrentMedicationUpToDate = is;
        } catch (NoValueException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean getCurrentMedicationUpToDate() throws NoValueException {
        return getOutPatientRecord().isCurrentMedicationUpToDate;
    }

    @Override
    public void editHr(double value) {
        try {
            getOutPatientRecord().hrValue = value;
        } catch (NoValueException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Double getHr() throws NoValueException {
        if (getOutPatientRecord().hrValue == null) {
            throw new NoValueException();
        }
        return getOutPatientRecord().hrValue;
    }

    @Override
    public void editRr(double value) {
        try {
            getOutPatientRecord().rrValue = value;
        } catch (NoValueException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Double getRr() throws NoValueException {
        if (getOutPatientRecord().rrValue == null) {
            throw new NoValueException();
        }
        return getOutPatientRecord().rrValue;
    }

    @Override
    public void editUpperBp(double value) {
        try {
            getOutPatientRecord().upperBpValue = value;
        } catch (NoValueException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Double getUpperBp() throws NoValueException {
        if (getOutPatientRecord().upperBpValue == null) {
            throw new NoValueException();
        }
        return getOutPatientRecord().upperBpValue;
    }

    @Override
    public void editLowerBp(double value) {
        try {
            getOutPatientRecord().lowerBpValue = value;
        } catch (NoValueException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Double getLowerBp() throws NoValueException {
        if (getOutPatientRecord().lowerBpValue == null) {
            throw new NoValueException();
        }
        return getOutPatientRecord().lowerBpValue;
    }

    @Override
    public void editT(double value) {
        try {
            getOutPatientRecord().tValue = value;
        } catch (NoValueException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Double getT() throws NoValueException {
        if (getOutPatientRecord().tValue == null) {
            throw new NoValueException();
        }
        return getOutPatientRecord().tValue;
    }

    @Override
    public void editPhysicalExamination(String value) {
        try {
            getOutPatientRecord().physicalExamination = value;
        } catch (NoValueException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getPhysicalExamination() throws NoValueException {
        return getOutPatientRecord().physicalExamination;
    }

    @Override
    public boolean getIsLongTermMonitoringDataReviewed() throws NoValueException {
        return getOutPatientRecord().isLongTermMonitoringDataReviewed;
    }

    @Override
    public void editIsLongTermMonitoringDataReviewed(boolean value) {
        try {
            getOutPatientRecord().isLongTermMonitoringDataReviewed=value;
        } catch (NoValueException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean getIsWorkupReviewed() throws NoValueException {
        return getOutPatientRecord().isWorkupReviewed;
    }

    @Override
    public void editIsWorkupReviewed(boolean value) {
        try {
            getOutPatientRecord().isWorkupReviewed=value;
        } catch (NoValueException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getWorkupReviewComment() throws NoValueException {
        return getOutPatientRecord().workupReviewComment;
    }

    @Override
    public void editWorkupReviewComment(String value) {
        try {
            getOutPatientRecord().workupReviewComment=value;
        } catch (NoValueException e) {
            e.printStackTrace();
        }
    }

    private OutpatientRecordDetailDisplayBean getOutPatientRecord() throws NoValueException {
        if (displayBean.record != null && displayBean.record.outpatientRecord != null) {
            return displayBean.record.outpatientRecord;
        } else {
            throw new NoValueException();
        }
    }
}
