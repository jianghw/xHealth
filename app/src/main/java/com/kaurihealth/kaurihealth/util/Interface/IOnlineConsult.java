package com.kaurihealth.kaurihealth.util.Interface;

import com.kaurihealth.kaurihealth.util.NoValueException;

/**
 * Created by 张磊 on 2016/6/24.
 * 介绍：
 */
public interface IOnlineConsult {
    /**
     * 网络医疗原因
     */
    void editOnlineConsultReason(String reason);

    String getOnlineConsultReason() throws NoValueException;

    /**
     * 目前主要问题
     */
    void editCurMajorIssue(String curIssue);

    String getCurMajorIssue() throws NoValueException;
}
