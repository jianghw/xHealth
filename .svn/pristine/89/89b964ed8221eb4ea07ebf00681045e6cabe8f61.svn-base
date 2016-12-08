package com.kaurihealth.datalib.service;



import com.kaurihealth.datalib.request_bean.bean.CreditTransactionDisplayBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by miping on 2016/7/12.
 */
public interface ICreditTransactionService {

    @GET("api/CreditTransaction/GetCreditTransactions")
    Call<List<CreditTransactionDisplayBean>> CreditTransactions();

    @GET("api/CreditTransaction/GetCreditTransactions")
    Observable<List<CreditTransactionDisplayBean>> CreditTransactions_new();
}
