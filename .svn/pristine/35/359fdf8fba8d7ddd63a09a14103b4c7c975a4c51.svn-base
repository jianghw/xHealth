package com.kaurihealth.kaurihealth.patient_v.medical_records_only_read;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.request_bean.bean.PrescriptionBean;
import com.kaurihealth.datalib.response_bean.DepartmentDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.StringPathViewAdapter;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.eventbus.EditPrescriptionNewBeanEvent;
import com.kaurihealth.kaurihealth.eventbus.PresriptionOnlyReadEvent;
import com.kaurihealth.kaurihealth.patient_v.medical_records.AddAndEditPrescriptionActivityNew;
import com.kaurihealth.kaurihealth.patient_v.prescription.PrescriptionActivity;
import com.kaurihealth.utilslib.date.DateUtils;
import com.kaurihealth.utilslib.image.GalleryUtil;
import com.kaurihealth.utilslib.image.PickImage;
import com.kaurihealth.utilslib.widget.TagsGridview;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * Created by mip on 2016/11/9.
 */

public class PresriptionOnlyReadActivity extends BaseActivity {

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
    private boolean isAble;

    private DoctorPatientRelationshipBean doctorPatientRelationshipBean;
    private DepartmentDisplayBean bean;
    private DoctorDisplayBean myself;
    private static final int ADDANDEDITPRESCRIPTION = 9 ;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.add_and_edit_prescription;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        adapter = new StringPathViewAdapter(getApplicationContext(), paths);
        mGvImage.setAdapter(adapter);
    }

    @Override
    protected void initDelayedData() {
        tv_add_edit_time.setText("处方时间:");
        myself = LocalData.getLocalData().getMyself();
        EventBus.getDefault().register(this);
    }

    @OnClick(R.id.tv_more)
    public void onMoreClick() {
        if (mTvMore.getText().equals(getString(R.string.swipe_tv_compile))) {
            EventBus.getDefault().postSticky(new EditPrescriptionNewBeanEvent(mPrescriptionBean,doctorPatientRelationshipBean,true));
            skipToForResult(AddAndEditPrescriptionActivityNew.class,null,ADDANDEDITPRESCRIPTION);
        }
    }

    /**
     * 只读  跳转到子Item
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(PresriptionOnlyReadEvent event) {
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

    private void personalOfInformation(DoctorPatientRelationshipBean bean) {
        mTvName.setText(bean.getPatient().getFullName());//患者姓名
        mTvGendar.setText(bean.getPatient().getGender());//患者性别
        mTvAge.setText(DateUtils.getAge(bean.getPatient().getDateOfBirth()) + "");//年龄
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADDANDEDITPRESCRIPTION && resultCode == PrescriptionActivity.Update){
            setResult(RESULT_OK);
            finishCur();
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
