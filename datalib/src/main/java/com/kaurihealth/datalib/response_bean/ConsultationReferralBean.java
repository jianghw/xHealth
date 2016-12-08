package com.kaurihealth.datalib.response_bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by mip on 2016/10/26.
 */


public class ConsultationReferralBean implements Serializable{
    private int consultationReferralId;
    private int sourceDoctorId;
    private int destinationDoctorId;
    private Date date;
    private String comment;


    private SourceDoctorBean sourceDoctor;


    private DestinationDoctorBean destinationDoctor;
    private int patientRequestId;
    private int doctorPatientRelationshipId;
    private String status;
    private int patientId;
    private Object patientRequest;


    private DoctorPatientRelationshipBean doctorPatientRelationship;

    public int getConsultationReferralId() {
        return consultationReferralId;
    }

    public void setConsultationReferralId(int consultationReferralId) {
        this.consultationReferralId = consultationReferralId;
    }

    public int getSourceDoctorId() {
        return sourceDoctorId;
    }

    public void setSourceDoctorId(int sourceDoctorId) {
        this.sourceDoctorId = sourceDoctorId;
    }

    public int getDestinationDoctorId() {
        return destinationDoctorId;
    }

    public void setDestinationDoctorId(int destinationDoctorId) {
        this.destinationDoctorId = destinationDoctorId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public SourceDoctorBean getSourceDoctor() {
        return sourceDoctor;
    }

    public void setSourceDoctor(SourceDoctorBean sourceDoctor) {
        this.sourceDoctor = sourceDoctor;
    }

    public DestinationDoctorBean getDestinationDoctor() {
        return destinationDoctor;
    }

    public void setDestinationDoctor(DestinationDoctorBean destinationDoctor) {
        this.destinationDoctor = destinationDoctor;
    }

    public int getPatientRequestId() {
        return patientRequestId;
    }

    public void setPatientRequestId(int patientRequestId) {
        this.patientRequestId = patientRequestId;
    }

    public int getDoctorPatientRelationshipId() {
        return doctorPatientRelationshipId;
    }

    public void setDoctorPatientRelationshipId(int doctorPatientRelationshipId) {
        this.doctorPatientRelationshipId = doctorPatientRelationshipId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public Object getPatientRequest() {
        return patientRequest;
    }

    public void setPatientRequest(Object patientRequest) {
        this.patientRequest = patientRequest;
    }

    public DoctorPatientRelationshipBean getDoctorPatientRelationship() {
        return doctorPatientRelationship;
    }

    public void setDoctorPatientRelationship(DoctorPatientRelationshipBean doctorPatientRelationship) {
        this.doctorPatientRelationship = doctorPatientRelationship;
    }

    public static class SourceDoctorBean {
        private int doctorId;
        private String avatar;
        private String email;
        private String workPhone;
        private String mobilePhone;
        private boolean isValidDoctor;
        private Object accountActivateDate;
        private String doctorType;
        private String firstName;
        private String lastName;
        private String kauriHealthId;
        private String gender;
        private String dateOfBirth;
        private int userId;
        private double registPercentage;
        private Object years;
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


        private DoctorInformationsBean doctorInformations;
        private Object doctorEducation;
        private List<?> doctorGrades;
        private List<?> doctorCertifications;
        private List<?> products;
        private List<?> doctorGuidances;

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

        public Object getAccountActivateDate() {
            return accountActivateDate;
        }

        public void setAccountActivateDate(Object accountActivateDate) {
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

        public String getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(String dateOfBirth) {
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

        public Object getYears() {
            return years;
        }

        public void setYears(Object years) {
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

        public DoctorInformationsBean getDoctorInformations() {
            return doctorInformations;
        }

        public void setDoctorInformations(DoctorInformationsBean doctorInformations) {
            this.doctorInformations = doctorInformations;
        }

        public Object getDoctorEducation() {
            return doctorEducation;
        }

        public void setDoctorEducation(Object doctorEducation) {
            this.doctorEducation = doctorEducation;
        }

        public List<?> getDoctorGrades() {
            return doctorGrades;
        }

        public void setDoctorGrades(List<?> doctorGrades) {
            this.doctorGrades = doctorGrades;
        }

        public List<?> getDoctorCertifications() {
            return doctorCertifications;
        }

        public void setDoctorCertifications(List<?> doctorCertifications) {
            this.doctorCertifications = doctorCertifications;
        }

        public List<?> getProducts() {
            return products;
        }

        public void setProducts(List<?> products) {
            this.products = products;
        }

        public List<?> getDoctorGuidances() {
            return doctorGuidances;
        }

        public void setDoctorGuidances(List<?> doctorGuidances) {
            this.doctorGuidances = doctorGuidances;
        }

        public static class DoctorInformationsBean {
            private int doctorInformationId;
            private int departmentId;
            private Object hospitalName;
            private int doctorId;
            private Object department;
            private Object outpatientAvalibility;

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

            public Object getHospitalName() {
                return hospitalName;
            }

            public void setHospitalName(Object hospitalName) {
                this.hospitalName = hospitalName;
            }

            public int getDoctorId() {
                return doctorId;
            }

            public void setDoctorId(int doctorId) {
                this.doctorId = doctorId;
            }

            public Object getDepartment() {
                return department;
            }

            public void setDepartment(Object department) {
                this.department = department;
            }

            public Object getOutpatientAvalibility() {
                return outpatientAvalibility;
            }

            public void setOutpatientAvalibility(Object outpatientAvalibility) {
                this.outpatientAvalibility = outpatientAvalibility;
            }
        }
    }

    public static class DestinationDoctorBean {
        private int doctorId;
        private String avatar;
        private String email;
        private String workPhone;
        private String mobilePhone;
        private boolean isValidDoctor;
        private Object accountActivateDate;
        private String doctorType;
        private String firstName;
        private String lastName;
        private String kauriHealthId;
        private String gender;
        private String dateOfBirth;
        private int userId;
        private double registPercentage;
        private Object years;
        private String practiceField;
        private Object introduction;
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


        private DoctorInformationsBean doctorInformations;
        private Object doctorEducation;
        private List<?> doctorGrades;
        private List<?> doctorCertifications;
        private List<?> products;
        private List<?> doctorGuidances;

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

        public Object getAccountActivateDate() {
            return accountActivateDate;
        }

        public void setAccountActivateDate(Object accountActivateDate) {
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

        public String getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(String dateOfBirth) {
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

        public Object getYears() {
            return years;
        }

        public void setYears(Object years) {
            this.years = years;
        }

        public String getPracticeField() {
            return practiceField;
        }

        public void setPracticeField(String practiceField) {
            this.practiceField = practiceField;
        }

        public Object getIntroduction() {
            return introduction;
        }

        public void setIntroduction(Object introduction) {
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

        public DoctorInformationsBean getDoctorInformations() {
            return doctorInformations;
        }

        public void setDoctorInformations(DoctorInformationsBean doctorInformations) {
            this.doctorInformations = doctorInformations;
        }

        public Object getDoctorEducation() {
            return doctorEducation;
        }

        public void setDoctorEducation(Object doctorEducation) {
            this.doctorEducation = doctorEducation;
        }

        public List<?> getDoctorGrades() {
            return doctorGrades;
        }

        public void setDoctorGrades(List<?> doctorGrades) {
            this.doctorGrades = doctorGrades;
        }

        public List<?> getDoctorCertifications() {
            return doctorCertifications;
        }

        public void setDoctorCertifications(List<?> doctorCertifications) {
            this.doctorCertifications = doctorCertifications;
        }

        public List<?> getProducts() {
            return products;
        }

        public void setProducts(List<?> products) {
            this.products = products;
        }

        public List<?> getDoctorGuidances() {
            return doctorGuidances;
        }

        public void setDoctorGuidances(List<?> doctorGuidances) {
            this.doctorGuidances = doctorGuidances;
        }

        public static class DoctorInformationsBean {
            private int doctorInformationId;
            private int departmentId;
            private Object hospitalName;
            private int doctorId;
            private Object department;
            private Object outpatientAvalibility;

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

            public Object getHospitalName() {
                return hospitalName;
            }

            public void setHospitalName(Object hospitalName) {
                this.hospitalName = hospitalName;
            }

            public int getDoctorId() {
                return doctorId;
            }

            public void setDoctorId(int doctorId) {
                this.doctorId = doctorId;
            }

            public Object getDepartment() {
                return department;
            }

            public void setDepartment(Object department) {
                this.department = department;
            }

            public Object getOutpatientAvalibility() {
                return outpatientAvalibility;
            }

            public void setOutpatientAvalibility(Object outpatientAvalibility) {
                this.outpatientAvalibility = outpatientAvalibility;
            }
        }
    }

    public static class DoctorPatientRelationshipBean {
        private int doctorPatientId;
        private int doctorId;
        private int patientId;
        private boolean isActive;
        private Object isWaitingForPatientConfirm;
        private Object isPatientConfirmed;
        private String relationship;


        private DoctorBean doctor;


        private PatientBean patient;
        private Date requestDate;
        private String startDate;
        private String endDate;
        private String comment;
        private String relationshipReason;
        private Object referralDoctor;
        private String referralDate;
        private Object referralReason;

        public int getDoctorPatientId() {
            return doctorPatientId;
        }

        public void setDoctorPatientId(int doctorPatientId) {
            this.doctorPatientId = doctorPatientId;
        }

        public int getDoctorId() {
            return doctorId;
        }

        public void setDoctorId(int doctorId) {
            this.doctorId = doctorId;
        }

        public int getPatientId() {
            return patientId;
        }

        public void setPatientId(int patientId) {
            this.patientId = patientId;
        }

        public boolean isIsActive() {
            return isActive;
        }

        public void setIsActive(boolean isActive) {
            this.isActive = isActive;
        }

        public Object getIsWaitingForPatientConfirm() {
            return isWaitingForPatientConfirm;
        }

        public void setIsWaitingForPatientConfirm(Object isWaitingForPatientConfirm) {
            this.isWaitingForPatientConfirm = isWaitingForPatientConfirm;
        }

        public Object getIsPatientConfirmed() {
            return isPatientConfirmed;
        }

        public void setIsPatientConfirmed(Object isPatientConfirmed) {
            this.isPatientConfirmed = isPatientConfirmed;
        }

        public String getRelationship() {
            return relationship;
        }

        public void setRelationship(String relationship) {
            this.relationship = relationship;
        }

        public DoctorBean getDoctor() {
            return doctor;
        }

        public void setDoctor(DoctorBean doctor) {
            this.doctor = doctor;
        }

        public PatientBean getPatient() {
            return patient;
        }

        public void setPatient(PatientBean patient) {
            this.patient = patient;
        }

        public Date getRequestDate() {
            return requestDate;
        }

        public void setRequestDate(Date requestDate) {
            this.requestDate = requestDate;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getRelationshipReason() {
            return relationshipReason;
        }

        public void setRelationshipReason(String relationshipReason) {
            this.relationshipReason = relationshipReason;
        }

        public Object getReferralDoctor() {
            return referralDoctor;
        }

        public void setReferralDoctor(Object referralDoctor) {
            this.referralDoctor = referralDoctor;
        }

        public String getReferralDate() {
            return referralDate;
        }

        public void setReferralDate(String referralDate) {
            this.referralDate = referralDate;
        }

        public Object getReferralReason() {
            return referralReason;
        }

        public void setReferralReason(Object referralReason) {
            this.referralReason = referralReason;
        }

        public static class DoctorBean {
            private int doctorId;
            private String avatar;
            private String email;
            private String workPhone;
            private String mobilePhone;
            private boolean isValidDoctor;
            private Object accountActivateDate;
            private String doctorType;
            private String firstName;
            private String lastName;
            private String kauriHealthId;
            private String gender;
            private String dateOfBirth;
            private int userId;
            private double registPercentage;
            private Object years;
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


            private DoctorInformationsBean doctorInformations;
            private Object doctorEducation;
            private List<?> doctorGrades;
            private List<?> doctorCertifications;
            private List<?> products;
            private List<?> doctorGuidances;

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

            public Object getAccountActivateDate() {
                return accountActivateDate;
            }

            public void setAccountActivateDate(Object accountActivateDate) {
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

            public String getDateOfBirth() {
                return dateOfBirth;
            }

            public void setDateOfBirth(String dateOfBirth) {
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

            public Object getYears() {
                return years;
            }

            public void setYears(Object years) {
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

            public DoctorInformationsBean getDoctorInformations() {
                return doctorInformations;
            }

            public void setDoctorInformations(DoctorInformationsBean doctorInformations) {
                this.doctorInformations = doctorInformations;
            }

            public Object getDoctorEducation() {
                return doctorEducation;
            }

            public void setDoctorEducation(Object doctorEducation) {
                this.doctorEducation = doctorEducation;
            }

            public List<?> getDoctorGrades() {
                return doctorGrades;
            }

            public void setDoctorGrades(List<?> doctorGrades) {
                this.doctorGrades = doctorGrades;
            }

            public List<?> getDoctorCertifications() {
                return doctorCertifications;
            }

            public void setDoctorCertifications(List<?> doctorCertifications) {
                this.doctorCertifications = doctorCertifications;
            }

            public List<?> getProducts() {
                return products;
            }

            public void setProducts(List<?> products) {
                this.products = products;
            }

            public List<?> getDoctorGuidances() {
                return doctorGuidances;
            }

            public void setDoctorGuidances(List<?> doctorGuidances) {
                this.doctorGuidances = doctorGuidances;
            }

            public static class DoctorInformationsBean {
                private int doctorInformationId;
                private int departmentId;
                private Object hospitalName;
                private int doctorId;
                private Object department;
                private Object outpatientAvalibility;

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

                public Object getHospitalName() {
                    return hospitalName;
                }

                public void setHospitalName(Object hospitalName) {
                    this.hospitalName = hospitalName;
                }

                public int getDoctorId() {
                    return doctorId;
                }

                public void setDoctorId(int doctorId) {
                    this.doctorId = doctorId;
                }

                public Object getDepartment() {
                    return department;
                }

                public void setDepartment(Object department) {
                    this.department = department;
                }

                public Object getOutpatientAvalibility() {
                    return outpatientAvalibility;
                }

                public void setOutpatientAvalibility(Object outpatientAvalibility) {
                    this.outpatientAvalibility = outpatientAvalibility;
                }
            }
        }

        public static class PatientBean {
            private int patientId;
            private String nationalIdentity;
            private String kauriHealthId;
            private String avatar;
            private String nationalMedicalInsuranceNumber;
            private String mobileNumber;
            private String homePhoneNumber;
            private String emergencyContactNumber;
            private Object emergencyContactName;
            private Object emergencyContactRelationship;
            private String patientType;
            private int userId;
            private String firstName;
            private String lastName;
            private String fullName;
            private String gender;
            private Date dateOfBirth;
            private String email;
            private Object address;
            private Object addressId;
            private boolean isActive;
            private double totalCredit;
            private double availableCredit;
            private boolean allowUnknownMessage;
            private Object allergyDetail;

            public int getPatientId() {
                return patientId;
            }

            public void setPatientId(int patientId) {
                this.patientId = patientId;
            }

            public String getNationalIdentity() {
                return nationalIdentity;
            }

            public void setNationalIdentity(String nationalIdentity) {
                this.nationalIdentity = nationalIdentity;
            }

            public String getKauriHealthId() {
                return kauriHealthId;
            }

            public void setKauriHealthId(String kauriHealthId) {
                this.kauriHealthId = kauriHealthId;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getNationalMedicalInsuranceNumber() {
                return nationalMedicalInsuranceNumber;
            }

            public void setNationalMedicalInsuranceNumber(String nationalMedicalInsuranceNumber) {
                this.nationalMedicalInsuranceNumber = nationalMedicalInsuranceNumber;
            }

            public String getMobileNumber() {
                return mobileNumber;
            }

            public void setMobileNumber(String mobileNumber) {
                this.mobileNumber = mobileNumber;
            }

            public String getHomePhoneNumber() {
                return homePhoneNumber;
            }

            public void setHomePhoneNumber(String homePhoneNumber) {
                this.homePhoneNumber = homePhoneNumber;
            }

            public String getEmergencyContactNumber() {
                return emergencyContactNumber;
            }

            public void setEmergencyContactNumber(String emergencyContactNumber) {
                this.emergencyContactNumber = emergencyContactNumber;
            }

            public Object getEmergencyContactName() {
                return emergencyContactName;
            }

            public void setEmergencyContactName(Object emergencyContactName) {
                this.emergencyContactName = emergencyContactName;
            }

            public Object getEmergencyContactRelationship() {
                return emergencyContactRelationship;
            }

            public void setEmergencyContactRelationship(Object emergencyContactRelationship) {
                this.emergencyContactRelationship = emergencyContactRelationship;
            }

            public String getPatientType() {
                return patientType;
            }

            public void setPatientType(String patientType) {
                this.patientType = patientType;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
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

            public String getFullName() {
                return fullName;
            }

            public void setFullName(String fullName) {
                this.fullName = fullName;
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

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public Object getAddress() {
                return address;
            }

            public void setAddress(Object address) {
                this.address = address;
            }

            public Object getAddressId() {
                return addressId;
            }

            public void setAddressId(Object addressId) {
                this.addressId = addressId;
            }

            public boolean isIsActive() {
                return isActive;
            }

            public void setIsActive(boolean isActive) {
                this.isActive = isActive;
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

            public Object getAllergyDetail() {
                return allergyDetail;
            }

            public void setAllergyDetail(Object allergyDetail) {
                this.allergyDetail = allergyDetail;
            }
        }
    }
}

