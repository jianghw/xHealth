package com.kaurihealth.mvplib.register_p;

        import com.kaurihealth.mvplib.base_p.IMvpPresenter;

/**
 * Created by jianghw on 2016/8/16.
 * <p/>
 * 描述：
 */
public interface IRegisterPersonInfoPresenter<V> extends IMvpPresenter<V> {
    //我 医生详情
    void loadDoctorDetail();

    //好友关系列表
    void loadContactListByDoctorId();
}
