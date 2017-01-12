package com.kaurihealth.kaurihealth.manager_v.history;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;

import com.kaurihealth.datalib.response_bean.PatientRecordDisplayDto;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.ClinicalOutpatientAdapter;
import com.kaurihealth.kaurihealth.eventbus.HospitalRecordCompileEvent;
import com.kaurihealth.kaurihealth.history_record_v.OutpatientRecordCompileActivity;
import com.kaurihealth.utilslib.controller.AbstractViewController;
import com.kaurihealth.utilslib.widget.CollapsibleLinearLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jianghw on 2016/12/15.
 * <p/>
 * Describe: 临床记录
 */

class ClinicalOutpatientView extends AbstractViewController<List<PatientRecordDisplayDto>> implements CollapsibleLinearLayout.ListViewItemClick {

    @Bind(R.id.lay_outpatient)
    CollapsibleLinearLayout mLayOutpatient;
    @Bind(R.id.cv_outpatient)
    CardView mCvOutpatient;

    private List<PatientRecordDisplayDto> list;
    private ClinicalOutpatientAdapter adapter;

    ClinicalOutpatientView(Context context) {
        super(context);
    }

    @Override
    protected int getResLayoutId() {
        return R.layout.include_clinical_outpatient;
    }

    @Override
    protected void onCreateView(View view) {
        ButterKnife.bind(this, view);

        list = new ArrayList<>();
        adapter = new ClinicalOutpatientAdapter(mContext, list);
        mLayOutpatient.setViewAdapter(adapter);
        mLayOutpatient.setListViewItemClick(this);
    }

    @Override
    protected void bindViewData(List<PatientRecordDisplayDto> data) {
        if (data != null && !data.isEmpty()) {
            stateTypeHiddenView(false);
            if (list != null && !list.isEmpty()) list.clear();
            if (list != null) list.addAll(data);
            adapter.notifyDataSetChanged();
            mLayOutpatient.notifyDataSetChanged();
        } else {
            defaultViewData();
        }
    }

    @Override
    protected void defaultViewData() {
        stateTypeHiddenView(true);
    }

    private void stateTypeHiddenView(boolean b) {
        mLayOutpatient.setHintTextHidden(b);
    }

    @Override
    protected void unbindView() {
        if (list != null) list.clear();
        ButterKnife.unbind(this);
    }

    @Override
    protected void showLayout() {
        mCvOutpatient.setVisibility(View.VISIBLE);
    }

    @Override
    protected void hiddenLayout() {
        mCvOutpatient.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PatientRecordDisplayDto bean = list.get(position);
        EventBus.getDefault().postSticky(new HospitalRecordCompileEvent(bean));
        Intent intent = new Intent(mContext, OutpatientRecordCompileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}
