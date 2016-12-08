package com.kaurihealth.kaurihealth.patient_v.health_condition;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.HealthConditionDisplayBean;
import com.kaurihealth.kaurihealth.mine_v.IHealthyRecordSelect;
import com.kaurihealth.kaurihealth.mine_v.HealthyRecordDialog;

/**
 * Created by 张磊 on 2016/6/15.
 * 介绍：
 */
public class Data {
    private String type;
    private String[] res;
    private TextView textView;
    private Activity activity;
    private HealthyRecordDialog healthyRecordDialog;
    private int patientId;
    private String category;

    public Data(final String type, final String[] res, TextView textView, Activity activity, boolean touchAble, int patientId, String category) {
        this.type = type;
        this.res = res;
        this.textView = textView;
        this.activity = activity;
        this.patientId = patientId;
        this.category = category;

        if (touchAble) {
            healthyRecordDialog = new HealthyRecordDialog(activity);
            healthyRecordDialog.setTitle(type);
            healthyRecordDialog.setData(res);
            healthyRecordDialog.setOnSelectListener(new IHealthyRecordSelect() {
                @Override
                public void onSelect(int position) {
                    Data.this.textView.setText(res[position]);
                    if (Data.this.healthySmallIteamBean == null) {
                        Data.this.healthySmallIteamBean = new HealthConditionDisplayBean(0, Data.this.patientId, type, res[position], Data.this.category);
                    } else {
                        Data.this.healthySmallIteamBean.detail = res[position];
                    }
                }
            });
            this.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    healthyRecordDialog.touch();
                }
            });
        }
    }

    HealthConditionDisplayBean healthySmallIteamBean;

    public void setData(HealthConditionDisplayBean healthySmallIteamBean) {
        this.healthySmallIteamBean = healthySmallIteamBean;
        textView.setText(healthySmallIteamBean.detail);
    }

    public HealthConditionDisplayBean getData() {
        return healthySmallIteamBean;
    }
}
