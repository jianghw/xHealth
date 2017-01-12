package com.kaurihealth.kaurihealth.casehistory_v;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.kaurihealth.chatlib.utils.LCIMConstants;
import com.kaurihealth.datalib.response_bean.KauriHealthPendingConsultationReferralBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.ProcessedRecordsAdapter;
import com.kaurihealth.kaurihealth.base_v.ChildBaseFragment;
import com.kaurihealth.kaurihealth.eventbus.AcceptReasonEvent;
import com.kaurihealth.kaurihealth.eventbus.HomefragmentPatientEvent;
import com.kaurihealth.kaurihealth.eventbus.RejectReasonEvent;
import com.kaurihealth.kaurihealth.home_v.referral.AcceptReasonActivity;
import com.kaurihealth.kaurihealth.home_v.referral.RejectReasonActivity;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.mvplib.casehistory_p.IProcessedRecordsView;
import com.kaurihealth.mvplib.casehistory_p.ProcessedRecordsPresenter;
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
 * 待处理病历
 */
public class ProcessedRecordsFragment extends ChildBaseFragment implements IProcessedRecordsView,
        MaterialSearchBar.OnSearchActionListener, ProcessedRecordsAdapter.ItemClickBack {
    @Inject
    ProcessedRecordsPresenter<IProcessedRecordsView> mPresenter;

    @Bind(R.id.searchBar)
    MaterialSearchBar mSearchBar;
    @Bind(R.id.lv_content)
    ListView mLvContent;
    @Bind(R.id.swipe_refresh)
    ScrollChildSwipeRefreshLayout mSwipeRefresh;
    @Bind(R.id.tv_note)
    TextView mTvNote;

    private List<KauriHealthPendingConsultationReferralBean> mBeanList = new ArrayList<>();
    private List<KauriHealthPendingConsultationReferralBean> mSearchList;
    private ProcessedRecordsAdapter adapter;

    public static ProcessedRecordsFragment newInstance() {
        return new ProcessedRecordsFragment();
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
        adapter = new ProcessedRecordsAdapter(getContext(), mBeanList);
        adapter.setOnItemClickListener(this);
        mLvContent.setAdapter(adapter);

        EventBus.getDefault().register(this);
    }

    @Override
    protected void childLazyLoadingData() {
        mPresenter.onSubscribe();
    }

    @Override
    public void onStop() {
        super.onStop();
        removeStickyEvent(HomefragmentPatientEvent.class);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribe();
        if (!mBeanList.isEmpty()) mBeanList.clear();
        if (mSearchList != null) mSearchList.clear();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 数据加载处理
     */
    @Override
    public void newLoadPendingConsultationReferralsByDoctorIdSucceed(List<KauriHealthPendingConsultationReferralBean> list) {
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
    public void manualSearchSucceed(List<KauriHealthPendingConsultationReferralBean> list) {
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
     * 接受
     */
    @Override
    public void onItemAcceptClick(int id) {
        EventBus.getDefault().postSticky(new AcceptReasonEvent(id));
        skipToForResult(AcceptReasonActivity.class, null, Global.RequestCode.ACCEPT);
    }

    /**
     * 拒绝
     */
    @Override
    public void onItemRefuseClick(int id) {
        EventBus.getDefault().postSticky(new RejectReasonEvent(id));
        skipToForResult(RejectReasonActivity.class, null, Global.RequestCode.REJECT);
    }

    /**
     * 事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventBusMain(HomefragmentPatientEvent event) {
        mPresenter.loadingRemoteData(true);
    }

    /**
     * 医师沟通
     *
     * @param id
     */
    @Override
    public void onItemChatClick(String id) {
        if (id == null) return;
        try {
            Intent intent = new Intent();
            intent.setPackage(getActivity().getApplication().getPackageName());
            intent.setAction(LCIMConstants.CONVERSATION_ITEM_CLICK_ACTION);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra(LCIMConstants.PEER_ID, id);
            startActivity(intent);
        } catch (ActivityNotFoundException exception) {
            Log.i(LCIMConstants.LCIM_LOG_TAG, exception.toString());
        }
    }
}
