package com.kaurihealth.kaurihealth.common.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bugtags.library.Bugtags;
import com.example.commonlibrary.widget.gallery.util.GalleryUtil;
import com.example.commonlibrary.widget.gallery.util.GetUrlsInterface;
import com.kaurihealth.utilslib.date.DateUtils;
import com.example.commonlibrary.widget.util.LoadingUtil;
import com.kaurihealth.utilslib.dialog.SelectDateDialog;
import com.example.commonlibrary.widget.util.SuccessInterface;
import com.kaurihealth.utilslib.dialog.SuccessInterfaceM;
import com.kaurihealth.datalib.request_bean.bean.DepartmentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.PersonInfoBean;
import com.kaurihealth.datalib.request_bean.bean.PrescriptionBean;
import com.kaurihealth.datalib.request_bean.bean.NewDocumentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewPrescriptionBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.datalib.request_bean.builder.NewDocumentDisplayBeanBuilder;
import com.kaurihealth.datalib.request_bean.builder.NewPrescriptionBeanBuilder;
import com.kaurihealth.kaurihealth.home.adapter.StringPathViewAdapter;
import com.kaurihealth.kaurihealth.home.util.BundleFactory;
import com.kaurihealth.kaurihealth.home.util.Interface.IBundleFactory;
import com.kaurihealth.kaurihealth.home.util.Interface.IGetBundleHealthyRecord;
import com.kaurihealth.kaurihealth.mine.activity.HospitalNameActivity;
import com.kaurihealth.kaurihealth.patientwithdoctor.activity.DepartmentLevel1Activity;
import com.kaurihealth.kaurihealth.patientwithdoctor.activity.DepartmentLevel2Activity;
import com.kaurihealth.kaurihealth.services.IServiceFactory;
import com.kaurihealth.kaurihealth.services.ServiceFactory;
import com.kaurihealth.kaurihealth.util.Enums.LoadingStatu;
import com.kaurihealth.kaurihealth.util.ExitApplication;
import com.kaurihealth.kaurihealth.util.Getter;
import com.kaurihealth.kaurihealth.util.Interface.IGetter;
import com.kaurihealth.kaurihealth.util.Interface.IPickImages;
import com.kaurihealth.kaurihealth.util.Interface.IUploadFile;
import com.kaurihealth.kaurihealth.util.PickImage;
import com.kaurihealth.kaurihealth.util.TagsGridview;
import com.kaurihealth.kaurihealth.util.UploadFileUtil;
import com.kaurihealth.kaurihealth.util.Url;
import com.youyou.zllibrary.util.CommonActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPrescriptionActivity extends CommonActivity {
    @Bind(R.id.tvRecordDate)
    TextView tvRecordDate;
    @Bind(R.id.edtDepartmentName)
    TextView edtDepartmentName;
    @Bind(R.id.edtHospital)
    TextView edtHospital;
    @Bind(R.id.edtDoctor)
    EditText edtDoctor;
    @Bind(R.id.gvContent)
    TagsGridview gvContent;
    @Bind(R.id.edtRemark)
    EditText edtRemark;
    private Dialog dateDialog;
    private int patientId = 0;
    String date;//开方日期
    String doctor;//医生
    String comment;//备注
    int departmentId = -1;
    String Hospital;//机构
    private StringPathViewAdapter adapter;
    //    private Dialog dialog;
    private NewPrescriptionBeanBuilder newPrescriptionBeanBuilder;
    private NewDocumentDisplayBeanBuilder newDocumentDisplayBeanBuilder;
    private String kauriHealthId;
    private IPickImages pickImages;
    private IUploadFile<String> uploadFile;
    private IBundleFactory iBundleFactory = new BundleFactory();
    private IGetBundleHealthyRecord iGetBundleHealthyRecord;
    private IServiceFactory prescriptionService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_prescription);
        ButterKnife.bind(this);
        init();
        ExitApplication.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    ArrayList<String> paths = new ArrayList<>();

    public void init() {
        setBack(R.id.iv_back);
        Bundle bundle = getBundle();
        iGetBundleHealthyRecord = iBundleFactory.getIGetBundleHealthyRecord(bundle);
        kauriHealthId = iGetBundleHealthyRecord.getKauriHealthId();
        patientId = iGetBundleHealthyRecord.getPatientId();
        IGetter getter = Getter.getInstance(getApplicationContext());
        pickImages = new PickImage(paths, this, new SuccessInterface() {
            @Override
            public void success() {
                adapter.notifyDataSetChanged();
            }
        });
        uploadFile = new UploadFileUtil(kauriHealthId, getter.getToken(), this, Url.AddImage);
        PersonInfoBean personInfo = getter.getPersonInfo();
        edtDepartmentName.setText(personInfo.department);
        edtHospital.setText(personInfo.organizationName);
        edtDoctor.setText(personInfo.fullName);
        SelectDateDialog selectDateDialog = new SelectDateDialog(this, new SelectDateDialog.DialogListener() {
            @Override
            public void onclick(String year, String month, String day) {
                setDateOfBirth(year, month, day);
            }
        });
        dateDialog = selectDateDialog.getDataDialog();
        //添加图片到GridView中
        adapter = new StringPathViewAdapter(getApplicationContext(), paths);
        gvContent.setAdapter(adapter);
        newPrescriptionBeanBuilder = new NewPrescriptionBeanBuilder();
        newDocumentDisplayBeanBuilder = new NewDocumentDisplayBeanBuilder();
        tvRecordDate.setText(DateUtils.GetNowDate("yyyy-MM-dd"));
        prescriptionService = new ServiceFactory(Url.prefix, getApplicationContext());
    }


    @OnClick({R.id.ivDelete, R.id.tv_operate, R.id.rlay_image, R.id.rlay_birthday, R.id.edtDepartmentName, R.id.edtHospital})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivDelete:
                clearTextView(tvRecordDate);
                break;
            case R.id.tv_operate:
                store();
                break;
            case R.id.rlay_image:
                pickImages.pickImage();
                break;
            case R.id.rlay_birthday:
                dateDialog.show();
                break;
            case R.id.edtDepartmentName:
                department();
                break;
            case R.id.edtHospital:
                hospital();
                break;
        }
    }

    /**
     * 验证输入信息
     */
    private void store() {
        boolean isUseful = true;
        date = getTvValue(tvRecordDate);//开方日期
        doctor = getTvValue(edtDoctor);//医生
        comment = getTvValue(edtRemark);//备注
        if (select != null) {
            departmentId = select.departmentId;//科室id
        }
        Hospital = getTvValue(edtHospital);//机构
        StringBuilder prompt = new StringBuilder();
        if (isEmpty(date)) {
            prompt.append("开方日期不能为空,");
            isUseful = false;
        }
        if (isEmpty(doctor)) {
            prompt.append("医生不能为空,");
            isUseful = false;
        }
        if (isEmpty(getTvValue(edtDepartmentName))) {
            prompt.append("科室不能为空,");
            isUseful = false;
        }
        if (paths.size() == 0) {
            prompt.append("处方图片不能为空,");
            isUseful = false;
        }
        if (isEmpty(Hospital)) {
            prompt.append("机构不能为空,");
            isUseful = false;
        }

        if (isUseful) {
            if (patientId != 0) {
                List<NewDocumentDisplayBean> documents = new ArrayList<>();
                //判断是否有图片上传
                if (paths.size() == 0) {
                    NewPrescriptionBean newPrescriptionBean = newPrescriptionBeanBuilder.Build(patientId, date, doctor, comment, departmentId, Hospital, documents);
                    insertPatient(newPrescriptionBean, null);
                } else {
                    final LoadingUtil loadingUtil = LoadingUtil.getInstance(this);
                    loadingUtil.show("保存成功");
                    uploadFile.startUpload(paths, new SuccessInterfaceM<String>() {
                        @Override
                        public void success(String response) {
                            List<NewDocumentDisplayBean> documents = newDocumentDisplayBeanBuilder.Build(response);
                            NewPrescriptionBean newPrescriptionBean = newPrescriptionBeanBuilder.Build(patientId, date, doctor, comment, departmentId, Hospital, documents);
                            insertPatient(newPrescriptionBean, loadingUtil);
                        }
                    });
                }
            }
        } else {
            prompt.deleteCharAt(prompt.length() - 1);
//            final NiftyDialogBuilder dialogBuilder   =  NiftyDialogBuilder.getInstance(AddPrescriptionActivity.this);
//            dialogBuilder.withTitle("错误提示").withDialogColor("#50d2c2").withButton1Text("确定").withMessage(""+prompt.toString()).setButton1Click(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialogBuilder.dismiss();
//                }
//            }).show();

            new SweetAlertDialog(AddPrescriptionActivity.this, SweetAlertDialog.ERROR_TYPE).setTitleText("错误！").setContentText(prompt.toString()).show();
        }
    }

    /**
     * 添加处方
     *
     * @param newPrescriptionBean
     */
    private void insertPatient(NewPrescriptionBean newPrescriptionBean, final LoadingUtil loadingUtil) {

        prescriptionService
                .getInsertPrescription()
                .InsertPrescription(newPrescriptionBean)
                .enqueue(new Callback<PrescriptionBean>() {
                    @Override
                    public void onResponse(Call<PrescriptionBean> call, Response<PrescriptionBean> response) {
                        if (response.isSuccessful()) {
                            setResult(PrescriptionActivity.Update);
                            loadingUtil.dismiss("保存成功", new LoadingUtil.Success() {
                                @Override
                                public void dismiss() {
                                    finishCur();
                                }
                            });
                        } else {
                            loadingUtil.dismiss("保存失败");
                        }
                    }

                    @Override
                    public void onFailure(Call<PrescriptionBean> call, Throwable t) {
                        loadingUtil.dismiss(LoadingStatu.NetError.value);
                        Bugtags.sendException(t);
                    }
                });
        loadingUtil.show("添加处方中");

    }


    //机构---------------------------------------------------------------------------------------------------------------

    public static final int EdithHospitalName = 10;

    //点击机构
    public void hospital() {
        Bundle data = new Bundle();
        data.putString("hospitalName", getTvValue(edtHospital));
        data.putInt("requestcode", EdithHospitalName);
        skipToForResult(HospitalNameActivity.class, data, EdithHospitalName);
    }

    private void setHospitalName(String HospitalName) {
        edtHospital.setText(HospitalName);
    }

    //科室-----------------------------------------------------------------------------------------------------------------
    private DepartmentDisplayBean select;


    //点击科室
    public void department() {
        skipToForResult(DepartmentLevel1Activity.class, null, DepartmentLevel1Activity.ToDepartmentLevel);
    }


    //修改处方界面图片无法放大的问题
    @OnItemClick(R.id.gvContent)
    public void picture() {
        GalleryUtil.toGallery(AddPrescriptionActivity.this, new GetUrlsInterface() {
            @Override
            public ArrayList<String> getUrls() {
                return paths;
            }
        });
    }

    //---------------------------------------------------------------------------------------------------------------------
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

    @Override
    //第一个参数为请求码，即调用startActivityForResult()传递过去的值
    //第二个参数为结果码，结果码用于标识返回数据来自哪个新Activity
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        pickImages.onRequestPermissionsResult(requestCode, permissions, grantResults);
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

}
