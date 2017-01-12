package com.kaurihealth.datalib.service;

import com.kaurihealth.datalib.request_bean.bean.NewHospitalizationPatientRecordDisplayBean;
import com.kaurihealth.datalib.response_bean.PatientRecordDisplayDto;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by jianghw on 2016/9/14.
 * <p>
 * 描述：病例图像
 * 插入图片
 */
public interface IHospitalizationService {

    //插入新的住院记录 医生和患者的token都可以用
    @POST("api/Hospitalization/InsertHospitalization")
    Observable<PatientRecordDisplayDto> insertHospitalization(@Body NewHospitalizationPatientRecordDisplayBean doctorDisplayBean);
}
