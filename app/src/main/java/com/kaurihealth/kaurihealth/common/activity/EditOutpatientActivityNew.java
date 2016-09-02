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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaurihealth.utilslib.dialog.SuccessInterfaceM;
import com.kaurihealth.datalib.request_bean.bean.MedicalRecordType;
import com.kaurihealth.datalib.request_bean.bean.DepartmentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.PatientRecordDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.clinical.util.ClinicalUtil;
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
import com.kaurihealth.kaurihealth.util.Abstract.AbsMedicalRecordOutpatient;
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

public class EditOutpatientActivityNew extends CommonActivity {
    @RecordType(cansetBackground = false)
    @Bind(R.id.tv_operate)
    TextView tvOperate;
    @RecordType(cansetBackground = false)
    @Bind(R.id.tv_type)
    TextView tvType;
    @RecordType(cansetBackground = false)
    @Bind(R.id.tv_subject)
    TextView tvSubject;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.edt_office)
    TextView edtOffice;
    @Bind(R.id.edt_organization)
    TextView edtOrganization;
    @RecordType(cansetBackground = false)
    @Bind(R.id.edt_doctor)
    EditText edtDoctor;
    @Bind(R.id.edt_history)
    EditText edtHistory;
    @RecordType(cansetBackground = false)
    @Bind(R.id.checkbox_hasread_history)
    CheckBox checkboxHasreadHistory;
    @RecordType(cansetBackground = false)
    @Bind(R.id.checkbox_hasupdate_history)
    CheckBox checkboxHasupdateHistory;
    @RecordType(cansetBackground = false)
    @Bind(R.id.checkbox_hasread_current)
    CheckBox checkboxHasreadCurrent;
    @RecordType(cansetBackground = false)
    @Bind(R.id.checkbox_hasupdate_current)
    CheckBox checkboxHasupdateCurrent;
    @Bind(R.id.edt_hr)
    EditText edtHr;
    @Bind(R.id.edt_rr)
    EditText edtRr;
    @Bind(R.id.edt_upperbp)
    EditText edtUpperbp;
    @Bind(R.id.edt_t)
    EditText edtT;
    @Bind(R.id.edt_lowerbp)
    EditText edtLowerbp;
    @Bind(R.id.edt_checkinfo)
    EditText edtCheckinfo;
    @RecordType(cansetBackground = false)
    @Bind(R.id.checkMedicalRecordHasreadSupplyTest_)
    CheckBox checkMedicalRecordHasreadSupplyTest;
    @RecordType(cansetBackground = false)
    @Bind(R.id.checkLongTestHasreadSupplyTest)
    CheckBox checkLongTestHasreadSupplyTest;
    @Bind(R.id.edtSupplyTest)
    EditText edtSupplyTest;
    @Bind(R.id.edt_plan)
    EditText edtPlan;
    @Bind(R.id.iv_uploadfile)
    ImageView ivUploadfile;
    @Bind(R.id.gvContent)
    TagsGridview gvContent;
    @Bind(R.id.lyouimage)
    LinearLayout lyouimage;
    @Bind(R.id.edt_remark)
    EditText edtRemark;
    @Bind(R.id.tv_delete)
    TextView tvDelete;
    @Bind(R.id.includelay)
    View includelay;
    private IGetter getter;
    private Bundle bundle;
    private IGetBundleHealthyRecord iGetBundleHealthyRecord;
    private AbsMedicalRecordOutpatient outpatientController;
    private MedicalRecordPickImage pickImage;
    private UploadFileUtil uploadFile;
    private PatientRecordDisplayBean data;
    private ClinicalUtil clinicalUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.outpatientrecord_medicalhistory);
        setBack(R.id.iv_back);
        ButterKnife.bind(this);
        init();
    }

    private IMedicalRecordUtil iMedicalRecordUtil = new MedicalRecordUtil();
    MedicalRecordType type = MedicalRecordType.ClinicalDiagnosisAndTreatment;

    @Override
    public void init() {
        super.init();
        getter = Getter.getInstance(getApplicationContext());
        bundle = getBundle();
        clinicalUtil = new ClinicalUtil();
        IBundleFactory bundleFactory = new BundleFactory();
        iGetBundleHealthyRecord = bundleFactory.getIGetBundleHealthyRecord(bundle);
        data = (PatientRecordDisplayBean) getBundle().getSerializable("data");
        outpatientController = MedicalRecordFactory.createOutpatientEditor(data);
        iMedicalRecordUtil.setTop(includelay, iGetBundleHealthyRecord.getName(), iGetBundleHealthyRecord.getGender(), iGetBundleHealthyRecord.getAge());
        uploadFile = new UploadFileUtil(getBundle().getString("kauriHealthId"), getter.getToken(), this, Url.AddImage);
        AbstractEditMedicaHistoryNew editMedicaHistory = new AbstractEditMedicaHistoryNew(getApplicationContext()) {
            private Dialog dateDialog;

            @Override
            public boolean isAbleEdit() {
                return (getter.getUserId() == outpatientController.getCreateBy()) && iGetBundleHealthyRecord.isAble();
            }

            @Override
            public void commitDataSuccess(PatientRecordDisplayBean backData) {
                super.commitDataSuccess(backData);
                outpatientController.setBean(backData);
                finishCur();
            }

            @Override
            public TextView getControlTv() {
                return tvOperate;
            }

            @Override
            public IMedicalRecordController getController() {
                return outpatientController;
            }

            @Override
            public void initUiWhenUnAbleEdit() {
                super.initUiWhenUnAbleEdit();
                tvDelete.setVisibility(View.GONE);
                setBack(R.id.iv_back);
                tvType.setText(outpatientController.getCategory());
                tvSubject.setText(outpatientController.getSubject());
                if (!TextUtils.isEmpty(outpatientController.getRecordDate())) {
                    try {
                        tvTime.setText(format.format(format.parse(outpatientController.getRecordDate())));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                edtDoctor.setText(outpatientController.getDoctor());
                edtOrganization.setText(outpatientController.getHospital());
                edtRemark.setText(outpatientController.getComment());
                edtOffice.setText(outpatientController.getDepartmentName());
                try {
                    //主诉/现病史
                    edtHistory.setText(outpatientController.getPresentIllness());
                } catch (NoValueException e) {
                    e.printStackTrace();
                }
                //既往病史   已阅
                try {
                    checkboxHasreadHistory.setChecked(outpatientController.getPastIllnessReviewed());
                } catch (NoValueException e) {
                    e.printStackTrace();
                }
                //既往病史   已更新
                try {
                    checkboxHasupdateHistory.setChecked(outpatientController.getPastIllnessUpToDate());
                } catch (NoValueException e) {
                    e.printStackTrace();
                }
                //目前使用药物   已阅
                try {
                    checkboxHasreadCurrent.setChecked(outpatientController.getCurrentMedicationReviewed());
                } catch (NoValueException e) {
                    e.printStackTrace();
                }
                //目前使用药物   已更新
                try {
                    checkboxHasupdateCurrent.setChecked(outpatientController.getCurrentMedicationUpToDate());
                } catch (NoValueException e) {
                    e.printStackTrace();
                }
                //HR
                try {
                    edtHr.setText(clinicalUtil.getDoubleIsString(outpatientController.getHr()));
                } catch (NoValueException e) {
                    e.printStackTrace();
                }
                //RR
                try {
                    edtRr.setText(clinicalUtil.getDoubleIsString(outpatientController.getRr()));
                } catch (NoValueException e) {
                    e.printStackTrace();
                }
                //upperBp
                try {
                    edtUpperbp.setText(clinicalUtil.getDoubleIsString(outpatientController.getUpperBp()));
                } catch (NoValueException e) {
                    e.printStackTrace();
                }
                //T
                try {
                    edtT.setText(clinicalUtil.getDoubleIsString(outpatientController.getT()));
                } catch (NoValueException e) {
                    e.printStackTrace();
                }
                //lowerBp
                try {
                    edtLowerbp.setText(clinicalUtil.getDoubleIsString(outpatientController.getLowerBp()));
                } catch (NoValueException e) {
                    e.printStackTrace();
                }
                //体格检查描述
                try {
                    edtCheckinfo.setText(outpatientController.getPhysicalExamination());
                } catch (NoValueException e) {
                    e.printStackTrace();
                }
                //印象/计划
                try {
                    edtPlan.setText(outpatientController.getConsultComment());
                } catch (NoValueException e) {
                    e.printStackTrace();
                }
                //辅助检查文本
                try {
                    edtSupplyTest.setText(outpatientController.getWorkupReviewComment());
                } catch (NoValueException e) {
                    e.printStackTrace();
                }
                //患者系统医疗记录辅助检查已阅
                try {
                    checkMedicalRecordHasreadSupplyTest.setChecked(outpatientController.getIsWorkupReviewed());
                } catch (NoValueException e) {
                    e.printStackTrace();
                }
                //患者长期监测指标已阅
                try {
                    checkLongTestHasreadSupplyTest.setChecked(outpatientController.getIsLongTermMonitoringDataReviewed());
                } catch (NoValueException e) {
                    e.printStackTrace();
                }
                //备注
                edtRemark.setText(outpatientController.getComment());
            }

            @Override
            public void initUiWhenIsAbleEdit() {
                super.initUiWhenIsAbleEdit();
                //门诊时间

                dateDialog = getTimeDialog(new SuccessInterfaceM<String>() {
                    @Override
                    public void success(String time) {
                        tvTime.setText(time);
                    }
                });
                tvTime.setOnClickListener(new View.OnClickListener()

                                          {
                                              @Override
                                              public void onClick(View v) {
                                                  dateDialog.show();
                                              }
                                          }
                );
                //科室
                edtOffice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        skipToForResult(DepartmentLevel1Activity.class, null, DepartmentLevel1Activity.ToDepartmentLevel);
                    }
                });
                //机构
                edtOrganization.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle data = new Bundle();
                        data.putString("hospitalName", edtOrganization.getText().toString());
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
                return EditOutpatientActivityNew.this;
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
                        check("主诉/现病史", getTvValue(edtHistory)) &&
                        check("印象计划", getTvValue(edtPlan)) && check("辅助检查", getTvValue(edtSupplyTest));
                return result;
            }


            @Override
            public void commitDataToServer(MedicalRecordType type, PatientRecordDisplayBean bean) {
                //医生姓名
                outpatientController.editDoctor(getTvValue(edtDoctor));
                //主诉/现病史
                outpatientController.editPresentIllness(getTvValue(edtHistory));
                //既往病史   已阅
                outpatientController.editPastIllnessReviewed(checkboxHasreadHistory.isChecked());
                //既往病史   已更新
                outpatientController.editPastIllnessUpToDate(checkboxHasupdateHistory.isChecked());
                //目前使用药物   已阅
                outpatientController.editCurrentMedicationReviewed(checkboxHasreadCurrent.isChecked());
                //目前使用药物   已更新
                outpatientController.editCurrentMedicationUpToDate(checkboxHasupdateCurrent.isChecked());
                //HR
                outpatientController.editHr(getDoubleValueOfTv(edtHr));
                //RR
                outpatientController.editRr(getDoubleValueOfTv(edtRr));
                //upperBp
                outpatientController.editUpperBp(getDoubleValueOfTv(edtUpperbp));
                //T
                outpatientController.editT(getDoubleValueOfTv(edtT));
                //lowerBp
                outpatientController.editLowerBp(getDoubleValueOfTv(edtLowerbp));
                //体格检查描述
                outpatientController.editPhysicalExamination(getTvValue(edtCheckinfo));
                //印象/计划
                outpatientController.editConsultComment(getTvValue(edtPlan));
                //辅助检查
                outpatientController.editWorkupReviewComment(getTvValue(edtSupplyTest));
                outpatientController.editIsWorkupReviewed(checkMedicalRecordHasreadSupplyTest.isChecked());
                outpatientController.editIsLongTermMonitoringDataReviewed(checkLongTestHasreadSupplyTest.isChecked());
                //备注
                outpatientController.editComment(getTvValue(edtRemark));
                super.commitDataToServer(type, bean);
            }

        };
        UtilEditMedicaHistoryNew utilEditMedicaHistoryNew = new UtilEditMedicaHistoryNew(editMedicaHistory);
        utilEditMedicaHistoryNew.start();
    }

    //提交检查获取数据
    private double getDoubleValueOfTv(TextView tvShow) {
        double result = 0;
        String tvValue = getTvValue(tvShow);
        if (!TextUtils.isEmpty(tvValue)) {
            result = Double.parseDouble(tvValue);
        }
        return result;
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
                    outpatientController.editDepartment(bean);
                    edtOffice.setText(bean.departmentName);
                }
                break;
            case AddPrescriptionActivity.EdithHospitalName:
                if (resultCode == RESULT_OK) {
                    String hospitalName = data.getStringExtra("hospitalName");
                    outpatientController.editHospital(hospitalName);
                    edtOrganization.setText(hospitalName);
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
