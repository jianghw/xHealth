package com.kaurihealth.kaurihealth.referrals_v;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.kaurihealth.chatlib.event.ReferralDoctorEvent;
import com.kaurihealth.chatlib.utils.LCIMConstants;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.DoctorRelationshipBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.ReferralDoctorAdapter;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.eventbus.ReferralReasonEvent;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.mvplib.patient_p.IReferralDoctorView;
import com.kaurihealth.mvplib.patient_p.ReferrcalDoctorPresenter;
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

/**
 * Created by mip on 2016/10/24.
 * <p/>
 * Describe: 患者选医生
 */

public class ReferralDoctorActivity extends BaseActivity implements IReferralDoctorView {
    @Inject
    ReferrcalDoctorPresenter<IReferralDoctorView> mPresenter;

    @Bind(R.id.tv_more)
    TextView tv_more;
    @Bind(R.id.LV_referral_doctor)
    ListView LV_referral_doctor;
    @Bind(R.id.home_referral_request_tips)
    TextView home_referral_request_tips;
    @Bind(R.id.swipe_refresh)
    ScrollChildSwipeRefreshLayout mSwipeRefresh;

    List<DoctorRelationshipBean> beanList = new ArrayList<>();
    List<Integer> doctorIdList = new ArrayList<>();
    private ReferralDoctorAdapter adapter;
    private DoctorPatientRelationshipBean mDoctorPatientRelationshipBean;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.referrcal_doctor;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        initNewBackBtn("选择转诊医生");
        tv_more.setText("下一步");

        mSwipeRefresh.setColorSchemeColors(
                ColorUtils.setSwipeRefreshColors(getApplicationContext())
        );
        mSwipeRefresh.setScrollUpChild(LV_referral_doctor);
        mSwipeRefresh.setSize(SwipeRefreshLayout.DEFAULT);
        mSwipeRefresh.setDistanceToTriggerSync(Global.Numerical.SWIPE_REFRESH);
        mSwipeRefresh.setOnRefreshListener(() -> mPresenter.onSubscribe());
    }

    @Override
    protected void initDelayedData() {
        adapter = new ReferralDoctorAdapter(this, beanList);
        LV_referral_doctor.setAdapter(adapter);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
            if (extras.containsKey(LCIMConstants.CONVERSATION_ID_REFERRAL_PATIENT)) {
                int patientId = extras.getInt(LCIMConstants.CONVERSATION_ID_REFERRAL_PATIENT, -1);
                if (patientId != -1) {
                    mPresenter.loadDoctorPatientRelationshipForPatientId(patientId);
                }
            }
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!beanList.isEmpty()) beanList.clear();
        if (!doctorIdList.isEmpty()) doctorIdList.clear();
        removeStickyEvent(ReferralDoctorEvent.class);
        mPresenter.unSubscribe();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void getDoctorRelationshipBean(DoctorPatientRelationshipBean bean) {
        mDoctorPatientRelationshipBean = bean;
        mPresenter.onSubscribe();
    }

    /**
     * 订阅事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(ReferralDoctorEvent event) {
        mDoctorPatientRelationshipBean = event.getBean();
        mPresenter.onSubscribe();
    }

    @OnClick(R.id.tv_more)
    public void onClickToNext() {
        if (!doctorIdList.isEmpty()) doctorIdList.clear();

        for (DoctorRelationshipBean bean : beanList) {
            if (bean.isCheck) doctorIdList.add(bean.getRelatedDoctor().getDoctorId());
        }

        if (doctorIdList.size() == 0) {
            displayErrorDialog(getString(R.string.referral_doctor_to_select));
        } else if (doctorIdList.size() > 1) {
            displayErrorDialog(getString(R.string.referral_doctor_to_select_only_one));
        } else {
            EventBus.getDefault().postSticky(new ReferralReasonEvent(
                    mDoctorPatientRelationshipBean.getPatient().getPatientId(),
                    mDoctorPatientRelationshipBean.getDoctorPatientId(),
                    doctorIdList));
            skipToForResult(ReferralReasonActivity.class, null, Global.RequestCode.REFERRAL_DOCTOR);
        }
    }

    @Override
    public void switchPageUI(String className) {
    }

    @Override
    public void loadingIndicator(boolean flag) {
        if (mSwipeRefresh == null) return;
        mSwipeRefresh.post(() -> mSwipeRefresh.setRefreshing(flag));
    }

    @Override
    public void getDoctorRelationshipBeanList(List<DoctorRelationshipBean> doctorRelationshipBeen) {
        home_referral_request_tips.setVisibility(doctorRelationshipBeen != null ? !doctorRelationshipBeen.isEmpty() ? View.GONE : View.VISIBLE : View.VISIBLE);
        if (!beanList.isEmpty()) beanList.clear();
        beanList.addAll(doctorRelationshipBeen);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Global.RequestCode.REFERRAL_DOCTOR == requestCode && resultCode == RESULT_OK) {
            finishCur();
        }
    }
}
