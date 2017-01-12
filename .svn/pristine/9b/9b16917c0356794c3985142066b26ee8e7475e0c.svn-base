package com.kaurihealth.kaurihealth.doctor_new;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.request_bean.bean.InitialiseSearchRequestBean;
import com.kaurihealth.datalib.request_bean.bean.SearchPatientBean;
import com.kaurihealth.datalib.request_bean.builder.InitialiseSearchRequestBeanBuilder;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.DoctorRelationshipBean;
import com.kaurihealth.datalib.response_bean.MainPagePatientDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.PatientSearchAdapter;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.doctor_v.DoctorInfoActivity;
import com.kaurihealth.kaurihealth.eventbus.DoctorRelationshipBeanEvent;
import com.kaurihealth.kaurihealth.eventbus.OpenAnAccountToAllMedicalRecordFragmentEvent;
import com.kaurihealth.kaurihealth.open_account_v.OpenAnAccountActivity;
import com.kaurihealth.kaurihealth.record_details_v.RecordDetailsActivity;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.mvplib.patient_new.IPatientSearchView;
import com.kaurihealth.mvplib.patient_new.PatientSearchPresenter;
import com.kaurihealth.utilslib.ColorUtils;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.widget.MaterialSearchBar;
import com.kaurihealth.utilslib.widget.ScrollChildSwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * Created by mip on 2017/1/4.
 * <p/>
 * Describe: 患者搜索页面
 */

public class PatientSearchActivity extends BaseActivity implements IPatientSearchView,
        MaterialSearchBar.OnSearchActionListener, PatientSearchAdapter.IAddPatientListener {
    @Inject
    PatientSearchPresenter<IPatientSearchView> mPresenter;

    @Bind(R.id.searchBar)
    MaterialSearchBar searchBar;
    @Bind(R.id.doctor_search_lv)
    ListView doctor_search_lv;
    @Bind(R.id.tv_note)
    TextView tv_note;
    @Bind(R.id.tv_note_new)
    RelativeLayout tv_note_new;

    @Bind(R.id.tv_skip)
    TextView tv_skip;
    @Bind(R.id.swipe_refresh)
    ScrollChildSwipeRefreshLayout mSwipeRefresh;

    List<SearchPatientBean> mBeanList;
    private String mEditText;
    private PatientSearchAdapter adapter;
    private int mDoctorId;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.patient_search;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        mSwipeRefresh.setColorSchemeColors(
                ColorUtils.setSwipeRefreshColors(this)
        );
        mSwipeRefresh.setScrollUpChild(mSwipeRefresh);
        mSwipeRefresh.setSize(SwipeRefreshLayout.DEFAULT);
        mSwipeRefresh.setDistanceToTriggerSync(Global.Numerical.SWIPE_REFRESH);
        mSwipeRefresh.setOnRefreshListener(() -> mPresenter.loadingRemoteData(true));

        searchBar.setOnSearchActionListener(this);
    }

    @Override
    protected void initDelayedData() {
        initNewBackBtn("搜索添加患者");

        mBeanList = new ArrayList<>();
        adapter = new PatientSearchAdapter(this, mBeanList, this);
        doctor_search_lv.setAdapter(adapter);
    }

    /**
     * item的点击事件
     */
    @OnItemClick(R.id.doctor_search_lv)
    public void onClickToDoctorItem(int position) {
        MainPagePatientDisplayBean bean = mBeanList.get(position).getItemsBean();
        Bundle bundle = new Bundle();
        bundle.putInt(Global.Bundle.SEARCH_PATIENT, bean.getPatientId());
        bundle.putString(Global.Bundle.SEARCH_GENDER, bean.getGender());
        bundle.putBoolean(Global.Bundle.PATIENT_SHIP, true);
        skipToBundle(RecordDetailsActivity.class, bundle);
    }

    /**
     * 没有患者的情况下  建立患者档案
     */
    @OnClick(R.id.tv_skip)
    public void onClick() {
        skipTo(OpenAnAccountActivity.class);
    }

    /**
     * 开始搜索
     */
    @Override
    public void onSearchStart() {

    }

    /**
     * 确认搜索
     *
     * @param text
     */
    @Override
    public void onSearchConfirmed(CharSequence text) {
        mEditText = text.toString().trim();
        if (!TextUtils.isEmpty(mEditText)) {
            mPresenter.onSubscribe();
        }
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
     */
    @Override
    public InitialiseSearchRequestBean getCurrentSearchRequestBean() {
        return new InitialiseSearchRequestBeanBuilder().Build("default", mEditText, LocalData.getLocalData().getMyself().doctorId);
    }

    /**
     * 返回结果
     */
    @Override
    public void updataDataSucceed(List<SearchPatientBean> bean) {
        if (mBeanList.size() > 0) mBeanList.clear();
        tv_note_new.setVisibility(bean.size() == 0 ? View.VISIBLE : View.GONE);

        mBeanList.addAll(bean);
        adapter.notifyDataSetChanged();
    }

    /**
     * 得到当前的doctor的Id
     */
    @Override
    public int getDoctorId() {
        return mDoctorId;
    }

    /**
     * 处理返回过来的数据 并跳转到activity
     */
    @Override
    public void getResult(DoctorRelationshipBean relationshipBean, boolean b) {
        EventBus.getDefault().postSticky(new DoctorRelationshipBeanEvent(relationshipBean, b, "mark"));
        switchPageUI("跳转DoctorInfoActivity");
    }

    /**
     * 添加好友回馈
     */
    @Override
    public void insertPatientSucceed(DoctorPatientRelationshipBean bean) {
        if (bean != null) {
            showToast("添加患者成功");
            EventBus.getDefault().postSticky(new OpenAnAccountToAllMedicalRecordFragmentEvent());//添加患者成功后刷新
            adapter.notifyDataSetChanged();
            finishCur();
        } else {
            showToast("添加患者失败");
        }
    }

    //下拉刷新
    @Override
    public void loadingIndicator(boolean flag) {
        if (mSwipeRefresh == null) return;
        mSwipeRefresh.post(() -> mSwipeRefresh.setRefreshing(flag));
    }

    @Override
    public void switchPageUI(String className) {
        skipTo(DoctorInfoActivity.class);
    }

    /**
     * 患者添加
     */
    @Override
    public void addPatient(int patientId) {
        for (SearchPatientBean bean : mBeanList) {
            if (bean.getItemsBean().getPatientId() == patientId)
                bean.setAdd(true);
        }
        mPresenter.insertNewRelationshipByDoctor(patientId);
    }
}
