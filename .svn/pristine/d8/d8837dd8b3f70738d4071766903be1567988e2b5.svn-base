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
import com.kaurihealth.datalib.response_bean.TotalSupplementaryTestCountsBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.RecordAccessoryAdapter;
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
 * Describe: 辅助检查
 */

class RecordAccessoryView extends AbstractViewController<PatientRecordCountDisplayBean> implements View.OnClickListener {
    @Bind(R.id.tv_accessory_title)
    TextView mTvAccessoryTitle;
    @Bind(R.id.lv_accessory_content)
    ListView mLvAccessoryContent;
    @Bind(R.id.tv_accessory_history)
    TextView mTvAccessoryHistory;
    @Bind(R.id.cv_accesory)
    CardView mCvAccesory;

    private List<TotalSupplementaryTestCountsBean> list;
    private RecordAccessoryAdapter adapter;
    private int mPatientId;


    RecordAccessoryView(Context context) {
        super(context);
    }

    @Override
    protected int getResLayoutId() {
        return R.layout.include_record_accessory;
    }

    @Override
    protected void onCreateView(View view) {
        ButterKnife.bind(this, view);
        mTvAccessoryTitle.setText("辅助检查");
        list = new ArrayList<>();
        adapter = new RecordAccessoryAdapter(mContext, list);
        mLvAccessoryContent.setAdapter(adapter);
    }

    @Override
    protected void bindViewData(PatientRecordCountDisplayBean data) {
        PatientBean patientBean = data.getPatient();
        if (patientBean != null) mPatientId = patientBean.getPatientId();

        List<TotalSupplementaryTestCountsBean> supplementaryTestCounts = data.getTotalSupplementaryTestCounts();
        if (supplementaryTestCounts != null && !supplementaryTestCounts.isEmpty()) {
            stateTypeView(true);
            if (list != null && !list.isEmpty()) list.clear();
            list.addAll(supplementaryTestCounts);
            adapter.notifyDataSetChanged();

            measureListViewHeight();
        } else {
            defaultViewData();
        }
    }

    private void measureListViewHeight() {
        ListAdapter mAdapter = mLvAccessoryContent.getAdapter();
        int totalHeight = 0;
        for (int i = 0; i < mAdapter.getCount(); i++) {
            View listItem = mAdapter.getView(i, null, mLvAccessoryContent);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = mLvAccessoryContent.getLayoutParams();
        params.height = totalHeight + (mLvAccessoryContent.getDividerHeight() * (mAdapter.getCount() - 1));
        mLvAccessoryContent.setLayoutParams(params);
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

    @OnClick({R.id.tv_accessory_history})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_accessory_history://历史记录
                Intent intent = new Intent(mContext, ClinicalHistoryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(Global.Bundle.CONVER_ITEM_PATIENT, mPatientId);
                bundle.putString(Global.Bundle.BUNDLE_CATEGORY, Global.Environment.AUXILIARY);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void stateTypeView(boolean isActive) {
        mLvAccessoryContent.setVisibility(isActive ? View.VISIBLE : View.GONE);
        mTvAccessoryHistory.setVisibility(isActive ? View.VISIBLE : View.GONE);
    }


    @Override
    protected void showLayout() {
        mCvAccesory.setVisibility(View.VISIBLE);
    }

    @Override
    protected void hiddenLayout() {
        mCvAccesory.setVisibility(View.GONE);
    }
}
