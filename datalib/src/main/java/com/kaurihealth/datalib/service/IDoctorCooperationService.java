package com.kaurihealth.datalib.service;

import com.kaurihealth.datalib.response_bean.DoctorCooperationBean;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by jianghw on 2016/9/19.
 * <p>
 * 描述：暂未使用 医生合作
 */
public interface IDoctorCooperationService {
    /**
     * 查询所有同意的协作医生
     *
     * @return
     */
    @GET("api/DoctorCooperation/LoadAcceptedCooperationDoctors")
    Observable<List<DoctorCooperationBean>> loadAcceptedCooperationDoctors();
}
