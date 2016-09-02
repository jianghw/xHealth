package com.kaurihealth.kaurihealth.common.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.commonlibrary.widget.util.DialogFactory;
import com.kaurihealth.utilslib.dialog.SuccessInterfaceM;
import com.kaurihealth.datalib.request_bean.bean.MedicalRecordType;
import com.kaurihealth.datalib.request_bean.bean.DepartmentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewLabTestDetailDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.PatientRecordDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.common.util.LabTestUtil;
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
import com.kaurihealth.kaurihealth.util.MedicalRecordFactory;
import com.kaurihealth.kaurihealth.util.MedicalRecordLabTest;
import com.kaurihealth.kaurihealth.util.TagsGridview;
import com.kaurihealth.kaurihealth.util.UploadFileUtil;
import com.kaurihealth.kaurihealth.util.Url;
import com.youyou.zllibrary.util.CommonActivity;

import java.text.ParseException;

import biz.kasual.materialnumberpicker.MaterialNumberPicker;
import butterknife.Bind;
import butterknife.ButterKnife;

public class EditLabtestActivity extends CommonActivity {
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
    @Bind(R.id.tv_labTest)
    TextView tvLabTest;
    private IGetter getter;
    private Bundle bundle;
    private IGetBundleHealthyRecord iGetBundleHealthyRecord;
    private MedicalRecordLabTest labTestController;
    private MedicalRecordPickImage pickImage;
    private UploadFileUtil uploadFile;
    private Dialog labtestDialog;
    private PatientRecordDisplayBean data;
    private IBundleFactory bundleFactory = new BundleFactory();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lab_test);
        ButterKnife.bind(this);
        init();
    }

    private IMedicalRecordUtil iMedicalRecordUtil = new MedicalRecordUtil();
    MedicalRecordType type = null;

    @Override
    public void init() {
        super.init();
        setBack(R.id.iv_back);
        getter = Getter.getInstance(getApplicationContext());
        bundle = getBundle();
        iGetBundleHealthyRecord = bundleFactory.getIGetBundleHealthyRecord(bundle);
        bundleFactory = new BundleFactory();
        data = (PatientRecordDisplayBean) getBundle().getSerializable("data");
        labTestController = MedicalRecordFactory.createLabEditor(data);
        uploadFile = new UploadFileUtil(getBundle().getString("kauriHealthId"), getter.getToken(), this, Url.AddImage);
        type = MedicalRecordType.LabTest;
        AbstractEditMedicaHistoryNew editMedicaHistory = new AbstractEditMedicaHistoryNew(getApplicationContext()) {
            private Dialog dateDialog;

            @Override
            public boolean isAbleEdit() {
                return (getter.getUserId() == labTestController.getCreateBy()) && iGetBundleHealthyRecord.isAble();
            }

            @Override
            public void commitDataSuccess(PatientRecordDisplayBean backData) {
                super.commitDataSuccess(backData);
                labTestController.setBean(backData);
                finishCur();
            }

            @Override
            public TextView getControlTv() {
                return tvOperate;
            }

            @Override
            public IMedicalRecordController getController() {
                return labTestController;
            }


            @Override
            public void initUiWhenUnAbleEdit() {
                super.initUiWhenUnAbleEdit();
                //设置顶部的  患者姓名   性别   年龄
                iMedicalRecordUtil.setTop(includelay, iGetBundleHealthyRecord.getName(), iGetBundleHealthyRecord.getGender(), iGetBundleHealthyRecord.getAge());
                tvCategory.setText(labTestController.getCategory());
                tvSubject.setText(labTestController.getSubject());
                if (!TextUtils.isEmpty(labTestController.getRecordDate())) {
                    try {
                        tvRecordDate.setText(format.format(format.parse(labTestController.getRecordDate())));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                edtDoctor.setText(labTestController.getDoctor());
                edtHospital.setText(labTestController.getHospital());
                edtRemark.setText(labTestController.getComment());
                edtDepartmentName.setText(labTestController.getDepartmentName());
                tvLabTest.setText(labTestController.getLabTestType());
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
                labtestDialog = getLabtestDialog(new SuccessInterfaceM<String>() {
                    @Override
                    public void success(String labTestType) {
                        labTestController.editLabTestType(labTestType);
                        tvLabTest.setText(labTestType);
                    }
                });
                tvLabTest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (labtestDialog.isShowing()) {
                            labtestDialog.dismiss();
                        } else {
                            labtestDialog.show();
                        }
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
                    if (labtestDialog.isShowing()) {
                        labtestDialog.dismiss();
                    }
                }

            }


            @Override
            public GridView getGridview() {
                return gvContent;
            }

            @Override
            public Activity getActivity() {
                return EditLabtestActivity.this;
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
                result = result && check("图片", imags) && check("检查分类", controller.getSubject());
                result = result && check("检查项目", data.labTest) && check("检查项目", data.labTest.labTestType);
                return result;
            }


            @Override
            public void commitDataToServer(MedicalRecordType type, PatientRecordDisplayBean bean) {
                IMedicalRecordController controller = getController();
                controller.editComment(edtRemark.getText().toString());
                controller.editDoctor(edtDoctor.getText().toString());
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
                    labTestController.editDepartment(bean);
                    edtDepartmentName.setText(bean.departmentName);
                }
                break;
            case AddPrescriptionActivity.EdithHospitalName:
                if (resultCode == RESULT_OK) {
                    String hospitalName = data.getStringExtra("hospitalName");
                    labTestController.editHospital(hospitalName);
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


    /**
     * 获得检查项目的弹出框
     */
    private Dialog getLabtestDialog(final SuccessInterfaceM<String> listener) {
        NewLabTestDetailDisplayBean[] labTestIteams = LabTestUtil.getNewLabTestDetailDisplayBean(labTestController.getCategory());
        final String[] labTestStr = new String[labTestIteams.length];
        for (int i = 0; i < labTestIteams.length; i++) {
            labTestStr[i] = labTestIteams[i].labTestType;
        }
        View departmentView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_number_picker, null);
        final MaterialNumberPicker numberpicker = (MaterialNumberPicker) departmentView.findViewById(R.id.number_picker);
        TextView tv_complete = (TextView) departmentView.findViewById(R.id.tv_complete);
        tv_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                labtestDialog.dismiss();
                int index = numberpicker.getValue();
                listener.success(labTestStr[index]);
            }
        });
        setNumberPicker(numberpicker, labTestStr);
        labtestDialog = DialogFactory.createDialog(departmentView, this, R.style.Dialog_Date);
        return labtestDialog;
    }

    private void setNumberPicker(MaterialNumberPicker numberpicker, String[] content) {
        numberpicker.setDisplayedValues(null);
        numberpicker.setMaxValue(content.length - 1);
        numberpicker.setMinValue(0);
        numberpicker.setValue(1);
        numberpicker.setDisplayedValues(content);
    }
}
