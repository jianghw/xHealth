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
 * Describe 心血管系统相关检查
 */

public class AngiocarpyTestView extends AbstractViewController<List<PatientRecordDisplayDto>>
        implements CollapsibleLinearLayout.ListViewItemClick{

    @Bind(R.id.lay_angiocarpy)
    CollapsibleLinearLayout lay_angiocarpy;
    @Bind(R.id.card_angiocarpy)
    CardView card_angiocarpy;

    private List<PatientRecordDisplayDto> list;
    private RecordAccessoryTestAdapter accessoryTestAdapter;

    public AngiocarpyTestView(Context context) {
        super(context);
    }

    @Override
    protected int getResLayoutId() {
        return R.layout.include_angiocarpy;
    }

    @Override
    protected void onCreateView(View view) {
        ButterKnife.bind(this, view);
        lay_angiocarpy.setCollapsibleTitle("心血管系统相关检查");
        list = new ArrayList<>();
        accessoryTestAdapter = new RecordAccessoryTestAdapter(mContext, list);
        lay_angiocarpy.setViewAdapter(accessoryTestAdapter);
        lay_angiocarpy.setListViewItemClick(this);
    }

    @Override
    protected void bindViewData(List<PatientRecordDisplayDto> data) {
        if (data != null && !data.isEmpty()) {
            stateTypeHiddenView(false);
            if (list != null && !list.isEmpty()) list.clear();
            list.addAll(data);
            accessoryTestAdapter.notifyDataSetChanged();
            lay_angiocarpy.notifyDataSetChanged();
        } else {
            defaultViewData();
        }
    }

    @Override
    protected void defaultViewData() {
        stateTypeHiddenView(true);
    }

    private void stateTypeHiddenView(boolean b) {
        lay_angiocarpy.setHintTextHidden(b);
    }

    @Override
    protected void unbindView() {
        if (list != null) list.clear();
        ButterKnife.unbind(this);
    }

    @Override
    protected void showLayout() {
        card_angiocarpy.setVisibility(View.VISIBLE);
    }

    @Override
    protected void hiddenLayout() {
        card_angiocarpy.setVisibility(View.GONE);
    }

    /**
     * listView 点击事件
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
        bundle.putString("ICONOGRAPHYTEST","心血管系统相关检查");
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}
