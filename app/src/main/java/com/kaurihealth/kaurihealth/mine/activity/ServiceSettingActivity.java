package com.kaurihealth.kaurihealth.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.commonlibrary.widget.util.LogFactory;
import com.example.commonlibrary.widget.util.LogUtilInterface;
import com.kaurihealth.datalib.request_bean.bean.ProductDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.util.ExitApplication;
import com.kaurihealth.kaurihealth.mine.util.PersoninfoConfig;
import com.kaurihealth.kaurihealth.util.Getter;
import com.kaurihealth.kaurihealth.util.Interface.IGetter;
import com.youyou.zllibrary.util.CommonActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 版权:    张磊
 * 作者:    张磊
 * 描述：
 * 修订日期:
 */
public class ServiceSettingActivity extends CommonActivity {
    private final String OffDefault = "未开通";
    @Bind(R.id.tvExclusiveDoctor)
    TextView tvExclusiveDoctor;
    @Bind(R.id.tvRemoteConsult)
    TextView tvRemoteConsult;
    ProductDisplayBean productExclusiveDoctor;
    ProductDisplayBean productRemoteConsultSetting;
    private LogUtilInterface logUtil;
    private IGetter getter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_setting);
        ButterKnife.bind(this);
        logUtil = LogFactory.getSimpleLog(getClass().getName());
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
        getter = Getter.getInstance(getApplicationContext());
        setBack(R.id.iv_back);
        getServiceSettingInfo();
    }

    @OnClick({R.id.rlayExclusiveDoctor, R.id.rlayRemoteConsultSetting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlayExclusiveDoctor:
                ExclusiveDoctor();
                break;
            case R.id.rlayRemoteConsultSetting:
                RemoteConsult();
                break;
        }
    }

    /***
     * 获取服务信息
     */
    private void getServiceSettingInfo() {
        for (ProductDisplayBean iteam : getter.getMyself().products) {
            if (iteam.productName.equals(PersoninfoConfig.ExclusiveDoctor)) {
                if (iteam.price.isAvailable) {
                    setPrice(tvExclusiveDoctor, iteam.price.unitPrice, "元/月");
                } else {
                    tvExclusiveDoctor.setText(OffDefault);
                }
                productExclusiveDoctor = iteam;
            } else if (iteam.productName.equals(PersoninfoConfig.RemoteDoctor)) {
                productRemoteConsultSetting = iteam;
                if (iteam.price.isAvailable) {
                    setPrice(tvRemoteConsult, iteam.price.unitPrice, "元/次");
                } else {
                    tvRemoteConsult.setText(OffDefault);
                }
            }
        }
    }

    private final int RemoteConsult = 3;
    private final int ExclusiveDoctor = 4;

    /**
     * 远程医疗咨询设置
     */
    private void RemoteConsult() {
        skipToForResult(RemoteConsultSettingActivity.class, null, RemoteConsult);
    }

    /**
     * 设置服务价格
     */
    private void setPrice(TextView tvPriceShow, double price, String unit) {
        tvPriceShow.setText(String.format("%.2f" + unit, price));
    }

    /***
     * 专属医生服务设置
     */
    private void ExclusiveDoctor() {
        skipToForResult(ExclusiveDoctorSettingActivity.class, null, ExclusiveDoctor);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RemoteConsult:
                if (resultCode == RESULT_OK) {
                    for (ProductDisplayBean iteam : getter.getMyself().products) {
                        if (iteam.productName.equals(PersoninfoConfig.RemoteDoctor)) {
                            productRemoteConsultSetting = iteam;
                            if (productRemoteConsultSetting.price.isAvailable) {
                                setPrice(tvRemoteConsult, productRemoteConsultSetting.price.unitPrice, "元/次");
                            } else {
                                tvRemoteConsult.setText(OffDefault);
                            }
                        }
                    }
                }
                break;
            case ExclusiveDoctor:
                if (resultCode == RESULT_OK) {
                    for (ProductDisplayBean item : getter.getMyself().products) {
                        if (item.productName.equals(PersoninfoConfig.ExclusiveDoctor)) {
                            productExclusiveDoctor = item;
                            if (productExclusiveDoctor.price.isAvailable) {
                                logUtil.i(productExclusiveDoctor.price + "");
                                setPrice(tvExclusiveDoctor, productExclusiveDoctor.price.unitPrice, "元/月");
                            } else {
                                tvExclusiveDoctor.setText(OffDefault);
                            }
                        }
                    }

                }
                break;
        }
    }
}
