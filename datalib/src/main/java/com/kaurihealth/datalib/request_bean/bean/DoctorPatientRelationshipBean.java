package com.kaurihealth.datalib.request_bean.bean;

import android.text.TextUtils;

import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.PatientDisplayBean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Nick on 21/04/2016.
 */
public class DoctorPatientRelationshipBean implements Serializable {
    /// <summary>
    /// 医患关系ID
    /// </summary>
    public int doctorPatientId;

    /// <summary>
    /// 医生ID
    /// </summary>
    public int doctorId;

    //Should remove this
    public int patientRequestId;

    /// <summary>
    /// 患者ID
    /// </summary>
    public int patientId;

    /// <summary>
    ///0：代表还在服务期间（进行中），1：代表服务已结束
    /// </summary>
    public boolean isActive;

    /// <summary>
    ///是否等待患者确认结束关系
    /// </summary>
    public boolean isWaitingForPatientConfirm;

    /// <summary>
    /// 患者是否确认结束关系
    /// </summary>
    public boolean isPatientConfirmed;

    /// <summary>
    /// 关系（0：普通，1：专属，2：转诊，3：协作）
    /// </summary>
    public String relationship;

    /// <summary>
    /// 医生的参数
    /// </summary>
    public DoctorDisplayBean doctor;

    /// <summary>
    /// 患者的参数
    /// </summary>
    public PatientDisplayBean patient;

    /// <summary>
    /// 请求时间
    /// </summary>
    public Date requestDate;

    /// <summary>
    /// 开始时间
    /// </summary>
    public Date startDate;

    /// <summary>
    /// 结束时间
    /// </summary>
    public Date endDate;

    /// <summary>
    /// 请求原因/备注
    /// </summary>
    public String comment;

    /// <summary>
    /// 医患关系发起原因
    /// </summary>
    public String relationshipReason;

    @Override
    public int hashCode() {
        return patientId;
    }


    @Override
    public boolean equals(Object o) {
        if (o instanceof DoctorPatientRelationshipBean) {
            return ((DoctorPatientRelationshipBean) o).patientId == patientId;
        } else {
            return super.equals(o);
        }
    }

    public String getComment() {
        if (this.comment == null) {
            return "无";
        } else {
            return this.comment;
        }
    }

    public String getRelationshipReason() {
        if (TextUtils.isEmpty(this.relationshipReason)) {
            return "";
        }
        if (this.relationshipReason.equals("门诊")) {
            return this.relationshipReason + "患者";
        } else if (this.relationshipReason.equals("远程医疗咨询")) {
            return this.relationshipReason + "服务";
        } else if (this.relationshipReason.equals("专属医生")) {
            return this.relationshipReason + "服务";
        } else if (this.relationshipReason.equals("转诊")) {
            return this.relationshipReason + "服务";
        } else {
            return this.relationshipReason + "服务";
        }
    }


}
