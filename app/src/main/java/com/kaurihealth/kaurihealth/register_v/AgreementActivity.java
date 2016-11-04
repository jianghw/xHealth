package com.kaurihealth.kaurihealth.register_v;

import android.os.Bundle;
import android.view.View;

import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;

import butterknife.OnClick;

/**
 * 描述：协议
 * 修订日期:
 */
public class AgreementActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_agreement;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {

    }

    @Override
    protected void initDelayedData() {

    }

    @OnClick({R.id.iv_cancle, R.id.tv_agree})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_cancle:
                agree();
                break;
            case R.id.tv_agree:
                agree();
                break;
        }
    }

    public void agree() {
        setResult(RESULT_OK);
        finishCur();
    }
}
