package com.kaurihealth.datalib.request_bean.builder;


import com.kaurihealth.datalib.request_bean.bean.NewLiteratureCommentDisplayBean;

/**
 * 版权所有者：刘正
 * 创建日期： 2016/7/5.
 * 备注：根据临床支持评论对象插入新的临床支持评论传入的对象
 */
public class NewLiteratureCommentDisplayBeanBuilder {
    public NewLiteratureCommentDisplayBean Builder(int id, String str) {
        NewLiteratureCommentDisplayBean newLiteratureCommentDisplayBean = new NewLiteratureCommentDisplayBean();
        newLiteratureCommentDisplayBean.medicalLiteratureId = id;
        newLiteratureCommentDisplayBean.literatureCommentContent = str;
        return newLiteratureCommentDisplayBean;
    }
}
