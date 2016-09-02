package com.kaurihealth.kaurihealth.home.util;

import android.os.Bundle;

import com.kaurihealth.kaurihealth.home.util.Interface.IBundleFactory;
import com.kaurihealth.kaurihealth.home.util.Interface.IGetBundleHealthyRecord;
import com.kaurihealth.kaurihealth.home.util.Interface.ISetBundleHealthyRecord;

/**
 * Created by 张磊 on 2016/6/8.
 * 介绍：
 */
public class BundleFactory implements IBundleFactory {
    @Override
    public IGetBundleHealthyRecord getIGetBundleHealthyRecord(Bundle bundle) {
        GetBundleHealthyRecord getBundleHealthyRecord = new GetBundleHealthyRecord(bundle);
        return getBundleHealthyRecord;
    }

    @Override
    public ISetBundleHealthyRecord getISetBundleHealthyRecord(Bundle bundle) {
        SetBundleHealthyRecord setBundleHealthyRecord = new SetBundleHealthyRecord(bundle);
        return setBundleHealthyRecord;
    }
}
