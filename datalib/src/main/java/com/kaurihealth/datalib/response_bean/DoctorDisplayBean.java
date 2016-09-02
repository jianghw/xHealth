package com.kaurihealth.datalib.response_bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kaurihealth.datalib.request_bean.bean.DoctorCertificationDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.DoctorEducationDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.DoctorGradeDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.DoctorGuidanceDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.DoctorInformationDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.ProductDisplayBean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Nick on 21/04/2016.
 */
public class DoctorDisplayBean implements Serializable {
    public int doctorId;
    public String avatar;  //头像
    public String email;
    public String workPhone;
    public String mobilePhone;
    public boolean isValidDoctor;
    public Date accountActivateDate;
    public String doctorType;
    public String firstName;
    public String lastName;
    public String kauriHealthId;
    public String gender;
    public Date dateOfBirth;
    public int userId;
    public int years;
    public String practiceField;
    public String introduction;
    public String fullName;
    public double totalCredit;
    public double availableCredit;
    public boolean allowUnknownMessage;
    public String educationTitle;
    public String mentorshipTitle;
    public String hospitalTitle;
    public String nationalIdentity;
    public String certificationNumber;
    public String workingExperience;
    public double registPercentage;
    public DoctorInformationDisplayBean doctorInformations;
    @JsonIgnoreProperties
    public DoctorEducationDisplayBean doctorEducation;
    @JsonIgnoreProperties
    public List<DoctorGradeDisplayBean> doctorGrades;
    @JsonIgnoreProperties
    public List<DoctorCertificationDisplayBean> doctorCertifications;
    @JsonIgnoreProperties
    public List<ProductDisplayBean> products;
    @JsonIgnoreProperties
    public List<DoctorGuidanceDisplayBean> doctorGuidances;

}
