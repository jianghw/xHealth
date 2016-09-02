package com.kaurihealth.kaurihealth.home.util;

import android.os.Bundle;

import com.example.commonlibrary.widget.util.ISetBundle;
import com.kaurihealth.kaurihealth.home.util.Config.ConfigBundleHealthyRecord;
import com.kaurihealth.kaurihealth.home.util.Interface.ISetBundleHealthyRecord;

import java.util.Date;

/**
 * Created by 张磊 on 2016/6/8.
 * 介绍：
 */
public class SetBundleHealthyRecord implements ISetBundleHealthyRecord, ISetBundle {
    private Bundle bundle;

    public SetBundleHealthyRecord(Bundle bundle) {
        this.bundle = bundle;
    }

    @Override
    public Bundle getBundle() {
        return bundle;
    }

    @Override
    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    @Override
    public void setGender(String gender) {
        bundle.putString(ConfigBundleHealthyRecord.BundleKeyGender, gender);
    }

    @Override
    public void setName(String name) {
        bundle.putString(ConfigBundleHealthyRecord.BundleKeyName, name);
    }

    @Override
    public void setBirthDate(Date date) {
        bundle.putSerializable(ConfigBundleHealthyRecord.BundleKeyBirthDate, date);
    }

    @Override
    public void setPatientId(int patientId) {
        bundle.putInt(ConfigBundleHealthyRecord.BundleKeyPatientId, patientId);
    }

    @Override
    public void setkauriHealthId(String kauriHealthId) {
        bundle.putString(ConfigBundleHealthyRecord.BundleKeyKauriHealthId, kauriHealthId);
    }

    @Override
    public void setAble(boolean isAble) {
        bundle.putBoolean(ConfigBundleHealthyRecord.IsAble, isAble);
    }
}
