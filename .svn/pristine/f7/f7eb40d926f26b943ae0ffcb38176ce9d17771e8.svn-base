package com.kaurihealth.kaurihealth.eventbus;

import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;

/**
 * Created by jianghw on 2016/8/24.
 * <p>
 * 描述：传 id 给  LongHealthyStandardActivity
 */
public class LongHStandardBeanEvent {

    private final DoctorPatientRelationshipBean bean;
    private final int id;
    private final boolean isAble;
    public LongHStandardBeanEvent(int id,boolean isAble,DoctorPatientRelationshipBean bean) {
        this.id = id;
        this.isAble = isAble;
        this.bean = bean;
    }

    public int getPatientId() {
        return id;
    }

    public boolean getIsAble(){
        return isAble;
    }

    public DoctorPatientRelationshipBean getBean(){
        return bean;
    }
}
