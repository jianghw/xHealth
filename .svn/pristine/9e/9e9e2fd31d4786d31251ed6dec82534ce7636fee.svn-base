package com.kaurihealth.kaurihealth.manager_v.history;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;

import com.kaurihealth.datalib.response_bean.PatientRecordDisplayDto;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.RecordAccessoryTestAdapter;
import com.kaurihealth.kaurihealth.eventbus.HospitalRecordCompileEvent;
import com.kaurihealth.kaurihealth.history_record_v.LobRecordCompileActivity;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.controller.AbstractViewController;
import com.kaurihealth.utilslib.widget.CollapsibleLinearLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mip on 2016/12/23.
 *
 * Describe: 常规粪便检查
 */

public class NormalFecesTestView extends AbstractViewController<List<PatientRecordDisplayDto>>
            implements CollapsibleLinearLayout.ListViewItemClick{

    @Bind(R.id.lay_narmal_faces)
    CollapsibleLinearLayout lay_narmal_faces;
    @Bind(R.id.cv_outpatient)
    CardView cv_outpatient;

    private List<PatientRecordDisplayDto> list;
    private RecordAccessoryTestAdapter accessoryTestAdapter;

    public NormalFecesTestView(Context context) {
        super(context);
    }

    @Override
    protected int getResLayoutId() {
        return R.layout.include_normal_faces;
    }

    @Override
    protected void onCreateView(View view) {
        ButterKnife.bind(this, view);

        list = new ArrayList<>();
        accessoryTestAdapter = new RecordAccessoryTestAdapter(mContext, list);
        lay_narmal_faces.setViewAdapter(accessoryTestAdapter);
        lay_narmal_faces.setListViewItemClick(this);
    }

    @Override
    protected void bindViewData(List<PatientRecordDisplayDto> data) {
        lay_narmal_faces.setCollapsibleTitle("常规粪便检查");
        if (data != null && !data.isEmpty()) {
            stateTypeHiddenView(false);
            if (list != null && !list.isEmpty()) list.clear();
            list.addAll(data);
            accessoryTestAdapter.notifyDataSetChanged();
            lay_narmal_faces.notifyDataSetChanged();
        } else {
            defaultViewData();
        }
    }

    @Override
    protected void defaultViewData() {
        stateTypeHiddenView(true);
    }

    private void stateTypeHiddenView(boolean b) {
        lay_narmal_faces.setHintTextHidden(b);
    }

    @Override
    protected void unbindView() {
        if (list != null) list.clear();
        ButterKnife.unbind(this);
    }

    @Override
    protected void showLayout() {
        cv_outpatient.setVisibility(View.VISIBLE);
    }

    @Override
    protected void hiddenLayout() {
        cv_outpatient.setVisibility(View.GONE);
    }

    /**
     * list item 的点击事件
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PatientRecordDisplayDto bean = list.get(position);
        EventBus.getDefault().postSticky(new HospitalRecordCompileEvent(bean));
        Intent intent = new Intent(mContext, LobRecordCompileActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Global.Bundle.ITEM_Lob,"常规类编检查");
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}
