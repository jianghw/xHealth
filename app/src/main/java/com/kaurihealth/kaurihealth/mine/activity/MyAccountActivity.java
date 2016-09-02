package com.kaurihealth.kaurihealth.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.util.ExitApplication;
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
public class MyAccountActivity extends CommonActivity {

    @Bind(R.id.tvTotalCredit)
    TextView tvTotalCredit;
    @Bind(R.id.tv_operate)
    TextView tv_operate;
    @Bind(R.id.tvAvailableCredit)
    TextView tvAvailableCredit;
    @Bind(R.id.tvWithDrawCash)
    TextView tvWithDrawCash;
    private IGetter getter;
    private final int WithdrawCashActivity = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        ButterKnife.bind(this);
        init();
        ExitApplication.getInstance().addActivity(this);
    }

    @OnClick({R.id.tv_operate, R.id.tvWithDrawCash})
    public void onClick(View view) {
        switch (view.getId()) {
            //跳转到账户明细界面
            case R.id.tv_operate:
                skipTo(AccountDetailsActivity.class);
                break;
            //提现
            case R.id.tvWithDrawCash:
                withDrawCash();
                break;
        }
    }

    @Override
    public void init() {
        super.init();
        getter = Getter.getInstance(getApplicationContext());
        setBack(R.id.iv_back);
        getRemainingMoney();
        //提现按钮处理
        if (getter.getAvailableCredit() > 0 ){
            tvWithDrawCash.setBackgroundResource(R.drawable.bg_blue);
        }else {
            tvWithDrawCash.setBackgroundResource(R.drawable.bg_gar);
        }
    }


    /**
     * 获取账户余额
     */
    private void getRemainingMoney() {
        tvTotalCredit.setText(String.format("￥%.2f", getter.getTotalCredit()));
        tvAvailableCredit.setText(String.format("￥%.2f", getter.getAvailableCredit()));
    }

    private void withDrawCash() {
        if(getter.getAvailableCredit() > 0) {
            skipToForResult(WithdrawCashActivity.class, null, WithdrawCashActivity);
        } else {
            return;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case WithdrawCashActivity:
                if (resultCode == RESULT_OK) {
                    getRemainingMoney();
                }
                break;
        }
    }
}
