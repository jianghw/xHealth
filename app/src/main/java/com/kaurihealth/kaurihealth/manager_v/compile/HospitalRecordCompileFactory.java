package com.kaurihealth.kaurihealth.manager_v.compile;

import android.content.Context;
import android.view.ViewGroup;

import com.kaurihealth.datalib.response_bean.PatientRecordDisplayDto;
import com.kaurihealth.utilslib.controller.IViewFactory;

/**
 * Created by jianghw on 2016/12/16.
 * <p/>
 * Describe: 病历详情组装页面
 */

public class HospitalRecordCompileFactory implements IViewFactory<PatientRecordDisplayDto> {

    private final Context mContext;
    /**
     * 病人信息模块
     */
    private HospitalPatientView mHospitalPatientView;
    /**
     * 入院记录
     */
    private HospitalAdmissionView mHospitalAdmissionView;
    /**
     * 院内
     */
    private HospitalCourtView mHospitalCourtView;
    /**
     * 出院
     */
    private HospitalOutView mHospitalOutView;


    public HospitalRecordCompileFactory(Context context) {
        this.mContext = context;
    }

    @Override
    public void createIncludeViews() {
        mHospitalPatientView = new HospitalPatientView(mContext);
        mHospitalAdmissionView = new HospitalAdmissionView(mContext);
        mHospitalCourtView = new HospitalCourtView(mContext);
        mHospitalOutView = new HospitalOutView(mContext);
    }

    @Override
    public void attachRootView(ViewGroup root) {
        mHospitalPatientView.attachRoot(root);
        mHospitalAdmissionView.attachRoot(root);
        mHospitalCourtView.attachRoot(root);
        mHospitalOutView.attachRoot(root);
    }

    @Override
    public void fillNewestData(PatientRecordDisplayDto beanData, int position) {
        mHospitalPatientView.fillData(beanData);
        mHospitalAdmissionView.fillData(beanData);
        mHospitalCourtView.fillData(beanData);
        mHospitalOutView.fillData(beanData);
    }

    @Override
    public void unbindView() {
        mHospitalPatientView.unbindView();
        mHospitalAdmissionView.unbindView();
        mHospitalCourtView.unbindView();
        mHospitalOutView.unbindView();
    }

    @Override
    public void showLayout() {
        mHospitalPatientView.showLayout();
        mHospitalAdmissionView.showLayout();
        mHospitalCourtView.showLayout();
        mHospitalOutView.showLayout();
    }

    @Override
    public void hiddenLayout() {
        mHospitalPatientView.hiddenLayout();
        mHospitalAdmissionView.hiddenLayout();
        mHospitalCourtView.hiddenLayout();
        mHospitalOutView.hiddenLayout();
    }

}
