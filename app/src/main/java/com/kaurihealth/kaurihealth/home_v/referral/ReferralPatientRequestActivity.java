package com.kaurihealth.kaurihealth.home_v.referral;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.PatientRequestDisplayBean;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.ReferralPatientRequestAdapter;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.eventbus.ReferralpatientInfoEvent;
import com.kaurihealth.mvplib.home_p.IReferralPatientRequestView;
import com.kaurihealth.mvplib.home_p.ReferralPatientRequestPresenter;
import com.kaurihealth.utilslib.ColorUtils;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.widget.ScrollChildSwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by mip on 2016/10/24.
 * <p/>
 * Describe:转诊病人的请求
 */

public class ReferralPatientRequestActivity extends BaseActivity implements IReferralPatientRequestView {
    @Inject
    ReferralPatientRequestPresenter<IReferralPatientRequestView> mPresenter;

    @Bind(R.id.swipe_refresh)
    ScrollChildSwipeRefreshLayout swipe_refresh;
    @Bind(R.id.LV_referral_request)
    ListView LV_referral_request;
    @Bind(R.id.home_referral_request_tips)
    TextView home_referral_request_tips;

    private ReferralPatientRequestAdapter adapter;
    private List<PatientRequestDisplayBean> requestDisplayBeenList = new ArrayList<>();

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_referral_patient_request;
    }


    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        initNewBackBtn("转诊患者");

        adapter = new ReferralPatientRequestAdapter(this, requestDisplayBeenList);
        LV_referral_request.setAdapter(adapter);

        swipe_refresh.setSize(SwipeRefreshLayout.DEFAULT);
        swipe_refresh.setColorSchemeColors(
                ColorUtils.setSwipeRefreshColors(getApplicationContext())
        );
        swipe_refresh.setDistanceToTriggerSync(Global.Numerical.SWIPE_REFRESH);
        swipe_refresh.setScrollUpChild(LV_referral_request);
        swipe_refresh.setOnRefreshListener(() -> mPresenter.loadReferralDetail(true));

    }

    @Override
    protected void initDelayedData() {
        mPresenter.onSubscribe();

        LV_referral_request.setOnItemClickListener((parent, view, position, id) -> {
            PatientRequestDisplayBean bean = requestDisplayBeenList.get(position);
            EventBus.getDefault().postSticky(new ReferralpatientInfoEvent(bean));
            skipToForResult(ReferralPatientInfoActivity.class, null,Global.RequestCode.REFERRAL);//跳转
        });
    }

    @Override
    public void switchPageUI(String className) {

    }

    /**
     * 得到PatientRequestDisplayBean集合
     */
    @Override
    public void getPatientRequestDisplayBeanList(List<PatientRequestDisplayBean> DisplayBeanList) {
        home_referral_request_tips.setVisibility(DisplayBeanList.size() > 0 ? View.GONE : View.VISIBLE);
        if (requestDisplayBeenList.size() > 0) requestDisplayBeenList.clear();
        requestDisplayBeenList.addAll(DisplayBeanList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Global.RequestCode.REFERRAL) {
            if (resultCode == Global.RequestCode.ACCEPT) {
                setResult(RESULT_OK);
                finishCur();
            }
        }
    }

    /**
     * 刷新失败或没有数据去掉刷新圆圈
     */
    @Override
    public void loadingIndicator(boolean flag) {
        if (swipe_refresh == null) return;
        swipe_refresh.post(() -> swipe_refresh.setRefreshing(flag));
    }
}
