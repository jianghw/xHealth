package com.kaurihealth.datalib.request_bean.bean;

/**
 * 版权:    张磊
 * 作者:    张磊
 * 描述：
 * 创建日期:2016/1/21
 * 修订日期:
 */
public class HealthConditionDisplayBean {

    /**
     * healthConditionId : 2987
     * patientId : 59122
     * type : 目前主要健康问题
     * detail : 哈哈哈
     * category : 目前主要健康问题
     */

    public int healthConditionId;
    public int patientId;
    public String type;
    public String detail;
    public String category;

    public HealthConditionDisplayBean() {
    }

    public HealthConditionDisplayBean(int healthConditionId, int patientId, String type, String detail, String category) {
        this.healthConditionId = healthConditionId;
        this.patientId = patientId;
        this.type = type;
        this.detail = detail;
        this.category = category;
    }



    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof HealthConditionDisplayBean) {
            return ((HealthConditionDisplayBean) o).healthConditionId == healthConditionId;
        }
        return super.equals(o);
    }
}
