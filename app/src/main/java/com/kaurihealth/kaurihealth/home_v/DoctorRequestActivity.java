package com.kaurihealth.kaurihealth.home_v;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.kaurihealth.datalib.response_bean.DoctorRelationshipBean;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.DoctorRequestAdapter;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.mvplib.home_p.DoctorRequestPresenter;
import com.kaurihealth.mvplib.home_p.IDoctorRequestView;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.widget.ScrollChildSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by mip on 2016/9/19.
 * <p/>
 * 描述：
 */
public class DoctorRequestActivity extends BaseActivity implements IDoctorRequestView {
    @Inject
    DoctorRequestPresenter<IDoctorRequestView> mPresenter;

    @Bind(R.id.tv_more)
    TextView tv_more;
    @Bind(R.id.swipe_refresh)
    ScrollChildSwipeRefreshLayout swipe_refresh;
    @Bind(R.id.LV_doctor_request)
    ListView LV_doctor_request;
    @Bind(R.id.home_doctor_request_tips)
    TextView home_doctor_request_tips;

    List<DoctorRelationshipBean> relationshipBeanList = new ArrayList<>();

    private DoctorRequestAdapter adapter;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.doctor_request;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
        mPresenter.onSubscribe();
    }

    @Override
    protected void initDelayedData() {
        initNewBackBtn("医生请求");
        tv_more.setVisibility(View.GONE);

        adapter = new DoctorRequestAdapter(this, relationshipBeanList);
        LV_doctor_request.setAdapter(adapter);

        swipe_refresh.setSize(SwipeRefreshLayout.DEFAULT);
        swipe_refresh.setColorSchemeColors(
                ContextCompat.getColor(this.getApplicationContext(), R.color.welcome_bg_cl),
                ContextCompat.getColor(this.getApplicationContext(), R.color.welcome_bg_cl),
                ContextCompat.getColor(this.getApplicationContext(), R.color.welcome_bg_cl)
        );
        swipe_refresh.setDistanceToTriggerSync(Global.Numerical.SWIPE_REFRESH);
        swipe_refresh.setScrollUpChild(LV_doctor_request);
        swipe_refresh.setOnRefreshListener(() -> mPresenter.loadDoctorDetail(true));

        LV_doctor_request.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DoctorRelationshipBean bean = relationshipBeanList.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("doctorRelationshipBean", bean);
                skipToForResult(FriendRequestsActivity.class, bundle, 100);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!relationshipBeanList.isEmpty()) relationshipBeanList.clear();
        mPresenter.unSubscribe();
    }

    /**
     * 界面返回事件
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 400://拒绝
                mPresenter.loadDoctorDetail(true);
                break;
            case 200:
                setResult(RESULT_OK);
                finishCur();
                break;
            default:
                break;
        }
    }

    @Override
    public void switchPageUI(String className) {

    }

    @Override
    public void loadingIndicator(boolean flag) {
        if (swipe_refresh == null) return;
        swipe_refresh.post(() -> swipe_refresh.setRefreshing(flag));
    }

    /**
     * 得到所有挂起的医生关系
     *
     * @param beanList
     */
    @Override
    public void getRequestResult(List<DoctorRelationshipBean> beanList) {
        if (beanList == null) return;
        home_doctor_request_tips.setVisibility(beanList.size() > 0 ? View.GONE : View.VISIBLE);
        relationshipBeanList.clear();
        relationshipBeanList.addAll(beanList);

        adapter.notifyDataSetChanged();
    }


}
