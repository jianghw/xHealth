package com.kaurihealth.datalib.service;


import com.kaurihealth.datalib.request_bean.bean.NewPasswordDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.RequestResetPasswordDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.ResetPasswordDisplayBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 版权所有者：刘正
 * 创建日期： 2016/4/25.
 * 备注：
 */
public interface IChangePasswordService {
    /**
     * 旧的api保留
     * @param newPasswordDisplayBean
     * @return
     */
    //更改密码(旧)
    @POST("api/ChangePassword/UpdateUserPassword")
    Call<ResponseDisplayBean> UpdateUserPassword_out(@Body NewPasswordDisplayBean newPasswordDisplayBean);

    //申请重置密码（旧）
    @POST("api/ChangePassword/RequestResetUserPassword")
    Call<ResponseDisplayBean> RequestResetUserPassword_out(@Body RequestResetPasswordDisplayBean requestResetPasswordDisplayBean);

    //重置用户密码(旧)
    @POST("api/ChangePassword/ResetUserPassword")
    Call<ResponseDisplayBean> ResetUserPassword_out(@Body ResetPasswordDisplayBean resetPasswordDisplayBean);

    //申请重置密码
    @POST("api/ChangePassword/RequestResetUserPassword")
    Observable<ResponseDisplayBean> RequestResetUserPassword(@Body RequestResetPasswordDisplayBean requestResetPasswordDisplayBean);

    //重置用户密码
    @POST("api/ChangePassword/ResetUserPassword")
    Observable<ResponseDisplayBean> ResetUserPassword(@Body ResetPasswordDisplayBean resetPasswordDisplayBean);

    //我的-->设置-> 修改密码  UpdateUserPassword
    @POST("api/ChangePassword/UpdateUserPassword")
    Observable<ResponseDisplayBean> UpdateUserPassword(@Body NewPasswordDisplayBean newPasswordDisplayBean);


}
