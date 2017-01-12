package com.kaurihealth.kaurihealth.manager_v.record;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

import com.kaurihealth.datalib.response_bean.LabTestCountsBean;
import com.kaurihealth.datalib.response_bean.LatestPatientRecordCountBean;
import com.kaurihealth.datalib.response_bean.PathologyRecordCountsBean;
import com.kaurihealth.datalib.response_bean.PatientBean;
import com.kaurihealth.datalib.response_bean.PatientRecordCountDisplayBean;
import com.kaurihealth.datalib.response_bean.SicknessesBean;
import com.kaurihealth.datalib.response_bean.SupplementaryTestCountsBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.history_record_v.ClinicalHistoryActivity;
import com.kaurihealth.utilslib.CheckUtils;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.controller.AbstractViewController;
import com.kaurihealth.utilslib.date.DateUtils;
import com.kaurihealth.utilslib.widget.RecentlyRecordLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jianghw on 2016/12/15.
 * <p/>
 * Describe: 最近临床诊疗
 */

class RecordRecentlyView extends AbstractViewController<PatientRecordCountDisplayBean> implements View.OnClickListener {

    @Bind(R.id.lay_data)
    RecentlyRecordLayout mLayData;
    @Bind(R.id.lay_type)
    RecentlyRecordLayout mLayType;
    @Bind(R.id.lay_project)
    RecentlyRecordLayout mLayProject;
    @Bind(R.id.lay_doctor)
    RecentlyRecordLayout mLayDoctor;
    @Bind(R.id.lay_departments)
    RecentlyRecordLayout mLayDepartments;
    @Bind(R.id.lay_organization)
    RecentlyRecordLayout mLayOrganization;
    @Bind(R.id.lay_disease)
    RecentlyRecordLayout mLayDisease;
    @Bind(R.id.lay_history)
    RecentlyRecordLayout mLayHistory;
    @Bind(R.id.lay_accessory)
    RecentlyRecordLayout mLayAccessory;
    @Bind(R.id.lay_lab)
    RecentlyRecordLayout mLayLab;
    @Bind(R.id.lay_pathology)
    RecentlyRecordLayout mLayPathology;
    @Bind(R.id.tv_recently_history)
    TextView mTvHistory;
    @Bind(R.id.cv_recently)
    CardView mCvRecently;

    private int mPatientId;

    RecordRecentlyView(Context context) {
        super(context);
    }

    @Override
    protected int getResLayoutId() {
        return R.layout.include_record_recently;
    }

    @Override
    protected void onCreateView(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    protected void bindViewData(PatientRecordCountDisplayBean data) {
        PatientBean patientBean = data.getPatient();
        if (patientBean != null) {
            mPatientId = patientBean.getPatientId();
        }

        LatestPatientRecordCountBean latestPatientRecordCount = data.getLatestPatientRecordCount();
        if (latestPatientRecordCount != null) {
            stateTypeView(true);
            String date = DateUtils.unifiedFormatYearMonthDay(latestPatientRecordCount.getRecordDate());
            mLayData.setContentTitle("时间：", CheckUtils.checkTextContent(date));
            mLayType.setContentTitle("类型：", CheckUtils.checkTextContent(latestPatientRecordCount.getRecordType()));
            mLayProject.setContentTitle("项目：", CheckUtils.checkTextContent(latestPatientRecordCount.getSubject()).equals("门诊记录电子病历") ? "门诊记录" : "暂无");
            mLayDoctor.setContentTitle("医师：", CheckUtils.checkTextContent(latestPatientRecordCount.getDoctor()));
            mLayDepartments.setContentTitle("科室：", CheckUtils.checkTextContent(latestPatientRecordCount.getDepartment()));
            mLayOrganization.setContentTitle("机构：", CheckUtils.checkTextContent(latestPatientRecordCount.getHospital()));
            //疾病
            List<SicknessesBean> sickList = latestPatientRecordCount.getSicknesses();
            StringBuilder stringBuilder = new StringBuilder();
            if (sickList != null && !sickList.isEmpty()) {
                for (SicknessesBean bean : sickList) {
                    stringBuilder.append(bean.getSicknessName());
                    stringBuilder.append(" / ");
                }
            } else {
                stringBuilder.append("暂无记录");
                stringBuilder.append("/ ");
            }
            String disease = stringBuilder.toString();
            String newDis = disease.substring(0, disease.length() - 2);
            mLayDisease.setContentTitle("疾病：", CheckUtils.checkTextContent(newDis));
            mLayHistory.setContentTitle("现病史：", CheckUtils.checkTextContent(latestPatientRecordCount.getCurrentCondition()));

            //辅助检查
            List<SupplementaryTestCountsBean> supplementaryTestCountsBeanList = latestPatientRecordCount.getSupplementaryTestCounts();
            if (supplementaryTestCountsBeanList == null || supplementaryTestCountsBeanList.isEmpty()) {
                mLayAccessory.setVisibility(View.GONE);
            } else {
                mLayAccessory.setVisibility(View.VISIBLE);
                StringBuilder sb = new StringBuilder();
                for (SupplementaryTestCountsBean bean : supplementaryTestCountsBeanList) {
                    addStringBuilder(sb, bean.getKeyName(), bean.getCount());
                }
                String toString = sb.toString();
                String accessory = toString.substring(0, toString.length() - 1);
                mLayAccessory.setContentTitle("辅助检查：", CheckUtils.checkTextContent(accessory));
            }
            //实验室检查
            List<LabTestCountsBean> labTestCountsBeenList = latestPatientRecordCount.getLabTestCounts();
            if (labTestCountsBeenList == null || labTestCountsBeenList.isEmpty()) {
                mLayLab.setVisibility(View.GONE);
            } else {
                mLayLab.setVisibility(View.VISIBLE);
                StringBuilder sb = new StringBuilder();
                for (LabTestCountsBean bean : labTestCountsBeenList) {
                    addStringBuilder(sb, bean.getKeyName(), bean.getCount());
                }
                String toString = sb.toString();
                String accessory = toString.substring(0, toString.length() - 1);
                mLayLab.setContentTitle("实验室检查：", CheckUtils.checkTextContent(accessory));
            }
            //病理
            List<PathologyRecordCountsBean> pathologyRecordCounts = latestPatientRecordCount.getPathologyRecordCounts();
            if (pathologyRecordCounts == null || pathologyRecordCounts.isEmpty()) {
                mLayPathology.setVisibility(View.GONE);
            } else {
                mLayPathology.setVisibility(View.VISIBLE);
                StringBuilder sb = new StringBuilder();
                for (PathologyRecordCountsBean bean : pathologyRecordCounts) {
                    addStringBuilder(sb, bean.getKeyName(), bean.getCount());
                }
                String toString = sb.toString();
                String accessory = toString.substring(0, toString.length() - 1);
                mLayPathology.setContentTitle("病理：", CheckUtils.checkTextContent(accessory));
            }
        } else {
            defaultViewData();
        }
    }

    private void addStringBuilder(StringBuilder sb, String name, int count) {
        sb.append(name);
        Spanned slash = Html.fromHtml(mContext.getResources().getString(R.string.green_slash, "/"));
        sb.append(slash.toString());
        String number = String.format(mContext.getResources().getString(R.string.green_number), count);
        sb.append(Html.fromHtml(mContext.getResources().getString(R.string.green_slash, number)));
        sb.append("\n");
    }

    @Override
    protected void defaultViewData() {
        stateTypeView(false);
    }

    @Override
    protected void unbindView() {
        ButterKnife.unbind(this);
    }

    private void stateTypeView(boolean isActive) {
        mTvHistory.setVisibility(isActive ? View.VISIBLE : View.GONE);
        mLayData.setVisibility(isActive ? View.VISIBLE : View.GONE);
        mLayType.setVisibility(isActive ? View.VISIBLE : View.GONE);
        mLayProject.setVisibility(isActive ? View.VISIBLE : View.GONE);
        mLayDoctor.setVisibility(isActive ? View.VISIBLE : View.GONE);
        mLayDepartments.setVisibility(isActive ? View.VISIBLE : View.GONE);
        mLayOrganization.setVisibility(isActive ? View.VISIBLE : View.GONE);
        mLayDisease.setVisibility(isActive ? View.VISIBLE : View.GONE);
        mLayHistory.setVisibility(isActive ? View.VISIBLE : View.GONE);
        mLayAccessory.setVisibility(isActive ? View.VISIBLE : View.GONE);
        mLayLab.setVisibility(isActive ? View.VISIBLE : View.GONE);
        mLayPathology.setVisibility(isActive ? View.VISIBLE : View.GONE);
    }

    @OnClick({R.id.tv_recently_history})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_recently_history://临床历史记录
                Intent intent = new Intent(mContext, ClinicalHistoryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(Global.Bundle.CONVER_ITEM_PATIENT, mPatientId);
                bundle.putString(Global.Bundle.BUNDLE_CATEGORY, Global.Environment.CLINICAL);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    protected void showLayout() {
        mCvRecently.setVisibility(View.VISIBLE);
    }

    @Override
    protected void hiddenLayout() {
        mCvRecently.setVisibility(View.GONE);
    }
}
