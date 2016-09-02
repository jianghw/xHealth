package com.kaurihealth.kaurihealth.mine_v;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.NewCashOutBean;
import com.kaurihealth.datalib.request_bean.bean.UserCashOutAccountDisplayBean;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.mine.activity.AddBankCardActivity;
import com.kaurihealth.kaurihealth.mine.bean.UserCashOutAccountBean;
import com.kaurihealth.kaurihealth.mine.util.NumberPickerDialog;
import com.kaurihealth.kaurihealth.util.Getter;
import com.kaurihealth.kaurihealth.util.Interface.IGetter;
import com.kaurihealth.kaurihealth.util.NoValueException;
import com.kaurihealth.kaurihealth.util.Putter;
import com.kaurihealth.kaurihealth.util.SuccessDialog;
import com.kaurihealth.kaurihealth.util.TextWatchClearError;
import com.kaurihealth.mvplib.mine_p.IWithdrawalView;
import com.kaurihealth.mvplib.mine_p.WithdrawalPresenter;
import com.kaurihealth.utilslib.ValidatorUtils;
import com.kaurihealth.utilslib.dialog.SuccessInterfaceM;
import com.mobsandgeeks.saripaar.QuickRule;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.DecimalMin;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Pattern;
import com.youyou.zllibrary.util.SpUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * 我的Button 里面的设置   created by Nick
 */
public class WithdrawActivity extends BaseActivity implements IWithdrawalView, Validator.ValidationListener {

    @NotEmpty(message = "身份证尚未填写")
    //1.@Pattern(regex = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$",message = "请输入有效的身份证号码")
    //2.@Pattern(regex = "^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X)$",message = "请输入有效的身份证号码")
    @Pattern(regex = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{4}$", message = "请输入有效的身份证号码")
    @Bind(R.id.edtIdentityCard)
    EditText edtIdentityCard;

    //银行卡
    @NotEmpty(message = "银行卡尚未选择")
    @Bind(R.id.tvBankCard)
    TextView tvBankCard;


    //金额
    @NotEmpty(message = "请金额填写")
    @DecimalMin(value = 0.0, message = "提现金额需要大于0元")   //最小金额0.0
    @Bind(R.id.edtMoney)
    EditText edtMoney;

    //您最多可支取XX元
    @Bind(R.id.tvContentHintWithdrawCash)
    TextView tvCash;

    @Bind(R.id.ivDelete)
    ImageView ivDelete;
    private Validator validator;
    private long mExitTime = 0;
    private Dialog accountDialog;
    private final int AddBankCardActivity = 10;
    private IGetter getter;
    private Putter putter;
    private SuccessDialog successDialog;
    private double money;
    private int position_to_store;
    private UserCashOutAccountDisplayBean item;
    private SpUtil spUtil;
    private NewCashOutBean newCashOutBean;
    final SweetAlertDialog sweetAlertDialog= new SweetAlertDialog(WithdrawActivity.this,SweetAlertDialog.SUCCESS_TYPE);


    @Inject
    WithdrawalPresenter<IWithdrawalView> mPresenter;



    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_withdrawal;
    }

    @Override
    protected void initPresenterAndData(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
    }


    @Override
    protected void initDelayedView() {
        initBackBtn(R.id.iv_back);
        validator = new Validator(this);
        validator.setValidationListener(this);
        getter = Getter.getInstance(this);
        putter = new Putter(getApplicationContext());
        tvCash.setText(String.format("%.2f", getter.getAvailableCredit()));
        successDialog = new SuccessDialog(this);
        successDialog.setConfirm(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishCur();
            }
        });
        edtIdentityCard.addTextChangedListener(new TextWatchClearError(edtIdentityCard, ivDelete));

        //重新定义validator的规则
        validator.put(edtMoney, new QuickRule<EditText>() {
            @Override
            public boolean isValid(EditText view) {
                String strMoney = edtMoney.getText().toString().trim();
                if (strMoney != null && !strMoney.isEmpty()) {
                    money = Double.parseDouble(strMoney);
                    return money < getter.getAvailableCredit();
                }
                return false;
            }

            //validator新规则报错信息
            @Override
            public String getMessage(Context context) {
                return "提现金额必须小于余额";
            }
        });
        spUtil = SpUtil.getInstance(getApplicationContext());
        try {
            String keepAccountBank = spUtil.get("keepAccountBank", String.class);
            String keepAccountBankCard = spUtil.get("keepAccountBankCard", String.class);
            tvBankCard.setText(keepAccountBankCard);
            edtIdentityCard.setText(keepAccountBank);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        //拿到用户所有的提现账号
        getAllBankCard();
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
        mPresenter.unSubscribe();

    }

    /**
     * ----------------------------继承基础IMvpView方法-----------------------------------
     *
     * @param className
     */

    @Override
    public void switchPageUI(String className) {

    }

    @Override
    public void onValidationSucceeded() {
        //点击"确定"按钮之后表单验证,去访问数据
        mPresenter.startNewCashOut();


    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        String message = ValidatorUtils.validationErrorMessage(getApplicationContext(), errors);
        displayErrorDialog(message);
    }

    @Override
    public NewCashOutBean getNewCashOutBean() {
        newCashOutBean = new NewCashOutBean();
        newCashOutBean.accountId = item.userCashOutAccountId;
        newCashOutBean.amount = Double.parseDouble(edtMoney.getText().toString());
        newCashOutBean.cashOutMethod = "银行";
        newCashOutBean.cashOutMethodDetail = item.accountDetail;
        newCashOutBean.comment = "";
        return newCashOutBean;
    }



    @OnClick({R.id.tv_operate, R.id.ivDelete, R.id.tvBankCard})
    public void onClick(View view) {
        switch (view.getId()) {
            //点击“确定”按钮
            case R.id.tv_operate:
                //防止提交按钮被多次点击，而进行多次提现
                preventClickTooMuch();
                break;
            //身份证的删除按钮
            case R.id.ivDelete:
                clearTextView(edtIdentityCard);
                break;
            //提现通道： 银行卡
            case R.id.tvBankCard:
                //弹出对话框
                if (accountDialog != null) {
                    accountDialog.show();
                }
                break;
        }
    }


    //防止提交按钮被多次点击
    private void preventClickTooMuch() {
        //两次点击时间间隔2s以上才进行表单验证
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            validator.validate();     //开启表单验证
            mExitTime = System.currentTimeMillis();
        } else {
            return;
        }
    }

    //初始化 银行卡对话框
    @Override
    public void createAccountDialog(List<UserCashOutAccountDisplayBean> accountList) throws NoValueException {
        if (accountList == null) {
            accountList = new ArrayList<>();
        }
        final UserCashOutAccountBean UserCashOutAccountDisplayBeanWrap = new UserCashOutAccountBean(accountList);
        UserCashOutAccountDisplayBeanWrap.setEnd("使用新卡提现");
        if (UserCashOutAccountDisplayBeanWrap.getAllIteamShow().size() >= 0) {
            //再次提现展示银行卡信息
            item = UserCashOutAccountDisplayBeanWrap.getIteam(position_to_store);
        }
        NumberPickerDialog.Builder dialogBuilder = new NumberPickerDialog.
                Builder(this).
                setItems(UserCashOutAccountDisplayBeanWrap.getAllIteamShow()).setPositiveButton(new SuccessInterfaceM<Integer>() {
            @Override
            public void success(Integer position) {
                if (UserCashOutAccountDisplayBeanWrap.isEnd(position)) {
                    addNewCard();
                } else {
                    try {
                        item = UserCashOutAccountDisplayBeanWrap.getIteam(position);
                        position_to_store = position;
                        tvBankCard.setText(UserCashOutAccountDisplayBeanWrap.getIteamShow(position));
                        String nationalId = UserCashOutAccountDisplayBeanWrap.getIteam(position).nationalId;
                        if (!(TextUtils.isEmpty(nationalId))) {
                            edtIdentityCard.setText(nationalId);
                        }
                    } catch (NoValueException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        //提现通道：选择银行卡对话框初始化
        accountDialog = dialogBuilder.create();
    }

    //
    public void getAllBankCard() {
        //获取用户所有的提现账号
        mPresenter.onSubscribe();
    }

    //跳转至"添加银行卡"界面
    private void addNewCard() {
        skipToForResult(AddBankCardActivity.class, null, AddBankCardActivity);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case AddBankCardActivity:
                if (resultCode == Activity.RESULT_OK) {
                    getAllBankCard();
                }
                break;
        }
    }


    //获得Handler对象
    @Override
    public void getHandlerInstance() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                spUtil.put("keepAccountBank", edtIdentityCard.getText().toString());
                spUtil.put("keepAccountBankCard", tvBankCard.getText().toString());
                tvCash.setText(String.format("%.2f", getter.getAvailableCredit()));
                sweetAlertDialog.dismiss();
                finishCur();
            }
        },1000);
    }


}
