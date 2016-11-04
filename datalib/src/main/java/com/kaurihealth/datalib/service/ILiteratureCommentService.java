package com.kaurihealth.datalib.service;


import com.kaurihealth.datalib.request_bean.bean.LiteratureCommentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewLiteratureCommentDisplayBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 版权所有者：刘正
 * 创建日期： 2016/7/4.
 * 备注：临床支持 - 评论
 */
public interface ILiteratureCommentService {
    /**
     * 根据临床支持Id加载相应临床支持评论
     *
     * @param medicalLiteratureId
     * @return
     */
    @GET("api/LiteratureComment/LoadLiteratureCommentsByMedicalLiteratureId")
    Call<List<LiteratureCommentDisplayBean>> LoadLiteratureCommentsByMedicalLiteratureId(@Query("medicalLiteratureId") int medicalLiteratureId);

    @GET("api/LiteratureComment/LoadLiteratureCommentsByMedicalLiteratureId")
    Observable<List<LiteratureCommentDisplayBean>> LoadLiteratureCommentsByMedicalLiteratureId_new(@Query("medicalLiteratureId") int medicalLiteratureId);
    /**
     * 根据临床支持评论对象插入新的临床支持评论
     *
     * @param newLiteratureCommentDisplayBean
     * @return
     */
    @POST("api/LiteratureComment/InsertLiteratureComment")
    Call<LiteratureCommentDisplayBean> InsertLiteratureComment(@Body NewLiteratureCommentDisplayBean newLiteratureCommentDisplayBean);

    @POST("api/LiteratureComment/InsertLiteratureComment")
    Observable<LiteratureCommentDisplayBean> InsertLiteratureComment_new(@Body NewLiteratureCommentDisplayBean newLiteratureCommentDisplayBean);
    /**
     * 根据临床支持评论Id删除相应的临床支持评论
     *
     * @param literatureCommentId
     * @return
     */
    @DELETE("api/LiteratureComment/DeleteLiteratureComment")
    Call<ResponseDisplayBean> DeleteLiteratureComment(@Query("literatureCommentId") int literatureCommentId);

    @DELETE("api/LiteratureComment/DeleteLiteratureComment")
    Observable<ResponseDisplayBean> DeleteLiteratureComment_new(@Query("literatureCommentId") int literatureCommentId);

    /**
     * 修改临床支持评论
     *
     * @param
     * @return
     */
    @POST("api/LiteratureComment/UpdatenewLiteratureComment")
    Call<LiteratureCommentDisplayBean> UpdatenewLiteratureComment(@Body LiteratureCommentDisplayBean literatureCommentDisplayBean);

    /**
     * 通过临床支持评论Id加载临床支评论
     *
     * @param
     * @return
     */
    @GET("api/LiteratureComment/LoadLiteratureCommentById")
    Call<LiteratureCommentDisplayBean> LoadLiteratureCommentById(@Query("literatureCommentId") int literatureCommentId);

}
