package com.kaurihealth.datalib.response_bean;

/**
 * Created by jianghw on 2016/12/19.
 * <p/>
 * Describe:实验室检查统计
 */
public class LabTestCountsBean {
    private int count;
    private String keyName;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }
}
