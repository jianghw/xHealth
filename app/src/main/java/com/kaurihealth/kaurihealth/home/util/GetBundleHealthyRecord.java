package com.kaurihealth.kaurihealth.home.util;

import android.os.Bundle;

import com.example.commonlibrary.widget.util.ISetBundle;
import com.kaurihealth.kaurihealth.home.util.Config.ConfigBundleHealthyRecord;
import com.kaurihealth.kaurihealth.home.util.Interface.IGetBundleHealthyRecord;
import com.youyou.zllibrary.util.Utils;

import java.util.Date;

/**
 * Created by 张磊 on 2016/6/8.
 * 介绍：
 */
public class GetBundleHealthyRecord implements IGetBundleHealthyRecord, ISetBundle {
    private Bundle bundle;

    public GetBundleHealthyRecord(Bundle bundle) {
        this.bundle = bundle;
    }
    @Override
    public String getGender() {
        return bundle.getString(ConfigBundleHealthyRecord.BundleKeyGender);
    }

    @Override
    public String getName() {
        return bundle.getString(ConfigBundleHealthyRecord.BundleKeyName);
    }

    @Override
    public String getAge() {
        Date birthDate = (Date) bundle.getSerializable(ConfigBundleHealthyRecord.BundleKeyBirthDate);
        int age = Utils.calculateAge(birthDate);
        return String.format("%d岁", age);
    }

    @Override
    public int getPatientId() {
        return bundle.getInt(ConfigBundleHealthyRecord.BundleKeyPatientId);
    }

    @Override
    public String getKauriHealthId() {
        return bundle.getString(ConfigBundleHealthyRecord.BundleKeyKauriHealthId);
    }

    @Override
    public boolean isAble() {
        return bundle.getBoolean(ConfigBundleHealthyRecord.IsAble);
    }

    @Override
    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }
}
