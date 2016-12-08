package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.mvplib.base_p.IMvpView;

import java.util.List;

/**
 * Created by mip on 2016/9/7.
 */
public interface IHospitalNameView extends IMvpView{

    void getHospitalNameList(List<String> list);

}
