package com.kaurihealth.datalib.response_bean;

/**
 * Created by Garnet_Wu on 2016/11/16.
 * 描述:  家庭成员 添加字段 ""  包装类
 */
public class FamilyMemberBeanWrapper<T> {
    private T bean;
    private String status;

    public FamilyMemberBeanWrapper(T bean, String status) {
        this.bean = bean;
        this.status = status;
    }



    public T getBean() {
        return bean;
    }

    public void setBean(T bean) {
        this.bean = bean;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
