package com.kaurihealth.kaurihealth.manager_v.compile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;

import com.kaurihealth.datalib.response_bean.DepartmentDisplayBean;
import com.kaurihealth.datalib.response_bean.OutpatientRecordBean;
import com.kaurihealth.datalib.response_bean.PatientRecordDisplayDto;
import com.kaurihealth.datalib.response_bean.RecordBean;
import com.kaurihealth.datalib.response_bean.SicknessBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.utilslib.CheckUtils;
import com.kaurihealth.utilslib.controller.AbstractViewController;
import com.kaurihealth.utilslib.date.DateUtils;
import com.kaurihealth.utilslib.widget.AuxiliaryExaminationLayout;
import com.kaurihealth.utilslib.widget.OutpatientGroupView;
import com.kaurihealth.utilslib.widget.PhysicalCheckLayout;
import com.kaurihealth.utilslib.widget.RecentlyRecordLayout;
import com.kaurihealth.utilslib.widget.ReviewImageLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jianghw on 2016/12/15.
 * <p/>
 * Describe: 门诊病历记录
 */

class OutpatientMedicalView extends AbstractViewController<PatientRecordDisplayDto> implements OutpatientGroupView.ChildGroupView {

    @Bind(R.id.lay_outpatient)
    OutpatientGroupView mLayAdmission;
    @Bind(R.id.cv_outpatient)
    CardView mCvAdmission;
    @Bind(R.id.lay_documents)
    ReviewImageLayout mLayDocuments;
    @Bind(R.id.cv_outpatient_img)
    CardView mCvOutpatientImg;

    RecentlyRecordLayout mLayDoctor;
    RecentlyRecordLayout mLayDepartments;
    RecentlyRecordLayout mLayOrganization;
    RecentlyRecordLayout mLayCreatedDate;

    private LinearLayout mScalingMeasurement;

    RecentlyRecordLayout mLayScalingSickness;
    RecentlyRecordLayout mLayScalingPresentIllness;
    AuxiliaryExaminationLayout mLayScalingAuxiliary;
    PhysicalCheckLayout mLayScalingPhysical;
    AuxiliaryExaminationLayout mLayScalingHistory;
    AuxiliaryExaminationLayout mLayScalingDrug;
    RecentlyRecordLayout mLayScalingPlan;
    RecentlyRecordLayout mLayScalingNote;

    OutpatientMedicalView(Context context) {
        super(context);
    }

    @Override
    protected int getResLayoutId() {
        return R.layout.include_outpatient_medical;
    }

    @Override
    protected void onCreateView(View view) {
        ButterKnife.bind(this, view);

        mLayAdmission.setChildGroupViewBack(this);
        mLayAdmission.initInflaterView();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void bindViewData(PatientRecordDisplayDto data) {
        if (data != null) {
            mLayDoctor.contentText(CheckUtils.checkTextContent(data.getDoctor()));
            DepartmentDisplayBean department = data.getDepartment();
            mLayDepartments.contentText(CheckUtils.checkTextContent(department != null ? department.getDepartmentName() : null));
            mLayOrganization.contentText(CheckUtils.checkTextContent(data.getHospital()));
            mLayCreatedDate.contentText(DateUtils.unifiedFormatYearMonthDay(data.getRecordDate()));

            List<SicknessBean> sickList = data.getSickness();
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
//门诊记录的参数
            RecordBean dataRecord = data.getRecord();
            if (dataRecord != null) {
                OutpatientRecordBean outpatientRecord = dataRecord.getOutpatientRecord();
                if(outpatientRecord!=null){
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

            mLayScalingAuxiliary.setEnableFalse(false);
            mLayScalingHistory.setEnableFalse(false);
            mLayScalingDrug.setEnableFalse(false);
            mLayScalingNote.contentText(CheckUtils.checkTextContent(data.getComment()));
            mLayAdmission.notifyDataSetChanged(mScalingMeasurement);

            mLayDocuments.initDocumentPathAdapter(data.getRecordDocuments());
        } else {
            defaultViewData();
        }
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
        return R.layout.include_outpatient_group_view_fixed;
    }

    @Override
    public void onCreateFixedView(View view) {
        mLayDoctor = (RecentlyRecordLayout) view.findViewById(R.id.lay_fixed_doctor);
        mLayDepartments = (RecentlyRecordLayout) view.findViewById(R.id.lay_fixed_departments);
        mLayOrganization = (RecentlyRecordLayout) view.findViewById(R.id.lay_fixed_organization);
        mLayCreatedDate = (RecentlyRecordLayout) view.findViewById(R.id.lay_fixed_createdDate);
    }

    /**
     * 折叠布局
     */
    @Override
    public int getScalingResLayoutId() {
        return R.layout.include_outpatient_group_view_scaling;
    }

    @Override
    public void onCreateScalingView(View view) {
        mScalingMeasurement = (LinearLayout) view.findViewById(R.id.lay_scaling_measurement);

        mLayScalingSickness = (RecentlyRecordLayout) view.findViewById(R.id.lay_scaling_sickness);
        mLayScalingPresentIllness = (RecentlyRecordLayout) view.findViewById(R.id.lay_scaling_presentIllness);
        mLayScalingAuxiliary = (AuxiliaryExaminationLayout) view.findViewById(R.id.lay_scaling_auxiliary);
        mLayScalingPhysical = (PhysicalCheckLayout) view.findViewById(R.id.lay_scaling_physical);
        mLayScalingHistory = (AuxiliaryExaminationLayout) view.findViewById(R.id.lay_scaling_history);
        mLayScalingDrug = (AuxiliaryExaminationLayout) view.findViewById(R.id.lay_scaling_drug);
        mLayScalingPlan = (RecentlyRecordLayout) view.findViewById(R.id.lay_scaling_plan);
        mLayScalingNote = (RecentlyRecordLayout) view.findViewById(R.id.lay_scaling_note);
    }
}
