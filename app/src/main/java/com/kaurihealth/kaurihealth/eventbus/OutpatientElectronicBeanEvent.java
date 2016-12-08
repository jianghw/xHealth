package com.kaurihealth.kaurihealth.eventbus;

        import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
        import com.kaurihealth.datalib.response_bean.PatientRecordDisplayBean;

/**
 * Created by jianghw on 2016/9/12.
 * <p>
 * 描述：描述：传 patientRecordDisplayBean 给  OutpatientElectronicActivity
 */
public class OutpatientElectronicBeanEvent {
    private final DoctorPatientRelationshipBean mDoctorPatientRelationshipBean;
    private PatientRecordDisplayBean patientRecordDisplayBean;


    public OutpatientElectronicBeanEvent(PatientRecordDisplayBean patientRecordDisplayBean, DoctorPatientRelationshipBean shipBean) {
        this.patientRecordDisplayBean = patientRecordDisplayBean;
        this.mDoctorPatientRelationshipBean = shipBean;
    }

    public PatientRecordDisplayBean getPatientRecordDisplayBean() {
        return patientRecordDisplayBean;
    }

    public DoctorPatientRelationshipBean getDoctorPatientRelationshipBean() {
        return mDoctorPatientRelationshipBean;
    }
}
