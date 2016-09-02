package com.kaurihealth.kaurihealth.mine_v;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.commonlibrary.widget.util.LoadingUtil;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.mine.util.PersonInfoUtil;
import com.kaurihealth.kaurihealth.util.Config;
import com.kaurihealth.kaurihealth.util.TextWatchClearError;
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
public class EnterCertificationNumberActivity extends BaseActivity implements IEnterCertificationNumberView,Validator.ValidationListener {

    //请输入执业证号编辑框
    @NotEmpty(message = "执业编号不能为空")
    @Pattern(sequence = 2, regex = "^\\d{15}$", message = "请输入15位正确的职业编号")
    @Bind(R.id.edtCertificationNumber)
    EditText edtCertificationNumber;
    //职业证号编辑框里的删除按钮(Style:Gone)
    @Bind(R.id.ivDelete)
    ImageView ivDelete;

    @Inject
    EnterCertificationNumberPresenter<IEnterCertificationNumberView> mPresenter;
    private Validator validator;
    private String certificationNumber;
    private LoadingUtil loadingUtil;
    private PersonInfoUtil personInfoUtil;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_enter_certification_number;
    }

    @Override
    protected void initPresenterAndData(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
    }

    @Override
    protected void initDelayedView() {
        initBackBtn(R.id.iv_back);
        loadingUtil = LoadingUtil.getInstance(EnterCertificationNumberActivity.this);
        Bundle data = getBundle();
        certificationNumber = data.getString("certificationNumber");
        edtCertificationNumber.setText(certificationNumber);
        edtCertificationNumber.addTextChangedListener(new TextWatchClearError(edtCertificationNumber,ivDelete));
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    @OnClick({R.id.tv_operate, R.id.ivDelete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_operate:
                validator.validate();  //点击“保存”，开启表单验证
                break;
            case R.id.ivDelete:
                delete();
                break;
        }
    }

    //清除执业证书编号
    private void delete() {
        clearTextView(edtCertificationNumber);
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

    /**
     * ----------------------------继承基础mvpView方法-----------------------------------
     *
     * @param className*/


    //IMvpView
    @Override
    public void switchPageUI(String className) {

    }

    //表单验证成功
    @Override
    public void onValidationSucceeded() {
        loadingUtil.show("修改成功");
        if (personInfoUtil == null) {
            personInfoUtil = PersonInfoUtil.getInstance(this, Config.Role.Doctor);
        }
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        bundle.putString("certificationNumber", edtCertificationNumber.getText().toString().trim());
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);

        if (!(edtCertificationNumber.getText().toString().equals(certificationNumber))) {

            loadingUtil.dismiss("修改成功", new LoadingUtil.Success() {
                @Override
                public void dismiss() {
                    finishCur();
                }
            });
        }else {
            finishCur();
        }
    }

    //表单验证失败
    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        //sweetDialog错误对话框
        String message = ValidatorUtils.validationErrorMessage(getApplicationContext(), errors);
        displayErrorDialog(message);
    }


}
