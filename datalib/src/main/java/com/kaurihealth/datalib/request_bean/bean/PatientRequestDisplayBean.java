package com.kaurihealth.datalib.request_bean.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Nick on 22/04/2016.
 */
public class PatientRequestDisplayBean implements Serializable {
    /// <summary>
    /// 患者请求ID
    /// </summary>
    public int patientRequestId;

    /// <summary>
    /// 医生ID
    /// </summary>
    public int doctorId;

    /// <summary>
    /// 患者ID
    /// </summary>
    public int patientId;

    /// <summary>
    /// 请求类型(0:门诊，1：远程会诊，2：网络会诊，3：专属医生，4：转诊，5：普通医生)
    /// </summary>
    public String requestType;

    /// <summary>
    /// 状态(0:等待,1:进行,2:拒绝,3:接受)
    /// </summary>
    public String status;

    /// <summary>
    /// 请求的原因
    /// </summary>
    public String requestReason;

    /// <summary>
    /// 医生留言
    /// </summary>
    public String doctorComment;

    /// <summary>
    /// 创建时间
    /// </summary>
    public Date createdDate;

    /// <summary>
    /// 请求时间
    /// </summary>
    public Date requestDate;

    /// <summary>
    /// 回复日期
    /// </summary>
    public Date replyDate;

    /// <summary>
    /// 结束时间
    /// </summary>
    public Date endDate;

    /// <summary>
    /// 会诊ID
    /// </summary>
    public int consultationId;

    /// <summary>
    /// Gets or sets the order identifier.
    /// </summary>
    /// <value>
    /// The order identifier.
    /// </value>
    public int orderId;

    /// <summary>
    /// 医生所需的参数
    /// </summary>
    public DoctorDisplayBean doctor;

    /// <summary>
    /// 患者所需的参数
    /// </summary>
    public PatientDisplayBean patient;

    @JsonIgnoreProperties
    public PatientRequestDisplayBean doctorPatientRelationship;

}
