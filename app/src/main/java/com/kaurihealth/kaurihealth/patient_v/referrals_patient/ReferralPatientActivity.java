package com.kaurihealth.kaurihealth.patient_v.referrals_patient;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.PatientRequestReferralPatientDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.ReferralPatientAdapter;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.eventbus.ReferralPatientEvent;
import com.kaurihealth.kaurihealth.eventbus.ReferralPatientReasonEvent;
import com.kaurihealth.mvplib.patient_p.IReferralPatientView;
import com.kaurihealth.mvplib.patient_p.ReferralPatientPresenter;
import com.kaurihealth.utilslib.CheckUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by mip on 2016/10/26.
 */

public class ReferralPatientActivity extends BaseActivity implements IReferralPatientView {
    @Bind(R.id.tv_more)
    TextView tv_more;
    @Bind(R.id.LV_referral_patient)
    ListView LV_referral_patient;
    private List<DoctorPatientRelationshipBean> beanList = new ArrayList<>();
    private List<PatientRequestReferralPatientDisplayBean.PatientListReferralBean> referralBeen = new ArrayList<>();
    private ReferralPatientAdapter adapter;
    private int doctorId;
    private final static String REFERRALPATIENT = "ReferralPatient";
    @Inject
    ReferralPatientPresenter<IReferralPatientView> mPresenter;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.referral_patient;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
        EventBus.getDefault().register(this);
        mPresenter.onSubscribe();
    }

    @Override
    protected void initDelayedData() {
        initNewBackBtn("选择转诊病人");
        tv_more.setText("下一步");

        adapter = new ReferralPatientAdapter(this, beanList, referralBeen);
        LV_referral_patient.setAdapter(adapter);
    }

    /**
     * --------------------------订阅---------------------------------------
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(ReferralPatientEvent event) {
        doctorId = event.getDoctorId();
    }

    @Override
    public void switchPageUI(String className) {

    }

    @OnClick(R.id.tv_more)
    public void onNext() {
        EventBus.getDefault().postSticky(new ReferralPatientReasonEvent(doctorId, referralBeen, REFERRALPATIENT));
        skipTo(ReferralReasonActivity.class);
    }

    /**
     * 得到返回bean
     *
     * @param bean
     */
    @Override
    public void getResultBean(List<DoctorPatientRelationshipBean> bean) {
        if (beanList.size() > 0) beanList.clear();
        CheckUtils.checkNotNull(bean);
        beanList.addAll(bean);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
