package com.kaurihealth.datalib.request_bean.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;

import java.util.Date;

/**
 * Created by 张磊 on 2016/7/27.
 * 介绍：
 */
public class DoctorRelationshipBean {
    public int doctorRelationshipId;
    public int sourceDoctorId;
    public int destinationDoctorId;
    public String status;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss", timezone="GMT")
    public Date createdDate;
    public String comment;
    public DoctorDisplayBean relatedDoctor;
}
