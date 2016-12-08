package com.kaurihealth.mvplib.patient_p;

        import com.kaurihealth.mvplib.base_p.IMvpPresenter;

/**
 * Created by jianghw on 2016/10/24.
 */

public interface IVisitRecordPresenter<V> extends IMvpPresenter<V> {
    void deleteHealthCondition(int id);
}
