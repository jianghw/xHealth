package com.kaurihealth.kaurihealth.util.Interface;

import com.kaurihealth.kaurihealth.util.NoValueException;

/**
 * Created by 张磊 on 2016/6/24.
 * 介绍：
 */
public interface IRemote {
    /**
     * 目前主要问题
     */
    void editCurrentHealthIssues(String currentHealthIssues);

    String getCurrentHealthIssues() throws NoValueException;

    /**
     * 目前所接受治疗
     */
    void editCurrentTreatment(String currentTreatment);

    String getCurrentTreatment() throws NoValueException;

    /**
     * 患者医疗记录
     */
    void editIsPatientHealthRecordReviewed(boolean is);

    boolean getIsPatientHealthRecordReviewed() throws NoValueException;
}
