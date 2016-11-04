package com.kaurihealth.datalib.request_bean.bean;

/**
 * Created by jianghw on 2016/11/3.
 * <p/>
 * Describe:
 */

public class ConversationItemBean<T> {

    private int count;
    private String[] mStrings;
    private T userBean;

    public ConversationItemBean(int count, String[] mStrings, T userBean) {
        this.count = count;
        this.mStrings = mStrings;
        this.userBean = userBean;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public T getUserBean() {
        return userBean;
    }

    public void setUserBean(T userBean) {
        this.userBean = userBean;
    }

}
