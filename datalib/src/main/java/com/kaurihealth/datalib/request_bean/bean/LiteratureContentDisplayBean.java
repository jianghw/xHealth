package com.kaurihealth.datalib.request_bean.bean;

import java.io.Serializable;

/**
 * 版权所有者：刘正
 * 创建日期： 2016/5/31.
 * 备注：文章内容
 */
public class LiteratureContentDisplayBean implements Serializable{
    /**
     * 临床支持内容Id
     */
    public int literatureContentId;
    /**
     * 临床支持Id
     */
    public int medicalLiteratureId;
    /**
     * 临床支持内容
     */
    public String commentsContent;
}
