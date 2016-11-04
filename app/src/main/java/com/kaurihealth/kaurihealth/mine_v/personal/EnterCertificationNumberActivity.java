package com.kaurihealth.kaurihealth.mine_v.personal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.mvplib.mine_p.EnterCertificationNumberPresenter;
import com.kaurihealth.mvplib.mine_p.IEnterCertificationNumberView;
import com.kaurihealth.utilslib.ValidatorUtils;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * 我的Button 完善个人信息--> 执业证号   created by Nick
 */
public class EnterCertificationNumberActivity extends BaseActivity implements IEnterCertificationNumberView, Validator.ValidationListener {
    @Inject
    EnterCertificationNumberPresenter<IEnterCertificationNumberView> mPresenter;

    @Bind(R.id.tv_more)
    TextView tvMore;
    //请输入执业证号编辑框
    @NotEmpty(message = "执业编号不能为空")
    @Pattern(sequence = 2, regex = "^\\d{15}$", message = "请输入15位正确的职业编号")
    @Bind(R.id.edtCertificationNumber)
    EditText edtCertificationNumber;
    @Bind(R.id.ivDelete)
    ImageView ivDelete;

    private Validator validator;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_enter_certification_number;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
    }

    @Override
    protected void initDelayedData() {
        initNewBackBtn(getString(R.string.certification_title_tv_title));
        tvMore.setText(getString(R.string.title_save));


        String certificationNumber = LocalData.getLocalData().getMyself().getCertificationNumber();
        //获取证书编号
        setEdtCertificationNumber(certificationNumber);
        setSelection(edtCertificationNumber);
        //初始化validator
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    @OnClick({R.id.tv_more, R.id.ivDelete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_more:
                validator.validate();  //点击“保存”，开启表单验证
                break;
            case R.id.ivDelete:
                deleteEditText();
                break;
            default:break;
        }
    }

    //清除执业证书编号
    private void deleteEditText() {
        clearTextView(edtCertificationNumber);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribe();
    }

    /**
     * ----------------------------继承基础mvpView方法-----------------------------------
     */

    //IMvpView
    @Override
    public void switchPageUI(String className) {
        Intent intent = getIntent();  //初始化intent
        setResult(RESULT_OK, intent);
        finishCur();
    }

    //表单验证成功
    @Override
    public void onValidationSucceeded() {
        mPresenter.onSubscribe();
    }

    //表单验证失败
    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        //sweetDialog错误对话框
        String message = ValidatorUtils.validationErrorMessage(getApplicationContext(), errors);
        displayErrorDialog(message);
    }

    //IEnterCertificationNumberView
    @Override
    public DoctorDisplayBean getDoctorDisplayBean() {
        DoctorDisplayBean bean = (DoctorDisplayBean) LocalData.getLocalData().getMyself().clone();
        bean.setCertificationNumber(getEdtCertificationNumber());
        return bean;
    }

    public String getEdtCertificationNumber() {
        return edtCertificationNumber.getText().toString().trim();
    }

    public void setEdtCertificationNumber(String edtCertificationNumber) {
        this.edtCertificationNumber.setText(edtCertificationNumber);
    }
}
