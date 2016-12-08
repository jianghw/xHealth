package com.kaurihealth.datalib.request_bean.builder;


import com.kaurihealth.datalib.request_bean.bean.NewLiteratureReplyDisplayBean;

/**
 * 版权所有者：刘正
 * 创建日期： 2016/7/6.
 * 备注：插入新的回复 需要传入的值
 */
public class NewLiteratureReplyDisplayBeanBuilder {
    public NewLiteratureReplyDisplayBean Builder(int id, String str) {
        NewLiteratureReplyDisplayBean newLiteratureReplyDisplayBean = new NewLiteratureReplyDisplayBean();
        newLiteratureReplyDisplayBean.commentId = id;
        newLiteratureReplyDisplayBean.literatureReplyComment = str;
        return newLiteratureReplyDisplayBean;
    }
}
