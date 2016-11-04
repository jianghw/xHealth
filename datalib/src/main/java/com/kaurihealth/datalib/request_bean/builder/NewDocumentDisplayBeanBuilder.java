package com.kaurihealth.datalib.request_bean.builder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaurihealth.datalib.request_bean.bean.NewDocumentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.PrescriptionBean;

import java.util.List;


/**
 * 版权所有者：刘正
 * 创建日期： 2016/4/19.
 * 备注：
 */
public class NewDocumentDisplayBeanBuilder {
    public NewDocumentDisplayBean Build(String documentUrl, String fileName, String displayName, String documentFormat, String comment) {
        NewDocumentDisplayBean newDocumentDisplayBean = new NewDocumentDisplayBean();
        newDocumentDisplayBean.setDocumentUrl(documentUrl);
        newDocumentDisplayBean.setFileName(fileName);
        newDocumentDisplayBean.setDisplayName(displayName);
        newDocumentDisplayBean.setDocumentFormat(documentFormat);
        newDocumentDisplayBean.setComment(comment);
        return newDocumentDisplayBean;
    }


    public List<NewDocumentDisplayBean> Build(String response) {
        List<NewDocumentDisplayBean> listNewDocumentDisplayBean = new Gson().fromJson(response,
                new TypeToken<List<NewDocumentDisplayBean>>() {
                }.getType());
        return listNewDocumentDisplayBean;
    }

    public List<PrescriptionBean.PrescriptionDocumentsEntity> Builder(int prescriptionId, String response) {
        List<PrescriptionBean.PrescriptionDocumentsEntity> listNewDocumentDisplayBean = new Gson().fromJson(response,
                new TypeToken<List<PrescriptionBean.PrescriptionDocumentsEntity>>() {
                }.getType());
        return listNewDocumentDisplayBean;
    }

//      public List<RecordDocumentDisplayBean> BuildPatientRecordDisplayBeans(String response, int patientRecordId) {
//            List<RecordDocumentDisplayBean> list = new LinkedList<>();
//            List<NewDocumentDisplayBean> newDocumentDisplayBeen = JSON.parseArray(response, NewDocumentDisplayBean.class);
//            for (NewDocumentDisplayBean iteam : newDocumentDisplayBeen) {
//                RecordDocumentDisplayBean bean = new RecordDocumentDisplayBean();
//                bean.isDeleted = false;
//                bean.recordDocumentId = 0;
//                bean.comment = "";
//                bean.displayName = iteam.displayName;
//                bean.documentFormat = iteam.documentFormat;
//                bean.documentUrl = iteam.documentUrl;
//                bean.fileName = iteam.fileName;
//                bean.patientRecordId = patientRecordId;
//                list.add(bean);
//            }
//            return list;
//    }
}
