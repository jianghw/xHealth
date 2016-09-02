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
import com.kaurihealth.kaurihealth.util.Abstract.AbsMedicalRecordOnlineConsult;
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
import com.youyou.zllibrary.util.RadioCheckboxForMaterial;

import java.text.ParseException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class EditOnlineConsultActivityNew extends CommonActivity {
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
    @RecordType(cansetBackground = false)
    @Bind(R.id.checkboxReasonHasUpdate)
    CheckBox checkboxReasonHasUpdate;
    @RecordType(cansetBackground = false)
    @Bind(R.id.checkboxReasonConsultAgain)
    CheckBox checkboxReasonConsultAgain;
    @RecordType(cansetBackground = false)
    @Bind(R.id.checkboxReasonOther)
    CheckBox checkboxReasonOther;
    @Bind(R.id.edtPresentIllness)
    EditText edtPresentIllness;
    @Bind(R.id.edtMajorIssue)
    EditText edtMajorIssue;
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
    private AbsMedicalRecordOnlineConsult onlineConsultController;
    private MedicalRecordPickImage pickImage;
    private UploadFileUtil uploadFile;
    private PatientRecordDisplayBean data;
    private IBundleFactory bundleFactory = new BundleFactory();
    private RadioCheckboxForMaterial raCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_online_consultation_record);
        ButterKnife.bind(this);
        init();
    }

    private IMedicalRecordUtil iMedicalRecordUtil = new MedicalRecordUtil();
    MedicalRecordType type = MedicalRecordType.ClinicalDiagnosisAndTreatment;

    @Override
    public void init() {
        super.init();
        setBack(R.id.iv_back);
        tvDelete.setVisibility(View.GONE);
        getter = Getter.getInstance(getApplicationContext());
        bundle = getBundle();
        iGetBundleHealthyRecord = bundleFactory.getIGetBundleHealthyRecord(bundle);
        bundleFactory = new BundleFactory();
        data = (PatientRecordDisplayBean) getBundle().getSerializable("data");
        onlineConsultController = MedicalRecordFactory.createOnlineEditor(data);
        uploadFile = new UploadFileUtil(getBundle().getString("kauriHealthId"), getter.getToken(), this, Url.AddImage);
        raCheckbox = new RadioCheckboxForMaterial();
        raCheckbox.regist(0, checkboxReasonHasUpdate);
        raCheckbox.regist(1, checkboxReasonConsultAgain);
        raCheckbox.regist(2, checkboxReasonOther);
        raCheckbox.setListener();
        AbstractEditMedicaHistoryNew editMedicaHistory = new AbstractEditMedicaHistoryNew(getApplicationContext()) {
            private Dialog dateDialog;

            @Override
            public boolean isAbleEdit() {
                return (getter.getUserId() == onlineConsultController.getCreateBy()) && iGetBundleHealthyRecord.isAble();
            }

            @Override
            public void commitDataSuccess(PatientRecordDisplayBean backData) {
                super.commitDataSuccess(backData);
                onlineConsultController.setBean(backData);
                finishCur();
            }

            @Override
            public TextView getControlTv() {
                return tvOperate;
            }

            @Override
            public IMedicalRecordController getController() {
                return onlineConsultController;
            }


            @Override
            public void initUiWhenUnAbleEdit() {
                super.initUiWhenUnAbleEdit();
                //设置顶部的  患者姓名   性别   年龄

                iMedicalRecordUtil.setTop(includelay, iGetBundleHealthyRecord.getName(), iGetBundleHealthyRecord.getGender(), iGetBundleHealthyRecord.getAge());
                //类型
                tvCategory.setText(onlineConsultController.getCategory());
                //项目
                tvSubject.setText(onlineConsultController.getSubject());
                //门诊时间
                if (!TextUtils.isEmpty(onlineConsultController.getRecordDate())) {
                    try {
                        tvRecordDate.setText(format.format(format.parse(onlineConsultController.getRecordDate())));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                //科室
                edtDepartmentName.setText(onlineConsultController.getDepartmentName());
                //机构
                edtHospital.setText(onlineConsultController.getHospital());
                //医师
                edtDoctor.setText(onlineConsultController.getDoctor());
                //网络咨询原因
                try {
                    switch (onlineConsultController.getOnlineConsultReason()) {
                        case "门诊辅助检查结果与治疗计划更新":
                            raCheckbox.select(0);
                            break;
                        case "日常复诊":
                            raCheckbox.select(1);
                            break;
                        case "其它健康问题":
                            raCheckbox.select(2);
                            break;

                    }
                } catch (NoValueException e) {
                    e.printStackTrace();
                }
                //主诉/现病史
                try {
                    edtPresentIllness.setText(onlineConsultController.getOnlineConsultReason());
                } catch (NoValueException e) {
                    e.printStackTrace();
                }
                //目前主要问题
                try {
                    edtMajorIssue.setText(onlineConsultController.getCurMajorIssue());
                } catch (NoValueException e) {
                    e.printStackTrace();
                }
                //印象/咨询建议
                try {
                    edtComment.setText(onlineConsultController.getConsultComment());
                } catch (NoValueException e) {
                    e.printStackTrace();
                }
                //备注
                edtRemark.setText(onlineConsultController.getComment());

            }

            @Override
            public void initUiWhenIsAbleEdit() {
                super.initUiWhenIsAbleEdit();
                final IMedicalRecordController controller = getController();
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
                if (isAbleEdit()) {
                    if (dateDialog != null) {
                        dateDialog.dismiss();
                    }
                }

            }


            @Override
            public GridView getGridview() {
                return gvContent;
            }

            @Override
            public Activity getActivity() {
                return EditOnlineConsultActivityNew.this;
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
                        check("门诊时间", controller.getRecordDate())
                        && check("主诉/现病史", getTvValue(edtPresentIllness))
                        && check("目前主要问题", getTvValue(edtMajorIssue))
                        && check("印象/咨询建议", getTvValue(edtComment));
                if (raCheckbox.getSelectedIndx() == -1) {
                    showToast("网络医疗咨询原因尚未填写");
                    result = result && false;
                } else {
                    result = result && true;
                }
                return result;
            }


            @Override
            public void commitDataToServer(MedicalRecordType type, PatientRecordDisplayBean bean) {
                IMedicalRecordController controller = getController();
                controller.editDoctor(getTvValue(edtDoctor));
                //网络咨询原因
                String reason = null;
                switch (raCheckbox.getSelectedIndx()) {
                    case 0:
                        reason = "门诊辅助检查结果与治疗计划更新";
                        break;
                    case 1:
                        reason = "日常复诊";
                        break;
                    case 2:
                        reason = "其它健康问题";
                        break;
                }
                onlineConsultController.editOnlineConsultReason(reason);
                //主诉/现病史
                onlineConsultController.editPresentIllness(getTvValue(edtPresentIllness));
                //目前主要问题
                onlineConsultController.editCurMajorIssue(getTvValue(edtMajorIssue));
                //印象/咨询建议
                onlineConsultController.editConsultComment(getTvValue(edtComment));
                controller.editComment(getTvValue(edtRemark));
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
                    onlineConsultController.editDepartment(bean);
                    edtDepartmentName.setText(bean.departmentName);
                }
                break;
            case AddPrescriptionActivity.EdithHospitalName:
                if (resultCode == RESULT_OK) {
                    String hospitalName = data.getStringExtra("hospitalName");
                    onlineConsultController.editHospital(hospitalName);
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
