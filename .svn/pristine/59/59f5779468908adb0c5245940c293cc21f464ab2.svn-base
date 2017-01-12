package com.kaurihealth.kaurihealth.manager_v.save;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;

import com.kaurihealth.datalib.request_bean.bean.NewLabTestPatientRecordDisplayBean;
import com.kaurihealth.datalib.response_bean.PatientRecordDisplayDto;
import com.kaurihealth.kaurihealth.manager_v.compile.HospitalPatientView;
import com.kaurihealth.utilslib.controller.IViewFactory;
import com.kaurihealth.utilslib.log.LogUtils;

import java.util.List;

/**
 * Created by mip on 2016/12/29.
 *
 * Descibe: 实验室检查保存工厂组装类
 */

public class LobRecordSaveFactory implements IViewFactory<PatientRecordDisplayDto> {

    private final Context mContext;
    private final String mTitle;
    /**
     * 病人信息模块
     */
    private HospitalPatientView mHospitalPatientView;

    private LobRecordCommonSaveView mRecordCommonSaveView;

    public LobRecordSaveFactory(Context context, String title){
        this.mContext = context;
        this.mTitle = title;
    }

    @Override
    public void createIncludeViews() {
        mHospitalPatientView = new HospitalPatientView(mContext);
        mRecordCommonSaveView = new LobRecordCommonSaveView(mContext,mTitle);
    }

    @Override
    public void attachRootView(ViewGroup root) {
        mHospitalPatientView.attachRoot(root);
        mRecordCommonSaveView.attachRoot(root);
    }

    @Override
    public void fillNewestData(PatientRecordDisplayDto beanData, int position) {
        LogUtils.jsonDate(beanData);
        mHospitalPatientView.fillData(beanData);
        mRecordCommonSaveView.fillData(beanData);
    }

    @Override
    public void unbindView() {
        mHospitalPatientView.unbindView();
        mRecordCommonSaveView.unbindView();
    }

    @Override
    public void showLayout() {
        mHospitalPatientView.showLayout();
        mRecordCommonSaveView.showLayout();
    }

    @Override
    public void hiddenLayout() {
        mHospitalPatientView.hiddenLayout();
        mRecordCommonSaveView.hiddenLayout();
    }

    public void childNeedActivity(Activity clazz) {
        mRecordCommonSaveView.childNeedActivity(clazz);
    }

    public void onRequestPermissionsResult(int code, String[] permissions, int[] results) {
        mRecordCommonSaveView.onRequestPermissionsResult(code, permissions, results);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mRecordCommonSaveView.onActivityResult(requestCode, resultCode, data);
    }

    public List<String> getImagePathsList() {
        return mRecordCommonSaveView.getImagePathsList();
    }

    public PatientRecordDisplayDto getRequestPatientRecordBean() {
        return mRecordCommonSaveView.getRequestPatientRecordBean();
    }

    public NewLabTestPatientRecordDisplayBean getNewLabTestPatientRecordDisplayBean() {
        return mRecordCommonSaveView.getNewLabTestPatientRecordDisplayBean();
    }

    /**
     * 没有填写完整的判断
     */
    public boolean getBoolean() {

        return mRecordCommonSaveView.getBoolean();
    }
}
