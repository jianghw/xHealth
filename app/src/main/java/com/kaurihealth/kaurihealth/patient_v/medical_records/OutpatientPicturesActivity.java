package com.kaurihealth.kaurihealth.patient_v.medical_records;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.request_bean.bean.NewPatientRecordDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewRecordDisplayBean;
import com.kaurihealth.datalib.response_bean.DepartmentDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.PatientDisplayBean;
import com.kaurihealth.datalib.response_bean.PatientRecordDisplayBean;
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
 * 医患-->医疗记录-->临床诊疗-->门诊记录图片存档
 * Created by Garnet_Wu on 2016/9/19.
 */
public class OutpatientPicturesActivity extends BaseActivity implements IOutpatientElectronicView,Validator.ValidationListener {

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
     * @return
     */
    private  ArrayList<String> paths = new ArrayList<>();
    private PickImage pickImages; //图片选择操作对象
    private StringPathViewAdapter adapter;
    private PatientRecordDisplayBean mPatientRecordDisplayBean;
    private DoctorPatientRelationshipBean relationshipBean;//医患关系
    private String kauriHealthId;//上传图片对应id
    private final static  int EditHospitalName = 10;
    private DepartmentDisplayBean bean;
    private int departmentId = 0 ;
    private Validator validator;
    private int DoctorPatientId;
    private DoctorDisplayBean myself;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_admission_record;
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
        //接受消息界面 ： 注册消息
        EventBus.getDefault().register(this);
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //有多个事件  移除
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

    /**
     * className ,bundle , requestCode
     */
    //选择科室，界面跳转
    private void selectDepartment() {
        Bundle bundle = new Bundle();
        bundle.putString(Global.Environment.BUNDLE, Global.Environment.CHOICE);
        skipToForResult(SelectDepartmentLevel1Activity.class, bundle, SelectDepartmentLevel1Activity.ToDepartmentLevel);
    }

    //选择医院，界面跳转
    public void selectHospital(){
        Bundle data = new Bundle();
        data.putString("hospitalName", getTvValue(mEtInstitutions));
        data.putString(Global.Environment.BUNDLE, Global.Environment.CHOICE);
        skipToForResult(EnterHospitalActivity.class, data, EditHospitalName);
    }



    //添加
    @OnClick(R.id.tv_more)
    public void onMoreClick() {
        if (mTvMore.getText().equals(getString(R.string.swipe_tv_compile))) {  //"编辑"--->已存在病例
            mTvMore.setText(getString(R.string.title_save));//"保存"
            //设置Activity里所有控件可操作
            setAllViewsEnable(true, this);
        } else if (mTvMore.getText().equals(getString(R.string.title_save))) {//"保存"
            if (mEtDoctor.getText().toString().trim().length()==0){
                displayErrorDialog("医生不能为空");
            }else{
                validator.validate();
            }
        } else {//添加
            if (mEtDoctor.getText().toString().trim().length()==0){
                displayErrorDialog("医生不能为空");
            }else{
                validator.validate();
            }
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
     * 添加操作  跳转到此界面的
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(OutpatientElectronicAddBeanEvent event) {
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
     * ----------------------------订阅事件----------------------------------
     * 点击事件 跳转 编辑项目
     **/
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(OutpatientElectronicBeanEvent event) {
        mTvMore.setText(getString(R.string.title_save));
        //view不可操作
//        setAllViewsEnable(false, this);
        mTvMore.setEnabled(true);

        mPatientRecordDisplayBean = event.getPatientRecordDisplayBean();
        List<RecordDocumentsBean> list = mPatientRecordDisplayBean.getRecordDocuments();
        if (!paths.isEmpty()){ paths.clear();}
        if (list != null && list.size() > 0) {
            for (RecordDocumentsBean documentsBean : list) {
                if (!documentsBean.isIsDeleted()) {
                    paths.add(documentsBean.getDocumentUrl());
                }
            }
        }

        relationshipBean = event.getDoctorPatientRelationshipBean();
        PatientDisplayBean patient = relationshipBean.getPatient();
        kauriHealthId = patient.getKauriHealthId();
        pickImages = new PickImage(paths, this, () -> adapter.notifyDataSetChanged());

        displayDataByBean(mPatientRecordDisplayBean, relationshipBean);
    }






    /**
     * 数据显示处理
     */
    private void displayDataByBean(PatientRecordDisplayBean bean, DoctorPatientRelationshipBean relationshipBean) {
        personalOfInformation(relationshipBean);
        projectByInformation(bean);
        setEtNote(bean.getComment());//备注
    }


    private void personalOfInformation(DoctorPatientRelationshipBean relationshipBean) {
        Date endDate = relationshipBean.getEndDate();
        boolean isActive = DateUtils.isActive(relationshipBean.isIsActive(), endDate);
        mTvMore.setVisibility(isActive ? View.VISIBLE : View.GONE);
        //判断是否是自己写的记录
        if (relationshipBean.getDoctorId() != myself.getDoctorId()) {
            mTvMore.setVisibility(View.GONE);
        }
        PatientDisplayBean patient = relationshipBean.getPatient();
        setTvName(patient.getFullName()); //全名
        setTvGendar(patient.getGender()); //性别
        int age = DateUtils.getAge(patient.getDateOfBirth());  //年龄
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
        setEtDoctor(bean.getDoctor());  //医生
        setEtDepartment(bean.getDepartment() != null ? bean.getDepartment().getDepartmentName() : getString(R.string.being_no));  //科室
        setEtInstitutions(bean.getHospital());//机构
        setEtClinicalTime(DateUtils.getFormatDate(bean.getRecordDate()));//就诊时间
    }

    /**
     * "添加" 请求bean 构造
     * @return
     */
    //IOutpatientElectronicView
    @Override
    public NewPatientRecordDisplayBean getNewPatientRecordDisplayBean() {
        NewPatientRecordDisplayBean newPatientRecordDisplayBean = new NewPatientRecordDisplayBean();
        newPatientRecordDisplayBean.setSubject(getTvSubject());   //主题
        newPatientRecordDisplayBean.setStatus("1");  //0草稿 1发布
        newPatientRecordDisplayBean.setRecordDate(DateUtils.getDateConversion(getEtClinicalTime()));  //记录日期
        newPatientRecordDisplayBean.setComment(getEtNote());  //留言
        newPatientRecordDisplayBean.setPatientId(getDoctorPatientRelationshipBean().getPatientId());  //用户
        newPatientRecordDisplayBean.setHospital(getEtInstitutions()); //医院
        newPatientRecordDisplayBean.setDoctor(getEtDoctor());   //医生

        NewRecordDisplayBean newRecordDisplayBean = new NewRecordDisplayBean();//病例的参数
        newRecordDisplayBean.setRecordType(getTvSubject());  //病例类型
        newRecordDisplayBean.setOwnerId(getDoctorPatientRelationshipBean().getPatientId()); //用户ID，病人ID


        newPatientRecordDisplayBean.setDepartmentId(departmentId==0?myself.getDoctorInformations().getDepartmentId():departmentId);  //科室ID

        newPatientRecordDisplayBean.setRecord(newRecordDisplayBean);
        return newPatientRecordDisplayBean;
    }



    /**
     * "保存"操作  向服务器提交数据  构造bean
     * @return
     */
    //IOutpatientElectronicView
    @Override
    public PatientRecordDisplayBean getRequestPatientRecordBean() {
        PatientRecordDisplayBean patientRecordBean = getPatientRecordBean();
        patientRecordBean.setRecordDate(DateUtils.getDateConversion(getEtClinicalTime()));  //诊疗时间
        patientRecordBean.setComment(getEtNote());  //备注
        patientRecordBean.setDoctor(getEtDoctor());  //医生
        patientRecordBean.setHospital(getEtInstitutions()); //机构
        patientRecordBean.setDepartmentId(bean == null ? patientRecordBean.getDepartmentId() : departmentId);  //测试id
        return patientRecordBean;
    }

    //IOutpatientElectronicView
    @Override
    public PatientRecordDisplayBean getPatientRecordBean() {
        CheckUtils.checkNullArgument(mPatientRecordDisplayBean, "PatientRecordBean is null");
        return mPatientRecordDisplayBean;
    }


    //IOutpatientElectronicView
    @Override
    public ArrayList<String> getPathsList() {
        return paths;
    }

    //IOutpatientElectronicView
    @Override
    public String getKauriHealthId() {
        return kauriHealthId;
    }


    //IOutpatientElectronicView
    @Override
    public DoctorPatientRelationshipBean getDoctorPatientRelationshipBean() {
        //对象非空判断
        CheckUtils.checkNullArgument(relationshipBean, "DoctorPatientRelationshipBean is null");
        return relationshipBean;
    }



    //IOutpatientElectronicView
    @Override
    public void updateSucceed(PatientRecordDisplayBean patientRecordDisplayBean) {
        //TODO 提示前页刷新
        EventBus.getDefault().postSticky(
                new MedicalRecordIdEvent(
                        getDoctorPatientRelationshipBean().getPatientId(), getDoctorPatientRelationshipBean(), true));
        switchPageUI(Global.Jump.MedicalRecordActivity);
    }


    //BaseActivity
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

    //姓名
    public void setTvName(String tvName) {
        mTvName.setText(tvName);
    }

    //性别
    public void setTvGendar(String tvGendar) {
        mTvGendar.setText(tvGendar);
    }

    //年龄
    public void setTvAge(String tvAge) {
        mTvAge.setText(tvAge);
    }

    //类型
    public void setTvCategory(String tvCategory) {
        mTvCategory.setText(tvCategory);
    }

    //项目
    public void setTvSubject(String tvSubject) {
        mTvSubject.setText(tvSubject);
    }

    //医生
    public void setEtDoctor(String etDoctor) {
        mEtDoctor.setText(etDoctor);
    }

    //科室
    public void setEtDepartment(String etDepartment) {
        mEtDepartment.setText(etDepartment);
    }

    //机构
    public void setEtInstitutions(String etInstitutions) {
        mEtInstitutions.setText(etInstitutions);
    }

    //就诊时间
    public void setEtClinicalTime(String etClinicalTime) {
        mEtClinicalTime.setText(etClinicalTime);
    }

    //主题
    public String getTvSubject() {
        return mTvSubject.getText().toString().trim();
    }

    //记录日期
    public String getEtClinicalTime() {
        return mEtClinicalTime.getText().toString().trim();
    }

    //留言
    public String getEtNote() {
        return mEtNote.getText().toString().trim();
    }

    //医院（机构）
    public String getEtInstitutions() {
        return mEtInstitutions.getText().toString().trim();
    }
    //医生
    public String getEtDoctor() {
        return  mEtDoctor.getText().toString().trim();
    }

    //设置医院名称
    private void setHospitalName(String hospitalName) {
        mEtInstitutions.setText(hospitalName);
    }

    //设置备注
    public void setEtNote(String etNote) {
        mEtNote.setText(etNote);
    }


    //表单验证成功，请求网络数据
    @Override
    public void onValidationSucceeded() {
        //判断图片是否为空，由图片地址集合是否为空判断
        if (paths.isEmpty()) {
            displayErrorDialog("没有图片或图片无法上传，请重新上传图片");
        } else {
            if (mTvMore.getText().equals(getString(R.string.title_save))) {  //"保存"-->编辑状态
                if (OnClickUtils.onNoDoubleClick()) return;
                mPresenter.onSubscribe();   //#请求数据
            } else {        //"添加"--> 创建新的医疗记录状态
                if (OnClickUtils.onNoDoubleClick()) return;
                mPresenter.addNewPatientRecord();  //插入新的临床诊疗记录
            }

        }
    }
    //表单验证失败
    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        ValidatorUtils.showValidationMessage(this, errors);
    }
}
