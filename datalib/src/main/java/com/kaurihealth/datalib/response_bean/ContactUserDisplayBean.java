package com.kaurihealth.datalib.response_bean;

import java.util.Date;

/**
 * Created by jianghw on 2016/10/10.
 * <p/>
 * 描述：读取医生相关好友列表
 */

public class ContactUserDisplayBean {
    /**
     * kauriHealthId : sample string 1
     * gender : 0
     * userType : 0
     * avatar : sample string 2
     * fullName : sample string 3
     */

//    @PrimaryKey(AssignType.AUTO_INCREMENT)
//    private int id;

    private boolean isTop;
    private String kauriHealthId;
    private String gender;
    private int userType;
    private String avatar;
    private String fullName;
    private Date dateOfBirth;

    public ContactUserDisplayBean(String fullName) {
        this.fullName = fullName;
    }

    public ContactUserDisplayBean(String kauriHealthId, String gender, int userType, String avatar, String fullName, Date dateOfBirth,boolean isTop) {
        this.kauriHealthId = kauriHealthId;
        this.gender = gender;
        this.userType = userType;
        this.avatar = avatar;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.isTop = isTop;
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

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isTop() {
        return isTop;
    }

    public void setTop(boolean top) {
        isTop = top;
    }
}
