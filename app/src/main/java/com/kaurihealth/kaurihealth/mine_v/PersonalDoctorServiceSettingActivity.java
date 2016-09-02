package com.kaurihealth.kaurihealth.mine_v;

import android.os.Bundle;
import android.widget.EditText;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.request_bean.bean.NewPriceBean;
import com.kaurihealth.datalib.request_bean.bean.PriceDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.ProductDisplayBean;
import com.kaurihealth.datalib.request_bean.builder.NewPriceBeanBuilder;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.mvplib.mine_p.IPersonalDoctorServiceSettingView;
import com.kaurihealth.mvplib.mine_p.PersonalDoctorServiceSettingPresenter;
import com.kaurihealth.utilslib.ValidatorUtils;
import com.kaurihealth.utilslib.constant.ProductName;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.DecimalMin;
import com.rey.material.widget.Switch;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * 描述: 专属医生服务
 */
public class PersonalDoctorServiceSettingActivity extends BaseActivity implements IPersonalDoctorServiceSettingView,Validator.ValidationListener {

    //关闭打开按钮
    @Bind(R.id.switch1)
    Switch switch1;

    //"请输入金额"编辑框
    @Bind(R.id.edtPrice)
    @DecimalMin(value = 0.01 , messageResId = R.string.service_price)
    EditText edtPrice;

    private Validator mValidator;//调用.validate()

    @Inject
    PersonalDoctorServiceSettingPresenter<IPersonalDoctorServiceSettingView> mPresenter;

    private DoctorDisplayBean myself;
    private ProductDisplayBean productDisplayBean;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_personal_doctor_service_setting;
    }

    @Override
    protected void initPresenterAndData(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
        myself = LocalData.getLocalData().getMyself();
        for(ProductDisplayBean product: myself.products) {
            if(product.productName.equals(ProductName.PersonalDoctorService)) {
                productDisplayBean = product;
            }
        }
    }

    @Override
    protected void initDelayedView() {
        initBackBtn(R.id.iv_back);

        switch1.setChecked(productDisplayBean.price.isAvailable);
        edtPrice.setText(String.format("%.2f",productDisplayBean.price.unitPrice));

        mValidator = new Validator(this);
        mValidator.setValidationListener(this);
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

    @Override
    public void switchPageUI(String className) {
        finishCur();
    }

    //造专属医生的bean
    @Override
    public PriceDisplayBean getPriceDisplayBean() {
        productDisplayBean.price.isAvailable = switch1.isChecked();
        productDisplayBean.price.unitPrice = Double.valueOf(edtPrice.getText().toString().trim());
        return productDisplayBean.price;
    }

    @Override
    public NewPriceBean createNewPriceBean() {
        int productId = productDisplayBean.productId;
        double unitPrice = Double.valueOf(edtPrice.getText().toString().trim());
        return new NewPriceBeanBuilder().Build(productId, unitPrice);
    }


    //点击保存按钮
    @OnClick(R.id.tv_operate)
    public void onClick() {
        mValidator.validate();
    }

    @Override
    public void onValidationSucceeded() {
        if(productDisplayBean.price != null){
            mPresenter.updatePrice();
        }else {
            mPresenter.insertPrice();
        }
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        String message = ValidatorUtils.validationErrorMessage(getApplicationContext(), errors);
        displayErrorDialog(message);
    }
}
