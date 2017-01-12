package com.kaurihealth.kaurihealth.manager_v.save;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;

import com.kaurihealth.datalib.request_bean.bean.NewHospitalizationPatientRecordDisplayBean;
import com.kaurihealth.datalib.response_bean.PatientRecordDisplayDto;
import com.kaurihealth.kaurihealth.manager_v.compile.HospitalPatientView;
import com.kaurihealth.utilslib.controller.IViewFactory;

import java.util.List;

/**
 * Created by jianghw on 2016/12/16.
 * <p/>
 * Describe: 病历详情组装页面
 */

public class HospitalRecordSaveFactory implements IViewFactory<PatientRecordDisplayDto> {

    private final Context mContext;
    /**
     * 病人信息模块
     */
    private HospitalPatientView mHospitalPatientView;
    /**
     * 入院记录
     */
    private HospitalAdmissionSaveView mHospitalAdmissionSaveView;
    /**
     * 院内
     */
    private HospitalCourtSaveView mHospitalCourtSaveView;
    /**
     * 出院
     */
    private HospitalOutSaveView mHospitalOutSaveView;


    public HospitalRecordSaveFactory(Context context) {
        this.mContext = context;
    }

    @Override
    public void createIncludeViews() {
        mHospitalPatientView = new HospitalPatientView(mContext);
        mHospitalAdmissionSaveView = new HospitalAdmissionSaveView(mContext);
        mHospitalCourtSaveView = new HospitalCourtSaveView(mContext);
        mHospitalOutSaveView = new HospitalOutSaveView(mContext);
    }

    @Override
    public void attachRootView(ViewGroup root) {
        mHospitalPatientView.attachRoot(root);
        mHospitalAdmissionSaveView.attachRoot(root);
        mHospitalCourtSaveView.attachRoot(root);
        mHospitalOutSaveView.attachRoot(root);
    }

    @Override
    public void fillNewestData(PatientRecordDisplayDto beanData, int position) {
        mHospitalPatientView.fillData(beanData);
        mHospitalAdmissionSaveView.fillData(beanData);
        mHospitalCourtSaveView.fillData(beanData);
        mHospitalOutSaveView.fillData(beanData);
    }

    @Override
    public void unbindView() {
        mHospitalPatientView.unbindView();
        mHospitalAdmissionSaveView.unbindView();
        mHospitalCourtSaveView.unbindView();
        mHospitalOutSaveView.unbindView();
    }

    @Override
    public void showLayout() {
        mHospitalPatientView.showLayout();
        mHospitalAdmissionSaveView.showLayout();
        mHospitalCourtSaveView.showLayout();
        mHospitalOutSaveView.showLayout();
    }

    @Override
    public void hiddenLayout() {
        mHospitalPatientView.hiddenLayout();
        mHospitalAdmissionSaveView.hiddenLayout();
        mHospitalCourtSaveView.hiddenLayout();
        mHospitalOutSaveView.hiddenLayout();
    }

    public void childNeedActivity(Activity clazz) {
        mHospitalAdmissionSaveView.childNeedActivity(clazz);
        mHospitalCourtSaveView.childNeedActivity(clazz);
        mHospitalOutSaveView.childNeedActivity(clazz);
    }

    public void onRequestPermissionsResult(int code, String[] permissions, int[] results) {
        mHospitalAdmissionSaveView.onRequestPermissionsResult(code, permissions, results);
        mHospitalCourtSaveView.onRequestPermissionsResult(code, permissions, results);
        mHospitalOutSaveView.onRequestPermissionsResult(code, permissions, results);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mHospitalAdmissionSaveView.onActivityResult(requestCode, resultCode, data);
        mHospitalCourtSaveView.onActivityResult(requestCode, resultCode, data);
        mHospitalOutSaveView.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 入院图片
     */
    public List<String> getAdmissionImagePathsList() {
        return mHospitalAdmissionSaveView.getAdmissionImagePathsList();
    }

    /**
     * 院内图片
     */
    public List<String> getCourtImagePathsList() {
        return mHospitalCourtSaveView.getCourtImagePathsList();
    }

    /**
     * 出院图片
     */
    public List<String> getOutImagePathsList() {
        return mHospitalOutSaveView.getOutImagePathsList();
    }

    /**
     * 入院编辑bean
     */
    public PatientRecordDisplayDto getAdmissionRequestPatientRecordBean() {
        return mHospitalAdmissionSaveView.getAdmissionRequestPatientRecordBean();
    }

    /**
     * 院内编辑bean
     */
    public PatientRecordDisplayDto getCourtRequestPatientRecordBean() {
        mHospitalAdmissionSaveView.getAdmissionRequestPatientRecordBean();
        return mHospitalCourtSaveView.getCourtRequestPatientRecordBean();
    }

    /**
     * 出院编辑bean
     */
    public PatientRecordDisplayDto getOutRequestPatientRecordBean() {
        mHospitalAdmissionSaveView.getAdmissionRequestPatientRecordBean();
        mHospitalCourtSaveView.getCourtRequestPatientRecordBean();
        return mHospitalOutSaveView.getOutRequestPatientRecordBean();
    }

    public String getBooleanString() {
        String string = "全好";
        if (mHospitalAdmissionSaveView.getBoolean()) {
            string = "入院";
        } else if (mHospitalCourtSaveView.getBoolean()) {
            string = "院内";
        } else if (mHospitalOutSaveView.getBoolean()) {
            string = "出院";
        }
        return string;
    }

    public NewHospitalizationPatientRecordDisplayBean getAdmissionInsertRecordBean() {
        return mHospitalAdmissionSaveView.getAdmissionInsertBean();
    }

    public NewHospitalizationPatientRecordDisplayBean getCourtInsertRecordBean() {
        NewHospitalizationPatientRecordDisplayBean bean = mHospitalAdmissionSaveView.getAdmissionInsertBean();
        return mHospitalCourtSaveView.getCourtInsertBean(bean);
    }

    public NewHospitalizationPatientRecordDisplayBean getOutInsertRecordBean() {
        NewHospitalizationPatientRecordDisplayBean bean = mHospitalAdmissionSaveView.getAdmissionInsertBean();
        NewHospitalizationPatientRecordDisplayBean courtInsertBean = mHospitalCourtSaveView.getCourtInsertBean(bean);
        return mHospitalOutSaveView.getOutInsertBean(courtInsertBean);
    }
}
