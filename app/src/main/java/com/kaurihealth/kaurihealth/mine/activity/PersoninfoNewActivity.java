package com.kaurihealth.kaurihealth.mine.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bugtags.library.Bugtags;
import com.example.commonlibrary.widget.CircleImageView;
import com.example.commonlibrary.widget.util.ErrorInterfaceM;
import com.example.commonlibrary.widget.util.LoadingUtil;
import com.example.commonlibrary.widget.util.LogFactory;
import com.example.commonlibrary.widget.util.LogUtilInterface;
import com.example.commonlibrary.widget.util.SuccessInterface;
import com.kaurihealth.datalib.request_bean.bean.DepartmentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.ImageBean;
import com.kaurihealth.datalib.request_bean.bean.PersonInfoBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;
import com.kaurihealth.datalib.service.IDoctorService;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.mine.util.PersonInfoUtil;
import com.kaurihealth.kaurihealth.mine.util.PersonUtilNew;
import com.kaurihealth.kaurihealth.patientwithdoctor.activity.DepartmentLevel1Activity;
import com.kaurihealth.kaurihealth.patientwithdoctor.activity.DepartmentLevel2Activity;
import com.kaurihealth.kaurihealth.util.Getter;
import com.kaurihealth.kaurihealth.util.Interface.IGetter;
import com.kaurihealth.kaurihealth.util.Interface.IPickImages;
import com.kaurihealth.kaurihealth.util.Interface.IPutter;
import com.kaurihealth.kaurihealth.util.Interface.IUploadFile;
import com.kaurihealth.kaurihealth.util.PicassoImageLoadUtil;
import com.kaurihealth.kaurihealth.util.PickImage;
import com.kaurihealth.kaurihealth.util.Putter;
import com.kaurihealth.kaurihealth.util.UploadFileUtil;
import com.kaurihealth.kaurihealth.util.Url;
import com.kaurihealth.utilslib.dialog.PopUpNumberPickerDialog;
import com.kaurihealth.utilslib.dialog.SuccessInterfaceM;
import com.squareup.picasso.Picasso;
import com.youyou.zllibrary.util.CommonActivity;
import com.youyou.zllibrary.util.Utils;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;

import biz.kasual.materialnumberpicker.MaterialNumberPicker;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 张磊 on 2016/5/18.
 * 介绍：
 */
public class PersoninfoNewActivity extends CommonActivity {
    public static final int PersoninfoNewActivityCode = 11;
    //个人照片
    @Bind(R.id.photo_personinfo)
    CircleImageView ivPhoto;
    //个人姓名
    @Bind(R.id.name_personinfo)
    TextView tvName;
    //性别
    @Bind(R.id.gender_personinfo)
    TextView tvGendar;
    //年龄
    @Bind(R.id.age_personinfo)
    TextView tvAge;
    //职称
    @Bind(R.id.job_personinfo)
    TextView tvJob;
    //科室
    @Bind(R.id.department_personinfo)
    TextView tvDepartmentName;
    //执业编号
    @Bind(R.id.num_personinfo)
    TextView tvNum;
    //学历
    @Bind(R.id.tv_educationLevel)
    TextView tv_educationLevel;
    //机构名称
    @Bind(R.id.organization_name_personinfo)
    TextView tvOrganization;
    //学习与工作经历
    @Bind(R.id.study_experience_personinfo)
    TextView tvStudyExperience;
    //专业特长
    @Bind(R.id.goodat_personinfo)
    TextView tvPracticeField;
    //毕业院校
    @Bind(R.id.tvEducationHistory)
    TextView tvEducationHistory;

    private DepartmentDisplayBean[] departmentBean;
    private PersonInfoBean personInfo;
    private PersonInfoBean resPersoninfo;
    private String[] educationLevel;
    private PopUpNumberPickerDialog educationLevelDialogPopUp;
    private Dialog educationLevelDialog;
    private IGetter getter;
    private IPickImages pickImages;
    private PersonInfoUtil personInfoUtil;
    LogUtilInterface logutil = LogFactory.getSimpleLog(getClass().getName());
    ArrayList<String> paths = new ArrayList<>();
    private IPutter putter;

    private IUploadFile<String> uploadFile;
    private IDoctorService doctorService;
    private PersonInfoBean personInfoBean;
    private LoadingUtil loadingUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);
        ButterKnife.bind(this);
        init();
    }

    LogUtilInterface logUtil = LogFactory.getSimpleLog(getClass().getName());

    @Override
    public void init() {
        super.init();
        loadingUtil = LoadingUtil.getInstance(PersoninfoNewActivity.this);
        putter = new Putter(getApplicationContext());
        getter = Getter.getInstance(getApplicationContext());

        personInfoUtil = new PersonInfoUtil();
        uploadFile = new UploadFileUtil(getter.getKauriHealthId(), getter.getToken(), this, Url.AddImage);
        pickImages = new PickImage(paths, this, new SuccessInterface() {
            @Override
            public void success() {
                loadingUtil.show();
                loadingUtil.dismiss("修改成功", new LoadingUtil.Success() {
                    @Override
                    public void dismiss() {
                        String path = paths.get(0);
                        PicassoImageLoadUtil.loadUrlToImageView(new File(path), ivPhoto, getApplicationContext());
                    }
                });

            }
        }, 1);
        //初始化学历
        neweducationLevelDialog();
        logutil.i("初始化了");
        //获取到科室对象对象
        departmentBean = getter.getDepartments();
        //获取到personInfoBean对象
        personInfo = getter.getPersonInfo();
        logUtil.i(JSON.toJSONString(personInfo));
        resPersoninfo = new PersonInfoBean(personInfo);
        setPersonPhoto();
        setPersonInfo(personInfo);
    }

    private void setPersonPhoto() {
        PicassoImageLoadUtil.loadUrlToImageView(personInfo.avatar, ivPhoto, PersoninfoNewActivity.this);
        if (!TextUtils.isEmpty(personInfo.avatar)) {
            Picasso.with(PersoninfoNewActivity.this).load(personInfo.avatar).into(ivPhoto);
        }

    }

    SuccessInterfaceM<ResponseDisplayBean> successInterface = new SuccessInterfaceM<ResponseDisplayBean>() {
        @Override
        public void success(ResponseDisplayBean mes) {
            if (mes != null) {
                putter.setRegistPercentage(mes.getMessage());
            }
            setResult(RESULT_OK);
            finishCur();
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            storePersonInfo(successInterface);
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @OnClick({R.id.rlay_school, R.id.iv_back, R.id.rlay_photo, R.id.rlay_name, R.id.rlay_sex, R.id.rlay_age, R.id.rlay_post, R.id.rlay_department, R.id.rlay_num, R.id.rlay_organization, R.id.rlay_EducationLevel, R.id.rlay_studyexperience, R.id.rlay_good})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                logutil.i("onClick");
                storePersonInfo(successInterface);
                break;
            case R.id.rlay_photo:
                pickImages.pickImage();
                break;
            //姓名
            case R.id.rlay_name:
                //modifyName();
                break;
            //性别
            case R.id.rlay_sex:
                //modifyGendar();
                break;
            //年龄
            case R.id.rlay_age:
                //modifyAge();
                break;
            //职称
            case R.id.rlay_post:
                modifyJobTitle();
                break;
            //科室
            case R.id.rlay_department:
                //科室
                skipToForResult(DepartmentLevel1Activity.class, null, DepartmentLevel1Activity.ToDepartmentLevel);
                break;
            //执业编号
            case R.id.rlay_num:
                modifyCertificationNumber();
                break;
            //学历
            case R.id.rlay_EducationLevel:
                modifyTv_educationLevel();
                break;
            //机构
            case R.id.rlay_organization:
                modifyOrganzation();
                break;
            //学习与工作经历
            case R.id.rlay_studyexperience:
                modifyStudyExperience();
                break;
            //专业特长
            case R.id.rlay_good:
                modifyPracticeField();
                break;
            case R.id.rlay_school:
                toGraduateSchool();
                break;
        }
    }

    private void setPersonInfo(PersonInfoBean personInfo) {
        Context context = getApplicationContext();
        PicassoImageLoadUtil.loadUrlToImageView(personInfo.avatar, ivPhoto, context);
        tvName.setText(personInfo.fullName);
        tvGendar.setText((personInfo.gender));
        tvAge.setText(String.format("%d岁", Utils.calculateAge(personInfo.dateOfBirth)));
        tvJob.setText((personInfo.jobTitle));
        tvDepartmentName.setText((personInfo.department));
        tvNum.setText((personInfo.certificationNumber));
        //机构
        tvOrganization.setText(personInfoUtil.isempty(personInfo.organizationName));
        //学习与工作经历
        tvStudyExperience.setText(personInfoUtil.isempty(personInfo.studyExperience));
        //专业特长
        tvPracticeField.setText(personInfoUtil.isempty(personInfo.goodat));
        tv_educationLevel.setText(personInfo.educationLevel);
        tvEducationHistory.setText(personInfo.educationHistory);
    }


    private final int PhotoRequest = 10;
    private final int NameRequest = 11;
    private final int GendarRequest = 12;
    private final int AgeRequest = 13;
    private final int JobTitleRequest = 14;
    private final int DepartmentRequest = 15;
    private final int NumRequest = 16;
    private final int OrganizationRequest = 17;
    private final int ExperienceRequest = 18;
    private final int PracticeFieldRequest = 19;
    private final int GraduateSchoolRequest = 20;

    //修改姓名
    private void modifyName() {
        Bundle data = new Bundle();
        data.putString("lastName", personInfo.lastName);
        data.putString("firstName", personInfo.firstName);
        skipToForResult(NameActivity.class, data, NameRequest);
    }


    //修改性别
    private void modifyGendar() {
        if (gendarDialog == null) {
            Context context = getApplicationContext();
            View gendarView = LayoutInflater.from(context).inflate(R.layout.dialog_number_picker, null);
            TextView tv_complete = (TextView) gendarView.findViewById(R.id.tv_complete);
            final MaterialNumberPicker numberpicker = (MaterialNumberPicker) gendarView.findViewById(R.id.number_picker);
            tv_complete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int selectValue = numberpicker.getValue();
                    tvGendar.setText((sex[selectValue]));
                    personInfo.gender = sex[selectValue];
                    gendarDialog.dismiss();
                }
            });
            gendarDialog = com.example.commonlibrary.widget.util.DialogFactory.createDialog(gendarView, this, R.style.Dialog_Date);
            setNumberPicker(numberpicker, sex);
        }
        if (gendarDialog != null && !gendarDialog.isShowing()) {
            gendarDialog.show();
        }
    }

    //修改年龄
    private void modifyAge() {
        Bundle data = new Bundle();
        data.putString("dateOfBirth", personInfo.dateOfBirth);
        skipToForResult(SelectDateOfBirthActivity.class, data, AgeRequest);
    }

    Dialog gendarDialog;
    private String[] sex = {"男", "女"};



    //修改职称
    private void modifyJobTitle() {
        Bundle bundle = new Bundle();
        bundle.putString("hospitalTitles", personInfo.HospitalTitles);
        bundle.putString("educationTitles", personInfo.EducationTitles);
        bundle.putString("mentorshipTitles", personInfo.MentorshipTitles);
        skipToForResult(SelectProfessionalTitleActivity.class, bundle, JobTitleRequest);
    }

    //修改执业编号
    private void modifyCertificationNumber() {
        Bundle bundle = new Bundle();
        bundle.putString("certificationNumber", personInfo.certificationNumber);
        skipToForResult(CertificationNumberActivity.class, bundle, NumRequest);
    }

    //修改学历
    private void modifyTv_educationLevel() {
        educationLevelDialog.show();
    }

    /**
     * 初始化学历
     */
    private void neweducationLevelDialog() {
        educationLevel = getResources()
                .getStringArray(R.array.educationLevel);
        educationLevelDialogPopUp = new PopUpNumberPickerDialog(this, educationLevel, new PopUpNumberPickerDialog.SetClickListener() {
            @Override
            public void onClick(final int index) {
                if (!(tv_educationLevel.getText().toString().equals(educationLevel[index]))) {
                    LoadingUtil loadingUtil = LoadingUtil.getInstance(PersoninfoNewActivity.this);
                    loadingUtil.show();
                    loadingUtil.dismiss("修改成功", new LoadingUtil.Success() {
                        @Override
                        public void dismiss() {
                            tv_educationLevel.setText(educationLevel[index]);
                        }
                    });
                }else {
                    tv_educationLevel.setText(educationLevel[index]);
                }
                personInfo.educationLevel = educationLevel[index];
            }
        });
        educationLevelDialog = educationLevelDialogPopUp.getDialog();
    }

    //修改机构dd
    private void modifyOrganzation() {
        Bundle data = new Bundle();
        data.putString("hospitalName", personInfoUtil.isempty(personInfo.organizationName));
        skipToForResult(HospitalNameActivity.class, data, OrganizationRequest);
    }

    //修改学习与工作经历
    private void modifyStudyExperience() {
        Bundle bundle = new Bundle();
        bundle.putString("workingExperience", personInfoUtil.isempty(personInfo.studyExperience));
        skipToForResult(StudyExperienceActivity.class, bundle, ExperienceRequest);
    }

    //修改专业特长
    private void modifyPracticeField() {
        Bundle bundle = new Bundle();
        bundle.putString("practiceField", personInfoUtil.isempty(personInfo.goodat));
        skipToForResult(PracticeFieldActivity.class, bundle, PracticeFieldRequest);
    }

    //保存个人信息
    public void storePersonInfo(final SuccessInterfaceM<ResponseDisplayBean> successInterface) {

        if (judgeWhetherStoreInfo()) {
            final LoadingUtil loadingUtil = LoadingUtil.getInstance(this);
//
//            loadingUtil.show("保存个人信息中");
            if (needModifyPhoto()) {
                uploadFile.startUpload(paths, new SuccessInterfaceM<String>() {
                    @Override
                    public void success(String s) {
                        logutil.i(s);
                        String avatar = JSON.parseArray(s, ImageBean.class).get(0).getDocumentUrl();
                        personInfo.avatar = avatar;
                        PersonUtilNew.updataPersoninfo(personInfo, new SuccessInterfaceM<ResponseDisplayBean>() {
                            @Override
                            public void success(final ResponseDisplayBean message) {
//                                loadingUtil.dismiss("修改个人信息成功", new LoadingUtil.Success() {
//                                    @Override
//                                    public void dismiss() {
                                        putter.setPersonInfo(personInfo);
                                        successInterface.success(message);
                                finishCur();
//                                    }
//                                });
                            }
                        }, new ErrorInterfaceM<Throwable>() {
                            @Override
                            public void error(final Throwable volleyError) {
//                                loadingUtil.dismiss("修改个人信息失败", new LoadingUtil.Success() {
//                                    @Override
//                                    public void dismiss() {
                                        Bugtags.sendException(volleyError);
                                        successInterface.success(null);
//                                    }
//                                });
                            }
                        }, PersoninfoNewActivity.this);
                    }
                });
            } else {
                PersonUtilNew.updataPersoninfo(personInfo, new SuccessInterfaceM<ResponseDisplayBean>() {
                    @Override
                    public void success(final ResponseDisplayBean message) {
//                        loadingUtil.dismiss("修改个人信息成功", new LoadingUtil.Success() {
//                            @Override
//                            public void dismiss() {
                                putter.setPersonInfo(personInfo);
                                successInterface.success(message);
//                            }
//                        });

                    }
                }, new ErrorInterfaceM<Throwable>() {
                    @Override
                    public void error(final Throwable volleyError) {
//                        loadingUtil.dismiss("修改个人信息失败", new LoadingUtil.Success() {
//                            @Override
//                            public void dismiss() {
                                Bugtags.sendException(volleyError);
                                volleyError.printStackTrace();
                                successInterface.success(null);
//                            }
//                        });
                    }
                }, PersoninfoNewActivity.this);
            }
        } else {
            successInterface.success(null);
        }
    }

    //判断是否需要保存个人信息
    private boolean judgeWhetherStoreInfo() {
        Field[] filds = personInfo.getClass().getDeclaredFields();
        for (int i = 0; i < filds.length; i++) {
            try {
                filds[i].setAccessible(true);
                Object one = filds[i].get(personInfo);
                Object two = filds[i].get(resPersoninfo);
                if (((one == null && two != null) || (one != null && two == null))) {
                    return true;
                }
                if ((one != null) && (two != null)) {
                    if (!(one.equals(two))) {
                        return true;
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                Bugtags.sendException(e);
                return false || needModifyPhoto();
            }
        }
        return false || needModifyPhoto();
    }

    private void setNumberPicker(MaterialNumberPicker numberpicker, String[] content) {
        numberpicker.setDisplayedValues(null);
        numberpicker.setMaxValue(content.length - 1);
        numberpicker.setMinValue(0);
        numberpicker.setValue(1);
        numberpicker.setDisplayedValues(content);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle bundle;
        pickImages.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            bundle = data.getExtras();
        } else {
            return;
        }
        switch (requestCode) {
            case NameRequest:
                if (resultCode == Activity.RESULT_OK) {
                    String firstName = bundle.getString("firstName", "");
                    String lastName = bundle.getString("lastName", "");
                    String fullName = lastName + firstName;
                    personInfo.lastName = lastName;
                    personInfo.lastName = lastName;
                    personInfo.fullName = fullName;
                    tvName.setText(fullName);
                }
                break;
            case AgeRequest:
                if (resultCode == Activity.RESULT_OK) {
                    Date dateOfBirth = (Date) bundle.getSerializable("dateOfBirth");
                    personInfo.dateOfBirth = Utils.getFormatDate(dateOfBirth);
                    tvAge.setText(String.format("%d岁", Utils.calculateAge(dateOfBirth)));
                }
                break;
            case JobTitleRequest:
                if (resultCode == Activity.RESULT_OK) {
                    String mentorshipTitles = bundle.getString("mentorshipTitles");
                    String hospitalTitles = bundle.getString("hospitalTitles");
                    String educationTitles = bundle.getString("educationTitles");
                    personInfo.MentorshipTitles = mentorshipTitles;
                    personInfo.HospitalTitles = hospitalTitles;
                    personInfo.jobTitle = hospitalTitles;
                    personInfo.EducationTitles = educationTitles;
                    tvJob.setText((hospitalTitles));
                }
                break;
            case NumRequest:
                if (resultCode == Activity.RESULT_OK) {
                    String certificationNumber = bundle.getString("certificationNumber");
                    personInfo.certificationNumber = certificationNumber;
                    tvNum.setText(certificationNumber);
                }
                break;
            case OrganizationRequest:
                if (resultCode == Activity.RESULT_OK) {
                    String hospitalName = bundle.getString("hospitalName");
                    personInfo.organizationName = hospitalName;
                    tvOrganization.setText((hospitalName));
                }
                break;
            case ExperienceRequest:
                if (resultCode == Activity.RESULT_OK) {
                    String workingExperience = bundle.getString("workingExperience");
                    personInfo.studyExperience = workingExperience;
                    tvStudyExperience.setText(workingExperience);
                }
                break;
            case PracticeFieldRequest:
                if (resultCode == Activity.RESULT_OK) {
                    String practiceField = bundle.getString("practiceField");
                    personInfo.goodat = practiceField;
                    tvPracticeField.setText(practiceField);
                }
                break;
            case GraduateSchoolRequest:
                if (resultCode == Activity.RESULT_OK) {
                    String strGraduateSchool = bundle.getString("educationHistory", "");
                    personInfo.educationHistory = strGraduateSchool;
                    tvEducationHistory.setText(strGraduateSchool);
                }
                break;
            case DepartmentLevel1Activity.ToDepartmentLevel:
                if (resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    DepartmentDisplayBean bean = (DepartmentDisplayBean) extras.getSerializable(DepartmentLevel2Activity.DepartmentLevel2ActivityKey);
                    personInfo.department = bean.departmentName;
                    personInfo.departmentId = bean.departmentId;
                    tvDepartmentName.setText(bean.departmentName);
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

    /**
     * @return true：需要修改头像， false：不需要修改头像
     */
    boolean needModifyPhoto() {
        return paths.size() != 0;
    }

    //跳转到毕业院校界面
    private void toGraduateSchool() {
        Bundle bundle = new Bundle();
        bundle.putString("educationHistory", personInfo.educationHistory);
        skipToForResult(GraduateSchoolActivity.class, bundle, GraduateSchoolRequest);
    }

}

