package com.kaurihealth.datalib.service;


import com.kaurihealth.datalib.request_bean.bean.DoctorProductPriceBean;
import com.kaurihealth.datalib.request_bean.bean.NewPriceBean;
import com.kaurihealth.datalib.response_bean.PriceDisplayBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Nick on 28/04/2016.
 */
public interface IPriceService {
    @POST("api/Price/InsertPrice_out")
    Call<ResponseDisplayBean> InsertPrice_out(@Body NewPriceBean newPriceBean);

    @POST("api/Price/UpdateDoctorProductPrice_out")
    Call<ResponseDisplayBean> UpdateDoctorProductPrice_out(@Body DoctorProductPriceBean priceDisplayBean);

    @POST("api/Price/UpdateDoctorProductPrice")
    Observable<ResponseDisplayBean> UpdateDoctorProductPrice(@Body PriceDisplayBean priceDisplayBean);

    /**
     * 插入新的价格
     *
     * @param newPriceBean
     * @return
     */
    @POST("api/Price/InsertPrice")
    Observable<ResponseDisplayBean> InsertPrice(@Body NewPriceBean newPriceBean);
}
