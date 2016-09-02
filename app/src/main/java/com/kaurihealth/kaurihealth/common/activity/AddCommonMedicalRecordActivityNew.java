package com.kaurihealth.kaurihealth.common.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bugtags.library.Bugtags;
import com.example.commonlibrary.widget.gallery.util.GalleryUtil;
import com.example.commonlibrary.widget.gallery.util.GetUrlsInterface;
import com.example.commonlibrary.widget.util.LoadingUtil;
import com.example.commonlibrary.widget.util.SuccessInterface;
import com.kaurihealth.datalib.request_bean.bean.DepartmentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.request_bean.bean.DocumentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewDocumentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewPathologyPatientRecordDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewPatientRecordDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewRecordDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewSupplementaryTestPatientRecordDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.PatientRecordDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.Status;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;
import com.kaurihealth.datalib.service.IDoctorPatientRelationshipService;
import com.kaurihealth.datalib.service.IPathologyRecordService;
import com.kaurihealth.datalib.service.IRecordService;
import com.kaurihealth.datalib.service.ISupplementaryTestService;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.common.Interface.IAddCommonMedicalRecord;
import com.kaurihealth.kaurihealth.common.bean.CommonMedicalBean;
import com.kaurihealth.kaurihealth.common.bean.MesBean;
import com.kaurihealth.kaurihealth.common.util.AddRecordConfig;
import com.kaurihealth.kaurihealth.common.util.MedicalRecordUtil;
import com.kaurihealth.kaurihealth.home.adapter.StringPathViewAdapter;
import com.kaurihealth.kaurihealth.home.util.BundleFactory;
import com.kaurihealth.kaurihealth.home.util.Config.ConfigBundleHealthyRecord;
import com.kaurihealth.kaurihealth.home.util.Interface.IGetBundleHealthyRecord;
import com.kaurihealth.kaurihealth.home.util.Interface.IMedicalRecordUtil;
import com.kaurihealth.kaurihealth.mine.activity.HospitalNameActivity;
import com.kaurihealth.kaurihealth.patientwithdoctor.activity.DepartmentLevel1Activity;
import com.kaurihealth.kaurihealth.patientwithdoctor.activity.DepartmentLevel2Activity;
import com.kaurihealth.kaurihealth.services.IServiceFactory;
import com.kaurihealth.kaurihealth.services.ServiceFactory;
import com.kaurihealth.kaurihealth.util.Getter;
import com.kaurihealth.kaurihealth.util.Interface.IGetter;
import com.kaurihealth.kaurihealth.util.NoValueException;
import com.kaurihealth.kaurihealth.util.PickImageUtil;
import com.kaurihealth.kaurihealth.util.UploadFileUtil;
import com.kaurihealth.kaurihealth.util.Url;
import com.kaurihealth.utilslib.date.DateUtils;
import com.kaurihealth.utilslib.dialog.DateBean;
import com.kaurihealth.utilslib.dialog.SelectDateDialog;
import com.kaurihealth.utilslib.dialog.SuccessInterfaceM;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.youyou.zllibrary.util.CommonActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCommonMedicalRecordActivityNew extends CommonActivity implements Validator.ValidationListener {
    CommonMedicalBean commonMedicalBean = new CommonMedicalBean();
    @Bind(R.id.includelay)
    View includelay;
    @Bind(R.id.tv_category)
    TextView tvCategory;
    @Bind(R.id.tv_subject)
    TextView tvSubject;
    @Bind(R.id.tvRecordDate)
    TextView tvRecordDate;
    @Bind(R.id.edtDepartmentName)
    TextView tvDepartmentName;
    @Bind(R.id.edtHospital)
    TextView tvHospital;
    @Bind(R.id.edtDoctor)
    EditText edtDoctor;
    @Bind(R.id.gvContent)
    GridView gvContent;
    @Bind(R.id.edtRemark)
    EditText edtRemark;
    @Bind(R.id.tv_title)
    TextView tv_title;
    private Dialog timeSelectDialog;
    private UploadFileUtil uploadFileTool;
    private PickImageUtil iPickImagesTool;
    private IRecordService addNewPatientRecordService;
    private ISupplementaryTestService iSupplementaryTestService;
    private IPathologyRecordService iPathologyRecordService;
    public static final int EdithHospitalName = 10;
    //private ErrorDialog errorDialog;
    private String category;
    private String subject;
    private int patientId;
    private LoadingUtil loadingUtil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_common_medical_record);
        ButterKnife.bind(this);
        setBack(R.id.iv_back);
        init();
    }


    @Override
    public void init() {
        super.init();
        IGetter getter = Getter.getInstance(getApplicationContext());
        Bundle bundle = getBundle();
        initTopUi(bundle, includelay);
        initCategoryAndSubject(bundle, tvCategory, tvSubject);
        initOtherUi(getter, tvRecordDate, tvDepartmentName, tvHospital, edtDoctor);
        StringPathViewAdapter adapter = getAdapter();
        gvContent.setAdapter(adapter);
        initTools(bundle, getter, adapter);
        gvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GalleryUtil.toGallery(AddCommonMedicalRecordActivityNew.this, new GetUrlsInterface() {
                    @Override
                    public ArrayList<String> getUrls() {
                        return commonMedicalBean.recordDocumentStrs;
                    }
                }, position);
            }
        });
    }

    @OnClick({R.id.tv_operate, R.id.tvRecordDate, R.id.edtDepartmentName, R.id.edtHospital, R.id.iv_uploadfile})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_operate:
                controlAddMedicalRecord();
                break;
            case R.id.tvRecordDate:
                timeSelectDialog.show();
                break;
            case R.id.edtDepartmentName:
                skipToForResult(DepartmentLevel1Activity.class, null, DepartmentLevel1Activity.ToDepartmentLevel);
                break;
            case R.id.edtHospital:
                chooseOrganization();
                break;
            case R.id.iv_uploadfile:
                iPickImagesTool.pickImage();
                break;
        }
    }

    //初始化顶部界面
    private void initTopUi(Bundle bundle, View includelay) {
        BundleFactory iBundleFactory = new BundleFactory();
        IGetBundleHealthyRecord iGetBundleHealthyRecord = iBundleFactory.getIGetBundleHealthyRecord(bundle);
        IMedicalRecordUtil iMedicalRecordUtil = new MedicalRecordUtil();
        iMedicalRecordUtil.setTop(includelay, iGetBundleHealthyRecord.getName(), iGetBundleHealthyRecord.getGender(), iGetBundleHealthyRecord.getAge());
        patientId = iGetBundleHealthyRecord.getPatientId();
    }

    //初始化类型与项目
    private void initCategoryAndSubject(Bundle bundle, TextView tvCategory, TextView tvSubject) {
        category = bundle.getString("category");
        subject = bundle.getString("subject");
        tvCategory.setText(category);
        tvSubject.setText(subject);
        tv_title.setText("医疗记录");
        commonMedicalBean.category = category;
        commonMedicalBean.subject = subject;
    }

    //初始化门诊时间,科室，机构，医师
    private void initOtherUi(IGetter iGetter, TextView tvRecordDate, TextView tvDepartment, TextView tvHospital, TextView tvDoctor) {
        Date date = Calendar.getInstance().getTime();
        String hospitalName = iGetter.getHospitalName();
        String fullName = iGetter.getFullName();
        tvRecordDate.setText(DateUtils.GetDateText(date, "yyyy-MM-dd"));
        try {
            DepartmentDisplayBean department = iGetter.getDepartment();
            tvDepartment.setText(department.departmentName);
            commonMedicalBean.department = department;
        } catch (NoValueException e) {
            e.printStackTrace();
        }
        tvHospital.setText(hospitalName);
        tvDoctor.setText(fullName);
        commonMedicalBean.recordDate = DateUtils.GetDateText(date);
        commonMedicalBean.hospital = hospitalName;
        commonMedicalBean.doctor = fullName;
    }

    //初始化工具类
    private void initTools(Bundle bundle, IGetter getter, BaseAdapter adapter) {
        initTimeTool();
        initSelectImageTool(commonMedicalBean.recordDocumentStrs, adapter);
        initUploadImagTool(bundle, getter);
        initServiceTool();
        // initHintTool();
    }

    private void initHintTool() {
        // errorDialog = new ErrorDialog(this);
    }

    private void initTimeTool() {
        SelectDateDialog selectDateDialog = new SelectDateDialog(this);
        selectDateDialog.setOnClickListener(new SuccessInterfaceM<DateBean>() {
            @Override
            public void success(DateBean dateBean) {
                tvRecordDate.setText(dateBean.dateStrShow);
                commonMedicalBean.recordDate = dateBean.dateStr;
            }
        }, "yyyy-MM-dd");
        timeSelectDialog = selectDateDialog.getDataDialog();
    }

    private void initSelectImageTool(ArrayList<String> images, BaseAdapter adapter) {
        iPickImagesTool = new PickImageUtil(this, images, adapter);
        iPickImagesTool.setListener(new SuccessInterface() {
            @Override
            public void success() {
                uploadImage();
            }
        });
    }

    private void initUploadImagTool(Bundle bundle, IGetter iGetter) {
        BundleFactory iBundleFactory = new BundleFactory();
        IGetBundleHealthyRecord iGetBundleHealthyRecord = iBundleFactory.getIGetBundleHealthyRecord(bundle);
        String kauriHealthId = iGetBundleHealthyRecord.getKauriHealthId();
        uploadFileTool = new UploadFileUtil(kauriHealthId, iGetter.getToken(), this, Url.AddImage);
    }

    private void initServiceTool() {
        IServiceFactory serviceFactory = new ServiceFactory(Url.prefix, getApplicationContext());
        //临床诊疗
        addNewPatientRecordService = serviceFactory.getIRecordService();
        //辅助检查
        iSupplementaryTestService = serviceFactory.getISupplementaryTestService();
        //病理检查
        iPathologyRecordService = serviceFactory.getIPathologyRecordService();
    }

    private StringPathViewAdapter getAdapter() {
        StringPathViewAdapter adapter = new StringPathViewAdapter(getApplicationContext(), commonMedicalBean.recordDocumentStrs);
        return adapter;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case EdithHospitalName:
                if (resultCode == Activity.RESULT_OK) {
                    String hospitalName = data.getStringExtra("hospitalName");
                    commonMedicalBean.hospital = hospitalName;
                    tvHospital.setText(hospitalName);
                }
                break;
            case DepartmentLevel1Activity.ToDepartmentLevel:
                if (resultCode == Activity.RESULT_OK) {
                    Bundle extras = data.getExtras();
                    DepartmentDisplayBean bean = (DepartmentDisplayBean) extras.getSerializable(DepartmentLevel2Activity.DepartmentLevel2ActivityKey);
                    tvDepartmentName.setText(bean.departmentName);
                    commonMedicalBean.department = bean;
                }
                break;
            default:
                iPickImagesTool.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        uploadFileTool.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        uploadFileTool.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        iPickImagesTool.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    private void controlAddMedicalRecord() {
        final CommonMedicalBean commonMedicalBean = iAddCommonMedicalRecord.createCommonMedicalBean();
        iAddCommonMedicalRecord.setListener(new SuccessInterfaceM<MesBean>() {
            @Override
            public void success(MesBean mesBean) {
                if (mesBean.isSuccess) {
                    Intent intent = getIntent();
                    //对远程患者进行特殊处理
                    DoctorPatientRelationshipBean  doctorPatientRelationshipBean = (DoctorPatientRelationshipBean) intent.getExtras().getSerializable(ConfigBundleHealthyRecord.CurrentDoctorPatientRelationship);
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
        });
        if (iAddCommonMedicalRecord.verifyData(commonMedicalBean)) {
            loadingUtil = LoadingUtil.getInstance(this);
            loadingUtil.show();
//            if (iAddCommonMedicalRecord.judgeMeedToUpload(commonMedicalBean)) {
//                iAddCommonMedicalRecord.uploadImags(commonMedicalBean, new SuccessInterfaceM<String>() {
//                    @Override
//                    public void success(String mes) {
//                        if (!(TextUtils.isEmpty(mes))) {
//                            List<DocumentDisplayBean> documentDisplayBeen = JSON.parseArray(mes, DocumentDisplayBean.class);
//                            CommonMedicalBean commonMedicalBeanNew = iAddCommonMedicalRecord.getCommonMedicalBean();
//                            commonMedicalBeanNew.recordDocuments = documentDisplayBeen;
//                        }
//                        iAddCommonMedicalRecord.addMedicalRecord(commonMedicalBean);
//                    }
//                });
//            } else {
            iAddCommonMedicalRecord.addMedicalRecord(commonMedicalBean);
//            }
        }
    }

    //点击机构
    private void chooseOrganization() {
        Bundle data = new Bundle();
        data.putString("hospitalName", getTvValue(tvHospital));
        data.putInt("requestcode", EdithHospitalName);
        skipToForResult(HospitalNameActivity.class, data, EdithHospitalName);
    }

    IAddCommonMedicalRecord iAddCommonMedicalRecord = new IAddCommonMedicalRecord() {
        private String[] medicalRecords;
        private StringBuilder stringBuilder = new StringBuilder();
        private int ErrorDefault = -10;
        private SuccessInterfaceM<MesBean> listener;

        @Override
        public void setListener(SuccessInterfaceM<MesBean> listener) {
            this.listener = listener;
        }

        @Override
        public CommonMedicalBean createCommonMedicalBean() {
            commonMedicalBean.comment = getTvValue(edtRemark);
            commonMedicalBean.doctor = getTvValue(edtDoctor);
            return commonMedicalBean;
        }

        @Override
        public CommonMedicalBean getCommonMedicalBean() {
            return commonMedicalBean;
        }

        @Override
        public boolean verifyData(CommonMedicalBean bean) {
            boolean result = true;
            stringBuilder.delete(0, stringBuilder.length());
            if (TextUtils.isEmpty(bean.category)) {
                result &= false;
                append("类型");
            }
            if (TextUtils.isEmpty(bean.subject)) {
                result &= false;
                append("项目名称");
            }
            if (TextUtils.isEmpty(bean.recordDate)) {
                result &= false;
                append("门诊时间");
            }
            if (bean.department == null) {
                result &= false;
                append("科室名称");
            }
            if (TextUtils.isEmpty(bean.hospital)) {
                result &= false;
                append("机构名称");
            }
            if (TextUtils.isEmpty(bean.doctor)) {
                result &= false;
                append("医师姓名");
            }
            if (bean.recordDocumentStrs.size() == 0) {
                result &= false;
                append("图片尚未选择\n", false);
            }
            if (!result) {

              /*  errorDialog.setMes(stringBuilder.toString());
                errorDialog.show();*/
                new SweetAlertDialog(AddCommonMedicalRecordActivityNew.this, SweetAlertDialog.WARNING_TYPE).setTitleText("错误提示！").setContentText(stringBuilder.toString()).show();


            }
            return result;
        }

        private void append(String mes) {
            append(mes, true);
        }

        private void append(String mes, boolean replace) {
            if (replace == false) {
                stringBuilder.append(mes);
            } else {
                stringBuilder.append(mes);
                stringBuilder.append("尚未填写\n");
            }

        }

        @Override
        public boolean judgeMeedToUpload(CommonMedicalBean bean) {
            return bean.recordDocumentStrs.size() != 0;
        }

        @Override
        public void uploadImags(CommonMedicalBean bean, SuccessInterfaceM<String> uploadSuccessListener) {
            uploadFileTool.startUpload(bean.recordDocumentStrs, uploadSuccessListener);
        }

        @Override
        public void addMedicalRecord(CommonMedicalBean bean) {
            if (medicalRecords == null) {
                medicalRecords = getResources().getStringArray(R.array.medicalrecod);
            }
            String category = getCategory();
            int index = ErrorDefault;
            for (int i = 0; i < medicalRecords.length; i++) {
                if (category.equals(medicalRecords[i])) {
                    index = i;
                    break;
                }
            }
            if (index != ErrorDefault) {
                switch (index) {
                    case 0:
                        addNewPatientRecord(bean);
                        break;
                    case 1:
                        insertSupplementaryTest(bean);
                        break;
                    case 3:
                        insertPathologyRecord(bean);
                        break;
                }
            }
        }


        @Override
        public String getCategory() {
            return category;
        }

        @Override
        public int getPatientId() {
            return patientId;
        }

        @Override
        public void insertPathologyRecord(CommonMedicalBean bean) {
            NewPathologyPatientRecordDisplayBean pathologyBean = new NewPathologyPatientRecordDisplayBean();
            pathologyBean.comment = bean.comment;
            pathologyBean.departmentId = bean.department.departmentId;
            pathologyBean.doctor = bean.doctor;
            pathologyBean.hospital = bean.hospital;
            pathologyBean.patientId = getPatientId();
            pathologyBean.recordDate = bean.recordDate;
            pathologyBean.status = Status.Release.value;
            pathologyBean.subject = bean.subject;
            pathologyBean.recordDocuments = convert(bean.recordDocuments);
            Call<PatientRecordDisplayBean> patientRecordDisplayBeanCall = iPathologyRecordService.InsertPathologyRecord(pathologyBean);
            patientRecordDisplayBeanCall.enqueue(new Callback<PatientRecordDisplayBean>() {
                @Override
                public void onResponse(Call<PatientRecordDisplayBean> call, Response<PatientRecordDisplayBean> response) {
                    iAddCommonMedicalRecord.onResponse(call, response, listener);
                }

                @Override
                public void onFailure(Call<PatientRecordDisplayBean> call, Throwable t) {
                    iAddCommonMedicalRecord.onFailure(call, t, listener);
                }
            });
        }

        @Override
        public void addNewPatientRecord(CommonMedicalBean bean) {
            NewPatientRecordDisplayBean patientRecordDisplayBean = new NewPatientRecordDisplayBean();
            patientRecordDisplayBean.patientId = getPatientId();
            patientRecordDisplayBean.comment = bean.comment;
            patientRecordDisplayBean.recordDate = bean.recordDate;
            patientRecordDisplayBean.doctor = bean.doctor;
            patientRecordDisplayBean.hospital = bean.hospital;
            patientRecordDisplayBean.departmentId = bean.department.departmentId;
            patientRecordDisplayBean.subject = bean.subject;
            patientRecordDisplayBean.status = Status.Release.value;
            patientRecordDisplayBean.recordDocuments = convert(bean.recordDocuments);
            patientRecordDisplayBean.record = new NewRecordDisplayBean();
            patientRecordDisplayBean.record.ownerId = getPatientId();
            patientRecordDisplayBean.record.recordType = bean.subject;
            Call<PatientRecordDisplayBean> patientRecordDisplayBeanCall = addNewPatientRecordService.AddNewPatientRecord(patientRecordDisplayBean);
            patientRecordDisplayBeanCall.enqueue(new Callback<PatientRecordDisplayBean>() {
                @Override
                public void onResponse(Call<PatientRecordDisplayBean> call, Response<PatientRecordDisplayBean> response) {
                    iAddCommonMedicalRecord.onResponse(call, response, listener);
                }

                @Override
                public void onFailure(Call<PatientRecordDisplayBean> call, Throwable t) {
                    iAddCommonMedicalRecord.onFailure(call, t, listener);
                }
            });
        }

        @Override
        public void insertSupplementaryTest(CommonMedicalBean bean) {
            NewSupplementaryTestPatientRecordDisplayBean supplementBean = new NewSupplementaryTestPatientRecordDisplayBean();
            supplementBean.comment = bean.comment;
            supplementBean.departmentId = bean.department.departmentId;
            supplementBean.doctor = bean.doctor;
            supplementBean.hospital = bean.hospital;
            supplementBean.patientId = getPatientId();
            supplementBean.recordDate = bean.recordDate;
            supplementBean.recordDocuments = convert(bean.recordDocuments);
            supplementBean.subject = bean.subject;
            supplementBean.status = Status.Release.value;
            Call<PatientRecordDisplayBean> patientRecordDisplayBeanCall = iSupplementaryTestService.InsertSupplementaryTest(supplementBean);
            patientRecordDisplayBeanCall.enqueue(new Callback<PatientRecordDisplayBean>() {
                @Override
                public void onResponse(Call<PatientRecordDisplayBean> call, Response<PatientRecordDisplayBean> response) {
                    iAddCommonMedicalRecord.onResponse(call, response, listener);
                }

                @Override
                public void onFailure(Call<PatientRecordDisplayBean> call, Throwable t) {
                    iAddCommonMedicalRecord.onFailure(call, t, listener);
                }
            });
        }


        @Override
        public void onFailure(Call<PatientRecordDisplayBean> call, Throwable t, SuccessInterfaceM<MesBean> listener) {
            Bugtags.sendException(t);
            MesBean mesBean = new MesBean();
            mesBean.isSuccess = false;
//            mesBean.message = "更新医疗记录失败";
            mesBean.message = "更新失败";
            listener.success(mesBean);
        }

        @Override
        public void onResponse(Call<PatientRecordDisplayBean> call, Response<PatientRecordDisplayBean> response, SuccessInterfaceM<MesBean> listener) {
            MesBean mesBean = new MesBean();
            mesBean.isSuccess = response.isSuccessful();
            if (response.isSuccessful()) {
//                mesBean.message = "插入医疗记录成功";
                mesBean.message = "保存成功";
            } else {
                mesBean.message = "保存失败";
            }
            listener.success(mesBean);
        }

        private List<NewDocumentDisplayBean> convert(List<DocumentDisplayBean> recordDocuments) {
            List<NewDocumentDisplayBean> result = new LinkedList<>();
            for (DocumentDisplayBean iteam : recordDocuments) {
                NewDocumentDisplayBean iteamNew = new NewDocumentDisplayBean();
                iteamNew.displayName = iteam.displayName;
                iteamNew.documentFormat = iteam.documentFormat;
                iteamNew.documentUrl = iteam.documentUrl;
                iteamNew.fileName = iteam.fileName;
                result.add(iteamNew);
            }
            return result;
        }
    };

    //上传图片
    private void uploadImage() {
        if (iAddCommonMedicalRecord.judgeMeedToUpload(commonMedicalBean)) {
            final LoadingUtil loadingUtil = LoadingUtil.getInstance(this);
            loadingUtil.show(AddRecordConfig.AddImages);
            iAddCommonMedicalRecord.uploadImags(commonMedicalBean, new SuccessInterfaceM<String>() {
                @Override
                public void success(String mes) {
                    if (!(TextUtils.isEmpty(mes))) {
                        List<DocumentDisplayBean> documentDisplayBeen = JSON.parseArray(mes, DocumentDisplayBean.class);
                        CommonMedicalBean commonMedicalBeanNew = iAddCommonMedicalRecord.getCommonMedicalBean();
                        commonMedicalBeanNew.recordDocuments = documentDisplayBeen;
                    }
                    loadingUtil.dismiss();
                }
            });
        }
    }

    @Override
    public void onValidationSucceeded() {


    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {


    }
}
