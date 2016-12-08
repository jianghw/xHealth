package com.kaurihealth.datalib.service;

import com.kaurihealth.datalib.request_bean.bean.NewReferralMessageAlertBean;
import com.kaurihealth.datalib.response_bean.ReferralMessageAlertDisplayBean;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by jianghw on 2016/11/9.
 * <p/>
 * Describe:复诊提醒
 */

public interface IReferralMessageAlertService {
    /**
     * 根据医患关系ID加载复诊提醒信息
     *
     * @param doctorPatientShipId
     * @return
     */
    @GET("api/ReferralMessageAlert/LoadReferralMessageAlert")
    Observable<List<ReferralMessageAlertDisplayBean>> loadReferralMessageAlert(@Query("doctorPatientShipId") int doctorPatientShipId);

    /**
     * 插入新的复诊提醒信息
     *
     * @param bean
     * @return
     */
    @POST("api/ReferralMessageAlert/InsertReferralMessageAlert")
    Observable<ReferralMessageAlertDisplayBean> insertReferralMessageAlert(@Body NewReferralMessageAlertBean bean);

    /**
     * 删除复诊提醒信息
     *
     * @param referralMessageAlertId
     * @return
     */
    @DELETE("api/ReferralMessageAlert/DeleteReferralMessageAlert")
    Observable<ReferralMessageAlertDisplayBean> deleteReferralMessageAlert(@Query("referralMessageAlertId") int referralMessageAlertId);
}
