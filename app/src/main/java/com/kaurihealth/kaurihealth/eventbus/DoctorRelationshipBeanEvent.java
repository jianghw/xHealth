package com.kaurihealth.kaurihealth.eventbus;

import com.kaurihealth.datalib.response_bean.DoctorRelationshipBean;

/**
 * Created by jianghw on 2016/8/24.
 * <p/>
 * 描述：传 DoctorCooperationBean DoctorInfoActivity
 */
public class DoctorRelationshipBeanEvent {

    private final DoctorRelationshipBean bean;

    private final boolean mBoolean;

    private final String mark;

    public DoctorRelationshipBeanEvent(DoctorRelationshipBean bean,boolean aBoolean,String mark) {
        this.bean = bean;
        this.mBoolean = aBoolean;
        this.mark = mark;
    }

    public DoctorRelationshipBean getBean() {
        return bean;
    }

    public boolean getBoolean(){
        return mBoolean;
    }

    public String getMark(){
        return mark;
    }
}
