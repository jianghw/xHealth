package com.kaurihealth.kaurihealth.patientwithdoctor.util.endtime;

import java.io.Serializable;

/**
 * Created by 张磊 on 2016/4/29.
 * 介绍：
 */
public class IllegalFormatTimeException extends IllegalArgumentException implements
        Serializable {

    private static final long serialVersionUID = 18830826L;

    // the constructor is not callable from outside from the package
    public IllegalFormatTimeException(String detailMessage) {
        super(detailMessage);
    }
}
