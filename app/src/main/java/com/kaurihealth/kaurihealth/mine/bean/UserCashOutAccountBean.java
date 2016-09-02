package com.kaurihealth.kaurihealth.mine.bean;

import com.kaurihealth.datalib.request_bean.bean.UserCashOutAccountDisplayBean;
import com.kaurihealth.kaurihealth.util.IllegalFormatException;
import com.kaurihealth.kaurihealth.util.NoValueException;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by 张磊 on 2016/7/12.
 * 介绍：
 */
public class UserCashOutAccountBean {
    private List<UserCashOutAccountDisplayBean> accountList;
    private String endStr;
    private List<String> accounts;

    public UserCashOutAccountBean(List<UserCashOutAccountDisplayBean> accountList) {
        this.accountList = accountList;
    }

    public String getIteamShow(int position) throws NoValueException {
        return accounts.get(position);
    }

    public UserCashOutAccountDisplayBean getIteam(int position) throws NoValueException {
        if (position >= (accountList.size() - 1) && position < 0) {
            throw new NoValueException();
        } else {
            return accountList.get(position);
        }
    }

    public List<String> getAllIteamShow() {
        accounts = new LinkedList<>();
        for (UserCashOutAccountDisplayBean iteam : accountList) {
            try {
                accounts.add(handleOne(iteam));
            } catch (IllegalFormatException e) {
                e.printStackTrace();
            }
        }
        accounts.add(endStr);
        return accounts;
    }

    private String handleOne(UserCashOutAccountDisplayBean userCashOutAccountDisplayBean) throws IllegalFormatException {
        if (userCashOutAccountDisplayBean.accountDetail.length() < 4) {
            throw new IllegalFormatException();
        }
        StringBuilder iteamShow = new StringBuilder();
        switch (userCashOutAccountDisplayBean.accountType) {
            case "支付宝":
                iteamShow.append(userCashOutAccountDisplayBean.accountType);
                iteamShow.append(" (");
                String userName = userCashOutAccountDisplayBean.accountOwner;
                userName = userName.substring(1, userName.length());
                iteamShow.append(userName);
                iteamShow.append(")");
                break;
            default:
                iteamShow.append(userCashOutAccountDisplayBean.accountType);
                iteamShow.append(" (");
                String accountDetail = userCashOutAccountDisplayBean.accountDetail;
                accountDetail = accountDetail.substring(accountDetail.length() - 4, accountDetail.length());
                iteamShow.append(accountDetail);
                iteamShow.append(")");
                break;
        }
        return iteamShow.toString();
    }

    public void setEnd(String endContent) {
        this.endStr = endContent;
    }

    public boolean isEnd(int position) {
        return accounts.size() == position+1;
    }
}
