package com.kaurihealth.datalib.response_bean;

/**
 * Created by Garnet_Wu on 2016/11/16.
 */
public class FamilyMemberBean {
    private int familyGroupId;   //家庭成员主id
    private int userId;   //用户id
    private String firstName;  //名
    private String lastName; //姓
    private String fullName; //姓名
    private String gender; //性别
    private String dateOfBirth ; //出生日期
    /**
     * 关系（0：夫妻，1：母亲，2：父亲，3：子女，4：丈母娘，5：老丈人）
     */
    private String relationship;  //关系
    private  String avatar; //头像



    //patientDisplayBean包含是否激活  boolean isActive;  isActive(Bool类型)判断患者类型, 1是正在进行服务患者, 0是已过期
    private PatientDisplayBean patient;   //患者

    public int getFamilyGroupId() {
        return familyGroupId;
    }

    public void setFamilyGroupId(int familyGroupId) {
        this.familyGroupId = familyGroupId;
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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public PatientDisplayBean getPatient() {
        return patient;
    }

    public void setPatient(PatientDisplayBean patientDisplayBean) {
        this.patient = patientDisplayBean;
    }
}
