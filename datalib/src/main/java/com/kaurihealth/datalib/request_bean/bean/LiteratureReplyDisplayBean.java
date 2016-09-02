package com.kaurihealth.datalib.request_bean.bean;

import java.io.Serializable;

/**
 * 版权所有者：刘正
 * 创建日期： 2016/5/31.
 * 备注：临床支持评论内容
 */
public class LiteratureReplyDisplayBean implements Serializable {
    /**
     * 回复Id
     */
    public int literatureReplyId;
    /**
     * 评论Id
     */
    public int commentId;
    /**
     * 用户Id
     */
    public int userId;
    /**
     * 用户名
     */
    public String userFullName;
    /**
     * 用户名头像
     */
    public String userAvartar;
    /**
     * 评论内容
     */
    public String literatureReplyComment;
    /**
     * 创建时间
     */
    public String createTime;
}
