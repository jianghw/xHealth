package com.kaurihealth.kaurihealth.mine_v.personal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.request_bean.bean.MobileUpdateDoctorBean;
import com.kaurihealth.datalib.response_bean.DepartmentDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorInformationDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.department_v.SelectDepartmentLevel1Activity;
import com.kaurihealth.kaurihealth.department_v.SelectDepartmentLevel2Activity;
import com.kaurihealth.kaurihealth.eventbus.HospitalAndDepartmentEvent;
import com.kaurihealth.kaurihealth.eventbus.MineFragmentEvent;
import com.kaurihealth.kaurihealth.main_v.MainActivity;
import com.kaurihealth.kaurihealth.open_account_v.ModifyNameActivity;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.mvplib.mine_p.DoctorDetailsPresenter;
import com.kaurihealth.mvplib.mine_p.IDoctorDetailsView;
import com.kaurihealth.utilslib.ValidatorUtils;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.dialog.DialogUtils;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * 医生完善更多信息界面
 */
public class DoctorDetailsActivity extends BaseActivity implements IDoctorDetailsView ,Validator.ValidationListener{
    @Inject
    DoctorDetailsPresenter<IDoctorDetailsView> mPresenter;

    @Bind(R.id.iv_delete)
    ImageView mIvDelete;

    //提交认证按钮
    @Bind(R.id.tv_operate_notify)
    TextView mTvOperateNotify;

    //姓名
    @NotEmpty(message = "姓名不能为空")
    @Bind(R.id.tv_name)
    TextView mTvName;

    @Bind(R.id.rlay_name)
    RelativeLayout mRlayName;

    //性别
    @NotEmpty(message = "性别不能为空")
    @Bind(R.id.tv_gender)
    TextView mTvGender;

    @Bind(R.id.rlay_sex)
    RelativeLayout mRlaySex;

    //科室
    @NotEmpty(message = "科室不能为空")
    @Bind(R.id.tv_department)
    TextView mTvDepartment;

    @Bind(R.id.rlay_department)
    RelativeLayout mRlayDepartment;

    //机构
    @NotEmpty(message = "机构不能为空")
    @Bind(R.id.tv_organization)
    TextView mTvOrganization;

    @Bind(R.id.rlay_organization)
    RelativeLayout mRlayOrganization;

    //职称
    @NotEmpty(message = "职称不能为空")
    @Bind(R.id.tv_job)
    TextView mTvJob;

    @Bind(R.id.rlay_post)
    RelativeLayout mRlayPost;

    //执业证号
    @NotEmpty(message = "执业证号不能为空")
    @Bind(R.id.tv_num)
    TextView mTvNum;

    @Bind(R.id.rlay_num)
    RelativeLayout mRlayNum;

    private String hospitalName;
    private String departmentName;
    private int departmentId;
    private String lastName;
    private String firstName;
    private String mHospitalTitles;
    private String mMentorshipTitles;
    private String mEducationTitles;
    private Validator mValidator;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_doctor_details;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
    }


    @Override
    protected void initDelayedData() {
        initDoctorDetails();

        initBundleDoctor();

        EventBus.getDefault().register(this);
        mValidator = new Validator(this);
        mValidator.setValidationListener(this);
    }

    /**
     * 默认
     */
    private void initDoctorDetails() {
        DoctorDisplayBean doctorDisplayBean = LocalData.getLocalData().getMyself();
        if (doctorDisplayBean != null) {
            setTvName(doctorDisplayBean.getFullName());
            setTvGender(doctorDisplayBean.getGender());
            DoctorInformationDisplayBean informations = doctorDisplayBean.getDoctorInformations();
            if (informations != null) {
                DepartmentDisplayBean department = informations.getDepartment();
                setTvDepartment(department != null ? department.getDepartmentName() : "请添加");
                setTvOrganization(informations.getHospitalName());
            }
            setTvJob(doctorDisplayBean.getHospitalTitle());
            setTvNum(doctorDisplayBean.getCertificationNumber());
        }
    }

    /**
     * 注册页面
     */
    private void initBundleDoctor() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            lastName = bundle.getString("lastName");
            firstName = bundle.getString("firstName");
            String sex = bundle.getString("sex");
            if (!TextUtils.isEmpty(lastName) && !TextUtils.isEmpty(firstName))
                setTvName(firstName + lastName);
            if (!TextUtils.isEmpty(sex)) setTvGender(sex);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mPresenter.unSubscribe();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(HospitalAndDepartmentEvent event) {
        hospitalName = event.getHospitalName();
        departmentName = event.getDepartmentName();
        departmentId = event.getDepartmentId();

        mTvOrganization.setText(hospitalName);
        mTvDepartment.setText(departmentName);
    }

    @Override
    public MobileUpdateDoctorBean getMobileUpdateDoctorBean() {
        MobileUpdateDoctorBean mobileUpdateDoctorBean = new MobileUpdateDoctorBean();
        mobileUpdateDoctorBean.setFirstName(firstName);  //名
        mobileUpdateDoctorBean.setLastName(lastName);   //姓
        mobileUpdateDoctorBean.setGender(getTvGender());

        mobileUpdateDoctorBean.setDepartmentId(departmentId); //科室id
        mobileUpdateDoctorBean.setHospitalName(hospitalName); //医院
        mobileUpdateDoctorBean.setDepartmentName(departmentName);  //科室姓名
        mobileUpdateDoctorBean.setHospitalTitle(getTvJob());
        mobileUpdateDoctorBean.setMentorshipTitle(mMentorshipTitles);
        mobileUpdateDoctorBean.setEducationTitle(mEducationTitles);
        mobileUpdateDoctorBean.setCertificationNumber(getTvNum());
        return mobileUpdateDoctorBean;

    }

    @Override
    public void connectLeanCloud() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Global.Environment.BUNDLE, 4);
        startActivity(intent);
    }

    /**
     * ----------------------------继承基础mvpView方法-----------------------------------
     */
    @Override
    public void switchPageUI(String className) {
        EventBus.getDefault().post(new MineFragmentEvent());
    }

    /**
     * 点击
     */
    @OnClick({R.id.rlay_name, R.id.rlay_sex, R.id.rlay_organization, R.id.rlay_department,
            R.id.rlay_post, R.id.rlay_num, R.id.tv_operate_notify, R.id.iv_delete})
    public void verifyClick(View view) {
        switch (view.getId()) {
            case R.id.rlay_name:
                selectName();
                break;
            case R.id.rlay_sex:     //性别
                showGenderDialog();
                break;
            case R.id.rlay_organization:
                selectHospital(getTvOrganization());
                break;
            case R.id.rlay_department:
                selectDepartment();
                break;
            case R.id.rlay_post:
                selectTitle();
                break;
            case R.id.rlay_num:
                selectNumber();
                break;
            case R.id.tv_operate_notify:  //"提交认证"按钮
                mValidator.validate();
                break;
            case R.id.iv_delete:
                finishCur();
                break;
            default:
                break;
        }
    }

    private void selectName() {
        Bundle bundle = new Bundle();
        bundle.putString("lastName", lastName);
        bundle.putString("firstName", firstName);
        skipToForResult(ModifyNameActivity.class, bundle, Global.RequestCode.NAME_LAST_FIRST);
    }

    private void showGenderDialog() {
        String[] gender = getResources().getStringArray(R.array.gender);
        DialogUtils.showStringDialog(this, gender, index -> setTvGender(gender[index]));
    }

    /**
     * 选择科室
     */
    private void selectDepartment() {
        Bundle bundle = new Bundle();
        bundle.putString(Global.Environment.BUNDLE, Global.Environment.CHOICE);
        skipToForResult(SelectDepartmentLevel1Activity.class, bundle, Global.RequestCode.DEPARTMENT);
    }

    /**
     * 选择医院
     */
    private void selectHospital(String content) {
        Bundle bundle = new Bundle();
        bundle.putString("hospitalName", content);
        bundle.putString(Global.Environment.BUNDLE, Global.Environment.CHOICE);
        skipToForResult(EnterHospitalActivity.class, bundle, Global.RequestCode.HOSPITAL);
    }

    private void selectTitle() {
        Bundle bundle = new Bundle();
        bundle.putString(Global.Environment.BUNDLE, Global.Environment.CHOICE);
        skipToForResult(SelectTitleActivity.class, bundle, Global.RequestCode.DOCTOR_TITLE);
    }

    private void selectNumber() {
        Bundle bundle = new Bundle();
        bundle.putString(Global.Environment.BUNDLE, Global.Environment.CHOICE);
        skipToForResult(EnterCertificationNumberActivity.class, bundle, Global.RequestCode.NUMBER_TITLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Global.RequestCode.NAME_LAST_FIRST:
                if (resultCode == RESULT_OK) {
                    lastName = data.getStringExtra("lastName");
                    firstName = data.getStringExtra("firstName");
                    setTvName(firstName + lastName);
                }
                break;
            case Global.RequestCode.HOSPITAL:
                if (resultCode == RESULT_OK) {
                    hospitalName = data.getStringExtra("hospitalName");
                    setTvOrganization(hospitalName);
                }
                break;
            case Global.RequestCode.DEPARTMENT:
                if (resultCode == RESULT_OK) {
                    DepartmentDisplayBean mDepartment = (DepartmentDisplayBean)
                            data.getSerializableExtra(SelectDepartmentLevel2Activity.DepartmentLevel2ActivityKey);
                    if (mDepartment != null) {
                        departmentName = mDepartment.getDepartmentName();
                        departmentId = mDepartment.getDepartmentId();
                        setTvDepartment(departmentName);
                    }
                }
                break;
            case Global.RequestCode.DOCTOR_TITLE://职称
                if (resultCode == RESULT_OK) {
                    mHospitalTitles = data.getStringExtra("HospitalTitles");
                    mMentorshipTitles = data.getStringExtra("MentorshipTitles");
                    mEducationTitles = data.getStringExtra("EducationTitles");
                    setTvJob(mHospitalTitles);
                }
                break;
            case Global.RequestCode.NUMBER_TITLE://执业证
                if (resultCode == RESULT_OK) {
                    String mEdtCertificationNumber = data.getStringExtra("EdtCertificationNumber");
                    setTvNum(mEdtCertificationNumber);
                }
                break;
            default:
                break;
        }
    }

    public String getTvName() {
        return mTvName.getText().toString().trim();
    }

    public void setTvName(String tvName) {
        mTvName.setText(tvName);
    }

    public String getTvGender() {
        return mTvGender.getText().toString().trim();
    }

    public void setTvGender(String tvGender) {
        mTvGender.setText(tvGender);
    }

    public String getTvDepartment() {
        return mTvDepartment.getText().toString().trim();
    }

    public void setTvDepartment(String tvDepartment) {
        mTvDepartment.setText(tvDepartment);
    }

    public String getTvOrganization() {
        return mTvOrganization.getText().toString().trim();
    }

    public void setTvOrganization(String tvOrganization) {
        mTvOrganization.setText(tvOrganization);
    }

    public String getTvJob() {
        return mTvJob.getText().toString().trim();
    }

    public void setTvJob(String tvJob) {
        mTvJob.setText(tvJob);
    }

    public String getTvNum() {
        return mTvNum.getText().toString().trim();
    }

    public void setTvNum(String tvNum) {
        mTvNum.setText(tvNum);
    }


    //验证通过
    @Override
    public void onValidationSucceeded() {
        mPresenter.onSubscribe();
    }

    //验证失败
    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        String message = ValidatorUtils.validationErrorMessage(getApplicationContext(), errors);
        displayErrorDialog(message);
    }
}
