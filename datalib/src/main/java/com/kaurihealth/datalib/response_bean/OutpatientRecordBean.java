package com.kaurihealth.datalib.response_bean;

/**
 * Created by jianghw on 2016/9/8.
 * <p>
 * 描述：{@link RecordBean}
 * 门诊记录的参数
 */
public class OutpatientRecordBean {  //OutpatientRecordDetailDisplayDto
    private int outPatientRecordId; //门诊记录ID
    private String presentIllness;  //目前的病情
    private boolean isPastIllnessReviewed;   //是否已阅过去的疾病
    private boolean isPastIllnessUpToDate;  //是否更新已阅的疾病
    private double hrValue; //HR
    private double upperBpValue; //UpperBp
    private double lowerBpValue;  //LowerBp
    private double rrValue; //Rr
    private double tValue; //TV
    private String physicalExamination;  //身体检查
    private boolean isWorkupReviewed; //是否处理审查

    private boolean isCurrentMedicationReviewed; //是否审查最新的药物
    private boolean isCurrentMedicationUpToDate;  //是否已经更新最新的药物
    private boolean isLongTermMonitoringDataReviewed;//是否审查监测数据
    private String workupReviewComment; //后处理审校意见
    private String treatmentAndPlan; //治疗计划
    private int recordId; //病例ID

    public int getOutPatientRecordId() {
        return outPatientRecordId;
    }

    public void setOutPatientRecordId(int outPatientRecordId) {
        this.outPatientRecordId = outPatientRecordId;
    }

    public String getPresentIllness() {
        return presentIllness;
    }

    public void setPresentIllness(String presentIllness) {
        this.presentIllness = presentIllness;
    }

    public boolean isIsPastIllnessReviewed() {
        return isPastIllnessReviewed;
    }

    public void setIsPastIllnessReviewed(boolean isPastIllnessReviewed) {
        this.isPastIllnessReviewed = isPastIllnessReviewed;
    }

    public boolean isIsPastIllnessUpToDate() {
        return isPastIllnessUpToDate;
    }

    public void setIsPastIllnessUpToDate(boolean isPastIllnessUpToDate) {
        this.isPastIllnessUpToDate = isPastIllnessUpToDate;
    }

    public double getHrValue() {
        return hrValue;
    }

    public void setHrValue(double hrValue) {
        this.hrValue = hrValue;
    }

    public double getUpperBpValue() {
        return upperBpValue;
    }

    public void setUpperBpValue(double upperBpValue) {
        this.upperBpValue = upperBpValue;
    }

    public double getLowerBpValue() {
        return lowerBpValue;
    }

    public void setLowerBpValue(double lowerBpValue) {
        this.lowerBpValue = lowerBpValue;
    }

    public double getRrValue() {
        return rrValue;
    }

    public void setRrValue(double rrValue) {
        this.rrValue = rrValue;
    }

    public double getTValue() {
        return tValue;
    }

    public void setTValue(double tValue) {
        this.tValue = tValue;
    }

    public String getPhysicalExamination() {
        return physicalExamination;
    }

    public void setPhysicalExamination(String physicalExamination) {
        this.physicalExamination = physicalExamination;
    }

    public boolean isIsWorkupReviewed() {
        return isWorkupReviewed;
    }

    public void setIsWorkupReviewed(boolean isWorkupReviewed) {
        this.isWorkupReviewed = isWorkupReviewed;
    }

    public boolean isIsCurrentMedicationReviewed() {
        return isCurrentMedicationReviewed;
    }

    public void setIsCurrentMedicationReviewed(boolean isCurrentMedicationReviewed) {
        this.isCurrentMedicationReviewed = isCurrentMedicationReviewed;
    }

    public boolean isIsCurrentMedicationUpToDate() {
        return isCurrentMedicationUpToDate;
    }

    public void setIsCurrentMedicationUpToDate(boolean isCurrentMedicationUpToDate) {
        this.isCurrentMedicationUpToDate = isCurrentMedicationUpToDate;
    }

    public boolean isIsLongTermMonitoringDataReviewed() {
        return isLongTermMonitoringDataReviewed;
    }

    public void setIsLongTermMonitoringDataReviewed(boolean isLongTermMonitoringDataReviewed) {
        this.isLongTermMonitoringDataReviewed = isLongTermMonitoringDataReviewed;
    }

    public String getWorkupReviewComment() {
        return workupReviewComment;
    }

    public void setWorkupReviewComment(String workupReviewComment) {
        this.workupReviewComment = workupReviewComment;
    }

    public String getTreatmentAndPlan() {
        return treatmentAndPlan;
    }

    public void setTreatmentAndPlan(String treatmentAndPlan) {
        this.treatmentAndPlan = treatmentAndPlan;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }
}
