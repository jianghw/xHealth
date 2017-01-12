package com.kaurihealth.datalib.response_bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jianghw on 2016/12/27.
 * <p/>
 * Describe:
 */
public class SicknessBean implements Parcelable {
    private Integer patientRecordSicknessId;
    private Integer patientRecordId;
    private Integer sicknessId;
    private String sicknessName;

    public Integer getPatientRecordSicknessId() {
        return patientRecordSicknessId;
    }

    public void setPatientRecordSicknessId(int patientRecordSicknessId) {
        this.patientRecordSicknessId = patientRecordSicknessId;
    }

    public Integer getPatientRecordId() {
        return patientRecordId;
    }

    public void setPatientRecordId(Integer patientRecordId) {
        this.patientRecordId = patientRecordId;
    }

    public Integer getSicknessId() {
        return sicknessId;
    }

    public void setSicknessId(int sicknessId) {
        this.sicknessId = sicknessId;
    }

    public String getSicknessName() {
        return sicknessName;
    }

    public void setSicknessName(String sicknessName) {
        this.sicknessName = sicknessName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.patientRecordSicknessId);
        dest.writeValue(this.patientRecordId);
        dest.writeValue(this.sicknessId);
        dest.writeString(this.sicknessName);
    }

    public SicknessBean() {
    }

    protected SicknessBean(Parcel in) {
        this.patientRecordSicknessId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.patientRecordId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.sicknessId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.sicknessName = in.readString();
    }

    public static final Parcelable.Creator<SicknessBean> CREATOR = new Parcelable.Creator<SicknessBean>() {
        @Override
        public SicknessBean createFromParcel(Parcel source) {
            return new SicknessBean(source);
        }

        @Override
        public SicknessBean[] newArray(int size) {
            return new SicknessBean[size];
        }
    };
}
