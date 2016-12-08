package com.kaurihealth.kaurihealth.referrals_v;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.kaurihealth.chatlib.event.ReferralPatientEvent;
import com.kaurihealth.datalib.request_bean.bean.PatientRequestReferralPatientDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.ReferralPatientAdapter;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.eventbus.PatientInfoShipBeanEvent;
import com.kaurihealth.kaurihealth.eventbus.ReferralPatientReasonEvent;
import com.kaurihealth.kaurihealth.patient_v.PatientInfoActivity;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.mvplib.patient_p.IReferralPatientView;
import com.kaurihealth.mvplib.patient_p.ReferralPatientPresenter;
import com.kaurihealth.utilslib.ColorUtils;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.widget.ScrollChildSwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * Created by mip on 2016/10/26.
 * <p/>
 * Describe:医生界面调用
 */

public class ReferralPatientActivity extends BaseActivity implements IReferralPatientView {
    @Inject
    ReferralPatientPresenter<IReferralPatientView> mPresenter;

    @Bind(R.id.tv_more)
    TextView tv_more;
    @Bind(R.id.LV_referral_patient)
    ListView LV_referral_patient;
    @Bind(R.id.home_referral_request_tips)
    TextView home_referral_request_tips;
    @Bind(R.id.swipe_refresh)
    ScrollChildSwipeRefreshLayout mSwipeRefresh;

    private List<DoctorPatientRelationshipBean> beanList = new ArrayList<>();
    private List<PatientRequestReferralPatientDisplayBean.PatientListReferralBean> listReferralBeen = new ArrayList<>();
    private ReferralPatientAdapter adapter;
    private int doctorId;
    private final static String REFERRALPATIENT = "ReferralPatient";

    @Override
    protected int getActivityLayoutID() {
        return R.layout.referral_patient;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        initNewBackBtn("选择转诊病人");
        tv_more.setText("下一步");

        mSwipeRefresh.setColorSchemeColors(
                ColorUtils.setSwipeRefreshColors(getApplicationContext())
        );
        mSwipeRefresh.setScrollUpChild(LV_referral_patient);
        mSwipeRefresh.setSize(SwipeRefreshLayout.DEFAULT);
        mSwipeRefresh.setDistanceToTriggerSync(Global.Numerical.SWIPE_REFRESH);
        mSwipeRefresh.setOnRefreshListener(() -> mPresenter.onSubscribe());
    }

    @Override
    protected void initDelayedData() {
        adapter = new ReferralPatientAdapter(this, beanList);
        LV_referral_patient.setAdapter(adapter);

        EventBus.getDefault().register(this);
    }

    /**
     * --------------------------订阅---------------------------------------
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(ReferralPatientEvent event) {
        doctorId = event.getDoctorId();
        mPresenter.onSubscribe();
    }

    @Override
    public void switchPageUI(String className) {

    }

    @OnClick(R.id.tv_more)
    public void onNext() {

        if (!listReferralBeen.isEmpty()) listReferralBeen.clear();
        for(DoctorPatientRelationshipBean bean:beanList){
            PatientRequestReferralPatientDisplayBean.PatientListReferralBean listReferralBean =
                    new PatientRequestReferralPatientDisplayBean.PatientListReferralBean();
            listReferralBean.setPatientId(bean.getPatientId());
            listReferralBean.setDoctorPatientShipId(bean.getDoctorPatientId());
            if(bean.isCheck) listReferralBeen.add(listReferralBean);
        }
        if (listReferralBeen.size() == 0) {
            displayErrorDialog(getString(R.string.referral_patient_to_select));
        } else if (listReferralBeen.size() > 1) {
            displayErrorDialog(getString(R.string.referral_patient_to_select_only_one));
        } else {
            EventBus.getDefault().postSticky(new ReferralPatientReasonEvent(doctorId, listReferralBeen, REFERRALPATIENT));
            skipToForResult(ReferralReasonActivity.class, null, Global.RequestCode.REFERRAL_DOCTOR);
        }
    }

    @OnItemClick(R.id.LV_referral_patient)
    public void onItemClick(int position) {
        EventBus.getDefault().postSticky(new PatientInfoShipBeanEvent(beanList.get(position), 1));
        skipTo(PatientInfoActivity.class);
    }

    @Override
    public void loadingIndicator(boolean flag) {
        if (mSwipeRefresh == null) return;
        mSwipeRefresh.post(() -> mSwipeRefresh.setRefreshing(flag));
    }

    /**
     * 得到返回bean
     */
    @Override
    public void getResultBean(List<DoctorPatientRelationshipBean> bean) {
        home_referral_request_tips.setVisibility(bean != null ? !bean.isEmpty() ? View.GONE : View.VISIBLE : View.VISIBLE);
        if (!beanList.isEmpty()) beanList.clear();
        beanList.addAll(bean);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!beanList.isEmpty()) beanList.clear();
        if (!listReferralBeen.isEmpty()) listReferralBeen.clear();
        mPresenter.unSubscribe();
        removeStickyEvent(ReferralPatientEvent.class);
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Global.RequestCode.REFERRAL_DOCTOR == requestCode && resultCode == RESULT_OK) {
            finishCur();
        }
    }
}
