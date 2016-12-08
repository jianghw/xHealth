package com.kaurihealth.kaurihealth.patient_v.medical_records_only_read;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.response_bean.DepartmentDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.PatientDisplayBean;
import com.kaurihealth.datalib.response_bean.PatientRecordDisplayBean;
import com.kaurihealth.datalib.response_bean.RecordDocumentsBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.StringPathViewAdapter;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.eventbus.OutpatientElectronicBeanEvent;
import com.kaurihealth.kaurihealth.eventbus.OutpatientElectronicOnlyReadEvent;
import com.kaurihealth.kaurihealth.patient_v.medical_records.DischargeRecordActivity;
import com.kaurihealth.utilslib.date.DateUtils;
import com.kaurihealth.utilslib.image.GalleryUtil;
import com.kaurihealth.utilslib.image.PickImage;
import com.kaurihealth.utilslib.widget.TagsGridview;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * Created by mip on 2016/11/8.
 */

public class DischargeRecordOnlyReadActivity extends BaseActivity {

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
     *
     * @return
     */
    private ArrayList<String> paths = new ArrayList<>();   //“附件”图片的地址集合
    private PickImage pickImages; //图片选择操作对象
    private StringPathViewAdapter adapter;
    private PatientRecordDisplayBean mPatientRecordDisplayBean;
    private DoctorPatientRelationshipBean relationshipBean;//医患关系
    private String kauriHealthId;//上传图片对应id
    private final static int EditHospitalName = 10;
    private DepartmentDisplayBean bean;
    private int departmentId = 0;
    private int DoctorPatientId;
    private DoctorDisplayBean myself;
    private static final int DISCHARGERECORD =  8;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_admission_record;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        adapter = new StringPathViewAdapter(getApplicationContext(), paths);
        mGvImage.setAdapter(adapter);
    }

    @Override
    protected void initDelayedData() {
        myself = LocalData.getLocalData().getMyself();
        //接受消息界面 ： 注册消息
        EventBus.getDefault().register(this);
    }

    @OnClick(R.id.tv_more)
    public void onMoreClick() {
        if (mTvMore.getText().equals(getString(R.string.swipe_tv_compile))) {
            EventBus.getDefault().postSticky(new OutpatientElectronicBeanEvent(mPatientRecordDisplayBean,relationshipBean));
            skipToForResult(DischargeRecordActivity.class,null,DISCHARGERECORD);
        }
    }


    /**
     * ----------------------------订阅事件----------------------------------
     * 点击事件 跳转 编辑项目
     **/
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(OutpatientElectronicOnlyReadEvent event) {
        mTvMore.setText(getString(R.string.swipe_tv_compile));
        //view不可操作
        setAllViewsEnable(false, this);
        mTvMore.setEnabled(true);

        mPatientRecordDisplayBean = event.getPatientRecordDisplayBean();
        List<RecordDocumentsBean> list = mPatientRecordDisplayBean.getRecordDocuments();
        if (!paths.isEmpty()) {
            paths.clear();
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DISCHARGERECORD && resultCode == RESULT_OK){
            finishCur();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //有多个事件  移除
        EventBus.getDefault().unregister(this);
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
        return mEtDoctor.getText().toString().trim();
    }

    //设置医院名称
    private void setHospitalName(String hospitalName) {
        mEtInstitutions.setText(hospitalName);
    }

    //设置备注
    public void setEtNote(String etNote) {
        mEtNote.setText(etNote);
    }
}
