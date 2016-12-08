package com.kaurihealth.datalib.service;


import com.kaurihealth.datalib.request_bean.bean.NewCashOutAccountBean;
import com.kaurihealth.datalib.request_bean.bean.NewCashOutBean;
import com.kaurihealth.datalib.response_bean.UserCashOutAccountDisplayBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by 张磊 on 2016/7/12.
 * 介绍：
 */
public interface IUserCashOutAccountService {
    //获取用户所有提现账号(旧)
    @GET("api/UserCashOutAccount/GetUserCashOutAccounts")
    Call<List<UserCashOutAccountDisplayBean>> GetUserCashOutAccounts_out();

    //添加用户提现账号
    @POST("api/UserCashOutAccount/InsertUserCashOutAccount")
    Call<UserCashOutAccountDisplayBean> InsertUserCashOutAccount(@Body NewCashOutAccountBean account);

    /**
     * 添加提现用户账号
     * @param account
     * @return
     */
    @POST("api/UserCashOutAccount/InsertUserCashOutAccount")
    Observable<UserCashOutAccountDisplayBean> InsertUserCashOutAccount_new(@Body NewCashOutAccountBean account);

    //发起提现请求
    @POST("api/CashOut/StartNewCashOut")
    Call<ResponseDisplayBean> StartNewCashOut_out(@Body NewCashOutBean newRequest);


    //获取用户所有提现账号
    @GET("api/UserCashOutAccount/GetUserCashOutAccounts")
    Observable<List<UserCashOutAccountDisplayBean>> GetUserCashOutAccounts();

    /**
     *  发起提现请求
     * @param newRequest
     * @return
     */
    @POST("api/CashOut/StartNewCashOut")
    Observable<ResponseDisplayBean> StartNewCashOut(@Body NewCashOutBean newRequest);

}
