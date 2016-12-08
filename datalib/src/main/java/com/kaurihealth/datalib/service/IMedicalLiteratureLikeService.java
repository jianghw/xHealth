package com.kaurihealth.datalib.service;


import com.kaurihealth.datalib.request_bean.bean.MedicalLiteratureLikeDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewMedicalLiteratureLikeDisplayBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 版权所有者：刘正
 * 创建日期： 2016/6/24.
 * 备注：临床支持 - 内容
 */
public interface IMedicalLiteratureLikeService {

    /**
     * 临床支持点赞
     *
     * @param newMedicalLiteratureLikeDisplayBean
     * @return
     */
    @POST("api/MedicalLiteratureLike/LikeMedicalLiterature")
    Call<MedicalLiteratureLikeDisplayBean> LikeMedicalLiterature(@Body NewMedicalLiteratureLikeDisplayBean newMedicalLiteratureLikeDisplayBean);

    @POST("api/MedicalLiteratureLike/LikeMedicalLiterature")
    Observable<MedicalLiteratureLikeDisplayBean> LikeMedicalLiterature_new(@Body NewMedicalLiteratureLikeDisplayBean newMedicalLiteratureLikeDisplayBean);

    /**
     * 检查登陆用户是否一堆某一个文献点赞。
     *
     * @param newMedicalLiteratureLikeDisplayBean
     * @return
     */
    @POST("api/MedicalLiteratureLike/CheckMedicalLiteratureLike")
    Call<ResponseDisplayBean> CheckMedicalLiteratureLike(@Body NewMedicalLiteratureLikeDisplayBean newMedicalLiteratureLikeDisplayBean);

    @POST("api/MedicalLiteratureLike/CheckMedicalLiteratureLike")
    Observable<ResponseDisplayBean> CheckMedicalLiteratureLikeNew(@Body NewMedicalLiteratureLikeDisplayBean newMedicalLiteratureLikeDisplayBean);

    /**
     * 取消临床支持点赞
     *
     * @param newMedicalLiteratureLikeDisplayBean
     * @return
     */
    @POST("api/MedicalLiteratureLike/DisLikeMedicalLiterature")
    Call<ResponseDisplayBean> DisLikeMedicalLiterature(@Body NewMedicalLiteratureLikeDisplayBean newMedicalLiteratureLikeDisplayBean);

    @POST("api/MedicalLiteratureLike/DisLikeMedicalLiterature")
    Observable<ResponseDisplayBean> DisLikeMedicalLiterature_new(@Body NewMedicalLiteratureLikeDisplayBean newMedicalLiteratureLikeDisplayBean);
}
