package com.kaurihealth.datalib.request_bean.bean;

/**
 * Created by KauriHealth on 2016/9/13.
 */
public class NewPrescriptionDocumentBean {

    /**
     * fileName : sample string 1
     * comment : sample string 2
     * documentFormat : sample string 3
     * documentUrl : sample string 4
     * displayName : sample string 5
     * prescriptionId : 6
     */

    private String fileName;
    private String comment;
    private String documentFormat;
    private String documentUrl;
    private String displayName;
    private int prescriptionId;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDocumentFormat() {
        return documentFormat;
    }

    public void setDocumentFormat(String documentFormat) {
        this.documentFormat = documentFormat;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(int prescriptionId) {
        this.prescriptionId = prescriptionId;
    }
}
