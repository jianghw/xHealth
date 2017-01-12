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
import com.kaurihealth.datalib.request_bean.bean.NewPathologyPatientRecordDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewSupplementaryTestPatientRecordDisplayBean;
import com.kaurihealth.datalib.response_bean.DepartmentDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
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

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

/**
 * Created by mip on 2016/12/29.
 * <p/>
 * Describe: 辅助检查通用view
 */

class AccessoryRecordCommonSaveView extends AbstractViewController<PatientRecordDisplayDto>
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

    private Activity mActivity;

    private String mTitle;

    private int departmentId;

    private DoctorDisplayBean myself;

    AccessoryRecordCommonSaveView(Context context, String title) {
        super(context);
        this.mTitle = title;
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
    protected void bindViewData(PatientRecordDisplayDto bean) {
        initListenerControls();
        defaultViewData();//展开、收起 提示显示
        if (bean != null) {
            setDate(bean);
        }
    }

    /**
     * 数据设置
     */
    private void setDate(PatientRecordDisplayDto bean) {

        mLayAdmission.setHintTextHidden(true);//true  为隐藏提示信息
        mLayAdmission.notifyDataSetChanged(mMeasurement);
        mLayCreatedDate.titleText("咨询时间：");
        mLayAdmission.setCollapsibleTitle(mTitle);//设置标题
        mLayDoctor.contentText(CheckUtils.checkTextContent(bean.getDoctor()));
//        DepartmentDisplayBean department = bean.getDepartment();
//        mLayDepartments.contentText(CheckUtils.checkTextContent(department.getDepartmentName()));
        mLayDepartments.contentText(bean.getDepartment() != null ? CheckUtils.checkTextContent(bean.getDepartment().getDepartmentName()) : null);
        mLayOrganization.contentText(CheckUtils.checkTextContent(bean.getHospital()));
        mLayCreatedDate.contentText(DateUtils.unifiedFormatYearMonthDay(bean.getRecordDate()).equals("暂无时间记录")?
                DateUtils.Today():DateUtils.unifiedFormatYearMonthDay(bean.getRecordDate()));
//        mLayHospitalNumber.contentText(CheckUtils.checkTextContent(bean.getHospitalNumber()));

        mLayDocuments.initRecordDocumentPathAdapter(bean.getRecordDocuments());
        mLayComment.contentText(CheckUtils.checkTextContent(bean.getComment()));

        mLayAdmission.notifyDataSetChanged(mMeasurement);
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
        mLayHospitalNumber.setVisibility(View.GONE);
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
        PatientRecordDisplayDto recordDisplayBean = mBeanData;
        recordDisplayBean.setRecordDate(DateUtils.getDateSubmit(mLayCreatedDate.getContent()));
        recordDisplayBean.setHospital(mLayOrganization.getContent());//医院
        recordDisplayBean.setDoctor(mLayDoctor.getContent());//医生
        recordDisplayBean.setComment(mLayComment.getContent());//留言
        recordDisplayBean.setDepartmentId(departmentId == 0 ? recordDisplayBean.getDepartmentId() : departmentId);//测试科室id
        return recordDisplayBean;
    }

    /**
     * 插入新的辅助检查请求bean
     *
     * @return
     */
    public NewSupplementaryTestPatientRecordDisplayBean getNewSupplementaryTestPatientRecordDisplayBean() {
        myself = LocalData.getLocalData().getMyself();
        NewSupplementaryTestPatientRecordDisplayBean displayBean = new NewSupplementaryTestPatientRecordDisplayBean();
        displayBean.setPatientId(mBeanData.getPatientId());//患者id
        displayBean.setComment(mLayComment.getContent());//留言
        displayBean.setRecordDate(DateUtils.getDateSubmit(mLayCreatedDate.getContent()));//就诊日期
        displayBean.setDoctor(mLayDoctor.getContent());//doctor名字
        displayBean.setHospital(mLayOrganization.getContent());//医院
        displayBean.setDepartmentId(departmentId == 0 ? myself.getDoctorInformations().getDepartmentId() : departmentId);//测试科室id
        displayBean.setSubject(mTitle);//科目
        displayBean.setStatus("1");
        return displayBean;
    }

    /**
     * 插入新的病理bean
     *
     * @return
     */
    public NewPathologyPatientRecordDisplayBean getNewPathologyPatientRecordDisplayBean() {
        myself = LocalData.getLocalData().getMyself();
        NewPathologyPatientRecordDisplayBean newPathologyPatientRecordDisplayBean =
                new NewPathologyPatientRecordDisplayBean();
        newPathologyPatientRecordDisplayBean.setPatientId(mBeanData.getPatientId());//患者id
        newPathologyPatientRecordDisplayBean.setComment(mLayComment.getContent());//留言
        newPathologyPatientRecordDisplayBean.setRecordDate(DateUtils.getDateSubmit(mLayCreatedDate.getContent()));//就诊日期
        newPathologyPatientRecordDisplayBean.setDoctor(mLayDoctor.getContent());//doctor名字
        newPathologyPatientRecordDisplayBean.setHospital(mLayOrganization.getContent());//医院
        newPathologyPatientRecordDisplayBean.setDepartmentId(departmentId == 0 ? myself.getDoctorInformations().getDepartmentId() : departmentId);//测试科室id
        newPathologyPatientRecordDisplayBean.setSubject(mTitle);//科目
        newPathologyPatientRecordDisplayBean.setStatus("1");
        return newPathologyPatientRecordDisplayBean;
    }

    //空判断
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
        }
        return b;
    }

}
