package com.kaurihealth.kaurihealth.eventbus;

import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;

/**
 * Created by jianghw on 2016/8/24.
 * <p/>
 * 描述：传 DoctorCooperationBean DoctorInfoActivity
 */
public class DoctorPatientRelationshipBeanEvent {
    /**
     * 接受 拒绝 等待 路人 添加 未知 本人
     */
    private final String type;
    private final DoctorPatientRelationshipBean bean;


    public DoctorPatientRelationshipBean getBean() {
        return bean;
    }

    public DoctorPatientRelationshipBeanEvent(DoctorPatientRelationshipBean bean, String type) {
        this.bean = bean;
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
