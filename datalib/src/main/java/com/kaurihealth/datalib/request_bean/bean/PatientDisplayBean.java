package com.kaurihealth.datalib.request_bean.bean;

import com.kaurihealth.utilslib.date.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Nick on 21/04/2016.
 */
public class PatientDisplayBean implements Serializable {
    /// <summary>
    /// 患者ID
    /// </summary>
    public int patientId;

    /// <summary>
    /// 身份证
    /// </summary>
    public String nationalIdentity;

    /// <summary>
    /// 佳仁ID
    /// </summary>
    public String kauriHealthId;
    /// <summary>
    /// 头像
    /// </summary>
    public String avatar;

    /// <summary>
    /// 国家医疗保险号码
    /// </summary>
    public String nationalMedicalInsuranceNumber;

    /// <summary>
    /// 手机号
    /// </summary>
    public String mobileNumber;

    /// <summary>
    /// 家庭电话号码
    /// </summary>
    public String homePhoneNumber;

    /// <summary>
    /// 紧急联系电话
    /// </summary>
    public String emergencyContactNumber;

    /// <summary>
    /// 紧急联系人名字
    /// </summary>
    public String emergencyContactName;

    /// <summary>
    /// 紧急联系人关系
    /// </summary>
    public String emergencyContactRelationship;

    /// <summary>
    /// 患者类型（0：注册病人，1：vip病人）
    /// </summary>
    public String patientType;

    /// <summary>
    /// 用户ID
    /// </summary>
    public int userId;

    /// <summary>
    /// 名
    /// </summary>
    public String firstName;

    /// <summary>
    /// 姓
    /// </summary>
    public String lastName;

    /// <summary>
    /// 姓名
    /// </summary>
    public String fullName;

    /// <summary>
    /// 性别
    /// </summary>
    public String gender;

    /// <summary>
    /// 出生年月
    /// </summary>
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "GMT")
    public Date dateOfBirth;

    /// <summary>
    /// 邮箱
    /// </summary>
    public String email;

    /// <summary>
    /// 地址的参数
    /// </summary>
    public AddressDisplayBean address;

    /// <summary>
    /// 获取或设置地址ID
    /// </summary>
    public int addressId;

    /// <summary>
    /// 是否主动
    /// </summary>
    public boolean isActive;

    /// <summary>
    /// 总金额
    /// </summary>
    public double totalCredit;

    /// <summary>
    /// 剩余金额
    /// </summary>
    public double availableCredit;

    /// <summary>
    /// 是否允许未知消息
    /// </summary>
    public boolean allowUnknownMessage;

    /// <summary>
    /// 过敏药物信息
    /// </summary>
    public String allergyDetail;

    /**
     * 获取电话号码
     *
     * @return
     */
    public String getEmergencyContactNumber() {
        if (this.mobileNumber == null) {
            return "无";
        } else {
            return this.mobileNumber;
        }
    }

    /**
     * 获取地址的参数
     *
     * @return
     */
    public String getAddress() {
        if (this.address == null) {
            return "无";
        } else {
            if (this.address.addressLine1 == null) {
                return "无";
            }
            return this.address.addressLine1;
        }

    }

    /**
     * 获取患者头像
     *
     * @return
     */
    public String getAvatar() {
        if (this.avatar == null) {
            return null;
        } else {
            return this.avatar;
        }

    }

    public String getDateOfBirth() {
        int age = DateUtils.getAge(this.dateOfBirth);
        return String.format("%d岁", age);
    }
}
