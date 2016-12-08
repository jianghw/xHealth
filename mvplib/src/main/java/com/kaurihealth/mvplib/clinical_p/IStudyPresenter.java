package com.kaurihealth.mvplib.clinical_p;

import com.kaurihealth.mvplib.base_p.IMvpPresenter;

/**
 * Created by mip on 2016/8/26.
 */
public interface IStudyPresenter<V> extends IMvpPresenter<V> {

    void loadingRemoteData(boolean isDirty);
}
