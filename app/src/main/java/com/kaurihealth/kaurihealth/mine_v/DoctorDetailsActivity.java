package com.kaurihealth.kaurihealth.mine_v;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.commonlibrary.widget.CircleImageView;
import com.example.commonlibrary.widget.util.LoadingUtil;
import com.example.commonlibrary.widget.util.SuccessInterface;
import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.request_bean.bean.DepartmentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.PersonInfoBean;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.department_v.SelectDepartmentLevel1Activity;
import com.kaurihealth.kaurihealth.mine.util.PersonInfoUtil;
import com.kaurihealth.kaurihealth.util.Getter;
import com.kaurihealth.kaurihealth.util.Interface.IGetter;
import com.kaurihealth.kaurihealth.util.PicassoImageLoadUtil;
import com.kaurihealth.kaurihealth.util.PickImage;
import com.kaurihealth.kaurihealth.util.UploadFileUtil;
import com.kaurihealth.kaurihealth.util.Url;
import com.kaurihealth.mvplib.mine_p.DoctorDetailsPresenter;
import com.kaurihealth.mvplib.mine_p.IDoctorDetailsView;
import com.kaurihealth.utilslib.constant.ImageRendition;
import com.kaurihealth.utilslib.dialog.PopUpNumberPickerDialog;
import com.squareup.picasso.Picasso;
import com.youyou.zllibrary.util.Utils;

import java.io.File;
import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * 医生个人信息界面
 */
public class DoctorDetailsActivity extends BaseActivity implements IDoctorDetailsView {
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


    private LoadingUtil loadingUtil;
    private IGetter getter;
    private PersonInfoUtil personInfoUtil;
    private UploadFileUtil uploadFile;
    private PersonInfoBean personInfo;
    private Dialog educationLevelDialog;

    ArrayList<String> paths = new ArrayList<>();


    @Inject
    DoctorDetailsPresenter<IDoctorDetailsView> mPresenter;
    private PickImage pickImages;
    private String[] educationLevel;
    private DepartmentDisplayBean[] departmentBean;
    private PopUpNumberPickerDialog educationLevelDialogPopUp;

    private DoctorDisplayBean myself;


    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_doctor_details;
    }

    @Override
    protected void initPresenterAndData(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
        myself = LocalData.getLocalData().getMyself();
    }

    @Override
    protected void initDelayedView() {
        initBackBtn(R.id.iv_back);
        //初始化对象
        //init();
        renderView();
    }


    private void renderView() {
        tvName.setText(myself.fullName);
        tvGendar.setText(myself.gender);
        tvAge.setText(String.format("%d岁", Utils.calculateAge(myself.dateOfBirth)));

        tvJob.setText((myself.hospitalTitle));
        tvNum.setText(myself.certificationNumber);

        tv_educationLevel.setText(myself.doctorEducation.educationLevel);
        tvOrganization.setText(myself.doctorInformations.hospitalName);
        tvOrganization.setText(myself.doctorEducation.educationHistory);
        tvOrganization.setText(myself.workingExperience);
        tvOrganization.setText(myself.practiceField);

        educationLevel = getResources().getStringArray(R.array.educationLevel);
        educationLevelDialogPopUp = new PopUpNumberPickerDialog(this, educationLevel, new PopUpNumberPickerDialog.SetClickListener() {
            @Override
            public void onClick(final int index) {
                if (educationLevel[index] != myself.doctorEducation.educationLevel) {
                    tv_educationLevel.setText(educationLevel[index]);
                    myself.doctorEducation.educationLevel = educationLevel[index];
                    mPresenter.onSubscribe();
                }
            }
        });
        educationLevelDialog = educationLevelDialogPopUp.getDialog();
        if (!TextUtils.isEmpty(myself.avatar)) {
            Picasso.with(DoctorDetailsActivity.this).load(myself.avatar + ImageRendition.Size[3]).into(ivPhoto);
        }
    }

    //初始化对象
    private void init() {
        loadingUtil = LoadingUtil.getInstance(DoctorDetailsActivity.this);
        getter = Getter.getInstance(getApplicationContext());

        personInfoUtil = new PersonInfoUtil();
        uploadFile = new UploadFileUtil(getter.getKauriHealthId(), getter.getToken(), this, Url.AddImage);
        pickImages = new PickImage(paths, this, new SuccessInterface() {
            @Override
            public void success() {
                loadingUtil.show();
                loadingUtil.dismiss("修改成功", new LoadingUtil.Success() {
                    //重写LoadingUtil里面的逻辑
                    @Override
                    public void dismiss() {
                        String path = paths.get(0);
                        PicassoImageLoadUtil.loadUrlToImageView(new File(path), ivPhoto, getApplicationContext());
                    }
                });
            }
        }, 1);
    }


    /**
     * ----------------------------继承基础mvpView方法-----------------------------------
     *
     * @param className
     */

    @Override
    public void switchPageUI(String className) {

    }


    @OnClick({R.id.rlay_school, R.id.iv_back, R.id.rlay_photo, R.id.rlay_name, R.id.rlay_sex, R.id.rlay_age, R.id.rlay_post, R.id.rlay_department, R.id.rlay_num, R.id.rlay_organization, R.id.rlay_EducationLevel, R.id.rlay_studyexperience, R.id.rlay_good})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:   //回退键保存个人信息
                //storePersonInfo(successInterface);
                break;
            case R.id.rlay_photo:
                //pickImages.pickImage();
                break;
            //姓名
            case R.id.rlay_name:
                break;
            //性别
            case R.id.rlay_sex:
                break;
            //年龄
            case R.id.rlay_age:
                break;
            //职称
            case R.id.rlay_post:
                //修改职称
                skipTo(SelectTitleActivity.class);
                break;
            //科室
            case R.id.rlay_department:
                //科室
                skipTo(SelectDepartmentLevel1Activity.class);
                break;
            //执业编号
            case R.id.rlay_num:
                //修改职业编号
                skipTo(EnterCertificationNumberActivity.class);
                break;
            //学历
            case R.id.rlay_EducationLevel:
                //修改学历
                educationLevelDialog.show();
                break;
            //机构
            case R.id.rlay_organization:
                //修改机构
                skipTo(EnterHospitalActivity.class);
                break;
            //学习与工作经历
            case R.id.rlay_studyexperience:
                //修改学习与工作经历
                skipTo(EnterStudyExperienceActivity.class);
                break;
            //专业特长
            case R.id.rlay_good:
                //修改专业特长
                skipTo(EnterPracticeFieldActivity.class);
                break;
            //毕业院校
            case R.id.rlay_school:
                skipTo(EnterGraduateSchoolActivity.class);
                break;
        }
    }


    /**
     * 是否需要修改头像
     * @return  true:需要修改头像   false:不需要修改头像
     */
    private boolean needModifyPhoto() {
       return paths.size() != 0;
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public DoctorDisplayBean getDoctorDisplayBean() {
        return myself;
    }

    @Override
    public void displayError(Throwable e) {
        displayErrorDialog(e.getMessage());
    }
}
