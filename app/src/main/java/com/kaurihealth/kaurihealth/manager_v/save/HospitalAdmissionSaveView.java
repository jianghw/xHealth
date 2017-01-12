package com.kaurihealth.kaurihealth.manager_v.save;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.request_bean.bean.NewHospitalizationPatientRecordDisplayBean;
import com.kaurihealth.datalib.response_bean.DepartmentDisplayBean;
import com.kaurihealth.datalib.response_bean.HospitalizationBean;
import com.kaurihealth.datalib.response_bean.PatientBean;
import com.kaurihealth.datalib.response_bean.PatientRecordDisplayDto;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.department_v.SelectDepartmentLevel1Activity;
import com.kaurihealth.kaurihealth.department_v.SelectDepartmentLevel2Activity;
import com.kaurihealth.kaurihealth.mine_v.personal.EnterHospitalActivity;
import com.kaurihealth.utilslib.CheckUtils;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.controller.AbstractViewController;
import com.kaurihealth.utilslib.date.DateUtils;
import com.kaurihealth.utilslib.dialog.DialogUtils;
import com.kaurihealth.utilslib.widget.CollapsibleGroupView;
import com.kaurihealth.utilslib.widget.RecentlySaveLayout;
import com.kaurihealth.utilslib.widget.SaveImageLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

/**
 * Created by jianghw on 2016/12/15.
 * <p/>
 * Describe: 入院记录保存页面
 */

class HospitalAdmissionSaveView extends AbstractViewController<PatientRecordDisplayDto>
        implements CollapsibleGroupView.ChildGroupView, RecentlySaveLayout.IEditTextClick, SaveImageLayout.IModifiedImage {

    @Bind(R.id.lay_admission)
    CollapsibleGroupView mLayAdmission;
    @Bind(R.id.cv_admission)
    CardView mCvAdmission;

    RecentlySaveLayout mLayDoctor;
    RecentlySaveLayout mLayDepartments;
    RecentlySaveLayout mLayOrganization;
    RecentlySaveLayout mLayCreatedDate;
    RecentlySaveLayout mLayHospitalNumber;
    SaveImageLayout mLayDocuments;
    RecentlySaveLayout mLayComment;

    private LinearLayout mMeasurement;

    /**
     * 危险
     */
    private Activity mActivity;
    private int departmentId;
    /**
     * 提交备案bean
     */
    private NewHospitalizationPatientRecordDisplayBean newHospitalizationPatientRecordDisplayBean;

    HospitalAdmissionSaveView(Context context) {
        super(context);
    }

    @Override
    protected int getResLayoutId() {
        return R.layout.include_hospital_admission;
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
        //初始化图片选择器
        mLayDocuments.onImageModifiedListener(this);
        mLayDocuments.initLayoutView(mActivity);
        //删除键
        mLayDocuments.initGridViewDeleteImage(mActivity);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void bindViewData(PatientRecordDisplayDto data) {
        initListenerControls();

        defaultViewData();

        List<HospitalizationBean> hospitalizationBeanList = data.getHospitalization();
        if (hospitalizationBeanList != null && !hospitalizationBeanList.isEmpty()) {
            setAdmissionData(hospitalizationBeanList, data);
        }

        //必填项
//        if (data.mark != null) {
//            mLayDoctor.contentText(CheckUtils.checkTextContent(data.getDoctor()));
//            mLayDepartments.contentText(CheckUtils.checkTextContent(data.getDepartment().getDepartmentName()));
//            mLayOrganization.contentText(CheckUtils.checkTextContent(data.getHospital()));
//            mLayCreatedDate.contentText(DateUtils.unifiedFormatYearMonthDay(data.getRecordDate()).equals("暂无时间记录") ?
//                    DateUtils.Today() : DateUtils.unifiedFormatYearMonthDay(data.getRecordDate()));
//        }
    }

    private void setAdmissionData(List<HospitalizationBean> hospitalizationBeanList, PatientRecordDisplayDto data) {
        for (HospitalizationBean bean : hospitalizationBeanList) {
            if (bean.getHospitalizationType().contains("入院")) {
                mLayAdmission.setHintTextHidden(true);//true 隐藏提示
                mLayDoctor.contentText(CheckUtils.checkTextContent(bean.getDoctor()));
                DepartmentDisplayBean department = bean.getDepartment();
                mLayDepartments.contentText(CheckUtils.checkTextContent(department.getDepartmentName()));
                mLayOrganization.contentText(CheckUtils.checkTextContent(bean.getHospital()));
                mLayCreatedDate.contentText(DateUtils.unifiedFormatYearMonthDay(bean.getCreatedDate()));
                mLayHospitalNumber.contentText(CheckUtils.checkTextContent(bean.getHospitalNumber()));

                mLayDocuments.initDocumentPathAdapter(bean.getHospitalizationDocuments());
                //可删除
                mLayComment.contentText(CheckUtils.checkTextContent(bean.getComment()));
                break;
            }
        }
    }

    @Override
    protected void defaultViewData() {
        mLayAdmission.setHintTextHidden(true);
    }

    @Override
    protected void unbindView() {
        ButterKnife.unbind(this);
    }

    @Override
    protected void showLayout() {
        mCvAdmission.setVisibility(View.VISIBLE);
    }

    @Override
    protected void hiddenLayout() {
        mCvAdmission.setVisibility(View.GONE);
    }

    @Override
    public int getChildResLayoutId() {
        return R.layout.include_collapsible_group_view_admission_save;
    }

    @Override
    public void onCreateChildView(View view) {
        mMeasurement = (LinearLayout) view.findViewById(R.id.lay_admission_measurement);

        mLayDoctor = (RecentlySaveLayout) view.findViewById(R.id.lay_admission_doctor);
        mLayDepartments = (RecentlySaveLayout) view.findViewById(R.id.lay_admission_departments);
        mLayOrganization = (RecentlySaveLayout) view.findViewById(R.id.lay_admission_organization);
        mLayCreatedDate = (RecentlySaveLayout) view.findViewById(R.id.lay_admission_createdDate);
        mLayHospitalNumber = (RecentlySaveLayout) view.findViewById(R.id.lay_admission_hospitalNumber);
        mLayDocuments = (SaveImageLayout) view.findViewById(R.id.lay_admission_documents);
        mLayComment = (RecentlySaveLayout) view.findViewById(R.id.lay_admission_comment);
    }

    /**
     * 重新测量控件高度
     */
    @Override
    public void redrawGroupView() {
        mLayAdmission.notifyDataSetChanged(mMeasurement);
    }

    /**
     * 点击事件回调
     */
    @Override
    public void contentEditTextBack(View v, String content) {
        switch (v.getId()) {
            case R.id.lay_admission_departments:
                selectDepartment();
                break;
            case R.id.lay_admission_organization:
                selectHospital(content);
                break;
            case R.id.lay_admission_createdDate:
                DialogUtils.showDateDialog(mActivity, (year, month, day) -> {
                    String time = year + "/"
                            + String.format(mContext.getResources().getString(R.string.date_month), Integer.valueOf(month)) + "/"
                            + String.format(mContext.getResources().getString(R.string.date_month), Integer.valueOf(day));
                    mLayCreatedDate.contentText(time);
                });
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
        mActivity.startActivityForResult(intent, Global.RequestCode.DEPARTMENT_1);
    }

    //选择医院，界面跳转
    private void selectHospital(String content) {
        Intent intent = new Intent(mContext, EnterHospitalActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("hospitalName", content);
        bundle.putString(Global.Environment.BUNDLE, Global.Environment.CHOICE);
        intent.putExtras(bundle);
        mActivity.startActivityForResult(intent, Global.RequestCode.HOSPITAL_1);
    }

    public void childNeedActivity(Activity clazz) {
        this.mActivity = clazz;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mLayDocuments.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Global.RequestCode.DEPARTMENT_1:
                if (resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    setDepartment(extras);
                }
                break;
            case Global.RequestCode.HOSPITAL_1:
                if (resultCode == RESULT_OK) {
                    String hospitalName = data.getStringExtra("hospitalName");
                    setHospitalName(hospitalName);
                }
                break;
        }
    }

    public void onRequestPermissionsResult(int code, String[] permissions, int[] results) {
        mLayDocuments.onRequestPermissionsResult(code, permissions, results);
    }

    private void setDepartment(Bundle bundle) {
        DepartmentDisplayBean bean = (DepartmentDisplayBean) bundle.getSerializable(SelectDepartmentLevel2Activity.DepartmentLevel2ActivityKey);
        departmentId = bean != null ? bean.getDepartmentId() : 0;
        mLayDepartments.contentText(bean != null ? bean.getDepartmentName() : "");
    }

    private void setHospitalName(String hospitalName) {
        mLayOrganization.contentText(hospitalName);
    }

    /**
     * 入院图片
     */
    public List<String> getAdmissionImagePathsList() {
        return mLayDocuments.getImagePathsList();
    }

    /**
     * 入院编辑bean
     */
    public PatientRecordDisplayDto getAdmissionRequestPatientRecordBean() {
        boolean needNew = true;
        PatientRecordDisplayDto recordDisplayBean = mBeanData;

        List<HospitalizationBean> hospitalization = recordDisplayBean.getHospitalization();
        if (hospitalization != null && !hospitalization.isEmpty()) {
            for (HospitalizationBean bean : hospitalization) {
                if (bean.getHospitalizationType().contains("入院")) {
                    bean.setHospitalNumber(mLayHospitalNumber.getContent());
                    bean.setHospital(mLayOrganization.getContent());//医院
                    bean.setCreatedDate(DateUtils.getDateSubmit(mLayCreatedDate.getContent()));
                    bean.setDoctor(mLayDoctor.getContent());//医生
                    bean.setComment(mLayComment.getContent());//留言
                    bean.setDepartmentId(departmentId == 0 ? recordDisplayBean.getDepartmentId() : departmentId);//测试科室id
                    needNew = false;
                }
            }
        }
        if (needNew) {
            HospitalizationBean bean = new HospitalizationBean();
            bean.setPatientRecordId(recordDisplayBean.getPatientRecordId());
            bean.setHospitalizationType("入院记录");
            bean.setHospital(mLayOrganization.getContent());//医院
            bean.setCreatedDate(DateUtils.getDateSubmit(mLayCreatedDate.getContent()));
            bean.setDoctor(mLayDoctor.getContent());//医生
            bean.setComment(mLayComment.getContent());//留言
            bean.setDepartmentId(departmentId == 0 ? recordDisplayBean.getDepartmentId() : departmentId);//测试科室id
            bean.setIsDeleted(false);

            if (hospitalization == null) hospitalization = new ArrayList<>();
            hospitalization.add(bean);
        }
        recordDisplayBean.setHospitalization(hospitalization);
        return recordDisplayBean;
    }

    public NewHospitalizationPatientRecordDisplayBean getAdmissionInsertBean() {
        boolean needNew = true;
        if (newHospitalizationPatientRecordDisplayBean == null)
            newHospitalizationPatientRecordDisplayBean = new NewHospitalizationPatientRecordDisplayBean();

        PatientBean currentPatient = LocalData.getLocalData().getCurrentPatient();
        if (currentPatient != null)
            newHospitalizationPatientRecordDisplayBean.setPatientId(currentPatient.getPatientId());
        newHospitalizationPatientRecordDisplayBean.setSubject("住院记录");
        newHospitalizationPatientRecordDisplayBean.setStatus("发布");

        newHospitalizationPatientRecordDisplayBean.setDepartmentId(departmentId);
        newHospitalizationPatientRecordDisplayBean.setHospital(mLayOrganization.getContent());
        newHospitalizationPatientRecordDisplayBean.setRecordDate(DateUtils.getDateSubmit(mLayCreatedDate.getContent()));
        newHospitalizationPatientRecordDisplayBean.setDoctor(mLayDoctor.getContent());

        List<HospitalizationBean> hospitalization = newHospitalizationPatientRecordDisplayBean.getHospitalization();
        if (hospitalization != null && !hospitalization.isEmpty()) {
            for (HospitalizationBean bean : hospitalization) {
                if (bean.getHospitalizationType().contains("入院")) {
                    bean.setHospitalNumber(mLayHospitalNumber.getContent());
                    bean.setHospital(mLayOrganization.getContent());//医院
                    bean.setCreatedDate(DateUtils.getDateSubmit(mLayCreatedDate.getContent()));
                    bean.setDoctor(mLayDoctor.getContent());//医生
                    bean.setComment(mLayComment.getContent());//留言
                    bean.setDepartmentId(departmentId == 0 ? newHospitalizationPatientRecordDisplayBean.getDepartmentId() : departmentId);//测试科室id
                    needNew = false;
                }
            }
        }
        if (needNew) {
            HospitalizationBean bean = new HospitalizationBean();
            bean.setHospitalizationType("入院记录");
            bean.setHospital(mLayOrganization.getContent());//医院
            bean.setCreatedDate(DateUtils.getDateSubmit(mLayCreatedDate.getContent()));
            bean.setHospitalNumber(mLayHospitalNumber.getContent());
            bean.setDoctor(mLayDoctor.getContent());//医
            bean.setComment(mLayComment.getContent());//留言
            bean.setDepartmentId(departmentId);//测试科室id

            if (hospitalization == null) hospitalization = new ArrayList<>();

            hospitalization.add(bean);
        }
        newHospitalizationPatientRecordDisplayBean.setHospitalization(hospitalization);

        return newHospitalizationPatientRecordDisplayBean;
    }

    public boolean getBoolean() {
        boolean b = false;
        if (mLayDoctor.getContent().equals("")) {
            b = true;
        } else if (mLayDepartments.getContent().equals("")) {
            b = true;
        } else if (mLayOrganization.getContent().equals("")) {
            b = true;
        } else if (mLayCreatedDate.getContent().equals("")) {
            b = true;
        } else if (mLayHospitalNumber.getContent().equals("")) {
            b = true;
        }
        return b;
    }


}
