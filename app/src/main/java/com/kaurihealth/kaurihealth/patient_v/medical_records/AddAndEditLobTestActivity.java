package com.kaurihealth.kaurihealth.patient_v.medical_records;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.request_bean.bean.NewLabTestDetailDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewLabTestPatientRecordDisplayBean;
import com.kaurihealth.datalib.response_bean.DepartmentDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.LabTestBean;
import com.kaurihealth.datalib.response_bean.PatientDisplayBean;
import com.kaurihealth.datalib.response_bean.PatientRecordDisplayBean;
import com.kaurihealth.datalib.response_bean.RecordDocumentsBean;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.common.util.LabTestUtil;
import com.kaurihealth.kaurihealth.department_v.SelectDepartmentLevel1Activity;
import com.kaurihealth.kaurihealth.department_v.SelectDepartmentLevel2Activity;
import com.kaurihealth.kaurihealth.eventbus.AddLobTestBeanEvent;
import com.kaurihealth.kaurihealth.eventbus.EditLabTestBeanEvent;
import com.kaurihealth.kaurihealth.eventbus.MedicalRecordIdEvent;
import com.kaurihealth.kaurihealth.adapter.StringPathViewAdapter;
import com.kaurihealth.kaurihealth.mine_v.personal.EnterHospitalActivity;
import com.kaurihealth.mvplib.patient_p.medical_records.AddAndEditLobTestPresenter;
import com.kaurihealth.mvplib.patient_p.medical_records.IAddAndEditLobTestView;
import com.kaurihealth.utilslib.CheckUtils;
import com.kaurihealth.utilslib.OnClickUtils;
import com.kaurihealth.utilslib.ValidatorUtils;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.date.DateUtils;
import com.kaurihealth.utilslib.dialog.DialogUtils;
import com.kaurihealth.utilslib.image.GalleryUtil;
import com.kaurihealth.utilslib.image.PickImage;
import com.kaurihealth.utilslib.widget.DateUtil;
import com.kaurihealth.utilslib.widget.TagsGridview;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import biz.kasual.materialnumberpicker.MaterialNumberPicker;
import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * Created by mip on 2016/9/29.
 * 医疗记录->实验室检查->常规血液检查，常规尿液检查，常规粪便检查，特殊检查，其他
 */
public class AddAndEditLobTestActivity extends BaseActivity implements IAddAndEditLobTestView, Validator.ValidationListener {

    @Inject
    AddAndEditLobTestPresenter<IAddAndEditLobTestView> mPresenter;
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
    //上传附件 （十字）
    @Bind(R.id.iv_add_upload)
    ImageView mIvAddUpload;
    //上传附件 -- 放上传图片缩略图的地方
    @Bind(R.id.gv_image)
    TagsGridview mGvImage;
    //备注：
    @Bind(R.id.et_note)
    EditText mEtNote;
    @Bind(R.id.tv_project)
    TextView tv_project;
    //检查项目
    @NotEmpty(message = "检查项目不能为空")
    @Bind(R.id.et_project_test)
    TextView et_project_test;

    /**
     * 图片地址集合，含本地及网络
     */
    private ArrayList<String> paths = new ArrayList<>();
    private PickImage pickImages;//图片选择操作对象
    private StringPathViewAdapter adapter;
    private PatientRecordDisplayBean mPatientRecordDisplayBean;
    private String kauriHealthId;//上传图片对应id
    private DoctorPatientRelationshipBean relationshipBean;//医患关系
    //机构
    public static final int EdithHospitalName = 10;
    private int DepartmentId = 0;
    private String subject;
    private DepartmentDisplayBean bean;
    private MaterialNumberPicker numberpicker;
    private Dialog labtestDialog;
    private NewLabTestDetailDisplayBean[] newLabTestDetailDisplayBean;
    private String[] content;
    private int index;
    private View departmentView;
    private int DoctorPatientId;
    private Validator validator;
    private DoctorDisplayBean myself;


    @Override
    protected int getActivityLayoutID() {
        return R.layout.add_and_edit_lob_test;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        adapter = new StringPathViewAdapter(getApplicationContext(), paths);
        mGvImage.setAdapter(adapter);
    }

    @Override
    protected void initDelayedData() {
        myself = LocalData.getLocalData().getMyself();
        tv_project.setText("检查分类:");
        validator = new Validator(this);
        validator.setValidationListener(this);
        EventBus.getDefault().register(this);
        departmentView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.numberpickerdialog, null);
        numberpicker = (MaterialNumberPicker) departmentView.findViewById(R.id.numberpicker);
        TextView tv_complete = (TextView) departmentView.findViewById(R.id.tv_complete);
        tv_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLabTest();
            }
        });
        labtestDialog = DialogUtils.createDialog(departmentView, this);
        setNumberPicker(numberpicker, content);
    }

    private void chooseLabTest() {
        if (labtestDialog.isShowing()) {
            labtestDialog.dismiss();
        } else {
            labtestDialog.show();
        }
    }

    /**
     * 点击检查项目
     */
    private void setLabTest() {
        labtestDialog.dismiss();
        index = numberpicker.getValue();
        et_project_test.setText(content[index]);
//        bean.labTest = newLabTestDetailDisplayBean[index];
    }

    private void setNumberPicker(MaterialNumberPicker numberpicker, String[] content) {
        numberpicker.setDisplayedValues(null);
        numberpicker.setMaxValue(content.length - 1);
        numberpicker.setMinValue(0);
        numberpicker.setValue(1);
        numberpicker.setDisplayedValues(content);
    }

    //添加
    @OnClick(R.id.tv_more)
    public void onMoreClick() {
        if (mTvMore.getText().equals(getString(R.string.swipe_tv_compile))) {  //已存在病例
            mTvMore.setText(getString(R.string.title_save));
            //设置Activity里所有控件可操作
            setAllViewsEnable(true, this);
        } else if (mTvMore.getText().equals(getString(R.string.title_save))) {//保存提交
            if (mEtDoctor.getText().toString().trim().length()==0){
                displayErrorDialog("医生不能为空");
            }else{
                validator.validate();
            }
        } else {//添加
            if (mEtDoctor.getText().toString().trim().length() == 0){
                displayErrorDialog("医生不能为空");
            }else{
                validator.validate();
            }
        }
    }

    //#点击事件          科室                    机构               就诊时间
    @OnClick({R.id.et_department, R.id.et_institutions, R.id.et_clinical_time, R.id.et_project_test})
    public void onTextClick(View view) {
        switch (view.getId()) {
            case R.id.et_department:
                department();
                break;
            case R.id.et_institutions:
                hospital();
                break;
            case R.id.et_project_test:
                chooseLabTest();
                break;
            case R.id.et_clinical_time:
                DialogUtils.showDateDialog(this, (year, month, day) -> {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(year);
                    stringBuilder.append("-");
                    stringBuilder.append(month);
                    stringBuilder.append("-");
                    stringBuilder.append(day);
                    String time = stringBuilder.toString();
                    //就诊时间
                    mEtClinicalTime.setText(time);
                });
                break;
            default:
                break;
        }
    }

    /**
     * ----------------------------订阅事件----------------------------------
     * 点击事件 跳转 编辑项目
     **/
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(EditLabTestBeanEvent event) {
        mTvMore.setText(getString(R.string.swipe_tv_compile));
        //view不可操作
        setAllViewsEnable(false, this);
        mTvMore.setEnabled(true);
        mPatientRecordDisplayBean = event.getPatientRecordDisplayBean();
        subject = mPatientRecordDisplayBean.getSubject();
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

        newLabTestDetailDisplayBean = LabTestUtil.getNewLabTestDetailDisplayBean(subject);
        content = new String[newLabTestDetailDisplayBean.length];
        for (int i = 0; i < newLabTestDetailDisplayBean.length; i++) {
            content[i] = newLabTestDetailDisplayBean[i].labTestType;
        }
        labtestDialog = DialogUtils.createDialog(departmentView, this);
        setNumberPicker(numberpicker, content);
    }


    /**
     * 添加操作  跳转到此界面的
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(AddLobTestBeanEvent event) {
        setMustItems();

        String category = event.getCategory();
        subject = event.getSubject();
        mTvMore.setText(getString(R.string.more_add));//添加
        initNewBackBtn(subject);
        setTvCategory(category);//类型
        setTvSubject(subject);//项目

        relationshipBean = event.getDoctorPatientRelationshipBean();

        DoctorPatientId = relationshipBean.getDoctorPatientId();
        PatientDisplayBean patient = relationshipBean.getPatient();
        kauriHealthId = patient.getKauriHealthId();
        pickImages = new PickImage(paths, this, () -> adapter.notifyDataSetChanged());
        personalOfInformation(relationshipBean);

        newLabTestDetailDisplayBean = LabTestUtil.getNewLabTestDetailDisplayBean(subject);
        content = new String[newLabTestDetailDisplayBean.length];
        for (int i = 0; i < newLabTestDetailDisplayBean.length; i++) {
            content[i] = newLabTestDetailDisplayBean[i].labTestType;
        }
        labtestDialog = DialogUtils.createDialog(departmentView, this);
        setNumberPicker(numberpicker, content);
    }

    private void setMustItems() {


        mEtClinicalTime.setText(DateUtil.GetNowDate("yyyy-MM-dd"));//设置当前时间
        mEtDepartment.setText(myself.getDoctorInformations().getDepartment().getDepartmentName());//初始化科室
        mEtInstitutions.setText(myself.getDoctorInformations().getHospitalName());//初始化机构
        mEtDoctor.setText(myself.getFullName());//设置医生

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
        setEtNote(bean.getComment());//备注
        setLabTestType(bean.getLabTest().getLabTestType());//检查类型
    }

    /**
     * 信息设置
     */
    private void personalOfInformation(DoctorPatientRelationshipBean relationshipBean) {
        Date endDate = relationshipBean.getEndDate();
        boolean isActive = DateUtils.isActive(relationshipBean.isIsActive(), endDate);
        mTvMore.setVisibility(isActive ? View.VISIBLE : View.GONE);
        PatientDisplayBean patient = relationshipBean.getPatient();
        setTvName(patient.getFullName());
        setTvGendar(patient.getGender());
        int age = DateUtils.getAge(patient.getDateOfBirth());
        setTvAge(age + "岁");
    }

    //点击科室
    public void department() {
        Bundle bundle = new Bundle();
        bundle.putString(Global.Environment.BUNDLE, Global.Environment.CHOICE);
        skipToForResult(SelectDepartmentLevel1Activity.class, bundle, SelectDepartmentLevel1Activity.ToDepartmentLevel);
    }

    //点击机构
    public void hospital() {
        Bundle data = new Bundle();
        data.putString("hospitalName", getTvValue(mEtInstitutions));
        data.putString(Global.Environment.BUNDLE, Global.Environment.CHOICE);
        skipToForResult(EnterHospitalActivity.class, data, EdithHospitalName);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //有多个事件注意 移除
        removeStickyEvent(AddLobTestBeanEvent.class);
        removeStickyEvent(EditLabTestBeanEvent.class);
        mPresenter.unSubscribe();
        EventBus.getDefault().unregister(this);
    }

    @Override
    //第一个参数为请求码，即调用startActivityForResult()传递过去的值
    //第二个参数为结果码，结果码用于标识返回数据来自哪个新Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        pickImages.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case EdithHospitalName:
                if (resultCode == RESULT_OK) {
                    String hospitalName = data.getStringExtra("hospitalName");
                    setHospitalName(hospitalName);
                }
                break;
            case SelectDepartmentLevel1Activity.ToDepartmentLevel:
                if (resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    bean = (DepartmentDisplayBean) extras.getSerializable(SelectDepartmentLevel2Activity.DepartmentLevel2ActivityKey);
                    DepartmentId = bean.getDepartmentId();
                    mEtDepartment.setText(bean.getDepartmentName());
                }
                break;
        }
    }

    //  #上传附件 （十字）
    @OnClick(R.id.iv_add_upload)
    public void imageClick() {
        pickImages.pickImage();
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

//    @OnItemLongClick(R.id.gv_image)
//    public void picture(int position) {
//
//    }
    // #Fragment 启动Activity并取回数据 权限有关
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        pickImages.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 重新设置医院name
     *
     * @param HospitalName
     */
    private void setHospitalName(String HospitalName) {
        mEtInstitutions.setText(HospitalName);
    }

    @Override
    public void switchPageUI(String className) {
        if (relationshipBean.getRelationshipReason().equals("远程医疗咨询")
                && relationshipBean.getEndDate() == null) {
            mPresenter.RequestEndDoctorPatientRelationship();
        }else {
            dismissInteractionDialog();
            finishPage();
        }
    }


    @Override
    public void finishPage() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putBoolean("ADDEND", true);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finishCur();
    }

    /**
     * ``````````重载方法`````````
     **/

    @Override
    public PatientRecordDisplayBean getRequestPatientRecordBean() {
        PatientRecordDisplayBean recordDisplayBean = getPatientRecordBean();
        LabTestBean labTest = recordDisplayBean.getLabTest();
        labTest.setLabTestType(content[index]);//获取实验室测试类型
        recordDisplayBean.setPatientId(getDoctorPatientRelationshipBean().getPatientId());//患者id
        recordDisplayBean.setComment(getEtNote());//留言
        recordDisplayBean.setRecordDate(DateUtils.getDateConversion(getEtClinicalTime()));//记录日期
        recordDisplayBean.setDoctor(getEtDoctor());
        recordDisplayBean.setHospital(getEtInstitutions());
        recordDisplayBean.setDepartmentId(bean == null ? recordDisplayBean.getDepartmentId() : DepartmentId);
        recordDisplayBean.setSubject(recordDisplayBean.getSubject());
        recordDisplayBean.setStatus("1");
        recordDisplayBean.setLabTest(labTest);
        return recordDisplayBean;
    }

    @Override
    public PatientRecordDisplayBean getPatientRecordBean() {
        CheckUtils.checkNullArgument(mPatientRecordDisplayBean, "PatientRecordBean is null");
        return mPatientRecordDisplayBean;
    }

    @Override
    public ArrayList<String> getPathsList() {
        return paths;
    }

    @Override
    public String getKauriHealthId() {
        return kauriHealthId;
    }

    /**
     * 得到新的请求bean
     *
     * @return
     */
    @Override
    public NewLabTestPatientRecordDisplayBean getNewLabTestPatientRecordDisplayBean() {
        NewLabTestPatientRecordDisplayBean newLabTestPatientRecordDisplayBean =
                new NewLabTestPatientRecordDisplayBean();
        NewLabTestPatientRecordDisplayBean.LabTestBean labTest = new NewLabTestPatientRecordDisplayBean.LabTestBean();
        labTest.setLabTestType(content[index]);//获取实验室测试类型
        newLabTestPatientRecordDisplayBean.setPatientId(getDoctorPatientRelationshipBean().getPatientId());//患者id
        newLabTestPatientRecordDisplayBean.setComment(getEtNote());//留言
        newLabTestPatientRecordDisplayBean.setRecordDate(DateUtils.getDateConversion(getEtClinicalTime()));//记录日期
        newLabTestPatientRecordDisplayBean.setDoctor(getEtDoctor());
        newLabTestPatientRecordDisplayBean.setHospital(getEtInstitutions());
        newLabTestPatientRecordDisplayBean.setDepartmentId(bean == null ? myself.getDoctorInformations().getDepartmentId() : DepartmentId);
        newLabTestPatientRecordDisplayBean.setSubject(subject);
        newLabTestPatientRecordDisplayBean.setStatus("1");
        newLabTestPatientRecordDisplayBean.setLabTest(labTest);
        return newLabTestPatientRecordDisplayBean;
    }

    @Override
    public void updateSucceed(PatientRecordDisplayBean patientRecordDisplayBean) {
        EventBus.getDefault().postSticky(
                new MedicalRecordIdEvent(
                        getDoctorPatientRelationshipBean().getPatientId(), getDoctorPatientRelationshipBean(), true));
        switchPageUI(Global.Jump.MedicalRecordActivity);
    }

    @Override
    public DoctorPatientRelationshipBean getDoctorPatientRelationshipBean() {
        CheckUtils.checkNullArgument(relationshipBean, "DoctorPatientRelationshipBean is null");
        return relationshipBean;
    }

    /**
     * 获得医患关系id
     *
     * @return
     */
    @Override
    public int getDoctorPatientId() {
        return DoctorPatientId;
    }


    public String getEtDoctor() {
        return mEtDoctor.getText().toString().trim();
    }

    public void setEtDoctor(String etDoctor) {
        mEtDoctor.setText(etDoctor);
    }

    public String getEtInstitutions() {
        return mEtInstitutions.getText().toString().trim();
    }


    public void setEtInstitutions(String etInstitutions) {
        mEtInstitutions.setText(etInstitutions);
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

    public String getEtNote() {
        return mEtNote.getText().toString().trim();
    }

    public void setEtNote(String etNote) {
        mEtNote.setText(etNote);
    }

    public String getEtClinicalTime() {
        return mEtClinicalTime.getText().toString().trim();
    }

    public void setEtClinicalTime(String etClinicalTime) {
        mEtClinicalTime.setText(etClinicalTime);
    }

    public String getEtDepartment() {
        return mEtDepartment.getText().toString().trim();
    }

    public void setEtDepartment(String etDepartment) {
        mEtDepartment.setText(etDepartment);
    }

    public String getLabTestType() {
        return et_project_test.getText().toString().trim();
    }

    public void setLabTestType(String LabTestType) {
        et_project_test.setText(LabTestType);
    }

    @Override
    public void onValidationSucceeded() {
        //判断图片是否为空，由图片地址集合是否为空判断
        if (paths.isEmpty()) {
            displayErrorDialog("没有图片或图片无法上传，请重新上传图片");
        } else {
            if (mTvMore.getText().equals(getString(R.string.title_save))) {
                if (OnClickUtils.onNoDoubleClick()) return;
                mPresenter.onSubscribe();   //#请求数据
            }
            if (OnClickUtils.onNoDoubleClick()) return;
            mPresenter.addNewPatientRecord();  //插入新的实验室检查
        }
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        ValidatorUtils.showValidationMessage(this, errors);
    }
}
