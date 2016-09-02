package com.kaurihealth.mvplib.resetpassword_p;

import com.kaurihealth.mvplib.base_p.IMvpPresenter;
import com.kaurihealth.mvplib.base_p.IMvpView;

/**
 * Created by Garnet_Wu on 2016/8/18.
 */
public interface IResetpasswordPresenter<V>  extends IMvpPresenter<V>{
    //点击 界面右上角 “完成”按钮去请求数据
    void clickFinishButton();

}
