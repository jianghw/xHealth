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
 * Describe: 特殊检查
 */

public class SpecialTestView extends AbstractViewController<List<PatientRecordDisplayDto>>
            implements CollapsibleLinearLayout.ListViewItemClick{

    @Bind(R.id.lay_spacial)
    CollapsibleLinearLayout lay_spacial;
    @Bind(R.id.cv_outpatient)
    LinearLayout cv_outpatient;

    private List<PatientRecordDisplayDto> list;
    private RecordAccessoryTestAdapter accessoryTestAdapter;

    public SpecialTestView(Context context) {
        super(context);
    }

    @Override
    protected int getResLayoutId() {
        return R.layout.include_special;
    }

    @Override
    protected void onCreateView(View view) {
        ButterKnife.bind(this, view);

        list = new ArrayList<>();
        accessoryTestAdapter = new RecordAccessoryTestAdapter(mContext, list);
        lay_spacial.setViewAdapter(accessoryTestAdapter);
        lay_spacial.setListViewItemClick(this);
    }

    @Override
    protected void bindViewData(List<PatientRecordDisplayDto> data) {
        lay_spacial.setCollapsibleTitle("特殊检查");
        if (data != null && !data.isEmpty()) {
            stateTypeHiddenView(false);
            if (list != null && !list.isEmpty()) list.clear();
            list.addAll(data);
            accessoryTestAdapter.notifyDataSetChanged();
            lay_spacial.notifyDataSetChanged();
        } else {
            defaultViewData();
        }
    }

    @Override
    protected void defaultViewData() {
        stateTypeHiddenView(true);
    }

    private void stateTypeHiddenView(boolean b) {
        lay_spacial.setHintTextHidden(b);
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
        bundle.putString(Global.Bundle.ITEM_Lob,"特殊检查");
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}
