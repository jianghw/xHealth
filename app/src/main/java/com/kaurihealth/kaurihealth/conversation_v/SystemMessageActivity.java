package com.kaurihealth.kaurihealth.conversation_v;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.kaurihealth.datalib.response_bean.UserNotifyDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.SystemMessageAdapter;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.mvplib.patient_p.ISystemMessageView;
import com.kaurihealth.mvplib.patient_p.SystemMessagePresenter;
import com.kaurihealth.utilslib.ColorUtils;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.widget.ScrollChildSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by jianghw on 2016/11/9.
 * <p/>
 * Describe:系统消息
 */

public class SystemMessageActivity extends BaseActivity implements ISystemMessageView, SystemMessageAdapter.ItemClickBack {
    @Inject
    SystemMessagePresenter<ISystemMessageView> mPresenter;

    @Bind(R.id.lv_visit)
    ListView mLvVisit;
    @Bind(R.id.swipe_refresh)
    ScrollChildSwipeRefreshLayout mSwipeRefresh;
    @Bind(R.id.tv_no_visit)
    TextView mTvNoVisit;
    @Bind(R.id.tv_more)
    TextView mTvMore;

    private SystemMessageAdapter adapter;
    private List<UserNotifyDisplayBean> beanList;


    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_visit_record;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        initNewBackBtn(getString(R.string.system_message_title));
        mTvMore.setText(getString(R.string.more_set));

        mSwipeRefresh.setColorSchemeColors(
                ColorUtils.setSwipeRefreshColors(getApplicationContext())
        );
        mSwipeRefresh.setSize(SwipeRefreshLayout.DEFAULT);
        mSwipeRefresh.setDistanceToTriggerSync(Global.Numerical.SWIPE_REFRESH);
        mSwipeRefresh.setScrollUpChild(mLvVisit);
        mSwipeRefresh.setOnRefreshListener(() -> mPresenter.onSubscribe());
    }

    @Override
    protected void initDelayedData() {
        beanList = new ArrayList<>();
        adapter = new SystemMessageAdapter(this, beanList, this);
        mLvVisit.setAdapter(adapter);

        mPresenter.onSubscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (beanList != null) beanList.clear();
        mPresenter.unSubscribe();
    }

    @OnClick(R.id.tv_more)
    public void insertHealthCondition() {
        skipToForResult(SystemMessageSettingActivity.class, null, Global.RequestCode.ADD_VISIT);
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
    public void loadUserAllNotifySucceed(List<UserNotifyDisplayBean> list) {
        mTvNoVisit.setVisibility(list != null ? !list.isEmpty() ? View.GONE : View.VISIBLE : View.VISIBLE);
        if (!beanList.isEmpty()) beanList.clear();
        beanList.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int referralMessageAlertId) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Global.RequestCode.ADD_VISIT) {
            if (!beanList.isEmpty()) beanList.clear();
            adapter.notifyDataSetChanged();
            mTvNoVisit.setVisibility(View.VISIBLE);
        }
    }
}
