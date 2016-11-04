package com.kaurihealth.kaurihealth.mine_v.service;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.request_bean.bean.NewPriceBean;
import com.kaurihealth.datalib.request_bean.builder.NewPriceBeanBuilder;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.PriceDisplayBean;
import com.kaurihealth.datalib.response_bean.ProductDisplayBean;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.mvplib.mine_p.IPersonalDoctorServiceSettingView;
import com.kaurihealth.mvplib.mine_p.PersonalDoctorServiceSettingPresenter;
import com.kaurihealth.utilslib.ValidatorUtils;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.DecimalMin;
import com.mobsandgeeks.saripaar.annotation.Pattern;
import com.rey.material.widget.Switch;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * 我的-->服务项目条目-->点击"远程咨询服务"条目-->远程医疗咨询Activity  Created By Garnet_Wu
 */
public class RemoteDoctorServiceSettingActivity extends BaseActivity implements IPersonalDoctorServiceSettingView, Validator.ValidationListener, Switch.OnCheckedChangeListener {
    //关闭打开按钮
    @Bind(R.id.switch1)
    Switch switch1;

    //"请输入金额"编辑框
    @Bind(R.id.edt_money)
    @DecimalMin(value = 0.01, message = "价格不能小于0.01")
    @Pattern(regex = "^((\\d{1,7}\\.\\d{1,2})|(\\d{1,7}))$", message = "价格小数点前最多7位，小数点后最多2位")
    EditText edtMoney;

    @Bind(R.id.tv_more)
    TextView tvMore;
    @Bind(R.id.rl_yuan)
    RelativeLayout rlYuan;

    @Inject
    PersonalDoctorServiceSettingPresenter<IPersonalDoctorServiceSettingView> mPresenter;

    private Validator mValidator;
    private int mProductId;//标记操作指向，插入或更新

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_remote_doctor_service_setting;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
        switch1.setOnCheckedChangeListener(this);
        renderView();
    }

    @Override
    protected void initDelayedData() {
        initNewBackBtn(getString(R.string.remote_doctor_service));
        tvMore.setText(getString(R.string.title_save));

        mValidator = new Validator(this);
        mValidator.setValidationListener(this);
    }

    private void renderView() {
        DoctorDisplayBean myself = LocalData.getLocalData().getMyself();
        ArrayList<ProductDisplayBean> products = myself.getProducts();
        if (products != null && products.size() > 0) {
            for (ProductDisplayBean product : products) {
                if (product.getProductName().equals(getString(R.string.remote_doctor_service))) {
                    PriceDisplayBean priceDisplayBean = product.getPrice();
                    if (priceDisplayBean != null) {
                        mProductId = priceDisplayBean.getProductId();
                        setSwitch1(priceDisplayBean.isAvailable());
                        edtMoney.setText(String.format("%.2f", priceDisplayBean.getUnitPrice()));
                        setSelection(edtMoney);
                    } else {
                        mProductId = product.getProductId();
                        setSwitch1(false);
                        edtMoney.setText("0.01");
                        setSelection(edtMoney);
                    }
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribe();
    }

    @Override
    public void onCheckedChanged(Switch view, boolean checked) {
        rlYuan.setVisibility(checked ? View.VISIBLE : View.INVISIBLE);
    }

    //点击保存按钮
    @OnClick(R.id.tv_more)
    public void onClick() {
        mValidator.validate();
    }

    /**
     * ----------------------------继承基础mvpView方法-----------------------------------
     */

    @Override
    public void switchPageUI(String className) {
        Intent intent = getIntent();  //初始化intent
        setResult(RESULT_OK, intent);
        finishCur();
    }

    @Override
    public void onValidationSucceeded() {
        mPresenter.updatePrice();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        String message = ValidatorUtils.validationErrorMessage(getApplicationContext(), errors);
        displayErrorDialog(message);
    }

    @Override
    public PriceDisplayBean getPriceDisplayBean() {
        DoctorDisplayBean bean = (DoctorDisplayBean) LocalData.getLocalData().getMyself().clone();
        ArrayList<ProductDisplayBean> products = bean.getProducts();
        PriceDisplayBean priceDisplayBean = null;
        if (products != null && products.size() > 0) {
            for (ProductDisplayBean product : products) {
                if (product.getProductName().equals(getString(R.string.remote_doctor_service))) {
                    priceDisplayBean = product.getPrice();
                    if (priceDisplayBean == null) {
                        priceDisplayBean = new PriceDisplayBean();
                        priceDisplayBean.setProductId(2);
                    }
                    priceDisplayBean.setAvailable(getSwitch1());
                    if (getSwitch1()) {
                        priceDisplayBean.setUnitPrice(Double.valueOf(edtMoney.getText().toString().trim()));
                    }
                }
            }
        }
        return priceDisplayBean;
    }

    @Override
    public NewPriceBean createNewPriceBean() {
        int productId = mProductId;
        double unitPrice = Double.valueOf(edtMoney.getText().toString().trim());
        return new NewPriceBeanBuilder().Build(productId, unitPrice, getSwitch1());
    }

    public boolean getSwitch1() {
        return switch1.isChecked();
    }

    public void setSwitch1(boolean switch1) {
        this.switch1.setChecked(switch1);
    }
}
