package com.kaurihealth.kaurihealth.mine.activity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.commonlibrary.widget.util.EittextTextUtils;
import com.kaurihealth.datalib.request_bean.bean.DoctorProductPriceBean;
import com.kaurihealth.datalib.request_bean.bean.ProductDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.util.ExitApplication;
import com.kaurihealth.kaurihealth.clinical.util.ClinicalUtil;
import com.kaurihealth.kaurihealth.mine.util.PersonInfoUtil;
import com.kaurihealth.kaurihealth.util.Config;
import com.kaurihealth.kaurihealth.util.Getter;
import com.kaurihealth.kaurihealth.util.Interface.IPutter;
import com.kaurihealth.kaurihealth.util.Interface.IGetter;
import com.kaurihealth.kaurihealth.util.Putter;
import com.rey.material.widget.Switch;
import com.youyou.zllibrary.util.CommonActivity;

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

/**
 * 远程咨询服务
 */
public class RemoteConsultSettingActivity extends CommonActivity {
    @Bind(R.id.switch1)
    Switch switch1;
    @Bind(R.id.edtMoney)
    EditText edtMoney;

    private PersonInfoUtil personInfoUtil;
    private ProductDisplayBean productBean;
    private DoctorProductPriceBean doctorProductPriceBean;
    private IGetter getter;
    private IPutter putter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_doctor_service_setting);
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
        EittextTextUtils.registTextwatch(edtMoney);
        setBack(R.id.iv_back);
        getter = Getter.getInstance(getApplicationContext());
        putter = new Putter(getApplicationContext());
        personInfoUtil = PersonInfoUtil.getInstance(this, Config.Role.Doctor);
        int doctorId = getter.getMyself().doctorId;
        productBean = getter.getRemoteProduct();
        if (productBean != null) {
            doctorProductPriceBean = new DoctorProductPriceBean(productBean.price.priceId, productBean.productId, doctorId, productBean.description, productBean.price.isAvailable, productBean.price.unitPrice);
            switch1.setChecked(productBean.price.isAvailable);
            edtMoney.setText(String.format("%.2f", productBean.price.unitPrice));
        }
    }

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
        personInfoUtil.DoctorSetting(doctorProductPriceBean, new PersonInfoUtil.UpdataListener() {
            @Override
            public void success() {
                productBean.price.isAvailable = doctorProductPriceBean.IsAvailable;
                //保存两位小数
                productBean.price.unitPrice = doctorProductPriceBean.getUnitPrice();
                putter.setRemoteProduct(productBean.price.isAvailable, productBean.price.unitPrice);
                setResult(RESULT_OK);
                new SweetAlertDialog(RemoteConsultSettingActivity.this,SweetAlertDialog.SUCCESS_TYPE).setTitleText("修改成功").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        finishCur();
                    }
                }).show();

            }
        });
    }
}
