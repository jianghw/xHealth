package com.kaurihealth.mvplib.register_p;

import com.kaurihealth.mvplib.base_p.IMvpView;

/**
 * Created by KauriHealth on 2016/8/11.
 */
public interface IRegisterPersonInfoView extends IMvpView{

    String getFirstName();

    String getLastName();

    String getGender();

    String getBirthday();

    void connectLeanCloud(String kaurihealthId);
}
