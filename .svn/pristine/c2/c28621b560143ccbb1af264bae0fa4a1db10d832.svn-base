package com.kaurihealth.kaurihealth.home_v.request;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.PatientRequestDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.PatientAdapter;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.eventbus.PatientRequestDisplayBeanEvent;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.kaurihealth.util.PatientRequestDateUtils;
import com.kaurihealth.mvplib.main_p.IPatientRequestView;
import com.kaurihealth.mvplib.main_p.PatientRequestPresenter;
import com.kaurihealth.utilslib.ColorUtils;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.widget.ScrollChildSwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
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

    @Bind(R.id.lv_content)
    ListView lvContent;

    @Bind(R.id.swipe_refresh)
    ScrollChildSwipeRefreshLayout swipe_refresh;

    private List<PatientRequestDisplayBean> homeList = new ArrayList<>();
    private PatientAdapter patientAdapter;

    public static final int Update = 8;
    public static final int UpdateAcc = 7;
    private PatientRequestDateUtils patientRequestDateUtils;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.patient_request;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        patientAdapter = new PatientAdapter(PatientRequestActivity.this, homeList);
        lvContent.setAdapter(patientAdapter);

        swipe_refresh.setSize(SwipeRefreshLayout.DEFAULT);
        swipe_refresh.setColorSchemeColors(
                ColorUtils.setSwipeRefreshColors(getApplicationContext())
        );
        swipe_refresh.setDistanceToTriggerSync(Global.Numerical.SWIPE_REFRESH);
        swipe_refresh.setScrollUpChild(lvContent);
        swipe_refresh.setOnRefreshListener(() -> mPresenter.loadDoctorDetail(true));
    }

    @Override
    protected void initDelayedData() {
        patientRequestDateUtils = new PatientRequestDateUtils();
        initNewBackBtn(getString(R.string.patient_request));
        mPresenter.onSubscribe();

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
        patient_tips.setVisibility(beanList != null ? beanList.size() > 0 ? View.GONE : View.VISIBLE : View.VISIBLE);
        if (!homeList.isEmpty()) homeList.clear();

        //将数据进行排序处理
        PatientRequestDisplayBean[] PatientRequestDisplayBean_array = patientRequestDateUtils.handleData(beanList);
        List<PatientRequestDisplayBean> PatientRequestDisplayBean_list = Arrays.asList(PatientRequestDisplayBean_array);

        homeList.addAll(PatientRequestDisplayBean_list);
        patientAdapter.notifyDataSetChanged();
    }

    /**
     * 跳转到被点击的item
     */
    @Override
    public void goToActivity(PatientRequestDisplayBean patientRequestDisplayBean) {
        EventBus.getDefault().postSticky(new PatientRequestDisplayBeanEvent(patientRequestDisplayBean));
        skipToForResult(PatientRequestInfoActivity.class, null, Global.RequestCode.REQUEST_PATIENT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Global.RequestCode.REQUEST_PATIENT) {
            if (resultCode == Global.RequestCode.ACCEPT) {
                setResult(RESULT_OK);
                finishCur();
            } else if (resultCode == Global.RequestCode.REJECT) {
                mPresenter.loadDoctorDetail(true);
            }
        }
    }
}
