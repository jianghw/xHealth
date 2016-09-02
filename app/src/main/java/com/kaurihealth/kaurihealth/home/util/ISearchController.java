package com.kaurihealth.kaurihealth.home.util;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by 张磊 on 2016/7/27.
 * 介绍：
 */
public interface ISearchController {
    boolean isDoctorFriend(int doctorId);

    boolean isPatientFriend(int patientId);

    void addDoctorFriend(int doctorId);

    void addPatientFriend(int patientId);

    void startActivityForResult(Class<? extends Activity> purpose, Bundle bundle, int requestCode);
}
