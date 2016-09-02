package com.kaurihealth.kaurihealth.common.util;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.home.util.Interface.IMedicalRecordUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 张磊 on 2016/6/8.
 * 介绍：
 */
public class MedicalRecordUtil implements IMedicalRecordUtil {
    @Nullable
    @Bind(R.id.tvPatientName)
    TextView tvName;
    @Nullable
    @Bind(R.id.tvPatientGender)
    TextView tvGender;
    @Nullable
    @Bind(R.id.tvAge)
    TextView tvAge;

    public MedicalRecordUtil() {
    }

    @Override
    public void setTop(View viewTop, String name, String gender, String age) {
        ButterKnife.bind(this, viewTop);
        tvName.setText(name);
        tvGender.setText(gender);
        tvAge.setText(age);
    }

}
