package com.kaurihealth.kaurihealth.mine_v.account;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.request_bean.bean.NewCashOutAccountBean;
import com.kaurihealth.datalib.request_bean.builder.NewCashOutAccountBeanBuilder;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.UserCashOutAccountDisplayBean;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.mvplib.mine_p.AddBankCardPresenter;
import com.kaurihealth.mvplib.mine_p.IAddBankCardView;
import com.kaurihealth.utilslib.CheckUtils;
import com.kaurihealth.utilslib.ValidatorUtils;
import com.kaurihealth.utilslib.dialog.DialogUtils;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by mip on 2016/9/14.
 * <p/>
 * Describe:
 */
public class AddBankCardActivity extends BaseActivity implements IAddBankCardView, Validator.ValidationListener {
    //持卡人
    @NotEmpty(message = "持卡人姓名尚未填写")
    @Bind(R.id.tv_name)
    TextView edtName;

    //卡类型
    @NotEmpty(message = "卡类型尚未填写")
    @Bind(R.id.tv_bankCardType)
    TextView tvBankCardType;

    //发卡网点
    @NotEmpty(message = "发卡网点尚未填写")
    @Bind(R.id.edt_place)
    EditText edtPlace;
    //卡号
    @NotEmpty(message = "卡号尚未填写")
    @Length(min = 10, max = 20, message = "请填写正确的银行卡号")
    @Bind(R.id.edt_bankCardCode)
    EditText edtBankCardCode;

    @Bind(R.id.tv_more)
    TextView tvMore;

    @Bind(R.id.ivHint)
    ImageView ivHint;

    private Validator validator;

    @Inject
    AddBankCardPresenter<IAddBankCardView> mPresenter;
    private String identityCard;//身份证

    public static final String KEY_USER_CASH = "USER_CASH";

    @Override
    protected int getActivityLayoutID() {
        return R.layout.add_bank_card;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        DoctorDisplayBean myself = LocalData.getLocalData().getMyself();
        setEdtName(myself.fullName);

        identityCard = getBundle().getString(WithdrawActivity.KEY_IDENTITY_CARD);
    }

    @Override
    protected void initDelayedData() {
        initNewBackBtn(getString(R.string.add_bank_card));
        tvMore.setText(getString(R.string.title_save));

        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    /**
     * `````````````````````点击事件`````````````````````
     */
    @OnClick({R.id.ivHint, R.id.tv_more, R.id.tv_bankCardType})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivHint:
                createAlertDialog();
                break;
            case R.id.tv_more://表单验证
                validator.validate();
                break;
            case R.id.tv_bankCardType:
                initCardTypesDialog();
                break;
            default:
                break;
        }
    }

    private void createAlertDialog() {
        AlertDialog.Builder hintBuilder = new AlertDialog.Builder(this);
        View hintView = LayoutInflater.from(this).inflate(R.layout.add_bankcard_dialoginfo, null);
        View tvKnow = hintView.findViewById(R.id.tvKnow);
        AlertDialog hintDialog = hintBuilder.create();
//        hintDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        hintDialog.setView(hintView);
        hintDialog.show();

        tvKnow.setOnClickListener(view -> hintDialog.dismiss());
    }

    private void initCardTypesDialog() {
        String[] strings = getResources().getStringArray(R.array.paychannels);
        DialogUtils.showStringDialog(this, strings, index -> setTvBankCardType(strings[index]));
    }

    //表单验证成功
    @Override
    public void onValidationSucceeded() {
        mPresenter.onSubscribe();
    }

    //表单验证失败
    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        String message = ValidatorUtils.validationErrorMessage(getApplicationContext(), errors);
        displayErrorDialog(message);
    }

    /**
     * 得到NewCashOutAccountBean
     */
    @Override
    public NewCashOutAccountBean getNewCashOutAccountBean() {
        DoctorDisplayBean bean = LocalData.getLocalData().getMyself();
        NewCashOutAccountBean newCashOutAccountBean = new NewCashOutAccountBeanBuilder().Build(
                TextUtils.isEmpty(identityCard) ? bean.getNationalIdentity() : identityCard,//身份证
                getTvBankCardType(),//账号类别
                getEdtPlace(),//账号出处（如开户行）
                getEdtName(),
                getEdtBankCardCode()//卡号
        );
        return CheckUtils.checkNullArgument(newCashOutAccountBean, "cashCard must be not null");//判断其不为空。
    }

    @Override
    public void insertCashSucceed(UserCashOutAccountDisplayBean userCashOutAccountDisplayBean) {
        Intent intent = getIntent();  //初始化intent
        intent.putExtra(KEY_USER_CASH, userCashOutAccountDisplayBean);
        setResult(RESULT_OK, intent);
        switchPageUI("添加银行卡成功");
    }

    /**
     * 添加银行卡成功后的跳转
     */
    @Override
    public void switchPageUI(String successString) {
        showToast(successString);
        finishCur();
    }

    public String getEdtName() {
        return edtName.getText().toString().trim();
    }

    public void setEdtName(String edtName) {
        this.edtName.setText(edtName);
    }

    public String getTvBankCardType() {
        return tvBankCardType.getText().toString().trim();
    }

    public void setTvBankCardType(String tvBankCardType) {
        this.tvBankCardType.setText(tvBankCardType);
    }

    public String getEdtPlace() {
        return edtPlace.getText().toString().trim();
    }

    public void setEdtPlace(String edtPlace) {
        this.edtPlace.setText(edtPlace);
    }

    public String getEdtBankCardCode() {
        return edtBankCardCode.getText().toString().trim();
    }

    public void setEdtBankCardCode(String edtBankCardCode) {
        this.edtBankCardCode.setText(edtBankCardCode);
    }
}
