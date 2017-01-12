package com.kaurihealth.mvplib.casehistory_p;

import com.kaurihealth.mvplib.base_p.IMvpPresenter;

/**
 * Created by mip on 2017/1/9.
 */

public interface ICaseHistoryPresenter<V> extends IMvpPresenter<V>{

     void loadingRemoteData(boolean isDirty);

     void clearCaseHistoryData();
}
