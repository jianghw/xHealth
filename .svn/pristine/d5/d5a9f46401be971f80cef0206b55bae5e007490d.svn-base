package com.kaurihealth.kaurihealth.patient_v.subsequent_visit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.kaurihealth.datalib.response_bean.ReferralMessageAlertDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.VisitRecordAdapter;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.mvplib.patient_p.IVisitRecordView;
import com.kaurihealth.mvplib.patient_p.VisitRecordPresenter;
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
 * Describe:复诊提醒
 */

public class VisitRecordActivity extends BaseActivity implements IVisitRecordView, VisitRecordAdapter.ItemClickBack {
    @Inject
    VisitRecordPresenter<IVisitRecordView> mPresenter;

    @Bind(R.id.lv_visit)
    ListView mLvVisit;
    @Bind(R.id.swipe_refresh)
    ScrollChildSwipeRefreshLayout mSwipeRefresh;
    @Bind(R.id.tv_no_visit)
    TextView mTvNoVisit;
    @Bind(R.id.tv_more)
    TextView mTvMore;

    private VisitRecordAdapter adapter;
    private List<ReferralMessageAlertDisplayBean> beanList;
    private int doctorPatientReplationshipId;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_visit_record;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        initNewBackBtn(getString(R.string.visit_record));
        mTvMore.setText(getString(R.string.more_add));

        Bundle bundle = getBundle();
        if (bundle != null) {
            doctorPatientReplationshipId = bundle.getInt(Global.Bundle.VISIT_PATIENT);
        }

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
        adapter = new VisitRecordAdapter(this, beanList, this);
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
        Bundle bundle = new Bundle();
        bundle.putInt(Global.Bundle.VISIT_PATIENT, doctorPatientReplationshipId);
        skipToForResult(AddVisitRecordActivity.class, bundle, Global.RequestCode.ADD_VISIT);
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
    public int getDoctorPatientShipId() {
        return doctorPatientReplationshipId;
    }

    @Override
    public void loadHealthConditionSucceed(List<ReferralMessageAlertDisplayBean> list) {
        mTvNoVisit.setVisibility(list != null ? !list.isEmpty() ? View.GONE : View.VISIBLE : View.VISIBLE);
        if (!beanList.isEmpty()) beanList.clear();
        beanList.addAll(list);
        adapter.notifyDataSetChanged();
    }

    /**
     * 删除复诊消息
     *
     * @param referralMessageAlertId
     */
    @Override
    public void onItemDeleteClick(int referralMessageAlertId) {
        new AlertDialog.Builder(this)
                .setMessage("确认删除该复诊提醒")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.deleteHealthCondition(referralMessageAlertId);
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    public void deleteHealthConditionSucceed(ReferralMessageAlertDisplayBean bean) {
        if (bean == null) {
            showToast("删除复诊消息失败");
        } else {
            mPresenter.onSubscribe();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Global.RequestCode.ADD_VISIT) {
            mPresenter.onSubscribe();
        }
    }

}
