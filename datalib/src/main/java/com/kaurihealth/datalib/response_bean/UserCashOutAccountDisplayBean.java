package com.kaurihealth.datalib.response_bean;

import java.io.Serializable;

/**
 * Created by 张磊 on 2016/7/12.
 * 介绍：读取所有用户提现账号
 */
public class UserCashOutAccountDisplayBean implements Serializable{
   /* [
    {
        "userCashOutAccountId": 1,
            "accountType": "农业银行",
            "accountSource": "15154348134513645",
            "accountDetail": "154525555555555",
            "accountOwner": "张一一",
            "userId": 20,
            "nationalId": null
    },
    {
        "userCashOutAccountId": 3,
            "accountType": "农业银行",
            "accountSource": "啦啦噜我是",
            "accountDetail": "412702199212208410",
            "accountOwner": "张一一",
            "userId": 20,
            "nationalId": null
    },
    {
        "userCashOutAccountId": 4,
            "accountType": "农业银行",
            "accountSource": "这些都是在你心里",
            "accountDetail": "412702199212208410",
            "accountOwner": "张一一",
            "userId": 20,
            "nationalId": null
    }
    ]*/
    public int userCashOutAccountId;
    public String accountType;
    public String accountSource;
    public String accountDetail;
    public String accountOwner;
    public int userId;
    public String nationalId;

    public int getUserCashOutAccountId() {
        return userCashOutAccountId;
    }

    public void setUserCashOutAccountId(int userCashOutAccountId) {
        this.userCashOutAccountId = userCashOutAccountId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountSource() {
        return accountSource;
    }

    public void setAccountSource(String accountSource) {
        this.accountSource = accountSource;
    }

    public String getAccountDetail() {
        return accountDetail;
    }

    public void setAccountDetail(String accountDetail) {
        this.accountDetail = accountDetail;
    }

    public String getAccountOwner() {
        return accountOwner;
    }

    public void setAccountOwner(String accountOwner) {
        this.accountOwner = accountOwner;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }
}
