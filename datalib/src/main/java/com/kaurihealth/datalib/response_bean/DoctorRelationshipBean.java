package com.kaurihealth.datalib.response_bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by 张磊 on 2016/7/27.
 * 介绍：
 */
public class DoctorRelationshipBean implements Parcelable {
    public int doctorRelationshipId;  //关联医生ID
    public int sourceDoctorId; //发起医生ID
    public int destinationDoctorId; //关联医生ID
    public String status;  //状态（0：等待。1：接受，2：拒绝）

    public Date createdDate; //创建时间
    public String comment; //说明
    public DoctorDisplayBean relatedDoctor;  //关联医生的参数

    public boolean isCheck;//新添加的字段为了判断

    private String index;
    private DoctorRelationshipBean DoctorRelationshipBean;

    public DoctorRelationshipBean(){
    }

    public DoctorRelationshipBean(String index, DoctorRelationshipBean name) {
        this.index = index;
        this.DoctorRelationshipBean = name;
    }

    public String getIndex() {
        return index;
    }

    public DoctorRelationshipBean getName() {
        return DoctorRelationshipBean;
    }

    public int getDoctorRelationshipId() {
        return doctorRelationshipId;
    }

    public void setDoctorRelationshipId(int doctorRelationshipId) {
        this.doctorRelationshipId = doctorRelationshipId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public DoctorDisplayBean getRelatedDoctor() {
        return relatedDoctor;
    }

    public void setRelatedDoctor(DoctorDisplayBean relatedDoctor) {
        this.relatedDoctor = relatedDoctor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.doctorRelationshipId);
        dest.writeInt(this.sourceDoctorId);
        dest.writeInt(this.destinationDoctorId);
        dest.writeString(this.status);
        dest.writeLong(this.createdDate != null ? this.createdDate.getTime() : -1);
        dest.writeString(this.comment);
        dest.writeSerializable(this.relatedDoctor);
        dest.writeByte(this.isCheck ? (byte) 1 : (byte) 0);
        dest.writeString(this.index);
        dest.writeParcelable(this.DoctorRelationshipBean, flags);
    }

    protected DoctorRelationshipBean(Parcel in) {
        this.doctorRelationshipId = in.readInt();
        this.sourceDoctorId = in.readInt();
        this.destinationDoctorId = in.readInt();
        this.status = in.readString();
        long tmpCreatedDate = in.readLong();
        this.createdDate = tmpCreatedDate == -1 ? null : new Date(tmpCreatedDate);
        this.comment = in.readString();
        this.relatedDoctor = (DoctorDisplayBean) in.readSerializable();
        this.isCheck = in.readByte() != 0;
        this.index = in.readString();
        this.DoctorRelationshipBean = in.readParcelable(com.kaurihealth.datalib.response_bean.DoctorRelationshipBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<DoctorRelationshipBean> CREATOR = new Parcelable.Creator<DoctorRelationshipBean>() {
        @Override
        public DoctorRelationshipBean createFromParcel(Parcel source) {
            return new DoctorRelationshipBean(source);
        }

        @Override
        public DoctorRelationshipBean[] newArray(int size) {
            return new DoctorRelationshipBean[size];
        }
    };
}
