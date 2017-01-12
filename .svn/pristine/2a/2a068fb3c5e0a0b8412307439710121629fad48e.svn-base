package com.kaurihealth.kaurihealth.manager_v.compile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;

import com.kaurihealth.datalib.response_bean.DepartmentDisplayBean;
import com.kaurihealth.datalib.response_bean.HospitalizationBean;
import com.kaurihealth.datalib.response_bean.PatientRecordDisplayDto;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.utilslib.CheckUtils;
import com.kaurihealth.utilslib.controller.AbstractViewController;
import com.kaurihealth.utilslib.date.DateUtils;
import com.kaurihealth.utilslib.widget.CollapsibleGroupView;
import com.kaurihealth.utilslib.widget.RecentlyRecordLayout;
import com.kaurihealth.utilslib.widget.ReviewImageLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jianghw on 2016/12/15.
 * <p/>
 * Describe: 出院记录
 */

class HospitalOutView extends AbstractViewController<PatientRecordDisplayDto> implements CollapsibleGroupView.ChildGroupView {

    @Bind(R.id.lay_out)
    CollapsibleGroupView mLayOut;
    @Bind(R.id.cv_out)
    CardView mCvOut;

    RecentlyRecordLayout mLayDoctor;
    RecentlyRecordLayout mLayDepartments;
    RecentlyRecordLayout mLayOrganization;
    RecentlyRecordLayout mLayCreatedDate;
    ReviewImageLayout mLayDocuments;
    RecentlyRecordLayout mLayComment;

    private LinearLayout mMeasurement;

    HospitalOutView(Context context) {
        super(context);
    }

    @Override
    protected int getResLayoutId() {
        return R.layout.include_hospital_out;
    }

    @Override
    protected void onCreateView(View view) {
        ButterKnife.bind(this, view);

        mLayOut.setChildGroupViewBack(this);
        mLayOut.initInflaterView();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void bindViewData(PatientRecordDisplayDto data) {
        List<HospitalizationBean> hospitalizationBeanList = data.getHospitalization();
        defaultViewData();
        if (!hospitalizationBeanList.isEmpty()) {
            setAdmissionData(hospitalizationBeanList,data);
        }
    }

    private void setAdmissionData(List<HospitalizationBean> hospitalizationBeanList,PatientRecordDisplayDto data) {
        for (HospitalizationBean bean : hospitalizationBeanList) {
            if (bean.getHospitalizationType().contains("出院")) {
                mLayOut.setHintTextHidden(false);
                mLayDoctor.contentText(CheckUtils.checkTextContent(bean.getDoctor()));
                DepartmentDisplayBean department = bean.getDepartment();
                mLayDepartments.contentText(CheckUtils.checkTextContent(department.getDepartmentName()));
                mLayOrganization.contentText(CheckUtils.checkTextContent(bean.getHospital()));
                mLayCreatedDate.contentText(DateUtils.unifiedFormatYearMonthDay(data.getRecordDate()));

                mLayDocuments.initHospitalDocumentPathAdapter(bean.getHospitalizationDocuments());
                mLayComment.contentText(CheckUtils.checkTextContent(bean.getComment()));

                mLayOut.notifyDataSetChanged(mMeasurement);
                break;
            }
        }
    }

    @Override
    protected void defaultViewData() {
        mLayOut.setHintTextHidden(true);
    }

    @Override
    protected void unbindView() {
        ButterKnife.unbind(this);
    }

    @Override
    protected void showLayout() {
        mCvOut.setVisibility(View.VISIBLE);
    }

    @Override
    protected void hiddenLayout() {
        mCvOut.setVisibility(View.GONE);
    }

    @Override
    public int getChildResLayoutId() {
        return R.layout.include_collapsible_group_view_out;
    }

    @Override
    public void onCreateChildView(View view) {
        mMeasurement = (LinearLayout) view.findViewById(R.id.lay_out_measurement);

        mLayDoctor = (RecentlyRecordLayout) view.findViewById(R.id.lay_out_doctor);
        mLayDepartments = (RecentlyRecordLayout) view.findViewById(R.id.lay_out_departments);
        mLayOrganization = (RecentlyRecordLayout) view.findViewById(R.id.lay_out_organization);
        mLayCreatedDate = (RecentlyRecordLayout) view.findViewById(R.id.lay_out_createdDate);
        mLayDocuments = (ReviewImageLayout) view.findViewById(R.id.lay_out_documents);
        mLayComment = (RecentlyRecordLayout) view.findViewById(R.id.lay_out_comment);
    }
}
