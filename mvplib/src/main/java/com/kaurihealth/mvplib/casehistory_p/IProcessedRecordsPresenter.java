package com.kaurihealth.mvplib.casehistory_p;

import com.kaurihealth.datalib.response_bean.KauriHealthPendingConsultationReferralBean;
import com.kaurihealth.mvplib.base_p.IMvpPresenter;

import java.util.List;

/**
 * Created by jianghw on 2016/8/12.
 * <p/>
 * 描述：
 */
public interface IProcessedRecordsPresenter<V> extends IMvpPresenter<V> {

    void loadingRemoteData(boolean isDirty);

    void newLoadPendingConsultationReferralsByDoctorId();

    void manualSearch(CharSequence text, List<KauriHealthPendingConsultationReferralBean> list);
}
