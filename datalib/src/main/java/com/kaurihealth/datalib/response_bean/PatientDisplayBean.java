package com.kaurihealth.datalib.response_bean;

import com.kaurihealth.datalib.request_bean.bean.AddressDisplayBean;

import java.util.Date;

/**
 * Created by jianghw on 2016/8/24.
 * <p>
 * 描述：
 */
public class PatientDisplayBean {
    private int patientId;  //患者ID
    private String nationalIdentity;  //身份证
    private String kauriHealthId;  //佳仁ID
    private String avatar;  //头像
    private String nationalMedicalInsuranceNumber;  //国家医疗保险号码
    private String mobileNumber;  //手机号
    private String homePhoneNumber;  //家庭电话号码
    private String emergencyContactNumber;  //紧急联系电话
    private String emergencyContactName;   //紧急联系人名字
    private String emergencyContactRelationship;  //紧急联系人关系
    private String patientType;  //患者类型（0：注册病人，1：vip病人,2:子账号）
    private int userId;  //用户ID
    private String firstName;  //名
    private String lastName; //姓
    private String fullName; //姓名
    private String gender; //性别
    private Date dateOfBirth; //出生年月
    private String email;  //邮箱
    private AddressDisplayBean address;  //地址的参数
    private int addressId;  //获取或设置地址ID
    private boolean isActive; //是否激活   isActive(Bool类型)判断患者类型, 1是正在进行服务患者, 0是已过期
    private double totalCredit; //总金额
    private double availableCredit;   //剩余金额
    private boolean allowUnknownMessage;  //是否允许未知消息
    private String allergyDetail;  //过敏药物信息
    private String  phoneNumber; //电话



    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getNationalIdentity() {
        return nationalIdentity;
    }

    public void setNationalIdentity(String nationalIdentity) {
        this.nationalIdentity = nationalIdentity;
    }

    public String getKauriHealthId() {
        return kauriHealthId;
    }

    public void setKauriHealthId(String kauriHealthId) {
        this.kauriHealthId = kauriHealthId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNationalMedicalInsuranceNumber() {
        return nationalMedicalInsuranceNumber;
    }

    public void setNationalMedicalInsuranceNumber(String nationalMedicalInsuranceNumber) {
        this.nationalMedicalInsuranceNumber = nationalMedicalInsuranceNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getHomePhoneNumber() {
        return homePhoneNumber;
    }

    public void setHomePhoneNumber(String homePhoneNumber) {
        this.homePhoneNumber = homePhoneNumber;
    }

    public String getEmergencyContactNumber() {
        return emergencyContactNumber;
    }

    public void setEmergencyContactNumber(String emergencyContactNumber) {
        this.emergencyContactNumber = emergencyContactNumber;
    }

    public Object getEmergencyContactName() {
        return emergencyContactName;
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public Object getEmergencyContactRelationship() {
        return emergencyContactRelationship;
    }

    public void setEmergencyContactRelationship(String emergencyContactRelationship) {
        this.emergencyContactRelationship = emergencyContactRelationship;
    }

    public String getPatientType() {
        return patientType;
    }

    public void setPatientType(String patientType) {
        this.patientType = patientType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AddressDisplayBean getAddress() {
        return address;
    }

    public void setAddress(AddressDisplayBean address) {
        this.address = address;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public double getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(double totalCredit) {
        this.totalCredit = totalCredit;
    }

    public double getAvailableCredit() {
        return availableCredit;
    }

    public void setAvailableCredit(double availableCredit) {
        this.availableCredit = availableCredit;
    }

    public boolean isAllowUnknownMessage() {
        return allowUnknownMessage;
    }

    public void setAllowUnknownMessage(boolean allowUnknownMessage) {
        this.allowUnknownMessage = allowUnknownMessage;
    }

    public String getAllergyDetail() {
        return allergyDetail;
    }

    public void setAllergyDetail(String allergyDetail) {
        this.allergyDetail = allergyDetail;
    }

    public String getPhoneNumber(){return phoneNumber ;}

    public void setPhoneNumber(String phoneNumber){ this.phoneNumber = phoneNumber;}
}
