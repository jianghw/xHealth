package com.kaurihealth.kaurihealth.mine_v;

import android.os.Bundle;

import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;

/**
 * Created By Garent_Wu
 * 描述：我的-->设置-->意见反馈
 * 修订日期:2016.8.29
 */
public class FeedbackActivity extends BaseActivity {

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initPresenterAndData(Bundle savedInstanceState) {

    }

    @Override
    protected void initDelayedView() {
        initBackBtn(R.id.iv_back);
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
}
