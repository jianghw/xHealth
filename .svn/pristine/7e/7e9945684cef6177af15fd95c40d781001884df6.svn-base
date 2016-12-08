package com.kaurihealth.kaurihealth.doctor_v;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.kaurihealth.chatlib.utils.LCIMConstants;
import com.kaurihealth.datalib.response_bean.DoctorRelationshipBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.DoctorCooperationAdapter;
import com.kaurihealth.kaurihealth.base_v.ChildBaseFragment;
import com.kaurihealth.kaurihealth.eventbus.DoctorFragmentRefreshEvent;
import com.kaurihealth.kaurihealth.eventbus.DoctorJumpEvent;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.mvplib.main_p.DoctorPresenter;
import com.kaurihealth.mvplib.main_p.IPatientView;
import com.kaurihealth.utilslib.ColorUtils;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.widget.ScrollChildSwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * 医患关系中的医生
 */
public class DoctorFragment extends ChildBaseFragment implements IPatientView {
    @Inject
    DoctorPresenter<IPatientView> mPresenter;

    @Bind(R.id.gv_content)
    GridView mGvContent;
    @Bind(R.id.swipe_refresh)
    ScrollChildSwipeRefreshLayout mSwipeRefresh;
    @Bind(R.id.tv_note)
    TextView tvNote;

    private List<DoctorRelationshipBean> mBeanList;
    private DoctorCooperationAdapter adapter;

    public static DoctorFragment newInstance() {
        return new DoctorFragment();
    }

    @Override
    protected int getFragmentLayoutID() {
        return R.layout.fragment_patient;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        mBeanList = new ArrayList<>();//empty data
        adapter = new DoctorCooperationAdapter(getContext(), mBeanList);
        mGvContent.setAdapter(adapter);
    }

    @Override
    protected void initDelayedData() {
        mSwipeRefresh.setColorSchemeColors(
                ColorUtils.setSwipeRefreshColors(getContext())
        );
        mSwipeRefresh.setScrollUpChild(mGvContent);
        mSwipeRefresh.setDistanceToTriggerSync(Global.Numerical.SWIPE_REFRESH);
        mSwipeRefresh.setOnRefreshListener(() -> mPresenter.loadingRemoteData(true)); //监听:下拉刷新和上拉加载时调用
        //注册事件
        EventBus.getDefault().register(this);
    }

    @Override
    protected void childLazyLoadingData() {
        mGvContent.setSelection(0);
        mPresenter.onSubscribe();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribe();
        if (mBeanList != null) mBeanList.clear();
        removeStickyEvent(DoctorJumpEvent.class);
        removeStickyEvent(DoctorFragmentRefreshEvent.class);
        //注销事件
        EventBus.getDefault().unregister(this);
    }

    /**
     * ----------------------------订阅事件----------------------------------
     **/
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventBusMain(DoctorJumpEvent event) {
        switchPageUI(event.getMessage());
        if (!event.getKauriHealthId().equals("0")) {
            try {
                Intent intent = new Intent();
                intent.setPackage(getContext().getPackageName());
                intent.setAction(LCIMConstants.CONVERSATION_ITEM_CLICK_ACTION);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.putExtra(LCIMConstants.PEER_ID, event.getKauriHealthId());
                getContext().startActivity(intent);
            } catch (ActivityNotFoundException exception) {
                Log.i(LCIMConstants.LCIM_LOG_TAG, exception.toString());
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(DoctorFragmentRefreshEvent event) {
        mPresenter.loadingRemoteData(true);
    }

    /**
     * ----------------------------view调用----------------------------------
     **/
    @Override
    public void switchPageUI(String className) {
        switch (className) {
            case Global.Jump.DoctorInfoActivity:
                skipTo(DoctorInfoActivity.class);
                break;
            default:
                break;
        }
    }

    /**
     * 更新list
     */
    @Override
    public void lazyLoadingDataSuccess(List<?> list) {
        tvNote.setVisibility(list != null ? !list.isEmpty() ? View.GONE : View.VISIBLE : View.VISIBLE);

        if (mBeanList.size() > 0) mBeanList.clear();
        mBeanList.addAll((Collection<? extends DoctorRelationshipBean>) list);
        adapter.notifyDataSetChanged();
    }

}
