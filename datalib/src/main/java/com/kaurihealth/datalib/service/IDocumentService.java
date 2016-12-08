package com.kaurihealth.datalib.service;

import com.kaurihealth.datalib.response_bean.DocumentDisplayBean;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by jianghw on 2016/9/14.
 * <p>
 * 描述：病例图像
 * 插入图片
 */
public interface IDocumentService {

    //插入图片
    @Multipart
    @POST("api/Document/Upload")
    Observable<List<DocumentDisplayBean>> uploadDocuments(@Part("kauriHealthId") RequestBody ID, @Part() List<MultipartBody.Part> parts);
}
