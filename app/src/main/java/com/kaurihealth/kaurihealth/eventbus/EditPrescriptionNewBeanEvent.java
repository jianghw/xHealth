package com.kaurihealth.kaurihealth.eventbus;

import com.kaurihealth.datalib.request_bean.bean.PrescriptionBean;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;

/**
 * Created by mip on 2016/9/28.
 * 描述：给编辑处方传递bean
 */
public class EditPrescriptionNewBeanEvent {
    private final PrescriptionBean bean;
    private final DoctorPatientRelationshipBean doctorPatientRelationshipBean;
    private final boolean isAble;
    public EditPrescriptionNewBeanEvent(PrescriptionBean bean, DoctorPatientRelationshipBean doctorPatientRelationshipBean,boolean isAble) {
        this.bean = bean;
        this.doctorPatientRelationshipBean = doctorPatientRelationshipBean;
        this.isAble = isAble;
    }

    public PrescriptionBean getPrescriptionBean(){
        return bean;
    }

    public DoctorPatientRelationshipBean getDoctorPatientRelationshipBean(){
        return doctorPatientRelationshipBean;
    }

    public boolean getIsAble(){
        return isAble;
    }
}
