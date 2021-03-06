package com.kaurihealth.kaurihealth.referrals_v;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.kaurihealth.chatlib.event.LCIMInputBottomBarEvent;
import com.kaurihealth.chatlib.event.ReferralPatientEvent;
import com.kaurihealth.datalib.request_bean.bean.PatientListReferralBean;
import com.kaurihealth.datalib.response_bean.MainPagePatientDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.PatientReferralAdapter;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.eventbus.ReferralPatientReasonEvent;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.mvplib.patient_p.IReferralPatientView;
import com.kaurihealth.mvplib.patient_p.ReferralPatientPresenter;
import com.kaurihealth.utilslib.ColorUtils;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.widget.MaterialSearchBar;
import com.kaurihealth.utilslib.widget.ScrollChildSwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

import static rx.Observable.from;

/**
 * Created by mip on 2016/10/26.
 * <p/>
 * Describe:医生界面调用--患者质料
 */

public class ReferralPatientActivity extends BaseActivity implements IReferralPatientView, MaterialSearchBar.OnSearchActionListener {
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
    @Bind(R.id.searchBar)
    MaterialSearchBar searchBar;

    private List<MainPagePatientDisplayBean> beanList = new ArrayList<>();
    private List<PatientListReferralBean> listReferralBeen = new ArrayList<>();

    private List<MainPagePatientDisplayBean> mSearchBeanList;
    private PatientReferralAdapter adapter;
    private int mDoctorId;
    private final static String REFERRALPATIENT = "ReferralPatient";
    private int mType;
    private String mKauriHealthId;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.referral_patient;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        initNewBackBtn("选择相关病人(单选)");
        tv_more.setText("下一步");

        mSwipeRefresh.setColorSchemeColors(
                ColorUtils.setSwipeRefreshColors(getApplicationContext())
        );
        mSwipeRefresh.setScrollUpChild(LV_referral_patient);
        mSwipeRefresh.setSize(SwipeRefreshLayout.DEFAULT);
        mSwipeRefresh.setDistanceToTriggerSync(Global.Numerical.SWIPE_REFRESH);
        mSwipeRefresh.setOnRefreshListener(() -> mPresenter.loadDoctorMainPage());
    }

    @Override
    protected void initDelayedData() {
        adapter = new PatientReferralAdapter(this, beanList);
        LV_referral_patient.setAdapter(adapter);

        searchBar.setOnSearchActionListener(this);

        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                mKauriHealthId = bundle.getString(Global.Bundle.SEARCH_BUNDLE);
            }
        }

        EventBus.getDefault().register(this);
    }

    /**
     * --------------------------订阅---------------------------------------
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(ReferralPatientEvent event) {
        mDoctorId = event.getDoctorId();
        mType = event.getType();

        mPresenter.loadDoctorMainPage();
    }

    @Override
    public void switchPageUI(String className) {
//TODO
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!beanList.isEmpty()) beanList.clear();
        if (!listReferralBeen.isEmpty()) listReferralBeen.clear();
        if (mSearchBeanList != null && !mSearchBeanList.isEmpty()) mSearchBeanList.clear();

        removeStickyEvent(ReferralPatientEvent.class);
        EventBus.getDefault().unregister(this);
        mPresenter.unSubscribe();
    }

    @OnClick(R.id.tv_more)
    public void onNext() {
        if (!listReferralBeen.isEmpty()) listReferralBeen.clear();

        for (MainPagePatientDisplayBean bean : beanList) {
            PatientListReferralBean listReferralBean = new PatientListReferralBean();
            listReferralBean.setPatientId(bean.getPatientId());
            if (bean.isCheck) listReferralBeen.add(listReferralBean);
        }

        if (listReferralBeen.size() == 0) {
            displayErrorDialog(getString(R.string.referral_patient_to_select));
        } else if (listReferralBeen.size() > 1) {
            displayErrorDialog(getString(R.string.referral_patient_to_select_only_one));
        } else if (mType == LCIMInputBottomBarEvent.INPUTBOTTOMBAR_REFERRAL_ACTION) {
            EventBus.getDefault().postSticky(new ReferralPatientReasonEvent(mDoctorId, listReferralBeen, REFERRALPATIENT));
            Bundle bundle = new Bundle();
            if (mKauriHealthId != null) bundle.putString(Global.Bundle.REQUEST_DOCTOR, mKauriHealthId);
            skipToForResult(ReferralReasonActivity.class, bundle, Global.RequestCode.REFERRAL_DOCTOR);
        } else if (mType == LCIMInputBottomBarEvent.INPUTBOTTOMBAR_MEDICAL_ACTION) {
            ArrayList<MainPagePatientDisplayBean> arrayList = new ArrayList<>();
            for (MainPagePatientDisplayBean bean : beanList) {
                if (bean.isCheck) arrayList.add(bean);
            }
            Intent intent = getIntent();
            intent.putParcelableArrayListExtra(Global.Bundle.RESULT_OK_SICKNESS, arrayList);
            setResult(RESULT_OK, intent);
            finishCur();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Global.RequestCode.REFERRAL_DOCTOR == requestCode && resultCode == RESULT_OK) {
            finishCur();
        }
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
    public void loadDoctorMainPageSucceed(List<MainPagePatientDisplayBean> bean) {
        home_referral_request_tips.setVisibility(bean != null ? !bean.isEmpty() ? View.GONE : View.VISIBLE : View.VISIBLE);
        if (!beanList.isEmpty()) beanList.clear();
        if (bean != null) beanList.addAll(bean);
        adapter.notifyDataSetChanged();
    }

    /**
     * 开始搜索
     */
    @Override
    public void onSearchStart() {
        if (mSearchBeanList == null) mSearchBeanList = new ArrayList<>();

        if (!mSearchBeanList.isEmpty()) mSearchBeanList.clear();
        mSearchBeanList.addAll(beanList);
    }

    /**
     * 确认搜索
     */
    @Override
    public void onSearchConfirmed(CharSequence text) {
        from(mSearchBeanList)
                .filter(bean -> bean.getPatientFullName().contains(text.toString()))
                .toList()
                .subscribe(displayBeanList -> {
                    if (beanList.size() > 0) beanList.clear();
                    beanList.addAll(displayBeanList);
                    adapter.notifyDataSetChanged();
                });
    }

    /**
     * 退出搜索
     */
    @Override
    public void onSearchCompleteBack() {
        if (beanList.size() > 0) beanList.clear();
        beanList.addAll(mSearchBeanList);
        adapter.notifyDataSetChanged();
    }

    /**
     * 删除键回调
     */
    @Override
    public void onSearchDeleteBack() {
        if (!beanList.isEmpty()) beanList.clear();
        beanList.addAll(mSearchBeanList);
    }

}
