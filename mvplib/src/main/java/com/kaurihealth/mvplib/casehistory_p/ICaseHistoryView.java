package com.kaurihealth.mvplib.casehistory_p;

import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

/**
 * Created by mip on 2017/1/9.
 */

public interface ICaseHistoryView extends IMvpView{
    void removeSuccess(ResponseDisplayBean bean);
}
