package com.kaurihealth.datalib.request_bean.bean;

import java.lang.reflect.Type;

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
/*
    Type说明:

    类型(0:目前主要健康问题，1：既往疾病与健康问题，2：目前应用药物，3：药物及其它过敏史，4：既往手术或其它既往治疗，5：吸烟，
            6：饮酒，7：预防接种史，8：饮食习惯，9：家族病史，10：运动习惯，11：月经周期，
            12：月经量，13：怀孕，14：生育，15：流产，16：是否有宫外孕，17：是否有刨腹产)*/


    /*
            category 分类说明:
            分类 目前主要健康问题 = 0,既往疾病与健康问题 = 1,目前应用药物 = 2,药物及其它过敏史 = 3,
            既往手术或其它既往治疗 = 4,家族病史 = 5,个人生活习惯 = 6,预防接种 = 7,月经与生育史 = 8
              */

    public int healthConditionId;  //健康状况ID
    public int patientId; //患者ID
    public String type;
    public String detail; //信息
    public String category; //分类

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

    public int getHealthConditionId() {
        return healthConditionId;
    }

    public void setHealthConditionId(int healthConditionId) {
        this.healthConditionId = healthConditionId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
