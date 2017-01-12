package com.kaurihealth.kaurihealth.casehistory_v;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.kaurihealth.datalib.response_bean.MainPagePatientDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.AllMedicalRecordsAdapter;
import com.kaurihealth.kaurihealth.base_v.ChildBaseFragment;
import com.kaurihealth.kaurihealth.eventbus.OpenAnAccountToAllMedicalRecordFragmentEvent;
import com.kaurihealth.kaurihealth.record_details_v.RecordDetailsActivity;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.mvplib.casehistory_p.AllMedicalRecordsPresenter;
import com.kaurihealth.mvplib.casehistory_p.IAllMedicalRecordsView;
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

/**
 * 全部病历
 */
public class AllMedicalRecordsFragment extends ChildBaseFragment implements
        IAllMedicalRecordsView, MaterialSearchBar.OnSearchActionListener, AdapterView.OnItemClickListener {
    @Inject
    AllMedicalRecordsPresenter<IAllMedicalRecordsView> mPresenter;

    @Bind(R.id.searchBar)
    MaterialSearchBar mSearchBar;
    @Bind(R.id.lv_content)
    ListView mLvContent;
    @Bind(R.id.swipe_refresh)
    ScrollChildSwipeRefreshLayout mSwipeRefresh;
    @Bind(R.id.tv_note)
    TextView mTvNote;

    private List<MainPagePatientDisplayBean> mBeanList = new ArrayList<>();
    private List<MainPagePatientDisplayBean> mSearchList;
    private AllMedicalRecordsAdapter adapter;

    public static AllMedicalRecordsFragment newInstance() {
        return new AllMedicalRecordsFragment();
    }

    @Override
    protected int getFragmentLayoutID() {
        return R.layout.fragment_all_medical_record;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        mSwipeRefresh.setColorSchemeColors(
                ColorUtils.setSwipeRefreshColors(getContext())
        );
        mSwipeRefresh.setScrollUpChild(mLvContent);
        mSwipeRefresh.setSize(SwipeRefreshLayout.DEFAULT);
        mSwipeRefresh.setDistanceToTriggerSync(Global.Numerical.SWIPE_REFRESH);
        mSwipeRefresh.setOnRefreshListener(() -> mPresenter.loadingRemoteData(true));

        mSearchBar.setOnSearchActionListener(this);
    }

    @Override
    protected void initDelayedData() {
        adapter = new AllMedicalRecordsAdapter(getContext(), mBeanList);
        mLvContent.setAdapter(adapter);
        mLvContent.setOnItemClickListener(this);

        EventBus.getDefault().register(this);
    }

    @Override
    protected void childLazyLoadingData() {
        mPresenter.onSubscribe();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mPresenter.unSubscribe();
        if (!mBeanList.isEmpty()) mBeanList.clear();
        if (mSearchList != null) mSearchList.clear();
    }

    /**
     * 建立新的患者  然后返回刷新
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(OpenAnAccountToAllMedicalRecordFragmentEvent event) {
        mPresenter.loadingRemoteData(true);
    }

    /**
     * 数据加载处理
     */
    @Override
    public void loadDoctorMainPageSucceed(List<MainPagePatientDisplayBean> list) {
        mTvNote.setVisibility(list == null || list.isEmpty() ? View.VISIBLE : View.GONE);

        if (!mBeanList.isEmpty()) mBeanList.clear();
        if (list != null) mBeanList.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void switchPageUI(String className) {
    }

    /**
     * 开始搜索
     */
    @Override
    public void onSearchStart() {
        mSwipeRefresh.setEnabled(false);

        if (mSearchList == null) {
            mSearchList = new ArrayList<>();
        } else {
            mSearchList.clear();
        }
        mSearchList.addAll(mBeanList);
    }

    /**
     * 搜索
     */
    @Override
    public void onSearchConfirmed(CharSequence text) {
        mPresenter.manualSearch(text, mBeanList);
    }

    @Override
    public void manualSearchSucceed(List<MainPagePatientDisplayBean> list) {
        mTvNote.setVisibility(list == null || list.isEmpty() ? View.VISIBLE : View.GONE);

        if (!mBeanList.isEmpty()) mBeanList.clear();
        if (list != null) mBeanList.addAll(list);
        adapter.notifyDataSetChanged();
    }

    /**
     * 退出搜索
     */
    @Override
    public void onSearchCompleteBack() {
        mSwipeRefresh.setEnabled(true);

        if (!mBeanList.isEmpty()) mBeanList.clear();
        mBeanList.addAll(mSearchList);
        mTvNote.setVisibility(mBeanList == null || mBeanList.isEmpty() ? View.VISIBLE : View.GONE);
        adapter.notifyDataSetChanged();
    }

    /**
     * 删除键回调
     */
    @Override
    public void onSearchDeleteBack() {
        if (!mBeanList.isEmpty()) mBeanList.clear();
        if (mBeanList != null) mBeanList.addAll(mSearchList);
    }

    /**
     * item 点击
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MainPagePatientDisplayBean bean = mBeanList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt(Global.Bundle.SEARCH_PATIENT, bean.getPatientId());
        bundle.putString(Global.Bundle.SEARCH_GENDER, bean.getGender());
        skipToBundle(RecordDetailsActivity.class, bundle);
    }
}
