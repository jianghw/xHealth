package com.kaurihealth.kaurihealth.patient_v.medical_records;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.request_bean.bean.NewOutpatientRecordDetailDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewPatientRecordDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewRecordDisplayBean;
import com.kaurihealth.datalib.response_bean.DepartmentDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.OutpatientRecordBean;
import com.kaurihealth.datalib.response_bean.PatientDisplayBean;
import com.kaurihealth.datalib.response_bean.PatientRecordDisplayBean;
import com.kaurihealth.datalib.response_bean.RecordBean;
import com.kaurihealth.datalib.response_bean.RecordDocumentsBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.StringPathViewAdapter;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.department_v.SelectDepartmentLevel1Activity;
import com.kaurihealth.kaurihealth.department_v.SelectDepartmentLevel2Activity;
import com.kaurihealth.kaurihealth.eventbus.MedicalRecordIdEvent;
import com.kaurihealth.kaurihealth.eventbus.OutpatientElectronicAddBeanEvent;
import com.kaurihealth.kaurihealth.eventbus.OutpatientElectronicBeanEvent;
import com.kaurihealth.kaurihealth.mine_v.personal.EnterHospitalActivity;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.mvplib.patient_p.medical_records.IOutpatientElectronicView;
import com.kaurihealth.mvplib.patient_p.medical_records.OutpatientElectronicPresenter;
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
import com.rey.material.widget.CheckBox;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;

/**
 * 医患-->医疗记录-->临床诊疗--> 门诊记录电子病历
 * 必填项：就诊时间，科室，机构，医生，主诉/现病史， 辅助检查（CheckBox）,印象/计划
 * 蒋宏伟：模板: 门诊记录电子病历
 */
public class OutpatientElectronicActivity extends BaseActivity implements IOutpatientElectronicView, Validator.ValidationListener {
    @Inject
    OutpatientElectronicPresenter<IOutpatientElectronicView> mPresenter;
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
    private Validator validator;
    private DoctorDisplayBean myself;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_outpatient_electronic;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        adapter = new StringPathViewAdapter(getApplicationContext(), paths);
        mGvImage.setAdapter(adapter);
    }

    @Override
    protected void initDelayedData() {
        myself = LocalData.getLocalData().getMyself();
        EventBus.getDefault().register(this);
        validator = new Validator(this);
        validator.setValidationListener(this);
        // Validator.registerAnnotation(Haggle.class); // Your annotation class instance
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //有多个事件注意 移除
        removeStickyEvent(OutpatientElectronicBeanEvent.class);
        removeStickyEvent(OutpatientElectronicAddBeanEvent.class);
        mPresenter.unSubscribe();
        EventBus.getDefault().unregister(this);
    }

    //#点击事件          科室                    机构               就诊时间
    @OnClick({R.id.et_department, R.id.et_institutions, R.id.et_clinical_time})
    public void onTextClick(View view) {
        switch (view.getId()) {
            case R.id.et_department:
                selectDepartment();
                break;
            case R.id.et_institutions:
                selectHospital();
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

    //点击科室
    public void selectDepartment() {
        Bundle bundle = new Bundle();
        bundle.putString(Global.Environment.BUNDLE, Global.Environment.CHOICE);
        skipToForResult(SelectDepartmentLevel1Activity.class, bundle, SelectDepartmentLevel1Activity.ToDepartmentLevel);
    }

    //点击机构
    public void selectHospital() {
        Bundle data = new Bundle();
        data.putString("hospitalName", getTvValue(mEtInstitutions));
        data.putString(Global.Environment.BUNDLE, Global.Environment.CHOICE);
        skipToForResult(EnterHospitalActivity.class, data, EdithHospitalName);
    }

    //添加
    @OnClick(R.id.tv_more)
    public void onMoreClick() {
        if (mTvMore.getText().equals(getString(R.string.swipe_tv_compile))) {  //已存在病例
            mTvMore.setText(getString(R.string.title_save));
            //设置Activity里所有控件可操作
            setAllViewsEnable(true, this);
        } else if (mTvMore.getText().equals(getString(R.string.title_save))) {//保存提交
            if (OnClickUtils.onNoDoubleClick()) return;
            validator.validate();
        } else {//添加
            if (OnClickUtils.onNoDoubleClick()) return;
            validator.validate();
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
     * 删除图片功能
     * @param position
     * @return
     */
    @OnItemLongClick(R.id.gv_image)
    public boolean deletePicture(int position){
        final PopupMenu popupMenu = new PopupMenu(this, mGvImage);
        Menu menu = popupMenu.getMenu();
        menu.add(Menu.NONE, Menu.FIRST, 0, "删除");
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case Menu.FIRST:
                    paths.remove(position);
                    adapter.notifyDataSetChanged();
                    break;
            }
            return false;
        });
        popupMenu.show();
        return true;
    }


    // #Fragment 启动Activity并取回数据 权限有关
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        pickImages.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //第一个参数为请求码，即调用startActivityForResult()传递过去的值
    //第二个参数为结果码，结果码用于标识返回数据来自哪个新Activity
    @Override
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
            case SelectDepartmentLevel1Activity.ToDepartmentLevel://科室
                if (resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    bean = (DepartmentDisplayBean) extras.getSerializable(SelectDepartmentLevel2Activity.DepartmentLevel2ActivityKey);
                    departmentId = bean.getDepartmentId();
                    mEtDepartment.setText(bean.getDepartmentName());
                }
                break;
        }
    }


    /**
     * ----------------------------订阅事件----------------------------------
     * 点击事件 跳转 编辑项目
     **/
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(OutpatientElectronicBeanEvent event) {
        mTvMore.setText(getString(R.string.title_save));
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
     * 添加操作  跳转到此界面的
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(OutpatientElectronicAddBeanEvent event) {
        mEtDoctor.setEnabled(false);
        String category = event.getCategory();
        String subject = event.getSubject();
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
        setMustItems();
    }

    private void setMustItems() {
        mEtClinicalTime.setText(DateUtil.GetNowDate("yyyy-MM-dd"));//设置当前时间
        mEtDepartment.setText(myself.getDoctorInformations()!=null
                ?myself.getDoctorInformations().getDepartment()!=null
                ?myself.getDoctorInformations().getDepartment().getDepartmentName():"":"");//初始化科室
        mEtInstitutions.setText(myself.getDoctorInformations()!=null?myself.getDoctorInformations().getHospitalName():"");//初始化机构
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

    @Override
    public PatientRecordDisplayBean getPatientRecordBean() {
        CheckUtils.checkNullArgument(mPatientRecordDisplayBean, "PatientRecordBean is null");
        return mPatientRecordDisplayBean;
    }

    @Override
    public DoctorPatientRelationshipBean getDoctorPatientRelationshipBean() {
        CheckUtils.checkNullArgument(relationshipBean, "DoctorPatientRelationshipBean is null");
        return relationshipBean;
    }


    /**
     * 请求bean构造
     */
    @Override
    public PatientRecordDisplayBean getRequestPatientRecordBean() {
        PatientRecordDisplayBean patientRecordBean = getPatientRecordBean();
        OutpatientRecordBean outpatientRecordBean = getOutpatientRecordBean(patientRecordBean);
        outpatientRecordBean.setPresentIllness(getEtMainTell());//目前的病情
        outpatientRecordBean.setIsPastIllnessReviewed(getCbHistoryF());
        outpatientRecordBean.setIsCurrentMedicationUpToDate(getCbHistoryS());
        outpatientRecordBean.setIsCurrentMedicationReviewed(getCbDrugF());
        outpatientRecordBean.setIsCurrentMedicationUpToDate(getCbDrugS());
        outpatientRecordBean.setHrValue(Double.valueOf(!TextUtils.isEmpty(getEtPhysicalHr()) ? getEtPhysicalHr() : "0.0"));//HR
        outpatientRecordBean.setUpperBpValue(Double.valueOf(!TextUtils.isEmpty(getEtPhysicalBp1()) ? getEtPhysicalBp1() : "0.0"));//Bp
        outpatientRecordBean.setLowerBpValue(Double.valueOf(!TextUtils.isEmpty(getEtPhysicalBp2()) ? getEtPhysicalBp2() : "0.0"));//Bp
        outpatientRecordBean.setRrValue(Double.valueOf(!TextUtils.isEmpty(getEtPhysicalRr()) ? getEtPhysicalRr() : "0.0"));
        outpatientRecordBean.setTValue(Double.valueOf(!TextUtils.isEmpty(getEtPhysicalT()) ? getEtPhysicalT() : "0.0"));
        outpatientRecordBean.setPhysicalExamination(getEtPhysicalNote());//身体检查
        outpatientRecordBean.setIsWorkupReviewed(getCbAuxiliaryF());//是否处理审查
        outpatientRecordBean.setIsLongTermMonitoringDataReviewed(getCbAuxiliaryS());//是否审查监测数据
        outpatientRecordBean.setWorkupReviewComment(getEtAuxiliaryNote());
        outpatientRecordBean.setTreatmentAndPlan(getEtImpressionPlan());//治疗计划
        patientRecordBean.setComment(getEtNote());//备注
        patientRecordBean.setRecordDate(DateUtils.getDateConversion(getEtClinicalTime()));//记录日期
        patientRecordBean.setHospital(getEtInstitutions());//机构
        patientRecordBean.setDepartmentId(bean == null ? patientRecordBean.getDepartmentId() : departmentId);  //测试id
        patientRecordBean.setDoctor(getEtDoctor());//医生


        return patientRecordBean;
    }

    /**
     * “添加”请求bean构造
     */
    @Override
    public NewPatientRecordDisplayBean getNewPatientRecordDisplayBean() {
        NewPatientRecordDisplayBean newPatientRecordDisplayBean = new NewPatientRecordDisplayBean();
        newPatientRecordDisplayBean.setSubject(getTvSubject());
        newPatientRecordDisplayBean.setStatus("1");

        NewOutpatientRecordDetailDisplayBean newOutpatientRecordDetailDisplayBean = new NewOutpatientRecordDetailDisplayBean();
        newOutpatientRecordDetailDisplayBean.setPresentIllness(getEtMainTell());
        newOutpatientRecordDetailDisplayBean.setPastIllnessReviewed(getCbHistoryF());//是否已阅过去的疾病
        newOutpatientRecordDetailDisplayBean.setCurrentMedicationUpToDate(getCbHistoryS());//是否更新已阅的疾病
        newOutpatientRecordDetailDisplayBean.setCurrentMedicationReviewed(getCbDrugF());//是否审查最新的药物
        newOutpatientRecordDetailDisplayBean.setCurrentMedicationUpToDate(getCbDrugS());//否已经更新最新的药物
        newOutpatientRecordDetailDisplayBean.setHrValue(Double.valueOf(!TextUtils.isEmpty(getEtPhysicalHr()) ? getEtPhysicalHr() : "0.0"));//HR
        newOutpatientRecordDetailDisplayBean.setUpperBpValue(Double.valueOf(!TextUtils.isEmpty(getEtPhysicalBp1()) ? getEtPhysicalBp1() : "0.0"));//Bp
        newOutpatientRecordDetailDisplayBean.setLowerBpValue(Double.valueOf(!TextUtils.isEmpty(getEtPhysicalBp2()) ? getEtPhysicalBp2() : "0.0"));//Bp
        newOutpatientRecordDetailDisplayBean.setRrValue(Double.valueOf(!TextUtils.isEmpty(getEtPhysicalRr()) ? getEtPhysicalRr() : "0.0"));
        newOutpatientRecordDetailDisplayBean.settValue(Double.valueOf(!TextUtils.isEmpty(getEtPhysicalT()) ? getEtPhysicalT() : "0.0"));
        newOutpatientRecordDetailDisplayBean.setPhysicalExamination(getEtPhysicalNote());//身体检查
        newOutpatientRecordDetailDisplayBean.setWorkupReviewed(getCbAuxiliaryF());//是否处理审查
        newOutpatientRecordDetailDisplayBean.setLongTermMonitoringDataReviewed(getCbAuxiliaryS());//是否审查监测数据
        newOutpatientRecordDetailDisplayBean.setWorkupReviewComment(getEtAuxiliaryNote());
        newOutpatientRecordDetailDisplayBean.setTreatmentAndPlan(getEtImpressionPlan());//治疗计划

        NewRecordDisplayBean newRecordDisplayBean = new NewRecordDisplayBean();
        newRecordDisplayBean.setOutpatientRecord(newOutpatientRecordDetailDisplayBean);//门诊电子记录的参数
        newRecordDisplayBean.setOwnerId(getDoctorPatientRelationshipBean().getPatientId());//用户ID/病人Id
        newRecordDisplayBean.setRecordType(getTvSubject());//病例类型

        newPatientRecordDisplayBean.setRecordDate(DateUtils.getDateConversion(getEtClinicalTime()));//记录日期
        newPatientRecordDisplayBean.setComment(getEtNote());//留言
        newPatientRecordDisplayBean.setPatientId(getDoctorPatientRelationshipBean().getPatientId());
        newPatientRecordDisplayBean.setHospital(getEtInstitutions());//医院
        newPatientRecordDisplayBean.setDoctor(getEtDoctor());//医生

        newPatientRecordDisplayBean.setDepartmentId(departmentId == 0 ? myself.getDoctorInformations().getDepartmentId() : departmentId);//科室ID
        newPatientRecordDisplayBean.setRecord(newRecordDisplayBean);

        return newPatientRecordDisplayBean;
    }

    @Override
    public ArrayList<String> getPathsList() {
        return paths;
    }

    @Override
    public String getKauriHealthId() {
        return kauriHealthId;
    }

    @Override
    public void updateSucceed(PatientRecordDisplayBean patientRecordDisplayBean) {
        //TODO 提示前页刷新
        EventBus.getDefault().postSticky(
                new MedicalRecordIdEvent(
                        getDoctorPatientRelationshipBean().getPatientId(), getDoctorPatientRelationshipBean(), true));
        switchPageUI(Global.Jump.MedicalRecordActivity);
    }

    @Override
    public void switchPageUI(String className) {
        if (relationshipBean.getRelationshipReason().equals("远程医疗咨询")
                && relationshipBean.getEndDate() == null) {
            mPresenter.RequestEndDoctorPatientRelationship();
        } else {
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

    //表单验证成功，请求数据
    @Override
    public void onValidationSucceeded() {
        if (mTvMore.getText().equals(getString(R.string.title_save))) {  //"保存"-->编辑状态
            mPresenter.onSubscribe();   //#请求数据
        } else {        //"添加"--> 创建新的医疗记录状态
            mPresenter.addNewPatientRecord();  //插入新的临床诊疗记录
        }
    }

    //表单验证失败，弹出错误提示框
    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        ValidatorUtils.showValidationMessage(this, errors);
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


   /* *//**
     * 辅助检查CheckBox二选一，Validator自定义规则
     *//*
    @ValidateUsing(HaggleRule.class)
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public  @interface  Haggle{
        public int messageResId()  default  -1;                 //Mandatory attribute
        public String message()    default  "辅助检查不能为空"; //Mandatory attribute
        public int sequence()      default  -1;         //Mandatory attribute




    }


    //内部类-->辅助检查单选框选择其一就表单通过
     class HaggleRule extends AnnotationRule<Haggle,Boolean>{

        protected  HaggleRule (Haggle haggle){
            super(haggle);
        }

        @Override
        public boolean isValid(Boolean isChecked) {
            isChecked = false;
            if (!mCbAuxiliaryF.isChecked()&&!mCbAuxiliaryS.isChecked()){   //只有

               isChecked = true;
            }
            return isChecked;
        }
    }*/


}
