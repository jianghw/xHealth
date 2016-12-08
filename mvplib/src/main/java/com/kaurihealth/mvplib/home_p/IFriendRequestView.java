package com.kaurihealth.mvplib.home_p;

import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

/**
 * Created by mip on 2016/9/20.
 */
public interface IFriendRequestView extends IMvpView{

    //得到当前的医生关系id
    int getCurrentDoctorRelationshipId();

    void agreeFriendSuccess(ResponseDisplayBean bean);
    void rejectFriendSuccess(ResponseDisplayBean bean);
}
