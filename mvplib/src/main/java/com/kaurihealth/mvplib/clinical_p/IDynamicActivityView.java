package com.kaurihealth.mvplib.clinical_p;

import com.kaurihealth.datalib.request_bean.bean.LiteratureCommentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.LiteratureReplyDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.MedicalLiteratureLikeDisplayBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

import java.util.List;

/**
 * Created by mip on 2016/8/29.
 */
public interface IDynamicActivityView extends IMvpView{

    void loadingIndicator(boolean flag);

    int getCurrentEdicalLiteratureId();

    void getCheckMedicalLiteratureLikeResponse(MedicalLiteratureLikeDisplayBean displayBean);

    void LoadLiteratureCommentsByMedicalLiteratureId(List<LiteratureCommentDisplayBean> literatureCommentDisplayBeen);

    void DisLikeMedicalLiterature(ResponseDisplayBean responseDisplayBean);

    void checkIsSucess(ResponseDisplayBean bean);

    void getDeleteCommentResponse(ResponseDisplayBean bean, int position);

    void getLiteratureReplyDisplayBean(List<LiteratureReplyDisplayBean> been);


}
