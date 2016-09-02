package com.kaurihealth.mvplib.clinical_p;

import com.kaurihealth.mvplib.base_p.IMvpPresenter;

/**
 * Created by KauriHealth on 2016/8/24.
 */
public interface IDynamicPresenter<V> extends IMvpPresenter<V> {

    void loadingRemoteData(boolean isDirty);

    void LoadMedicalLiteratureById(int medicalLiteratureId);
}
