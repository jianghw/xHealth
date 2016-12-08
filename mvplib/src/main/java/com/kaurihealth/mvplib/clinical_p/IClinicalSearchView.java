package com.kaurihealth.mvplib.clinical_p;

import com.kaurihealth.datalib.request_bean.bean.MedicalLiteratureDisPlayBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

import java.util.List;

/**
 * Created by mip on 2016/9/1.
 */
public interface IClinicalSearchView extends IMvpView{

    int getCurrentMedicalLiteratureId();

    void getMedicalLiteratureDisPlayBean(MedicalLiteratureDisPlayBean bean);

    void getliteratureDisPlayBeanList(List<MedicalLiteratureDisPlayBean> been);
}
