package com.kaurihealth.datalib.response_bean;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.enums.AssignType;

/**
 * Created by jianghw on 2016/8/24.
 * <p/>
 * 描述：用户的参数
 */
public class UserBean {
    // 指定自增，每个对象需要有一个主键
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int id;

    private int userId;
    private String userName;
    private String password;
    private String email;
    private String userType;
    private boolean txtVerified;
    private String firstName;
    private String lastName;
    private String fullName;
    private String gender;
    private String dateOfBirth;
    private String registerType;
    private String kauriHealthId;
    private boolean allowUnknownMessage;
    private String avatar;
    private double totalCredit;
    private double availableCredit;
    private double registPercentage;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public boolean isTxtVerified() {
        return txtVerified;
    }

    public void setTxtVerified(boolean txtVerified) {
        this.txtVerified = txtVerified;
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

    public String getRegisterType() {
        return registerType;
    }

    public void setRegisterType(String registerType) {
        this.registerType = registerType;
    }

    public String getKauriHealthId() {
        return kauriHealthId;
    }

    public void setKauriHealthId(String kauriHealthId) {
        this.kauriHealthId = kauriHealthId;
    }

    public boolean isAllowUnknownMessage() {
        return allowUnknownMessage;
    }

    public void setAllowUnknownMessage(boolean allowUnknownMessage) {
        this.allowUnknownMessage = allowUnknownMessage;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public double getRegistPercentage() {
        return registPercentage;
    }

    public void setRegistPercentage(double registPercentage) {
        this.registPercentage = registPercentage;
    }
}
