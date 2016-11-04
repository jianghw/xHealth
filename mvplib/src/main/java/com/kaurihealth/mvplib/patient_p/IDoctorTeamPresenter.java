package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.mvplib.base_p.IMvpPresenter;

/**
 * Created by Nick on 1/09/2016.
 */
public interface IDoctorTeamPresenter<V> extends IMvpPresenter<V> {
    //访问远程数据
    void loadingRemoteData(boolean isDirty);

    //查询和自己已经建立联系的协作医生
    void  showCooperationDoctor();
}
