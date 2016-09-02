package com.kaurihealth.datalib.request_bean.bean;

/**
 * Created by Nick on 21/07/2016.
 */
public class NewDocumentDisplayBean {
    public String documentUrl;
    public String documentFormat;
    public String fileName;
    public String displayName;
    public String comment;

    public NewDocumentDisplayBean(String documentUrl, String fileName, String displayName, String documentFormat, String comment) {
        this.documentUrl = documentUrl;
        this.fileName = fileName;
        this.displayName = displayName;
        this.documentFormat = documentFormat;
        this.comment = comment;
    }

    public NewDocumentDisplayBean() {
    }
}
