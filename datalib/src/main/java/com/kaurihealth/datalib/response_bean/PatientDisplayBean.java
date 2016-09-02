package com.kaurihealth.datalib.response_bean;

import com.kaurihealth.datalib.request_bean.bean.AddressDisplayBean;

import java.util.Date;

/**
 * Created by jianghw on 2016/8/24.
 * <p>
 * 描述：
 */
public class PatientDisplayBean {
    private int patientId;
    private String nationalIdentity;
    private String kauriHealthId;
    private String avatar;
    private String nationalMedicalInsuranceNumber;
    private String mobileNumber;
    private String homePhoneNumber;
    private String emergencyContactNumber;
    private String emergencyContactName;
    private String emergencyContactRelationship;
    private String patientType;
    private int userId;
    private String firstName;
    private String lastName;
    private String fullName;
    private String gender;
    private Date dateOfBirth;
    private String email;
    private AddressDisplayBean address;
    private int addressId;
    private boolean isActive;
    private double totalCredit;
    private double availableCredit;
    private boolean allowUnknownMessage;
    private String allergyDetail;

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

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public Object getEmergencyContactRelationship() {
        return emergencyContactRelationship;
    }

    public void setEmergencyContactRelationship(String emergencyContactRelationship) {
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

    public AddressDisplayBean getAddress() {
        return address;
    }

    public void setAddress(AddressDisplayBean address) {
        this.address = address;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
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

    public String getAllergyDetail() {
        return allergyDetail;
    }

    public void setAllergyDetail(String allergyDetail) {
        this.allergyDetail = allergyDetail;
    }
}
