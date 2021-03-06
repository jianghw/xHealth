package com.kaurihealth.kaurihealth.patient_v.medical_records_only_read;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.response_bean.DepartmentDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.OutpatientRecordBean;
import com.kaurihealth.datalib.response_bean.PatientDisplayBean;
import com.kaurihealth.datalib.response_bean.PatientRecordDisplayBean;
import com.kaurihealth.datalib.response_bean.RecordBean;
import com.kaurihealth.utilslib.image.RecordDocumentsBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.StringPathViewAdapter;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.eventbus.OutpatientElectronicBeanEvent;
import com.kaurihealth.kaurihealth.eventbus.OutpatientElectronicOnlyReadEvent;
import com.kaurihealth.kaurihealth.patient_v.medical_records.OutpatientElectronicActivity;
import com.kaurihealth.utilslib.CheckUtils;
import com.kaurihealth.utilslib.date.DateUtils;
import com.kaurihealth.utilslib.image.GalleryUtil;
import com.kaurihealth.utilslib.image.PickImage;
import com.kaurihealth.utilslib.widget.TagsGridview;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.rey.material.widget.CheckBox;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * Created by mip on 2016/11/7.
 */

public class OutpatientElectronicOnlyReadActivity extends BaseActivity {

    //添加
    @Bind(R.id.tv_more)
    TextView mTvMore;
    //姓名
    @Bind(R.id.tv_name)
    TextView mTvName;
    //性别
    @Bind(R.id.tv_gendar)
    TextView mTvGendar;
    //年龄
    @Bind(R.id.tv_age)
    TextView mTvAge;
    //类型
    @Bind(R.id.tv_category)
    TextView mTvCategory;
    //项目
    @Bind(R.id.tv_subject)
    TextView mTvSubject;
    //医生
    @NotEmpty(message = "医生不能为空")
    @Bind(R.id.et_doctor)
    EditText mEtDoctor;
    //科室
    @NotEmpty(message = "科室不能为空")
    @Bind(R.id.et_department)
    TextView mEtDepartment;
    //机构
    @NotEmpty(message = "机构不能为空")
    @Bind(R.id.et_institutions)
    TextView mEtInstitutions;
    //就诊时间
    @NotEmpty(message = "就诊时间不能为空")
    @Bind(R.id.et_clinical_time)
    TextView mEtClinicalTime;
    //主诉/现病史
    @NotEmpty(message = "主诉/现病史不能为空")
    @Bind(R.id.et_main_tell)
    EditText mEtMainTell;
    //既往病史--checkbox 1
    @Bind(R.id.cb_history_f)
    CheckBox mCbHistoryF;
    //既往病史--checkbox 2
    @Bind(R.id.cb_history_s)
    CheckBox mCbHistoryS;
    //目前使用药物--checkbox 1
    @Bind(R.id.cb_drug_f)
    CheckBox mCbDrugF;
    //目前使用药物--checkbox 2
    @Bind(R.id.cb_drug_s)
    CheckBox mCbDrugS;


    //体格检查 HR  数字，小数点
//    @Pattern(regex = "^[+-]?\\d+(\\.\\d+)?$",message = "体格检查只能输入数字和小数点")
    @Bind(R.id.et_physical_hr)
    EditText mEtPhysicalHr;
    //体格检查 BP upper
    @Bind(R.id.et_physical_bp_1)
    EditText mEtPhysicalBp1;
    //体格检查 BP lower
    @Bind(R.id.et_physical_bp_2)
    EditText mEtPhysicalBp2;
    //体格检查 RR
    @Bind(R.id.et_physical_rr)
    EditText mEtPhysicalRr;
    //体格检查 TT
    @Bind(R.id.et_physical_t)
    EditText mEtPhysicalT;


    //体格检查 备注
    @Bind(R.id.et_physical_note)
    EditText mEtPhysicalNote;
    //辅助检查  医疗记录已阅 checkbox 1
    @Bind(R.id.cb_auxiliary_f)
    CheckBox mCbAuxiliaryF;
    //辅助检查  长期监测指标已阅  checkbox2
    @Bind(R.id.cb_auxiliary_s)
    CheckBox mCbAuxiliaryS;
    //辅助检查  备注
    @NotEmpty(message = "请填写辅助检查")
    @Bind(R.id.et_auxiliary_note)
    EditText mEtAuxiliaryNote;
    //印象/计划
    @NotEmpty(message = "印象/计划不能为空")
    @Bind(R.id.et_impression_plan)
    EditText mEtImpressionPlan;
    //上传附件 （十字）
    @Bind(R.id.iv_add_upload)
    ImageView mIvAddUpload;
    //上传附件 -- 放上传图片缩略图的地方
    @Bind(R.id.gv_image)
    TagsGridview mGvImage;
    //备注：
    @Bind(R.id.et_note)
    EditText mEtNote;

    /**
     * 图片地址集合，含本地及网络
     */
    private ArrayList<String> paths = new ArrayList<>();

    private PickImage pickImages;//图片选择操作对象
    private StringPathViewAdapter adapter;
    private PatientRecordDisplayBean mPatientRecordDisplayBean;
    private String kauriHealthId;//上传图片对应id
    private DoctorPatientRelationshipBean relationshipBean;//医患关系
    //机构名称
    public static final int EdithHospitalName = 10;

    private int departmentId = 0;
    private int DoctorPatientId;
    private DepartmentDisplayBean bean;
    private DoctorDisplayBean myself;
    private static final int OUTPAIENTELECTRONIC = 3;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_outpatient_electronic;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        adapter = new StringPathViewAdapter(getApplicationContext(), paths);
        mGvImage.setAdapter(adapter);
    }

    @Override
    protected void initDelayedData() {
        myself = LocalData.getLocalData().getMyself();
        EventBus.getDefault().register(this);
    }

    //跳转编辑
    @OnClick(R.id.tv_more)
    public void onMoreClick() {
        if (mTvMore.getText().equals(getString(R.string.swipe_tv_compile))) {  //已存在病例
            EventBus.getDefault().postSticky(new OutpatientElectronicBeanEvent(mPatientRecordDisplayBean,relationshipBean));
            skipToForResult(OutpatientElectronicActivity.class,null,OUTPAIENTELECTRONIC);
        }
    }


    /**
     * ----------------------------订阅事件----------------------------------
     * 点击事件 跳转 只读
     **/
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(OutpatientElectronicOnlyReadEvent event) {
        mTvMore.setText(getString(R.string.swipe_tv_compile));
        //view不可操作
        setAllViewsEnable(false, this);
        mTvMore.setEnabled(true);

        mPatientRecordDisplayBean = event.getPatientRecordDisplayBean();
        List<RecordDocumentsBean> list = mPatientRecordDisplayBean.getRecordDocuments();

        if (!paths.isEmpty()) paths.clear();
        if (list != null && list.size() > 0) {
            for (RecordDocumentsBean documentsBean : list) {
                if (!documentsBean.isIsDeleted()) paths.add(documentsBean.getDocumentUrl());
            }
        }

        relationshipBean = event.getDoctorPatientRelationshipBean();
        PatientDisplayBean patient = relationshipBean.getPatient();
        kauriHealthId = patient.getKauriHealthId();
        pickImages = new PickImage(paths, this, () -> adapter.notifyDataSetChanged());

        displayDataByBean(mPatientRecordDisplayBean, relationshipBean);
    }

    /**
     * ----------------------------继承基础mvpView方法-----------------------------------
     */
    /*数据显示处理*/
    private void displayDataByBean(PatientRecordDisplayBean bean, DoctorPatientRelationshipBean relationshipBean) {
        personalOfInformation(relationshipBean);
        projectByInformation(bean);
        fillAllCases(bean);
    }

    private void personalOfInformation(DoctorPatientRelationshipBean relationshipBean) {
        Date endDate = relationshipBean.getEndDate();
        boolean isActive = DateUtils.isActive(relationshipBean.isIsActive(), endDate);
        mTvMore.setVisibility(isActive ? View.VISIBLE : View.GONE);
        PatientDisplayBean patient = relationshipBean.getPatient();
        setTvName(patient.getFullName());
        setTvGendar(patient.getGender());
        int age = DateUtils.calculateAge(patient.getDateOfBirth());
        setTvAge(age + "岁");
    }

    private void projectByInformation(PatientRecordDisplayBean bean) {
        //判断是否是自己写的记录
        if (bean.getCreatedBy() != myself.getUserId()) {
            mTvMore.setVisibility(View.GONE);
        }
        String subject = bean.getSubject();
        initNewBackBtn(subject);
        setTvCategory(bean.getCategory());//类型
        setTvSubject(subject);//项目
        setEtDoctor(bean.getDoctor());
        setEtDepartment(bean.getDepartment() != null ? bean.getDepartment().getDepartmentName() : getString(R.string.being_no));
        setEtInstitutions(bean.getHospital());//机构
        setEtClinicalTime(DateUtils.getFormatDate(bean.getRecordDate()));//就诊时间
    }

    //数据显示处理
    private void fillAllCases(PatientRecordDisplayBean bean) {
        //病例的参数
        OutpatientRecordBean outpatientRecordBean = getOutpatientRecordBean(bean);
        setEtMainTell(outpatientRecordBean.getPresentIllness());//目前的病情  主诉/现病史
        setCbHistoryF(outpatientRecordBean.isIsPastIllnessReviewed());
        setCbHistoryS(outpatientRecordBean.isIsCurrentMedicationUpToDate());
        setCbDrugF(outpatientRecordBean.isIsCurrentMedicationReviewed());  //目前使用药物  病例已阅
        setCbDrugS(outpatientRecordBean.isIsCurrentMedicationUpToDate()); //目前使用药物   病例已更新

        setEtPhysicalHr(String.valueOf(outpatientRecordBean.getHrValue()));//HR
        setEtPhysicalBp1(String.valueOf(outpatientRecordBean.getUpperBpValue()));//BP
        setEtPhysicalBp2(String.valueOf(outpatientRecordBean.getLowerBpValue()));
        setEtPhysicalRr(String.valueOf(outpatientRecordBean.getRrValue()));//RR
        setEtPhysicalT(String.valueOf(outpatientRecordBean.getTValue()));//T

        setEtPhysicalNote(outpatientRecordBean.getPhysicalExamination());//身体检查
        setCbAuxiliaryF(outpatientRecordBean.isIsWorkupReviewed());//是否处理审查
        setCbAuxiliaryS(outpatientRecordBean.isIsLongTermMonitoringDataReviewed());//是否审查监测数据
        setEtAuxiliaryNote(outpatientRecordBean.getWorkupReviewComment());
        setEtImpressionPlan(outpatientRecordBean.getTreatmentAndPlan());//治疗计划
        setEtNote(bean.getComment());//备注
    }

    @NonNull
    private OutpatientRecordBean getOutpatientRecordBean(PatientRecordDisplayBean bean) {
        RecordBean recordBean = bean.getRecord();
        CheckUtils.checkNullArgument(recordBean, "recordBean is null");
        OutpatientRecordBean outpatientRecordBean = recordBean.getOutpatientRecord();
        CheckUtils.checkNullArgument(outpatientRecordBean, "outpatientRecordBean is null");
        return outpatientRecordBean;
    }

    /**
     * 返回主Activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == OUTPAIENTELECTRONIC){
            finishCur();
        }
    }

    /**
     * #附件-- 放缩略图的地方
     * 点击放大图片
     */
    @OnItemClick(R.id.gv_image)
    public void picture(int position) {
        GalleryUtil.toGallery(this, new GalleryUtil.GetUrlsInterface() {
            @Override
            public ArrayList<String> getUrls() {
                return paths;
            }
        }, position);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public String getTvName() {
        return mTvName.getText().toString().trim();
    }

    public void setTvName(String tvName) {
        mTvName.setText(tvName);
    }

    public String getTvGendar() {
        return mTvGendar.getText().toString().trim();
    }

    public void setTvGendar(String tvGendar) {
        mTvGendar.setText(tvGendar);
    }

    public String getTvAge() {
        return mTvAge.getText().toString().trim();
    }

    public void setTvAge(String tvAge) {
        mTvAge.setText(tvAge);
    }

    public String getTvCategory() {
        return mTvCategory.getText().toString().trim();
    }

    public void setTvCategory(String tvCategory) {
        mTvCategory.setText(tvCategory);
    }

    public String getTvSubject() {
        return mTvSubject.getText().toString().trim();
    }

    public void setTvSubject(String tvSubject) {
        mTvSubject.setText(tvSubject);
    }

    public String getEtDoctor() {
        return mEtDoctor.getText().toString().trim();
    }

    public void setEtDoctor(String etDoctor) {
        mEtDoctor.setText(etDoctor);
    }

    public String getEtDepartment() {
        return mEtDepartment.getText().toString().trim();
    }

    public void setEtDepartment(String etDepartment) {
        mEtDepartment.setText(etDepartment);
    }

    public String getEtInstitutions() {
        return mEtInstitutions.getText().toString().trim();
    }

    public void setEtInstitutions(String etInstitutions) {
        mEtInstitutions.setText(etInstitutions);
    }

    public String getEtClinicalTime() {
        return mEtClinicalTime.getText().toString().trim();
    }

    public void setEtClinicalTime(String etClinicalTime) {
        mEtClinicalTime.setText(etClinicalTime);
    }

    public String getEtMainTell() {
        return mEtMainTell.getText().toString().trim();
    }

    public void setEtMainTell(String etMainTell) {
        mEtMainTell.setText(etMainTell);
    }

    public boolean getCbHistoryF() {
        return mCbHistoryF.isChecked();
    }

    public void setCbHistoryF(boolean cbHistoryF) {
        mCbHistoryF.setChecked(cbHistoryF);
    }

    public boolean getCbHistoryS() {
        return mCbHistoryS.isChecked();
    }

    public void setCbHistoryS(boolean cbHistoryS) {
        mCbHistoryS.setChecked(cbHistoryS);
    }

    public boolean getCbDrugF() {
        return mCbDrugF.isChecked();
    }

    public void setCbDrugF(boolean cbDrugF) {
        mCbDrugF.setChecked(cbDrugF);
    }

    public boolean getCbDrugS() {
        return mCbDrugS.isChecked();
    }

    public void setCbDrugS(boolean cbDrugS) {
        mCbDrugS.setChecked(cbDrugS);
    }

    public String getEtPhysicalHr() {
        return mEtPhysicalHr.getText().toString().trim();
    }

    public void setEtPhysicalHr(String etPhysicalHr) {
        mEtPhysicalHr.setText(etPhysicalHr);
    }

    public String getEtPhysicalBp1() {
        return mEtPhysicalBp1.getText().toString().trim();
    }

    public void setEtPhysicalBp1(String etPhysicalBp1) {
        mEtPhysicalBp1.setText(etPhysicalBp1);
    }

    public String getEtPhysicalBp2() {
        return mEtPhysicalBp2.getText().toString().trim();
    }

    public void setEtPhysicalBp2(String etPhysicalBp2) {
        mEtPhysicalBp2.setText(etPhysicalBp2);
    }

    public String getEtPhysicalRr() {
        return mEtPhysicalRr.getText().toString().trim();
    }

    public void setEtPhysicalRr(String etPhysicalRr) {
        mEtPhysicalRr.setText(etPhysicalRr);
    }

    public String getEtPhysicalT() {
        return mEtPhysicalT.getText().toString().trim();
    }

    public void setEtPhysicalT(String etPhysicalT) {
        mEtPhysicalT.setText(etPhysicalT);
    }

    public String getEtPhysicalNote() {
        return mEtPhysicalNote.getText().toString().trim();
    }

    public void setEtPhysicalNote(String etPhysicalNote) {
        mEtPhysicalNote.setText(etPhysicalNote);
    }

    public boolean getCbAuxiliaryF() {
        return mCbAuxiliaryF.isChecked();
    }

    public void setCbAuxiliaryF(boolean cbAuxiliaryF) {
        mCbAuxiliaryF.setChecked(cbAuxiliaryF);
    }

    public boolean getCbAuxiliaryS() {
        return mCbAuxiliaryS.isChecked();
    }

    public void setCbAuxiliaryS(boolean cbAuxiliaryS) {
        mCbAuxiliaryS.setChecked(cbAuxiliaryS);
    }

    public String getEtAuxiliaryNote() {
        return mEtAuxiliaryNote.getText().toString().trim();
    }

    public void setEtAuxiliaryNote(String etAuxiliaryNote) {
        mEtAuxiliaryNote.setText(etAuxiliaryNote);
    }

    public String getEtImpressionPlan() {
        return mEtImpressionPlan.getText().toString().trim();
    }

    public void setEtImpressionPlan(String etImpressionPlan) {
        mEtImpressionPlan.setText(etImpressionPlan);
    }

    public String getEtNote() {
        return mEtNote.getText().toString().trim();
    }

    public void setEtNote(String etNote) {
        mEtNote.setText(etNote);
    }

    //设置医院名称
    private void setHospitalName(String hospitalName) {
        mEtInstitutions.setText(hospitalName);
    }
}
