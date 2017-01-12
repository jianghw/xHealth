package com.kaurihealth.kaurihealth.manager_v.compile;

import android.content.Context;
import android.view.ViewGroup;

import com.kaurihealth.datalib.response_bean.PatientRecordDisplayDto;
import com.kaurihealth.utilslib.controller.IViewFactory;

/**
 * Created by jianghw on 2016/12/16.
 * <p/>
 * Describe: 门诊装页面
 */

public class OutpatientRecordCompileFactory implements IViewFactory<PatientRecordDisplayDto> {

    private final Context mContext;
    /**
     * 病人信息模块
     */
    private HospitalPatientView mHospitalPatientView;
    /**
     * 门诊模块
     */
    private OutpatientMedicalView mOutpatientMedicalView;

    public OutpatientRecordCompileFactory(Context context) {
        this.mContext = context;
    }

    @Override
    public void createIncludeViews() {
        mHospitalPatientView = new HospitalPatientView(mContext);
        mOutpatientMedicalView = new OutpatientMedicalView(mContext);
    }

    @Override
    public void attachRootView(ViewGroup root) {
        mHospitalPatientView.attachRoot(root);
        mOutpatientMedicalView.attachRoot(root);
    }

    @Override
    public void fillNewestData(PatientRecordDisplayDto beanData, int position) {
        mHospitalPatientView.fillData(beanData);
        mOutpatientMedicalView.fillData(beanData);
    }

    @Override
    public void unbindView() {
        mHospitalPatientView.unbindView();
        mOutpatientMedicalView.unbindView();
    }

    @Override
    public void showLayout() {
        mHospitalPatientView.showLayout();
        mOutpatientMedicalView.showLayout();
    }

    @Override
    public void hiddenLayout() {
        mHospitalPatientView.hiddenLayout();
        mOutpatientMedicalView.hiddenLayout();
    }

}
