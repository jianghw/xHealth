package com.kaurihealth.kaurihealth.manager_v.history;

import android.content.Context;
import android.view.ViewGroup;

import com.kaurihealth.datalib.response_bean.PatientRecordDisplayDto;
import com.kaurihealth.utilslib.controller.IViewFactory;

import java.util.List;

/**
 * Created by mip on 2016/12/23.
 *
 * Describe:病理组装
 */

public class RecordPhologyFactory implements IViewFactory<List<PatientRecordDisplayDto>> {

    private final Context mContext;

    /**
     * 病理
     */
    PhologyView phologyView;


    public RecordPhologyFactory(Context context) {
        this.mContext = context;
    }


    @Override
    public void createIncludeViews() {
        phologyView = new PhologyView(mContext);
    }

    @Override
    public void attachRootView(ViewGroup root) {
        phologyView.attachRoot(root);
    }

    @Override
    public void fillNewestData(List<PatientRecordDisplayDto> beanData, int position) {
        phologyView.fillData(beanData);
    }

    @Override
    public void unbindView() {
        phologyView.unbindView();
    }

    @Override
    public void showLayout() {
        phologyView.showLayout();
    }

    @Override
    public void hiddenLayout() {
        phologyView.hiddenLayout();
    }
}
