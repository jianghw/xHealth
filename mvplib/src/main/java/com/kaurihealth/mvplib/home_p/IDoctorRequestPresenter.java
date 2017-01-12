package com.kaurihealth.mvplib.home_p;

import com.kaurihealth.datalib.response_bean.DoctorRelationshipBean;
import com.kaurihealth.mvplib.base_p.IMvpPresenter;

import java.util.List;

/**
 * Created by jianghw on 2016/9/19.
 */
public interface IDoctorRequestPresenter<V> extends IMvpPresenter<V> {
    void loadDoctorDetail(boolean b);

    void acceptDoctorRelationship(int relationshipId);

    void rejectDoctorRelationship(int relationshipId);

    void manualSearch(CharSequence text, List<DoctorRelationshipBean> list);

    void removeNonAccepted();

}
