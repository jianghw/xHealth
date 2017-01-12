package com.kaurihealth.datalib.response_bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jianghw on 2016/12/14.
 * <p/>
 * Describe: 疾病
 * GET api/DoctorPatientRelationship/LoadDoctorMainPage
 */
public class SicknessesBean implements Parcelable {
    private int sicknessId;
    private int departmentId;
    private String sicknessName;
    private String departmentName;

    public int getSicknessId() {
        return sicknessId;
    }

    public void setSicknessId(int sicknessId) {
        this.sicknessId = sicknessId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getSicknessName() {
        return sicknessName;
    }

    public void setSicknessName(String sicknessName) {
        this.sicknessName = sicknessName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.sicknessId);
        dest.writeInt(this.departmentId);
        dest.writeString(this.sicknessName);
        dest.writeString(this.departmentName);
    }

    public SicknessesBean() {
    }

    protected SicknessesBean(Parcel in) {
        this.sicknessId = in.readInt();
        this.departmentId = in.readInt();
        this.sicknessName = in.readString();
        this.departmentName = in.readString();
    }

    public static final Parcelable.Creator<SicknessesBean> CREATOR = new Parcelable.Creator<SicknessesBean>() {
        @Override
        public SicknessesBean createFromParcel(Parcel source) {
            return new SicknessesBean(source);
        }

        @Override
        public SicknessesBean[] newArray(int size) {
            return new SicknessesBean[size];
        }
    };
}
