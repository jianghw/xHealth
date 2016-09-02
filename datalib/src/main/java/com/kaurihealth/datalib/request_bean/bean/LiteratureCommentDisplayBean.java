package com.kaurihealth.datalib.request_bean.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 版权所有者：刘正
 * 创建日期： 2016/5/31.
 * 备注：文章评论
 */
public class LiteratureCommentDisplayBean implements Serializable {
    /**
     * 评论Id
     */
    public int literatureCommentId;
    /**
     * 临床支持Id
     */
    public int medicalLiteratureId;
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
     * 临床支持评论内容
     */
    public String literatureCommentContent;
    /**
     * 临床支持评论内容
     */
    public List<LiteratureReplyDisplayBean> replies;
    /**
     * 创建时间
     */
    public String createTime;

}
