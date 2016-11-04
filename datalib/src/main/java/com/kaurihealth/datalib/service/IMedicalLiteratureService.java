package com.kaurihealth.datalib.service;


import com.kaurihealth.datalib.request_bean.bean.MedicalLiteratureDisPlayBean;

import java.util.List;

import retrofit2.Call;import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 版权所有者：刘正
 * 创建日期： 2016/6/7.
 * 备注：临床支持
 */
public interface IMedicalLiteratureService {
    /**
     * 根据临床支持Id加载相应临床支持
     *
     * @param medicalLiteratureId
     * @return
     */
    @GET("api/MedicalLiterature/LoadMedicalLiteratureById")
    Call<MedicalLiteratureDisPlayBean> LoadMedicalLiteratureById_out(@Query("medicalLiteratureId") int medicalLiteratureId);


    @GET("api/MedicalLiterature/LoadMedicalLiteratureById")
    Observable<MedicalLiteratureDisPlayBean> LoadMedicalLiteratureById(@Query("medicalLiteratureId") int medicalLiteratureId);
    /**
     * 读取所有的临床支持(为初始加载时读取所有数据，无传参)
     *
     * @return
     */
    @GET("api/MedicalLiterature/LoadAllMedicalLiteratures")
    Call<List<MedicalLiteratureDisPlayBean>> LoadAllMedicalLiteratures();

    @GET("api/MedicalLiterature/LoadAllMedicalLiteratures")
    Observable<List<MedicalLiteratureDisPlayBean>> LoadAllMedicalLiteratures_new();

    /**
     * 移动端简化版读取所有的临床支持(为初始加载时读取所有数据，无传参)
     *
     * @return
     */
    @GET("api/MedicalLiterature/LoadAllMobileMedicalLiteratures")
    Call<List<MedicalLiteratureDisPlayBean>> LoadAllMobileMedicalLiteratures();

    /**
     * 根据指定类型加载对应的所有临床支持
     *
     * @param medicalLiteratureType
     * @return
     */
    @GET("api/MedicalLiterature/LoadAllMedicalLitreaturesByType")
    Call<List<MedicalLiteratureDisPlayBean>> LoadAllMedicalLitreaturesByType_out(@Query("medicalLiteratureType") String medicalLiteratureType);

    @GET("api/MedicalLiterature/LoadAllMedicalLitreaturesByType")
    Observable<List<MedicalLiteratureDisPlayBean>> LoadAllMedicalLitreaturesByType(@Query("medicalLiteratureType") String medicalLiteratureType);
}
