package com.kaurihealth.kaurihealth.register_v;

import android.os.Bundle;
import android.view.View;

import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;

import butterknife.OnClick;

/**
 * 版权:    张磊
 * 作者:    张磊
 * 描述：
 * 修订日期:
 */
public class AgreementActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getActivityLayoutID() {
        return R.layout.agreement;
    }

    @Override
    protected void initPresenterAndData(Bundle savedInstanceState) {

    }

    @Override
    protected void initDelayedView() {

    }

    public void agree() {
        setResult(RESULT_OK);
        finishCur();
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
}
