package com.kaurihealth.kaurihealth.mine_v.personal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.response_bean.DepartmentDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorInformationDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.department_v.SelectDepartmentLevel1Activity;
import com.kaurihealth.kaurihealth.department_v.SelectDepartmentLevel2Activity;
import com.kaurihealth.kaurihealth.eventbus.HospitalAndDepartmentEvent;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.mvplib.mine_p.HospitalAndDepartmentPresenter;
import com.kaurihealth.mvplib.mine_p.IHospitalAndDepartmentView;
import com.kaurihealth.utilslib.ValidatorUtils;
import com.kaurihealth.utilslib.constant.Global;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Garnet_Wu on 2016/12/30.
 * 描述：
 * Step1： 点击“我的” 科室 或者 医院 都跳转到这个界面
 * Step2： 界面分为两个条目， 点击科室条目跳转科室列表界面 ； 机构也是如此
 * Step3： 科室，机构信息拿到之后， 再用EventBus发送回原来的DoctorDetailsActivity。进行科室和机构的回显
 * 只有在点击 "存储" 按钮的时候, 才会去更新. 其他点任何按钮 都是无作用的,点击机构的"完成"只是
 */

public class HospitalAndDepartmentActivity extends BaseActivity implements IHospitalAndDepartmentView, Validator.ValidationListener {
    @Inject
    HospitalAndDepartmentPresenter<IHospitalAndDepartmentView> mPresenter;

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_save)
    TextView tvSave;

    @Bind(R.id.rlay_hospital)
    RelativeLayout rlayHospital;
    @NotEmpty(message = "机构不能为空")
    @Bind(R.id.tv_select_hospital)
    TextView tvHospital;

    @Bind(R.id.rlay_department)
    RelativeLayout rlayDeparment;
    @NotEmpty(message = "科室不能为空")
    @Bind(R.id.tv_select_department)
    TextView tvDepartment;

    private Validator validator;
    private DepartmentDisplayBean mDepartment;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_hospital_and_department;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        tvSave.setText("存储");
        if (getBundle() != null) {
            String bundle = getBundle().getString(Global.Environment.BUNDLE);
            if (bundle != null && !TextUtils.isEmpty(bundle) && bundle.contains("/")) {
                String[] strings = bundle.split("/");
                setTvHospital(strings[0]);
                setTvDepartmentName(strings[1]);
            }
        }
    }

    @Override
    protected void initDelayedData() {
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    /**
     * ----------------------------点击onClick事件-----------------------------------
     */
    @OnClick({R.id.rlay_hospital, R.id.rlay_department})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlay_hospital:   //修改医院
                selectHospital(getEdtHospitalName());
                break;
            case R.id.rlay_department: //修改科室
                selectDepartment();
                break;
            default:
                break;
        }
    }

    //回退键 ，存储键的点击事件
    @OnClick({R.id.iv_back, R.id.tv_save})
    public void onClickTitle(View view) {
        switch (view.getId()) {
            case R.id.iv_back:  //回退键
                finishCur();
                break;
            case R.id.tv_save:
                validator.validate();
                break;
            default:
                break;
        }
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

    /**
     * 接受科室
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Global.RequestCode.HOSPITAL:
                if (resultCode == RESULT_OK) {
                    String hospitalName = data.getStringExtra("hospitalName");
                    setTvHospital(hospitalName);
                }
                break;
            case Global.RequestCode.DEPARTMENT:
                if (resultCode == RESULT_OK) {
                    mDepartment = (DepartmentDisplayBean) data.getSerializableExtra(SelectDepartmentLevel2Activity.DepartmentLevel2ActivityKey);
                    setTvDepartmentName(mDepartment != null ? mDepartment.getDepartmentName() : "暂无");
                }
                break;
            default:
                break;
        }
    }

    public void setTvHospital(String tvOrganization) {
        this.tvHospital.setText(tvOrganization);
    }

    public void setTvDepartmentName(String tvDepartmentName) {
        this.tvDepartment.setText(tvDepartmentName);
    }

    //验证通过
    @Override
    public void onValidationSucceeded() {
        EventBus.getDefault().post(new HospitalAndDepartmentEvent(getEdtHospitalName(), getTvDepartmentName(), mDepartment.getDepartmentId()));
        finishCur();
    }

    //验证失败
    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        String message = ValidatorUtils.validationErrorMessage(getApplicationContext(), errors);
        displayErrorDialog(message);
    }

    //IMvpView
    @Override
    public void switchPageUI(String className) {
        //将数据回传给DoctorDetailsActivity  或者   RegisterPersonInfoActivity
        EventBus.getDefault().postSticky(new HospitalAndDepartmentEvent(getEdtHospitalName(), getTvDepartmentName(), mDepartment.getDepartmentId()));   //发送机构，科室
    }

    //"我的"
    @Override
    public DoctorDisplayBean getDoctorDisplayBean() {
        DoctorDisplayBean bean = (DoctorDisplayBean) LocalData.getLocalData().getMyself().clone();
        DoctorInformationDisplayBean doctorInformations = bean.getDoctorInformations();
        if (doctorInformations != null) {
            doctorInformations.setHospitalName(getEdtHospitalName());   //医院
        }
        //科室
        if (bean.getDoctorInformations() != null) {
            bean.getDoctorInformations().setDepartmentId(mDepartment.getDepartmentId());
            if (bean.getDoctorInformations().getDepartment() != null) {
                bean.getDoctorInformations().getDepartment().setDepartmentId(mDepartment.getDepartmentId());
            } else {
                bean.getDoctorInformations().setDepartment(mDepartment);
            }
        }
        return bean;
    }

    public String getEdtHospitalName() {
        return tvHospital.getText().toString().trim();
    }

    public String getTvDepartmentName() {
        return tvDepartment.getText().toString().trim();
    }

}
