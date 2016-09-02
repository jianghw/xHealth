package com.kaurihealth.kaurihealth.common.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.kaurihealth.datalib.request_bean.bean.ClinicalDiagnosisAndTreatmentGroupIteam;
import com.kaurihealth.datalib.request_bean.bean.PatientRecordDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.common.Interface.IGetMedicaHistoryRecord;
import com.kaurihealth.kaurihealth.common.activity.EditCommonMedicalRecordActivityNew;
import com.kaurihealth.kaurihealth.common.activity.EditOnlineConsultActivityNew;
import com.kaurihealth.kaurihealth.common.activity.EditOutpatientActivityNew;
import com.kaurihealth.kaurihealth.common.activity.EditRemoteActivityNew;
import com.kaurihealth.kaurihealth.common.adapter.ClinicalDiagnAndTreatAdapter;
import com.kaurihealth.kaurihealth.common.util.FragmentControl;
import com.kaurihealth.kaurihealth.home.util.BundleFactory;
import com.kaurihealth.kaurihealth.home.util.CompatorUtil;
import com.kaurihealth.kaurihealth.home.util.Interface.IBundleFactory;
import com.kaurihealth.kaurihealth.home.util.Interface.ISetBundleHealthyRecord;
import com.kaurihealth.kaurihealth.util.Interface.IStrartActivity;
import com.youyou.zllibrary.util.CommonFragment;
import com.youyou.zllibrary.util.FragmentData;
import com.youyou.zllibrary.widget.AnimatedExpandableListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 版权:    张磊
 * 作者:    张磊
 * 描述：临床诊疗
 * 创建日期:2016/1/22
 * 修订日期:
 */
public class ClinicalDiagnosisAndTreatmentFragment extends CommonFragment implements FragmentData<PatientRecordDisplayBean>, FragmentControl{
    View view;
    String[] titlsKey = {"门诊记录电子病历", "远程医疗咨询", "网络医疗咨询", "门诊记录图片存档", "入院记录", "院内治疗相关记录", "出院记录"};
    String[] titleValue = null;
    //门诊电子记录
    ClinicalDiagnosisAndTreatmentGroupIteam outpatinent = new ClinicalDiagnosisAndTreatmentGroupIteam(titlsKey[0], EditOutpatientActivityNew.class);
    //远程会诊咨询
    ClinicalDiagnosisAndTreatmentGroupIteam remoteMedicalConsult = new ClinicalDiagnosisAndTreatmentGroupIteam(titlsKey[1], EditRemoteActivityNew.class);
    //网络医疗咨询
    ClinicalDiagnosisAndTreatmentGroupIteam onlineMedicalConsult = new ClinicalDiagnosisAndTreatmentGroupIteam(titlsKey[2], EditOnlineConsultActivityNew.class);
    //门诊图片记录
    ClinicalDiagnosisAndTreatmentGroupIteam outpatinentPicture = new ClinicalDiagnosisAndTreatmentGroupIteam(titlsKey[3], EditCommonMedicalRecordActivityNew.class);
    //入院记录
    ClinicalDiagnosisAndTreatmentGroupIteam intoHistoryRecord = new ClinicalDiagnosisAndTreatmentGroupIteam(titlsKey[4], EditCommonMedicalRecordActivityNew.class);
    //院内治疗记录
    ClinicalDiagnosisAndTreatmentGroupIteam recordWithinHospital = new ClinicalDiagnosisAndTreatmentGroupIteam(titlsKey[5], EditCommonMedicalRecordActivityNew.class);
    //出院记录
    ClinicalDiagnosisAndTreatmentGroupIteam outHistoryRecord = new ClinicalDiagnosisAndTreatmentGroupIteam(titlsKey[6], EditCommonMedicalRecordActivityNew.class);
    ClinicalDiagnosisAndTreatmentGroupIteam[] clinicalList = {outpatinent, remoteMedicalConsult, onlineMedicalConsult, outpatinentPicture, intoHistoryRecord, recordWithinHospital, outHistoryRecord};
    //门诊电子记录
    List<PatientRecordDisplayBean> outpatientList = new ArrayList<>();
    //远程会诊咨询
    List<PatientRecordDisplayBean> remoteMedicalConsultList = new ArrayList<>();
    //网络医疗咨询
    List<PatientRecordDisplayBean> onlineMedicalConsultList = new ArrayList<>();
    //门诊图片记录
    List<PatientRecordDisplayBean> outpatinentPictureList = new ArrayList<>();
    //入院记录
    List<PatientRecordDisplayBean> intoHistoryRecordList = new ArrayList<>();
    //院内治疗记录
    List<PatientRecordDisplayBean> recordWithinHospitalList = new ArrayList<>();
    //出院记录
    List<PatientRecordDisplayBean> outHistoryRecordList = new ArrayList<>();
    private List<ClinicalDiagnosisAndTreatmentGroupIteam> list = new ArrayList<>();

    {
        outpatinent.setList(outpatientList);
        remoteMedicalConsult.setList(remoteMedicalConsultList);
        onlineMedicalConsult.setList(onlineMedicalConsultList);
        outpatinentPicture.setList(outpatinentPictureList);
        intoHistoryRecord.setList(intoHistoryRecordList);
        recordWithinHospital.setList(recordWithinHospitalList);
        outHistoryRecord.setList(outHistoryRecordList);
        list.add(outpatinent);
        list.add(remoteMedicalConsult);
        list.add(onlineMedicalConsult);
        list.add(outpatinentPicture);
        list.add(intoHistoryRecord);
        list.add(recordWithinHospital);
        list.add(outHistoryRecord);

    }

    private IStrartActivity iStrartActivity;
    private CompatorUtil compatorUtil;
    private IGetMedicaHistoryRecord medicaHistoryRecord;

    public void setiStrartActivity(IStrartActivity iStrartActivity) {
        this.iStrartActivity = iStrartActivity;
    }

    @Bind(R.id.animexpandableview_content_clinicatreatment)
    AnimatedExpandableListView expandableListView;
    @Bind(R.id.clinical_SR)
    SwipeRefreshLayout clinical_SR;
    private ClinicalDiagnAndTreatAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.clinicaltreatment, null);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void init() {
        super.init();


        clinical_SR.setSize(SwipeRefreshLayout.DEFAULT);
        clinical_SR.setColorSchemeResources(R.color.holo_blue_light_new
                , R.color.holo_blue_light_new,
                R.color.holo_blue_light_new, R.color.holo_blue_light_new);
        clinical_SR.setProgressBackgroundColor(R.color.linelogin);
        clinical_SR.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                medicaHistoryRecord.getDate();
                clinical_SR.setRefreshing(false);

            }
        });
        titleValue = getResources().getStringArray(R.array.ClinicalDiagnosisAndTreatment);
        adapter = new ClinicalDiagnAndTreatAdapter(getContext(), list);
        expandableListView.setAdapter(adapter);
        expandableListView.setGroupIndicator(null);
        final IBundleFactory bundleFactory = new BundleFactory();

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                iteam = null;
                purpose = clinicalList[groupPosition].getPurpose();
                iteam = clinicalList[groupPosition].getList().get(childPosition);
                if (iteam != null && purpose != null) {
                    bundle.putSerializable("data", iteam);
                    ISetBundleHealthyRecord iSetBundleHealthyRecord = bundleFactory.getISetBundleHealthyRecord(bundle);
                    iSetBundleHealthyRecord.setAble(isEditable);
                    iStrartActivity.startActivityForResult(purpose, bundle, 14);
                    return true;
                } else {
                    return false;
                }
            }
        });

    }

    private PatientRecordDisplayBean iteam;

    private Class purpose;

    @Override
    public void setData(List<PatientRecordDisplayBean> list) {
        for (int i = 0; i < clinicalList.length; i++) {
            clinicalList[i].getList().clear();
        }
        compatorUtil = new CompatorUtil();
        PatientRecordDisplayBean[] jsonNodes = compatorUtil.handleData(list);
        for (PatientRecordDisplayBean iteam : jsonNodes) {
            for (int i = 0; i < clinicalList.length; i++) {
                if (iteam.subject.equals(clinicalList[i].getTitle())) {
                    clinicalList[i].getList().add(iteam);
                    break;
                }
            }
        }
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    private Bundle bundle;

    @Override
    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    @Override
    public void setEditable(boolean isEditable) {
        this.isEditable = isEditable;
    }

    boolean isEditable;

    public void setGetMedicaHistoryRecordListener(IGetMedicaHistoryRecord medicaHistoryRecord){
        this.medicaHistoryRecord = medicaHistoryRecord;
    }

}
