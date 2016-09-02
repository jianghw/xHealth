package com.kaurihealth.kaurihealth.common.bean;

import com.kaurihealth.datalib.request_bean.bean.DepartmentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.DocumentDisplayBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张磊 on 2016/7/16.
 * 介绍：
 */
public class CommonMedicalBean {
    /**
     * 类别（0：临床诊疗记录，1：实验室检查，2：辅助检查，3：病理）
     */
    public String category;
    /**
     * 主题（其它 = 0,实验室检查 = 1,辅助检查 = 2,病理 = 3,门诊记录电子病历 = 4,远程医疗咨询 = 5,网络医疗咨询 = 6,
     * 入院记录 = 7,协作医疗咨询 = 9,院内治疗相关记录 = 10,影像学检查 = 11,心血管系统相关检查 = 12,血液 = 14,
     * 大便 = 16,临床指导记录 = 17,常规血液学检查 = 18,常规尿液检查 = 19,常规粪便检查 = 20,特殊检查 = 21,门诊记录图片存档 = 22）
     */
    public String subject;
    /**
     * 记录日期
     */
    public String recordDate;
    /**
     * 科室的参数
     */
    public DepartmentDisplayBean department;
    /**
     * 医院
     */
    public String hospital;
    /**
     * 医生
     */
    public String doctor;
    /**
     * 备注
     */
    public String comment;

    public ArrayList<String> recordDocumentStrs = new ArrayList<>();

    public List<DocumentDisplayBean> recordDocuments = new ArrayList<>();
}
