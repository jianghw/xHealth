package com.kaurihealth.kaurihealth.mine_v.service;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.ProductDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * Created By Garnet_Wu
 * 描述: 我的 --> 服务项目
 */
public class ServiceSettingActivity extends BaseActivity {
    @Bind(R.id.tvExclusiveDoctor)
    TextView tvExclusiveDoctor;
    @Bind(R.id.tvRemoteConsult)
    TextView tvRemoteConsult;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_service_setting;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        renderView();
    }

    @Override
    protected void initDelayedData() {
        initNewBackBtn(getString(R.string.service_title));
    }

    private void renderView() {
        DoctorDisplayBean myself = LocalData.getLocalData().getMyself();
        List<ProductDisplayBean> products = new ArrayList<>();
        products.addAll(myself.getProducts());

        for (ProductDisplayBean product : products) {
            if (product.getProductName().equals(getString(R.string.personal_doctor_service))) {
                if (product.getPrice() != null && product.getPrice().isAvailable()) {
                    tvExclusiveDoctor.setText(String.format("%.2f" + "元/月", product.getPrice().getUnitPrice()));
                } else {
                    tvExclusiveDoctor.setText(getString(R.string.default_service_price));
                }
            } else if (product.getProductName().equals(getString(R.string.remote_doctor_service))) {
                if (product.getPrice() != null && product.getPrice().isAvailable()) {
                    tvRemoteConsult.setText(String.format("%.2f" + "元/次", product.getPrice().getUnitPrice()));
                } else {
                    tvRemoteConsult.setText(getString(R.string.default_service_price));
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.rlayExclusiveDoctor, R.id.rlayRemoteConsultSetting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlayExclusiveDoctor:
                skipToForResult(PersonalDoctorServiceSettingActivity.class, null, 0);
                break;
            case R.id.rlayRemoteConsultSetting:
                skipToForResult(RemoteDoctorServiceSettingActivity.class, null, 1);
                break;
            default:
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //有做修改时调用
        renderView();
        Intent intent = getIntent();  //初始化intent
        setResult(RESULT_OK, intent);
    }
}
