package com.kaurihealth.kaurihealth.main_v;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.PatientRequestDisplayBean;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.eventbus.PatientRequestDisplayBeanEvent;
import com.kaurihealth.kaurihealth.adapter.PatientAdapter;
import com.kaurihealth.kaurihealth.home_v.PatientRequestInfoActivity;
import com.kaurihealth.mvplib.main_p.IPatientRequestView;
import com.kaurihealth.mvplib.main_p.PatientRequestPresenter;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.widget.ScrollChildSwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by mip on 2016/9/18.
 */
public class PatientRequestActivity extends BaseActivity implements IPatientRequestView {
    @Inject
    PatientRequestPresenter<IPatientRequestView> mPresenter;

    @Bind(R.id.patient_tips)
    TextView patient_tips;
    //SwipeRefreshLayoiut里面的ListView
    @Bind(R.id.lv_content)
    ListView lvContent;
    //下拉刷新Google自带控件
    @Bind(R.id.swipe_refresh)
    ScrollChildSwipeRefreshLayout swipe_refresh;

    private List<PatientRequestDisplayBean> homeList = new ArrayList<>();
    private PatientAdapter patientAdapter;
    public static final int Update = 8;
    public static final int UpdateAcc = 7;
    public static final int FROM_HOME_FRAGMENT = 1;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.patient_request;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        mPresenter.onSubscribe();
    }

    @Override
    protected void initDelayedData() {
        initNewBackBtn("候诊患者");

        patientAdapter = new PatientAdapter(PatientRequestActivity.this, homeList);
        lvContent.setAdapter(patientAdapter);

        swipe_refresh.setSize(SwipeRefreshLayout.DEFAULT);
        swipe_refresh.setColorSchemeColors(
                ContextCompat.getColor(this.getApplicationContext(), R.color.welcome_bg_cl),
                ContextCompat.getColor(this.getApplicationContext(), R.color.welcome_bg_cl),
                ContextCompat.getColor(this.getApplicationContext(), R.color.welcome_bg_cl)
        );
        swipe_refresh.setDistanceToTriggerSync(Global.Numerical.SWIPE_REFRESH);
        swipe_refresh.setScrollUpChild(lvContent);
        swipe_refresh.setOnRefreshListener(() -> mPresenter.loadDoctorDetail(true));

        lvContent.setOnItemClickListener((parent, view, position, id) -> {
            PatientRequestDisplayBean selectedItem = homeList.get(position);
            goToActivity(selectedItem);
        });
    }

    @Override
    public void loadingIndicator(boolean flag) {
        if (swipe_refresh == null) return;
        swipe_refresh.post(() -> swipe_refresh.setRefreshing(flag));
    }

    @Override
    public void switchPageUI(String className) {

    }

    @Override
    public void refreshListView(List<PatientRequestDisplayBean> beanList) {
        if (beanList == null) return;
        patient_tips.setVisibility(beanList.size() > 0 ? View.GONE : View.VISIBLE);
        //刷先数据
        homeList.clear();
        homeList.addAll(beanList);
        patientAdapter.notifyDataSetChanged();
    }

    /**
     * 跳转到被点击的item
     *
     * @param patientRequestDisplayBean  患者请求详情页面
     */
    @Override
    public void goToActivity(PatientRequestDisplayBean patientRequestDisplayBean) {
        EventBus.getDefault().postSticky(new PatientRequestDisplayBeanEvent(patientRequestDisplayBean));
        skipToForResult(PatientRequestInfoActivity.class, null,FROM_HOME_FRAGMENT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FROM_HOME_FRAGMENT) {
            if (resultCode == UpdateAcc) {
                setResult(RESULT_OK);
                finishCur();
            }
        }
    }
}
