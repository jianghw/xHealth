package com.kaurihealth.kaurihealth.patient_v.health_condition;

import java.io.Serializable;

/**
 * Created by 张磊 on 2016/4/28.
 * 介绍：
 */
class HealthyRecordNewActivityBean implements Serializable {
    private int id;
    private HealthConditionActivity.EditAddMode mode;
    private String value;
    private int index;
    private String txtName;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    HealthyRecordNewActivityBean(int id, HealthConditionActivity.EditAddMode mode, String txtName) {
        this.id = id;
        this.mode = mode;
        this.txtName = txtName;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public HealthConditionActivity.EditAddMode getMode() {
        return mode;
    }

    public String getValue() {
        return value;
    }

    String getTxtName() {
        return txtName;
    }

    public void setTxtName(String txtName) {
        this.txtName = txtName;
    }
}
