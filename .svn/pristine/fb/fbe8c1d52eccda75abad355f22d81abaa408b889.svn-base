package com.kaurihealth.kaurihealth.manager_v.save;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.request_bean.bean.NewOutpatientRecordDetailDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewPatientRecordDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewRecordDisplayBean;
import com.kaurihealth.datalib.response_bean.DepartmentDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.OutpatientRecordBean;
import com.kaurihealth.datalib.response_bean.PatientRecordDisplayDto;
import com.kaurihealth.datalib.response_bean.RecordBean;
import com.kaurihealth.datalib.response_bean.SicknessBean;
import com.kaurihealth.datalib.response_bean.SicknessesBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.department_v.SelectDepartmentLevel1Activity;
import com.kaurihealth.kaurihealth.department_v.SelectDepartmentLevel2Activity;
import com.kaurihealth.kaurihealth.mine_v.personal.EnterHospitalActivity;
import com.kaurihealth.kaurihealth.sickness_v.LoadAllSicknessActivity;
import com.kaurihealth.utilslib.CheckUtils;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.controller.AbstractViewController;
import com.kaurihealth.utilslib.date.DateUtils;
import com.kaurihealth.utilslib.dialog.DialogUtils;
import com.kaurihealth.utilslib.widget.AuxiliaryExaminationLayout;
import com.kaurihealth.utilslib.widget.OutpatientGroupView;
import com.kaurihealth.utilslib.widget.PhysicalCheckSaveLayout;
import com.kaurihealth.utilslib.widget.RecentlyRecordLayout;
import com.kaurihealth.utilslib.widget.RecentlySaveLayout;
import com.kaurihealth.utilslib.widget.SaveImageLayout;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

/**
 * Created by jianghw on 2016/12/15.
 * <p/>
 * Describe: 门诊病历记录
 */

class OutpatientMedicalSaveView extends AbstractViewController<PatientRecordDisplayDto>
        implements OutpatientGroupView.ChildGroupView, RecentlySaveLayout.IEditTextClick {

    @Bind(R.id.lay_outpatient)
    OutpatientGroupView mLayAdmission;
    @Bind(R.id.cv_outpatient)
    CardView mCvAdmission;
    @Bind(R.id.lay_outpatient_documents)
    SaveImageLayout mLayDocuments;
    @Bind(R.id.cv_outpatient_img)
    CardView mCvOutpatientImg;

    @NotEmpty(message = "医生不能为空")
    RecentlyRecordLayout mLayDoctor;
    @NotEmpty(message = "科室不能为空")
    RecentlySaveLayout mLayDepartments;
    @NotEmpty(message = "机构不能为空")
    RecentlySaveLayout mLayOrganization;
    @NotEmpty(message = "就诊时间不能为空")
    RecentlySaveLayout mLayCreatedDate;

    private LinearLayout mScalingMeasurement;

    RecentlySaveLayout mLayScalingSickness;
    @NotEmpty(message = "主诉/现病史不能为空")
    RecentlySaveLayout mLayScalingPresentIllness;
    AuxiliaryExaminationLayout mLayScalingAuxiliary;
    PhysicalCheckSaveLayout mLayScalingPhysical;
    AuxiliaryExaminationLayout mLayScalingHistory;
    AuxiliaryExaminationLayout mLayScalingDrug;
    @NotEmpty(message = "印象/计划不能为空")
    RecentlySaveLayout mLayScalingPlan;
    RecentlySaveLayout mLayScalingNote;

    private Activity mActivity;
    private int departmentId;
    private DoctorDisplayBean mSelf;
    /**
     * 原始疾病bean
     */
    private List<SicknessBean> sickList = null;
    private int mPatientRecordId;

    OutpatientMedicalSaveView(Context context) {
        super(context);
    }

    @Override
    protected int getResLayoutId() {
        return R.layout.include_outpatient_medical_save;
    }

    @Override
    protected void onCreateView(View view) {
        ButterKnife.bind(this, view);

        mLayAdmission.setChildGroupViewBack(this);
        mLayAdmission.initInflaterView();
    }

    private void initListenerControls() {
        mLayDepartments.onEditClickListener(this);
        mLayOrganization.onEditClickListener(this);
        mLayCreatedDate.onEditClickListener(this);
        mLayScalingSickness.onEditClickListener(this);
        //初始化图片选择器
//        mLayDocuments.onImageModifiedListener(this);
        mLayDocuments.initLayoutView(mActivity);
        mLayDocuments.initGridViewDeleteImage(mActivity);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void bindViewData(PatientRecordDisplayDto data) {
        initListenerControls();

        if (data != null) {
            mLayDoctor.contentText(CheckUtils.checkTextContent(data.getDoctor()));
            DepartmentDisplayBean department = data.getDepartment();
            mLayDepartments.contentText(CheckUtils.checkTextContent(department != null ? department.getDepartmentName() : null));
            mLayOrganization.contentText(CheckUtils.checkTextContent(data.getHospital()));
            mLayCreatedDate.contentText(DateUtils.unifiedFormatYearMonthDay(data.getRecordDate()).equals("暂无时间记录")?
                    DateUtils.Today():DateUtils.unifiedFormatYearMonthDay(data.getRecordDate()));
            mPatientRecordId = data.getPatientRecordId();
//疾病
            sickList = data.getSickness();
            initSicknessText();
//门诊记录的参数
            RecordBean dataRecord = data.getRecord();
            if (dataRecord != null) {
                OutpatientRecordBean outpatientRecord = dataRecord.getOutpatientRecord();
                if (outpatientRecord != null) {
                    mLayScalingPresentIllness.contentText(CheckUtils.checkTextContent(outpatientRecord.getPresentIllness()));

                    mLayScalingAuxiliary.boxFirstChecked(outpatientRecord.isIsWorkupReviewed());
                    mLayScalingAuxiliary.boxSecondChecked(outpatientRecord.isIsLongTermMonitoringDataReviewed());

                    mLayScalingPhysical.hrText(CheckUtils.checkTextContent(String.valueOf(outpatientRecord.getHrValue())));
                    mLayScalingPhysical.bpFText(CheckUtils.checkTextContent(String.valueOf(outpatientRecord.getLowerBpValue())));
                    mLayScalingPhysical.bpSText(CheckUtils.checkTextContent(String.valueOf(outpatientRecord.getUpperBpValue())));
                    mLayScalingPhysical.rrText(CheckUtils.checkTextContent(String.valueOf(outpatientRecord.getRrValue())));
                    mLayScalingPhysical.tText(CheckUtils.checkTextContent(String.valueOf(outpatientRecord.getTValue())));

                    mLayScalingHistory.boxFirstChecked(outpatientRecord.isIsPastIllnessReviewed());
                    mLayScalingHistory.boxSecondChecked(outpatientRecord.isIsPastIllnessUpToDate());

                    mLayScalingDrug.boxFirstChecked(outpatientRecord.isIsCurrentMedicationReviewed());
                    mLayScalingDrug.boxSecondChecked(outpatientRecord.isIsCurrentMedicationUpToDate());

                    mLayScalingPlan.contentText(CheckUtils.checkTextContent(outpatientRecord.getTreatmentAndPlan()));
                }
            }
            mLayScalingNote.contentText(CheckUtils.checkTextContent(data.getComment()));
            mLayAdmission.notifyDataSetChanged(mScalingMeasurement);

            mLayDocuments.initRecordDocumentPathAdapter(data.getRecordDocuments());
        } else {
            defaultViewData();
        }
    }

    private void initSicknessText() {
        StringBuilder stringBuilder = new StringBuilder();
        if (sickList != null && !sickList.isEmpty()) {
            for (SicknessBean bean : sickList) {
                stringBuilder.append(bean.getSicknessName());
                stringBuilder.append("/");
            }
        } else {
            stringBuilder.append("暂无记录");
            stringBuilder.append("/");
        }
        String disease = stringBuilder.toString();
        String sick = disease.substring(0, disease.length() - 1);
        mLayScalingSickness.contentText(sick);
    }

    @Override
    protected void defaultViewData() {
    }

    @Override
    protected void unbindView() {
        ButterKnife.unbind(this);
    }

    @Override
    protected void showLayout() {
        mCvAdmission.setVisibility(View.VISIBLE);
        mCvOutpatientImg.setVisibility(View.VISIBLE);
    }

    @Override
    protected void hiddenLayout() {
        mCvAdmission.setVisibility(View.GONE);
        mCvOutpatientImg.setVisibility(View.GONE);
    }

    /**
     * 固定布局
     */
    @Override
    public int getFixedResLayoutId() {
        return R.layout.include_outpatient_save_fixed;
    }

    @Override
    public void onCreateFixedView(View view) {
        mLayDoctor = (RecentlyRecordLayout) view.findViewById(R.id.lay_fixed_doctor);
        mLayDepartments = (RecentlySaveLayout) view.findViewById(R.id.lay_outpatient_departments);
        mLayOrganization = (RecentlySaveLayout) view.findViewById(R.id.lay_outpatient_organization);
        mLayCreatedDate = (RecentlySaveLayout) view.findViewById(R.id.lay_outpatient_createdDate);
    }

    /**
     * 折叠布局
     */
    @Override
    public int getScalingResLayoutId() {
        return R.layout.include_outpatient_save_scaling;
    }

    @Override
    public void onCreateScalingView(View view) {
        mScalingMeasurement = (LinearLayout) view.findViewById(R.id.lay_scaling_measurement);

        mLayScalingSickness = (RecentlySaveLayout) view.findViewById(R.id.lay_scaling_sickness);
        mLayScalingPresentIllness = (RecentlySaveLayout) view.findViewById(R.id.lay_scaling_presentIllness);
        mLayScalingAuxiliary = (AuxiliaryExaminationLayout) view.findViewById(R.id.lay_scaling_auxiliary);
        mLayScalingPhysical = (PhysicalCheckSaveLayout) view.findViewById(R.id.lay_scaling_physical);
        mLayScalingHistory = (AuxiliaryExaminationLayout) view.findViewById(R.id.lay_scaling_history);
        mLayScalingDrug = (AuxiliaryExaminationLayout) view.findViewById(R.id.lay_scaling_drug);
        mLayScalingPlan = (RecentlySaveLayout) view.findViewById(R.id.lay_scaling_plan);
        mLayScalingNote = (RecentlySaveLayout) view.findViewById(R.id.lay_scaling_note);
    }


    /**
     * 点击事件回调
     */
    @Override
    public void contentEditTextBack(View v, String content) {
        switch (v.getId()) {
            case R.id.lay_outpatient_departments:
                selectDepartment();
                break;
            case R.id.lay_outpatient_organization:
                selectHospital(content);
                break;
            case R.id.lay_outpatient_createdDate:
                DialogUtils.showDateDialog(mActivity, (year, month, day) -> {
                    String time = year + "/"
                            + String.format(mContext.getResources().getString(R.string.date_month), Integer.valueOf(month)) + "/"
                            + String.format(mContext.getResources().getString(R.string.date_month), Integer.valueOf(day));
                    mLayCreatedDate.contentText(time);
                });
                break;
            case R.id.lay_scaling_sickness:
                selectSickness(content);
                break;
            default:
                break;
        }
    }

    //选择科室，界面跳转
    private void selectDepartment() {
        Intent intent = new Intent(mContext, SelectDepartmentLevel1Activity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Global.Environment.BUNDLE, Global.Environment.CHOICE);
        intent.putExtras(bundle);
        mActivity.startActivityForResult(intent, Global.RequestCode.DEPARTMENT);
    }

    //选择医院，界面跳转
    private void selectHospital(String content) {
        Intent intent = new Intent(mContext, EnterHospitalActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("hospitalName", content);
        bundle.putString(Global.Environment.BUNDLE, Global.Environment.CHOICE);
        intent.putExtras(bundle);
        mActivity.startActivityForResult(intent, Global.RequestCode.HOSPITAL);
    }

    //选择疾病，界面跳转
    private void selectSickness(String content) {
        Intent intent = new Intent(mContext, LoadAllSicknessActivity.class);
        Bundle bundle = new Bundle();
        ArrayList<SicknessBean> arrayList = new ArrayList<>();
        if (sickList != null) arrayList.addAll(sickList);
        bundle.putParcelableArrayList(Global.Environment.BUNDLE, arrayList);
        intent.putExtras(bundle);
        mActivity.startActivityForResult(intent, Global.RequestCode.SICKNESS);
    }

    public void childNeedActivity(Activity clazz) {
        this.mActivity = clazz;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mLayDocuments.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Global.RequestCode.DEPARTMENT:
                if (resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    setDepartment(extras);
                }
                break;
            case Global.RequestCode.HOSPITAL:
                if (resultCode == RESULT_OK) {
                    String hospitalName = data.getStringExtra("hospitalName");
                    setHospitalName(hospitalName);
                }
                break;
            case Global.RequestCode.SICKNESS://疾病
                if (resultCode == RESULT_OK) {
                    ArrayList<SicknessesBean> sickness = data.getParcelableArrayListExtra(Global.Bundle.RESULT_OK_SICKNESS);
                    if (!sickness.isEmpty()) {
                        if (sickList == null) sickList = new ArrayList<>();
                        if (!sickList.isEmpty()) sickList.clear();
                        for (SicknessesBean bean : sickness) {
                            SicknessBean sicknessBean = new SicknessBean();
                            if (bean.getSicknessId() != 0) {
                                sicknessBean.setPatientRecordId(mPatientRecordId);
                                sicknessBean.setSicknessId(bean.getSicknessId());
                            }
                            sicknessBean.setSicknessName(bean.getSicknessName());
                            sickList.add(sicknessBean);
                        }
                    }
                    initSicknessText();
                }
                break;
        }
    }

    public void onRequestPermissionsResult(int code, String[] permissions, int[] results) {
        mLayDocuments.onRequestPermissionsResult(code, permissions, results);
    }

    private void setDepartment(Bundle bundle) {
        DepartmentDisplayBean bean = (DepartmentDisplayBean) bundle.getSerializable(SelectDepartmentLevel2Activity.DepartmentLevel2ActivityKey);
        departmentId = bean.getDepartmentId();
        mLayDepartments.contentText(bean.getDepartmentName());
    }

    private void setHospitalName(String hospitalName) {
        mLayOrganization.contentText(hospitalName);
    }

    /**
     * 图片
     */
    public List<String> getImagePathsList() {
        return mLayDocuments.getImagePathsList();
    }

    /**
     * 构建编辑bean
     */
    public PatientRecordDisplayDto getRequestPatientRecordBean() {
        PatientRecordDisplayDto patientRecordDisplayDto = mBeanData;
        RecordBean recordBean = patientRecordDisplayDto.getRecord();
        if (recordBean != null) {
            OutpatientRecordBean outpatientRecordBean = recordBean.getOutpatientRecord();
            outpatientRecordBean.setPresentIllness(mLayScalingPresentIllness.getContent());//目前的病情

            outpatientRecordBean.setIsWorkupReviewed(mLayScalingAuxiliary.getBoxFirst());//是否处理审查
            outpatientRecordBean.setIsLongTermMonitoringDataReviewed(mLayScalingAuxiliary.getBoxSecond());//是否审查监测数据

            outpatientRecordBean.setHrValue(Double.valueOf(!TextUtils.isEmpty(mLayScalingPhysical.getHrText()) ? mLayScalingPhysical.getHrText() : "0.0"));//HR
            outpatientRecordBean.setLowerBpValue(Double.valueOf(!TextUtils.isEmpty(mLayScalingPhysical.getBpFirText()) ? mLayScalingPhysical.getBpFirText() : "0.0"));//Bp
            outpatientRecordBean.setUpperBpValue(Double.valueOf(!TextUtils.isEmpty(mLayScalingPhysical.getBpSecText()) ? mLayScalingPhysical.getBpSecText() : "0.0"));//Bp
            outpatientRecordBean.setRrValue(Double.valueOf(!TextUtils.isEmpty(mLayScalingPhysical.getRrText()) ? mLayScalingPhysical.getRrText() : "0.0"));
            outpatientRecordBean.setTValue(Double.valueOf(!TextUtils.isEmpty(mLayScalingPhysical.getTText()) ? mLayScalingPhysical.getTText() : "0.0"));

            outpatientRecordBean.setIsPastIllnessReviewed(mLayScalingHistory.getBoxFirst());
            outpatientRecordBean.setIsPastIllnessUpToDate(mLayScalingHistory.getBoxSecond());

            outpatientRecordBean.setIsCurrentMedicationReviewed(mLayScalingDrug.getBoxFirst());
            outpatientRecordBean.setIsCurrentMedicationUpToDate(mLayScalingDrug.getBoxSecond());

            outpatientRecordBean.setTreatmentAndPlan(mLayScalingPlan.getContent());//治疗计划
        }

        patientRecordDisplayDto.setComment(mLayScalingNote.getContent());
        patientRecordDisplayDto.setRecordDate(DateUtils.getDateSubmit(mLayCreatedDate.getContent()));
        patientRecordDisplayDto.setHospital(mLayOrganization.getContent());//机构
        patientRecordDisplayDto.setDepartmentId(departmentId == 0 ? patientRecordDisplayDto.getDepartmentId() : departmentId);  //测试id

        patientRecordDisplayDto.setSickness(sickList);

        return patientRecordDisplayDto;
    }

    /**
     * 门诊记录添加bean
     */
    public NewPatientRecordDisplayBean getNewPatientRecordDisplayBean() {
        mSelf = LocalData.getLocalData().getMyself();
        NewPatientRecordDisplayBean newPatientRecordDisplayBean = new NewPatientRecordDisplayBean();
        newPatientRecordDisplayBean.setSubject("门诊记录电子病历");
        newPatientRecordDisplayBean.setStatus("1");

        NewOutpatientRecordDetailDisplayBean newOutpatientRecordDetailDisplayBean = new NewOutpatientRecordDetailDisplayBean();
        newOutpatientRecordDetailDisplayBean.setWorkupReviewed(mLayScalingAuxiliary.getBoxFirst());//是否处理审查
        newOutpatientRecordDetailDisplayBean.setLongTermMonitoringDataReviewed(mLayScalingAuxiliary.getBoxSecond());//是否审查监测数据

        newOutpatientRecordDetailDisplayBean.setPastIllnessReviewed(mLayScalingHistory.getBoxFirst());//是否已阅过去的疾病
        newOutpatientRecordDetailDisplayBean.setCurrentMedicationUpToDate(mLayScalingHistory.getBoxSecond());//是否更新已阅的疾病

        newOutpatientRecordDetailDisplayBean.setCurrentMedicationReviewed(mLayScalingDrug.getBoxFirst());//是否审查最新的药物
        newOutpatientRecordDetailDisplayBean.setPastIllnessUpToDate(mLayScalingDrug.getBoxSecond());

        newOutpatientRecordDetailDisplayBean.setPresentIllness(mLayScalingPresentIllness.getContent());
        newOutpatientRecordDetailDisplayBean.setHrValue(Double.valueOf(!TextUtils.isEmpty(mLayScalingPhysical.getHrText()) ? mLayScalingPhysical.getHrText() : "0.0"));//HR
        newOutpatientRecordDetailDisplayBean.setUpperBpValue(Double.valueOf(!TextUtils.isEmpty(mLayScalingPhysical.getBpSecText()) ? mLayScalingPhysical.getBpSecText() : "0.0"));//Bp
        newOutpatientRecordDetailDisplayBean.setLowerBpValue(Double.valueOf(!TextUtils.isEmpty(mLayScalingPhysical.getBpFirText()) ? mLayScalingPhysical.getBpFirText() : "0.0"));//Bp
        newOutpatientRecordDetailDisplayBean.setRrValue(Double.valueOf(!TextUtils.isEmpty(mLayScalingPhysical.getRrText()) ? mLayScalingPhysical.getRrText() : "0.0"));
        newOutpatientRecordDetailDisplayBean.settValue(Double.valueOf(!TextUtils.isEmpty(mLayScalingPhysical.getTText()) ? mLayScalingPhysical.getTText() : "0.0"));

        newOutpatientRecordDetailDisplayBean.setTreatmentAndPlan(mLayScalingPlan.getContent());//治疗计划

        NewRecordDisplayBean newRecordDisplayBean = new NewRecordDisplayBean();
        newRecordDisplayBean.setOutpatientRecord(newOutpatientRecordDetailDisplayBean);//门诊电子记录的参数
        newRecordDisplayBean.setOwnerId(mBeanData.getPatientId());//用户ID/病人Id
        newRecordDisplayBean.setRecordType("门诊记录电子病历");//病例类型

        newPatientRecordDisplayBean.setRecordDate(DateUtils.getDateSubmit(mLayCreatedDate.getContent()));//记录日期
        newPatientRecordDisplayBean.setComment(mLayScalingNote.getContent());//留言
        newPatientRecordDisplayBean.setPatientId(mBeanData.getPatientId());
        newPatientRecordDisplayBean.setHospital(mLayOrganization.getContent());//医院
        newPatientRecordDisplayBean.setDoctor(mSelf.getFullName());//医生

        newPatientRecordDisplayBean.setSickness(sickList);

        newPatientRecordDisplayBean.setDepartmentId(departmentId == 0 ? mSelf.getDoctorInformations().getDepartmentId() : departmentId);//科室ID
        newPatientRecordDisplayBean.setRecord(newRecordDisplayBean);
        return newPatientRecordDisplayBean;
    }
}
