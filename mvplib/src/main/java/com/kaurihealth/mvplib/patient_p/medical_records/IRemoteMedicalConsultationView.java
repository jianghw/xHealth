package com.kaurihealth.mvplib.patient_p.medical_records;

import com.kaurihealth.datalib.request_bean.bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.request_bean.bean.NewPatientRecordDisplayBean;
import com.kaurihealth.datalib.response_bean.PatientRecordDisplayBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

import java.util.ArrayList;

/**
 * Created by Garnet_Wu on 2016/9/18.
 */
public interface IRemoteMedicalConsultationView  extends IMvpView{

    //图片地址的集合
    ArrayList<String> getPathsList();

    //上传图片对应id
    String getKauriHealthId();

    //"添加"操作 ： 请求bean构造
    NewPatientRecordDisplayBean getNewPatientRecordDisplayBean();

    //更新界面：跳转到医疗记录界面
    void  updateSucceed(PatientRecordDisplayBean patientRecordDisplayBean);

    //医患关系 DoctorPatientRelationBean
    com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean getDoctorPatientRelationshipBean();

    //"保存"操作  向服务器提交数据  : 请求bean构造
    PatientRecordDisplayBean  getRequestPatientRecordBean();


    PatientRecordDisplayBean getPatientRecordBean();

}
