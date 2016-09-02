package com.kaurihealth.datalib.request_bean.bean;

import java.io.Serializable;

/**
 * 版权所有者：刘正
 * 创建日期： 2016/5/31.
 * 备注：发表的医生
 */
public class MedicalLiteratureDoctorDisplayBean implements Serializable {
    /**
     * 医生ID
     */
    public int doctorId;
    /**
     * 科室ID
     */
    public int departmentId;
    /**
     *头像
     */
    public String avatar;
    /**
     *医生类型（0：普通医生，1：知名医生）
     */
    public String doctorType;
    /**
     *名
     */
    public String firstName;
    /**
     *姓
     */
    public String lastName;
    /**
     *佳人id
     */
    public String kauriHealthId;
    /**
     *级别
     */
    public String gender;
    /**
     *出生日期
     */
    public String dateOfBirth;
    /**
     *用户ID
     */
    public int userId;
    /**
     *工作年限
     */
    public int years;
    /**
     *实践领域
     */
    public String practiceField;
    /**
     *介绍
     */
    public String introduction;
    /**
     *姓名
     */
    public String fullName;
    /**
     *教育职称
     */
    public String educationTitle;
    /**
     *导师职称
     */
    public String mentorshipTitle;
    /**
     *医院职称
     */
    public String hospitalTitle;
    /**
     *身份证
     */
    public String nationalIdentity;
    /**
     *证书编号
     */
    public String certificationNumber;
    /**
     *工作经历
     */
    public String workingExperience;
    /**
     *医生信息的参数
     */
    public DoctorInformationDisplayBean doctorInformation;

}

