package com.kaurihealth.kaurihealth.common.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
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
import com.kaurihealth.kaurihealth.util.Abstract.AbsMedicalRecordRemoteConsult;
import com.kaurihealth.kaurihealth.util.Getter;
import com.kaurihealth.kaurihealth.util.Interface.IGetter;
import com.kaurihealth.kaurihealth.util.Interface.IMedicalRecordController;
import com.kaurihealth.kaurihealth.util.MedicalRecordFactory;
import com.kaurihealth.kaurihealth.util.NoValueException;
import com.kaurihealth.kaurihealth.util.TagsGridview;
import com.kaurihealth.kaurihealth.util.UploadFileUtil;
import com.kaurihealth.kaurihealth.util.Url;
import com.rey.material.widget.CheckBox;
import com.youyou.zllibrary.util.CommonActivity;

import java.text.ParseException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class EditRemoteActivityNew extends CommonActivity {
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
    @RecordType(cansetBackground = false)
    @Bind(R.id.edtDoctor)
    EditText edtDoctor;
    @Bind(R.id.edtPresentIllness)
    EditText edtPresentIllness;
    @Bind(R.id.edtMajorIssue)
    EditText edtMajorIssue;
    @Bind(R.id.edtAccetedTreatment)
    EditText edtAccetedTreatment;
    @RecordType(cansetBackground = false)
    @Bind(R.id.checkboxReasonHasRead)
    CheckBox checkboxReasonHasRead;
    @Bind(R.id.edtComment)
    EditText edtComment;
    @Bind(R.id.iv_uploadfile)
    ImageView ivUploadfile;
    @Bind(R.id.gvContent)
    TagsGridview gvContent;
    @Bind(R.id.edtRemark)
    EditText edtRemark;
    @Bind(R.id.tv_delete)
    TextView tvDelete;
    @Bind(R.id.includelay)
    View includelay;
    private IGetter getter;
    private Bundle bundle;
    private IGetBundleHealthyRecord iGetBundleHealthyRecord;
    private AbsMedicalRecordRemoteConsult remoteController;
    private MedicalRecordPickImage pickImage;
    private UploadFileUtil uploadFile;
    private PatientRecordDisplayBean data;
    private IBundleFactory bundleFactory = new BundleFactory();
    MedicalRecordType type = MedicalRecordType.ClinicalDiagnosisAndTreatment;

    private IMedicalRecordUtil iMedicalRecordUtil = new MedicalRecordUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_remote_consultation_record);
        ButterKnife.bind(this);
        init();
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
    public void init() {
        super.init();
        getter = Getter.getInstance(getApplicationContext());
        bundle = getBundle();
        iGetBundleHealthyRecord = bundleFactory.getIGetBundleHealthyRecord(bundle);
        data = (PatientRecordDisplayBean) getBundle().getSerializable("data");
        remoteController = MedicalRecordFactory.createRemoteEditor(data);
        uploadFile = new UploadFileUtil(getBundle().getString("kauriHealthId"), getter.getToken(), this, Url.AddImage);
        AbstractEditMedicaHistoryNew editMedicaHistory = new AbstractEditMedicaHistoryNew(getApplicationContext()) {
            private Dialog dateDialog;

            @Override
            public boolean isAbleEdit() {
                return (getter.getUserId() == remoteController.getCreateBy()) && iGetBundleHealthyRecord.isAble();
            }

            @Override
            public void initUiWhenUnAbleEdit() {
                super.initUiWhenUnAbleEdit();
                setBack(R.id.iv_back);
                tvDelete.setVisibility(View.GONE);
                //设置顶部的  患者姓名   性别   年龄

                iMedicalRecordUtil.setTop(includelay, iGetBundleHealthyRecord.getName(), iGetBundleHealthyRecord.getGender(), iGetBundleHealthyRecord.getAge());
                //类型
                tvCategory.setText(remoteController.getCategory());
                //项目
                tvSubject.setText(remoteController.getSubject());
                //门诊时间
                if (!TextUtils.isEmpty(remoteController.getRecordDate())) {
                    try {
                        tvRecordDate.setText(format.format(format.parse(remoteController.getRecordDate())));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                //科室
                edtDepartmentName.setText(remoteController.getDepartmentName());
                //机构
                edtHospital.setText(remoteController.getHospital());
                //医师
                edtDoctor.setText(remoteController.getDoctor());
                try {
                    //主诉/现病史
                    edtPresentIllness.setText(remoteController.getPresentIllness());
                } catch (NoValueException e) {
                    e.printStackTrace();
                }
                //目前主要问题
                try {
                    edtMajorIssue.setText(remoteController.getCurrentHealthIssues());
                } catch (NoValueException e) {
                    e.printStackTrace();
                }
                //目前所接受治疗
                try {
                    edtAccetedTreatment.setText(remoteController.getCurrentTreatment());
                } catch (NoValueException e) {
                    e.printStackTrace();
                }
                //患者记录已阅
                try {
                    checkboxReasonHasRead.setChecked(remoteController.getIsPatientHealthRecordReviewed());
                } catch (NoValueException e) {
                    e.printStackTrace();
                }
                //印象/咨询建议
                try {
                    edtComment.setText(remoteController.getConsultComment());
                } catch (NoValueException e) {
                    e.printStackTrace();
                }
                //备注
                edtRemark.setText(remoteController.getComment());
            }

            @Override
            public void commitDataSuccess(PatientRecordDisplayBean backData) {
                super.commitDataSuccess(backData);
                remoteController.setBean(backData);
                finishCur();
            }

            @Override
            public TextView getControlTv() {
                return tvOperate;
            }


            @Override
            public IMedicalRecordController getController() {
                return remoteController;
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
                        bundle.putString("hospitalName", edtHospital.getText().toString());
                        bundle.putInt("requestcode", AddPrescriptionActivity.EdithHospitalName);
                        skipToForResult(HospitalNameActivity.class, bundle, AddPrescriptionActivity.EdithHospitalName);
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
                if (isAbleEdit()) {
                    if (dateDialog != null) {
                        dateDialog.dismiss();
                    }
                }
            }

            @Override
            public void setEditMode() {
                super.setEditMode();
                setEdtEditable(edtDoctor, false);
            }

            @Override
            public GridView getGridview() {
                return gvContent;
            }

            @Override
            public Activity getActivity() {
                return EditRemoteActivityNew.this;
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
                result = result && check("项目", controller.getSubject()) &&
                        check("门诊时间", remoteController.getRecordDate()) &&
                        check("科室", remoteController.getDepartmentName()) &&
                        check("医师", getTvValue(edtDoctor)) &&
                        check("主诉/现病史", getTvValue(edtPresentIllness)) &&
                        check("目前主要问题", getTvValue(edtMajorIssue)) &&
                        check("目前所接受治疗", getTvValue(edtAccetedTreatment));
                return result;
            }

            @Override
            public void commitDataToServer(MedicalRecordType type, PatientRecordDisplayBean bean) {
                //医生
                remoteController.editDoctor(getTvValue(edtDoctor));
                //主诉/现病史
                remoteController.editPresentIllness(getTvValue(edtPresentIllness));
                //目前主要问题
                remoteController.editCurrentHealthIssues(getTvValue(edtMajorIssue));
                //目前所接受治疗
                remoteController.editCurrentTreatment(getTvValue(edtAccetedTreatment));
                //患者医疗记录已阅
                remoteController.editIsPatientHealthRecordReviewed(checkboxReasonHasRead.isChecked());
                //印象/咨询建议
                remoteController.editConsultComment(getTvValue(edtComment));
                //备注
                remoteController.editComment(getTvValue(edtRemark));
                super.commitDataToServer(type, bean);
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
                    remoteController.editDepartment(bean);
                    edtDepartmentName.setText(bean.departmentName);
                }
                break;
            case AddPrescriptionActivity.EdithHospitalName:
                if (resultCode == RESULT_OK) {
                    String hospitalName = data.getStringExtra("hospitalName");
                    remoteController.editHospital(hospitalName);
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


}
