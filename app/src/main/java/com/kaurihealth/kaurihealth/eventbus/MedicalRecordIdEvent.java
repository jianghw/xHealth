package com.kaurihealth.kaurihealth.eventbus;

import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;

/**
 * Created by jianghw on 2016/8/24.
 * <p>
 * 描述：传 id 给  MedicalRecordActivity
 */
public class MedicalRecordIdEvent {

    private final int id;
    private final DoctorPatientRelationshipBean mPatientRelationshipBean;
    private final boolean isAble;

    public MedicalRecordIdEvent(int id, DoctorPatientRelationshipBean shipBean,boolean isAble) {
        this.mPatientRelationshipBean = shipBean;
        this.id = id;
        this.isAble = isAble;
    }

    public int getPatientId() {
        return id;
    }

    public DoctorPatientRelationshipBean getPatientRelationshipBean() {
        return mPatientRelationshipBean;
    }
    public boolean getIsAble(){
        return isAble;
    }
}
