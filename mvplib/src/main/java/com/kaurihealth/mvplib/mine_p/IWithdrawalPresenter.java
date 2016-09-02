package com.kaurihealth.mvplib.mine_p;


import com.kaurihealth.mvplib.base_p.IMvpPresenter;

/**
 * Created by Nick on 24/08/2016.
 */
public interface IWithdrawalPresenter<V> extends IMvpPresenter<V> {

    //发送提现请求
    void startNewCashOut();
}
