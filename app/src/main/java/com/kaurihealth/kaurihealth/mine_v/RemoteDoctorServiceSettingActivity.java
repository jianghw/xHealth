package com.kaurihealth.kaurihealth.mine_v;

import android.os.Bundle;
import android.widget.EditText;

import com.example.commonlibrary.widget.util.EittextTextUtils;
import com.kaurihealth.datalib.request_bean.bean.DoctorProductPriceBean;
import com.kaurihealth.datalib.request_bean.bean.ProductDisplayBean;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.clinical.util.ClinicalUtil;
import com.kaurihealth.kaurihealth.mine.util.PersonInfoUtil;
import com.kaurihealth.kaurihealth.util.Config;
import com.kaurihealth.kaurihealth.util.EdtUtil;
import com.kaurihealth.kaurihealth.util.ExitApplication;
import com.kaurihealth.kaurihealth.util.Getter;
import com.kaurihealth.kaurihealth.util.Interface.IGetter;
import com.kaurihealth.kaurihealth.util.Putter;
import com.kaurihealth.mvplib.mine_p.IRemoteDoctorServiceSettingView;
import com.kaurihealth.mvplib.mine_p.RemoteDoctorServiceSettingPresenter;
import com.rey.material.widget.Switch;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 *  我的-->服务项目条目-->点击"远程咨询服务"条目-->远程医疗咨询Activity
 */
public class RemoteDoctorServiceSettingActivity extends BaseActivity implements IRemoteDoctorServiceSettingView {

    @Bind(R.id.switch1)
    Switch switch1;
    @Bind(R.id.edtMoney)
    EditText edtMoney;

    @Inject
    RemoteDoctorServiceSettingPresenter<IRemoteDoctorServiceSettingView> mPresenter;
    private IGetter getter;
    private Putter putter;
    private PersonInfoUtil personInfoUtil;
    private ProductDisplayBean productBean;
    private int doctorId;
    private DoctorProductPriceBean doctorProductPriceBean;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_remote_doctor_service_setting;
    }

    @Override
    protected void initPresenterAndData(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
        ExitApplication.getInstance().addActivity(this);
    }

    @Override
    protected void initDelayedView() {
        initBackBtn(R.id.iv_back);
        init();
    }

    //初始化对象
    private void init() {
        EittextTextUtils.registTextwatch(edtMoney);
        getter = Getter.getInstance(getApplicationContext());
        putter = new Putter(getApplicationContext());
        personInfoUtil = PersonInfoUtil.getInstance(this, Config.Role.Doctor);
        doctorId = getter.getMyself().doctorId;
        productBean = getter.getRemoteProduct();
        if (productBean!=null) {
            doctorProductPriceBean = getDoctorProductPriceBean();
            switch1.setChecked(productBean.price.isAvailable);
            edtMoney.setText(String.format("%.2f",productBean.price.unitPrice));
        }
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

    }


    //得到医生远程咨询的bean
    @Override
    public DoctorProductPriceBean getDoctorProductPriceBean() {
        DoctorProductPriceBean doctorProductPriceBean = new DoctorProductPriceBean(productBean.price.priceId, productBean.productId, doctorId, productBean.description, productBean.price.isAvailable, productBean.price.unitPrice);
        return doctorProductPriceBean;
    }

    //点击"保存" 按钮
    @OnClick(R.id.tv_operate)
    public void onClick() {
        doctorProductPriceBean.setIsAvailable(switch1.isChecked());
        String strShow = edtMoney.getText().toString();
        if (switch1.isChecked()) {
            if (new ClinicalUtil().getToBoolean(strShow)) {
                new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("错误提示").setContentText("金额不能小于等于0元").show();
            } else {
                doctorProductPriceBean.setUnitPrice(Double.valueOf(strShow.trim()));
                store();
            }
        } else {
            if (strShow.length() == 0) {
                doctorProductPriceBean.setUnitPrice(0);
            } else {
                doctorProductPriceBean.setUnitPrice(Double.valueOf(strShow.trim()));
            }
            store();
        }
    }

    /**
     * 保存界面数据
     */
    private void store() {
        personInfoUtil.DoctorSetting(doctorProductPriceBean,new PersonInfoUtil.UpdataListener(){
            @Override
            public void success() {
                productBean.price.isAvailable = doctorProductPriceBean.IsAvailable;
                  //保存两位小数
                productBean.price.unitPrice = doctorProductPriceBean.getUnitPrice();
                putter.setRemoteProduct(productBean.price.isAvailable,productBean.price.unitPrice);
                setResult(RESULT_OK);
                new SweetAlertDialog(RemoteDoctorServiceSettingActivity.this,SweetAlertDialog.SUCCESS_TYPE).setTitleText("修改成功").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        finishCur();
                    }
                }).show();

            }
        });
    }
}
