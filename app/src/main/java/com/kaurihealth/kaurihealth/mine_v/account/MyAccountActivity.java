package com.kaurihealth.kaurihealth.mine_v.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.mine_v.AccountDetailsActivity;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * 我的Fragment-->点击“我的账户”条目 --> “我的账户”Activity
 */
public class MyAccountActivity extends BaseActivity{
    //账户总余额
    @Bind(R.id.tv_totalCredit)
    TextView tvTotalCredit;

    //可支配余额
    @Bind(R.id.tv_availableCredit)
    TextView tvAvailableCredit;

    //账户明细按钮--> 跳转到“账户明细”Activity
    @Bind(R.id.tv_more)
    TextView tvMore;
    //“提现” 按钮
    @Bind(R.id.btn_drawCash)
    Button btnDrawCash;

    private static final int KEY_WITH_DRAW = 200;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_my_account;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        initNewBackBtn(getString(R.string.account_title));
        tvMore.setText(getString(R.string.title_account));
    }

    @Override
    protected void initDelayedData() {
        //获取账户余额
        initRemainingMoney();
    }

    //获取账户余额
    private void initRemainingMoney() {
        DoctorDisplayBean myself = LocalData.getLocalData().getMyself();
        double totalCredit = myself.getTotalCredit();
        double availableCredit = myself.getAvailableCredit();
        tvTotalCredit.setText(String.format("￥%.2f", totalCredit));
        tvAvailableCredit.setText(String.format("￥%.2f", availableCredit));
        btnDrawCash.setEnabled(availableCredit > 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.tv_more, R.id.btn_drawCash})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_more://跳转到账户明细界面
                skipTo(AccountDetailsActivity.class);
                break;
            case R.id.btn_drawCash://提现操作逻辑
                withDrawCash();
                break;
            default:
                break;
        }
    }

    private void withDrawCash() {
        //账户内余额大于0，跳转到“提现信息”Activity
        skipToForResult(WithdrawActivity.class, null, KEY_WITH_DRAW);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case KEY_WITH_DRAW:
                if (resultCode == RESULT_OK) {
                    initRemainingMoney();
                }
                break;
            default:
                break;
        }
    }

}
