package com.kaurihealth.kaurihealth.patient_v.medical_records;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.request_bean.bean.NewOnlineConsultationRecordDetailDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewPatientRecordDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewRecordDisplayBean;
import com.kaurihealth.datalib.response_bean.DepartmentDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.OnlineConsultationRecordBean;
import com.kaurihealth.datalib.response_bean.PatientDisplayBean;
import com.kaurihealth.datalib.response_bean.PatientRecordDisplayBean;
import com.kaurihealth.datalib.response_bean.RecordBean;
import com.kaurihealth.datalib.response_bean.RecordDocumentsBean;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.StringPathViewAdapter;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.department_v.SelectDepartmentLevel1Activity;
import com.kaurihealth.kaurihealth.department_v.SelectDepartmentLevel2Activity;
import com.kaurihealth.kaurihealth.eventbus.MedicalRecordIdEvent;
import com.kaurihealth.kaurihealth.eventbus.OutpatientElectronicAddBeanEvent;
import com.kaurihealth.kaurihealth.eventbus.OutpatientElectronicBeanEvent;
import com.kaurihealth.kaurihealth.mine_v.personal.EnterHospitalActivity;
import com.kaurihealth.kaurihealth.util.RadioCheckboxForMaterial;
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
 * 医患-->医疗记录-->临床诊疗--> 网络医疗咨询
 * Created by Garnet_Wu on 2016/9/19.
 */
public class NetworkMedicalConsultationActivity extends BaseActivity implements IOutpatientElectronicView, Validator.ValidationListener{

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
    //类别
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
    //网络咨询原因 --checkbox1
    @Bind(R.id.cb_reason_fir)
    CheckBox mCbReasonFir;
    //网络咨询原因--checkbox2
    @Bind(R.id.cb_reason_sec)
    CheckBox mCbReasonSec;
    //网络咨询原因--checkbox3
    @Bind(R.id.cb_reason_third)
    CheckBox mCbReasonThird;
    //主诉、现病史
    @NotEmpty(message = "主诉/现病史不能为空")
    @Bind(R.id.et_main_tell)
    EditText mEtMainTell;
    //目前主要问题
    @NotEmpty(message = "目前主要问题不能为空")
    @Bind(R.id.et_main_problem)
    EditText mEtMainProblem;
    //目前接受治疗
    @NotEmpty(message = "目前接受治疗不能为空")
    @Bind(R.id.et_current_treatment)
    EditText mEtTreatment;
    //印象/咨询建议
    @NotEmpty(message = "印象/咨询建议不能为空")
    @Bind(R.id.et_impression_advice)
    EditText mEtImpressionAdvice;
    //上传附件 -- 放上传图片缩略图的地方
    @Bind(R.id.gv_image)
    TagsGridview mGvImage;
    //备注：
    @Bind(R.id.et_note)
    EditText mEtNote;

    /**
     * 图片地址集合，含本地及网络
     */
    private  ArrayList<String> paths = new ArrayList<>();
    private StringPathViewAdapter adapter;
    private PickImage pickImages ; //图片选择操作对象
    private PatientRecordDisplayBean mPatientRecordDisplayBean;
    private DoctorPatientRelationshipBean relationshipBean;
    private String kauriHealthId;
    private String etDepartment;
    private String etMainTell;
    private static  final  int EditHospitalName =10;
    private DepartmentDisplayBean bean;
    private int departmentId;
    private int DoctorPatientId;
    private DoctorDisplayBean myself;
    private RadioCheckboxForMaterial raCheckbox;
    private Validator validator;


    //BaseActivity
    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_network_medical_consultation;
    }

    //BaseActivity
    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        adapter = new StringPathViewAdapter(getApplicationContext(), paths);
        mGvImage.setAdapter(adapter);

    }
    //BaseActivity
    @Override
    protected void initDelayedData() {
        myself = LocalData.getLocalData().getMyself();
        validator = new Validator(this);
        validator.setValidationListener(this);
        raCheckbox = new RadioCheckboxForMaterial();
        raCheckbox.regist(0, mCbReasonFir);
        raCheckbox.regist(1, mCbReasonSec);
        raCheckbox.regist(2, mCbReasonThird);
        raCheckbox.setListener();
        //发送消息界面：注册
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeStickyEvent(OutpatientElectronicBeanEvent.class);
        removeStickyEvent(OutpatientElectronicAddBeanEvent.class);
        //发送消息界面：取消消息
        EventBus.getDefault().unregister(this);
    }

    //点击事件       科室                 机构                  就诊时间
    @OnClick({R.id.et_department,R.id.et_institutions,R.id.et_clinical_time})
    public void  onClick(View view){
        switch (view.getId()){
            case R.id.et_department:
                selectDepartment();  //选择科室
                break;
            case R.id.et_institutions:
                selectHospital();
                break;
            case R.id.et_clinical_time:
                DialogUtils.showDateDialog(this,(year,month,day)->{
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


    //选择科室  界面跳转
    private void selectDepartment() {
        Bundle bundle = new Bundle();
        bundle.putString(Global.Environment.BUNDLE, Global.Environment.CHOICE);
        skipToForResult(SelectDepartmentLevel1Activity.class, bundle, SelectDepartmentLevel1Activity.ToDepartmentLevel);
    }

    //选择医院  界面提爱哦逆转
    private void selectHospital() {
        Bundle data = new Bundle();
        data.putString("hospitalName", getTvValue(mEtInstitutions));
        data.putString(Global.Environment.BUNDLE, Global.Environment.CHOICE);
        skipToForResult(EnterHospitalActivity.class, data, EditHospitalName);
    }


    //添加
    @OnClick(R.id.tv_more)
    public void  onMoreClick(){
        if (mTvMore.getText().equals("编辑")){   //已存在病例
            mTvMore.setText("保存");
            //设置Activity里所有控件可操作
            setAllViewsEnable(true,this);
        } else if (mTvMore.getText().equals("保存")){  //保存提交
            if (OnClickUtils.onNoDoubleClick()) return;
            if (mCbReasonFir.isChecked()||mCbReasonSec.isChecked()||mCbReasonThird.isChecked()){   //必须选择一个单选框才能请求数据
                validator.validate();
            }else{
                displayErrorDialog("网络咨询原因不能为空");
            }

        } else{
            if (OnClickUtils.onNoDoubleClick()) return;
            if (mCbReasonFir.isChecked()||mCbReasonSec.isChecked()||mCbReasonThird.isChecked()){
                validator.validate();
            }else{
                displayErrorDialog("网络咨询原因不能为空");
            }
        }
    }

    /**
     *  #上传附件（十字）
     */
    @OnClick(R.id.iv_add_upload)
    public void  imageClick(){
        pickImages.pickImage();
    }

    /**
     * #附件 --放缩略图的地方
     * 点击放大图片
     * @return
     */
    @OnItemClick(R.id.gv_image)
    public  void picture(int position){
        GalleryUtil.toGallery(this,new GalleryUtil.GetUrlsInterface(){
            @Override
            public ArrayList<String> getUrls() {
                return paths;
            }
        },position);
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



    // #Fragment 启动Activity 并取回数据 权限有关
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        pickImages.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        pickImages.onActivityResult(requestCode,resultCode,data);
        switch (requestCode){
            case  EditHospitalName:
                if (resultCode == RESULT_OK) {
                    String hospitalName = data.getStringExtra("hospitalName");
                    setHospitalName(hospitalName);
                }
                break;
            case SelectDepartmentLevel1Activity.ToDepartmentLevel:
                if (resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    bean = (DepartmentDisplayBean)extras.getSerializable(SelectDepartmentLevel2Activity.DepartmentLevel2ActivityKey);
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
    @Subscribe(threadMode = ThreadMode.MAIN ,sticky =  true )
    public  void  eventBusMain(OutpatientElectronicBeanEvent event){
        mTvMore.setText("保存");
        //view 不可操作
//        setAllViewsEnable(false,this);
        mTvMore.setEnabled(true);

        mPatientRecordDisplayBean = event.getPatientRecordDisplayBean();
        List<RecordDocumentsBean> list = mPatientRecordDisplayBean.getRecordDocuments();   //病例图像参数
        if (paths.isEmpty()) {

            paths.clear();
        }
        if (list !=null && list.size()>0) {
            for (RecordDocumentsBean documentsBean:list) {
                if (!documentsBean.isIsDeleted()) {
                    paths.add(documentsBean.getDocumentUrl());
                }
            }
        }

        relationshipBean = event.getDoctorPatientRelationshipBean();
        PatientDisplayBean patient = relationshipBean.getPatient();
        kauriHealthId = patient.getKauriHealthId();
        pickImages = new PickImage(paths, this, () -> adapter.notifyDataSetChanged());

        displayDataByBean(mPatientRecordDisplayBean,relationshipBean);

    }


    /**
     * 添加操作  跳转到此界面
     */
    @Subscribe(threadMode = ThreadMode.MAIN ,sticky = true)
    public void eventBusMain(OutpatientElectronicAddBeanEvent event){
        String category = event.getCategory();
        String subject = event.getSubject();
        mEtDoctor.setEnabled(false);
        mTvMore.setText("添加"); //添加
        initNewBackBtn(subject);
        setTvCategory(category); //类型
        setTvSubject(subject); //项目

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
        int age = DateUtils.getAge(patient.getDateOfBirth());
        setTvAge(age + "岁");
    }

    private void projectByInformation(PatientRecordDisplayBean bean) {
        //判断是否是自己写的记录
        if (bean.getCreatedBy() != myself.getUserId()) {
            mTvMore.setVisibility(View.GONE);
        }
        String subject = bean.getSubject();  //项目
        initNewBackBtn(subject);
        setTvCategory(bean.getCategory()); //类型
        setTvSubject(subject);  //项目
        setEtDoctor(bean.getDoctor());//医生
        setEtDepartment(bean.getDepartment() != null ? bean.getDepartment().getDepartmentName() : "暂无");
        setEtInstitutions(bean.getHospital()); //机构
        setEtClinicalTime(DateUtils.getFormatDate(bean.getRecordDate())); //就诊时间
    }

    //数据显示处理
    private void fillAllCases(PatientRecordDisplayBean bean) {
        OnlineConsultationRecordBean onlineConsultationRecordBean = getOnlineConsultationRecordBean(bean);
        setEtMainTell(onlineConsultationRecordBean.getPresentIllness());  //主诉/现病史
        //TODO onlineConsultationRecordBean网络原因为String 需要判断
        setCbReasonFir(onlineConsultationRecordBean.getOnlineConsultationPurpose());  //网络咨询原因


        setEtMainProblem(onlineConsultationRecordBean.getMajorIssue()); //目前主要问题
        setEtCurrentTreatment(onlineConsultationRecordBean.getCurrentTreatment()); //目前接受治疗
        setEtImpressionAdvice(onlineConsultationRecordBean.getOnlineConsultationDoctorComment()) ; //印象、咨询建议
        setEtNote(bean.getComment());  //备注
    }




    public OnlineConsultationRecordBean getOnlineConsultationRecordBean(PatientRecordDisplayBean bean) {
        RecordBean recordBean = bean.getRecord();
        CheckUtils.checkNullArgument(recordBean,"recordBean is null");
        OnlineConsultationRecordBean onlineConsultationRecord = recordBean.getOnlineConsultationRecord();
        CheckUtils.checkNullArgument(onlineConsultationRecord,"onlineConsultationRecord is null");
        return onlineConsultationRecord;
    }


    /**
     * "保存"操作 向服务器提交数据 请求bean构造
     * @return
     */
    @Override
    public PatientRecordDisplayBean getRequestPatientRecordBean() {
        PatientRecordDisplayBean patientRecordBean = getPatientRecordBean();
        OnlineConsultationRecordBean onlineConsultationRecordBean = getOnlineConsultationRecordBean(patientRecordBean);
        onlineConsultationRecordBean.setPresentIllness(getEtMainTell());  //主诉，现病史
        onlineConsultationRecordBean.setMajorIssue(getEtMainProblem()); //目前主要问题
        onlineConsultationRecordBean.setCurrentTreatment(getCurrentTreatment());   //目前接受治疗
        //网络咨询原因
        onlineConsultationRecordBean.setOnlineConsultationPurpose(getOnlineConsultationPurpose(raCheckbox.getSelectedIndx()));
        onlineConsultationRecordBean.setOnlineConsultationDoctorComment(getEtImpressionAdvice());  //印象、咨询意见
        patientRecordBean.setComment(getNote());  //备注
        patientRecordBean.setRecordDate(DateUtils.getDateConversion(geEtClinicalTime()));  //记录日期
        patientRecordBean.setHospital(getEtInstitutions());//医院
        patientRecordBean.setDepartmentId(bean == null ? patientRecordBean.getDepartmentId() : departmentId);  //科室的ID
        patientRecordBean.setPatientId(getDoctorPatientRelationshipBean().getPatientId());//患者id
        patientRecordBean.setDoctor(getEtDoctor());  //医生
        return patientRecordBean;
    }

    @Override
    public PatientRecordDisplayBean getPatientRecordBean() {
        CheckUtils.checkNullArgument(mPatientRecordDisplayBean,"PatientRecordDisplayBean is null");
        return mPatientRecordDisplayBean;
    }

    @Override
    public DoctorPatientRelationshipBean getDoctorPatientRelationshipBean() {
        CheckUtils.checkNullArgument(relationshipBean ,"DoctorPatientRelationshipBean is null");
        return relationshipBean;
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
     * 网络医疗咨询原因
     */
    private String getOnlineConsultationPurpose(int select) {
        switch (select) {
            case 0:
                return "门诊辅助检查结果与治疗计划更新";
            case 1:
                return "日常复诊";
            case 2:
                return "其它健康问题";
            default:
                return null;
        }
    }

    private int getCheckBox(String str) {
        switch (str) {
            case "门诊辅助检查结果与治疗计划更新":
                return 0;
            case "日常复诊":
                return 1;
            case "其它健康问题":
                return 2;
            default:
                return 0;
        }
    }


    /**
     * 描述："添加"新的临床诊疗记录
     * "添加”请求操作bean
     * @return
     */
    @Override
    public NewPatientRecordDisplayBean getNewPatientRecordDisplayBean() {
        //新添加的
        NewOnlineConsultationRecordDetailDisplayBean newOnlineConsultationRecordDetailDisplayBean = new NewOnlineConsultationRecordDetailDisplayBean();
        newOnlineConsultationRecordDetailDisplayBean.setPresentIllness(getEtMainTell());   //主诉，现病史
        newOnlineConsultationRecordDetailDisplayBean.setOnlineConsultationPurpose(getOnlineConsultationPurpose(raCheckbox.getSelectedIndx()));
        newOnlineConsultationRecordDetailDisplayBean.setMajorIssue(getEtMainProblem());   //目前主要问题
        newOnlineConsultationRecordDetailDisplayBean.setCurrentTreatment(getCurrentTreatment()); //目前接受治疗
        newOnlineConsultationRecordDetailDisplayBean.setOnlineConsultationDoctorComment(getEtImpressionAdvice());  //咨询、印象建议

        NewRecordDisplayBean newRecordDisplayBean = new NewRecordDisplayBean();
        newRecordDisplayBean.setOnlineConsultationRecord(newOnlineConsultationRecordDetailDisplayBean);  //   在线咨询实录的参数
        newRecordDisplayBean.setOwnerId(getDoctorPatientRelationshipBean().getPatientId());  //用户ID、病人ID
        newRecordDisplayBean.setRecordType(getTvSubject());  //病例类型


        NewPatientRecordDisplayBean newPatientRecordDisplayBean = new NewPatientRecordDisplayBean();
        newPatientRecordDisplayBean.setSubject(getTvSubject());
        newPatientRecordDisplayBean.setStatus("1");
        newPatientRecordDisplayBean.setRecordDate(DateUtils.getDateConversion(geEtClinicalTime()));  //记录日期
        newPatientRecordDisplayBean.setComment(getNote());  //备注，留言
        newPatientRecordDisplayBean.setPatientId(getDoctorPatientRelationshipBean().getPatientId());
        newPatientRecordDisplayBean.setHospital(getEtInstitutions());   //医院
        newPatientRecordDisplayBean.setDoctor(getEtDoctor());  //医生

        newPatientRecordDisplayBean.setDepartmentId(departmentId==0?myself.getDoctorInformations().getDepartmentId():departmentId);  //科室的ID


        newPatientRecordDisplayBean.setRecord(newRecordDisplayBean);

        return newPatientRecordDisplayBean;
    }


    @Override
    public void updateSucceed(PatientRecordDisplayBean patientRecordDisplayBean) {
        //TODO  提示前页面刷新
        EventBus.getDefault().postSticky(new MedicalRecordIdEvent(
                getDoctorPatientRelationshipBean().getPatientId() , getDoctorPatientRelationshipBean(),true));
        switchPageUI(Global.Jump.MedicalRecordActivity);
    }



    //IMvpView
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
    public int getDoctorPatientId() {
        return DoctorPatientId;
    }

    @Override
    public void finishPage() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putBoolean("ADDEND",true);
        intent.putExtras(bundle);
        setResult(RESULT_OK,intent);
        finishCur();
    }

    public void setTvName(String tvName) {
        mTvName.setText(tvName);
    }

    private void setTvGendar(String tvGendar) {
        mTvGendar.setText(tvGendar);
    }

    private void setTvAge(String tvAge) {
        mTvAge.setText(tvAge);
    }

    public void setTvCategory(String tvCategory) {
       mTvCategory.setText(tvCategory);
    }

    public void setTvSubject(String tvSubject) {
        mTvSubject.setText(tvSubject);
    }

    public void setEtDoctor(String etDoctor) {
        mEtDoctor.setText(etDoctor);
    }

    public void setEtDepartment(String etDepartment) {
        mEtDepartment.setText(etDepartment);
    }

    public void setEtInstitutions(String etInstitutions) {
        mEtInstitutions.setText(etInstitutions);
    }

    public void setEtClinicalTime(String etClinicalTime) {
        mEtClinicalTime.setText(etClinicalTime);
    }

    public void setEtMainTell(String etMainTell) {
        mEtMainTell.setText(etMainTell);
    }

    //网络咨询
    public void setCbReasonFir(String cbReasonFir) {
        raCheckbox.select(getCheckBox(cbReasonFir));
    }

    //目前主要问题
    public void setEtMainProblem(String etMainProblem) {
        mEtMainProblem.setText(etMainProblem);
    }

    //目前接受治疗
    public void setEtCurrentTreatment(String etCurrentTreatment) {
        mEtTreatment.setText(etCurrentTreatment);
    }

    //印象，咨询建议
    public void setEtImpressionAdvice(String etImpressionAdvice) {
        mEtImpressionAdvice.setText(etImpressionAdvice);
    }

    //备注
    private void setEtNote(String comment) {
        mEtNote.setText(comment);
    }

    //得到  主诉，现病史
    public String getEtMainTell() {
        return mEtMainTell.getText().toString().trim();
    }

    //得到 目前主要问题
    public String getEtMainProblem() {
        return mEtMainProblem.getText().toString();
    }

    //得到 目前主要接受治疗
    public String getCurrentTreatment() {
        return mEtTreatment.getText().toString().trim();
    }

    //得到 印象，咨询建议
    public String getEtImpressionAdvice() {
        return mEtImpressionAdvice.getText().toString().trim();
    }

    //得到 备注
    public String getNote() {
        return mEtNote.getText().toString().trim();
    }

    //得到 主题
    public String getTvSubject() {
        return mTvSubject.getText().toString().trim();
    }
    //得到 就诊时间
    private String geEtClinicalTime() {
        return  mEtClinicalTime.getText().toString().trim();
    }

    //得到  机构
    public String getEtInstitutions() {
        return mEtInstitutions.getText().toString().trim();
    }

    public String getEtDoctor() {
        return mEtDoctor.getText().toString().trim();
    }

    //设置医院名称
    private void setHospitalName(String hospitalName) {
        mEtInstitutions.setText(hospitalName);
    }

    @Override
    public void onValidationSucceeded() {
       // if (verifyData()) {//必填项
            if (mTvMore.getText().equals(getString(R.string.title_save))) {  //"保存"-->编辑状态
                mPresenter.onSubscribe();   //#请求数据
            } else {        //"添加"--> 创建新的医疗记录状态
                mPresenter.addNewPatientRecord();  //插入新的临床诊疗记录
            }
      //  }
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        ValidatorUtils.showValidationMessage(this, errors);
    }

    /**
     * 验证数据
     */
    private boolean verifyData() {
        StringBuilder  mes = new StringBuilder();
        boolean result = true;
        //网络咨询原因
        if (getOnlineConsultationPurpose(raCheckbox.getSelectedIndx()) == null) {
            //showToast("网络咨询原因未选择");
            mes.append("网络咨询原因未选择\n");
            result = false;
        }
        if (paths.isEmpty()){
            mes.append("没有图片或图片无法上传，请重新上传图片\n");
            result = false;
        }
        if (result ==false) {
            displayErrorDialog(mes.toString());
        }
        return result;
    }
}
