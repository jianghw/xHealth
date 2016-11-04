package com.kaurihealth.kaurihealth.patient_v.referrals_patient;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.DoctorRelationshipBean;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.ReferralDoctorAdapter;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.eventbus.ReferralDoctorEvent;
import com.kaurihealth.kaurihealth.eventbus.ReferralReasonEvent;
import com.kaurihealth.mvplib.patient_p.IReferralDoctorView;
import com.kaurihealth.mvplib.patient_p.ReferrcalDoctorPresenter;
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
 * Created by mip on 2016/10/24.
 */

public class ReferralDoctorActivity extends BaseActivity implements IReferralDoctorView {

    @Bind(R.id.tv_more)
    TextView tv_more;
    @Bind(R.id.LV_referral_doctor)
    ListView LV_referral_doctor;
    List<DoctorRelationshipBean> beanList = new ArrayList<>();
    private ReferralDoctorAdapter adapter;
    private DoctorPatientRelationshipBean eventBean;
    int patientId;

    private List<Integer> doctorIdlist = new ArrayList<>();

    @Inject
    ReferrcalDoctorPresenter<IReferralDoctorView> mPresenter;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.referrcal_doctor;
    }


    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
        //注册事件
        EventBus.getDefault().register(this);
        mPresenter.onSubscribe();
    }

    @Override
    protected void initDelayedData() {
        initNewBackBtn("选择转诊医生");
        tv_more.setText("下一步");
        adapter = new ReferralDoctorAdapter(this, beanList, doctorIdlist);
        LV_referral_doctor.setAdapter(adapter);

    }

    /**
     * 订阅事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(ReferralDoctorEvent event) {
        eventBean = event.getBean();
    }

    @OnClick(R.id.tv_more)
    public void onClickToNext() {
        EventBus.getDefault().postSticky(new ReferralReasonEvent(
                eventBean.getPatient().getPatientId(),
                eventBean.getDoctorPatientId(),
                doctorIdlist));
        skipToForResult(ReferralReasonActivity.class,null,123);
    }

    @Override
    public void switchPageUI(String className) {

    }

    @Override
    public void getDoctorRelationshipBeanList(List<DoctorRelationshipBean> doctorRelationshipBeen) {
        if (beanList.size() > 0) beanList.clear();
        CheckUtils.checkNotNull(doctorRelationshipBeen);
        beanList.addAll(doctorRelationshipBeen);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123&&resultCode==RESULT_OK){
            if (doctorIdlist.size() > 0) doctorIdlist.clear();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
