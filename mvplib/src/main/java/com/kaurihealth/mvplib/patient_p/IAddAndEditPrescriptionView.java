package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.datalib.request_bean.bean.NewPrescriptionBean;
import com.kaurihealth.datalib.request_bean.bean.PrescriptionBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

import java.util.ArrayList;

/**
 * Created by mip on 2016/9/28.
 */
public interface IAddAndEditPrescriptionView extends IMvpView{

    /**
     * 操作后的路径
     *
     * @return
     */
    ArrayList<String> getPathsList();

    String getKauriHealthId();

    /**
     * 得到新的bean
     * @return
     */
    NewPrescriptionBean getNewPrescriptionBean();

    /**
     * 成功回调
     * @param prescriptionBean
     */
    void updateSucceed(PrescriptionBean prescriptionBean);

    /**
     * requestbean
     */
    PrescriptionBean getRequestPrescriptionBean();
}
