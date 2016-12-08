package com.kaurihealth.datalib.response_bean;

import com.litesuits.orm.db.annotation.Mapping;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.enums.AssignType;
import com.litesuits.orm.db.enums.Relation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Nick on 21/04/2016.
 * 查询医生信息
 */
public class DoctorDisplayBean implements Cloneable,Serializable {
    // 指定自增，每个对象需要有一个主键
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int id;

    public int doctorId;  //医生ID
    public String avatar;  //头像
    public String email; //邮箱
    public String workPhone;  //工作电话
    public String mobilePhone; //移动电话
    public boolean isValidDoctor;  //医生是否有效
    public Date accountActivateDate; //账号激活日期
    public String doctorType; //医生类型（0：普通医生 ，1：知名医生）
    public String firstName; //名
    public String lastName; //姓
    public String kauriHealthId; //佳仁id
    public String gender; //性别
    public Date dateOfBirth; //出生日期
    public int userId; //用户ID
    public int years; //工作年限
    public String practiceField; //实践领域
    public String introduction; //介绍
    public String fullName; //姓名
    public double totalCredit; //账户余额
    public double availableCredit; //可提取金额
    public boolean allowUnknownMessage; //是否允许未知信息
    public String educationTitle; //教育职称
    public String mentorshipTitle; //导师职称
    public String hospitalTitle; //医生职称
    public String nationalIdentity; //身份证
    public String certificationNumber; //证书编号
    public String workingExperience; //工作经历
    public double registPercentage; //注册完成度

    @Mapping(Relation.OneToOne)// 一对一
    public DoctorInformationDisplayBean doctorInformations;//医生信息的参数
    @Mapping(Relation.OneToOne)// 一对一
    public DoctorEducationDisplayBean doctorEducation;

    @Mapping(Relation.OneToMany)//一对多,注意集合形式
    public ArrayList<DoctorGradeDisplayBean> doctorGrades;//医生评分的参数
    @Mapping(Relation.OneToMany)
    public ArrayList<DoctorCertificationDisplayBean> doctorCertifications;//医生证书信息的参数
    @Mapping(Relation.OneToMany)
    public ArrayList<ProductDisplayBean> products;//医生提供服务的参数
    @Mapping(Relation.OneToMany)
    public ArrayList<DoctorGuidanceDisplayBean> doctorGuidances;

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public boolean isValidDoctor() {
        return isValidDoctor;
    }

    public void setValidDoctor(boolean validDoctor) {
        isValidDoctor = validDoctor;
    }

    public Date getAccountActivateDate() {
        return accountActivateDate;
    }

    public void setAccountActivateDate(Date accountActivateDate) {
        this.accountActivateDate = accountActivateDate;
    }

    public String getDoctorType() {
        return doctorType;
    }

    public void setDoctorType(String doctorType) {
        this.doctorType = doctorType;
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

    public String getKauriHealthId() {
        return kauriHealthId;
    }

    public void setKauriHealthId(String kauriHealthId) {
        this.kauriHealthId = kauriHealthId;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public String getPracticeField() {
        return practiceField;
    }

    public void setPracticeField(String practiceField) {
        this.practiceField = practiceField;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getEducationTitle() {
        return educationTitle;
    }

    public void setEducationTitle(String educationTitle) {
        this.educationTitle = educationTitle;
    }

    public String getMentorshipTitle() {
        return mentorshipTitle;
    }

    public void setMentorshipTitle(String mentorshipTitle) {
        this.mentorshipTitle = mentorshipTitle;
    }

    public String getHospitalTitle() {
        return hospitalTitle;
    }

    public void setHospitalTitle(String hospitalTitle) {
        this.hospitalTitle = hospitalTitle;
    }

    public String getNationalIdentity() {
        return nationalIdentity;
    }

    public void setNationalIdentity(String nationalIdentity) {
        this.nationalIdentity = nationalIdentity;
    }

    public String getCertificationNumber() {
        return certificationNumber;
    }

    public void setCertificationNumber(String certificationNumber) {
        this.certificationNumber = certificationNumber;
    }

    public String getWorkingExperience() {
        return workingExperience;
    }

    public void setWorkingExperience(String workingExperience) {
        this.workingExperience = workingExperience;
    }

    public double getRegistPercentage() {
        return registPercentage;
    }

    public void setRegistPercentage(double registPercentage) {
        this.registPercentage = registPercentage;
    }

    public DoctorInformationDisplayBean getDoctorInformations() {
        return doctorInformations;
    }

    public void setDoctorInformations(DoctorInformationDisplayBean doctorInformations) {
        this.doctorInformations = doctorInformations;
    }

    public DoctorEducationDisplayBean getDoctorEducation() {
        return doctorEducation;
    }

    public void setDoctorEducation(DoctorEducationDisplayBean doctorEducation) {
        this.doctorEducation = doctorEducation;
    }

    public ArrayList<DoctorGradeDisplayBean> getDoctorGrades() {
        return doctorGrades;
    }

    public void setDoctorGrades(ArrayList<DoctorGradeDisplayBean> doctorGrades) {
        this.doctorGrades = doctorGrades;
    }

    public ArrayList<DoctorCertificationDisplayBean> getDoctorCertifications() {
        return doctorCertifications;
    }

    public void setDoctorCertifications(ArrayList<DoctorCertificationDisplayBean> doctorCertifications) {
        this.doctorCertifications = doctorCertifications;
    }

    public ArrayList<ProductDisplayBean> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<ProductDisplayBean> products) {
        this.products = products;
    }

    public ArrayList<DoctorGuidanceDisplayBean> getDoctorGuidances() {
        return doctorGuidances;
    }

    public void setDoctorGuidances(ArrayList<DoctorGuidanceDisplayBean> doctorGuidances) {
        this.doctorGuidances = doctorGuidances;
    }

    @Override
    public Object clone(){
        DoctorDisplayBean doctorDisplayBean = null;
        try {
            doctorDisplayBean = (DoctorDisplayBean) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return doctorDisplayBean;
    }
}
