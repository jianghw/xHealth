package com.kaurihealth.kaurihealth.common.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaurihealth.utilslib.dialog.SuccessInterfaceM;
import com.kaurihealth.datalib.request_bean.bean.MedicalRecordType;
import com.kaurihealth.datalib.request_bean.bean.DepartmentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.PatientRecordDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.common.util.MedicalRecordUtil;
import com.kaurihealth.kaurihealth.common.util.RecordType;
import com.kaurihealth.kaurihealth.common.util.UtilEditMedicaHistoryNew;
import com.kaurihealth.kaurihealth.home.adapter.MedicalRecordPickImage;
import com.kaurihealth.kaurihealth.home.util.Abstract.AbstractEditMedicaHistoryNew;
import com.kaurihealth.kaurihealth.home.util.BundleFactory;
import com.kaurihealth.kaurihealth.home.util.Interface.IBundleFactory;
import com.kaurihealth.kaurihealth.home.util.Interface.IGetBundleHealthyRecord;
import com.kaurihealth.kaurihealth.home.util.Interface.IMedicalRecordUtil;
import com.kaurihealth.kaurihealth.mine.activity.HospitalNameActivity;
import com.kaurihealth.kaurihealth.patientwithdoctor.activity.DepartmentLevel1Activity;
import com.kaurihealth.kaurihealth.patientwithdoctor.activity.DepartmentLevel2Activity;
import com.kaurihealth.kaurihealth.util.Getter;
import com.kaurihealth.kaurihealth.util.Interface.IGetter;
import com.kaurihealth.kaurihealth.util.Interface.IMedicalRecordController;
import com.kaurihealth.kaurihealth.util.Interface.IPickImages;
import com.kaurihealth.kaurihealth.util.MedicalRecordFactory;
import com.kaurihealth.kaurihealth.util.TagsGridview;
import com.kaurihealth.kaurihealth.util.UploadFileUtil;
import com.kaurihealth.kaurihealth.util.Url;
import com.youyou.zllibrary.util.CommonActivity;

import java.text.ParseException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class EditCommonMedicalRecordActivityNew extends CommonActivity {
    @RecordType(cansetBackground = false)
    @Bind(R.id.tv_operate)
    TextView tvOperate;
    @RecordType(cansetBackground = false)
    @Bind(R.id.tv_category)
    TextView tvCategory;
    @RecordType(cansetBackground = false)
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
    @Bind(R.id.iv_uploadfile)
    ImageView ivUploadfile;
    @Bind(R.id.gvContent)
    TagsGridview gvContent;
    @Bind(R.id.edtRemark)
    EditText edtRemark;
    @Bind(R.id.includelay)
    View includelay;
    private IGetter getter;
    private Bundle bundle;
    private IGetBundleHealthyRecord iGetBundleHealthyRecord;
    private Resources resources;
    private String[] clinicalDiagnosis;
    private String[] supplementaryTest;
    private TextView[] tvs;
    private EditText[] edts;
    private ImageView[] ivs;
    private IMedicalRecordController pathologyController;
    private IPickImages pickImage;
    private UploadFileUtil uploadFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_common_medical_record);
        ButterKnife.bind(this);
        init();
    }

    private IMedicalRecordUtil iMedicalRecordUtil = new MedicalRecordUtil();
    MedicalRecordType type = null;

    @Override
    public void init() {
        super.init();
        setBack(R.id.iv_back);
        tvs = new TextView[]{tvCategory, tvSubject, tvRecordDate, edtDepartmentName, edtHospital};
        edts = new EditText[]{edtDoctor, edtRemark};
        ivs = new ImageView[]{ivUploadfile};
        getter = Getter.getInstance(getApplicationContext());
        bundle = getBundle();
        IBundleFactory bundleFactory = new BundleFactory();
        iGetBundleHealthyRecord = bundleFactory.getIGetBundleHealthyRecord(bundle);
        PatientRecordDisplayBean data = (PatientRecordDisplayBean) getBundle().getSerializable("data");
        pathologyController = MedicalRecordFactory.createPathology(data);
        iMedicalRecordUtil.setTop(includelay, iGetBundleHealthyRecord.getName(), iGetBundleHealthyRecord.getGender(), iGetBundleHealthyRecord.getAge());
        resources = getResources();
        uploadFile = new UploadFileUtil(getBundle().getString("kauriHealthId"), getter.getToken(), this, Url.AddImage);
        //临床诊疗
        String[] temp = resources.getStringArray(R.array.ClinicalDiagnosisAndTreatment);
        clinicalDiagnosis = new String[temp.length - 3];
        for (int i = 0; i < clinicalDiagnosis.length; i++) {
            clinicalDiagnosis[i] = temp[i + 3];
        }
        //辅助检查
        supplementaryTest = resources.getStringArray(R.array.SupplementaryTest);
        //实验室检查
        switch (data.category) {
            case "临床诊疗记录":
                type = MedicalRecordType.ClinicalDiagnosisAndTreatment;
                break;
            case "辅助检查":
                type = MedicalRecordType.SupplementaryTest;
                break;
            case "实验室检查":
                type = MedicalRecordType.LabTest;
                break;
            case "病理":
                type = MedicalRecordType.Pathology;
                break;
        }
        AbstractEditMedicaHistoryNew editMedicaHistory = new AbstractEditMedicaHistoryNew(getApplicationContext()) {
            private Dialog dateDialog;

            @Override
            public boolean isAbleEdit() {
                return (getter.getUserId() == pathologyController.getCreateBy()) && iGetBundleHealthyRecord.isAble();
            }

            @Override
            public void commitDataSuccess(PatientRecordDisplayBean backData) {
                super.commitDataSuccess(backData);
                pathologyController.setBean(backData);
                finishCur();
            }

            @Override
            public TextView getControlTv() {
                return tvOperate;
            }

            @Override
            public IMedicalRecordController getController() {
                return pathologyController;
            }


            @Override
            public void initUiWhenUnAbleEdit() {
                super.initUiWhenUnAbleEdit();
                IMedicalRecordController controller = getController();
                tvCategory.setText(controller.getCategory());
                tvSubject.setText(controller.getSubject());
                if (!TextUtils.isEmpty(controller.getRecordDate())) {
                    try {
                        tvRecordDate.setText(format.format(format.parse(controller.getRecordDate())));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                edtDoctor.setText(controller.getDoctor());
                edtHospital.setText(controller.getHospital());
                edtRemark.setText(controller.getComment());
                edtDepartmentName.setText(controller.getDepartmentName());
            }

            @Override
            public void initUiWhenIsAbleEdit() {
                final IMedicalRecordController controller = getController();
                super.initUiWhenIsAbleEdit();
                //门诊时间
                dateDialog = getTimeDialog(new SuccessInterfaceM<String>() {
                    @Override
                    public void success(String s) {
                        tvRecordDate.setText(s);
                    }
                });
                tvRecordDate.setOnClickListener(new View.OnClickListener()

                                                {
                                                    @Override
                                                    public void onClick(View v) {
                                                        dateDialog.show();
                                                    }
                                                }
                );
                //科室
                edtDepartmentName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        skipToForResult(DepartmentLevel1Activity.class, null, DepartmentLevel1Activity.ToDepartmentLevel);
                    }
                });
                //机构
                edtHospital.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle data = new Bundle();
                        data.putString("hospitalName", edtHospital.getText().toString());
                        data.putInt("requestcode", AddPrescriptionActivity.EdithHospitalName);
                        skipToForResult(HospitalNameActivity.class, data, AddPrescriptionActivity.EdithHospitalName);
                    }
                });
                pickImage = new MedicalRecordPickImage(getActivity(), imags, adapter, 100);
                ivUploadfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pickImage.pickImage();
                    }
                });
            }

            @Override
            public void setReadMode() {
                super.setReadMode();
                setAllEdtTvIv(false);
                if (isAbleEdit()) {
                    if (dateDialog != null) {
                        dateDialog.dismiss();
                    }
                }
            }

            @Override
            public void setEditMode() {
                super.setEditMode();
                setAllEdtTvIv(true);
            }

            @Override
            public GridView getGridview() {
                return gvContent;
            }

            @Override
            public Activity getActivity() {
                return EditCommonMedicalRecordActivityNew.this;
            }

            @Override
            public MedicalRecordType getType() {
                return type;
            }

            @Override
            public UploadFileUtil getUploadFile() {
                return uploadFile;
            }

            @Override
            public boolean checkDataComplete() {
                IMedicalRecordController controller = getController();
                boolean result = super.checkDataComplete();
                result = result && check("项目", controller.getSubject()) && check("门诊时间", controller.getRecordDate()) && check("图片", imags);
                return result;
            }

            @Override
            public void commitDataToServer(MedicalRecordType type, PatientRecordDisplayBean bean) {
                IMedicalRecordController controller = getController();
                controller.editComment(edtRemark.getText().toString());
                controller.editDoctor(edtDoctor.getText().toString());
                super.commitDataToServer(type, bean);
            }

            private void setAllEdtTvIv(boolean clickable) {
                for (int i = 0; i < tvs.length; i++) {
                    tvs[i].setClickable(clickable);
                }
                for (int i = 0; i < edts.length; i++) {
                    edts[i].setEnabled(clickable);
                }
                for (int i = 0; i < ivs.length; i++) {
                    ivs[i].setClickable(clickable);
                }
            }

        };
        UtilEditMedicaHistoryNew utilEditMedicaHistoryNew = new UtilEditMedicaHistoryNew(editMedicaHistory);
        utilEditMedicaHistoryNew.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        pickImage.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case DepartmentLevel1Activity.ToDepartmentLevel:
                if (resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    DepartmentDisplayBean bean = (DepartmentDisplayBean) extras.getSerializable(DepartmentLevel2Activity.DepartmentLevel2ActivityKey);
                    pathologyController.editDepartment(bean);
                    edtDepartmentName.setText(bean.departmentName);
                }
                break;
            case AddPrescriptionActivity.EdithHospitalName:
                if (resultCode == RESULT_OK) {
                    String hospitalName = data.getStringExtra("hospitalName");
                    pathologyController.editHospital(hospitalName);
                    edtHospital.setText(hospitalName);
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        pickImage.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
