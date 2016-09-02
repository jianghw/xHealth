package com.kaurihealth.kaurihealth.util.Interface;

import com.kaurihealth.kaurihealth.util.NoValueException;

/**
 * Created by 张磊 on 2016/6/24.
 * 介绍：
 */
public interface IOutPatient {
    //既往病史

    /**
     * 修改患者系统病历记录已阅
     */
    void editPastIllnessReviewed(boolean is);

    boolean getPastIllnessReviewed() throws NoValueException;

    /**
     * 修改患者系统病历记录已更新
     */
    void editPastIllnessUpToDate(boolean is);

    boolean getPastIllnessUpToDate() throws NoValueException;
    //修改目前使用药物

    /**
     * 修改系统病历记录已阅
     */
    void editCurrentMedicationReviewed(boolean is);

    boolean getCurrentMedicationReviewed() throws NoValueException;

    /**
     * 修改系统病历记录已更新
     */
    void editCurrentMedicationUpToDate(boolean is);

    boolean getCurrentMedicationUpToDate() throws NoValueException;

    // 修改体格检查
    void editHr(double value);

    Double getHr() throws NoValueException;

    void editRr(double value);

    Double getRr() throws NoValueException;

    void editUpperBp(double value);

    Double getUpperBp() throws NoValueException;

    void editLowerBp(double value);

    Double getLowerBp() throws NoValueException;

    void editT(double value);

    Double getT() throws NoValueException;

    void editPhysicalExamination(String value);

    String getPhysicalExamination() throws NoValueException;


    /**
     * 辅助检查
     */
    //患者长期检测指标已阅
    boolean getIsLongTermMonitoringDataReviewed() throws NoValueException;

    void editIsLongTermMonitoringDataReviewed(boolean value);

    //患者系统医疗记录辅助检查已阅
    boolean getIsWorkupReviewed() throws NoValueException;

    void editIsWorkupReviewed(boolean value);

    //辅助检查文本
    String getWorkupReviewComment() throws NoValueException;

    void editWorkupReviewComment(String value);

}
