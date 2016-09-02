package com.kaurihealth.mvplib.clinical_p;

import com.kaurihealth.datalib.request_bean.bean.MedicalLiteratureDisPlayBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

import java.util.List;

/**
 * Created by mip on 2016/8/26.
 */
public interface ITreatmentView extends IMvpView {

    void loadingIndicator(boolean flag);

    void getAllMedicalLitreaturesSuccess(List<MedicalLiteratureDisPlayBean> mMedicalLiteratureDisPlayBeanList);

    void getAllMedicalLitreaturesError(String error);

    MedicalLiteratureDisPlayBean getMedicalLitreatures(MedicalLiteratureDisPlayBean mMedicalLiteratureDisPlayBeanList);
}
