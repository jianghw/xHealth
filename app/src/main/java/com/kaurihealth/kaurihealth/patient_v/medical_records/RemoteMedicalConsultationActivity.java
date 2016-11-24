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
import com.kaurihealth.datalib.request_bean.bean.NewPatientRecordDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewRecordDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewRemoteConsultationRecordDetailDisplayBean;
import com.kaurihealth.datalib.response_bean.DepartmentDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.PatientDisplayBean;
import com.kaurihealth.datalib.response_bean.PatientRecordDisplayBean;
import com.kaurihealth.datalib.response_bean.RecordBean;
import com.kaurihealth.datalib.response_bean.RecordDocumentsBean;
import com.kaurihealth.datalib.response_bean.RemoteConsultationRecordBean;
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
 * 医患-->医疗记录-->临床诊疗--> 远程医疗咨询
 * 必填项：就诊时间。科室，机构，医生，主诉/现病史，目前主要问题，目前接受治疗，印象/咨询建议
 * Created by Garnet_Wu on 2016/9/18.
 */
public class RemoteMedicalConsultationActivity extends BaseActivity implements IOutpatientElectronicView ,Validator.ValidationListener{
    @Inject
    OutpatientElectronicPresenter<IOutpatientElectronicView> mPresenter;

    //添加
    @Bind(R.id.tv_more)
    TextView mTvMore ;
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
    TextView mEtMainTell;
    //目前主要问题
    @NotEmpty(message = "目前主要问题不能为空")
    @Bind(R.id.et_main_problem)
    EditText mEtMainProblem;
    //目前接受治疗
    @NotEmpty(message = "目前接受治疗不能为空")
    @Bind(R.id.et_current_treatment)
    EditText mEtTreatment;
    //患者医疗记录 checkbox
    @Bind(R.id.cb_record_fir)
    CheckBox mCbRecordFir;
    //印象/咨询建议
    @NotEmpty(message = "印象/咨询建议不能为空")
    @Bind(R.id.et_impression_advice)
    EditText mEtImpressionAdvice;
    //上传附件  -- 放上传图片缩略图的地方
    @Bind(R.id.gv_image)
    TagsGridview mGvImage;
    //备注：
    @Bind(R.id.et_note)
    EditText mEtNote;

    /*图片地址集合,含本地及网路*/
    private  ArrayList<String> paths = new ArrayList<>();

    private PickImage pickImages ; //图片选择操作对象
    private StringPathViewAdapter adapter;
    private String kauriHealthId;  //上传图片对应id
    private PatientRecordDisplayBean mPatientRecordDisplayBean;
    private DoctorPatientRelationshipBean relationshipBean;
    private final static  int EditHospitalName = 10;
    private DepartmentDisplayBean bean;
    private int departmentId = 0;
    private Validator validator;
    private int DoctorPatientId;
    private DoctorDisplayBean myself;

    //BaseActivity
    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_remote_medical_consultation;
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
        //发送消息界面:注册
        EventBus.getDefault().register(this);
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeStickyEvent(OutpatientElectronicAddBeanEvent.class);
        removeStickyEvent(OutpatientElectronicBeanEvent.class);
        mPresenter.unSubscribe();
        //发送消息界面：取消消息
        EventBus.getDefault().unregister(this);
    }

    //点击事件      科室                  机构
    @OnClick({R.id.et_department ,R.id.et_institutions,R.id.et_clinical_time})
    public  void  onClick(View view){
        switch (view.getId()){
            case R.id.et_department:
                selectDepartment();
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

    /**
     * className,bundle,requestCode
     */
    //选择科室，界面跳转
    private void selectDepartment() {
        Bundle bundle = new Bundle();
        bundle.putString(Global.Environment.BUNDLE, Global.Environment.CHOICE);
        skipToForResult(SelectDepartmentLevel1Activity.class, bundle, SelectDepartmentLevel1Activity.ToDepartmentLevel);
    }

    //选择医院，界面跳转
    private void selectHospital() {
        Bundle data = new Bundle();
        data.putString("hospitalName", getTvValue(mEtInstitutions));
        data.putString(Global.Environment.BUNDLE, Global.Environment.CHOICE);
        skipToForResult(EnterHospitalActivity.class, data, EditHospitalName);
    }

    //添加
    @OnClick(R.id.tv_more)
    public void onMoreClick() {
        if (mTvMore.getText().equals("编辑")) {  // 已存在病例
            mTvMore.setText("保存");
            //设置Activity里所有的控件可操作
            setAllViewsEnable(true, this);
        } else if (mTvMore.getText().equals("保存")) {  //保存提交
            validator.validate();
        } else {  //添加
            validator.validate();
        }
    }


    // #上传附件 (十字)
    @OnClick(R.id.iv_add_upload)
    public void imageClick(){  pickImages.pickImage(); }

    /**
     * #附件 -- 放缩略图的地方  GridView
     * 点击方法图片
     */
    @OnItemClick(R.id.gv_image)
    public void  picture(int position){
        GalleryUtil.toGallery(this, new GalleryUtil.GetUrlsInterface() {
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


    // #Fragment 启动Activity并取回数据  权限有关  V4包下
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
            case EditHospitalName:
                if (resultCode == RESULT_OK) {
                    String hospitalName = data.getStringExtra("hospitalName");
                    setHospitalName(hospitalName);
                }
                break;

            case SelectDepartmentLevel1Activity.ToDepartmentLevel:
                if (resultCode ==RESULT_OK) {
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
     * 点击事件，跳转 编辑项目
     **/
    @Subscribe(threadMode = ThreadMode.MAIN , sticky = true )
    public void eventBusMain(OutpatientElectronicBeanEvent event){
        mTvMore.setText("保存");
        //RemoteMedicaklActivity 里面的view不可操作
//        setAllViewsEnable(false,this);
        mTvMore.setEnabled(true);
        mPatientRecordDisplayBean =  event.getPatientRecordDisplayBean();
        List<RecordDocumentsBean> list = mPatientRecordDisplayBean.getRecordDocuments();
        if (!paths.isEmpty()) {
            paths.clear();
        }
        if ((list != null && list.size() > 0)) {
            for (RecordDocumentsBean documentBean : list) {
                if (!documentBean.isIsDeleted()) {
                    paths.add(documentBean.getDocumentUrl());

                }
            }
        }
            relationshipBean = event.getDoctorPatientRelationshipBean();
            PatientDisplayBean patient = relationshipBean.getPatient();
            kauriHealthId = patient.getKauriHealthId();
            pickImages =  new PickImage(paths, this, () -> adapter.notifyDataSetChanged());

            displayDataByBean(mPatientRecordDisplayBean,relationshipBean);
    }

    /**
     *
     * 添加操作 跳转过来的
     */
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void eventBusMain(OutpatientElectronicAddBeanEvent event){
        mEtDoctor.setEnabled(false);
        String category = event.getCategory();
        String subject = event.getSubject();
        mTvMore.setText("添加");  //添加
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

    //数据显示处理
    private void displayDataByBean(PatientRecordDisplayBean bean, DoctorPatientRelationshipBean relationshipBean) {
        personalOfInformation(relationshipBean); //个人信息
        projectByInformation(bean); //类型，项目
        fillAllCases(bean);
        setEtNote(bean.getComment());//备注
    }

    //数据显示处理(已改)
    private void fillAllCases(PatientRecordDisplayBean bean) {
        //病例的参数
        RemoteConsultationRecordBean remoteConsultationRecordBean = getRemoteConsultationRecordBean(bean);
        setEtMainTell(remoteConsultationRecordBean.getPresentIllness()); //主诉/现病史
        setEtMainProblem(remoteConsultationRecordBean.getCurrentHealthIssues()) ;   //目前只要问题
        setEtCurrentTreatment(remoteConsultationRecordBean.getCurrentTreatment());              //目前接受治疗

        setCbRecordFir(remoteConsultationRecordBean.isIsPatientHealthRecordReviewed());  //患者系统医疗记录是否已阅
        setEtImpressionAdvice(remoteConsultationRecordBean.getRemoteConsultationDoctorComment()); //印象/咨询建议
        setNote(bean.getComment()); //备注
    }

    //备注
    private void setNote(String comment) {
        mEtNote.setText(comment);
    }

    //印象，咨询建议
    private void setEtImpressionAdvice(String remoteConsultationDoctorComment) {
        mEtImpressionAdvice.setText(remoteConsultationDoctorComment);
    }

    //患者医疗系统医疗记录是否已阅
    private void setCbRecordFir(boolean cbRecordFir) {
        mCbRecordFir.setChecked(cbRecordFir);
    }


    //目前接受治疗
    private void setEtCurrentTreatment(String currentTreatment) {
        mEtTreatment.setText(currentTreatment);
    }

    //目前主要问题
    private void setEtMainProblem(String currentHealthIssues) {
        mEtMainProblem.setText(currentHealthIssues);
    }

    //主诉/现病史
    private void setEtMainTell(String etMainTell) {
        mEtMainTell.setText(etMainTell);
    }

    private void projectByInformation(PatientRecordDisplayBean bean) {
        //判断是否是自己写的记录
        if (bean.getCreatedBy() != myself.getUserId()) {
            mTvMore.setVisibility(View.GONE);
        }
        String subject = bean.getSubject();
        initNewBackBtn(subject);
        setTvCategory(bean.getCategory()); //类型
        setTvSubject(subject) ; //项目
        setEtDoctor(bean.getDoctor()); //医生
        setEtDepartment(bean.getDepartment() != null ? bean.getDepartment().getDepartmentName() : "暂无");
        setEtInstitutions(bean.getHospital()); //机构
        setEtClinicalTime(DateUtils.getFormatDate(bean.getRecordDate())); //就诊时间
    }

    private void setEtClinicalTime(String etClinicalTime) {
        mEtClinicalTime.setText(etClinicalTime);
    }

    private void setEtInstitutions(String etHospital) {
        mEtInstitutions.setText(etHospital);
    }


    private void setEtDepartment(String etDepartment) {
        mEtDepartment.setText(etDepartment);
    }

    private void setEtDoctor(String etDoctor) {
        mEtDoctor.setText(etDoctor);
    }

    private void setTvSubject(String tvSubject) {
        mTvSubject.setText(tvSubject);
    }

    private void setTvCategory(String tvCategory) {
        mTvCategory.setText(tvCategory);
    }


    //个人信息栏设置
    private void personalOfInformation(DoctorPatientRelationshipBean relationshipBean) {
        Date endDate = relationshipBean.getEndDate();
        boolean isActivie = DateUtils.isActive(relationshipBean.isIsActive(), endDate);
        //"添加"按钮
        mTvMore.setVisibility(isActivie ? View.VISIBLE :View.GONE);
        PatientDisplayBean patient = relationshipBean.getPatient();
        //姓名
        setTvName(patient.getFullName());
        //性别
        setTvGender(patient.getGender());
        //年龄
        int age = DateUtils.getAge(patient.getDateOfBirth());
        setTvAge(age+"岁");
    }

    //年龄设置
    private void setTvAge(String age) {
        mTvAge.setText(age);
    }

    //性别设置
    private void setTvGender(String gender) {
        mTvGendar.setText(gender);
    }

    //姓名：全名
    private void setTvName(String fullName) {
        mTvName.setText(fullName);
    }


    //IMvpView 跳转界面
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

    //图片地址的集合
    @Override
    public ArrayList<String> getPathsList() {
        return paths;
    }

    //上传图片对应的id
    @Override
    public String getKauriHealthId() {
        return kauriHealthId;
    }


    @Override
    public void updateSucceed(PatientRecordDisplayBean patientRecordDisplayBean) {
        //TODO 提示前页刷新
        EventBus.getDefault().postSticky(
                new MedicalRecordIdEvent(
                        getDoctorPatientRelationshipBean().getPatientId(), getDoctorPatientRelationshipBean(),true));
        switchPageUI(Global.Jump.MedicalRecordActivity);
    }

    @Override
    public PatientRecordDisplayBean getPatientRecordBean() {
        CheckUtils.checkNullArgument(mPatientRecordDisplayBean,"PatientRecordBean is null");
        return mPatientRecordDisplayBean;
    }

    @Override
    public DoctorPatientRelationshipBean getDoctorPatientRelationshipBean() {
        //对象非空判断
        CheckUtils.checkNotNull(relationshipBean,"DoctorPatientRelation is null");
        return relationshipBean;
    }



    //"保存"操作  向服务器提交数据  构造bean
    @Override
    public PatientRecordDisplayBean getRequestPatientRecordBean() {
        PatientRecordDisplayBean patientRecordBean = getPatientRecordBean();
        RemoteConsultationRecordBean remoteConsultationRecordBean = getRemoteConsultationRecordBean(patientRecordBean);
        remoteConsultationRecordBean.setPresentIllness(getEtMainTell());  //主诉，现病史
        remoteConsultationRecordBean.setCurrentHealthIssues(getEtMainProblem());  //目前主要健康问题
        remoteConsultationRecordBean.setCurrentTreatment(getCurrentTreatment()); //目前接受治疗
        remoteConsultationRecordBean.setIsPatientHealthRecordReviewed(getCbRecordFir()); // 患者医疗记录知否已阅
        remoteConsultationRecordBean.setRemoteConsultationDoctorComment(getEtImpressionAdvice());      //印象、咨询建议
        patientRecordBean.setComment(getEtNote()); //备注
        patientRecordBean.setRecordDate(DateUtils.getDateConversion(getEtClinicalTime()));//记录日期
        patientRecordBean.setHospital(getEtmEtInstitutions());//机构
        patientRecordBean.setDepartmentId(bean == null ? patientRecordBean.getDepartmentId() : departmentId);  //测试id
        patientRecordBean.setDoctor(getEtDoctor());//医师
        return patientRecordBean;
    }

    /**
     * 描述 ：“添加”新的临床诊疗记录
     * "添加"请求bean构造
     */
    @Override
    public NewPatientRecordDisplayBean getNewPatientRecordDisplayBean() {
        NewRemoteConsultationRecordDetailDisplayBean newRemoteConsultationRecordDetailDisplayBean = new NewRemoteConsultationRecordDetailDisplayBean();//新"添加"的远程医疗咨询
        newRemoteConsultationRecordDetailDisplayBean.setPresentIllness(getEtMainTell());  //主诉，现病史
        newRemoteConsultationRecordDetailDisplayBean.setCurrentHealthIssues(getEtMainProblem()); //目前主要问题
        newRemoteConsultationRecordDetailDisplayBean.setCurrentTreatment(getCurrentTreatment()); //目前接受治疗
        newRemoteConsultationRecordDetailDisplayBean.setPatientHealthRecordReviewed(getCbRecordFir()); //患者系统医疗记录已阅
        newRemoteConsultationRecordDetailDisplayBean.setRemoteConsultationDoctorComment(getEtImpressionAdvice()); //印象，咨询意见


        NewRecordDisplayBean newRecordDisplayBean = new NewRecordDisplayBean();
        newRecordDisplayBean.setRemoteConsultationRecord(newRemoteConsultationRecordDetailDisplayBean);  //远程医疗记录的参数
        newRecordDisplayBean.setOwnerId(getDoctorPatientRelationshipBean().getPatientId()); //用户ID或者病人ID
        newRecordDisplayBean.setRecordType(getTvSubject());  //病例类型

        NewPatientRecordDisplayBean newPatientRecordDisplayBean = new NewPatientRecordDisplayBean();
        newPatientRecordDisplayBean.setSubject(getTvSubject());
        newPatientRecordDisplayBean.setStatus("1");  //0草稿  1发布
        newPatientRecordDisplayBean.setRecordDate(DateUtils.getDateConversion(getEtClinicalTime()));  //记录日期
        newPatientRecordDisplayBean.setComment(getEtNote());  //留言
        newPatientRecordDisplayBean.setPatientId(getDoctorPatientRelationshipBean().getPatientId());  //用户
        newPatientRecordDisplayBean.setHospital(getEtmEtInstitutions()); //医院
        newPatientRecordDisplayBean.setDoctor(getEtDoctor());//医师
        newPatientRecordDisplayBean.setDepartmentId(departmentId==0?myself.getDoctorInformations().getDepartmentId():departmentId);  //科室ID
        newPatientRecordDisplayBean.setRecord(newRecordDisplayBean);

        return newPatientRecordDisplayBean;
    }

    //得到， 机构、医院
    private String getEtmEtInstitutions() {
        return mEtInstitutions.getText().toString().trim();
    }

    //得到 记录日期
    private String getEtClinicalTime() {
        return mEtClinicalTime.getText().toString().trim();
    }

    private String getTvSubject() {
        return  mTvSubject.getText().toString().trim();
    }


    @NonNull
    private RemoteConsultationRecordBean getRemoteConsultationRecordBean(PatientRecordDisplayBean bean) {
        RecordBean recordBean = bean.getRecord();
        CheckUtils.checkNullArgument(recordBean , "recordBean is null");
        RemoteConsultationRecordBean remoteConsultationRecord = recordBean.getRemoteConsultationRecord();
        CheckUtils.checkNullArgument(remoteConsultationRecord,"remoteConsultationRecord is null");
        return  remoteConsultationRecord;

    }


    //得到 主诉，现病史
    public String getEtMainTell() {
        return mEtMainTell.getText().toString().trim();
    }
    //得到  目前主要问题
    public String getEtMainProblem() {
        return  mEtMainProblem.getText().toString().trim();
    }
    //得到 目前接受治疗
    public String getCurrentTreatment() {
        return mEtTreatment.getText().toString().trim();
    }

    //得到 患者医疗记录
    public boolean getCbRecordFir() {
        return mCbRecordFir.isChecked();
    }
    //得到  印象、咨询建议
    public String getEtImpressionAdvice() {
        return mEtImpressionAdvice.getText().toString().trim();
    }

    public String getEtDoctor(){
        return mEtDoctor.getText().toString().trim();
    }

    //得到  备注
    private String getEtNote() {
        return mEtNote.getText().toString().trim();
    }

    //设置医院名称
    public void setHospitalName(String hospitalName) {
        mEtInstitutions.setText(hospitalName);
    }

    //设置 备注
    public void setEtNote(String etNote) {
        mEtNote.setText(etNote);
    }

    //表单验证成功，请求数据
    @Override
    public void onValidationSucceeded() {
        if (mTvMore.getText().equals(getString(R.string.title_save))) {  //"保存"-->编辑状态
            if (OnClickUtils.onNoDoubleClick()) return;
            mPresenter.onSubscribe();   //#请求数据
        } else {        //"添加"--> 创建新的医疗记录状态
            if (OnClickUtils.onNoDoubleClick()) return;
            mPresenter.addNewPatientRecord();  //插入新的临床诊疗记录
        }
    }

    //表单验证失败 ，弹出错误提示框
    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        ValidatorUtils.showValidationMessage(this, errors);
    }
}
