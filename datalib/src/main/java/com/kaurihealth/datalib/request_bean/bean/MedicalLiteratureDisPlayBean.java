package com.kaurihealth.datalib.request_bean.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 版权所有者：刘正
 * 创建日期： 2016/5/31.
 * 备注：临床支持
 */
public class MedicalLiteratureDisPlayBean implements Serializable {
    /**
     * 临床支持Id
     */
    public int medicalLiteratureId;
    /**
     * 临床支持标题
     */
    public String medicalLiteratureTitle;
    /**
     * 临床支持类型 科学文献 = 0,医学教育 = 1,疾病 = 2,会议及学习 = 3
     */
    public String medicalLiteratureType;
    /**
     * 内容摘要
     */
    public String contentBrief;
    /**
     * 浏览量
     */
    public int browse;
    /**
     * 下载量
     */
    public int downLoads;
    /**
     * 评论次数
     */
    public int commentCount;
    /**
     * 来源
     */
    public String source;
    /**
     * 创建时间
     */
    public String creatTime;
    /**
     * 文献状态 置顶 = 0,发布中 = 1,已发布 = 2,过期 =3
     */
    public int medicalLiteratureState;
    /**
     * 临床支持子类别
     * 院室介绍 = 0,名医介绍 = 1,医学信息 = 2,行业动态 = 3,图文 = 4,
     * 课件 = 5,视频 = 6,在线会议 = 7,疾病与治疗 = 8,会议及学习信息 = 9
     */
    public String medicalLiteratureCategory;
    /**
     * 标题图片
     */
    public String titleImage;
    /**
     * 文章内容
     */
    public LiteratureContentDisplayBean content;
    /**
     * 文章评论
     */
    public List<LiteratureCommentDisplayBean> comments;
    /**
     * 发表的医生
     */
    public MedicalLiteratureDoctorDisplayBean doctor;
    /**
     * 用户Id
     */
    public int userId;
    /**
     * 收藏次数
     */
    public int favoriteCount;
    /**
     * 点赞次数
     */
    public int likeCount;
    /**
     * 临床支持附件
     */
    public List<MedicalLiteratureDocumentDisplayBean> documents;

    /**
     * 最近更新时间
     */
    public String lastUpdateTime;
}
