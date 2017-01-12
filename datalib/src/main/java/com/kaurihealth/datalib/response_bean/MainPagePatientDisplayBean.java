package com.kaurihealth.datalib.response_bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;

/**
 * Created by jianghw on 2016/12/14.
 * <p/>
 * Describe:
 * GET api/DoctorPatientRelationship/LoadDoctorMainPage
 * 首页--> 全部病历
 */

public class MainPagePatientDisplayBean implements Parcelable {

    /**
     * patientRecordId : 1
     * patientId : 2
     * doctorFullName : sample string 3
     * patientFullName : sample string 4
     * recordType : sample string 5
     * gender : sample string 6
     * dateOfBirth : 2016-12-14T13:12:50+08:00
     * latestRecordDate : 2016-12-14T13:12:50+08:00
     * sicknesses : [{"sicknessId":1,"sicknessName":"sample string 1"},{"sicknessId":1,"sicknessName":"sample string 1"}]
     */

    private int patientRecordId;
    private int patientId;
    private String doctorFullName;
    private String patientFullName;
    private String recordType;
    private String gender;
    private Date dateOfBirth;
    private Date latestRecordDate;

    public boolean isCheck;//自新增
    public String age;//自新增
    /**
     * sicknessId : 1
     * sicknessName : sample string 1
     */

    private List<SicknessesBean> sicknesses;

    public int getPatientRecordId() {
        return patientRecordId;
    }

    public void setPatientRecordId(int patientRecordId) {
        this.patientRecordId = patientRecordId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getDoctorFullName() {
        return doctorFullName;
    }

    public void setDoctorFullName(String doctorFullName) {
        this.doctorFullName = doctorFullName;
    }

    public String getPatientFullName() {
        return patientFullName;
    }

    public void setPatientFullName(String patientFullName) {
        this.patientFullName = patientFullName;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
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

    public Date getLatestRecordDate() {
        return latestRecordDate;
    }

    public void setLatestRecordDate(Date latestRecordDate) {
        this.latestRecordDate = latestRecordDate;
    }

    public List<SicknessesBean> getSicknesses() {
        return sicknesses;
    }

    public void setSicknesses(List<SicknessesBean> sicknesses) {
        this.sicknesses = sicknesses;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.patientRecordId);
        dest.writeInt(this.patientId);
        dest.writeString(this.doctorFullName);
        dest.writeString(this.patientFullName);
        dest.writeString(this.recordType);
        dest.writeString(this.gender);
        dest.writeLong(this.dateOfBirth != null ? this.dateOfBirth.getTime() : -1);
        dest.writeLong(this.latestRecordDate != null ? this.latestRecordDate.getTime() : -1);
        dest.writeByte(this.isCheck ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.sicknesses);
    }

    public MainPagePatientDisplayBean() {
    }

    protected MainPagePatientDisplayBean(Parcel in) {
        this.patientRecordId = in.readInt();
        this.patientId = in.readInt();
        this.doctorFullName = in.readString();
        this.patientFullName = in.readString();
        this.recordType = in.readString();
        this.gender = in.readString();
        long tmpDateOfBirth = in.readLong();
        this.dateOfBirth = tmpDateOfBirth == -1 ? null : new Date(tmpDateOfBirth);
        long tmpLatestRecordDate = in.readLong();
        this.latestRecordDate = tmpLatestRecordDate == -1 ? null : new Date(tmpLatestRecordDate);
        this.isCheck = in.readByte() != 0;
        this.sicknesses = in.createTypedArrayList(SicknessesBean.CREATOR);
    }

    public static final Parcelable.Creator<MainPagePatientDisplayBean> CREATOR = new Parcelable.Creator<MainPagePatientDisplayBean>() {
        @Override
        public MainPagePatientDisplayBean createFromParcel(Parcel source) {
            return new MainPagePatientDisplayBean(source);
        }

        @Override
        public MainPagePatientDisplayBean[] newArray(int size) {
            return new MainPagePatientDisplayBean[size];
        }
    };
}
