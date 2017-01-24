package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.FamilyMemberBean;
import com.kaurihealth.mvplib.base_p.IMvpPresenter;

import java.util.List;

/**
 * Created by Nick on 22/09/2016.
 */
public interface IFamilyMembersPresenter<V> extends IMvpPresenter<V> {
    //查询已经和本人成为医患关系的患者
    void showAlreadyPatient(List<FamilyMemberBean> familyMemberList);

    //手动处理数据
    void manualProcessingData(List<FamilyMemberBean> familyMemberList);

    //转包装类, isPatient字段封装
    void   pateintProcessingData(List<FamilyMemberBean> familyMemberList,List<DoctorPatientRelationshipBean> list);

    //点击"添加患者" 按钮
    void   insertNewRelationshipByDoctor(int patientId);

    void loadingRemoteData(boolean isDirty);

}
