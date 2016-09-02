package com.kaurihealth.kaurihealth.mine_v;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.mine.activity.AccountDetailsActivity;
import com.kaurihealth.kaurihealth.mine.activity.WithdrawCashActivity;
import com.kaurihealth.kaurihealth.util.Getter;
import com.kaurihealth.kaurihealth.util.Interface.IGetter;
import com.kaurihealth.mvplib.mine_p.IMyAccountView;
import com.kaurihealth.mvplib.mine_p.MyAccountPresenter;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;


/**
 *我的Fragment-->点击“我的账户”条目 --> “我的账户”Activity
 */
public class MyAccountActivity extends BaseActivity implements IMyAccountView {
    //账户总余额
    @Bind(R.id.tvTotalCredit)
    TextView tvTotalCredit;
    //可支配余额
    @Bind(R.id.tvAvailableCredit)
    TextView tvAvailableCredit;

    //账户明细按钮--> 跳转到“账户明细”Activity
    @Bind(R.id.tv_operate)
    TextView tv_operate;

    //“提现” 按钮
    @Bind(R.id.tvWithDrawCash)
    TextView tvWithDrawCash;


    @Inject
    MyAccountPresenter<IMyAccountView> mPresenter;
    private IGetter getter;
    private  final int WithdrawCashActivity =10;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_my_account;
    }

    @Override
    protected void initPresenterAndData(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
    }

    @Override
    protected void initDelayedView() {
        initBackBtn(R.id.iv_back);
        getter = Getter.getInstance(getApplicationContext());
        //获取账户余额
        getRemainingMoney();
        //提现按钮处理
        if (getter.getAvailableCredit()>0) {
            //账户余额>0，提现按钮显示绿色
            tvWithDrawCash.setBackgroundResource(R.drawable.bg_blue);
        }else{
            tvWithDrawCash.setBackgroundResource(R.drawable.bg_gar);
        }
    }

    //获取账户余额
    private void getRemainingMoney() {
        tvTotalCredit.setText(String.format("￥%.2f", getter.getTotalCredit()));
        tvAvailableCredit.setText(String.format("￥%.2f", getter.getAvailableCredit()));
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


    @OnClick({R.id.tv_operate, R.id.tvWithDrawCash})
    public void onClick(View view) {
        switch (view.getId()) {
            //跳转到账户明细界面
            case R.id.tv_operate:
                skipTo(AccountDetailsActivity.class);
                break;
            //提现
            case R.id.tvWithDrawCash:
                //提现操作逻辑
                withDrawCash();
                break;
        }
    }

    private void withDrawCash() {
        if(getter.getAvailableCredit() > 0) {
            //账户内余额大于0，跳转到“提现信息”Activity
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
