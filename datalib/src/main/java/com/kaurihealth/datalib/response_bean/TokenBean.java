package com.kaurihealth.datalib.response_bean;


import com.litesuits.orm.db.annotation.Mapping;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.enums.AssignType;
import com.litesuits.orm.db.enums.Relation;

/**
 * 个人帐户登录
 */
public class TokenBean{

    // 指定自增，每个对象需要有一个主键
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int id;

    /**
     * accessToken : sample string 1
     * refreshToken : sample string 2
     * createdDate : 2016-08-09T09:32:13+08:00
     * expiredDate : 2016-08-09T09:32:13+08:00
     * user : {"userId":1,"userName":"sample string 2","password":"sample string 3","email":"sample string 4","userType":"sample string 5","txtVerified":true,"firstName":"sample string 7","lastName":"sample string 8","fullName":"sample string 9","gender":"sample string 10","dateOfBirth":"2016-08-09T09:32:13+08:00","registerType":"sample string 11","kauriHealthId":"sample string 12","allowUnknownMessage":true,"avatar":"sample string 14","totalCredit":15,"availableCredit":16,"registPercentage":17}
     */

    private String accessToken;
    private String refreshToken;
    private String createdDate;
    private String expiredDate;
    /**
     * userId : 1
     * userName : sample string 2
     * password : sample string 3
     * email : sample string 4
     * userType : sample string 5
     * txtVerified : true
     * firstName : sample string 7
     * lastName : sample string 8
     * fullName : sample string 9
     * gender : sample string 10
     * dateOfBirth : 2016-08-09T09:32:13+08:00
     * registerType : sample string 11
     * kauriHealthId : sample string 12
     * allowUnknownMessage : true
     * avatar : sample string 14
     * totalCredit : 15.0
     * availableCredit : 16.0
     * registPercentage : 17.0
     */

    @Mapping(Relation.OneToOne)
    private UserBean user;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

}
