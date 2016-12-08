package com.kaurihealth.kaurihealth.patient_v.doctor_team;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.kaurihealth.chatlib.utils.LCIMConstants;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.RelationshipBeanWrapper;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.DoctorTeamAdapter;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.doctor_v.DoctorInfoActivity;
import com.kaurihealth.kaurihealth.eventbus.DoctorPatientRelationshipBeanEvent;
import com.kaurihealth.kaurihealth.search_v.VerificationActivity;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.mvplib.patient_p.DoctorTeamPresenter;
import com.kaurihealth.mvplib.patient_p.IDoctorTeamView;
import com.kaurihealth.utilslib.ColorUtils;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.widget.ScrollChildSwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;


/**
 * 医疗团队
 */
public class DoctorTeamActivity extends BaseActivity implements IDoctorTeamView, DoctorTeamAdapter.ItemClickBack {
    @Inject
    DoctorTeamPresenter<IDoctorTeamView> mPresenter;

    @Bind(R.id.gv_content)
    GridView mGvContent;
    @Bind(R.id.tv_note)
    TextView tv_note;
    @Bind(R.id.swipe_refresh)
    ScrollChildSwipeRefreshLayout mSwipeRefresh;

    private ArrayList<RelationshipBeanWrapper> mBeanList;
    private DoctorTeamAdapter adapter;
    private int mPatientId;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_doctor_team;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        initNewBackBtn(getString(R.string.doctor_team));

        mSwipeRefresh.setColorSchemeColors(
                ColorUtils.setSwipeRefreshColors(getApplicationContext())
        );
        mSwipeRefresh.setScrollUpChild(mGvContent);
        mSwipeRefresh.setDistanceToTriggerSync(Global.Numerical.SWIPE_REFRESH);
        mSwipeRefresh.setOnRefreshListener(() -> mPresenter.onSubscribe());
        Bundle bundle = getBundle();
        if (bundle != null) {
            mPatientId = bundle.getInt(Global.Bundle.DOCTOR_TEAM);
        }
    }

    @Override
    protected void initDelayedData() {
        mBeanList = new ArrayList<>();
        adapter = new DoctorTeamAdapter(getApplicationContext(), mBeanList, this);
        mGvContent.setAdapter(adapter);

        mPresenter.onSubscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBeanList != null) mBeanList.clear();
        mPresenter.unSubscribe();
    }

    @Override
    public void loadingIndicator(boolean flag) {
        if (mSwipeRefresh == null) return;
        mSwipeRefresh.post(() -> mSwipeRefresh.setRefreshing(flag));
    }

    @Override
    public int getPatientId() {
        return mPatientId;
    }

    @Override
    public void lazyLoadingDataSuccess(List<RelationshipBeanWrapper> list) {
        tv_note.setVisibility(list != null ? !list.isEmpty() ? View.GONE : View.VISIBLE : View.VISIBLE);
        if (mBeanList.size() > 0) mBeanList.clear();
        mBeanList.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void switchPageUI(String className) {

    }

    /**
     * 聊天
     *
     * @param kauriHealthId
     */
    @Override
    public void onItemChatClick(String kauriHealthId) {
        try {
            Intent intent = new Intent();
            intent.setPackage(getApplication().getPackageName());
            intent.setAction(LCIMConstants.CONVERSATION_ITEM_CLICK_ACTION);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra(LCIMConstants.PEER_ID, kauriHealthId);
            startActivity(intent);
        } catch (ActivityNotFoundException exception) {
            Log.i(LCIMConstants.LCIM_LOG_TAG, exception.toString());
        }
    }

    /**
     * 添加医生
     *
     * @param doctorID
     */
    @Override
    public void onItemAddClick(int doctorID) {
        Bundle bundle = new Bundle();
        bundle.putInt("relatedDoctorId", doctorID);
        skipToForResult(VerificationActivity.class, bundle, 100);
    }

    /**
     * 医生详情  onItemInfoClick
     *
     * @param doctorWrapper
     */
    @Override
    public void onItemInfoClick(DoctorPatientRelationshipBean doctorWrapper, String type) {
        EventBus.getDefault().postSticky(new DoctorPatientRelationshipBeanEvent(doctorWrapper, type));
        skipTo(DoctorInfoActivity.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            mPresenter.onSubscribe();
        }
    }
}
