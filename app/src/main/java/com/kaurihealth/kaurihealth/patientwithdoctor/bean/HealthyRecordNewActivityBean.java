package com.kaurihealth.kaurihealth.patientwithdoctor.bean;

import java.io.Serializable;

/**
 * Created by 张磊 on 2016/4/28.
 * 介绍：
 */
public class HealthyRecordNewActivityBean implements Serializable {
    private int id;
    private EditAddMode mode;
    private String value;
    private int index;
    private String txtName;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public HealthyRecordNewActivityBean(int id, EditAddMode mode, String txtName) {
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

    public EditAddMode getMode() {
        return mode;
    }

    public String getValue() {
        return value;
    }

    public String getTxtName() {
        return txtName;
    }

    public void setTxtName(String txtName) {
        this.txtName = txtName;
    }
}
