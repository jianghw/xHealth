package com.kaurihealth.mvplib.clinical_p;

import com.kaurihealth.datalib.request_bean.bean.LiteratureCommentDisplayBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

/**
 * Created by mip on 2016/8/30.
 */
public interface ICommentActivityView extends IMvpView {

    String getComment();

    int getCurrentMedicalLiteratureId();

    void getLiteratureCommentDisplayBean(LiteratureCommentDisplayBean bean);
}
