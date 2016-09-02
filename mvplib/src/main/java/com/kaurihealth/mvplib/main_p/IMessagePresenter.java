package com.kaurihealth.mvplib.main_p;

import com.kaurihealth.mvplib.base_p.IMvpPresenter;

/**
 * Created by jianghw on 2016/8/12.
 * <p/>
 * 描述：
 */
public interface IMessagePresenter<V> extends IMvpPresenter<V> {

    void loadingRemoteData(boolean isDirty);
}
