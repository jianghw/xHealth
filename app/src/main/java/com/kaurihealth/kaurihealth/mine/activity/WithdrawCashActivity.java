package com.kaurihealth.kaurihealth.mine.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bugtags.library.Bugtags;
import com.kaurihealth.datalib.request_bean.bean.NewCashOutBean;
import com.kaurihealth.datalib.request_bean.bean.UserCashOutAccountDisplayBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;
import com.kaurihealth.datalib.service.IUserCashOutAccountService;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.mine.bean.UserCashOutAccountBean;
import com.kaurihealth.kaurihealth.mine.util.NumberPickerDialog;
import com.kaurihealth.kaurihealth.services.IServiceFactory;
import com.kaurihealth.kaurihealth.services.ServiceFactory;
import com.kaurihealth.kaurihealth.util.Enums.LoadingStatu;
import com.kaurihealth.kaurihealth.util.Getter;
import com.kaurihealth.kaurihealth.util.Interface.IGetter;
import com.kaurihealth.kaurihealth.util.Interface.IPutter;
import com.kaurihealth.kaurihealth.util.NoValueException;
import com.kaurihealth.kaurihealth.util.Putter;
import com.kaurihealth.kaurihealth.util.SuccessDialog;
import com.kaurihealth.kaurihealth.util.TextWatchClearError;
import com.kaurihealth.kaurihealth.util.Url;
import com.kaurihealth.utilslib.dialog.SuccessInterfaceM;
import com.mobsandgeeks.saripaar.QuickRule;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.DecimalMin;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Pattern;
import com.youyou.zllibrary.util.CommonActivity;
import com.youyou.zllibrary.util.SpUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WithdrawCashActivity extends CommonActivity implements Validator.ValidationListener {
    @NotEmpty(message = "身份证尚未填写")
    //1.@Pattern(regex = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$",message = "请输入有效的身份证号码")
    //2.@Pattern(regex = "^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X)$",message = "请输入有效的身份证号码")
    @Pattern(regex = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{4}$",message = "请输入有效的身份证号码")
    @Bind(R.id.edtIdentityCard)
    EditText edtIdentityCard;

    //银行卡
    @NotEmpty(message = "银行卡尚未选择")
    @Bind(R.id.tvBankCard)
    TextView tvBankCard;


    //金额
    @NotEmpty(message = "请金额填写")
    @DecimalMin(value = 0.0 ,message = "提现金额需要大于0元")   //最小金额0.0
    @Bind(R.id.edtMoney)
    EditText edtMoney;

    @Bind(R.id.tvContentHintWithdrawCash)
    TextView tvCash;

    @Bind(R.id.ivDelete)
    ImageView ivDelete;

    private IUserCashOutAccountService userCashOutAccountService;
    private IGetter getter;
    private Dialog accountDialog;

    private final int AddBankCardActivity = 10;
    private IPutter putter;
    private NewCashOutBean newCashOutBean;
    private SuccessDialog successDialog;
    private UserCashOutAccountDisplayBean item;
    private Validator validator;
    private SpUtil spUtil;
    private double money ;
    private long mExitTime = 0;
    private int position_to_store;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal);
        ButterKnife.bind(this);

        init();
    }

    @OnClick({R.id.tv_operate, R.id.ivDelete, R.id.tvBankCard})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_operate:
               // confirm();
                //表单验证
                //防止按钮被点击两次
                yeladey();

                break;
            case R.id.ivDelete:
                clearTextView(edtIdentityCard);
                break;
            case R.id.tvBankCard:
                if (accountDialog != null) {
                    accountDialog.show();
                }
                break;
        }
    }


    //防止多次连续点击多次提交按钮
    private void yeladey(){
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            validator.validate();
            mExitTime = System.currentTimeMillis();
        }else {
            return;
        }
    }

    @Override
    public void init() {
        super.init();
        setBack(R.id.iv_back);
        getter = Getter.getInstance(this);
        putter = new Putter(getApplicationContext());

        tvCash.setText(String.format("%.2f", getter.getAvailableCredit()));
        IServiceFactory iServiceFactory = new ServiceFactory(Url.prefix, getApplicationContext());
        userCashOutAccountService = iServiceFactory.getIUserCashOutAccountService();
        getAllBankCard();
        successDialog = new SuccessDialog(this);
        successDialog.setConfirm(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishCur();
            }
        });
        edtIdentityCard.addTextChangedListener(new TextWatchClearError(edtIdentityCard,ivDelete));
        validator = new Validator(this);
        validator.setValidationListener(this);

        validator.put(edtMoney, new QuickRule<EditText>() {
            @Override
            public boolean isValid(EditText view) {
                String strMoney = edtMoney.getText().toString();
                if(strMoney != null && !strMoney.isEmpty()) {
                    money = Double.parseDouble(strMoney);
                    return money < getter.getAvailableCredit();
                }
                return true;
            }

            @Override
            public String getMessage(Context context) {
                return "金额必须小于余额";
            }
        });
        spUtil = SpUtil.getInstance(getApplicationContext());
        try {
            String keepAccountBank= spUtil.get("keepAccountBank",String.class);
            String keepAccountBankCard= spUtil.get("keepAccountBankCard",String.class);
            tvBankCard.setText(keepAccountBankCard);
            edtIdentityCard.setText(keepAccountBank);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    private void createAccountDialog(List<UserCashOutAccountDisplayBean> accountList) throws NoValueException {
        if (accountList == null) {
            accountList = new ArrayList<>();
        }
        final UserCashOutAccountBean UserCashOutAccountDisplayBeanWrap = new UserCashOutAccountBean(accountList);
        UserCashOutAccountDisplayBeanWrap.setEnd("使用新卡提现");
        if(UserCashOutAccountDisplayBeanWrap.getAllIteamShow().size()>=0) {
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
                        position_to_store =position;
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

    private void getAllBankCard() {
        Call<List<UserCashOutAccountDisplayBean>> listCall = userCashOutAccountService.GetUserCashOutAccounts_out();
        listCall.enqueue(new Callback<List<UserCashOutAccountDisplayBean>>() {
            @Override
            public void onResponse(Call<List<UserCashOutAccountDisplayBean>> call, Response<List<UserCashOutAccountDisplayBean>> response) {
                if (response.isSuccessful()) {
                    List<UserCashOutAccountDisplayBean> accountList = response.body();
                    try {
                        createAccountDialog(accountList);
                    } catch (NoValueException e) {
                        e.printStackTrace();
                    }
                } else {
                    showSnackBar("获取账号信息失败", Snackbar.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<List<UserCashOutAccountDisplayBean>> call, Throwable t) {
                Bugtags.sendException(t);
                showSnackBar(LoadingStatu.NetError.value, Snackbar.LENGTH_SHORT);
            }
        });
    }



    @Override
    public void onValidationSucceeded() {
        newCashOutBean = new NewCashOutBean();
        newCashOutBean.accountId = item.userCashOutAccountId;
        newCashOutBean.amount = Double.parseDouble(edtMoney.getText().toString());
        newCashOutBean.cashOutMethod = "银行";
        newCashOutBean.cashOutMethodDetail = item.accountDetail;
        newCashOutBean.comment = "";
        //NewCashOutBean newCashOutBean = new NewCashOutBeanBuilder().Build(item.userCashOutAccountId ,Double.parseDouble(edtMoney.getText().toString()) , item.accountDetail);
        Call<ResponseDisplayBean> responseDisplayBeanCall = userCashOutAccountService.StartNewCashOut_out(newCashOutBean);
        responseDisplayBeanCall.enqueue(new Callback<ResponseDisplayBean>() {
            @Override
            public void onResponse(Call<ResponseDisplayBean> call, Response<ResponseDisplayBean> response) {
                if (response.isSuccessful()) {
                    ResponseDisplayBean responseBody = response.body();
                    if (responseBody.isIsSucess()) {
                        putter.reduceAvailableCredit(newCashOutBean.amount);
                        putter.reduceTotalCredit(newCashOutBean.amount);
//                        tvCash.setText(String.valueOf(getter.getAvailableCredit()));
                        setResult(Activity.RESULT_OK);

//                        successDialog.setMes("提现成功!");
//                        successDialog.show();

                        final SweetAlertDialog sweetAlertDialog= new SweetAlertDialog(WithdrawCashActivity.this,SweetAlertDialog.SUCCESS_TYPE);
                        sweetAlertDialog.setTitleText("您的提现请求已发送完成").show();

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

                    } else {
//                        showSnackBar(responseBody.message, Snackbar.LENGTH_LONG);
                        new SweetAlertDialog(WithdrawCashActivity.this,SweetAlertDialog.WARNING_TYPE).setTitleText("错误提示").setContentText(responseBody.getMessage()).show();
                    }
                } else {
                    showSnackBar("提现失败", Snackbar.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<ResponseDisplayBean> call, Throwable t) {
                showSnackBar(LoadingStatu.NetError.value, Snackbar.LENGTH_SHORT);
                Bugtags.sendException(t);
            }
        });

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        StringBuilder prompt = new StringBuilder();
        for (ValidationError error : errors) {
            String message = error.getCollatedErrorMessage(WithdrawCashActivity.this);
            prompt.append(message).append("\n");
        }
        new SweetAlertDialog(WithdrawCashActivity.this,SweetAlertDialog.WARNING_TYPE).setTitleText("错误提示").setContentText(prompt.toString()).show();

    }

}

