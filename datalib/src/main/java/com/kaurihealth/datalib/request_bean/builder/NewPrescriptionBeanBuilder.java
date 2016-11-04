package com.kaurihealth.datalib.request_bean.builder;


import com.kaurihealth.datalib.request_bean.bean.NewPrescriptionDocumentBean;

/**
 * 版权所有者：刘正
 * 创建日期： 2016/4/19.
 * 备注：
 */
public class NewPrescriptionBeanBuilder {
//    public NewPrescriptionBean Build(int patientId, String date, String doctor, String comment, int departmentId, String hospital, List<NewDocumentDisplayBean> documents) {
//        NewPrescriptionBean newPrescriptionBean = new NewPrescriptionBean();
//        newPrescriptionBean.setPatientId(patientId);
//        newPrescriptionBean.setType("处方药");
//        newPrescriptionBean.setDate(date);
//        newPrescriptionBean.setPrescriptionDetail("无");
//        newPrescriptionBean.setDuration(0);
//        newPrescriptionBean.setDoctor(doctor);
//        newPrescriptionBean.setDepartmentId(departmentId);
//        newPrescriptionBean.setHospital(hospital);
//        newPrescriptionBean.setComment(comment);
//        newPrescriptionBean.setPrescriptionImages(documents);
//        return newPrescriptionBean;
//    }

    public NewPrescriptionDocumentBean Build(String url,int prescriptionId,String commet,String displayName,String DocumentFormat,String fileName){
        NewPrescriptionDocumentBean documentBean = new NewPrescriptionDocumentBean();
        documentBean.setDocumentUrl(url);
        documentBean.setPrescriptionId(prescriptionId);
        documentBean.setComment(commet);
        documentBean.setDisplayName(displayName);
        documentBean.setDocumentFormat(DocumentFormat);
        documentBean.setFileName(fileName);
        return documentBean;
    }
}
