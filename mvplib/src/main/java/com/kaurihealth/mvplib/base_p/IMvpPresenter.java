package com.kaurihealth.mvplib.base_p;

/**
 * Created by jianghw on 2016/8/12.
 * <p/>
 * 描述：
 */
public interface IMvpPresenter<V> {

    void setPresenter(V view);

    void onSubscribe();

    void unSubscribe();
}
