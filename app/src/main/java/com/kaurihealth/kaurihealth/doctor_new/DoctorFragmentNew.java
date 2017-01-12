package com.kaurihealth.kaurihealth.doctor_new;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaurihealth.datalib.response_bean.DoctorRelationshipBean;
import com.kaurihealth.datalib.response_bean.DoctorRelationshipBeanWrapper;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.DoctorContactsAdapter;
import com.kaurihealth.kaurihealth.base_v.BaseFragment;
import com.kaurihealth.kaurihealth.doctor_new.utils.Cn2Py;
import com.kaurihealth.kaurihealth.doctor_new.utils.Constant;
import com.kaurihealth.kaurihealth.doctor_new.view.WaveSideBar;
import com.kaurihealth.kaurihealth.doctor_v.DoctorInfoActivity;
import com.kaurihealth.kaurihealth.eventbus.DoctorRelationshipBeanEvent;
import com.kaurihealth.kaurihealth.home_v.request.DoctorRequestActivity;
import com.kaurihealth.kaurihealth.main_v.MainActivity;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.mvplib.doctor_new.DoctorPresenter;
import com.kaurihealth.mvplib.doctor_new.IDoctorView;
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

import static rx.Observable.from;

/**
 * Created by mip on 2016/12/13.
 * <p/>
 * Describe:
 */

public class DoctorFragmentNew extends BaseFragment implements IDoctorView,
        DoctorContactsAdapter.MyItemClickListener, MaterialSearchBar.OnSearchActionListener {
    @Inject
    DoctorPresenter<IDoctorView> mPresenter;

    @Bind(R.id.rv_contacts)
    RecyclerView rv_contacts;
    @Bind(R.id.side_bar)
    WaveSideBar side_bar;
    @Bind(R.id.swipe_refresh)
    ScrollChildSwipeRefreshLayout mSwipeRefresh;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.tv_more)
    ImageView tv_more;
    @Bind(R.id.reddot)
    ImageView reddot;
    @Bind(R.id.tv_note)
    TextView tv_note;
    @Bind(R.id.searchBar)
    MaterialSearchBar mSearchBar;

    DoctorContactsAdapter adapter;

    private List<DoctorRelationshipBean> mBeanList;

    private List<DoctorRelationshipBeanWrapper> beanList = new ArrayList<>();
    private List<DoctorRelationshipBeanWrapper> SearchBeanList;

    public static DoctorFragmentNew getInstance() {
        return new DoctorFragmentNew();
    }

    @Override
    protected int getFragmentLayoutID() {
        return R.layout.doctor_fragment_new;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        mSearchBar.setOnSearchActionListener(this);

        mSwipeRefresh.setColorSchemeColors(
                ColorUtils.setSwipeRefreshColors(getContext())
        );
        mSwipeRefresh.setScrollUpChild(rv_contacts);
        mSwipeRefresh.setDistanceToTriggerSync(Global.Numerical.SWIPE_REFRESH);
        mSwipeRefresh.setOnRefreshListener(() -> mPresenter.loadingRemoteData(true));
    }

    @Override
    protected void initDelayedData() {
        tv_title.setText("医生");

        mBeanList = new ArrayList<>();
        rv_contacts.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new DoctorContactsAdapter(getContext(), beanList);
        rv_contacts.setAdapter(adapter);

        side_bar.setOnSelectIndexItemListener(index -> {
            for (int i = 0; i < beanList.size(); i++) {
                if (beanList.get(i).getName() != null) {
                    if (beanList.get(i).getIndex().equals(index)) {
                        ((LinearLayoutManager) rv_contacts.getLayoutManager()).scrollToPositionWithOffset(i, 0);
                        return;
                    }
                }
            }
        });

        adapter.setOnItemClickListener(this);
    }

    /**
     * 添加医生
     */
    @OnClick(R.id.tv_more)
    public void onClickToMore() {
        skipTo(DoctorSearchActivity.class);
    }

    /**
     * 新医生
     */
    @OnClick(R.id.LY_new_friend)
    public void onClickToNewFriend() {
        skipTo(DoctorRequestActivity.class);
    }

    @Override
    public void onItemClick(View view, int postion) {
        EventBus.getDefault().postSticky(new DoctorRelationshipBeanEvent(beanList.get(postion).getName(), true, null));
        skipTo(DoctorInfoActivity.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadPendingDoctorRelationships(true);
    }

    /**
     * 数据加载
     */
    @Override
    protected void lazyLoadingData() {
        mPresenter.onSubscribe();
        mPresenter.loadPendingDoctorRelationships(true);
    }

    /**
     * 成功数据处理
     */
    @Override
    public void lazyLoadingDataSuccess(List<DoctorRelationshipBean> list) {
        if (mBeanList.size() > 0) mBeanList.clear();
        if (list != null) {
            mBeanList.addAll(list);
            if (beanList.size() > 0) beanList.clear();
            for (int i = 0; i < Constant.DEFAULT_INDEX_ITEMS.length; i++) {
                for (int j = 0; j < mBeanList.size(); j++) {
                    if (Cn2Py.getRootChar(mBeanList.get(j).getRelatedDoctor().getFullName()).equals(Constant.DEFAULT_INDEX_ITEMS[i])) {
                        beanList.add(new DoctorRelationshipBeanWrapper(Constant.DEFAULT_INDEX_ITEMS[i], mBeanList.get(j)));
                    }
                }
            }
            rv_contacts.setVisibility(View.VISIBLE);
            side_bar.setVisibility(View.VISIBLE);
            tv_note.setVisibility(!list.isEmpty() ? View.GONE : View.VISIBLE);
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * 判断是否有医生请求
     */
    @Override
    public void isHasDoctorRequest(boolean b) {
        reddot.setVisibility(b ? View.VISIBLE : View.GONE);
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) mainActivity.setUiBottomTip(1, b ? 10 : 0);
    }

    /**
     * 开始搜索
     */
    @Override
    public void onSearchStart() {
        if (SearchBeanList == null) {
            SearchBeanList = new ArrayList<>();
        }else {
            SearchBeanList.clear();
        }
        SearchBeanList.addAll(beanList);
    }

    /**
     * 搜索
     */
    @Override
    public void onSearchConfirmed(CharSequence text) {
        from(SearchBeanList)
                .filter(doctorRelationshipBeanWrapper ->
                        doctorRelationshipBeanWrapper
                                .getName().getRelatedDoctor()
                                .getFullName()
                                .contains(text))
                .toList()
                .subscribe(doctorRelationshipBeanWrappers -> {
                    if (doctorRelationshipBeanWrappers.size() == 0) {
                        tv_note.setVisibility(View.VISIBLE);
                        rv_contacts.setVisibility(View.INVISIBLE);
                        side_bar.setVisibility(View.INVISIBLE);
                    } else {
                        if (beanList.size() > 0) beanList.clear();
                        rv_contacts.setVisibility(View.VISIBLE);
                        side_bar.setVisibility(View.VISIBLE);
                        tv_note.setVisibility(View.GONE);

                        beanList.addAll(doctorRelationshipBeanWrappers);
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    /**
     * 退出搜索
     */
    @Override
    public void onSearchCompleteBack() {
        if (beanList.size() > 0) beanList.clear();
        beanList.addAll(SearchBeanList);
        adapter.notifyDataSetChanged();

        tv_note.setVisibility(View.GONE);
        rv_contacts.setVisibility(View.VISIBLE);
        side_bar.setVisibility(View.VISIBLE);
    }

    /**
     * 删除键回调
     */
    @Override
    public void onSearchDeleteBack() {
        if (!beanList.isEmpty()) beanList.clear();
        beanList.addAll(SearchBeanList);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!beanList.isEmpty()) beanList.clear();
        if (SearchBeanList != null) SearchBeanList.clear();
        mPresenter.unSubscribe();
    }

    @Override
    public void switchPageUI(String className) {

    }
}
