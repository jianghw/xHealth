package com.kaurihealth.kaurihealth.common.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bugtags.library.Bugtags;
import com.example.commonlibrary.widget.gallery.util.GalleryUtil;
import com.example.commonlibrary.widget.gallery.util.GetUrlsInterface;
import com.example.commonlibrary.widget.util.DialogFactory;
import com.example.commonlibrary.widget.util.LoadingUtil;
import com.example.commonlibrary.widget.util.SuccessInterface;
import com.kaurihealth.datalib.request_bean.bean.DepartmentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.request_bean.bean.NewDocumentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewLabTestDetailDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewLabTestPatientRecordDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.PatientRecordDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.Status;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;
import com.kaurihealth.datalib.service.IDoctorPatientRelationshipService;
import com.kaurihealth.datalib.service.ILabTestService;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.common.util.AddRecordConfig;
import com.kaurihealth.kaurihealth.common.util.LabTestUtil;
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
import com.kaurihealth.kaurihealth.util.PickImageUtil;
import com.kaurihealth.kaurihealth.util.UploadFileUtil;
import com.kaurihealth.kaurihealth.util.Url;
import com.kaurihealth.utilslib.date.DateUtils;
import com.kaurihealth.utilslib.dialog.SelectDateDialog;
import com.kaurihealth.utilslib.dialog.SuccessInterfaceM;
import com.youyou.zllibrary.util.CommonActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import biz.kasual.materialnumberpicker.MaterialNumberPicker;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddLabTestActivity extends CommonActivity {
    @Bind(R.id.tv_labTest)
    TextView tvLabtest;
    @Bind(R.id.tv_category)
    TextView tvCategory;
    @Bind(R.id.tv_subject)
    TextView tvSubject;
    @Bind(R.id.tvRecordDate)
    TextView tvRecordDate;
    @Bind(R.id.edtDepartmentName)
    TextView edtDepartmentName;
    @Bind(R.id.edtHospital)
    TextView edtHospital;
    @Bind(R.id.edtDoctor)
    EditText edtDoctor;
    @Bind(R.id.edtRemark)
    EditText edtRemark;
    @Bind(R.id.gvContent)
    GridView gvContent;
    @Bind(R.id.includelay)
    View includelay;
    @Bind(R.id.tv_title)
    TextView tv_title;
    NewLabTestPatientRecordDisplayBean bean = new NewLabTestPatientRecordDisplayBean();

    {
        bean.status = Status.Release.value;
    }

    private IMedicalRecordUtil iMedicalRecordUtil = new MedicalRecordUtil();
    /**
     * 保存图片的路径
     */
    private ArrayList<String> imagePath = new ArrayList<>();
    private StringPathViewAdapter imageAdapter;
    private PickImageUtil pickImageUtil;
    private UploadFileUtil uploadFileUtil;
    private IGetBundleHealthyRecord iGetBundleHealthyRecord;
    private Bundle bundle;
    private DoctorPatientRelationshipBean doctorPatientRelationshipBean;
    private String category;
    private String subject;
    private IGetter getter;
    private Dialog dateDialog;
    private Dialog labtestDialog;
    private MaterialNumberPicker numberpicker;
    private String[] content;
    private NewLabTestDetailDisplayBean[] newLabTestDetailDisplayBean;
    private ILabTestService iLabTestService;
    private LoadingUtil loadingUtil;
    private Callback<PatientRecordDisplayBean> callback;
    private List<NewDocumentDisplayBean> imags = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lab_test);
        ButterKnife.bind(this);
        init();
    }

    //------------------------------------------初始化-------------------------------------------
    @Override
    public void init() {
        super.init();
        initTools();
        initUi();
    }

    /***
     * 初始化工具
     */
    private void initTools() {
        bundle = getBundle();
        getter = Getter.getInstance(getApplicationContext());
        doctorPatientRelationshipBean = (DoctorPatientRelationshipBean) bundle.getSerializable(ConfigBundleHealthyRecord.CurrentDoctorPatientRelationship);
        bean.patientId = doctorPatientRelationshipBean.patientId;
        bean.status = "1";
        imageAdapter = new StringPathViewAdapter(getApplicationContext(), imagePath);
        pickImageUtil = new PickImageUtil(this, imagePath, imageAdapter);
        pickImageUtil.setListener(new SuccessInterface() {
            @Override
            public void success() {
                resetTime();
                uploadImags();
            }
        });
        uploadFileUtil = new UploadFileUtil(bundle.getString("kauriHealthId"), getter.getToken(), this, Url.AddImage);
        IBundleFactory iBundleFactory = new BundleFactory();
        iGetBundleHealthyRecord = iBundleFactory.getIGetBundleHealthyRecord(bundle);
        category = bundle.getString("category");
        subject = bundle.getString("subject");
        tv_title.setText(subject);
        SelectDateDialog selectDateDialog = new SelectDateDialog(this, new SelectDateDialog.DialogListener() {
            @Override
            public void onclick(String year, String month, String day) {
                setDateOfBirth(year, month, day);
            }
        });
        dateDialog = selectDateDialog.getDataDialog();
        newLabTestDetailDisplayBean = LabTestUtil.getNewLabTestDetailDisplayBean(subject);
        content = new String[newLabTestDetailDisplayBean.length];
        for (int i = 0; i < newLabTestDetailDisplayBean.length; i++) {
            content[i] = newLabTestDetailDisplayBean[i].labTestType;
        }
        View departmentView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_number_picker, null);
        numberpicker = (MaterialNumberPicker) departmentView.findViewById(R.id.number_picker);
        TextView tv_complete = (TextView) departmentView.findViewById(R.id.tv_complete);
        tv_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLabTest();
            }
        });
        labtestDialog = DialogFactory.createDialog(departmentView, this, R.style.Dialog_Date);
        setNumberPicker(numberpicker, content);

        loadingUtil = LoadingUtil.getInstance(this);

        callback = new Callback<PatientRecordDisplayBean>() {
            @Override
            public void onResponse(Call<PatientRecordDisplayBean> call, Response<PatientRecordDisplayBean> response) {
                if (response.isSuccessful()) {
                    loadingUtil.dismiss("提交数据成功", new LoadingUtil.Success() {
                        @Override
                        public void dismiss() {
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
                            finishCur();
                        }
                    });
                } else {
                    loadingUtil.dismiss("提交数据失败");
                }
            }

            @Override
            public void onFailure(Call<PatientRecordDisplayBean> call, Throwable t) {
                Bugtags.sendException(t);
                loadingUtil.dismiss(LoadingStatu.NetError.value);
            }
        };
    }

    /**
     * 初始化界面
     */
    private void initUi() {
        setBack(R.id.iv_back);
        gvContent.setAdapter(imageAdapter);
        iMedicalRecordUtil.setTop(includelay, iGetBundleHealthyRecord.getName(), iGetBundleHealthyRecord.getGender(), iGetBundleHealthyRecord.getAge());
        tvCategory.setText(category);
        tvSubject.setText(subject);
        tvRecordDate.setText(DateUtils.GetNowDate("yyyy-MM-dd"));
        bean.recordDate = DateUtils.GetNowDate("yyyy-MM-dd'T'HH:mm:ss");
        bean.subject = subject;
        initDepartment();
        String hospitalName = getter.getHospitalName();
        bean.hospital = hospitalName;
        edtHospital.setText(hospitalName);
        edtDoctor.setText(getter.getFullName());
        bean.doctor = getter.getFullName();
        bean.recordDocuments = imags;
        gvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GalleryUtil.toGallery(AddLabTestActivity.this, new GetUrlsInterface() {
                    @Override
                    public ArrayList<String> getUrls() {
                        return imagePath;
                    }
                }, position);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        uploadFileUtil.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        uploadFileUtil.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        pickImageUtil.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case DepartmentLevel1Activity.ToDepartmentLevel:
                if (resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    DepartmentDisplayBean bean = (DepartmentDisplayBean) extras.getSerializable(DepartmentLevel2Activity.DepartmentLevel2ActivityKey);
                    AddLabTestActivity.this.bean.departmentId = bean.departmentId;
                    edtDepartmentName.setText(bean.departmentName);
                }
                break;
            case HospitalNameActivity.EdithHospitalName:
                if (resultCode == RESULT_OK) {
                    String hospitalName = data.getStringExtra("hospitalName");
                    edtHospital.setText(hospitalName);
                    AddLabTestActivity.this.bean.hospital = hospitalName;
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        pickImageUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //------------------------------------------点击事件-------------------------------------------
    @OnClick({R.id.edtHospital, R.id.iv_uploadfile, R.id.tv_operate, R.id.edtDepartmentName, R.id.tvRecordDate, R.id.tv_labTest})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edtHospital:
                chooseHospital();
                break;
            case R.id.iv_uploadfile:
                chooseImage();
                break;
            case R.id.tv_operate:
                chooseComplete();
                break;
            case R.id.edtDepartmentName:
                chooseDepartment();
                break;
            case R.id.tvRecordDate:
                chooseTime();
                break;
            case R.id.tv_labTest:
                chooseLabTest();
                break;
            default:
                break;
        }
    }

    private void chooseLabTest() {
        if (labtestDialog.isShowing()) {
            labtestDialog.dismiss();
        } else {
            labtestDialog.show();
        }
    }

    /**
     * 点击检查项目
     */
    private void setLabTest() {
        labtestDialog.dismiss();
        int index = numberpicker.getValue();
        tvLabtest.setText(content[index]);
        bean.labTest = newLabTestDetailDisplayBean[index];
    }

    /**
     * 点击完成
     */
    private void chooseComplete() {
        bean.doctor = edtDoctor.getText().toString();
        bean.comment = edtRemark.getText().toString();
        if (checkData()) {
            commitDataToServer();
        }
    }

    /**
     * 点击选择图片
     */
    private void chooseImage() {
        storeTime(bean.recordDate);
        pickImageUtil.pickImage();
    }

    /**
     * 点击选择机构
     */
    private void chooseHospital() {
        Bundle data = new Bundle();
        data.putString("hospitalName", bean.hospital);
        data.putInt("requestcode", HospitalNameActivity.EdithHospitalName);
        skipToForResult(HospitalNameActivity.class, data, HospitalNameActivity.EdithHospitalName);
    }

    /**
     * 点击选择科室
     */
    private void chooseDepartment() {
        skipToForResult(DepartmentLevel1Activity.class, null, DepartmentLevel1Activity.ToDepartmentLevel);
    }

    /**
     * 点击选择门诊时间
     */
    private void chooseTime() {
        if (dateDialog.isShowing()) {
            dateDialog.dismiss();
        } else {
            dateDialog.show();
        }
    }

    /**
     * 检查输入是否完整
     */
    private boolean checkData() {
        boolean result = check("检查分类", bean.subject) &&
                check("检查项目", bean.labTest) &&
                check("门诊时间", bean.recordDate) &&
                check("医师", bean.doctor) &&
                check("科室", bean.departmentId) &&
                check("图片", imagePath);
        return result;
    }

    private boolean check(String iteam, Object object) {
        boolean result = true;
        String hint = iteam + "尚未填写";
        if (iteam != null) {
            if (object instanceof String) {
                if (TextUtils.isEmpty((String) object)) {
                    result = false;
                }
            } else if (object instanceof Integer) {
                if (((Integer) object) == NewLabTestPatientRecordDisplayBean.errorDefault) {
                    result = false;
                }
            } else if (object instanceof List) {
                if (((List) object).size() == 0) {
                    result = false;
                }
            } else {
                if (object == null) {
                    result = false;
                }
            }
        } else {
            result = false;
        }
        if (result == false) {
            showToast(hint);
        }
        return result;
    }

    /***
     * 提交数据到服务器
     */
    private void commitDataToServer() {
        loadingUtil = LoadingUtil.getInstance(this);
        loadingUtil.show("提交数据中");
        IServiceFactory iServiceFactory = new ServiceFactory(Url.prefix,getApplicationContext());
        iLabTestService = iServiceFactory.getILabTestService();
        Call<PatientRecordDisplayBean> patientRecordDisplayBeanCall = iLabTestService.InsertLabTest(bean);
        patientRecordDisplayBeanCall.enqueue(callback);
    }


    //------------------------抽取出来的方法------------------------
    private void initDepartment() {
        DepartmentDisplayBean department = null;
        try {
            department = getter.getDepartment();
            edtDepartmentName.setText(department.departmentName);
            bean.departmentId = department.departmentId;
        } catch (NoValueException e) {
            e.printStackTrace();
        }
    }

    private void setNumberPicker(MaterialNumberPicker numberpicker, String[] content) {
        numberpicker.setDisplayedValues(null);
        numberpicker.setMaxValue(content.length - 1);
        numberpicker.setMinValue(0);
        numberpicker.setValue(1);
        numberpicker.setDisplayedValues(content);
    }

    private void setDateOfBirth(String year, String month, String day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.valueOf(year));
        calendar.set(Calendar.MONTH, Integer.valueOf(month) - 1);
        calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(day));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String timeShow = format.format(calendar.getTime());
        tvRecordDate.setText(timeShow);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        bean.recordDate = format1.format(calendar.getTime());
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
            bean.recordDate = time;
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
            tvRecordDate.setText(format2.format(format1.parse(time)));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void uploadImags() {
        if (imagePath.size() != 0) {
            final LoadingUtil loadingUtil = LoadingUtil.getInstance(this);
            loadingUtil.show(AddRecordConfig.AddImages);
            uploadFileUtil.startUpload(imagePath, new SuccessInterfaceM<String>() {
                @Override
                public void success(String s) {
                    if (!(TextUtils.isEmpty(s))) {
                        List<NewDocumentDisplayBean> newDocumentDisplayBeen = JSON.parseArray(s, NewDocumentDisplayBean.class);
                        bean.recordDocuments = newDocumentDisplayBeen;
                    }
                    loadingUtil.dismiss();
                }
            });
        }
    }
}

