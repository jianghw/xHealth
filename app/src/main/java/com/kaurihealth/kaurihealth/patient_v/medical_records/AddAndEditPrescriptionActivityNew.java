package com.kaurihealth.kaurihealth.patient_v.medical_records;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.request_bean.bean.NewPrescriptionBean;
import com.kaurihealth.datalib.request_bean.bean.PrescriptionBean;
import com.kaurihealth.datalib.response_bean.DepartmentDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.department_v.SelectDepartmentLevel1Activity;
import com.kaurihealth.kaurihealth.department_v.SelectDepartmentLevel2Activity;
import com.kaurihealth.kaurihealth.eventbus.AddPrescriptionBeanEvent;
import com.kaurihealth.kaurihealth.eventbus.EditPrescriptionNewBeanEvent;
import com.kaurihealth.kaurihealth.adapter.StringPathViewAdapter;
import com.kaurihealth.kaurihealth.mine_v.personal.EnterHospitalActivity;
import com.kaurihealth.kaurihealth.patient_v.PrescriptionActivity;
import com.kaurihealth.mvplib.patient_p.IAddAndEditPrescriptionView;
import com.kaurihealth.mvplib.patient_p.medical_records.AddAndEditPrescriptionPresenter;
import com.kaurihealth.utilslib.CheckUtils;
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
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * Created by mip on 2016/9/28.
 * 描述： 医患-->患者信息-->处方
 */
public class AddAndEditPrescriptionActivityNew extends BaseActivity implements IAddAndEditPrescriptionView, Validator.ValidationListener {

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
    //处方时间
    @Bind(R.id.tv_add_edit_time)
    TextView tv_add_edit_time;

    /**
     * 图片地址集合，含本地及网络
     */
    private ArrayList<String> paths = new ArrayList<>();
    private PickImage pickImages;//图片选择操作对象
    private StringPathViewAdapter adapter;
    //机构
    public static final int EdithHospitalName = 10;
    private int DepartmentId = -1;
    private PrescriptionBean mPrescriptionBean;
    private Validator validator;
    private boolean isAble;

    @Inject
    AddAndEditPrescriptionPresenter<IAddAndEditPrescriptionView> mPresenter;
    private DoctorPatientRelationshipBean doctorPatientRelationshipBean;
    private DepartmentDisplayBean bean;
    private DoctorDisplayBean myself;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.add_and_edit_prescription;
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
        tv_add_edit_time.setText("处方时间");
        myself = LocalData.getLocalData().getMyself();
        validator = new Validator(this);
        validator.setValidationListener(this);
        EventBus.getDefault().register(this);
    }


    //添加
    @OnClick(R.id.tv_more)
    public void onMoreClick() {
        if (mTvMore.getText().equals(getString(R.string.swipe_tv_compile))) {  //已存在病例
            mTvMore.setText(getString(R.string.title_save));
            //设置Activity里所有控件可操作
            setAllViewsEnable(true, this);
        } else if (mTvMore.getText().equals(getString(R.string.title_save))) {//保存提交
            if (mEtDoctor.getText().toString().trim().length() == 0) {
                displayErrorDialog("医生不能为空");
            } else {
                validator.validate();
            }
        } else {//添加
            if (mEtDoctor.getText().toString().trim().length() == 0) {
                displayErrorDialog("医生不能为空");
            } else {
                validator.validate();
            }
        }
    }

    //#点击事件          科室                    机构               就诊时间
    @OnClick({R.id.et_department, R.id.et_institutions, R.id.et_clinical_time})
    public void onTextClick(View view) {
        switch (view.getId()) {
            case R.id.et_department:
                department();
                break;
            case R.id.et_institutions:
                hospital();
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

    /**
     * 添加操作  跳转到此界面的
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(AddPrescriptionBeanEvent event) {
        mTvMore.setText(getString(R.string.more_add));//添加
        initNewBackBtn("处方");
        doctorPatientRelationshipBean = event.getBean();
        pickImages = new PickImage(paths, this, () -> adapter.notifyDataSetChanged());
        personalOfInformation(doctorPatientRelationshipBean);
        initMustItems();   //初始化必选项
    }

    /**
     * 必填项
     */
    private void initMustItems() {

        mEtClinicalTime.setText(DateUtil.GetNowDate("yyyy-MM-dd"));//设置当前时间
        mEtDepartment.setText(myself.getDoctorInformations().getDepartment().getDepartmentName());//初始化科室
        mEtInstitutions.setText(myself.getDoctorInformations().getHospitalName());//初始化机构
        mEtDoctor.setText(myself.getFullName());//设置医生
    }


    /**
     * 编辑操作  跳转到子Item
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(EditPrescriptionNewBeanEvent event) {
        mTvMore.setText(getString(R.string.swipe_tv_compile));
        isAble = event.getIsAble();
        offCurActivity(isAble);
        //view不可操作
        setAllViewsEnable(false, this);
        mTvMore.setEnabled(true);
        initNewBackBtn("处方");
        mPrescriptionBean = event.getPrescriptionBean();
        List<PrescriptionBean.PrescriptionDocumentsEntity> list = mPrescriptionBean.getPrescriptionDocuments();
        if (!paths.isEmpty()) paths.clear();
        if (list != null && list.size() > 0) {
            for (PrescriptionBean.PrescriptionDocumentsEntity documentsBean : list) {
                if (!documentsBean.isIsDeleted()) paths.add(documentsBean.getdocumentUrl());
            }
        }

        doctorPatientRelationshipBean = event.getDoctorPatientRelationshipBean();
        pickImages = new PickImage(paths, this, () -> adapter.notifyDataSetChanged());
        personalOfInformation(doctorPatientRelationshipBean);
        setData(mPrescriptionBean);
    }

    /**
     * 设置当前界面为不可编辑状态
     */
    private void offCurActivity(boolean isAble) {
        mTvMore.setVisibility(isAble ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置数据
     */
    private void setData(PrescriptionBean bean) {
        //判断是否是自己写的记录
        if (bean.getCreatedBy() != myself.getUserId()) {
            mTvMore.setVisibility(View.GONE);
        }
        mEtClinicalTime.setText(DateUtils.getFormatDate(bean.getDate()));//时间
        mEtDepartment.setText(bean.getDepartmentName());//科室
        mEtInstitutions.setText(bean.getHospital());//医院
        mEtDoctor.setText(bean.getDoctor());//医生
        mEtNote.setText(bean.getComment());//留言
    }

    private void personalOfInformation(DoctorPatientRelationshipBean bean) {
        mTvName.setText(bean.getPatient().getFullName());//患者姓名
        mTvGendar.setText(bean.getPatient().getGender());//患者性别
        mTvAge.setText(DateUtils.getAge(bean.getPatient().getDateOfBirth()) + "");//年龄
    }


    // #Fragment 启动Activity并取回数据 权限有关
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        pickImages.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
        data.putInt("requestcode", EdithHospitalName);
        data.putString(Global.Environment.BUNDLE, Global.Environment.CHOICE);
        skipToForResult(EnterHospitalActivity.class, data, EdithHospitalName);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //有多个事件注意 移除
        removeStickyEvent(AddPrescriptionBeanEvent.class);
        removeStickyEvent(EditPrescriptionNewBeanEvent.class);
        mPresenter.unSubscribe();
        EventBus.getDefault().unregister(this);
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
        setResult(PrescriptionActivity.Update);
        finishCur();
    }

    @Override
    public ArrayList<String> getPathsList() {
        return paths;
    }

    @Override
    public String getKauriHealthId() {
        return doctorPatientRelationshipBean.getPatient().getKauriHealthId();
    }

    /**
     * 得到新的bean
     */
    @Override
    public NewPrescriptionBean getNewPrescriptionBean() {
        NewPrescriptionBean newPrescriptionBean = new NewPrescriptionBean();
        newPrescriptionBean.setDate(DateUtils.getDateConversion(getEtClinicalTime()));//就诊时间
        newPrescriptionBean.setDepartmentId(bean == null ? myself.getDoctorInformations().getDepartmentId() : DepartmentId);//测试科室id
        newPrescriptionBean.setHospital(getEtInstitutions());//得到机构信息
        newPrescriptionBean.setDoctor(getEtDoctor());//医生信息
        newPrescriptionBean.setComment(getComment());//留言
        newPrescriptionBean.setPatientId(doctorPatientRelationshipBean.getPatientId());//患者id
        newPrescriptionBean.setType("处方药");
        newPrescriptionBean.setPrescriptionDetail("无");
        newPrescriptionBean.setDuration(0);
        return newPrescriptionBean;
    }

    /**
     * 成功回调
     */
    @Override
    public void updateSucceed(PrescriptionBean prescriptionBean) {
        switchPageUI("PrescriptionActivity");
    }

    @Override
    public PrescriptionBean getRequestPrescriptionBean() {
        CheckUtils.checkNotNull(mPrescriptionBean, "mPrescriptionBean must be not null");
        PrescriptionBean prescriptionBean = mPrescriptionBean;
        prescriptionBean.setDate(DateUtils.getDateConversion(getEtClinicalTime()));//就诊时间
        prescriptionBean.setDepartmentId(bean == null ? prescriptionBean.getDepartmentId() : DepartmentId);//测试科室id
        prescriptionBean.setHospital(getEtInstitutions());//得到机构信息
        prescriptionBean.setDoctor(getEtDoctor());//医生信息
        prescriptionBean.setComment(getComment());//留言
        prescriptionBean.setPatientId(doctorPatientRelationshipBean.getPatientId());//患者id
        prescriptionBean.setType("处方药");
        prescriptionBean.setPrescriptionDetail("无");
        prescriptionBean.setDuration(0);
        prescriptionBean.setDepartmentName(bean == null ? prescriptionBean.getDepartmentName() : bean.getDepartmentName());
        return prescriptionBean;
    }

    private String getEtClinicalTime() {
        return mEtClinicalTime.getText().toString().trim();
    }

    private String getEtInstitutions() {
        return mEtInstitutions.getText().toString().trim();
    }

    private String getEtDoctor() {
        return mEtDoctor.getText().toString().trim();
    }

    private String getComment() {
        return mEtNote.getText().toString().trim();
    }

    @Override
    public void onValidationSucceeded() {
        //图片图片是否为空，由图片地址集合是否为空判断
        if (paths.isEmpty()) {
            displayErrorDialog("没有图片或者图片无法上传，请上传图片");
        } else {
            if (mTvMore.getText().equals(getString(R.string.title_save))) {
                mPresenter.onSubscribe();   //#请求数据
            } else {
                mPresenter.addNewPatientRecord();  //插入新的处
            }
        }


    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        ValidatorUtils.showValidationMessage(this, errors);
    }
}
