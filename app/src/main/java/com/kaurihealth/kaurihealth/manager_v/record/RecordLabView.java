package com.kaurihealth.kaurihealth.manager_v.record;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.kaurihealth.datalib.response_bean.PatientBean;
import com.kaurihealth.datalib.response_bean.PatientRecordCountDisplayBean;
import com.kaurihealth.datalib.response_bean.TotalLabTestCountsBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.RecordLabAdapter;
import com.kaurihealth.kaurihealth.history_record_v.ClinicalHistoryActivity;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.controller.AbstractViewController;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jianghw on 2016/12/15.
 * <p/>
 * Describe: 实验室检查统计
 */

class RecordLabView extends AbstractViewController<PatientRecordCountDisplayBean> implements View.OnClickListener {

    @Bind(R.id.tv_lab_title)
    TextView mTvLabTitle;
    @Bind(R.id.lv_lab_content)
    ListView mLvLabContent;
    @Bind(R.id.tv_lab_history)
    TextView mTvLabHistory;
    @Bind(R.id.cv_lab)
    CardView mCvLab;

    private List<TotalLabTestCountsBean> list;
    private RecordLabAdapter adapter;
    private int mPatientId;

    RecordLabView(Context context) {
        super(context);
    }

    @Override
    protected int getResLayoutId() {
        return R.layout.include_record_lab;
    }

    @Override
    protected void onCreateView(View view) {
        ButterKnife.bind(this, view);

        mTvLabTitle.setText("实验室检查");
        list = new ArrayList<>();
        adapter = new RecordLabAdapter(mContext, list);
        mLvLabContent.setAdapter(adapter);
    }

    @Override
    protected void bindViewData(PatientRecordCountDisplayBean data) {
        PatientBean patientBean = data.getPatient();
        if (patientBean != null) mPatientId = patientBean.getPatientId();

        List<TotalLabTestCountsBean> labTestCounts = data.getTotalLabTestCounts();
        if (labTestCounts != null && !labTestCounts.isEmpty()) {
            stateTypeView(true);
            if (list != null && !list.isEmpty()) list.clear();
            list.addAll(labTestCounts);
            adapter.notifyDataSetChanged();

            measureListViewHeight();
        } else {
            defaultViewData();
        }
    }

    private void measureListViewHeight() {
        ListAdapter mAdapter = mLvLabContent.getAdapter();
        int totalHeight = 0;
        for (int i = 0; i < mAdapter.getCount(); i++) {
            View listItem = mAdapter.getView(i, null, mLvLabContent);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = mLvLabContent.getLayoutParams();
        params.height = totalHeight + (mLvLabContent.getDividerHeight() * (mAdapter.getCount() - 1));
        mLvLabContent.setLayoutParams(params);
    }

    @Override
    protected void defaultViewData() {
        stateTypeView(false);
    }

    @Override
    protected void unbindView() {
        if (list != null) list.clear();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tv_lab_history})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_lab_history://历史记录
                Intent intent = new Intent(mContext, ClinicalHistoryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(Global.Bundle.CONVER_ITEM_PATIENT, mPatientId);
                bundle.putString(Global.Bundle.BUNDLE_CATEGORY, Global.Environment.LOB);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void stateTypeView(boolean isActive) {
        mLvLabContent.setVisibility(isActive ? View.VISIBLE : View.GONE);
        mTvLabHistory.setVisibility(isActive ? View.VISIBLE : View.GONE);
    }


    @Override
    protected void showLayout() {
        mCvLab.setVisibility(View.VISIBLE);
    }

    @Override
    protected void hiddenLayout() {
        mCvLab.setVisibility(View.GONE);
    }
}
