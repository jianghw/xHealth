package com.kaurihealth.kaurihealth.doctor_new;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.request_bean.bean.InitialiseSearchRequestBean;
import com.kaurihealth.datalib.request_bean.bean.SearchBooleanResultBean;
import com.kaurihealth.datalib.request_bean.builder.InitialiseSearchRequestBeanBuilder;
import com.kaurihealth.datalib.response_bean.DoctorRelationshipBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.DoctorSearchAdapter;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.doctor_v.DoctorInfoActivity;
import com.kaurihealth.kaurihealth.eventbus.DoctorRelationshipBeanEvent;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.mvplib.doctor_new.DoctorSearchPresenter;
import com.kaurihealth.mvplib.doctor_new.IDoctorSearchView;
import com.kaurihealth.utilslib.widget.MaterialSearchBar;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnItemClick;

/**
 * Created by mip on 2016/12/15.
 */

public class DoctorSearchActivity extends BaseActivity implements IDoctorSearchView,
        MaterialSearchBar.OnSearchActionListener {
    @Inject
    DoctorSearchPresenter<IDoctorSearchView> mPresenter;

    @Bind(R.id.searchBar)
    MaterialSearchBar searchBar;
    @Bind(R.id.doctor_search_lv)
    ListView doctor_search_lv;
    @Bind(R.id.tv_note)
    TextView tv_note;

    List<SearchBooleanResultBean> mBeanList;
    private String mEditText;
    private DoctorSearchAdapter adapter;
    private int mDoctorId;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.doctor_search;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        searchBar.setOnSearchActionListener(this);
    }

    @Override
    protected void initDelayedData() {
        initNewBackBtn("搜索添加医生");

        mBeanList = new ArrayList<>();
        adapter = new DoctorSearchAdapter(this, mBeanList);
        doctor_search_lv.setAdapter(adapter);
    }

    /**
     * item的点击事件
     * @param position
     */
    @OnItemClick(R.id.doctor_search_lv)
    public void onClickToDoctorItem(int position){
//        mDoctorId = Integer.parseInt(mBeanList.get(position).getItemsBean().getDoctorid());
        mDoctorId = mBeanList.get(position).getItemsBean().getDoctorId();
        mPresenter.LoadDoctorDetailByDoctorId();
    }

    /**
     * 确认搜索
     *
     * @param text
     */
    @Override
    public void onSearchConfirmed(CharSequence text) {
        mEditText = text.toString();
        if (mEditText != null) {
            mPresenter.onSubscribe();
        }
    }

    /**
     * 开始搜索
     */
    @Override
    public void onSearchStart() {

    }

    /**
     * 返回搜索
     */
    @Override
    public void onSearchCompleteBack() {

    }

    @Override
    public void onSearchDeleteBack() {

    }

    /**
     * 得到搜索请求bean
     *
     * @return
     */
    @Override
    public InitialiseSearchRequestBean getCurrentSearchRequestBean() {
        return new InitialiseSearchRequestBeanBuilder().Build("default", mEditText, LocalData.getLocalData().getMyself().doctorId);
    }

    /**
     * 返回结果
     *
     * @param bean
     */
    @Override
    public void updataDataSucceed(List<SearchBooleanResultBean> bean) {
        if (mBeanList.size() > 0) mBeanList.clear();
        tv_note.setVisibility(bean != null ? View.GONE : View.VISIBLE);
        doctor_search_lv.setVisibility(bean != null ? View.VISIBLE : View.GONE);
        if (bean != null) {
            mBeanList.addAll(bean);
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 得到当前的doctor的Id
     * @return
     */
    @Override
    public int getDoctorId() {
        return mDoctorId;
    }

    /**
     * 处理返回过来的数据 并跳转到activity
     * @param relationshipBean
     * @param b
     */
    @Override
    public void getResult(DoctorRelationshipBean relationshipBean, boolean b) {
        EventBus.getDefault().postSticky(new DoctorRelationshipBeanEvent(relationshipBean, b, "mark"));
        switchPageUI("跳转DoctorInfoActivity");
    }

    @Override
    public void switchPageUI(String className) {
        skipTo(DoctorInfoActivity.class);
    }
}
