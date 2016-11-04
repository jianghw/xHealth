package com.kaurihealth.mvplib.clinical_p;

import com.kaurihealth.mvplib.base_p.IMvpPresenter;

/**
 * Created by mip on 2016/9/1.
 */
public interface IMyClinicalListPresenter<V> extends IMvpPresenter<V> {

    void LoadMedicalLiteratureById();

}
