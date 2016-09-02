package com.kaurihealth.kaurihealth.common.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.example.commonlibrary.widget.gallery.util.GalleryUtil;
import com.example.commonlibrary.widget.gallery.util.GetUrlsInterface;
import com.example.commonlibrary.widget.util.LoadingUtil;
import com.example.commonlibrary.widget.util.LogFactory;
import com.example.commonlibrary.widget.util.LogUtilInterface;
import com.example.commonlibrary.widget.util.SuccessInterface;
import com.kaurihealth.datalib.request_bean.bean.AddOutpatientRecordBean;
import com.kaurihealth.datalib.request_bean.bean.DepartmentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.request_bean.bean.NewDocumentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewOutpatientRecordDetailDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewPatientRecordDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewRecordDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.PatientRecordDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.Status;
import com.kaurihealth.datalib.request_bean.builder.NewDocumentDisplayBeanBuilder;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;
import com.kaurihealth.datalib.service.IDoctorPatientRelationshipService;
import com.kaurihealth.datalib.service.IRecordService;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.common.bean.MesBean;
import com.kaurihealth.kaurihealth.common.util.AddRecordConfig;
import com.kaurihealth.kaurihealth.common.util.MedicalRecordUtil;
import com.kaurihealth.kaurihealth.home.adapter.StringPathViewAdapter;
import com.kaurihealth.kaurihealth.home.util.BundleFactory;
import com.kaurihealth.kaurihealth.home.util.Config.ConfigBundleHealthyRecord;
import com.kaurihealth.kaurihealth.home.util.Interface.IBundleFactory;
import com.kaurihealth.kaurihealth.home.util.Interface.IGetBundleHealthyRecord;
import com.kaurihealth.kaurihealth.home.util.Interface.IMedicalRecordUtil;
import com.kaurihealth.kaurihealth.mine.activity.HospitalNameActivity;
import com.kaurihealth.kaurihealth.patientwithdoctor.activity.DepartmentLevel1Activity;
import com.kaurihealth.kaurihealth.patientwithdoctor.activity.DepartmentLevel2Activity;
import com.kaurihealth.kaurihealth.services.IServiceFactory;
import com.kaurihealth.kaurihealth.services.ServiceFactory;
import com.kaurihealth.kaurihealth.util.Enums.LoadingStatu;
import com.kaurihealth.kaurihealth.util.Getter;
import com.kaurihealth.kaurihealth.util.Interface.IGetter;
import com.kaurihealth.kaurihealth.util.NoValueException;
import com.kaurihealth.kaurihealth.util.PickImage;
import com.kaurihealth.kaurihealth.util.UploadFileUtil;
import com.kaurihealth.kaurihealth.util.Url;
import com.kaurihealth.utilslib.date.DateUtils;
import com.kaurihealth.utilslib.dialog.SelectDateDialog;
import com.kaurihealth.utilslib.dialog.SuccessInterfaceM;
import com.rey.material.widget.CheckBox;
import com.youyou.zllibrary.util.CommonActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * 版权:    张磊
 * 作者:    张磊
 * 描述：
 * 修订日期:
 */

/**
 * 添加门诊记录
 */
public class AddOutpatientRecordActivity extends CommonActivity {
    @Bind(R.id.tv_operate)
    TextView tv_operate;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.edt_office)
    TextView edtOffice;
    @Bind(R.id.edt_organization)
    TextView edtOrganization;
    @Bind(R.id.edt_doctor)
    EditText edtDoctor;
    @Bind(R.id.edt_history)
    EditText edtHistory;
    @Bind(R.id.edt_hr)
    EditText edtHr;
    @Bind(R.id.edt_rr)
    EditText edtRr;
    @Bind(R.id.edt_upperbp)
    EditText edtupperBp;
    @Bind(R.id.edt_lowerbp)
    EditText edtlowerbp;
    @Bind(R.id.edt_t)
    EditText edtT;
    /**
     * 体格检查
     */
    @Bind(R.id.edt_checkinfo)
    EditText edtCheckinfo;
    /**
     * 印象/计划
     */
    @Bind(R.id.edt_plan)
    EditText edtPlan;
    @Bind(R.id.edt_remark)
    EditText edtRemark;
    @Bind(R.id.checkbox_hasread_history)
    CheckBox checkboxHasreadHistory;
    @Bind(R.id.checkbox_hasupdate_history)
    CheckBox checkboxHasupdateHistory;
    @Bind(R.id.checkbox_hasread_current)
    CheckBox checkboxHasreadCurrent;
    @Bind(R.id.checkbox_hasupdate_current)
    CheckBox checkboxHasupdateCurrent;
    @Bind(R.id.tv_delete)
    TextView tv_delete;
    @Bind(R.id.gvContent)
    GridView gridView;
    @Bind(R.id.includelay)
    View includelay;
    @Bind(R.id.checkMedicalRecordHasreadSupplyTest_)
    CheckBox checkMedicalRecordHasreadSupplyTest;
    @Bind(R.id.checkLongTestHasreadSupplyTest)
    CheckBox checkLongTestHasreadSupplyTest;
    @Bind(R.id.edtSupplyTest)
    EditText edtSupplyTest;
    private IMedicalRecordUtil iMedicalRecordUtil = new MedicalRecordUtil();
    private ArrayList<String> list = new ArrayList<>();
    private StringPathViewAdapter adapter;
    private NewDocumentDisplayBeanBuilder newDocumentDisplayBeanBuilder;
    private Dialog dateDialog;
    private IGetter getter;
    private UploadFileUtil uploadFile;
    private PickImage pickImages;
    private Bundle bundle;
    private IGetBundleHealthyRecord iGetBundleHealthyRecord;
    private LoadingUtil loadingUtil;
    private IRecordService addNewPatientRecord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        logUtil.i("onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.outpatientrecord_medicalhistory);
        ButterKnife.bind(this);
        init();
    }

    LogUtilInterface logUtil = LogFactory.getSimpleLog(getClass().getName());

    @Override
    public void init() {
        super.init();
        bundle = getBundle();
        getter = Getter.getInstance(getApplicationContext());
        try {
            select = getter.getDepartment();
        } catch (NoValueException e) {
            e.printStackTrace();
        }
        uploadFile = new UploadFileUtil(getBundle().getString("kauriHealthId"), getter.getToken(), this, Url.AddImage);
        pickImages = new PickImage(list, this, new SuccessInterface() {
            @Override
            public void success() {
                adapter.notifyDataSetChanged();
                resetTime();
                uploadImags();
            }
        });
        setBack(R.id.iv_back);
        tv_delete.setVisibility(View.GONE);
        SelectDateDialog selectDateDialog = new SelectDateDialog(this, new SelectDateDialog.DialogListener() {
            @Override
            public void onclick(String year, String month, String day) {
                setDateOfBirth(year, month, day);
            }
        });
        dateDialog = selectDateDialog.getDataDialog();
        netInit();
        tvTime.setText(DateUtils.GetNowDate("yyyy-MM-dd"));
        dayOfBirth = DateUtils.GetNowDate("yyyy-MM-dd'T'HH:mm:ss");
        tv_operate.setText("完成");
        edtDoctor.setText(getter.getFullName());
        //存放图片
        newDocumentDisplayBeanBuilder = new NewDocumentDisplayBeanBuilder();
        adapter = new StringPathViewAdapter(getApplicationContext(), list);
        gridView.setAdapter(adapter);
        IBundleFactory bundleFactory = new BundleFactory();
        iGetBundleHealthyRecord = bundleFactory.getIGetBundleHealthyRecord(bundle);
        iMedicalRecordUtil.setTop(includelay, iGetBundleHealthyRecord.getName(), iGetBundleHealthyRecord.getGender(), iGetBundleHealthyRecord.getAge());
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GalleryUtil.toGallery(AddOutpatientRecordActivity.this, new GetUrlsInterface() {
                    @Override
                    public ArrayList<String> getUrls() {
                        return list;
                    }
                }, position);
            }
        });

        try {
            edtOffice.setText(getter.getDepartment().departmentName);
        } catch (NoValueException e) {
        }
        initServiceTool();
    }

    @OnClick({R.id.tv_time, R.id.edt_office, R.id.edt_organization, R.id.tv_operate, R.id.iv_uploadfile, R.id.lyouimage})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_time:
                dateDialog.show();
                break;
            case R.id.edt_office:
                skipToForResult(DepartmentLevel1Activity.class, null, DepartmentLevel1Activity.ToDepartmentLevel);
                break;
            case R.id.edt_organization:
                toHospital();
                break;
            case R.id.tv_operate:
                complete();
                break;
            case R.id.iv_uploadfile:
            case R.id.lyouimage:
                storeTime(dayOfBirth);
                pickImages.pickImage();
                break;
        }
    }


    String dayOfBirth;

    private void setDateOfBirth(String year, String month, String day) {
        StringBuilder stringBuilder = new StringBuilder(year);
        stringBuilder.append("-");
        stringBuilder.append(month);
        stringBuilder.append("-");
        stringBuilder.append(day);
        tvTime.setText(stringBuilder.toString());
        stringBuilder.append("T00:00:00");
        dayOfBirth = stringBuilder.toString();
    }

    /**
     * 初始化科室
     */
    private void netInit() {
        setHospitalName(getter.getPersonInfo().organizationName);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private DepartmentDisplayBean select = null;


    private int index = -1;


    public static final int EdithHospitalName = 10;

    /**
     * 跳到机构
     */
    public void toHospital() {
        Bundle data = new Bundle();
        data.putString("hospitalName", hospitalName);
        data.putInt("requestcode", EdithHospitalName);
        skipToForResult(HospitalNameActivity.class, data, EdithHospitalName);
    }

    private String hospitalName = "";

    private void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
        edtOrganization.setText(hospitalName);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        logUtil.i("onActivityResult");
        pickImages.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case EdithHospitalName:
                if (resultCode == RESULT_OK) {
                    String hospitalName = data.getStringExtra("hospitalName");
                    setHospitalName(hospitalName);
                }
                break;
            case DepartmentLevel1Activity.ToDepartmentLevel:
                if (resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    DepartmentDisplayBean bean = (DepartmentDisplayBean) extras.getSerializable(DepartmentLevel2Activity.DepartmentLevel2ActivityKey);
                    select = bean;
                    edtOffice.setText(bean.departmentName);
                }
                break;
        }
    }

    /**
     * 验证数据
     */
    private boolean verifyData() {
        boolean result = true;

        //科室
        if (select == null) {
            showToast("科室尚未选择");
            result = false;
        }
        //机构
        String hospitalName = getTvValue(edtOrganization);
        if (isEmpty(hospitalName)) {
            showToast("机构尚未选择");
            result = false;
        }
        //医师名称
        String doctorName = getTvValue(edtDoctor);
        if (isEmpty(doctorName)) {
            showToast("医师名称尚未填写");
            result = false;
        }
        //主诉/现病史edt_history
        if (isEmpty(getTvValue(edtHistory))) {
            showToast("主诉/现病史尚未填写");
            result = false;
        }
        //印象/计划
        String plan = getTvValue(edtPlan);
        if (isEmpty(plan)) {
            showToast("印象/计划尚未填写");
            result = false;
        }
        //辅助检查文字内容
        String strSupplyTest = getTvValue(edtSupplyTest);
        if (isEmpty(strSupplyTest)) {
            showToast("辅助检查文字内容尚未填写");
            result = false;
        }
        return result;
    }

    /**
     * 组合数据
     */
    private AddOutpatientRecordBean makeUpData() {
        if (verifyData()) {
            int patientId = iGetBundleHealthyRecord.getPatientId();
            if (patientId != 0) {
                AddOutpatientRecordBean bean = new AddOutpatientRecordBean(
                        patientId,
                        getTvValue(edtRemark),
                        getTvValue(tvTime) + "T00:00:00",
                        getTvValue(edtDoctor),
                        getTvValue(edtOrganization),
                        select.departmentId,
                        "门诊记录电子病历",
                        select.departmentName,
                        getTvValue(edtHistory),
                        checkboxHasreadHistory.isChecked(),
                        checkboxHasupdateHistory.isChecked(),
                        checkboxHasreadCurrent.isChecked(),
                        checkboxHasupdateCurrent.isChecked(),
                        TextUtils.isEmpty(getTvValue(edtHr)) ? 0 : Double.parseDouble(getTvValue(edtHr)),
                        TextUtils.isEmpty(getTvValue(edtupperBp)) ? 0 : Double.parseDouble(getTvValue(edtupperBp)),
                        TextUtils.isEmpty(getTvValue(edtlowerbp)) ? 0 : Double.parseDouble(getTvValue(edtlowerbp)),
                        TextUtils.isEmpty(getTvValue(edtRr)) ? 0 : Double.parseDouble(getTvValue(edtRr)),
                        TextUtils.isEmpty(getTvValue(edtT)) ? 0 : Double.parseDouble(getTvValue(edtT)),
                        getTvValue(edtCheckinfo),
                        getTvValue(edtPlan),
                        null,
                        Status.Release,
                        getTvValue(edtSupplyTest),
                        checkMedicalRecordHasreadSupplyTest.isChecked(),
                        checkLongTestHasreadSupplyTest.isChecked());
                return bean;
            } else {
                return null;
            }

        } else {
            return null;
        }
    }

    /**
     * 点击完成
     */
    public void complete() {
        loadingUtil = LoadingUtil.getInstance(this);
        final SuccessInterfaceM listener = new SuccessInterfaceM<MesBean>() {
            @Override
            public void success(MesBean mesBean) {
                if (mesBean.isSuccess) {
                    Intent intent = getIntent();

                    //对远程患者进行特殊处理
                    DoctorPatientRelationshipBean doctorPatientRelationshipBean = (DoctorPatientRelationshipBean) intent.getExtras().getSerializable(ConfigBundleHealthyRecord.CurrentDoctorPatientRelationship);
                    if (doctorPatientRelationshipBean.relationshipReason.equals("远程医疗咨询")){
                        if (doctorPatientRelationshipBean.endDate == null) {
                            IServiceFactory serviceFactory;
                            serviceFactory = new ServiceFactory(Url.prefix, getApplicationContext());
                            IDoctorPatientRelationshipService doctorPatientRelationshipService = serviceFactory.getDoctorPatientRelationshipService();
                            Call<ResponseDisplayBean> beanCall = doctorPatientRelationshipService.RequestEndDoctorPatientRelationship(doctorPatientRelationshipBean.doctorPatientId);
                            beanCall.enqueue(new Callback<ResponseDisplayBean>() {
                                @Override
                                public void onResponse(Call<ResponseDisplayBean> call, Response<ResponseDisplayBean> response) {
                                    if (response.isSuccessful()) {
                                        Log.i("success", "请求成功");
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseDisplayBean> call, Throwable t) {

                                }
                            });
                        }
                    }
                    intent.putExtra(ConfigBundleHealthyRecord.UpdataPatientRelActivity, true);
                    setResult(RESULT_OK, intent);
                    loadingUtil.dismiss(mesBean.message, new LoadingUtil.Success() {
                        @Override
                        public void dismiss() {
                            finishCur();
                        }
                    });
                } else {
                    loadingUtil.dismiss(mesBean.message);
                }
            }
        };
        AddOutpatientRecordBean bean = makeUpData();
        if (bean != null) {
            loadingUtil.show();
            if (list.size() == 0) {
                insertClinicalDiagnosisAndTreatment(bean, listener);
            } else {
                AddOutpatientRecordBean addOutpatientRecordBean = makeUpData();
                addOutpatientRecordBean.setRecorddocuments(listNewDocumentDisplayBean);
                insertClinicalDiagnosisAndTreatment(addOutpatientRecordBean, listener);
            }
        }
    }

    /**
     * 添加远程咨询病例
     *
     * @param bean
     */
    private void insertClinicalDiagnosisAndTreatment(AddOutpatientRecordBean bean, final SuccessInterfaceM<MesBean> listener) {
        Call<PatientRecordDisplayBean> patientRecordDisplayBeanCall = addNewPatientRecord.AddNewPatientRecord(convert(bean));
        patientRecordDisplayBeanCall.enqueue(new Callback<PatientRecordDisplayBean>() {
            @Override
            public void onResponse(Call<PatientRecordDisplayBean> call, Response<PatientRecordDisplayBean> response) {
                MesBean mesBean = new MesBean();
                mesBean.isSuccess = response.isSuccessful();
                if (response.isSuccessful()) {
                    mesBean.message = "保存成功";
                } else {
                    mesBean.message = "保存失败";
                }
                listener.success(mesBean);
            }

            @Override
            public void onFailure(Call<PatientRecordDisplayBean> call, Throwable t) {
                MesBean mesBean = new MesBean();
                mesBean.isSuccess = false;
                mesBean.message = LoadingStatu.NetError.value;
                listener.success(mesBean);
            }
        });
    }

    private NewPatientRecordDisplayBean convert(AddOutpatientRecordBean bean) {
        NewPatientRecordDisplayBean beanNew = new NewPatientRecordDisplayBean();
        beanNew.recordDocuments = bean.recorddocuments;
        beanNew.subject = bean.subject;
        beanNew.comment = bean.comment;
        beanNew.departmentId = bean.departmentId;
        beanNew.doctor = bean.doctor;
        beanNew.hospital = bean.hospital;
        beanNew.patientId = bean.patientId;
        beanNew.recordDate = bean.recordDate;
        beanNew.status = bean.status;
        beanNew.record = new NewRecordDisplayBean();
        beanNew.record.recordType = bean.record.recordType;
        beanNew.record.ownerId = bean.record.ownerId;
        beanNew.record.outpatientRecord = new NewOutpatientRecordDetailDisplayBean();
        beanNew.record.outpatientRecord.hrValue = bean.record.outpatientRecord.hrValue;
        beanNew.record.outpatientRecord.isCurrentMedicationReviewed = bean.record.outpatientRecord.isCurrentMedicationReviewed;
        beanNew.record.outpatientRecord.isCurrentMedicationUpToDate = bean.record.outpatientRecord.isCurrentMedicationUpToDate;
        beanNew.record.outpatientRecord.isLongTermMonitoringDataReviewed = bean.record.outpatientRecord.isLongTermMonitoringDataReviewed;
        beanNew.record.outpatientRecord.isPastIllnessUpToDate = bean.record.outpatientRecord.isPastIllnessUpToDate;
        beanNew.record.outpatientRecord.isPastIllnessReviewed = bean.record.outpatientRecord.isPastIllnessReviewed;
        beanNew.record.outpatientRecord.workupReviewComment = bean.record.outpatientRecord.workupReviewComment;
        beanNew.record.outpatientRecord.lowerBpValue = bean.record.outpatientRecord.lowerBpValue;
        beanNew.record.outpatientRecord.physicalExamination = bean.record.outpatientRecord.physicalExamination;
        beanNew.record.outpatientRecord.presentIllness = bean.record.outpatientRecord.presentIllness;
        beanNew.record.outpatientRecord.upperBpValue = bean.record.outpatientRecord.upperBpValue;
        beanNew.record.outpatientRecord.rrValue = bean.record.outpatientRecord.rrValue;
        beanNew.record.outpatientRecord.tValue = bean.record.outpatientRecord.tValue;
        beanNew.record.outpatientRecord.isWorkupReviewed = bean.record.outpatientRecord.isWorkupReviewed;
        beanNew.record.outpatientRecord.treatmentAndPlan = bean.record.outpatientRecord.treatmentAndPlan;
        return beanNew;
    }

    private void initServiceTool() {
        IServiceFactory serviceFactory = new ServiceFactory(Url.prefix,getApplicationContext());
        addNewPatientRecord = serviceFactory.getAddNewPatientRecord();
    }

    @Override
    protected void onResume() {
        super.onResume();
        uploadFile.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        uploadFile.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        pickImages.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    /**
     * 咨询时间适配索尼
     */
    /**
     * 保存时间
     */
    private String className = getClass().getName();
    private String TimeKey = className + "Time";

    private void storeTime(String time) {
        put(TimeKey, time);
    }

    /**
     * 设置保存的时间
     */
    private void resetTime() {

        try {
            String time = get(TimeKey, String.class);
            dayOfBirth = time;
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
            tvTime.setText(format2.format(format1.parse(dayOfBirth)));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private List<NewDocumentDisplayBean> listNewDocumentDisplayBean;

    private void uploadImags() {
        if (list.size() != 0) {
            final LoadingUtil loadingUtil = LoadingUtil.getInstance(this);
            loadingUtil.show(AddRecordConfig.AddImages);
            uploadFile.startUpload(list, new SuccessInterfaceM<String>() {
                @Override
                public void success(String response) {
                    if (!(TextUtils.isEmpty(response))) {
                        listNewDocumentDisplayBean = newDocumentDisplayBeanBuilder.Build(response);
                    }
                    loadingUtil.dismiss();

                }
            });
        }
    }
}
