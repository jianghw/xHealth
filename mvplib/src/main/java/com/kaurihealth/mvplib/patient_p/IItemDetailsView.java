package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.mvplib.base_p.IMvpView;

/**
 * Created by mip on 2016/12/1.
 * <p/>
 * Describe:
 */

public interface IItemDetailsView<T> extends IMvpView{

    int getDoctorOrPatientId();

    void getResult(T t,String mark);

    String getKuarihealthId();

}
