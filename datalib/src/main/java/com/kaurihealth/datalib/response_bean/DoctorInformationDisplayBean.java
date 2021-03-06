package com.kaurihealth.datalib.response_bean;

import com.litesuits.orm.db.annotation.Mapping;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.enums.AssignType;
import com.litesuits.orm.db.enums.Relation;

import java.io.Serializable;

/**
 * Created by jianghw on 2016/9/19.
 * <p>
 * 描述：医生信息的参数  {@link RelatedDoctorBean}
 */
public class DoctorInformationDisplayBean implements Serializable{
    // 指定自增，每个对象需要有一个主键
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int id;

    private int doctorInformationId;  //医生信息ID
    private int departmentId;    //科室ID
    private String hospitalName;  //医院名字
    private int doctorId;  //医生ID
    /**
     * departmentId : 40
     * departmentName : 手显微外科
     * level : 2
     * parent : 37
     */
    @Mapping(Relation.OneToOne)// 一对一
    private DepartmentDisplayBean department;  //科室的参数
    private String outpatientAvalibility;  //门诊作息

    public int getDoctorInformationId() {
        return doctorInformationId;
    }

    public void setDoctorInformationId(int doctorInformationId) {
        this.doctorInformationId = doctorInformationId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public DepartmentDisplayBean getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDisplayBean department) {
        this.department = department;
    }

    public String getOutpatientAvalibility() {
        return outpatientAvalibility;
    }

    public void setOutpatientAvalibility(String outpatientAvalibility) {
        this.outpatientAvalibility = outpatientAvalibility;
    }
}
