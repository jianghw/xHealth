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

import com.bugtags.library.Bugtags;
import com.example.commonlibrary.widget.gallery.util.GalleryUtil;
import com.example.commonlibrary.widget.gallery.util.GetUrlsInterface;
import com.example.commonlibrary.widget.util.LoadingUtil;
import com.example.commonlibrary.widget.util.SuccessInterface;
import com.kaurihealth.datalib.request_bean.bean.AddOnlineConsultationRecordBean;
import com.kaurihealth.datalib.request_bean.bean.DepartmentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.request_bean.bean.NewDocumentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewOnlineConsultationRecordDetailDisplayBean;
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
import com.kaurihealth.utilslib.dialog.SelectDateDialog;
import com.kaurihealth.utilslib.dialog.SuccessInterfaceM;
import com.rey.material.widget.CheckBox;
import com.youyou.zllibrary.util.CommonActivity;
import com.youyou.zllibrary.util.RadioCheckboxForMaterial;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
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
 * 添加网络医疗咨询记录
 */
public class AddOnlineConsultationRecordActivity extends CommonActivity {

    @Bind(R.id.tv_operate)
    TextView tvOperate;
    @Bind(R.id.tvRecordDate)
    TextView tvRecordDate;
    //科室
    @Bind(R.id.edtDepartmentName)
    TextView edtDepartmentName;
    //机构
    @Bind(R.id.edtHospital)
    TextView edtHospital;
    @Bind(R.id.edtDoctor)
    EditText edtDoctor;
    @Bind(R.id.edtPresentIllness)
    EditText edtPresentIllness;
    @Bind(R.id.edtMajorIssue)
    EditText edtMajorIssue;
    @Bind(R.id.edtComment)
    EditText edtComment;
    @Bind(R.id.edtRemark)
    EditText edtRemark;
    @Bind(R.id.tv_delete)
    TextView tvDelete;
    @Bind(R.id.checkboxReasonHasUpdate)
    CheckBox checkboxReasonHasUpdate;
    @Bind(R.id.checkboxReasonConsultAgain)
    CheckBox checkboxReasonConsultAgain;
    @Bind(R.id.checkboxReasonOther)
    CheckBox checkboxReasonOther;
    //附件
    @Bind(R.id.gvContent)
    GridView gridView;
    @Bind(R.id.includelay)
    View includelay;

    private IMedicalRecordUtil iMedicalRecordUtil = new MedicalRecordUtil();
    private RadioCheckboxForMaterial raCheckbox;
    private ArrayList<String> list = new ArrayList<>();
    private StringPathViewAdapter adapter;
    private NewDocumentDisplayBeanBuilder newDocumentDisplayBeanBuilder;
    private DepartmentDisplayBean select;
    private Dialog dateDialog;
    private String subject;
    private IGetter getter;
    private UploadFileUtil uploadFile;
    private PickImage pickImages;
    private Bundle bundle;
    private IGetBundleHealthyRecord iGetBundleHealthyRecord;
    private IRecordService addNewPatientRecord;
    private NewPatientRecordDisplayBean beanNew = new NewPatientRecordDisplayBean();

    {
        beanNew.record = new NewRecordDisplayBean();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_online_consultation_record);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void init() {
        super.init();
        bundle = getBundle();
        IBundleFactory bundleFactory = new BundleFactory();
        iGetBundleHealthyRecord = bundleFactory.getIGetBundleHealthyRecord(bundle);
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
                upLoadImags();
            }
        });
        try {
            edtDepartmentName.setText(getter.getDepartment().departmentName);
        } catch (NoValueException e) {
            e.printStackTrace();
        }
        edtHospital.setText(getter.getHospitalName());
        raCheckbox = new RadioCheckboxForMaterial();
        raCheckbox.regist(0, checkboxReasonHasUpdate);
        raCheckbox.regist(1, checkboxReasonConsultAgain);
        raCheckbox.regist(2, checkboxReasonOther);
        raCheckbox.setListener();
        String[] array = getResources().getStringArray(R.array.ClinicalDiagnosisAndTreatment);
        subject = array[2];
        tvOperate.setText("保存");
        edtDoctor.setText(getter.getFullName());
        setBack(R.id.iv_back);
        tvDelete.setVisibility(View.GONE);
        tvRecordDate.setText((new SimpleDateFormat("yyyy-MM-dd")).format(Calendar.getInstance().getTime()));
        SelectDateDialog selectDateDialog = new SelectDateDialog(this, new SelectDateDialog.DialogListener() {
            @Override
            public void onclick(String year, String month, String day) {
                setDateOfBirth(year, month, day);
            }
        });
        dateDialog = selectDateDialog.getDataDialog();
        //存放图片
        newDocumentDisplayBeanBuilder = new NewDocumentDisplayBeanBuilder();
        adapter = new StringPathViewAdapter(getApplicationContext(), list);
        gridView.setAdapter(adapter);
        iMedicalRecordUtil.setTop(includelay, iGetBundleHealthyRecord.getName(), iGetBundleHealthyRecord.getGender(), iGetBundleHealthyRecord.getAge());
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GalleryUtil.toGallery(AddOnlineConsultationRecordActivity.this, new GetUrlsInterface() {
                    @Override
                    public ArrayList<String> getUrls() {
                        return list;
                    }
                }, position);
            }
        });
        initServiceTool();
    }

    private void initServiceTool() {
        IServiceFactory serviceFactory = new ServiceFactory(Url.prefix,getApplicationContext());
        addNewPatientRecord = serviceFactory.getAddNewPatientRecord();
    }


    @OnClick({R.id.tvRecordDate, R.id.edtHospital, R.id.edtDepartmentName, R.id.tv_operate, R.id.iv_uploadfile})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvRecordDate:
                dateDialog.show();
                break;
            case R.id.edtHospital:
                hospital();
                break;
            case R.id.edtDepartmentName:
                skipToForResult(DepartmentLevel1Activity.class, null, DepartmentLevel1Activity.ToDepartmentLevel);
                break;
            case R.id.tv_operate:
                complete();
                break;
            case R.id.iv_uploadfile:
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
        tvRecordDate.setText(stringBuilder.toString());
        stringBuilder.append("T00:00:00");
        dayOfBirth = stringBuilder.toString();
    }


    private final int EdithHospitalName = 10;

    /**
     * 点击机构
     */
    public void hospital() {
        Bundle data = new Bundle();
        data.putString("hospitalName", getTvValue(edtHospital));
        data.putInt("requestcode", EdithHospitalName);
        skipToForResult(HospitalNameActivity.class, data, EdithHospitalName);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
                    edtDepartmentName.setText(bean.departmentName);
                }
                break;
        }
    }


    private void setHospitalName(String HospitalName) {
        edtHospital.setText(HospitalName);
    }

    /**
     * 点击完成
     */
    public void complete() {
        final LoadingUtil loadingUtil = LoadingUtil.getInstance(this);
        AddOnlineConsultationRecordBean bean = combine();
        final SuccessInterfaceM<MesBean> listener = new SuccessInterfaceM<MesBean>() {
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
        if (bean != null) {
            loadingUtil.show();
            if (list.size() == 0) {
                inserOnlineConsultationRecord(bean, listener);
            } else {
                AddOnlineConsultationRecordBean addOnlineConsultationRecordBean = combine();
                addOnlineConsultationRecordBean.setRecorddocuments(listNewDocumentDisplayBean);
                inserOnlineConsultationRecord(addOnlineConsultationRecordBean, listener);
            }
        }
    }

    /**
     * 添加
     *
     * @param bean
     */
    private void inserOnlineConsultationRecord(AddOnlineConsultationRecordBean bean, final SuccessInterfaceM<MesBean> listener) {
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
                Bugtags.sendException(t);
                listener.success(mesBean);
            }
        });
    }

    private NewPatientRecordDisplayBean convert(AddOnlineConsultationRecordBean bean) {
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
        beanNew.record.onlineConsultationRecord = new NewOnlineConsultationRecordDetailDisplayBean();

        beanNew.record.onlineConsultationRecord.currentTreatment = bean.record.onlineConsultationRecord.currentTreatment;
        beanNew.record.onlineConsultationRecord.majorIssue = bean.record.onlineConsultationRecord.majorIssue;
        beanNew.record.onlineConsultationRecord.onlineConsultationDoctorComment = bean.record.onlineConsultationRecord.onlineConsultationDoctorComment;
        beanNew.record.onlineConsultationRecord.onlineConsultationPurpose = bean.record.onlineConsultationRecord.onlineConsultationPurpose;
        beanNew.record.onlineConsultationRecord.presentIllness = bean.record.onlineConsultationRecord.presentIllness;
        return beanNew;
    }

    /**
     * 验证数据
     */
    private boolean verifyData() {
        StringBuilder  mes = new StringBuilder();
        boolean result = true;
        //机构
        if (select == null) {
            //showToast("科室尚未选择");
            mes.append("科室尚未选择\n");
            result = false;
        }
        //科室
        String hospitalName = getTvValue(edtHospital);
        if (isEmpty(hospitalName)) {
            //showToast("机构尚未选择");
            mes.append("机构尚未选择\n");
            result = false;
        }
        //医师名称
        String doctorName = getTvValue(edtDoctor);
        if (isEmpty(doctorName)) {
            //showToast("医师名称尚未填写");
            mes.append("医师名称尚未填写\n");
            result = false;
        }
        //网络咨询原因
        if (getOnlineConsultationPurpose(raCheckbox.getSelectedIndx()) == null) {
            //showToast("网络咨询原因未选择");
            mes.append("网络咨询原因未选择\n");
            result = false;
        }
        //主诉/现病史edt_history
        if (isEmpty(getTvValue(edtPresentIllness))) {
          //  showToast("主诉/现病史尚未填写");
            mes.append("主诉/现病史尚未填写\n");
            result = false;
        }
        //印象/计划
        String plan = getTvValue(edtComment);
        if (isEmpty(plan)) {
           // showToast("印象/计划尚未填写");
            mes.append("印象/计划尚未填写");
            result = false;
        }
        if (result ==false) {
            new SweetAlertDialog(AddOnlineConsultationRecordActivity.this, SweetAlertDialog.WARNING_TYPE).setTitleText("错误提示！").setContentText(mes.toString()).show();
        }
        return result;
    }


    //组合数据
    private AddOnlineConsultationRecordBean combine() {
        if (verifyData()) {
            int patientId = iGetBundleHealthyRecord.getPatientId();
            if (patientId != 0) {
                AddOnlineConsultationRecordBean bean = new AddOnlineConsultationRecordBean(
                        patientId,
                        getTvValue(edtRemark),
                        getTvValue(tvRecordDate) + "T00:00:00",
                        getTvValue(edtDoctor),
                        getTvValue(edtHospital),
                        select.departmentId,
                        subject,
                        getOnlineConsultationPurpose(raCheckbox.getSelectedIndx()),
                        getTvValue(edtMajorIssue),
                        getTvValue(edtComment), null,
                        getTvValue(edtPresentIllness),
                        null
                        , Status.Release);
                return bean;
            }
        }
        return null;
    }

    /**
     * 0: 1: 2:
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
            tvRecordDate.setText(format2.format(format1.parse(dayOfBirth)));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void upLoadImags() {
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

    private List<NewDocumentDisplayBean> listNewDocumentDisplayBean;
}
