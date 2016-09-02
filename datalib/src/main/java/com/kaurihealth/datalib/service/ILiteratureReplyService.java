package com.kaurihealth.datalib.service;


import com.kaurihealth.datalib.request_bean.bean.LiteratureReplyDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewLiteratureReplyDisplayBean;
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
 * 创建日期： 2016/6/28.
 * 备注：临床支持 - 回复
 */
public interface ILiteratureReplyService {

    /**
     * 根据评论Id读取回复内容
     *
     * @param literatureCommentId
     * @return
     */
    @GET("api/LiteratureReply/LoadLiteratureReplyByLiteratureCommentId")
    Call<List<LiteratureReplyDisplayBean>> LoadLiteratureReplyByLiteratureCommentId(@Query("literatureCommentId") int literatureCommentId);

    /**
     * 插入新的回复
     *
     * @param newLiteratureReplyDisplayBean
     * @return
     */
    @POST("api/LiteratureReply/InsertLiteratureReply")
    Call<LiteratureReplyDisplayBean> InsertLiteratureReply(@Body NewLiteratureReplyDisplayBean newLiteratureReplyDisplayBean);

    @POST("api/LiteratureReply/InsertLiteratureReply")
    Observable<LiteratureReplyDisplayBean> InsertLiteratureReply_new(@Body NewLiteratureReplyDisplayBean newLiteratureReplyDisplayBean);

    /**
     * 根据回复Id删除相应回复
     *
     * @param literatureReplyId
     * @return
     */
    @DELETE("api/LiteratureReply/DeleteLiteratureReply")
    Call<ResponseDisplayBean> DeleteLiteratureReply(@Query("literatureReplyId") int literatureReplyId);

    /**
     * 修改回复
     *
     * @param literatureReplyDisplayBean
     * @return
     */
    @POST("api/LiteratureReply/UpdateLiteratureReply")
    Call<LiteratureReplyDisplayBean> UpdateLiteratureReply(@Body LiteratureReplyDisplayBean literatureReplyDisplayBean);

    /**
     * 根据回复Id加载相应回复
     *
     * @param literatureReplyId
     * @return
     */
    @GET("api/LiteratureReply/LoadLiteratureReplyById")
    Call<LiteratureReplyDisplayBean> LoadLiteratureReplyById(@Query("literatureReplyId") int literatureReplyId);

}
