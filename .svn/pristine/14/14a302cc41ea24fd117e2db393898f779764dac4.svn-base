package com.kaurihealth.kaurihealth.manager_v.save;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;

import com.kaurihealth.datalib.request_bean.bean.NewPathologyPatientRecordDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewSupplementaryTestPatientRecordDisplayBean;
import com.kaurihealth.datalib.response_bean.PatientRecordDisplayDto;
import com.kaurihealth.kaurihealth.manager_v.compile.HospitalPatientView;
import com.kaurihealth.utilslib.controller.IViewFactory;
import com.kaurihealth.utilslib.log.LogUtils;

import java.util.List;

/**
 * Created by mip on 2016/12/29.
 */

public class AccessoryRecordSaveFactory implements IViewFactory<PatientRecordDisplayDto> {

    private final Context mContext;
    private final String mTitle;
    /**
     * 病人信息模块
     */
    private HospitalPatientView mHospitalPatientView;

    private AccessoryRecordCommonSaveView mRecordCommonSaveView;

    public AccessoryRecordSaveFactory(Context context,String title){
        this.mContext = context;
        this.mTitle = title;
    }

    @Override
    public void createIncludeViews() {
        mHospitalPatientView = new HospitalPatientView(mContext);
        mRecordCommonSaveView = new AccessoryRecordCommonSaveView(mContext,mTitle);
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
    public NewSupplementaryTestPatientRecordDisplayBean getNewSupplementaryTestPatientRecordDisplayBean(){
        return mRecordCommonSaveView.getNewSupplementaryTestPatientRecordDisplayBean();
    }

    public NewPathologyPatientRecordDisplayBean getNewPathologyPatientRecordDisplayBean(){
        return  mRecordCommonSaveView.getNewPathologyPatientRecordDisplayBean();
    }

    //空判断
    public boolean  getBoolean() {
        return mRecordCommonSaveView.getBoolean();
    }
}
