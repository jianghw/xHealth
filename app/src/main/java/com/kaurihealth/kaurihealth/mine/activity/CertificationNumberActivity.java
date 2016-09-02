package com.kaurihealth.kaurihealth.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.commonlibrary.widget.util.LoadingUtil;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.mine.util.PersonInfoUtil;
import com.kaurihealth.kaurihealth.util.Config;
import com.kaurihealth.kaurihealth.util.ExitApplication;
import com.kaurihealth.kaurihealth.util.TextWatchClearError;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Pattern;
import com.youyou.zllibrary.util.CommonActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 版权:    张磊
 * 作者:    张磊
 * 描述：
 * 修订日期:
 */
public class CertificationNumberActivity extends CommonActivity implements Validator.ValidationListener {
   //请输入职业证号编辑框
    @NotEmpty(message = "职业编号不能为空")
    @Pattern(sequence = 2, regex = "^\\d{15}$", message = "请输入15位正确的职业编号")
    @Bind(R.id.edtCertificationNumber)
    EditText edtCertificationNumber;

    //职业证号编辑框里的删除按钮
    @Bind(R.id.ivDelete)
    ImageView ivDelete;

    private PersonInfoUtil personInfoUtil;
    private Validator validator;
    private LoadingUtil loadingUtil;
    private String certificationNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_certification_number);
        ButterKnife.bind(this);
        init();
        ExitApplication.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void init() {
        super.init();
        loadingUtil = LoadingUtil.getInstance(CertificationNumberActivity.this);
        setBack(R.id.iv_back);
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
                validator.validate();
                break;
            case R.id.ivDelete:
                delete();
                break;
        }
    }

    /**
     * 删除
     */
    private void delete() {
        clearTextView(edtCertificationNumber);
    }

    @Override
    public void onValidationSucceeded() {
        loadingUtil.show("修改成功");
        if (personInfoUtil == null) {  //personInfoUtil???没用？？
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

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        StringBuilder prompt = new StringBuilder();
        for (ValidationError error : errors) {
            String message = error.getCollatedErrorMessage(this);
            prompt.append(message).append("\n");
        }
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText("错误提示").setContentText(prompt.toString()).show();
    }
}
