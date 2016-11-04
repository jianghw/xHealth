package com.kaurihealth.datalib.response_bean;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.enums.AssignType;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Nick on 24/04/2016.  {@link RelatedDoctorBean}
 * 医生评分的参数
 */
public class DoctorGradeDisplayBean implements Serializable{
    // 指定自增，每个对象需要有一个主键
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int id;
    /// <summary>
    /// 医生评分ID
    /// </summary>
    public int doctorGradeId;

    /// <summary>
    /// 医生ID
    /// </summary>
    public int doctorId;

    /// <summary>
    /// 评分医生ID
    /// </summary>
    public int markDoctorId;

    /// <summary>
    /// 评分
    /// </summary>
    public int grade;

    /// <summary>
    /// 说明
    /// </summary>
    public String comment;

    /// <summary>
    /// 时间
    /// </summary>
    public Date date;

    public int getDoctorGradeId() {
        return doctorGradeId;
    }

    public void setDoctorGradeId(int doctorGradeId) {
        this.doctorGradeId = doctorGradeId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getMarkDoctorId() {
        return markDoctorId;
    }

    public void setMarkDoctorId(int markDoctorId) {
        this.markDoctorId = markDoctorId;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
