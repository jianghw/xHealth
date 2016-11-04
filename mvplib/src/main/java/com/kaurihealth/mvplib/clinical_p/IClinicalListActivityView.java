package com.kaurihealth.mvplib.clinical_p;

import com.kaurihealth.datalib.request_bean.bean.MedicalLiteratureDisPlayBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

/**
 * Created by mip on 2016/9/1.
 */
public interface IClinicalListActivityView extends IMvpView {

    int getCurrentMedicalLiteratureId();

    void getMedicalLiteratureDisPlayBean(MedicalLiteratureDisPlayBean bean);
}
