package com.kaurihealth.datalib.response_bean;

import com.litesuits.orm.db.annotation.Ignore;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.enums.AssignType;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Nick on 24/04/2016.
 * 指导医生的参数  {@link RelatedDoctorBean}
 */
public class DoctorGuidanceDisplayBean implements Serializable {
    // 指定自增，每个对象需要有一个主键
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int id;
    /// <summary>
    /// 指导医生ID
    /// </summary>
    public int doctorGuidanceId;

    /// <summary>
    /// 导师ID
    /// </summary>
    public int coachDoctorId;

    /// <summary>
    /// 学生ID
    /// </summary>
    public int studentDoctorId;

    /// <summary>
    /// 是否激活
    /// </summary>
    public boolean isActive;

    /// <summary>
    /// 指导类型（0：师生指导，1：跨院指导，2：学术指导，3:监督指导，4：临床指导，5：分析指导）
    /// </summary>
    public String guidanceType;

    /// <summary>
    /// 开始时间
    /// </summary>
    public Date startDate;

    /// <summary>
    /// 导师的参数
    /// </summary>
    @Ignore
    public DoctorDisplayBean coachDoctor;

    /// <summary>
    /// 学生的参数
    /// </summary>
    @Ignore
    public DoctorDisplayBean studentDoctor;

    public int getDoctorGuidanceId() {
        return doctorGuidanceId;
    }

    public void setDoctorGuidanceId(int doctorGuidanceId) {
        this.doctorGuidanceId = doctorGuidanceId;
    }

    public int getCoachDoctorId() {
        return coachDoctorId;
    }

    public void setCoachDoctorId(int coachDoctorId) {
        this.coachDoctorId = coachDoctorId;
    }

    public int getStudentDoctorId() {
        return studentDoctorId;
    }

    public void setStudentDoctorId(int studentDoctorId) {
        this.studentDoctorId = studentDoctorId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getGuidanceType() {
        return guidanceType;
    }

    public void setGuidanceType(String guidanceType) {
        this.guidanceType = guidanceType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public DoctorDisplayBean getCoachDoctor() {
        return coachDoctor;
    }

    public void setCoachDoctor(DoctorDisplayBean coachDoctor) {
        this.coachDoctor = coachDoctor;
    }

    public DoctorDisplayBean getStudentDoctor() {
        return studentDoctor;
    }

    public void setStudentDoctor(DoctorDisplayBean studentDoctor) {
        this.studentDoctor = studentDoctor;
    }
}
