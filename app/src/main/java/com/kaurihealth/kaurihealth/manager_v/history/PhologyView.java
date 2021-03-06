package com.kaurihealth.kaurihealth.manager_v.history;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.kaurihealth.datalib.response_bean.PatientRecordDisplayDto;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.RecordAccessoryTestAdapter;
import com.kaurihealth.kaurihealth.eventbus.HospitalRecordCompileEvent;
import com.kaurihealth.kaurihealth.history_record_v.AccessoryRecordCompileActivity;
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
 * Describe：病理
 */

public class PhologyView extends AbstractViewController<List<PatientRecordDisplayDto>>
        implements CollapsibleLinearLayout.ListViewItemClick{

    @Bind(R.id.lay_phology)
    CollapsibleLinearLayout lay_phology;
    @Bind(R.id.card_phology)
    LinearLayout card_phology;

    private List<PatientRecordDisplayDto> list;
    private RecordAccessoryTestAdapter accessoryTestAdapter;

    public PhologyView(Context context) {
        super(context);
    }

    @Override
    protected int getResLayoutId() {
        return R.layout.include_phology;
    }

    @Override
    protected void onCreateView(View view) {
        ButterKnife.bind(this, view);
        lay_phology.setCollapsibleTitle("病理");
        list = new ArrayList<>();
        accessoryTestAdapter = new RecordAccessoryTestAdapter(mContext, list);
        lay_phology.setViewAdapter(accessoryTestAdapter);
        lay_phology.setListViewItemClick(this);
    }

    @Override
    protected void bindViewData(List<PatientRecordDisplayDto> data) {

        if (data != null && !data.isEmpty()) {
            stateTypeHiddenView(false);
            if (list != null && !list.isEmpty()) list.clear();
            list.addAll(data);
            accessoryTestAdapter.notifyDataSetChanged();
            lay_phology.notifyDataSetChanged();
        } else {
            defaultViewData();
        }
    }

    @Override
    protected void defaultViewData() {
        stateTypeHiddenView(true);
    }

    private void stateTypeHiddenView(boolean b) {
        lay_phology.setHintTextHidden(b);
    }

    @Override
    protected void unbindView() {
        if (list != null) list.clear();
        ButterKnife.unbind(this);
    }

    @Override
    protected void showLayout() {
        card_phology.setVisibility(View.VISIBLE);
    }

    @Override
    protected void hiddenLayout() {
        card_phology.setVisibility(View.GONE);
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
        Intent intent = new Intent(mContext, AccessoryRecordCompileActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("ICONOGRAPHYTEST","病理");
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}
