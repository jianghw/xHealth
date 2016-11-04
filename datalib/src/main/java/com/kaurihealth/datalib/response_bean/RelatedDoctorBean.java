package com.kaurihealth.datalib.response_bean;

import java.util.Date;
import java.util.List;

/**
 * Created by jianghw on 2016/9/19.
 * <p>
 * 描述：关联医生的参数  {@link DoctorCooperationBean}
 */
public class RelatedDoctorBean {
    private int doctorId;
    private String avatar;
    private String email;
    private String workPhone;
    private String mobilePhone;
    private boolean isValidDoctor;
    private Date accountActivateDate;
    private String doctorType;
    private String firstName;
    private String lastName;
    private String kauriHealthId;
    private String gender;
    private Date dateOfBirth;
    private int userId;
    private double registPercentage;
    private int years;
    private String practiceField;
    private String introduction;
    private String fullName;
    private double totalCredit;
    private double availableCredit;
    private boolean allowUnknownMessage;
    private String educationTitle;
    private String mentorshipTitle;
    private String hospitalTitle;
    private String nationalIdentity;
    private String certificationNumber;
    private String workingExperience;
    /**
     * doctorInformationId : 635
     * departmentId : 40
     * hospitalName : 天津市职工医院
     * doctorId : 11807
     * department : {"departmentId":40,"departmentName":"手显微外科","level":2,"parent":37}
     * outpatientAvalibility : null
     */

    private DoctorInformationDisplayBean doctorInformations;
    private DoctorEducationDisplayBean doctorEducation;
    private List<DoctorGradeDisplayBean> doctorGrades;
    private List<DoctorCertificationDisplayBean> doctorCertifications;
    private List<ProductDisplayBean> products;
    private List<DoctorGuidanceDisplayBean> doctorGuidances;

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public boolean isIsValidDoctor() {
        return isValidDoctor;
    }

    public void setIsValidDoctor(boolean isValidDoctor) {
        this.isValidDoctor = isValidDoctor;
    }

    public Date getAccountActivateDate() {
        return accountActivateDate;
    }

    public void setAccountActivateDate(Date accountActivateDate) {
        this.accountActivateDate = accountActivateDate;
    }

    public String getDoctorType() {
        return doctorType;
    }

    public void setDoctorType(String doctorType) {
        this.doctorType = doctorType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getKauriHealthId() {
        return kauriHealthId;
    }

    public void setKauriHealthId(String kauriHealthId) {
        this.kauriHealthId = kauriHealthId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getRegistPercentage() {
        return registPercentage;
    }

    public void setRegistPercentage(double registPercentage) {
        this.registPercentage = registPercentage;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public String getPracticeField() {
        return practiceField;
    }

    public void setPracticeField(String practiceField) {
        this.practiceField = practiceField;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public double getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(double totalCredit) {
        this.totalCredit = totalCredit;
    }

    public double getAvailableCredit() {
        return availableCredit;
    }

    public void setAvailableCredit(double availableCredit) {
        this.availableCredit = availableCredit;
    }

    public boolean isAllowUnknownMessage() {
        return allowUnknownMessage;
    }

    public void setAllowUnknownMessage(boolean allowUnknownMessage) {
        this.allowUnknownMessage = allowUnknownMessage;
    }

    public String getEducationTitle() {
        return educationTitle;
    }

    public void setEducationTitle(String educationTitle) {
        this.educationTitle = educationTitle;
    }

    public String getMentorshipTitle() {
        return mentorshipTitle;
    }

    public void setMentorshipTitle(String mentorshipTitle) {
        this.mentorshipTitle = mentorshipTitle;
    }

    public String getHospitalTitle() {
        return hospitalTitle;
    }

    public void setHospitalTitle(String hospitalTitle) {
        this.hospitalTitle = hospitalTitle;
    }

    public String getNationalIdentity() {
        return nationalIdentity;
    }

    public void setNationalIdentity(String nationalIdentity) {
        this.nationalIdentity = nationalIdentity;
    }

    public String getCertificationNumber() {
        return certificationNumber;
    }

    public void setCertificationNumber(String certificationNumber) {
        this.certificationNumber = certificationNumber;
    }

    public String getWorkingExperience() {
        return workingExperience;
    }

    public void setWorkingExperience(String workingExperience) {
        this.workingExperience = workingExperience;
    }

    public DoctorInformationDisplayBean getDoctorInformations() {
        return doctorInformations;
    }

    public void setDoctorInformations(DoctorInformationDisplayBean doctorInformations) {
        this.doctorInformations = doctorInformations;
    }

    public DoctorEducationDisplayBean getDoctorEducation() {
        return doctorEducation;
    }

    public void setDoctorEducation(DoctorEducationDisplayBean doctorEducation) {
        this.doctorEducation = doctorEducation;
    }

    public List<DoctorGradeDisplayBean> getDoctorGrades() {
        return doctorGrades;
    }

    public void setDoctorGrades(List<DoctorGradeDisplayBean> doctorGrades) {
        this.doctorGrades = doctorGrades;
    }

    public List<DoctorCertificationDisplayBean> getDoctorCertifications() {
        return doctorCertifications;
    }

    public void setDoctorCertifications(List<DoctorCertificationDisplayBean> doctorCertifications) {
        this.doctorCertifications = doctorCertifications;
    }

    public List<ProductDisplayBean> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDisplayBean> products) {
        this.products = products;
    }

    public List<DoctorGuidanceDisplayBean> getDoctorGuidances() {
        return doctorGuidances;
    }

    public void setDoctorGuidances(List<DoctorGuidanceDisplayBean> doctorGuidances) {
        this.doctorGuidances = doctorGuidances;
    }

}
