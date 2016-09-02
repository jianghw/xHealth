package com.kaurihealth.kaurihealth.util.Interface;

import com.kaurihealth.kaurihealth.util.NoValueException;

/**
 * Created by 张磊 on 2016/6/24.
 * 介绍：
 */
public interface IMedicalRecordControllerCommon {
    /**
     * 主诉/现病史
     */
    void editPresentIllness(String presentIllness);

    String getPresentIllness() throws NoValueException;

    /**
     * 印象/咨询建议
     */
    void editConsultComment(String consultComment);

    String getConsultComment() throws NoValueException;

}
