package com.kaurihealth.kaurihealth.search_v;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.SearchBooleanResultBean;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.SearchBaseAdapter;
import com.kaurihealth.kaurihealth.adapter.SearchHistoryAdapter;
import com.kaurihealth.kaurihealth.base_v.BaseFragment;
import com.kaurihealth.kaurihealth.eventbus.SearchAtyToFgtEvent;
import com.kaurihealth.kaurihealth.home_v.VerificationActivity;
import com.kaurihealth.mvplib.home_p.ISearchCommonFragmentView;
import com.kaurihealth.mvplib.home_p.SearchCommonFragmentPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by jianghw on 2016/10/28.
 * <p/>
 * Describe: 搜索页--所有
 */
public class SearchHospotalFragment extends BaseFragment implements SearchBaseAdapter.ItemClickBack,
        SearchHistoryAdapter.ItemClickBack, ISearchCommonFragmentView {
    @Inject
    SearchCommonFragmentPresenter<ISearchCommonFragmentView> mPresenter;

    @Bind(R.id.tv_hint)
    TextView mTvHint;
    @Bind(R.id.rv_data)
    RecyclerView mRvData;
    @Bind(R.id.rv_history)
    RecyclerView mRvHistory;

    private String aDefault;//过滤数据标记
    private List<SearchBooleanResultBean> beanList;//eventbus
    List<SearchBooleanResultBean> dataContainer = new ArrayList<>();
    private SearchBaseAdapter mDataAdapter;
    private SearchHistoryAdapter mHistoryAdapter;

    public static SearchHospotalFragment newInstance() {
        return new SearchHospotalFragment();
    }

    @Override
    protected int getFragmentLayoutID() {
        return R.layout.fragment_search_common;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        mDataAdapter = new SearchBaseAdapter(getActivity(), dataContainer);
        mRvData.setAdapter(mDataAdapter);
        mRvData.setLayoutManager(new LinearLayoutManager(getContext()));
        mDataAdapter.setItemClickBack(this);
    }

    @Override
    protected void initDelayedData() {
        mHistoryAdapter = new SearchHistoryAdapter(this, ((SearchActivity) getActivity()).getSearchHistroyList(), this);
        mRvHistory.setAdapter(mHistoryAdapter);
        mRvHistory.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));

        EventBus.getDefault().register(this);
    }

    @Override
    protected void lazyLoadingData() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!dataContainer.isEmpty()) dataContainer.clear();
        mPresenter.unSubscribe();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 事件，处理数据
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventBusMain(SearchAtyToFgtEvent event) {
        beanList = event.getSearchResultBean();
        mPresenter.onSubscribe();
    }

    @Override
    public void processingData(List<SearchBooleanResultBean> list) {
        boolean isEmpty = (list == null || list != null && list.isEmpty());
        showHistoryData(isEmpty, isEmpty ? 0 : list.size());
        if (isEmpty) {
            updateHistroyToRecyclerView();
        } else {
            updateDataToRecyclerView(list);
        }
    }

    /**
     * 显示处理
     */
    private void showHistoryData(boolean b, int size) {
        String error = String.format(getString(R.string.no_search_data), getEditTextContent());
        String succeed = String.format(getString(R.string.search_data_size), size);
        setTvHint(b ? error : succeed);
        mRvData.setVisibility(b ? View.GONE : View.VISIBLE);
        mRvHistory.setVisibility(b ? View.VISIBLE : View.GONE);
    }

    private void updateHistroyToRecyclerView() {
        mHistoryAdapter.notifyDataSetChanged();
    }

    private void updateDataToRecyclerView(List<SearchBooleanResultBean> list) {
        if (!dataContainer.isEmpty()) dataContainer.clear();
        dataContainer.addAll(list);
        mDataAdapter.notifyDataSetChanged();
    }

    public String getTvHint() {
        return mTvHint.getText().toString().trim();
    }

    public void setTvHint(String tvHint) {
        mTvHint.setText(tvHint);
    }

    /**
     * item点击事件
     */
    @Override
    public void onItemTextClick(String doctorID) {
        Bundle bundle = new Bundle();
        bundle.putInt("relatedDoctorId", Integer.parseInt(doctorID));
        skipToForResult(VerificationActivity.class, bundle, 100);
    }

    /**
     * 搜索
     */
    @Override
    public void onItemClick(String content) {
        ((SearchActivity) getActivity()).onSearchHistory(content);
    }

    public void setTitle(String mDefault) {
        this.aDefault = mDefault;
    }

    @Override
    public String getTitle() {
        return aDefault;
    }

    @Override
    public List<SearchBooleanResultBean> getSearchList() {
        return beanList;
    }

    /**
     * 输入框内容
     */
    @Override
    public String getEditTextContent() {
        return ((SearchActivity) getActivity()).getEditTextSearch();
    }

    @Override
    public void switchPageUI(String className) {
//TODO
    }



}
