package com.kaurihealth.mvplib.rcord_details_p;

import com.kaurihealth.mvplib.base_p.IMvpPresenter;

/**
 * Created by jianghw on 2016/8/12.
 * <p/>
 * 描述：
 */
public interface INMedicalRecordsPresenter<V> extends IMvpPresenter<V> {

    void loadingRemoteData(boolean isDirty);

    void LoadNewPatientRecordCountsByPatientId();
}
