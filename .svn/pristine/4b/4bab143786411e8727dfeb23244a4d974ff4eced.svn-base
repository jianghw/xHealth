package com.kaurihealth.kaurihealth.manager_v.history;

import android.content.Context;
import android.view.ViewGroup;

import com.kaurihealth.datalib.response_bean.PatientRecordDisplayDto;
import com.kaurihealth.utilslib.controller.IViewFactory;

import java.util.List;

/**
 * Created by jianghw on 2016/12/16.
 * <p/>
 * Describe: 临床诊疗历史记录组装页面
 */

public class ClinicalHistoryFactory implements IViewFactory<List<PatientRecordDisplayDto>> {

    private final Context mContext;
    /**
     * 门诊记录
     */
    private ClinicalOutpatientView mRecordPatientView;
    /**
     * 住院记录
     */
    private ClinicalHospitalView mHospitalView;

    public ClinicalHistoryFactory(Context context) {
        this.mContext = context;
    }

    @Override
    public void createIncludeViews() {
        mRecordPatientView = new ClinicalOutpatientView(mContext);
        mHospitalView = new ClinicalHospitalView(mContext);
    }

    @Override
    public void attachRootView(ViewGroup root) {
        mRecordPatientView.attachRoot(root);
        mHospitalView.attachRoot(root);
    }

    @Override
    public void fillNewestData(List<PatientRecordDisplayDto> beanData, int position) {
        if (position == 1) {//门诊记录
            mRecordPatientView.fillData(beanData);
        } else if (position == 2) {//住院记录
            mHospitalView.fillData(beanData);
        }
    }

    @Override
    public void unbindView() {
        mRecordPatientView.unbindView();
        mHospitalView.unbindView();
    }

    @Override
    public void showLayout() {
        mRecordPatientView.showLayout();
        mHospitalView.showLayout();
    }

    @Override
    public void hiddenLayout() {
        mRecordPatientView.hiddenLayout();
        mHospitalView.hiddenLayout();
    }

}
