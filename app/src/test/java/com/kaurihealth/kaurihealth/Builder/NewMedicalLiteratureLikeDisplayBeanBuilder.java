package com.kaurihealth.kaurihealth.Builder;

import com.kaurihealth.datalib.request_bean.bean.NewMedicalLiteratureLikeDisplayBean;

/**
 * 版权所有者：刘正
 * 创建日期： 2016/6/27.
 * 备注：
 */
public class NewMedicalLiteratureLikeDisplayBeanBuilder {
    public NewMedicalLiteratureLikeDisplayBean Builder(int id) {
        NewMedicalLiteratureLikeDisplayBean newMedicalLiteratureLikeDisplayBean = new NewMedicalLiteratureLikeDisplayBean();
        newMedicalLiteratureLikeDisplayBean.medicalLiteratureId = id;
        return newMedicalLiteratureLikeDisplayBean;
    }
}
