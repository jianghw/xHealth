package com.kaurihealth.kaurihealth.manager_v.compile;

import android.content.Context;
import android.view.ViewGroup;

import com.kaurihealth.datalib.response_bean.PatientRecordDisplayDto;
import com.kaurihealth.utilslib.controller.IViewFactory;

/**
 * Created by mip on 2016/12/29.
 *
 * Decribe: 辅助检查组装
 */

public class AccessoryRecordCompileFactory implements IViewFactory<PatientRecordDisplayDto>{

    private final Context mContext;
    private final String mTitle;

    /**
     * 病人信息模块
     */
    private HospitalPatientView mHospitalPatientView;

    /**
     * 辅助检查信息通用模块
     */
    AccessoryRecordCommonView mRecordCommonView;


    public AccessoryRecordCompileFactory(Context context,String mTitle){
        this.mContext = context;
        this.mTitle = mTitle;
    }

    @Override
    public void createIncludeViews() {
        mHospitalPatientView = new HospitalPatientView(mContext);
        mRecordCommonView = new AccessoryRecordCommonView(mContext,mTitle);
    }

    @Override
    public void attachRootView(ViewGroup root) {
        mHospitalPatientView.attachRoot(root);
        mRecordCommonView.attachRoot(root);
    }

    @Override
    public void fillNewestData(PatientRecordDisplayDto beanData, int position) {
        mHospitalPatientView.fillData(beanData);
        mRecordCommonView.fillData(beanData);
    }

    @Override
    public void unbindView() {
        mHospitalPatientView.unbindView();
        mRecordCommonView.unbindView();
    }

    @Override
    public void showLayout() {
        mHospitalPatientView.showLayout();
        mRecordCommonView.showLayout();
    }

    @Override
    public void hiddenLayout() {
        mHospitalPatientView.hiddenLayout();
        mRecordCommonView.hiddenLayout();
    }
}
