package com.kaurihealth.datalib.request_bean.bean;




import java.io.Serializable;
import java.util.List;

/**
 * 版权所有者：刘正
 * 创建日期： 2016/6/7.
 * 备注：更新患者临床诊疗记录 医生和患者的token都可以
 */
public class PatientRecordDisplayBean implements Serializable{
    /**
     * 患者记录ID
     */
    public int patientRecordId;
    /**
     * 患者ID
     */
    public int patientId;
    /**
     * 备注
     */
    public String comment;
    /**
     * 创建时间
     */
    public String createdDate;
    /**
     * 创建者
     */
    public int createdBy;
    /**
     * 修改时间
     */
    public String modifiedDate;
    /**
     * 记录日期
     */
    public String recordDate;
    /**
     * 修改者
     */
    public int modifiedBy;
    /**
     * 医生
     */
    public String doctor;
    /**
     * 医院
     */
    public String hospital;
    /**
     * 科室ID
     */
    public int departmentId;
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
     * 病例的参数
     */
    public RecordDisplayBean record;
    /**
     * 实验室测试的参数
     */
    public LabTestDisplayBean labTest;
    /**
     * 辅助检查的参数
     */
    public SupplementaryTestDisplayBean supplementaryTest;
    /**
     * 病理的参数
     */
    public PathologyRecordDisplayBean pathologyRecord;
    /**
     * 科室的参数
     */
    public DepartmentDisplayBean department;
    /**
     * 是否删除
     */
    public boolean isDeleted;
    /**
     * 状态（0：草稿，1：发布）
     */
    public String status;
    /**
     * 是不是最新的
     */
    public boolean isNew;
    /**
     * 病例图像的参数
     */
    public List<RecordDocumentDisplayBean> recordDocuments;
}
