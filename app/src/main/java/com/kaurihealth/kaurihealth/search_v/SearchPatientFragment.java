package com.kaurihealth.kaurihealth.search_v;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.SearchBooleanPatientBean;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.SearchHistoryAdapter;
import com.kaurihealth.kaurihealth.adapter.SearchPatientAdapter;
import com.kaurihealth.kaurihealth.base_v.BaseFragment;
import com.kaurihealth.kaurihealth.eventbus.PatientFragmentEvent;
import com.kaurihealth.kaurihealth.eventbus.SearchAtyToFgtEvent;
import com.kaurihealth.kaurihealth.util.VerticalSpaceItemDecoration;
import com.kaurihealth.mvplib.home_p.ISearchpatientFragmentView;
import com.kaurihealth.mvplib.home_p.SearchPatientFragmentPresenter;

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
public class SearchPatientFragment extends BaseFragment implements
        SearchHistoryAdapter.ItemClickBack, ISearchpatientFragmentView, SearchPatientAdapter.ItemClickBack {
    @Inject
    SearchPatientFragmentPresenter<ISearchpatientFragmentView> mPresenter;

    @Bind(R.id.tv_hint)
    TextView mTvHint;
    @Bind(R.id.rv_data)
    RecyclerView mRvData;
    @Bind(R.id.rv_history)
    RecyclerView mRvHistory;

    List<SearchBooleanPatientBean> dataContainer = new ArrayList<>();
    private SearchPatientAdapter mDataAdapter;
    private SearchHistoryAdapter mHistoryAdapter;
    private boolean isSearching;//是否进行搜索

    public static SearchPatientFragment newInstance() {
        return new SearchPatientFragment();
    }

    @Override
    protected int getFragmentLayoutID() {
        return R.layout.fragment_search_common;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        mDataAdapter = new SearchPatientAdapter(getActivity(), dataContainer,this);
        mRvData.setAdapter(mDataAdapter);
        mRvData.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvData.addItemDecoration(new VerticalSpaceItemDecoration(10));
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
        if (getEditTextContent() != null && getEditTextContent().length() > 0 && isSearching) {
            mPresenter.onSubscribe();
        }else {
            setEditTextHintContent();
        }
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
        isSearching = true;
        if (isVisible) {
            mPresenter.onSubscribe();
        }
    }

    @Override
    public void processingData(List<SearchBooleanPatientBean> list) {
        isSearching = false;
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

    private void updateDataToRecyclerView(List<SearchBooleanPatientBean> list) {
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
     * 搜索
     */
    @Override
    public void onItemClick(String content) {
        ((SearchActivity) getActivity()).onSearchHistory(content);
    }

    /**
     * 输入框内容
     */
    @Override
    public String getEditTextContent() {
        return ((SearchActivity) getActivity()).getEditTextSearch();
    }

    /**
     * 设置当前edit的提示内容
     */
    @Override
    public void setEditTextHintContent() {
        ((SearchActivity) getActivity()).setEditTextHint();
    }

    @Override
    public void switchPageUI(String className) {
    }

    /**
     * 添加患者
     */
    @Override
    public void onItemTextClick(int patientID) {
        mPresenter.insertNewRelationshipByDoctor(patientID);
    }

    @Override
    public void insertPatientSucceed(DoctorPatientRelationshipBean bean) {
        if(bean!=null){
            showToast("添加患者成功");
            EventBus.getDefault().postSticky(new PatientFragmentEvent());//让其刷新
            SearchActivity activity = (SearchActivity) getActivity();
            activity.skipPatientFragment();
        }else{
            showToast("添加患者失败");
        }
    }


}
