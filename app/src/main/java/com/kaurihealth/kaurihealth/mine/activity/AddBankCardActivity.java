package com.kaurihealth.kaurihealth.mine.activity;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bugtags.library.Bugtags;
import com.kaurihealth.utilslib.dialog.SuccessInterfaceM;
import com.kaurihealth.datalib.request_bean.bean.NewCashOutAccountBean;
import com.kaurihealth.datalib.request_bean.bean.UserCashOutAccountDisplayBean;
import com.kaurihealth.datalib.service.IUserCashOutAccountService;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.datalib.request_bean.builder.NewCashOutAccountBeanBuilder;
import com.kaurihealth.kaurihealth.mine.bean.SelectBean;
import com.kaurihealth.kaurihealth.mine.util.ListViewDialog;
import com.kaurihealth.kaurihealth.services.IServiceFactory;
import com.kaurihealth.kaurihealth.services.ServiceFactory;
import com.kaurihealth.kaurihealth.util.Enums.LoadingStatu;
import com.kaurihealth.kaurihealth.util.Getter;
import com.kaurihealth.kaurihealth.util.Interface.IGetter;
import com.kaurihealth.kaurihealth.util.Url;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.youyou.zllibrary.util.CommonActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBankCardActivity extends CommonActivity   implements Validator.ValidationListener {
    //持卡人
    @NotEmpty(message = "持卡人姓名尚未填写")
    @Bind(R.id.edtName)
    TextView edtName;
    //卡类型
    @NotEmpty(message = "卡类型尚未填写")
    @Bind(R.id.edtBankCardType)
    TextView edtBankCardType;
    //发卡网点
    @NotEmpty(message = "发卡网点尚未填写")
    @Bind(R.id.edtPlace)
    EditText edtPlace;
    //卡号
    @NotEmpty(message = "卡号尚未填写")
    @Bind(R.id.edtBankCardCode)
    EditText edtBankCardCode;
    private AlertDialog hintDialog;
    private IGetter getter;
    private IUserCashOutAccountService iUserCashOutAccountService;
    private Dialog cardTypesDialog;
    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_bank_card);
        ButterKnife.bind(this);
        init();
    }
    @OnClick({R.id.ivHint, R.id.tv_operate, R.id.edtBankCardType})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivHint:
                hintDialog.show();
                break;
            case R.id.tv_operate:
                validator.validate();
                break;
            case R.id.edtBankCardType:
                cardTypesDialog.show();
                break;
        }
    }
    @Override
    public void init() {
        super.init();
        setBack(R.id.iv_back);
        AlertDialog.Builder hintBuilder = new AlertDialog.Builder(this);
        View hintView = LayoutInflater.from(this).inflate(R.layout.add_bankcard_dialoginfo, null);
        View tvKnow = hintView.findViewById(R.id.tvKnow);
        tvKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hintDialog.dismiss();
            }
        });
        hintBuilder.setView(hintView);
        hintDialog = hintBuilder.create();

        hintDialog.setContentView(hintView);

        hintDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getter = Getter.getInstance(getApplicationContext());
        IServiceFactory serviceFactory = new ServiceFactory(Url.prefix,getApplicationContext());
        iUserCashOutAccountService = serviceFactory.getIUserCashOutAccountService();
        edtName.setText(getter.getFullName());
        cardTypesDialog = new ListViewDialog.
                Builder(this).
                setItems(R.array.paychannels).
                setOnIteamClickListener(new SuccessInterfaceM<SelectBean<String>>() {
                    @Override
                    public void success(SelectBean<String> stringSelectBean) {
                        edtBankCardType.setText(stringSelectBean.t);
                    }
                }).create();
        validator = new Validator(this);
        validator.setValidationListener(this);
    }
    //添加银行卡操作
    private void addBankCard(IUserCashOutAccountService iUserCashOutAccountService) {
        NewCashOutAccountBean cashCard = new NewCashOutAccountBeanBuilder().
                Build(getter.getNationalIdentity(),
                        getTvValue(edtBankCardType),
                        getTvValue(edtPlace) ,
                        getTvValue(edtName) ,
                        getTvValue(edtBankCardCode));
        Call<UserCashOutAccountDisplayBean> responseDisplayBeanCall = iUserCashOutAccountService.InsertUserCashOutAccount(cashCard);
        responseDisplayBeanCall.enqueue(new Callback<UserCashOutAccountDisplayBean>() {
            @Override
            public void onResponse(Call<UserCashOutAccountDisplayBean> call, Response<UserCashOutAccountDisplayBean> response) {
                if (response.isSuccessful()) {
                    setResult(Activity.RESULT_OK);
                    new SweetAlertDialog(AddBankCardActivity.this,SweetAlertDialog.SUCCESS_TYPE).setTitleText("添加银行卡成功").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            finishCur();
                        }
                    }).show();
                } else {
                    new SweetAlertDialog(AddBankCardActivity.this,SweetAlertDialog.ERROR_TYPE).setTitleText("添加银行卡失败").show();
                }
            }

            @Override
            public void onFailure(Call<UserCashOutAccountDisplayBean> call, Throwable t) {
                Bugtags.sendException(t);
                showSnackBar(LoadingStatu.NetError.value);
            }
        });
    }
    //表单验证成功
    @Override
    public void onValidationSucceeded() {
        addBankCard(iUserCashOutAccountService);
    }

   //表单验证失败
    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        showValidationMessage(errors);
    }
}
