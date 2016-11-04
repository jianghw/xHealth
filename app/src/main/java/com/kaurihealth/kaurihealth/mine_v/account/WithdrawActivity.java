package com.kaurihealth.kaurihealth.mine_v.account;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.request_bean.bean.NewCashOutBean;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.UserCashOutAccountDisplayBean;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.mvplib.mine_p.IWithdrawalView;
import com.kaurihealth.mvplib.mine_p.WithdrawalPresenter;
import com.kaurihealth.utilslib.OnClickUtils;
import com.kaurihealth.utilslib.RegularUtils;
import com.kaurihealth.utilslib.SharedUtils;
import com.kaurihealth.utilslib.ValidatorUtils;
import com.kaurihealth.utilslib.dialog.DialogUtils;
import com.mobsandgeeks.saripaar.QuickRule;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.DecimalMin;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * 我的Button 里面的设置   created by Nick
 */
public class WithdrawActivity extends BaseActivity implements IWithdrawalView, Validator.ValidationListener {
    @Inject
    WithdrawalPresenter<IWithdrawalView> mPresenter;

    //您最多可支取XX元
    @Bind(R.id.tvContentHintWithdrawCash)
    TextView tvCash;

    @NotEmpty(message = "身份证尚未填写")
    //1.@Pattern(regex = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$",message = "请输入有效的身份证号码")
    //2.@Pattern(regex = "^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X)$",message = "请输入有效的身份证号码")
    @Pattern(regex = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$", message = "请输入有效的身份证号码")
    @Bind(R.id.edt_identityCard)
    EditText edtIdentityCard;

    //银行卡
    @NotEmpty(message = "银行卡尚未选择")
    @Bind(R.id.tv_bankCard)
    TextView tvBankCard;

    //金额
    @NotEmpty(message = "请金额填写")
    @DecimalMin(value = 0.0, message = "提现金额需要大于0元")   //最小金额0.0
    @Bind(R.id.edt_money)
    EditText edtMoney;

    @Bind(R.id.tv_more)
    TextView tvMore;

    private Validator validator;

    private List<UserCashOutAccountDisplayBean> mAccountList = new ArrayList<>();
    private String[] mStrings;
    private UserCashOutAccountDisplayBean userCashOutAccountDisplayBean;//当期显示的卡对应的信息
    public static final String KEY_IDENTITY_CARD = "IDENTITY_CARD";
    private String mAccountDetail;
    private String mAccountType;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_withdrawal;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        initNewBackBtn(getString(R.string.account_title));
        tvMore.setText(getString(R.string.title_complete));

        validator = new Validator(this);
        validator.setValidationListener(this);

        //重新定义validator的规则
        validator.put(edtMoney, new QuickRule<EditText>() {
            @Override
            public boolean isValid(EditText view) {
                String strMoney = getEdtMoney();
                if (!TextUtils.isEmpty(strMoney)) {
                    double money = Double.parseDouble(strMoney);
                    double cashMoney = Double.parseDouble(getTvCash());
                    return money <= cashMoney;
                }
                return false;
            }

            //validator新规则报错信息
            @Override
            public String getMessage(Context context) {
                return "提现金额必须小于余额";
            }
        });
    }

    @Override
    protected void initDelayedData() {
        //拿到用户所有的提现账号
        mPresenter.onSubscribe();
        renderView();
    }

    private void renderView() {
        DoctorDisplayBean myself = LocalData.getLocalData().getMyself();
        double availableCredit = myself.getAvailableCredit();
        setTvCash(String.format("%.2f", availableCredit));
        setEdtIdentityCard(myself.getNationalIdentity());//身份证
        setSelection(edtIdentityCard);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribe();
        if (!mAccountList.isEmpty()) mAccountList.clear();
    }

    @OnClick({R.id.tv_more, R.id.tv_bankCard})
    public void onClick(View view) {
        if (OnClickUtils.onNoDoubleClick()) return;
        switch (view.getId()) {
            case R.id.tv_more://防止提交按钮被多次点击，而进行多次提现
                validator.validate();
                break;
            case R.id.tv_bankCard://弹出对话框 提现通道： 银行卡
                DialogUtils.showStringDialog(this, mStrings, index -> {
                    if (mStrings[index].equalsIgnoreCase("使用新卡提现")) {
                        addNewCard();
                    } else {
                        userCashOutAccountDisplayBean = mAccountList.get(index);
                        setTvBankCard(mStrings[index]);

                        if (!TextUtils.isEmpty(userCashOutAccountDisplayBean.getNationalId()))
                            setEdtIdentityCard(userCashOutAccountDisplayBean.getNationalId());
                    }
                });
                break;
            default:
                break;
        }
    }

    @Override
    public void onValidationSucceeded() {//点击"确定"按钮之后表单验证,去访问数据
        if (bankCardJudgment()) mPresenter.startNewCashOut();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        String message = ValidatorUtils.validationErrorMessage(getApplicationContext(), errors);
        displayErrorDialog(message);
    }

    //跳转至"添加银行卡"界面
    private void addNewCard() {
        if (bankCardJudgment()) {
            Bundle bundle = new Bundle();
            bundle.putString(KEY_IDENTITY_CARD, getEdtIdentityCard());
            skipToForResult(AddBankCardActivity.class, bundle, 200);
        }
    }

    private boolean bankCardJudgment() {
        String edtIdentityCard = getEdtIdentityCard();
        String identity = LocalData.getLocalData().getMyself().getNationalIdentity();
        if (TextUtils.isEmpty(edtIdentityCard)) {
            displayErrorDialog("请先填写身份证号");
            return false;
        } else if (!RegularUtils.matcherIdentityCard(edtIdentityCard)) {
            displayErrorDialog("身份证号不正确");
            return false;
        } else if (!TextUtils.isEmpty(identity) && !identity.equals(edtIdentityCard)) {
            displayErrorDialog("身份证号不与当前银行卡匹配");
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 200:
                if (resultCode == Activity.RESULT_OK) {
                    UserCashOutAccountDisplayBean userCashOutAccountDisplayBean =
                            (UserCashOutAccountDisplayBean) data.getSerializableExtra(AddBankCardActivity.KEY_USER_CASH);
                    mAccountDetail = userCashOutAccountDisplayBean.getAccountDetail();
                    mAccountType = userCashOutAccountDisplayBean.getAccountType();
                    mPresenter.loadUserCashOutAccounts(true);
                }
                break;
            default:
                break;
        }
    }

    /**
     * ----------------------------继承基础mvpView方法-----------------------------------
     */

    //初始化 银行卡对话框数据
    @Override
    public void createAccountDialog(List<UserCashOutAccountDisplayBean> accountList, String[] strings) {
        if (!mAccountList.isEmpty()) mAccountList.clear();
        mAccountList.addAll(accountList);
        mStrings = strings;
        if (!accountList.isEmpty()) {
            for (UserCashOutAccountDisplayBean bean : accountList) {
                accordingToLoadCard(bean);
            }
        }
    }

    private void accordingToLoadCard(UserCashOutAccountDisplayBean bean) {
        if (TextUtils.isEmpty(mAccountType) || TextUtils.isEmpty(mAccountDetail)) {
            if (SharedUtils.getInteger(this, "AccountId", -1) == bean.getUserCashOutAccountId()) {
                renderCardData(bean);
            }
        } else {
            if (bean.getAccountType().equals(mAccountType) && bean.getAccountDetail().equals(mAccountDetail)) {
                renderCardData(bean);
            }
        }
    }

    /**
     * 显示新添加的卡号信息
     */
    private void renderCardData(UserCashOutAccountDisplayBean userCashOutAccountDisplayBean) {
        this.userCashOutAccountDisplayBean = userCashOutAccountDisplayBean;
        try {
            String accountType = userCashOutAccountDisplayBean.getAccountType();
            String detail = userCashOutAccountDisplayBean.getAccountDetail();
            String bankCardName = String.format("%s ( %s )",
                    accountType, detail.substring(detail.length() > 4 ? detail.length() - 4 : 0, detail.length()), detail.length());
            setTvBankCard(bankCardName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(userCashOutAccountDisplayBean.getNationalId()))
            setEdtIdentityCard(userCashOutAccountDisplayBean.getNationalId());
    }

    /**
     * 提现构建bean
     */
    @Override
    public NewCashOutBean getNewCashOutBean() {
        NewCashOutBean newCashOutBean = new NewCashOutBean();
        newCashOutBean.setAccountId(userCashOutAccountDisplayBean.getUserCashOutAccountId());
        newCashOutBean.setAmount(Double.valueOf(getEdtMoney()));
        newCashOutBean.setCashOutMethod("银行");
        newCashOutBean.setCashOutMethodDetail(userCashOutAccountDisplayBean.getAccountDetail());
        return newCashOutBean;
    }

    //获得Handler对象
    @Override
    public void startNewCashSucceed() {
        SharedUtils.setInteger(this, "AccountId", userCashOutAccountDisplayBean.getUserCashOutAccountId());
        showToast("您的提现请求已发送完成!");
        Intent intent = getIntent();  //初始化intent
        setResult(RESULT_OK, intent);
        switchPageUI("MyAccountActivity");
    }

    @Override
    public void switchPageUI(String className) {
        finishCur();
    }

    public String getTvCash() {
        return tvCash.getText().toString().trim();
    }

    public void setTvCash(String tvCash) {
        this.tvCash.setText(tvCash);
    }

    public String getEdtMoney() {
        return edtMoney.getText().toString().trim();
    }

    public void setEdtMoney(String edtMoney) {
        this.edtMoney.setText(edtMoney);
    }

    public String getTvBankCard() {
        return tvBankCard.getText().toString().trim();
    }

    public void setTvBankCard(String tvBankCard) {
        this.tvBankCard.setText(tvBankCard);
    }

    public String getEdtIdentityCard() {
        return edtIdentityCard.getText().toString().trim();
    }

    public void setEdtIdentityCard(String edtIdentityCard) {
        this.edtIdentityCard.setText(edtIdentityCard);
    }

}
